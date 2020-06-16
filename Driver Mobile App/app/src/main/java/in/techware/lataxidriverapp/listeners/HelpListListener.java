package in.techware.lataxidriverapp.listeners;

import in.techware.lataxidriverapp.model.HelpListBean;

/**
 * Created by Jemsheer K D on 19 May, 2017.
 * Package in.techware.lataxidriver.listeners
 * Project LaTaxiDriver
 */

public interface HelpListListener {
    void onLoadCompleted(HelpListBean helpListBean);

    void onLoadFailed(String error);
}
