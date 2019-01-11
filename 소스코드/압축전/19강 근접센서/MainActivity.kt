package com.example.softcampus.proximity

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.service.carrier.CarrierMessagingService
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var listener : SensorListener? = null
    var manager : SensorManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        manager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        listener = SensorListener()

        button.setOnClickListener { view ->
            var sensor = manager?.getDefaultSensor(Sensor.TYPE_PROXIMITY)
            var chk = manager?.registerListener(listener, sensor, SensorManager.SENSOR_DELAY_UI)
            if(chk == false){
                textView.text = "근접 센서를 지원하지 않습니다"
            }
        }

        button2.setOnClickListener{ view ->
            manager?.unregisterListener(listener)
        }
    }

    inner class SensorListener : SensorEventListener{
        override fun onSensorChanged(event: SensorEvent?) {
            if(event?.sensor?.type == Sensor.TYPE_PROXIMITY){
                textView.text = "물체와의 거리 : ${event?.values[0]} cm"
            }
        }

        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

        }
    }
}
