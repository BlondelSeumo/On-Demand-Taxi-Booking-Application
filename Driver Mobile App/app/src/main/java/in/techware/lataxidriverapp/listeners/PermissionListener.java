package in.techware.lataxidriverapp.listeners;


public interface PermissionListener {

    void onPermissionCheckCompleted(int requestCode, boolean isPermissionGranted);

}
