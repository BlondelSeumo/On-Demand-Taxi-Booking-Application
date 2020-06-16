package in.techware.lataxicustomer.listeners;


import in.techware.lataxicustomer.model.AuthBean;

public interface LoginListener {

    void onLoadCompleted(AuthBean authBean);

    void onLoadFailed(String error);
}
