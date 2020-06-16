package `in`.techware.lataxidriverapp.model

import java.util.*

/**
 * Created by Jemsheer K D on 16 May, 2017.
 * Package in.techware.lataxidriver.model
 * Project LaTaxiDriver
 */

class WeeklyEarningsBean : BaseBean() {

    var weekOfYear: Int = 0
    var weekStart: Long = 0
    var weekEnd: Long = 0
    var year: Int = 0
    var totalPayout: String = ""
    var dailyEarnings: ArrayList<DailyEarningBean> = ArrayList()
}
