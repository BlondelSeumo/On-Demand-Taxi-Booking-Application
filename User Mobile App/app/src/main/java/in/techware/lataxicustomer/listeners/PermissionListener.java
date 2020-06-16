package in.techware.lataxicustomer.listeners;


public interface PermissionListener {

    void onPermissionCheckCompleted(int requestCode, boolean isPermissionGranted);

}
