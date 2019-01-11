package kr.co.softcampus.actionbarbasic

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.main_menu, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when(item?.itemId){
            R.id.item1 -> {
                textView.text = "1번 메뉴"
            }
            R.id.item2 -> {
                textView.text = "2번 메뉴"
            }
            R.id.item3 ->{
                textView.text = "3번 메뉴"
            }
        }

        return super.onOptionsItemSelected(item)
    }
}









