package in.techware.lataxidriverapp.listeners;

import in.techware.lataxidriverapp.model.RatingDetailsBean;

/**
 * Created by Jemsheer K D on 18 May, 2017.
 * Package in.techware.lataxidriver.listeners
 * Project LaTaxiDriver
 */

public interface RatingDetailsListener {
    void onLoadCompleted(RatingDetailsBean ratingDetailsBean);

    void onLoadFailed(String error);

}
