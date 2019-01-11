package kr.co.softcampus.fcmtest

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.FirebaseInstanceIdService

class MyFCMIdService : FirebaseInstanceIdService() {

    override fun onTokenRefresh() {
        var token = FirebaseInstanceId.getInstance().token
        Log.d("test123", "token : ${token}")
        var thread = HttpClass(token!!)
        thread.start()
    }
}
