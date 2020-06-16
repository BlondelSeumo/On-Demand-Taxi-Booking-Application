package in.techware.lataxicustomer.net.invokers;


import org.json.JSONObject;

import java.util.HashMap;

import in.techware.lataxicustomer.model.RequestBean;
import in.techware.lataxicustomer.net.ServiceNames;
import in.techware.lataxicustomer.net.WebConnector;
import in.techware.lataxicustomer.net.parsers.RequestRideParser;
import in.techware.lataxicustomer.net.utils.WSConstants;

public class RequestRideInvoker extends BaseInvoker {

    public RequestRideInvoker() {
        super();
    }

    public RequestRideInvoker(HashMap<String, String> urlParams,
                              JSONObject postData) {
        super(urlParams, postData);
    }

    public RequestBean invokeRequestRideWS() {

        System.out.println("POSTDATA>>>>>>>" + postData);

        WebConnector webConnector;

        webConnector = new WebConnector(new StringBuilder(ServiceNames.REQUEST_RIDE), WSConstants.PROTOCOL_HTTP, null, postData);

        //		webConnector= new WebConnector(new StringBuilder(ServiceNames.AUTH_EMAIL), WSConstants.PROTOCOL_HTTP, postData,null);
        //webConnector= new WebConnector(new StringBuilder(ServiceNames.MODELS), WSConstants.PROTOCOL_HTTP, null);
        String wsResponseString = webConnector.connectToPOST_service();
        //	String wsResponseString=webConnector.connectToGET_service(true);
        System.out.println(">>>>>>>>>>> response: " + wsResponseString);
        RequestBean requestBean = null;
        if (wsResponseString.equals("")) {
            /*registerBean=new RegisterBean();
            registerBean.setWebError(true);*/
            return requestBean = null;
        } else {
            requestBean = new RequestBean();
            RequestRideParser requestRideParser = new RequestRideParser();
            requestBean = requestRideParser.parseRequestRideResponse(wsResponseString);
            return requestBean;
        }
    }
}
