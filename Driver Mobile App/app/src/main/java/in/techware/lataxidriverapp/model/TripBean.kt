package `in`.techware.lataxidriverapp.model

import com.google.android.gms.maps.model.LatLng

/**
 * Created by Jemsheer K D on 05 May, 2017.
 * Package in.techware.lataxidriver.model
 * Project LaTaxiDriver
 */

class TripBean : BaseBean(), Comparable<TripBean> {


    var id: String = ""
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
    var endTime: Long = 0
    var fare: String = ""
    var fee: String = ""
    var tax: String = ""
    var estimatedPayout: String = ""
    var duration: String = ""
    var distance: String = ""
    var rating: Float = 0.toFloat()


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

    override fun compareTo(other: TripBean): Int {
        val bean = other
        val comparison = id.compareTo(bean.id)
        return if (comparison == 0) {
            0
        } else if (comparison > 0) {
            1
        } else
            -1
    }
}
