package `in`.techware.lataxidriverapp.model

import java.util.*

/**
 * Created by Jemsheer K D on 05 May, 2017.
 * Package in.techware.lataxidriver.model
 * Project LaTaxiDriver
 */

class TripListBean : BaseBean() {

    var totalFare: String = ""
    var totalTimeOnline: String = ""
    var totalRidesTaken: Int = 0
    var trips: ArrayList<TripBean> = ArrayList()
    var pagination: PaginationBean = PaginationBean()
}
