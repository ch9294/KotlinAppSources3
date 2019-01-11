package kr.co.softcampus.fcmtest

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.iid.FirebaseInstanceId

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var token = FirebaseInstanceId.getInstance().token
        if(token != null){
            Log.d("test123", "token : ${token}")
            var thread = HttpClass(token)
            thread.start()
        }
    }
}
