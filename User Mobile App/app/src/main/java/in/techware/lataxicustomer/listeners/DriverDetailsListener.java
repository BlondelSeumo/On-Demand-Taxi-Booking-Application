package in.techware.lataxicustomer.listeners;


import in.techware.lataxicustomer.model.DriverBean;

public interface DriverDetailsListener {

    void onLoadCompleted(DriverBean driverBean);

    void onLoadFailed(String error);
}
