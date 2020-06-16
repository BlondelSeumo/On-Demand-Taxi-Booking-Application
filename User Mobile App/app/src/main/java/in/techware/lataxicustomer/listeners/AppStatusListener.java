package in.techware.lataxicustomer.listeners;


import in.techware.lataxicustomer.model.DriverBean;

public interface AppStatusListener {

    void onLoadFailed(String error);

    void onLoadCompleted(DriverBean driverBean);

}
