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
import com.google.android.gms.maps.model.LatLng

class MainActivity : AppCompatActivity() {

    var googleMap:GoogleMap? = null
    var locManager:LocationManager? = null

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
}









