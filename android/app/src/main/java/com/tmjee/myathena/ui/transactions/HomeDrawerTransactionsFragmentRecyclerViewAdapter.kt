package com.tmjee.myathena.ui.transactions

import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tmjee.myathena.databinding.FragmentHomeDrawerTransactionsBinding
import com.tmjee.myathena.databinding.RecyclerViewTransactionItemBinding
import com.tmjee.myathena.databinding.RecyclerViewTransactionItemEntryBinding
import com.tmjee.myathena.domain.Transaction
import com.tmjee.myathena.domain.TransactionEntry
import com.tmjee.myathena.ui.UIUtil

object DiffItemCallback: DiffUtil.ItemCallback<Transaction>() {
    override fun areItemsTheSame(oldItem: Transaction, newItem: Transaction): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: Transaction, newItem: Transaction): Boolean {
        return oldItem == newItem
    }
}

class ViewHolder constructor(context: Context, layoutInflater: LayoutInflater, private val binding: RecyclerViewTransactionItemBinding): RecyclerView.ViewHolder(binding.root) {

    init {
       binding.recyclerView.layoutManager = LinearLayoutManager(context)
       binding.recyclerView.adapter = InnerAdapter(layoutInflater)
       binding.recyclerView.isNestedScrollingEnabled = false
    }

    fun bind(transaction: Transaction) {
        binding.transaction = transaction
        (binding.recyclerView.adapter as InnerAdapter).submitList(transaction.entries)
        binding.executePendingBindings()
    }
}


class Adapter constructor(private val context: Context, private val layoutInflator: LayoutInflater): ListAdapter<Transaction, ViewHolder>(DiffItemCallback) {

    lateinit var binding: RecyclerViewTransactionItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = RecyclerViewTransactionItemBinding.inflate(layoutInflator, parent, false)
        binding.uiUtil = UIUtil
        return ViewHolder(context, layoutInflator, binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val transaction: Transaction = getItem(position);
        holder.bind(transaction)
    }
}


/////////////////////// inner adapter

object InnerDiffItemCallback: DiffUtil.ItemCallback<TransactionEntry>() {
    override fun areItemsTheSame(oldItem: TransactionEntry, newItem: TransactionEntry): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: TransactionEntry, newItem: TransactionEntry): Boolean {
        return oldItem == newItem
    }
}

class InnerViewHolder constructor(private val bindings: RecyclerViewTransactionItemEntryBinding): RecyclerView.ViewHolder(bindings.root) {

    fun bind(entry: TransactionEntry) {
        bindings.entry = entry
        bindings.executePendingBindings()
    }
}

class InnerAdapter constructor(private val layoutInflater: LayoutInflater): ListAdapter<TransactionEntry, InnerViewHolder>(InnerDiffItemCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InnerViewHolder {
        val binding = RecyclerViewTransactionItemEntryBinding.inflate(layoutInflater, parent, false)
        binding.uiUtil = UIUtil
        return InnerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: InnerViewHolder, position: Int) {
        val entry: TransactionEntry = getItem(position)
        holder.bind(entry)
    }
}