package `in`.techware.lataxidriverapp.model

/**
 * Created by Jemsheer K D on 19 May, 2017.
 * Package in.techware.lataxidriver.model
 * Project LaTaxiDriver
 */

class CommentListBean : BaseBean() {

    var comments: ArrayList<CommentBean> = ArrayList()
    var pagination: PaginationBean = PaginationBean()
}
