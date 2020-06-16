package in.techware.lataxicustomer.listeners;

import in.techware.lataxicustomer.model.TripFeedbackBean;

/**
 * Created by SIB-QC4 on 4/12/2017.
 */

public interface TripFeedbackListener {

    void onLoadFailed(String error);

    void onLoadCompleted(TripFeedbackBean tripFeedbackBean);
}
