package `in`.techware.lataxidriverapp.model

import com.google.android.gms.maps.model.LatLng

/**
 * Created by Jemsheer K D on 14 June, 2017.
 * Package in.techware.lataxidriver.model
 * Project LaTaxiDriver
 */

class AppStatusBean : BaseBean() {

    var id: String = ""
    var appStatus: Int = 0
    var tripStatus: String = ""
    var driverID: String = ""
    var driverName: String = ""
    var driverPhoto: String = ""
    var driverStatus: Int = 0
    var customerID: String = ""
    var customerName: String = ""
    var customerPhoto: String = ""
    var sourceLocation: String = ""
    var sourceLatitude: String = ""
    var sourceLongitude: String = ""

    fun getSourceLatLng(): LatLng {
        return LatLng(dSourceLatitude, dSourceLongitude)
    }

    var destinationLocation: String = ""
    var destinationLatitude: String = ""
    var destinationLongitude: String = ""

    fun getDestinationLatLng(): LatLng {
        return LatLng(dDestinationLatitude, dDestinationLongitude)
    }

    var startTime: Long = 0


    val dSourceLatitude: Double
        get() {
            try {
                return java.lang.Double.parseDouble(sourceLatitude)
            } catch (e: NumberFormatException) {
                e.printStackTrace()
                return 0.0
            }

        }


    val dSourceLongitude: Double
        get() {
            try {
                return java.lang.Double.parseDouble(sourceLongitude)
            } catch (e: NumberFormatException) {
                e.printStackTrace()
                return 0.0
            }

        }


    val dDestinationLatitude: Double
        get() {
            try {
                return java.lang.Double.parseDouble(destinationLatitude)
            } catch (e: NumberFormatException) {
                e.printStackTrace()
                return 0.0
            }

        }


    val dDestinationLongitude: Double
        get() {
            try {
                return java.lang.Double.parseDouble(destinationLongitude)
            } catch (e: NumberFormatException) {
                e.printStackTrace()
                return 0.0
            }

        }


}
