package in.techware.lataxicustomer.listeners;


import in.techware.lataxicustomer.model.PlaceBean;

public interface PolyLineListener {

    void onLoadFailed(String error);

    void onLoadCompleted(PlaceBean placeBean);
}
