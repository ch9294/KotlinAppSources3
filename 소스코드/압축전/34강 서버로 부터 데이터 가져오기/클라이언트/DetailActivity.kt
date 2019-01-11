package kr.co.softcampus.httpmultipart

import android.graphics.BitmapFactory
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.*
import org.json.JSONObject
import java.io.FileNotFoundException
import java.io.IOException
import java.net.URL

class DetailActivity : AppCompatActivity() {

    var mobile_idx:Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        mobile_idx = intent.getIntExtra("mobile_idx", 0)

        var thread = getDataThread()
        thread.start()
    }

    inner class getDataThread : Thread(){
        override fun run() {
            var client = OkHttpClient()
            var builder = Request.Builder()
            var url = builder.url("http://192.168.1.101:8080/MobileServer/get_data.jsp")

            var bodyBuilder = FormBody.Builder()
            bodyBuilder.add("mobile_idx", "${mobile_idx}")

            var body = bodyBuilder.build()

            var post = url.post(body)

            var request = post.build()

            var callback = Callback1()
            client.newCall(request).enqueue(callback)
        }
    }

    inner class Callback1 : Callback {
        override fun onFailure(call: Call?, e: IOException?) {

        }

        override fun onResponse(call: Call?, response: Response?) {
            var result = response?.body()?.string()

            var obj = JSONObject(result)

            var mobile_img = obj.getString("mobile_img")
            var mobile_str1 = obj.getString("mobile_str1")
            var mobile_str2 = obj.getString("mobile_str2");

            var image_thread = ImageNetworkThread(mobile_img)
            image_thread.start()

            runOnUiThread {
                textView2.text = mobile_str1
                textView3.text = mobile_str2
            }
        }
    }

    inner class ImageNetworkThread(var fileName : String) : Thread(){
        override fun run() {
            var url = URL("http://192.168.1.101:8080/MobileServer/upload/${fileName}")

            var connection = url.openConnection()
            var stream = connection.getInputStream()
            var bitmap = BitmapFactory.decodeStream(stream)


            runOnUiThread {
                imageView3.setImageBitmap(bitmap)
            }
        }
    }
}









