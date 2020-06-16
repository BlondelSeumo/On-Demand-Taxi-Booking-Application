package in.techware.lataxicustomer.listeners;


import in.techware.lataxicustomer.model.TripDetailsBean;

public interface TripDetailsListener {

    void onLoadCompleted(TripDetailsBean tripDetailsBean);

    void onLoadFailed(String error);
}
