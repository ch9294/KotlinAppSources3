package kr.co.softcampus.toolbar

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 액션바 대신 툴바를 사용하도록 설정한다.
        setSupportActionBar(toolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.main_menu, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            R.id.item1 ->{
                textView.text = "메뉴 1을 눌렀습니다"
            }
            R.id.item2 -> {
                textView.text = "메뉴 2를 눌렀습니다"
            }
            R.id.item3 -> {
                textView.text = "메뉴 3을 눌렀습니다"
            }
        }
        return super.onOptionsItemSelected(item)
    }
}










