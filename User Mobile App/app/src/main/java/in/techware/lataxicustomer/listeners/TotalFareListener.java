package in.techware.lataxicustomer.listeners;


import in.techware.lataxicustomer.model.FareBean;

public interface TotalFareListener {

    void onLoadCompleted(FareBean fareBean);

    void onLoadFailed(String error);
}
