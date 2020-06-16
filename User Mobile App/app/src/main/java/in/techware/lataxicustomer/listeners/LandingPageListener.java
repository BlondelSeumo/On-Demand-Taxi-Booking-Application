package in.techware.lataxicustomer.listeners;


import in.techware.lataxicustomer.model.LandingPageBean;

public interface LandingPageListener {

    void onLoadFailed(String error);

    void onLoadCompleted(LandingPageBean landingPageBean);

}
