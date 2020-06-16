package in.techware.lataxicustomer.listeners;


import in.techware.lataxicustomer.model.LocationBean;

public interface SavedLocationListener {

    void onLoadCompleted(LocationBean locationBean);

    void onLoadFailed(String error);

}
