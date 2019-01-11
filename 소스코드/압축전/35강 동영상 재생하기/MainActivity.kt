package kr.co.softcampus.videostreamming

import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.MediaController
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener { view ->
            var controller = MediaController(this)
            controller.setAnchorView(videoView)

            var uri = Uri.parse("http://192.168.1.103:8080/MediaServer/media/video.mp4")
            videoView.setMediaController(controller)
            videoView.setVideoURI(uri)
            videoView.requestFocus()
            videoView.start()
        }
    }
}
