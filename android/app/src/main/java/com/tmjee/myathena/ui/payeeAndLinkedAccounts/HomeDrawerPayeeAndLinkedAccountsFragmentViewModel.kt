package com.tmjee.myathena.ui.payeeAndLinkedAccounts

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.tmjee.myathena.domain.Photos
import com.tmjee.myathena.repository.PhotosRepo
import kotlinx.coroutines.flow.Flow


class HomeDrawerPayeeAndLinkedAccountsFragmentViewModel @ViewModelInject constructor(
    private val photosRepo: PhotosRepo
): ViewModel() {

    lateinit var photosFlow: Flow<PagingData<Photos>>

    fun init(accountNumber: String) {
        photosFlow = Pager<Int, Photos>(
            config = PagingConfig(
                pageSize = 5
            ),
        ) {
            PhotosPagingSource(photosRepo, accountNumber)
        }.flow
    }

    override fun onCleared() {
        super.onCleared()
    }


}