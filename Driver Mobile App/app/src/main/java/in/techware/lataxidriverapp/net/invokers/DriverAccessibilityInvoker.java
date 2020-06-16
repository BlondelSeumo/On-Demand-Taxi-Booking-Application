package in.techware.lataxidriverapp.net.invokers;

import org.json.JSONObject;

import java.util.HashMap;

import in.techware.lataxidriverapp.model.AccessibilityBean;
import in.techware.lataxidriverapp.net.ServiceNames;
import in.techware.lataxidriverapp.net.WebConnector;
import in.techware.lataxidriverapp.net.parsers.DriverAccessibilityParser;
import in.techware.lataxidriverapp.net.utils.WSConstants;

public class DriverAccessibilityInvoker extends BaseInvoker {

    public DriverAccessibilityInvoker() {
        super();
    }

    public DriverAccessibilityInvoker(HashMap<String, String> urlParams,
                                      JSONObject postData) {
        super(urlParams, postData);
    }

    public AccessibilityBean invokeDriverAccessibilityInvokerWS() {

        WebConnector webConnector;

        webConnector = new WebConnector(new StringBuilder(ServiceNames.DRIVER_ACCESSIBILITY_FETCH), WSConstants.PROTOCOL_HTTP, urlParams, null);

        //webConnector= new WebConnector(new StringBuilder(ServiceNames.MODELS), WSConstants.PROTOCOL_HTTP, null);
//    String wsResponseString=webConnector.connectToPOST_service();
        String wsResponseString = webConnector.connectToGET_service();
        System.out.println(">>>>>>>>>>> response: " + wsResponseString);
        AccessibilityBean accessibilityBean = null;
        if (wsResponseString.equals("")) {
            /*registerBean=new RegisterBean();
            registerBean.setWebError(true);*/
            return accessibilityBean = null;
        } else {
            accessibilityBean = new AccessibilityBean();
            DriverAccessibilityParser driverAccessibilityParser = new DriverAccessibilityParser();
            accessibilityBean = driverAccessibilityParser.parseDriverAccessibilityResponse(wsResponseString);
            return accessibilityBean;
        }
    }
}
