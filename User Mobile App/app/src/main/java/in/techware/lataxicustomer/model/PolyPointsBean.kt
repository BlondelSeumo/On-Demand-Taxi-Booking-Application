package `in`.techware.lataxicustomer.model


import java.util.HashMap

class PolyPointsBean : BaseBean() {

    var time: Long = 0
    var distance: Int = 0
    var timeText: String = ""
    var distanceText: String = ""

    var routes: List<List<HashMap<String, String>>> = ArrayList()
}
