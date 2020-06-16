package in.techware.lataxidriverapp.listeners;


import in.techware.lataxidriverapp.model.TripSummaryBean;

public interface TripSummaryListener {

    void onLoadCompleted(TripSummaryBean tripSummaryBean);

    void onLoadFailed(String error);
}
