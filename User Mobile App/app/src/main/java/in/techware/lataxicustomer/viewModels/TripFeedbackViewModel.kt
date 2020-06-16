package `in`.techware.lataxicustomer.viewModels

import `in`.techware.lataxicustomer.model.FeedbackBean
import android.arch.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng

/**
 * Created by Jemsheer K D on 27 July, 2018.
 * Package `in`.techware.lataxi.viewModels
 * Project Carrefour
 */
class TripFeedbackViewModel : ViewModel() {

    var rating: Float = 5F
    var time: Long = 0
    var driverName: String = ""
    var driverPhoto: String = ""
    var fare: String = ""
    var source: String = ""
    var sourceLatitude: String = ""
    var sourceLongitude: String = ""
    var destination: String = ""
    var destinationLatitude: String = ""
    var destinationLongitude: String = ""

    var goodFeedbackList: ArrayList<String> = ArrayList()
    var badFeedbackList: ArrayList<String> = ArrayList()

    var feedback: String = ""

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

    fun generateFeedbackBean(): FeedbackBean {
        var feedbackBean = FeedbackBean()

        feedbackBean.rating = rating
        feedbackBean.goodFeedbackList = goodFeedbackList
        feedbackBean.badFeedbackList = badFeedbackList
        feedbackBean.feedback = feedback

        return feedbackBean
    }

    fun isDataCollected(): Boolean {
        return (rating != 0F)

    }

    fun clearFeedback() {
        rating = 5f
    }

    /*fun indexOf(feedbackBean: FeedbackBean): Int {
        for (bean in feedbackList) {
            if (bean.barcode == feedbackBean.barcode) {
                return feedbackList.indexOf(bean)
            }
        }
        return -1
    }

    fun lastIndex(): Int {
        return feedbackList.size - 1
    }*/
}