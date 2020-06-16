package `in`.techware.lataxicustomer.model


import java.util.ArrayList

class FeedbackBean : BaseBean() {

    var successBean: String = ""
    var rating: Float = 0.toFloat()
    var badFeedbackList = ArrayList<String>()
    var goodFeedbackList = ArrayList<String>()
    var badFeedBackType: String = ""
    var goodFeedBackTYpe: String = ""
    var feedback: String = ""
}
