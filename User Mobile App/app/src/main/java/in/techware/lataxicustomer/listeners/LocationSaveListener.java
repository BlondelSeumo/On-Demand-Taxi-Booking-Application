package in.techware.lataxicustomer.listeners;


import in.techware.lataxicustomer.model.LocationBean;

public interface LocationSaveListener {

    void onLoadCompleted(LocationBean locationBean);

    void onLoadFailed(String error);
}


