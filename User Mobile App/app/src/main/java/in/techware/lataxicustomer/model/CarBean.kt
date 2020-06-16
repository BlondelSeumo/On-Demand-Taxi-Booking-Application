package `in`.techware.lataxicustomer.model

class CarBean : BaseBean(), Comparable<CarBean> {

    var carID: String = ""
    var carsAvailable: String = ""
    var carName: String = ""
    var carImage: String = ""
    var minTime: String = ""
    var minFare: String = ""
    var maxSize: String = ""

    override fun compareTo(other: CarBean): Int {
        val id = Integer.parseInt(carID)
        val tripID = Integer.parseInt(other.carID)
        return if (id == tripID) {
            0
        } else if (id > tripID) {
            1
        } else
            -1
    }
}
