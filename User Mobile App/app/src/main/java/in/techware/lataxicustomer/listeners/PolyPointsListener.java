package in.techware.lataxicustomer.listeners;


import in.techware.lataxicustomer.model.PolyPointsBean;

public interface PolyPointsListener {

    void onLoadFailed(String error);

    void onLoadCompleted(PolyPointsBean polyPointsBean);
}
