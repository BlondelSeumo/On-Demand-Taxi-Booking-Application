package `in`.techware.lataxidriverapp.model

import com.google.android.gms.maps.model.LatLng

/**
 * Created by Jemsheer K D on 08 June, 2017.
 * Package in.techware.lataxidriver.model
 * Project LaTaxiDriver
 */

class RequestDetailsBean : BaseBean() {

    var requestID: String = ""
    var carType: String = ""
    var distance: String = ""
    var eta: String = ""
    var carTypeImage: String = ""
    var customerID: String = ""
    var customerName: String = ""
    var customerPhoto: String = ""
    var customerLocation: String = ""
    var customerLatitude: String = ""
    var customerLongitude: String = ""


    fun getDestinationLatLng(): LatLng {
        return LatLng(dDestinationLatitude, dDestinationLongitude)
    }

    var dDestinationLatitude: Double = 0.0
        get() {
            try {
                return java.lang.Double.parseDouble(customerLatitude)
            } catch (e: NumberFormatException) {
                e.printStackTrace()
                return 0.0
            }

        }


    var dDestinationLongitude: Double = 0.0
        get() {
            try {
                return java.lang.Double.parseDouble(customerLongitude)
            } catch (e: NumberFormatException) {
                e.printStackTrace()
                return 0.0
            }

        }
}
