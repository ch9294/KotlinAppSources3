package kr.co.softcampus.fcmtest

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import android.os.IBinder
import android.support.v4.app.NotificationCompat
import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import java.net.URLDecoder

class MyFCMMessingService : FirebaseMessagingService() {

    override fun onMessageReceived(p0: RemoteMessage?) {

        Log.d("test123", "message : ${p0?.data}")

        var builder = getNotificationBuilder("channel1", "첫 번째 채널")
        builder.setTicker("fcm")
        builder.setSmallIcon(R.mipmap.ic_launcher)
        var bitmap = BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher)
        builder.setLargeIcon(bitmap)
        builder.setNumber(100)
        builder.setAutoCancel(true)
        builder.setContentTitle("Firebase 메시지")

        var msg:String? = p0?.data?.get("msg")
        msg = URLDecoder.decode(msg, "EUC-KR")

        builder.setContentText("${msg}")

        var notification = builder.build()

        var mng = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        mng.notify(10, notification)
    }

    fun getNotificationBuilder(id:String, name:String) : NotificationCompat.Builder{
        var builder:NotificationCompat.Builder? = null

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            var manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            var channel = NotificationChannel(id, name, NotificationManager.IMPORTANCE_HIGH)
            channel.enableLights(true)
            channel.lightColor = Color.RED
            channel.enableVibration(true)
            manager.createNotificationChannel(channel)

            builder = NotificationCompat.Builder(this, id)
        } else {
            builder = NotificationCompat.Builder(this)
        }

        return builder
    }
}









