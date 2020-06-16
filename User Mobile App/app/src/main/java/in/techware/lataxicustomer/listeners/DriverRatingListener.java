package in.techware.lataxicustomer.listeners;


import in.techware.lataxicustomer.model.DriverRatingBean;

public interface DriverRatingListener {

    void onLoadCompleted(DriverRatingBean driverRatingBean);

    void onLoadFailed(String error);
}


