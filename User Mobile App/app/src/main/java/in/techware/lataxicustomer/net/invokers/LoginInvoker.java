package in.techware.lataxicustomer.net.invokers;

import org.json.JSONObject;

import java.util.HashMap;

import in.techware.lataxicustomer.model.AuthBean;
import in.techware.lataxicustomer.net.ServiceNames;
import in.techware.lataxicustomer.net.WebConnector;
import in.techware.lataxicustomer.net.parsers.LoginParser;
import in.techware.lataxicustomer.net.utils.WSConstants;

public class LoginInvoker extends BaseInvoker {

    public LoginInvoker() {
        super();
    }

    public LoginInvoker(HashMap<String, String> urlParams,
                        JSONObject postData) {
        super(urlParams, postData);
    }

    public AuthBean invokeLoginWS() {

        System.out.println("POSTDATA>>>>>>>" + postData);

        WebConnector webConnector;

        webConnector = new WebConnector(new StringBuilder(ServiceNames.USER_LOGIN), WSConstants.PROTOCOL_HTTP, null, postData);

        //		webConnector= new WebConnector(new StringBuilder(ServiceNames.AUTH_EMAIL), WSConstants.PROTOCOL_HTTP, postData,null);
        //webConnector= new WebConnector(new StringBuilder(ServiceNames.MODELS), WSConstants.PROTOCOL_HTTP, null);
        String wsResponseString = webConnector.connectToPOST_service();
        //	String wsResponseString=webConnector.connectToGET_service(true);
        System.out.println(">>>>>>>>>>> response: " + wsResponseString);
        AuthBean authBean = null;
        if (wsResponseString.equals("")) {
            return authBean = null;
        } else {
            authBean = new AuthBean();
            LoginParser loginParser = new LoginParser();
            authBean = loginParser.parseLoginResponse(wsResponseString);
            return authBean;
        }
    }
}
