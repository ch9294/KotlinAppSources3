package kr.co.softcampus.appbarlayout

import android.content.DialogInterface
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.Gravity
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.input.*

class MainActivity : AppCompatActivity() {

    var data_list = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.hide()

        toolbar.title = "AppbarLayout"

//        toolbar_layout.setCollapsedTitleTextColor(Color.CYAN)
//        toolbar_layout.setExpandedTitleColor(Color.WHITE)
//        toolbar_layout.collapsedTitleGravity = Gravity.CENTER_HORIZONTAL
//        toolbar_layout.expandedTitleGravity = Gravity.RIGHT + Gravity.TOP

        toolbar_layout.setCollapsedTitleTextColor(Color.BLACK)
        toolbar_layout.setExpandedTitleColor(Color.BLACK)

        app_bar_image.setImageResource(R.drawable.img_android)

        list1.adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, data_list)

        btn1.setOnClickListener{ view ->
            var builder =  AlertDialog.Builder(this)
            builder.setTitle("문자열 입력")

            var v1 = layoutInflater.inflate(R.layout.input, null)
            builder.setView(v1)

            builder.setNegativeButton("취소", null)
            builder.setPositiveButton("확인", DialogListener())
            builder.show()
        }
    }

    inner class DialogListener : DialogInterface.OnClickListener{
        override fun onClick(dialog: DialogInterface?, which: Int) {
            var alert = dialog as AlertDialog
            var str1 = alert.editText.text.toString()

            data_list.add(str1)

            var adapter = list1.adapter as ArrayAdapter<String>
            adapter.notifyDataSetChanged()
        }
    }
}
