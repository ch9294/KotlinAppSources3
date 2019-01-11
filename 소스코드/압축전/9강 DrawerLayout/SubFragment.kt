package kr.co.softcampus.drawerlayout


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_sub.view.*


/**
 * A simple [Fragment] subclass.
 *
 */
class SubFragment : Fragment() {

    var str1:String? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        var v1 = inflater.inflate(R.layout.fragment_sub, container, false)
        v1.textView.text = str1

        return v1
    }


}
