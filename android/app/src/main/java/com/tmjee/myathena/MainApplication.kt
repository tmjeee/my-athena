package com.tmjee.myathena

import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import androidx.multidex.MultiDexApplication
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MainApplication: MultiDexApplication(), ViewModelStoreOwner {

    fun cleanup() {
        println("****************** Main Application cleanup")
        viewModelStore.clear()
    }

    override fun getViewModelStore(): ViewModelStore {
        return ViewModelStore()
    }
}