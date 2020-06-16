package in.techware.lataxidriverapp.listeners;

import in.techware.lataxidriverapp.model.DocumentStatusBean;

/**
 * Created by Jemsheer K D on 28 April, 2017.
 * Package in.techware.lataxidriver.listeners
 * Project LaTaxiDriver
 */

public interface DocumentStatusListener {

    void onLoadCompleted(DocumentStatusBean documentStatusBean);

    void onLoadFailed(String error);
}
