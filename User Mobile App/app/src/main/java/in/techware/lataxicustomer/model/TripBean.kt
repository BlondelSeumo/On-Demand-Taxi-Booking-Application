package `in`.techware.lataxicustomer.model


import com.google.android.gms.maps.model.LatLng

class TripBean : BaseBean(), Comparable<TripBean> {

    var id: String = ""
    var tripStatus: String = ""
    var date: String = ""
    var carName: String = ""
    var time: Long = 0
    var rate: String = ""
    var driverPhoto: String = ""
    var sourceLatitude: String = ""
    var sourceLongitude: String = ""
    var destinationLatitude: String = ""
    var destinationLongitude: String = ""

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


    override operator fun compareTo(other: TripBean): Int {
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
