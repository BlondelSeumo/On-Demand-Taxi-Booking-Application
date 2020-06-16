package in.techware.lataxicustomer.listeners;

import in.techware.lataxicustomer.model.BasicBean;

public interface OTPResendCodeListener {

    void onLoadCompleted(BasicBean basicBean);

    void onLoadFailed(String error);
}
