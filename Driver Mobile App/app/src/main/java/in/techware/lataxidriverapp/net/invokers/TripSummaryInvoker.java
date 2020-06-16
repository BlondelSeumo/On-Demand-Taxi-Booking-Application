package in.techware.lataxidriverapp.net.invokers;


import org.json.JSONObject;

import java.util.HashMap;

import in.techware.lataxidriverapp.model.TripSummaryBean;
import in.techware.lataxidriverapp.net.ServiceNames;
import in.techware.lataxidriverapp.net.WebConnector;
import in.techware.lataxidriverapp.net.parsers.TripSummaryParser;
import in.techware.lataxidriverapp.net.utils.WSConstants;

public class TripSummaryInvoker extends BaseInvoker {

    public TripSummaryInvoker() {
        super();
    }

    public TripSummaryInvoker(HashMap<String, String> urlParams,
                              JSONObject postData) {
        super(urlParams, postData);
    }

    public TripSummaryBean invokeTripSummaryWS() {

        WebConnector webConnector;

        webConnector = new WebConnector(new StringBuilder(ServiceNames.TRIP_SUMMARY), WSConstants.PROTOCOL_HTTP, urlParams, null);

        //webConnector= new WebConnector(new StringBuilder(ServiceNames.MODELS), WSConstants.PROTOCOL_HTTP, null);
//    String wsResponseString=webConnector.connectToPOST_service();
        String wsResponseString = webConnector.connectToGET_service();
        System.out.println(">>>>>>>>>>> response: " + wsResponseString);
        TripSummaryBean tripSummaryBean = null;
        if (wsResponseString.equals("")) {
            /*registerBean=new RegisterBean();
            registerBean.setWebError(true);*/
            return tripSummaryBean = null;
        } else {
            tripSummaryBean = new TripSummaryBean();
            TripSummaryParser tripSummaryParser = new TripSummaryParser();
            tripSummaryBean = tripSummaryParser.parseTripSummaryResponse(wsResponseString);
            return tripSummaryBean;

        }
    }
}
