package in.techware.lataxicustomer.net.invokers;


import org.json.JSONObject;

import java.util.HashMap;

import in.techware.lataxicustomer.model.RequestBean;
import in.techware.lataxicustomer.net.ServiceNames;
import in.techware.lataxicustomer.net.WebConnector;
import in.techware.lataxicustomer.net.parsers.RequestStatusParser;
import in.techware.lataxicustomer.net.utils.WSConstants;

public class RequestStatusInvoker extends BaseInvoker {

    public RequestStatusInvoker(HashMap<String, String> urlParams,
                            JSONObject postData) {
        super(urlParams, postData);
    }

    public RequestBean invokeRequestStatusWS() {

        WebConnector webConnector;

        webConnector = new WebConnector(new StringBuilder(ServiceNames.REQUEST_RIDE), WSConstants.PROTOCOL_HTTP, urlParams, null);

        //webConnector= new WebConnector(new StringBuilder(ServiceNames.MODELS), WSConstants.PROTOCOL_HTTP, null);
//    String wsResponseString=webConnector.connectToPOST_service();
        String wsResponseString = webConnector.connectToGET_service(true);
        System.out.println(">>>>>>>>>>> response: " + wsResponseString);
        RequestBean requestBean = null;
        if (wsResponseString.equals("")) {
            return requestBean = null;

        } else {
            requestBean = new RequestBean();
            RequestStatusParser requestStatusParser = new RequestStatusParser();
            requestBean = requestStatusParser.parseRequestResponse(wsResponseString);
            return requestBean;
        }
    }
}
