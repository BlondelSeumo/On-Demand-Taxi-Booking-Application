package in.techware.lataxicustomer.listeners;


import in.techware.lataxicustomer.model.FreeRideBean;

public interface FreeRideListener {

    void onLoadCompleted(FreeRideBean freeRideBean);

    void onLoadFailed(String error);

}
