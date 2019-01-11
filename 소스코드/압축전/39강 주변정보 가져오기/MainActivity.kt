package kr.co.softcampus.googlemap

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapFragment
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

class MainActivity : AppCompatActivity() {

    var googleMap:GoogleMap? = null
    var locManager:LocationManager? = null

    var lat_list = ArrayList<Double>()
    var lng_list = ArrayList<Double>()
    var name_list = ArrayList<String>()
    var vicinity_list = ArrayList<String>()

    var marker_list = ArrayList<Marker>()

    var permission_list = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            requestPermissions(permission_list, 0)
        } else {
            init()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        for(result in grantResults){
            if(result == PackageManager.PERMISSION_DENIED){
                return
            }
        }
        init()
    }

    fun init(){
        var callback = MapReadyCallback()
        var mapFragment = fragmentManager.findFragmentById(R.id.map) as MapFragment
        mapFragment.getMapAsync(callback)
    }

    inner class MapReadyCallback : OnMapReadyCallback{
        override fun onMapReady(p0: GoogleMap?) {
            googleMap = p0
            getMyLocation()
        }
    }

    fun getMyLocation(){
        locManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED){
                return
            }
            if(checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_DENIED){
                return
            }
        }

        var location = locManager?.getLastKnownLocation(LocationManager.GPS_PROVIDER)
        var location2 = locManager?.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)

        if(location != null){
            setMyLocation(location)
        } else {
            if(location2 != null){
                setMyLocation(location2)
            }
        }

        var listener = GetMyLocationListener()

        if(locManager?.isProviderEnabled(LocationManager.GPS_PROVIDER)!! == true){
            locManager?.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 10f, listener)
        } else if(locManager?.isProviderEnabled(LocationManager.NETWORK_PROVIDER)!! == true){
            locManager?.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 10f, listener)
        }
    }

    fun setMyLocation(location: Location){

//        Log.d("test123", "위도 ${location.latitude}")
//        Log.d("test123", "경도 ${location.longitude}")

        var position = LatLng(location.latitude, location.longitude)

        var update1 = CameraUpdateFactory.newLatLng(position)
        var update2 = CameraUpdateFactory.zoomTo(15f)

        googleMap?.moveCamera(update1)
        googleMap?.animateCamera(update2)

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED){
                return
            }
            if(checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_DENIED){
                return
            }
        }
        googleMap?.isMyLocationEnabled = true

        // googleMap?.mapType = GoogleMap.MAP_TYPE_NORMAL
        // googleMap?.mapType = GoogleMap.MAP_TYPE_HYBRID
        // googleMap?.mapType = GoogleMap.MAP_TYPE_SATELLITE
        // googleMap?.mapType = GoogleMap.MAP_TYPE_TERRAIN
        // googleMap?.mapType = GoogleMap.MAP_TYPE_NONE

        var thread = NetworkThread(location.latitude, location.longitude)
        thread.start()

    }

    inner class GetMyLocationListener : LocationListener{
        override fun onLocationChanged(p0: Location?) {
            setMyLocation(p0!!)
            locManager?.removeUpdates(this)
        }

        override fun onProviderDisabled(p0: String?) {

        }

        override fun onProviderEnabled(p0: String?) {

        }

        override fun onStatusChanged(p0: String?, p1: Int, p2: Bundle?) {

        }
    }

    inner class NetworkThread(var lat:Double, var lng:Double) : Thread(){
        override fun run() {
            var client = OkHttpClient()
            var builder = Request.Builder()

            var str = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=${lat},${lng}&radius=1000&key=AIzaSyDq66IfrWAcB1rNu_5VoeZpGCnyMRr1I60&type=restaurant&sensor=false&language=ko"

            var url = builder.url(str)
            var request = url.build()

            var callback = Callback1()
            client.newCall(request).enqueue(callback)
        }
    }

    inner class Callback1 : Callback {
        override fun onFailure(call: Call?, e: IOException?) {

        }

        override fun onResponse(call: Call?, response: Response?) {
            var result = response?.body()?.string()

            var obj = JSONObject(result)

            var status = obj.getString("status")

            if(status == "OK"){

                var results = obj.getJSONArray("results")

                lat_list.clear()
                lng_list.clear()
                name_list.clear()
                vicinity_list.clear()

                for(i in 0 until results.length()){
                    var obj2 = results.getJSONObject(i)

                    var geometry = obj2.getJSONObject("geometry")
                    var location = geometry.getJSONObject("location")
                    var lat2 = location.getDouble("lat")
                    var lng2 = location.getDouble("lng")

                    var name = obj2.getString("name")
                    var vicinity = obj2.getString("vicinity")

                    lat_list.add(lat2)
                    lng_list.add(lng2)
                    name_list.add(name)
                    vicinity_list.add(vicinity)

                }

                runOnUiThread {
                    for(marker in marker_list){
                        marker.remove()
                    }
                    marker_list.clear()

                    for(i in 0 until lat_list.size){
                        var lat3 = lat_list.get(i)
                        var lng3 = lng_list.get(i)
                        var name3 = name_list.get(i)
                        var vicinity3 = vicinity_list.get(i)

                        var position = LatLng(lat3, lng3)

                        var option = MarkerOptions()
                        option.position(position)

                        option.title(name3)
                        option.snippet(vicinity3)

                        var bitmap = BitmapDescriptorFactory.fromResource(android.R.drawable.ic_menu_mylocation)
                        option.icon(bitmap)


                        var marker = googleMap?.addMarker(option)

                        marker_list.add(marker!!)
                    }
                }
            }
        }
    }
}









