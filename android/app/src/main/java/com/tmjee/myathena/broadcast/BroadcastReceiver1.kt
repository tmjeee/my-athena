package com.tmjee.myathena.broadcast

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG

class BroadcastReceiver1: BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {

        println("******* broadcast received")
        Toast
            .makeText(context,
                "Broadcast receiver #1 received broadcast intent ${intent}", LENGTH_LONG)
            .show()
        println("******* broadcast received step 2")


        setResult(Activity.RESULT_OK, "data",
            Bundle().apply {
                putString("K1", "K1V")
            })
    }

}