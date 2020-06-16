package in.techware.lataxidriverapp.listeners;


import in.techware.lataxidriverapp.model.AuthBean;

public interface PhoneRegistrationListener {

    void onLoadCompleted(AuthBean authBean);

    void onLoadFailed(String error);
}
