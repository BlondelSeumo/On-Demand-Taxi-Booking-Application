package in.techware.lataxidriverapp.net.invokers;

import org.json.JSONObject;

import java.util.HashMap;

import in.techware.lataxidriverapp.model.TripListBean;
import in.techware.lataxidriverapp.net.ServiceNames;
import in.techware.lataxidriverapp.net.WebConnector;
import in.techware.lataxidriverapp.net.parsers.TripListParser;
import in.techware.lataxidriverapp.net.utils.WSConstants;

/**
 * Created by Jemsheer K D on 08 May, 2017.
 * Package in.techware.lataxidriver.net.invokers
 * Project LaTaxiDriver
 */

public class TodayTripListInvoker extends BaseInvoker {

    public TodayTripListInvoker() {
        super();
    }

    public TodayTripListInvoker(HashMap<String, String> urlParams,
                                JSONObject postData) {
        super(urlParams, postData);
    }

    public TripListBean invokeTodayTripListWS() {

        WebConnector webConnector;

        webConnector = new WebConnector(new StringBuilder(ServiceNames.TODAY_TRIP_LIST), WSConstants.PROTOCOL_HTTP, urlParams, null);

        //webConnector= new WebConnector(new StringBuilder(ServiceNames.MODELS), WSConstants.PROTOCOL_HTTP, null);
//    String wsResponseString=webConnector.connectToPOST_service();
        String wsResponseString = webConnector.connectToGET_service();
        System.out.println(">>>>>>>>>>> response: " + wsResponseString);
        TripListBean tripListBean = null;
        if (wsResponseString.equals("")) {
            /*registerBean=new RegisterBean();
            registerBean.setWebError(true);*/
            return tripListBean = null;
        } else {
            tripListBean = new TripListBean();
            TripListParser tripListParser = new TripListParser();
            tripListBean = tripListParser.parseTripListResponse(wsResponseString);
            return tripListBean;
        }
    }
}
