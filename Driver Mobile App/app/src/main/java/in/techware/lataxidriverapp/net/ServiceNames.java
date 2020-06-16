package in.techware.lataxidriverapp.net;


public class ServiceNames {

    /*Set BASE URL here*/
    private static final String PRODUCTION_API = "http://techlabz.in";
  

    /* Set API VERSION here*/
    public static final String API_VERSION = "/lataxi/Webservices_driver";


    /*Set UPLOAD PATH. DO NOT CHANGE THIS UNLESS YOU KNOW WHAT YOU ARE DOING.*/
    public static final String PATH_UPLOADS = "/lataxi/";


    /*Set API URL here*/
    private static final String API = PRODUCTION_API + API_VERSION;

    /*Set IMAGE UPLOAD URL here.  DO NOT CHANGE THIS UNLESS YOU KNOW WHAT YOU ARE DOING.*/
    public static final String API_UPLOADS = PRODUCTION_API + PATH_UPLOADS;


    /* API END POINTS*/


    public static final String DUMMY = API + "/dummy?";

    public static final String POLY_POINTS = "https://maps.googleapis.com/maps/api/directions/json?";

    public static final String REGISTRATION = API + "/registration?";
    public static final String AUTH_EMAIL = API + "/login?";
    public static final String FORGOT_PASSWORD = API + "/forgot_password?";

    public static final String FCM_UPDATE = API + "/update_fcm_token?";

    public static final String PHONE_REGISTRATION = API + "/register_mobile?";
    public static final String MOBILE_NUMBER_AVAILABILITY = API + "/mobile_number_availability?";
    public static final String OTP_VERIFICATION = API + "/verify_otp?";
    public static final String OTP_SEND = API + "/login?";
    public static final String OTP_RESEND_CODE = API + "/resend_otp?";

    public static final String DOCUMENT_UPDATE = API + "/document_upload?";
    public static final String DOCUMENT_STATUS = API + "/document_status?";

    public static final String DRIVER_DETAILS_REGISTRATION = API + "/registration?";
    public static final String DRIVER_TYPE_REGISTRATION = API + "/update_driver_type?";
    public static final String DRIVER_STATUS_CHANGE = API + "/update_driver_status?";
    public static final String DRIVER_STATUS = API + "/get_driver_status?";
    public static final String DRIVER_LOCATION_UPDATE = API + "/update_driver_location?";

    public static final String TODAY_TRIP_LIST = API + "/trip_list_for_today?";
    public static final String TRIP_HISTORY = API + "/trip_history?";
    public static final String TRIP_DETAILS = API + "/trip_details?";

    public static final String WEEKLY_EARNINGS = API + "/weekly_earnings?";
    public static final String RATING_DETAILS = API + "/rating_details?";

    public static final String TRIP_FEEDBACK = API + "/tripfeedback?";
    public static final String TRIP_SUMMARY = API + "/trip_summary?";
    public static final String REQUEST_DETAILS = API + "/request_details?";

    public static final String TRIP_ACCEPT = API + "/trip_accept?";
    public static final String TRIP_CONFIRM_CAR_ARRIVAL = API + "/confirm_car_arrival?";
    public static final String TRIP_START = API + "/trip_start?";
    public static final String TRIP_END = API + "/trip_end?";
    public static final String TRIP_CONFIRM_CASH_COLLECTION = API + "/confirm_cash_collection?";


    public static final String ISSUE_LIST = API + "/rider_feedback_issues?";
    public static final String COMMENT_LIST = API + "/rider_feedback_comments?";

    public static final String HELP_LIST = API + "/help_page_list?";
    public static final String HELP_PAGE = API + "/help?";
    public static final String HELP_PAGE_REVIEW = API + "/help_page_review?";

    public static final String APP_STATUS = API + "/app_status?";

    public static final String UPLOAD_PROFILE_PHOTO = API + "/profile_photo_upload?";

    public static final String PROFILE = API + "/get_profile?";
    public static final String PROFILE_UPDATE = API + "/update_profile?";

    public static final String DRIVER_ACCESSIBILITY_FETCH = API + "/fetch_accesibility_settings?";
    public static final String DRIVER_ACCESSIBILITY_POST = API + "/update_accesibility_settings?";

}
