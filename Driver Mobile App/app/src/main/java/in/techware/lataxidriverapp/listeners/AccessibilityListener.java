package in.techware.lataxidriverapp.listeners;


import in.techware.lataxidriverapp.model.AccessibilityBean;

public interface AccessibilityListener {

    void onLoadCompleted(AccessibilityBean accessibilityBean);

    void onLoadFailed(String error);
}
