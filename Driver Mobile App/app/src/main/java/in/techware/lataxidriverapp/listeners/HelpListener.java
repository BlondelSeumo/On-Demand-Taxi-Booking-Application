package in.techware.lataxidriverapp.listeners;

import in.techware.lataxidriverapp.model.HelpBean;

/**
 * Created by Jemsheer K D on 20 May, 2017.
 * Package in.techware.lataxidriver.listeners
 * Project LaTaxiDriver
 */

public interface HelpListener {
    void onLoadCompleted(HelpBean helpBean);

    void onLoadFailed(String error);
}
