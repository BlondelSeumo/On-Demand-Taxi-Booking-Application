package in.techware.lataxidriverapp.listeners;

import in.techware.lataxidriverapp.model.TripListBean;

/**
 * Created by Jemsheer K D on 08 May, 2017.
 * Package in.techware.lataxidriver.listeners
 * Project LaTaxiDriver
 */

public interface TripListListener {

    void onLoadCompleted(TripListBean tripListBean);

    void onLoadFailed(String error);

}
