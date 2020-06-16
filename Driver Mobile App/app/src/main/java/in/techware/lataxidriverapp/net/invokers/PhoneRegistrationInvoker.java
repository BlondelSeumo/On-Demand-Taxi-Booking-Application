package in.techware.lataxidriverapp.net.invokers;


import org.json.JSONObject;

import java.util.HashMap;

import in.techware.lataxidriverapp.model.AuthBean;
import in.techware.lataxidriverapp.net.ServiceNames;
import in.techware.lataxidriverapp.net.WebConnector;
import in.techware.lataxidriverapp.net.parsers.PhoneRegisrationParser;
import in.techware.lataxidriverapp.net.utils.WSConstants;

public class PhoneRegistrationInvoker extends BaseInvoker {

    public PhoneRegistrationInvoker() {
        super();
    }

    public PhoneRegistrationInvoker(HashMap<String, String> urlParams,
                                    JSONObject postData) {
        super(urlParams, postData);
    }

    public AuthBean invokePhoneRegistrationWS() {

        System.out.println("POSTDATA>>>>>>>" + postData);

        WebConnector webConnector;

        webConnector = new WebConnector(new StringBuilder(ServiceNames.PHONE_REGISTRATION), WSConstants.PROTOCOL_HTTP, null, postData);

        String wsResponseString = webConnector.connectToPOST_service();

        System.out.println(">>>>>>>>>>> response: " + wsResponseString);
        AuthBean authBean = null;
        if (wsResponseString.equals("")) {

            return authBean = null;
        } else {
            authBean = new AuthBean();
            PhoneRegisrationParser phoneRegisrationParser = new PhoneRegisrationParser();
            authBean = phoneRegisrationParser.parsePhoneRegistrationResponse(wsResponseString);
            return authBean;
        }
    }
}
