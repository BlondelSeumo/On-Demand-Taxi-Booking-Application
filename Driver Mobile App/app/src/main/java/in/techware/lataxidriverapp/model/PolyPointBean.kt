package `in`.techware.lataxidriverapp.model

import java.util.ArrayList
import java.util.HashMap

/**
 * Created by Jemsheer K D on 09 May, 2017.
 * Package in.techware.lataxidriver.model
 * Project LaTaxiDriver
 */

class PolyPointBean : BaseBean() {

    var time: Long = 0
    var distance: Int = 0
    var timeText: String= ""
    var distanceText: String= ""

    var routes: ArrayList<List<HashMap<String, String>>> = ArrayList()
}
