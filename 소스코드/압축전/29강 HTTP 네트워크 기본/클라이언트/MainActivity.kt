package kr.co.softcampus.parameter

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.*
import org.json.JSONObject
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
            var url = builder.url("http://192.168.1.101:8080/WebServer/basic.jsp")

            var bodyBuilder = FormBody.Builder()

            bodyBuilder.add("data1", "문자열1")
            bodyBuilder.add("data2", "문자열2")
            bodyBuilder.add("data3", "문자열3")
            bodyBuilder.add("data3", "문자열4")

            var body = bodyBuilder.build()

            var post = url.post(body)

            var request = post.build()

            //client.newCall(request).execute()
            client.newCall(request).enqueue(Callback1())
        }
    }

    inner class Callback1 : Callback{
        override fun onFailure(call: Call?, e: IOException?) {

        }

        override fun onResponse(call: Call?, response: Response?) {
            var result = response?.body()?.string()

            runOnUiThread {
                var obj = JSONObject(result)

                var value1 = obj.getInt("value1")
                var value2 = obj.getDouble("value2")
                var value3 = obj.getString("value3")

                textView.text = "value1 : ${value1}\n"
                textView.append("value2 : ${value2}\n")
                textView.append("value3 : ${value3}\n")
            }
        }
    }
}








