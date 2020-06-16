package in.techware.lataxicustomer.listeners;

import in.techware.lataxicustomer.model.TripCancellationBean;

public interface TripCancellationListener {

    void onLoadCompleted(TripCancellationBean tripCancellationBean);

    void onLoadFailed(String error);
}
