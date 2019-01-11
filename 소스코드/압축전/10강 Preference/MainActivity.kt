package kr.co.softcampus.preferences

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener { view ->
            var pref = getSharedPreferences("data", Context.MODE_PRIVATE)
            var editor = pref.edit()

            editor.putBoolean("data1", true)
            editor.putFloat("data2", 11.11f)
            editor.putInt("data3", 100)
            editor.putLong("data4", 10000L)
            editor.putString("data5", "문자열 데이터")

            var set = HashSet<String>()
            set.add("문자열1")
            set.add("문자열2")
            set.add("문자열3")
            editor.putStringSet("data6", set)

            editor.commit()

            textView.text = "저장완료"
        }

        button2.setOnClickListener { view ->
            var pref = getSharedPreferences("data", Context.MODE_PRIVATE)

            var data1 = pref.getBoolean("data1", false)
            var data2 = pref.getFloat("data2", 0.0f)
            var data3 = pref.getInt("data3", 0)
            var data4 = pref.getLong("data4", 0L)
            var data5 = pref.getString("data5", "초기값")
            var data6 = pref.getStringSet("data6", null)

            textView.text = "data1 : ${data1}\n"
            textView.append("data2 : ${data2}\n")
            textView.append("data3 : ${data3}\n")
            textView.append("data4 : ${data4}\n")
            textView.append("data5 : ${data5}\n")

            for(str in data6){
                textView.append("data6 : ${str}\n")
            }
        }
    }
}












