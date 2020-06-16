package in.techware.lataxicustomer.listeners;


import in.techware.lataxicustomer.model.TripListBean;

public abstract interface TripListListener {

    void onLoadCompleted(TripListBean tripListBean);

    void onLoadFailed(String error);

}
