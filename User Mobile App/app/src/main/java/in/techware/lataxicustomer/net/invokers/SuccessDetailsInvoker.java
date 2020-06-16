package in.techware.lataxicustomer.net.invokers;


import org.json.JSONObject;

import java.util.HashMap;

import in.techware.lataxicustomer.model.SuccessBean;
import in.techware.lataxicustomer.net.ServiceNames;
import in.techware.lataxicustomer.net.WebConnector;
import in.techware.lataxicustomer.net.parsers.SuccessDetailsParser;
import in.techware.lataxicustomer.net.utils.WSConstants;

public class SuccessDetailsInvoker extends BaseInvoker{

    public SuccessDetailsInvoker() {
        super();
    }

    public SuccessDetailsInvoker(HashMap<String, String> urlParams,
                           JSONObject postData) {
        super(urlParams, postData);
    }

    public SuccessBean invokeSuccessDetailsWS() {

        WebConnector webConnector;

        webConnector = new WebConnector(new StringBuilder(ServiceNames.TRIP_COMPLETION_DETAILS), WSConstants.PROTOCOL_HTTP, urlParams, null);

        //webConnector= new WebConnector(new StringBuilder(ServiceNames.MODELS), WSConstants.PROTOCOL_HTTP, null);
//    String wsResponseString=webConnector.connectToPOST_service();
        String wsResponseString = webConnector.connectToGET_service(true);
        System.out.println(">>>>>>>>>>> response: " + wsResponseString);
        SuccessBean successBean = null;
        if (wsResponseString.equals("")) {
            /*registerBean=new RegisterBean();
			registerBean.setWebError(true);*/
            return successBean = null;
        } else {
            successBean = new SuccessBean();
            SuccessDetailsParser successDetailsParser = new SuccessDetailsParser();
            successBean = successDetailsParser.parseSuccessDetailsResponse(wsResponseString);
            return successBean;
        }
    }
}
