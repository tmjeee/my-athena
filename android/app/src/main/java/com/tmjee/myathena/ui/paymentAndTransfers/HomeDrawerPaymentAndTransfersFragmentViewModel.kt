package com.tmjee.myathena.ui.paymentAndTransfers

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.tmjee.myathena.domain.Payment
import com.tmjee.myathena.domain.Payments
import com.tmjee.myathena.repository.PaymentsRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.withContext

class HomeDrawerPaymentAndTransfersFragmentViewModel @ViewModelInject constructor(private val paymentsRepo: PaymentsRepo): ViewModel() {

    lateinit var pager: Pager<Int, Payment>
    lateinit var pagingDataFlow: Flow<PagingData<Payment>>

    fun init(accountId: Int) {
        pager = Pager(
            config = PagingConfig(5)) {
            PaymentsPagingSource(paymentsRepo, accountId)
        }
        // pagingDataFlow = pager.flow.cachedIn(viewModelScope)
        pagingDataFlow = pager.flow
        println("****** viewModel flow ready")
    }

    override fun onCleared() {
        super.onCleared()
    }
}


