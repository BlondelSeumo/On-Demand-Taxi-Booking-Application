package in.techware.lataxidriverapp.listeners;


import in.techware.lataxidriverapp.model.TripFeedbackBean;

public interface TripFeedbackListener {

    void onLoadFailed(String error);

    void onLoadCompleted(TripFeedbackBean tripFeedbackBean);

}
