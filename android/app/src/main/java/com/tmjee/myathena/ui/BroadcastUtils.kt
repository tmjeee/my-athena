package com.tmjee.myathena.ui

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import androidx.annotation.RequiresApi
import com.tmjee.myathena.MainActivity

const val PERMISSION_PRIVATE = "com.tmjee.myathena.permission.PRIVATE";

@RequiresApi(Build.VERSION_CODES.O)
fun registerBroadcastReceiver(context: Context, broadcastReceiver: BroadcastReceiver) {
    context.registerReceiver(
        broadcastReceiver,
        IntentFilter(BROADCAST_INTENT_ACTION),
        PERMISSION_PRIVATE,
        null
    )
}

const val BROADCAST_INTENT_ACTION = "tmjeee.myathena.broadcast.intent.action"
fun sendBroadcast(context: Context) {
    val intent = Intent(BROADCAST_INTENT_ACTION).apply {
        putExtra("k1", "v1")
    }
    context.sendOrderedBroadcast(intent, PERMISSION_PRIVATE)
    println("******* broadcast sent")
}
