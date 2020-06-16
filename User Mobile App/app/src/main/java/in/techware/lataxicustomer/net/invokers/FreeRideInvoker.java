package in.techware.lataxicustomer.net.invokers;


import org.json.JSONObject;

import java.util.HashMap;

import in.techware.lataxicustomer.model.FreeRideBean;
import in.techware.lataxicustomer.net.ServiceNames;
import in.techware.lataxicustomer.net.WebConnector;
import in.techware.lataxicustomer.net.parsers.FreeRideParser;
import in.techware.lataxicustomer.net.utils.WSConstants;

public class FreeRideInvoker extends BaseInvoker {

    public FreeRideInvoker() {
        super();
    }

    public FreeRideInvoker(HashMap<String, String> urlParams,
                           JSONObject postData) {
        super(urlParams, postData);
    }

    public FreeRideBean invokeFreeRideWS() {

        System.out.println("POSTDATA>>>>>>>" + postData);

        WebConnector webConnector;

        webConnector = new WebConnector(new StringBuilder(ServiceNames.FREE_RIDES), WSConstants.PROTOCOL_HTTP, null, postData);

        //		webConnector= new WebConnector(new StringBuilder(ServiceNames.AUTH_EMAIL), WSConstants.PROTOCOL_HTTP, postData,null);
        //webConnector= new WebConnector(new StringBuilder(ServiceNames.MODELS), WSConstants.PROTOCOL_HTTP, null);
        String wsResponseString = webConnector.connectToPOST_service();
        //	String wsResponseString=webConnector.connectToGET_service(true);
        System.out.println(">>>>>>>>>>> response: " + wsResponseString);
        FreeRideBean freeRideBean = null;
        if (wsResponseString.equals("")) {
            return freeRideBean = null;

        } else {
            freeRideBean = new FreeRideBean();
            FreeRideParser freeRideParser = new FreeRideParser();
            freeRideBean = freeRideParser.parseFreeRideResponse(wsResponseString);
            return freeRideBean;
        }
    }
}
