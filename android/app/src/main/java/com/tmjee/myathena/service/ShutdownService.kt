package com.tmjee.myathena.service

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import com.tmjee.myathena.MainApplication
import com.tmjee.myathena.repository.PaymentsRepo
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ShutdownService: Service() {

    @Inject()
    lateinit var paymentsRepo: PaymentsRepo   // field injection only if requred

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        println("******************* ShutdownService onStartCommand ${paymentsRepo}")
        return Service.START_STICKY;
    }

    override fun onDestroy() {
        super.onDestroy()
        println("****************** ShutdownService onDestroy")
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        super.onTaskRemoved(rootIntent)
        println("*************************** ShutdownService onTaskRemoved")
        (application as MainApplication).cleanup()
    }


    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, ShutdownService::class.java)
        }
    }

}