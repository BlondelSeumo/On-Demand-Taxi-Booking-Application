package in.techware.lataxidriverapp.listeners;

import in.techware.lataxidriverapp.model.CommentListBean;

/**
 * Created by Jemsheer K D on 19 May, 2017.
 * Package in.techware.lataxidriver.listeners
 * Project LaTaxiDriver
 */

public interface CommentListListener {

    void onLoadCompleted(CommentListBean commentListBean);

    void onLoadFailed(String error);
}
