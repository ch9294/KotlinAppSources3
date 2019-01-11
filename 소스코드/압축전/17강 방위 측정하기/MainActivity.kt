package com.example.softcampus.compass

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var listener : SensorListener? = null
    var manager : SensorManager? = null

    var accValue : FloatArray? = null
    var magValue : FloatArray? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        manager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        listener = SensorListener()

        button.setOnClickListener { view ->
            var sensor1 = manager?.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
            var sensor2 = manager?.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)

            manager?.registerListener(listener, sensor1, SensorManager.SENSOR_DELAY_UI)
            manager?.registerListener(listener, sensor2, SensorManager.SENSOR_DELAY_UI)
        }

        button2.setOnClickListener { view ->
            manager?.unregisterListener(listener)
        }
    }

    inner class SensorListener : SensorEventListener{
        override fun onSensorChanged(event: SensorEvent?) {
            when(event?.sensor?.type){
                Sensor.TYPE_ACCELEROMETER -> {
                    accValue = event?.values?.clone()
                }
                Sensor.TYPE_MAGNETIC_FIELD -> {
                    magValue = event?.values?.clone()
                }
            }
            if(magValue != null && accValue != null){
                var R = FloatArray(9)
                var I = FloatArray(9)

                SensorManager.getRotationMatrix(R, I, accValue, magValue)

                var values = FloatArray(3)
                SensorManager.getOrientation(R, values)

                var azimuth = radian2Degree(values[0])
                var pitch = radian2Degree(values[1])
                var roll = radian2Degree(values[2])

                textView.text = "방위값 : ${azimuth}\n"
                textView.append("좌우 기울기 : ${pitch}\n")
                textView.append("앞뒤 기울기 : ${roll}")
            }
        }

        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

        }
    }

    fun radian2Degree(radian : Float) : Float{
        return radian * 180 / Math.PI.toFloat()
    }
}
