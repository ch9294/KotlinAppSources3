package kr.co.softcampus.gallary

import android.Manifest
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.ExifInterface
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.math.E

class MainActivity : AppCompatActivity() {

    var permission_list = arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            requestPermissions(permission_list, 0)
        }

        button.setOnClickListener { view ->
            var intent = Intent(Intent.ACTION_PICK)
            intent.type = android.provider.MediaStore.Images.Media.CONTENT_TYPE
            startActivityForResult(intent, 1)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode == RESULT_OK){

            var c = contentResolver.query(data?.data, null, null, null, null)
            c.moveToNext()

            var index = c.getColumnIndex(MediaStore.Images.Media.DATA)
            var source = c.getString(index)

            var bitmap = BitmapFactory.decodeFile(source)

            bitmap = resizeBitmap(1024, bitmap)

            imageView.setImageBitmap(bitmap)

            var degree = getDegree(source)
            imageView.rotation = degree
        }
    }

    fun resizeBitmap(targetWidth : Int, source:Bitmap) : Bitmap {

        var ratio = source.height.toDouble() / source.width.toDouble()
        var targetHeight = (targetWidth * ratio).toInt()
        var result = Bitmap.createScaledBitmap(source, targetWidth, targetHeight, false)
        if(result != source){
            source.recycle()
        }
        return result
    }

    fun getDegree(source:String) : Float {
        var exif = ExifInterface(source)

        var degree = 0

        var ori = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, -1)
        when(ori){
            ExifInterface.ORIENTATION_ROTATE_90 ->{
                degree = 90
            }
            ExifInterface.ORIENTATION_ROTATE_180 ->{
                degree = 180
            }
            ExifInterface.ORIENTATION_ROTATE_270 ->{
                degree = 270
            }
        }

        return degree.toFloat()
    }
}










