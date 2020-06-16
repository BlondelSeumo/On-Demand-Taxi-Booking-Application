package `in`.techware.lataxidriverapp.model

/**
 * Created by Jemsheer K D on 03 December, 2016.
 * Package in.techware.lataxidriver.model
 * Project LaTaxi
 */

open class BasicBean : BaseBean() {


    /* private ArrayList<CountryBean> countries;
    private ArrayList<StateBean> states;
    private ArrayList<DistrictBean> cities;
    private ArrayList<CourtBean> courts;*/

    var requestID: String= ""
    var tripID: String= ""
    var tripStatus: Int = 0
    var otpCode: String= ""
    var phone: String= ""

    var countryID: Int = 0
    var stateID: Int = 0
    var districtID: Int = 0

    var isDriverOnline: Boolean = false
    var isPhoneAvailable: Boolean = false
    /*
    public ArrayList<CourtBean> getCourts() {
        return courts;
    }

    public void setCourts(List<CourtBean> courts) {
        this.courts = courts;
    }

    public ArrayList<CountryBean> getCountries() {
        return countries;
    }

    public void setCountries(List<CountryBean> countries) {
        this.countries = countries;
    }

    public ArrayList<StateBean> getStates() {
        return states;
    }

    public void setStates(List<StateBean> states) {
        this.states = states;
    }

    public ArrayList<DistrictBean> getDistricts() {
        return cities;
    }

    public void setDistricts(List<DistrictBean> cities) {
        this.cities = cities;
    }*/
}
