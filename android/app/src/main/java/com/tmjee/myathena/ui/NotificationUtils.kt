package com.tmjee.myathena.ui

import android.app.NotificationChannel
import android.app.NotificationChannelGroup
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.tmjee.myathena.MainActivity
import com.tmjee.myathena.R

const val CHANNEL_GROUP_ID_1 = "com.tmjee.notification.channel.group.1"
const val CHANNEL_ID_1 = "com.tmjeee.notification.channel.id1"
const val CHANNEL_ID_2 = "com.tmjeee.notification.channel.id2"

fun setupNotificationChannel(context: Context) {
    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
        // groups
        val group1 = NotificationChannelGroup(CHANNEL_GROUP_ID_1, "My Athena Notification Group #1")

        // channels
        val notificationChannel1 = NotificationChannel(CHANNEL_ID_1, "My Athena Channel #1", NotificationManager.IMPORTANCE_DEFAULT).apply {
            description = "My Athena channel #1 descrption, something interesting"
            group = group1.id
        }
        val notificationChannel2 = NotificationChannel(CHANNEL_ID_2, "My Athena Channel #2", NotificationManager.IMPORTANCE_DEFAULT).apply {
            description = "My Athena channel #2 descrption, something interesting"
            group = group1.id
        }

        // register groups and channels
        val notificationManager = context.getSystemService(NotificationManager::class.java)
        notificationManager.createNotificationChannelGroup(group1)
        notificationManager.createNotificationChannel(notificationChannel1)
        notificationManager.createNotificationChannel(notificationChannel2)
    }
}

const val NOTIFICATION_ID = 1
const val REQ_CODE = 1
fun sendNotification(context: Context) {
    val intent = Intent(context, MainActivity::class.java)
    val pendingIntent = PendingIntent.getActivity(context, REQ_CODE, intent, 0)

    val randomNumber = System.currentTimeMillis()
    val notification = NotificationCompat.Builder(context, CHANNEL_ID_1)
        .setTicker("Ticker")
        .setSmallIcon(R.drawable.ic_launcher_foreground)
        .setContentTitle("Content title ${randomNumber}")
        .setContentText("Content text ${randomNumber}")
        .setContentIntent(pendingIntent)
        .setAutoCancel(true)
        // .addAction(object: Action() {
        .build()
    NotificationManagerCompat.from(context).notify(NOTIFICATION_ID, notification)

}
