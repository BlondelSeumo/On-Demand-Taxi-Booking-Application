package in.techware.lataxicustomer.listeners;


import in.techware.lataxicustomer.model.SuccessBean;

public interface SuccessListener {

    void onLoadCompleted(SuccessBean successBean);

    void onLoadFailed(String error);
}
