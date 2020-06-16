package in.techware.lataxidriverapp.net.invokers;

import org.json.JSONObject;

import java.util.HashMap;

import in.techware.lataxidriverapp.model.TripBean;
import in.techware.lataxidriverapp.net.ServiceNames;
import in.techware.lataxidriverapp.net.WebConnector;
import in.techware.lataxidriverapp.net.parsers.TripDetailsParser;
import in.techware.lataxidriverapp.net.utils.WSConstants;

/**
 * Created by Jemsheer K D on 03 July, 2017.
 * Package in.techware.lataxidriver.net.invokers
 * Project LaTaxiDriver
 */

public class TripDetailsInvoker extends BaseInvoker {

    public TripDetailsInvoker() {
        super();
    }

    public TripDetailsInvoker(HashMap<String, String> urlParams,
                              JSONObject postData) {
        super(urlParams, postData);
    }

    public TripBean invokeTripDetailsWS() {

        WebConnector webConnector;

        webConnector = new WebConnector(new StringBuilder(ServiceNames.TRIP_DETAILS), WSConstants.PROTOCOL_HTTP, urlParams, null);

        //webConnector= new WebConnector(new StringBuilder(ServiceNames.MODELS), WSConstants.PROTOCOL_HTTP, null);
//    String wsResponseString=webConnector.connectToPOST_service();
        String wsResponseString = webConnector.connectToGET_service();
        System.out.println(">>>>>>>>>>> response: " + wsResponseString);
        TripBean tripBean = null;
        if (wsResponseString.equals("")) {
            /*registerBean=new RegisterBean();
            registerBean.setWebError(true);*/
            return tripBean = null;
        } else {
            tripBean = new TripBean();
            TripDetailsParser tripDetailsParser = new TripDetailsParser();
            tripBean = tripDetailsParser.parseTripDetailsResponse(wsResponseString);
            return tripBean;
        }
    }
}
