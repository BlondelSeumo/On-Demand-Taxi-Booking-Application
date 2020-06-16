package in.techware.lataxidriverapp.listeners;


import in.techware.lataxidriverapp.model.ProfileBean;


public interface ProfileListener {

    void onLoadCompleted(ProfileBean profileBean);

    void onLoadFailed(String error);
}
