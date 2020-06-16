package in.techware.lataxidriverapp.listeners;

import in.techware.lataxidriverapp.model.RequestDetailsBean;

/**
 * Created by Jemsheer K D on 08 June, 2017.
 * Package in.techware.lataxidriver.listeners
 * Project LaTaxiDriver
 */

public interface RequestDetailsListener {

    void onLoadCompleted(RequestDetailsBean requestDetailsBean);

    void onLoadFailed(String error);
}
