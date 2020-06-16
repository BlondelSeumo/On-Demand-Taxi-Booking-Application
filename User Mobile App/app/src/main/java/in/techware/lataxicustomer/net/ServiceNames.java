package in.techware.lataxicustomer.net;


public class ServiceNames {

    /*Set BASE URL here*/
    private static final String PRODUCTION_API = "http://techlabz.in";
   

    /* Set API VERSION here*/
    public static final String API_VERSION = "/lataxi/Webservices";
 

    /*Set UPLOAD PATH. DO NOT CHANGE THIS UNLESS YOU KNOW WHAT YOU ARE DOING.*/
    public static final String PATH_UPLOADS = "/lataxi/";
  

    /*Set API URL here*/
    private static final String API = PRODUCTION_API + API_VERSION;

    /*Set IMAGE UPLOAD URL here.  DO NOT CHANGE THIS UNLESS YOU KNOW WHAT YOU ARE DOING.*/
    public static final String API_UPLOADS = PRODUCTION_API + PATH_UPLOADS;

    /*END POINTS*/

    public static final String LOCATION_NAME = "https://maps.googleapis.com/maps/api/geocode/json?";
    public static final String POLY_POINTS = "https://maps.googleapis.com/maps/api/directions/json?";

    public static final String DUMMY = API + "/dummy?";

    public static final String APP_STATUS = API + "/app_status?";

    public static final String PHONE_NUMBER_AVAILABILITY = API + "/mobile_number_availability?";
    public static final String OTP_SEND = API + "/login?";
    public static final String OTP_RESEND_CODE = API + "/otpresendcode?";
    public static final String USER_REGISTRATION = API + "/user_registration?";
    public static final String USER_LOGIN = API + "/do_login?";

    public static final String NEW_PASSWORD = API + "/forgot_password?";
    public static final String UPDATE_FCM_TOKEN = API + "/save_fcmtoken?";
    public static final String EDIT_PROFILE = API + "/edit_user?";

    public static final String USER_INFO = API + "/user_details?";
    public static final String LOCATION_SAVE = API + "/save_location?";


    public static final String SAVED_LOCATION = API + "/saved_location?";
    public static final String CAR_TYPES = API + "/cars?";
    public static final String CAR_AVAILABILITY = API + "/car_availability?";
    public static final String FARE_INFO = API + "/fare_calculate?";

//    public static final String CAR_LIST = "http://demo8142432.mockable.io/carlist?";
    public static final String REQUEST_RIDE = API + "/request_ride?";
    public static final String REQUEST_CANCEL = API + "/request_cancelled?";
    public static final String REQUEST_TRIGGERING = API + "/request_trigger?";
    public static final String REQUEST_STATUS = API + "/request_status?";

    public static final String TRIP_CANCELLATION = API + "/trip_cancellation?";
    public static final String RECENT_SEARCHES = API + "/otpresendcode?";


    public static final String SEARCH_RESULTS = API + "/otpresendcode?";
    public static final String TRIP_LIST = API + "/trip_list?";
    public static final String TRIP_DETAILS = API + "/trip_details?";

    public static final String TRIP_COMPLETION_DETAILS = API + "/trip_completiondetails?";
    public static final String DRIVER_RATING = API + "/driver_rating?";

    public static final String TRIP_FEEDBACK = API + "/tripfeedback?";
    public static final String PROMO_CODE = "http://demo8142432.mockable.io/promocode?";

    public static final String FREE_RIDES = API + "/save_promocode?";

}
