package com.tmjee.myathena.ui.loanDetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import com.tmjee.myathena.MainActivityViewModel
import com.tmjee.myathena.R
import com.tmjee.myathena.databinding.FragmentHomeDrawerLoanDetailsBinding
import com.tmjee.myathena.ui.MenuAwareFragment
import com.tmjee.myathena.ui.UIUtil
import com.tmjee.myathena.ui.home.HomeFragment
import com.tmjee.myathena.ui.setSupportActionBarAndSetupWithNavController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeDrawerLoanDetailsFragment: MenuAwareFragment() {

    val viewModel: HomeDrawerLoanDetailsFragmentViewModel by viewModels()
    val activityViewModel: MainActivityViewModel by activityViewModels()
    lateinit var bindings: FragmentHomeDrawerLoanDetailsBinding


    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {
        bindings = FragmentHomeDrawerLoanDetailsBinding.inflate(inflater, container, false)
        bindings.lifecycleOwner = this
        bindings.viewModel = viewModel
        bindings.uiUtil = UIUtil


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
            navController = navController,
            toolbar = bindings.appBarLayoutIncluded.toolbar,
            navigationView = homeFragment.binding.navigationView,
            appBarConfiguration = appBarConfiguration
        )

        return bindings.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.loading.observe(viewLifecycleOwner) {
            bindings.swipRefreshLayout.isRefreshing = it
        }

        bindings.swipRefreshLayout.setOnRefreshListener {
            loadLoanDetails()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        loadLoanDetails()
    }

    fun loadLoanDetails() {
        activityViewModel.account?.value?.apply {
            viewModel.loadLoanDetails(this.accountId)
        }
    }

}