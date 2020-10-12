package com.tmjee.myathena.ui.paymentAndTransfers

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
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import com.tmjee.myathena.MainActivityViewModel
import com.tmjee.myathena.R
import com.tmjee.myathena.databinding.FragmentHomeDrawerPaymentAndTransfersBinding
import com.tmjee.myathena.ui.MenuAwareFragment
import com.tmjee.myathena.ui.home.HomeFragment
import com.tmjee.myathena.ui.setSupportActionBarAndSetupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.collectLatest
import okhttp3.internal.applyConnectionSpec

@AndroidEntryPoint
class HomeDrawerPaymentAndTransfersFragment: MenuAwareFragment() {

    val viewModel: HomeDrawerPaymentAndTransfersFragmentViewModel by viewModels()
    val activityViewModel: MainActivityViewModel by activityViewModels()
    lateinit var bindings: FragmentHomeDrawerPaymentAndTransfersBinding
    lateinit var adapter: Adapter

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {
        val accountId = activityViewModel.account.value?.accountId ?: 0
        viewModel.init(accountId)

        bindings = FragmentHomeDrawerPaymentAndTransfersBinding.inflate(inflater, container, false)
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
            homeFragment.drawer_layout)

        setSupportActionBarAndSetupWithNavController(
            toolbar = bindings.appBarLayoutIncluded.toolbar,
            navController = navController,
            appBarConfiguration = appBarConfiguration,
            navigationView = homeFragment.binding.navigationView
        )

        adapter = Adapter(layoutInflater, viewLifecycleOwner)
        bindings.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        bindings.recyclerView.adapter = adapter.withLoadStateHeaderAndFooter(
            header = StateAdapter(layoutInflater, viewLifecycleOwner),
            footer = StateAdapter(layoutInflater, viewLifecycleOwner),
        )
        return bindings.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch(CoroutineName("loadStateCoroutine")) {
            adapter.loadStateFlow.collectLatest {
                val isLoading = it.refresh is LoadState.Loading
                bindings.swipeRefreshLayout.isRefreshing = isLoading
            }
        }

        bindings.swipeRefreshLayout.setOnRefreshListener {
            val accountId = activityViewModel.account.value?.accountId ?: 0
            viewModel.init(accountId)
            reload()
        }
        reload()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }


    fun reload() {
         viewLifecycleOwner.lifecycleScope.launch() {
            viewModel.pagingDataFlow.collectLatest {
                adapter.submitData(it)
            }
        }
    }
}