package `in`.techware.lataxidriverapp.model

import java.util.ArrayList

/**
 * Created by Jemsheer K D on 18 May, 2017.
 * Package in.techware.lataxidriver.model
 * Project LaTaxiDriver
 */

class IssueListBean : BaseBean() {

    var pagination: PaginationBean = PaginationBean()
    var issues: ArrayList<IssueBean> = ArrayList()

}
