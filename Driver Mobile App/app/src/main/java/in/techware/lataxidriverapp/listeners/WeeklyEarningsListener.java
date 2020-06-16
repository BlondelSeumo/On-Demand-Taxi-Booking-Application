package in.techware.lataxidriverapp.listeners;

import in.techware.lataxidriverapp.model.WeeklyEarningsBean;

/**
 * Created by Jemsheer K D on 16 May, 2017.
 * Package in.techware.lataxidriver.listeners
 * Project LaTaxiDriver
 */

public interface WeeklyEarningsListener {

    void onLoadCompleted(WeeklyEarningsBean weeklyEarningsBean);

    void onLoadFailed(String error);
}
