package `in`.techware.lataxidriverapp.model

/**
 * Created by Jemsheer K D on 18 May, 2017.
 * Package in.techware.lataxidriver.model
 * Project LaTaxiDriver
 */

class RatingDetailsBean : BaseBean() {
    var totalRating: Int = 0
    var averageRatings: Float = 0f
    var totalRequests: Int = 0
    var requestsAccepted: Int = 0
    var totalTrips: Int = 0
    var tripsCancelled: Int = 0

    var requestAcceptedPercentage: Float = 0f
        get() = if (totalRequests == 0) 0F else requestsAccepted * 100f / totalRequests

    var tripsCancelledPercentage: Float = 0f
        get() = if (totalTrips == 0) 0F else tripsCancelled * 100f / totalTrips


}
