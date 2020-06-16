package `in`.techware.lataxicustomer.model


import com.google.android.gms.maps.model.LatLng

class DriverBean : BaseBean() {

    var tripID: String = ""
    var appStatus: Int = 0
    var requestStatus: String = ""
    var driverName: String = ""
    var driverPhoto: String = ""
    var driverNumber: String = ""
    var rating: Float = -1f
    var carName: String = ""
    var carNumber: String = ""
    var time: String = ""
    var carPhoto: String = ""
    var sourceLatitude: String = ""
    var sourceLongitude: String = ""
    fun getSourceLatLng(): LatLng {
        return LatLng(dSourceLatitude, dSourceLongitude)
    }

    var destinationLatitude: String = ""
    var destinationLongitude: String = ""
    fun getDestinationLatLng(): LatLng {
        return LatLng(dDestinationLatitude, dDestinationLongitude)
    }

    var carLatitude: String = ""
    var carLongitude: String = ""
    fun getCarLatLng(): LatLng {
        return LatLng(dCarLatitude, dCarLongitude)
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


    val dCarLatitude: Double
        get() {
            try {
                return java.lang.Double.parseDouble(carLatitude)
            } catch (e: NumberFormatException) {
                e.printStackTrace()
                return 0.0
            }

        }


    val dCarLongitude: Double
        get() {
            try {
                return java.lang.Double.parseDouble(carLongitude)
            } catch (e: NumberFormatException) {
                e.printStackTrace()
                return 0.0
            }

        }

}
