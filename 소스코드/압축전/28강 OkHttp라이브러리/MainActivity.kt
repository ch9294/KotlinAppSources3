package kr.co.softcampus.okhttpnetwork

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.*
import java.io.IOException

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener { view ->
            var thread = NetworkThread()
            thread.start()
        }
    }

    inner class NetworkThread : Thread(){
        override fun run() {

            var client = OkHttpClient()

            var builder = Request.Builder()
            var url = builder.url("http://google.com")
            var request = url.build()

            var callback = Callback1()

            client.newCall(request).enqueue(callback)
        }
    }

    inner class Callback1 : Callback {
        override fun onFailure(call: Call?, e: IOException?) {

        }

        override fun onResponse(call: Call?, response: Response?) {

            var result = response?.body()?.string()

            runOnUiThread {
                textView.text = result
            }
        }
    }
}









