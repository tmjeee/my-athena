package com.tmjee.myathena.ui.hub

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tmjee.myathena.databinding.RecyclerViewHubEntryBinding
import com.tmjee.myathena.domain.Account
import com.tmjee.myathena.ui.UIUtil

object diffUtilItemCallback: DiffUtil.ItemCallback<Account>() {
    override fun areItemsTheSame(oldItem: Account, newItem: Account): Boolean {
        return oldItem.accountId == newItem.accountId
    }

    override fun areContentsTheSame(oldItem: Account, newItem: Account): Boolean {
        return oldItem == newItem
    }
}

abstract class ViewHolder constructor(val binding: RecyclerViewHubEntryBinding): RecyclerView.ViewHolder(binding.root) {
    lateinit var account: Account
    init {
        itemView.setOnClickListener(this::onClick)
        binding.uiUtil = UIUtil
    }

    fun bind(account: Account) {
        this.account = account
        binding.account = account
    }

    fun onClick(v: View) {
        onItemViewClicked(account)
    }

    abstract fun onItemViewClicked(account: Account)
}

abstract class Adapter constructor(val layoutInflator: LayoutInflater): ListAdapter<Account, ViewHolder>(diffUtilItemCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RecyclerViewHubEntryBinding.inflate(layoutInflator, parent, false)
        return object:ViewHolder(binding) {
            override fun onItemViewClicked(account: Account) {
                this@Adapter.onItemViewClicked(account)
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val account = getItem(position)
        holder.bind(account)
    }

    abstract fun onItemViewClicked(account: Account)
}

