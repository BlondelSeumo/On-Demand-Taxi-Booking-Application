package in.techware.lataxicustomer.listeners;


import in.techware.lataxicustomer.model.CarBean;

public interface CarInfoListener {

    void onLoadFailed(String error);

    void onLoadCompleted(CarBean carBean);

}
