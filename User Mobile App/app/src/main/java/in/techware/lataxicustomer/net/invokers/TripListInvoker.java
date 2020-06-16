package in.techware.lataxicustomer.net.invokers;


import org.json.JSONObject;

import java.util.HashMap;

import in.techware.lataxicustomer.model.TripListBean;
import in.techware.lataxicustomer.net.ServiceNames;
import in.techware.lataxicustomer.net.WebConnector;
import in.techware.lataxicustomer.net.parsers.TripListParser;
import in.techware.lataxicustomer.net.utils.WSConstants;

public class TripListInvoker extends BaseInvoker {

    private TripListBean tripListBean;

    public TripListInvoker() {
        super();
    }

    public TripListInvoker(HashMap<String, String> urlParams,
                           JSONObject postData) {
        super(urlParams, postData);
    }

    public TripListBean invokeTripsWS() {

        WebConnector webConnector;

        webConnector = new WebConnector(new StringBuilder(ServiceNames.TRIP_LIST), WSConstants.PROTOCOL_HTTP, urlParams, null);

        //webConnector= new WebConnector(new StringBuilder(ServiceNames.MODELS), WSConstants.PROTOCOL_HTTP, null);
//    String wsResponseString=webConnector.connectToPOST_service();
        String wsResponseString = webConnector.connectToGET_service(true);
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
