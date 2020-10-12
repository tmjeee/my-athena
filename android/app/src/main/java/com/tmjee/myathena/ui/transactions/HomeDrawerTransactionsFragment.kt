package com.tmjee.myathena.ui.transactions

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tmjee.myathena.MainActivityViewModel
import com.tmjee.myathena.R
import com.tmjee.myathena.databinding.FragmentHomeDrawerTransactionsBinding
import com.tmjee.myathena.ui.MenuAwareFragment
import com.tmjee.myathena.ui.UIUtil
import com.tmjee.myathena.ui.home.HomeFragment
import com.tmjee.myathena.ui.setSupportActionBarAndSetupWithNavController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeDrawerTransactionsFragment constructor(): MenuAwareFragment() {

    lateinit var binding: FragmentHomeDrawerTransactionsBinding
    val activityViewModel: MainActivityViewModel by activityViewModels()
    val viewModel: HomeDrawerTransactionsFragmentViewModel by viewModels()
    lateinit var adapter: Adapter


    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {
        binding = FragmentHomeDrawerTransactionsBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        val navController = Navigation.findNavController(container!!);
        val mainNavController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)

        val homeFragment: HomeFragment = parentFragment // R.id.drawer_nav_host_fragment
                                            ?.parentFragment as HomeFragment // home fragment

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.home_drawer_loan_details_fragment,
                R.id.home_drawer_payee_and_linked_accounts_fragment,
                R.id.home_drawer_payment_and_transfers_fragment,
                R.id.home_drawer_statements_fragment,
                R.id.home_drawer_transactions_fragment
            ),
            homeFragment.binding.drawerLayout)

        binding.appBarLayoutIncluded.account = activityViewModel.account.value
        binding.appBarLayoutIncluded.uiUtil = UIUtil

        setSupportActionBarAndSetupWithNavController(
            toolbar = binding.appBarLayoutIncluded.toolbar,
            navController = navController,
            appBarConfiguration = appBarConfiguration,
            navigationView = homeFragment.binding.navigationView
        )

        adapter = Adapter(requireContext(), layoutInflater)
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.swipeRefresh.setOnRefreshListener {
           viewModel.reload(activityViewModel.user.value!!.userId, activityViewModel.account.value!!.accountId)
        }

        viewModel.reloading.observe(viewLifecycleOwner) {
            binding.swipeRefresh.isRefreshing = it
        }

        viewModel.transactions.observe(viewLifecycleOwner) {
            adapter.submitList(it.entries)
        }

        binding.recyclerView.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
                val itemCount = layoutManager.itemCount
                val visibleChildCount = layoutManager.childCount


                if (visibleChildCount + firstVisibleItemPosition >= itemCount) {
                    viewModel.loadMore(activityViewModel.user.value!!.userId, activityViewModel.account.value!!.accountId)
                }
            }
        })

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.reload(activityViewModel.user.value!!.userId, activityViewModel.account.value!!.accountId)
    }

}


