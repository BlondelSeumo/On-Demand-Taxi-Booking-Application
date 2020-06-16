package in.techware.lataxidriverapp.listeners;

import in.techware.lataxidriverapp.model.IssueListBean;

/**
 * Created by Jemsheer K D on 19 May, 2017.
 * Package in.techware.lataxidriver.listeners
 * Project LaTaxiDriver
 */

public interface IssueListListener {

    void onLoadCompleted(IssueListBean issueListBean);

    void onLoadFailed(String error);

}
