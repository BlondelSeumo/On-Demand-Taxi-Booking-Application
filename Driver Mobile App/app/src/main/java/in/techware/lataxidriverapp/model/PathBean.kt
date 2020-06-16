package `in`.techware.lataxidriverapp.model

/**
 * Created by Jemsheer K D on 14 June, 2017.
 * Package in.techware.lataxidriver.model
 * Project LaTaxiDriver
 */

class PathBean : BaseBean() {

    var tripID: String = ""
    var index: Int = 0
    var time: Long = 0
    var latitude: String= ""
    var longitude: String= ""

    fun getDLatitude(): Double {
        try {
            return latitude.toDouble()
        } catch (e: Exception) {
            return 0.0
        }
    }

    fun getDLongitude(): Double {
        try {
            return longitude.toDouble()
        } catch (e: Exception) {
            return 0.0
        }
    }
}
