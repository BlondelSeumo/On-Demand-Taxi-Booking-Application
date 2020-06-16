package in.techware.lataxicustomer.listeners;


import in.techware.lataxicustomer.model.UserBean;

public interface EditProfileListener {

    void onLoadCompleted(UserBean userBean);

    void onLoadFailed(String error);

}
