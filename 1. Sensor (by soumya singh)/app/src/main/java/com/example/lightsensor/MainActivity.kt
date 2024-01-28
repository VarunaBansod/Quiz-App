package com.example.lightsensor

import android.content.ContentValues.TAG
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import java.io.IOException
// 1step: you have to extend sensor event listner, then implement members
class MainActivity : AppCompatActivity(), SensorEventListener {

    var sensor: Sensor?=null //1st var is of ->type variable
    var sensorManager:SensorManager?=null //2nd var is of sensor manager type
    //we have used following two in activity layout so to use them we have declared them
    lateinit var imglightOn:ImageView
    lateinit var imglightOff:ImageView
    lateinit var background:LinearLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        imglightOn=findViewById(R.id.lighton_img)
        imglightOff = findViewById(R.id.lightoff_img)
        background=findViewById(R.id.backgroundMain)
        imglightOn.visibility=View.INVISIBLE //hiding image at first, so that we'll know if app is working fine or not
        //initializing sensorManager var and sensor var
        sensorManager= getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensor= sensorManager!!.getDefaultSensor(Sensor.TYPE_LIGHT)

    }

    override fun onResume() {
        // Register a listener for the sensor.
        super.onResume()
        //type "?" after sensorManager to tackle null pointer
        sensorManager?.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL)
    }
    override fun onSensorChanged(event: SensorEvent?) {
        //most imp sensor
        //sensor se info array form me ati hai
        //try catch is used to avoid app crashing if some issue is faced by sensor (IMP CONCEPT)
        try{
            if (event != null) {
                Log.d(TAG,"onSensorChanged: "+ event.values[0])
            }
            if(event!!.values[0]<30){
                //light is dim
                imglightOn.visibility= View.INVISIBLE //torch image remove ho jayegi agr light dim hai to
//                background.setBackgroundColor(getColor(R.color.black))
                imglightOff.visibility=View.VISIBLE
                background.setBackgroundColor(getColor(R.color.gray))
            }else{
                //show torch if light intensity is high
                imglightOn.visibility=View.VISIBLE
                imglightOff.visibility=View.INVISIBLE
                background.setBackgroundColor((getColor(R.color.light)))
            }
        }catch (e:IOException){
            Log.d(TAG,"onSensorChanged: + ${e.message}") //$(e.message0 will show error message
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }
    override fun onPause() {
        // Be sure to unregister the sensor when the activity pauses. so that there will be no activity if app is unused
        super.onPause()
        sensorManager?.unregisterListener(this)
    }

}