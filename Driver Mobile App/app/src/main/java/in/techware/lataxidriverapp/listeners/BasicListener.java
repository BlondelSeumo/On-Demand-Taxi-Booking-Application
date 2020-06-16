package in.techware.lataxidriverapp.listeners;


import in.techware.lataxidriverapp.model.BasicBean;

public interface BasicListener {

    void onLoadCompleted(BasicBean basicBean);

    void onLoadFailed(String error);
}
