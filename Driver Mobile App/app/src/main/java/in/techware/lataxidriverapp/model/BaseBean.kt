package `in`.techware.lataxidriverapp.model

import java.io.Serializable

open class BaseBean : Serializable {

    var isWebError: Boolean = false
    protected var success: Boolean = false
    var status: String = ""
    var error: String = ""
    var errorMsg: String = ""
    var webMessage: String = ""


}
