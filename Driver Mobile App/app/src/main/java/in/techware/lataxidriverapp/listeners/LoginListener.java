package in.techware.lataxidriverapp.listeners;

import in.techware.lataxidriverapp.model.AuthBean;

/**
 * Created by Jemsheer K D on 28 April, 2017.
 * Package in.techware.lataxidriver.listeners
 * Project LaTaxiDriver
 */

public interface LoginListener {

    void onLoadCompleted(AuthBean authBean);

    void onLoadFailed(String error);

}
