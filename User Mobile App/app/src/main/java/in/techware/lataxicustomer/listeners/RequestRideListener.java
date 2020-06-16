package in.techware.lataxicustomer.listeners;


import in.techware.lataxicustomer.model.RequestBean;

public interface RequestRideListener {

    void onLoadCompleted(RequestBean requestBean);

    void onLoadFailed(String error);
}
