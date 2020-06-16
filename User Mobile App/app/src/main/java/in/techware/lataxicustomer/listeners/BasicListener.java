package in.techware.lataxicustomer.listeners;

import in.techware.lataxicustomer.model.BasicBean;

public interface BasicListener {

    void onLoadCompleted(BasicBean basicBean);

    void onLoadFailed(String error);

}
