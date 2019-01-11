package kr.co.softcampus.sensorlist

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var manager = getSystemService(Context.SENSOR_SERVICE) as SensorManager

        var list = manager.getSensorList(Sensor.TYPE_ALL)

        textView.text = ""

        for(sensor in list){
            textView.append("센서 이름 : ${sensor.name}\n")
            textView.append("센서 종류 : ${sensor.type}\n\n")
        }

    }
}
