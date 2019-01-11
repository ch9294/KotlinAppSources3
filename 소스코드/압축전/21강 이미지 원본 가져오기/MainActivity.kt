package kr.co.softcampus.cameraoriginal

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.support.v4.content.FileProvider
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File

class MainActivity : AppCompatActivity() {

    var permission_list = arrayOf(
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    );

    var dirPath:String? = null
    var contentUri: Uri? = null

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
        for(a1 in grantResults){
            if(a1 == PackageManager.PERMISSION_DENIED){
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

        button.setOnClickListener { view ->
            var intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

            var fileName = "temp_${System.currentTimeMillis()}.jpg"
            var picPath = "${dirPath}/${fileName}"


            var file = File(picPath)

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
                contentUri = FileProvider.getUriForFile(this, "kr.co.softcampus.cameraoriginal.file_provider", file)
            } else {
                contentUri = Uri.fromFile(file)
            }

            intent.putExtra(MediaStore.EXTRA_OUTPUT, contentUri)
            startActivityForResult(intent, 1)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        var bitmap = BitmapFactory.decodeFile(contentUri?.path)
        imageView.setImageBitmap(bitmap)
    }
}







