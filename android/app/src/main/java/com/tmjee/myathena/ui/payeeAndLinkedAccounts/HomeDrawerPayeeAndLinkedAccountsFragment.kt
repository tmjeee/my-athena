package com.tmjee.myathena.ui.payeeAndLinkedAccounts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.tmjee.myathena.MainActivityViewModel
import com.tmjee.myathena.R
import com.tmjee.myathena.databinding.FragmentHomeDrawerPayeeAndLinkedAccountsBinding
import com.tmjee.myathena.ui.MainApplicationViewModel
import com.tmjee.myathena.ui.MenuAwareFragment
import com.tmjee.myathena.ui.applicationViewModels
import com.tmjee.myathena.ui.home.HomeFragment
import com.tmjee.myathena.ui.setSupportActionBarAndSetupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import okhttp3.internal.addHeaderLenient

@AndroidEntryPoint
class HomeDrawerPayeeAndLinkedAccountsFragment: MenuAwareFragment() {

    val viewModel: HomeDrawerPayeeAndLinkedAccountsFragmentViewModel by viewModels()
    val activityViewModel: MainActivityViewModel by activityViewModels()
    val applicationViewModel: MainApplicationViewModel by applicationViewModels()


    lateinit var bindings: FragmentHomeDrawerPayeeAndLinkedAccountsBinding
    lateinit var recyclerViewAdapter: RecyclerViewAdapter

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {

        val accountNumber = activityViewModel.account.value?.accountNumber;
        viewModel.init(accountNumber ?: "0")

        bindings = FragmentHomeDrawerPayeeAndLinkedAccountsBinding.inflate(inflater, container, false)
        bindings.lifecycleOwner = this
        bindings.viewModel = viewModel

        val navController = findNavController()
        val mainNavController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)

        val homeFragment = parentFragment?.parentFragment as HomeFragment

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.home_drawer_loan_details_fragment,
                R.id.home_drawer_payee_and_linked_accounts_fragment,
                R.id.home_drawer_payment_and_transfers_fragment,
                R.id.home_drawer_statements_fragment,
                R.id.home_drawer_transactions_fragment
            ),
            homeFragment.binding.drawerLayout)

        setSupportActionBarAndSetupWithNavController(
            toolbar = bindings.appBarLayoutIncluded.toolbar,
            navController = navController,
            appBarConfiguration = appBarConfiguration,
            navigationView = homeFragment.binding.navigationView
        )

        recyclerViewAdapter = RecyclerViewAdapter(layoutInflater, this)
        bindings.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        bindings.recyclerView.adapter = recyclerViewAdapter
            .withLoadStateHeaderAndFooter(
                header = PhotosLoadStateAdapter(layoutInflater, viewLifecycleOwner),
                footer = PhotosLoadStateAdapter(layoutInflater, viewLifecycleOwner)
            )

        return bindings.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch {
            recyclerViewAdapter.loadStateFlow.collectLatest {
                when (it.refresh) {
                    is LoadState.Loading -> bindings.swipeRefreshLayout.isRefreshing = true
                    else -> bindings.swipeRefreshLayout.isRefreshing = false
                }
            }
        }
        bindings.swipeRefreshLayout.setOnRefreshListener {
            val accountNumber = activityViewModel.account.value?.accountNumber;
            viewModel.init(accountNumber ?: "0")
            reload()
        }
        reload()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    fun reload() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.photosFlow.collectLatest {
                println("****** photos from flow ${it}")
                recyclerViewAdapter.submitData(it)
            }
        }
    }
}