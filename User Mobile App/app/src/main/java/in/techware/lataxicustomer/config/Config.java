package in.techware.lataxicustomer.config;

import java.util.Locale;

import okhttp3.OkHttpClient;

public class Config {

    private static Config instance;

    private OkHttpClient okHttpClient;

    private String authToken;
    private String currentLatitude;
    private String currentLongitude;
    private String userID;
    private String email;
    private String name;
    private String firstName;
    private String lastName;
    private String phone;
    private String gender;
    private String DOB;
    private boolean isPhoneVerified;
    private String currentLocation;
    private String password;
    private boolean firstTime;
    private String profilePhoto;
    private String mobileNumber;
    private String carID;
    private String driverName;
    private String driverPhoto;
    private String carNumber;
    private String carName;
    private String carsAvailable;
    private String minTime;
    private String minFare;
    private String maxSize;

    private String totalFare;

    private String addHome;
    private String addWork;
    private String homeLatitude;
    private String homeLongitude;
    private String workLatitude;
    private String workLongitude;

    private String locale = Locale.getDefault().getLanguage();

    public static Config getInstance() {
        if (instance == null)
            instance = new Config();

        return instance;
    }

    public static void clear() {
        instance = null;
        instance = new Config();
    }

    private Config() {
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public OkHttpClient getOkHttpClient() {
        return okHttpClient;
    }

    public void setOkHttpClient(OkHttpClient okHttpClient) {
        this.okHttpClient = okHttpClient;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public boolean isPhoneVerified() {
        return isPhoneVerified;
    }

    public void setPhoneVerified(boolean phoneVerified) {
        isPhoneVerified = phoneVerified;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFirstTime(boolean firstTime) {
        this.firstTime = firstTime;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public void setProfilePhoto(String profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    public String getProfilePhoto() {
        return profilePhoto;
    }

    public String getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(String currentLocation) {
        this.currentLocation = currentLocation;
    }

    public String getCurrentLatitude() {
        return currentLatitude;
    }

    public void setCurrentLatitude(String currentLatitude) {
        this.currentLatitude = currentLatitude;
    }

    public String getCurrentLongitude() {
        return currentLongitude;
    }

    public void setCurrentLongitude(String currentLongitude) {
        this.currentLongitude = currentLongitude;
    }

    public double getDCurrentLatitude() {
        try {
            return Double.parseDouble(currentLatitude);
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

    public double getDCurrentLongitude() {
        try {
            return Double.parseDouble(currentLongitude);
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

    public String getPassword() {
        return password;
    }

    public boolean isFirstTime() {
        return firstTime;
    }

    public String getCarID() {
        return carID;
    }

    public void setCarID(String carID) {
        this.carID = carID;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getDriverPhoto() {
        return driverPhoto;
    }

    public void setDriverPhoto(String driverPhoto) {
        this.driverPhoto = driverPhoto;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public String getCarsAvailable() {
        return carsAvailable;
    }

    public void setCarsAvailable(String carsAvailable) {
        this.carsAvailable = carsAvailable;
    }

    public String getMinTime() {
        return minTime;
    }

    public void setMinTime(String minTime) {
        this.minTime = minTime;
    }

    public String getMinFare() {
        return minFare;
    }

    public void setMinFare(String minFare) {
        this.minFare = minFare;
    }

    public String getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(String maxSize) {
        this.maxSize = maxSize;
    }

    public String getTotalFare() {
        return totalFare;
    }

    public void setTotalFare(String totalFare) {
        this.totalFare = totalFare;
    }

    public String getAddHome() {
        return addHome;
    }

    public void setAddHome(String addHome) {
        this.addHome = addHome;
    }

    public String getAddWork() {
        return addWork;
    }

    public void setAddWork(String addWork) {
        this.addWork = addWork;
    }

    public String getHomeLatitude() {
        return homeLatitude;
    }

    public void setHomeLatitude(String homeLatitude) {
        this.homeLatitude = homeLatitude;
    }

    public String getHomeLongitude() {
        return homeLongitude;
    }

    public void setHomeLongitude(String homeLongitude) {
        this.homeLongitude = homeLongitude;
    }

    public String getWorkLatitude() {
        return workLatitude;
    }

    public void setWorkLatitude(String workLatitude) {
        this.workLatitude = workLatitude;
    }

    public String getWorkLongitude() {
        return workLongitude;
    }

    public void setWorkLongitude(String workLongitude) {
        this.workLongitude = workLongitude;
    }
}


