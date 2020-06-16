package `in`.techware.lataxidriverapp.model

import `in`.techware.lataxidriverapp.util.AppConstants
import java.util.*

/**
 * Created by Jemsheer K D on 28 April, 2017.
 * Package in.techware.lataxidriver.model
 * Project LaTaxiDriver
 */

class DocumentStatusBean : BaseBean() {

    var documents: ArrayList<DocumentBean> = ArrayList()

    val isAllDocumentsUploaded: Boolean
        get() {


            if (!documents.isEmpty()) {
                for (bean in documents) {
                    if (!bean.isUploaded || bean.documentStatus != AppConstants.DOCUMENT_STATUS_PENDING_APPROVAL
                            && bean.documentStatus != AppConstants.DOCUMENT_STATUS_APPROVED) {
                        return false
                    }
                }
            }

            return true
        }
}
