package kr.co.softcampus.httpmultipart

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var listData =  ArrayList<HashMap<String, Any>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var adapter = ListAdapter()
        main_list.adapter = adapter

        init()
    }

    fun init(){
        var map1 = HashMap<String, Any>()
        var map2 = HashMap<String, Any>()
        var map3 = HashMap<String, Any>()

        map1.put("mobile_img", android.R.drawable.ic_menu_add)
        map1.put("mobile_str1", "항목1")

        map2.put("mobile_img", android.R.drawable.ic_menu_agenda)
        map2.put("mobile_str1", "항목2")

        map3.put("mobile_img", android.R.drawable.ic_menu_camera)
        map3.put("mobile_str1", "항목3")

        listData.add(map1)
        listData.add(map2)
        listData.add(map3)

        var adapter = main_list.adapter as ListAdapter
        adapter.notifyDataSetChanged()

        main_list.setOnItemClickListener { adapterView, view, i, l ->
            var detail_intent = Intent(this, DetailActivity::class.java)
            startActivity(detail_intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            R.id.menu_write -> {
                var write_intent = Intent(this, WriteActivity::class.java)
                startActivity(write_intent)
            }
            R.id.menu_reload ->{

            }
        }
        return super.onOptionsItemSelected(item)
    }

    inner class ListAdapter:BaseAdapter(){
        override fun getCount(): Int {
            return listData.size
        }

        override fun getItem(p0: Int): Any {
            return 0
        }

        override fun getItemId(p0: Int): Long {
            return 0
        }

        override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
            var convertView = p1

            if(convertView == null){
                convertView = layoutInflater.inflate(R.layout.row, null)
            }

            var img1 = convertView?.findViewById<ImageView>(R.id.imageView)
            var str1 = convertView?.findViewById<TextView>(R.id.textView)

            var map = listData.get(p0)

            var mobile_img = map.get("mobile_img") as Int
            var mobile_str1 = map.get("mobile_str1") as String

            img1?.setImageResource(mobile_img)
            str1?.text = mobile_str1

            return convertView!!
        }
    }
}
