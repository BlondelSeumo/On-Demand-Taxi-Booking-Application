package in.techware.lataxicustomer.listeners;


import in.techware.lataxicustomer.model.UserBean;

public interface UserInfoListener {

    void onLoadCompleted(UserBean userBean);

    void onLoadFailed(String error);

}
