package kr.co.softcampus.camerabasic

import android.content.Intent
import android.graphics.Bitmap
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.coroutines.experimental.buildIterator

class MainActivity : AppCompatActivity() {

    val CAMERA_ACTIVITY = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener { view ->
            var intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(intent, CAMERA_ACTIVITY)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == CAMERA_ACTIVITY){
            if(resultCode == RESULT_OK){
                var bitmap = data?.getParcelableExtra<Bitmap>("data")
                imageView.setImageBitmap(bitmap)
            }
        }
    }

}
