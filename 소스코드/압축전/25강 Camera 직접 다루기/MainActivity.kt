package kr.co.softcampus.cameraapi1

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.hardware.Camera
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.view.Surface
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File
import java.io.FileOutputStream

class MainActivity : AppCompatActivity() {

    var permission_list = arrayOf(
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    var camera:Camera? = null
    var dirPath:String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            requestPermissions(permission_list, 0)
        } else {
            init()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        for(result in grantResults){
            if(result == PackageManager.PERMISSION_DENIED){
                return
            }
        }
        init()
    }

    fun init(){
        var tempPath = Environment.getExternalStorageDirectory().absolutePath
        dirPath = "${tempPath}/Android/data/${packageName}"

        var file = File(dirPath)
        if(file.exists() == false){
            file.mkdir()
        }

        camera = Camera.open()

        var degree = 0

        when(windowManager.defaultDisplay.rotation){
            Surface.ROTATION_0 -> {
                degree = 90
            }
            Surface.ROTATION_90 ->{
                degree = 0
            }
            Surface.ROTATION_180 ->{
                degree = 270
            }
            Surface.ROTATION_270 ->{
                degree = 180
            }
        }
        camera?.setDisplayOrientation(degree)
        camera?.setPreviewDisplay(surfaceView.holder)
        camera?.startPreview()

        button.setOnClickListener { view ->
            var callback1 = Callback1()
            camera?.takePicture(null, null, callback1)
        }
    }

    inner class Callback1 : Camera.PictureCallback{
        override fun onPictureTaken(data: ByteArray?, camera: Camera?) {

            var bitmap = BitmapFactory.decodeByteArray(data, 0, data?.size!!)

            var filePath = "${dirPath}/temp_${System.currentTimeMillis()}.jpg"
            var file = File(filePath)
            var fos = FileOutputStream(file)

            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)
            fos.flush()
            fos.close()
        }
    }
}









