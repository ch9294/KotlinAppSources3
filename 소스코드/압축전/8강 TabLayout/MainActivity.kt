package kr.co.softcampus.tablayout

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var frag_list = ArrayList<SubFragment>()
    var title_list = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.hide()

        tabs.setTabTextColors(Color.WHITE, Color.RED)

        for(i in 0..9){
            var sub = SubFragment()
            sub.str1 = "Sub ${i + 1}"

            frag_list.add(sub)
            title_list.add("tab ${i + 1}")
        }

        pager.adapter = PagerAdapter(supportFragmentManager)
        tabs.setupWithViewPager(pager)
    }

    inner class PagerAdapter(fm : FragmentManager) : FragmentPagerAdapter(fm){

        override fun getCount(): Int {
            return frag_list.size
        }

        override fun getItem(position: Int): Fragment {
            return frag_list.get(position)
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return title_list.get(position)
        }
    }
}







