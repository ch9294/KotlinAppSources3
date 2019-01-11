package kr.co.softcampus.preferencescreen


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_view.view.*


/**
 * A simple [Fragment] subclass.
 *
 */
class ViewFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        var v1 = inflater.inflate(R.layout.fragment_view, container, false)

        v1.button.setOnClickListener { view ->
            var pref = PreferenceManager.getDefaultSharedPreferences(activity)

            var data1 = pref.getString("data1", null)
            v1.textResult.text = "문자열 : ${data1}\n"

            var data2 = pref.getBoolean("data2", false)
            if(data2 == true){
                v1.textResult.append("체크박스가 체크되어 있습니다\n")
            } else {
                v1.textResult.append("체크박스가 체크되어 있지 않습니다\n")
            }

            var data3 = pref.getBoolean("data3", false)
            if(data3 == true){
                v1.textResult.append("스위치 ON\n")
            } else {
                v1.textResult.append("스위치 OFF\n")
            }

            var data4 = pref.getString("data4", null)
            v1.textResult.append("data4 : ${data4}\n")

            var data5 = pref.getStringSet("data5", null)
            for(str in data5){
                v1.textResult.append("data5 : ${str}\n")
            }
        }

        return v1
    }


}
