package `in`.techware.lataxicustomer.model


import java.util.ArrayList

class DriverRatingBean : BaseBean() {

    var rating: String = ""
    var badFeedbackList : ArrayList<String> = ArrayList()
    var goodFeedbackList : ArrayList<String> = ArrayList()
    var feedback: String = ""
}
