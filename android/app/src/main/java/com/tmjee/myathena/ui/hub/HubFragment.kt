package com.tmjee.myathena.ui.hub

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.tmjee.myathena.MainActivityViewModel
import com.tmjee.myathena.R
import com.tmjee.myathena.databinding.FragmentHubBinding
import com.tmjee.myathena.domain.Account
import com.tmjee.myathena.ui.MenuAwareFragment
import com.tmjee.myathena.ui.setSupportActionBarAndSetupWithNavController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HubFragment: MenuAwareFragment() {

    lateinit var binding: FragmentHubBinding
    val viewModel: HubFragmentViewModel by viewModels()
    val activityViewModel: MainActivityViewModel by activityViewModels()
    lateinit var adapter: Adapter
    val args:HubFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHubBinding.inflate(inflater, container, false);
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        viewModel.args = args

        val navController = findNavController()
        val appBarConfiguration = AppBarConfiguration(setOf(R.id.fragment_login, R.id.fragment_hub))

        setSupportActionBarAndSetupWithNavController(
            toolbar = binding.appBarLayoutIncluded.toolbar,
            navController = navController,
            appBarConfiguration = appBarConfiguration,
            navigationView = null
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = object:Adapter(layoutInflater) {
            override fun onItemViewClicked(account: Account) {
                viewModel.accountSelected(account)
            }
        }

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.swipeRefresh.setOnRefreshListener {
            viewModel.reload(args.userId)
        }


        viewModel.events.observe(viewLifecycleOwner) {
            if (!it.handled) {
                when (it.type) {
                    HubFragmentViewModel.EVT_TYPE_ACCOUNTS -> {
                        val accounts: List<Account> = it.payload as List<Account>
                        adapter.submitList(accounts)
                    }
                    HubFragmentViewModel.EVT_TYPE_REFRESHING -> {
                        val value: Boolean = it.payload as Boolean
                        binding.swipeRefresh.isRefreshing = value
                    }
                    HubFragmentViewModel.EVT_TYPE_FAILURE -> {
                        val throwable: Throwable = it.payload as Throwable
                        Toast.makeText(
                            requireContext(),
                            "Failure: ${throwable.message}",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    HubFragmentViewModel.EVT_TYPE_EMPTY -> {
                        val value: Boolean = it.payload as Boolean
                    }
                    HubFragmentViewModel.EVT_TYPE_ACCOUNT_SELECTED -> {
                        val account: Account = it.payload as Account
                        activityViewModel.account.value = account
                        findNavController().navigate(
                            HubFragmentDirections.actionToHome(
                                args.userId,
                                account.accountId
                            )
                        )
                    }
                }
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.reload(args.userId)
    }

}
