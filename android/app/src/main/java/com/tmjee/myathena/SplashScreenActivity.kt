package com.tmjee.myathena

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.tmjee.myathena.service.ShutdownService

class SplashScreenActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // sample #1: using handler.post to perform "fire and forget"
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 2000)



        // sample #2: using handler.sendXxx to perform "fire and process response", need to destroy HandlerThread
        val handler = Handler(HandlerThread("HandlerThread").run {
            start()
            looper
        }, Handler.Callback { message ->
            when(message.what) {
               1 -> println("********** received handler message 1")
               else -> println("************** received handler message other than 1")
            }
            true
        })
        handler.obtainMessage(1).sendToTarget()
        handler.obtainMessage(2).sendToTarget()


        startService(ShutdownService.newIntent(applicationContext))
    }
}

