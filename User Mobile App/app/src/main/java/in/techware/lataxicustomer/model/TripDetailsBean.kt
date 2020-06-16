package `in`.techware.lataxicustomer.model

import com.google.android.gms.maps.model.LatLng

class TripDetailsBean : BaseBean() {

    var id: String = ""
    var tripStatus: String = ""
    var time: String = ""
    var rating: Float = 0.toFloat()
    var driverName: String = ""
    var driverPhoto: String = ""
    var kilometer: String = ""
    var minute: String = ""
    var baseFare: String = ""
    var kilometerFare: String = ""
    var minutesFare: String = ""
    var subTotalFare: String = ""
    var promotionFare: String = ""
    var totalFare: String = ""

    var sourceLatitude: String = ""
    var sourceLongitude: String = ""
    var destinationLatitude: String = ""
    var destinationLongitude: String = ""

    var sourceName: String = ""
    var destinationName: String = ""

    var path: List<PlaceBean> = ArrayList()

    fun getSourceLatLng(): LatLng {
        return LatLng(dSourceLatitude, dSourceLongitude)
    }

    fun getDestinationLatLng(): LatLng {
        return LatLng(dDestinationLatitude, dDestinationLongitude)
    }


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
