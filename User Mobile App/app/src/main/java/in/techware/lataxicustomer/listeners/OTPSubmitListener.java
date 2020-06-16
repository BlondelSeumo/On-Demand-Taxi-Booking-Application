package in.techware.lataxicustomer.listeners;

import in.techware.lataxicustomer.model.OTPBean;


public interface OTPSubmitListener {

    void onLoadCompleted(OTPBean otpBean);

    void onLoadFailed(String error);

}
