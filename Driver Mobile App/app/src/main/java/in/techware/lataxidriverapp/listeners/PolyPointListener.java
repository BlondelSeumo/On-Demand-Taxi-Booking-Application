package in.techware.lataxidriverapp.listeners;

import in.techware.lataxidriverapp.model.PolyPointBean;

/**
 * Created by Jemsheer K D on 09 May, 2017.
 * Package in.techware.lataxidriver.listeners
 * Project LaTaxiDriver
 */

public interface PolyPointListener {
    void onLoadCompleted(PolyPointBean polyPointBean);

    void onLoadFailed(String error);
}
