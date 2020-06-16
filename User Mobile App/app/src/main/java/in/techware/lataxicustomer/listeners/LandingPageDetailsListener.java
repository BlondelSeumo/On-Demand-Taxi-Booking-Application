package in.techware.lataxicustomer.listeners;


import in.techware.lataxicustomer.model.LandingPageBean;

public interface LandingPageDetailsListener {

    void onLoadCompleted(LandingPageBean landingPageListBean);

    void onLoadFailed(String error);
}
