package `in`.techware.lataxicustomer.model

class LandingPageBean : BaseBean() {

    var cars: ArrayList<CarBean> = ArrayList()

    fun getCar(carID: String): CarBean? {
        for (car in cars) {
            if (car.carID.equals(carID, ignoreCase = true)) {
                return car
            }
        }
        return null
    }


}
