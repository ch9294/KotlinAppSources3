package kr.co.softcampus.drawerlayout

import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.nav_header_main.view.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    var sub1 = SubFragment()
    var sub2 = SubFragment()
    var sub3 = SubFragment()
    var sub4 = SubFragment()
    var sub5 = SubFragment()
    var sub6 = SubFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        var header_view = nav_view.getHeaderView(0)
        header_view.setBackgroundResource(R.drawable.header_background)
        header_view.header_img1.setImageResource(R.drawable.header_image)
        header_view.header_text1.setTextColor(Color.BLACK)
        header_view.header_text1.text = "header text 1"
        header_view.header_text2.setTextColor(Color.BLACK)
        header_view.header_text2.text = "header text 2"


        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.

        var tran = supportFragmentManager.beginTransaction()

        when(item.itemId){
            R.id.menu1_1 ->{
                sub1.str1 = "sub1 fragment"
                tran.replace(R.id.frag_container, sub1)
            }
            R.id.menu1_2 ->{
                sub2.str1 = "sub2 fragment"
                tran.replace(R.id.frag_container, sub2)
            }
            R.id.menu1_3 ->{
                sub3.str1 = "sub3 fragment"
                tran.replace(R.id.frag_container, sub3)
            }
            R.id.menu2_1 ->{
                sub4.str1 = "sub4 fragment"
                tran.replace(R.id.frag_container, sub4)
            }
            R.id.menu2_2 ->{
                sub5.str1 = "sub5 fragment"
                tran.replace(R.id.frag_container, sub5)
            }
            R.id.menu2_3 ->{
                sub6.str1 = "sub6 fragment"
                tran.replace(R.id.frag_container, sub6)
            }
        }

        tran.commit()

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}
