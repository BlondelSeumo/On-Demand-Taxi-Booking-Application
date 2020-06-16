package in.techware.lataxidriverapp.listeners;

import in.techware.lataxidriverapp.model.AppStatusBean;

/**
 * Created by Jemsheer K D on 14 June, 2017.
 * Package in.techware.lataxidriver.listeners
 * Project LaTaxiDriver
 */

public interface AppStatusListener {
    void onLoadCompleted(AppStatusBean appStatusBean);

    void onLoadFailed(String error);
}
