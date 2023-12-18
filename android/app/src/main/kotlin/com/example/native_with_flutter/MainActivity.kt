package com.example.native_with_flutter



import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.content.IntentFilter
import android.location.Location
import android.location.LocationManager
import android.os.BatteryManager
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import android.widget.Toast
import androidx.annotation.NonNull
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugins.GeneratedPluginRegistrant

class MainActivity: FlutterActivity() {

    
    private val CHANNEL = "flutternativecode"

    override fun configureFlutterEngine(@NonNull flutterEngine: FlutterEngine) {
        GeneratedPluginRegistrant.registerWith(flutterEngine)
        MethodChannel(flutterEngine.dartExecutor.binaryMessenger, CHANNEL).setMethodCallHandler {
            // Note: this method is invoked on the main thread.
                call, result ->

            if (call.method == "showToast") {
                dndnwqdnqww
                val message = call.argument<String>("message")
                showToast(message)
                result.success("Toast shown") // Return success message to Flutter
            }
           else if (call.method == "getBatteryLevel") {
                val batteryLevel = this.getBatteryLevel()

                if (batteryLevel != -1) {
                    result.error("UNAVAILABLE", "Battery level not available.", null)
//                    result.success(batteryLevel)
                } else {
                    result.error("UNAVAILABLE", "Battery level not available.", null)
                }
            }
            else if(call.method == "greetingFromNativeCode")
            {
                val name = call.argument<String>("name") ?: "unknown"
                result.success("Hello $name, from Android code")
            }
//            else if (call.method == "getLocation") {
//                val location = getLastKnownLocation()
//                if (location != null) {
//                    val locationMap = mapOf(
//                        "latitude" to location.latitude,
//                        "longitude" to location.longitude
//                    )
//                    result.success(locationMap)
//                } else {
//                    result.error("UNAVAILABLE", "Location not available.", null)
//                }
//            }
            else {
                result.notImplemented()
            }
        }

    }
    fun getBatteryLevel(): Int {
        val batteryLevel: Int
        if (VERSION.SDK_INT >= VERSION_CODES.LOLLIPOP) {
            val batteryManager = getSystemService(Context.BATTERY_SERVICE) as BatteryManager
            batteryLevel = batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY)
        } else {
            val intent = ContextWrapper(applicationContext).registerReceiver(null, IntentFilter(Intent.ACTION_BATTERY_CHANGED))
            batteryLevel = intent!!.getIntExtra(BatteryManager.EXTRA_LEVEL, -1) * 100 / intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1)
        }

        return batteryLevel
    }

    private fun showToast(message: String?) {
        runOnUiThread {
            Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()

        }
    }

//    private  fun getLastKnownLocation(): Location? {
//        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
//        return try {
//            if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
//                locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
//            } else {
//                null
//            }
//        } catch (e: SecurityException) {
//            e.printStackTrace()
//            null
//        }
//    }
}
