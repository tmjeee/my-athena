package com.tmjee.myathena.ui.paymentAndTransfers

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.LifecycleOwner
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.paging.PagingDataAdapter
import androidx.paging.PagingSource
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.tmjee.myathena.databinding.RecyclerViewPaymentBinding
import com.tmjee.myathena.databinding.RecyclerViewPaymentLoadingStateBinding
import com.tmjee.myathena.domain.Payment
import com.tmjee.myathena.domain.Payments
import com.tmjee.myathena.repository.PaymentsRepo
import com.tmjee.myathena.ui.UIUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DiffCallback: DiffUtil.ItemCallback<Payment>() {
    override fun areItemsTheSame(oldItem: Payment, newItem: Payment): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: Payment, newItem: Payment): Boolean {
        return oldItem == newItem
    }
}

class PaymentViewHolder(private val binding: RecyclerViewPaymentBinding): RecyclerView.ViewHolder(binding.root) {

    fun bind(payment: Payment?) {
        binding.payment = payment
    }
}

class Adapter(private val layoutInflater: LayoutInflater, private val lifecycleOwner: LifecycleOwner): PagingDataAdapter<Payment, PaymentViewHolder>(DiffCallback()) {

    override fun onBindViewHolder(holder: PaymentViewHolder, position: Int) {
        val payment: Payment? = getItem(position)
        holder.bind(payment)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaymentViewHolder {
        val binding = RecyclerViewPaymentBinding.inflate(layoutInflater, parent, false)
        binding.uiUtil = UIUtil
        binding.lifecycleOwner = lifecycleOwner
        return PaymentViewHolder(binding)
    }
}

class LoadStateViewHolder(private val binding: RecyclerViewPaymentLoadingStateBinding): RecyclerView.ViewHolder(binding.root) {

    fun bind(loadState: LoadState) {
        binding.buttonRetry.isVisible = loadState is LoadState.Error
        binding.labelError.isVisible = loadState is LoadState.Error
        binding.labelLoading.isVisible = loadState is LoadState.Loading
    }
}

class StateAdapter(private val layoutInflater: LayoutInflater, private val lifecycleOwner: LifecycleOwner): LoadStateAdapter<LoadStateViewHolder>() {
    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        val bindings = RecyclerViewPaymentLoadingStateBinding.inflate(layoutInflater, parent, false)
        bindings.lifecycleOwner = lifecycleOwner
        return LoadStateViewHolder(bindings)
    }

}


class PaymentsPagingSource (private val paymentsRepo: PaymentsRepo, private val accountId: Int): PagingSource<Int, Payment>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Payment> {
        val page = params.key ?: 1

        try {
            val response: Response<Payments> =
                withContext(Dispatchers.IO) {
                    paymentsRepo.getPayments(accountId, 5 /* limit */,  (page -1) * 5 /* offset */)
                        .execute()
                }

            val data = response.body()?.payments ?: emptyList()
            return LoadResult.Page(
                data = data,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (data.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            e.printStackTrace()
           return LoadResult.Error(e)
        }
    }
}
