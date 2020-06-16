package in.techware.lataxicustomer.net.invokers;

import org.json.JSONObject;

import java.util.HashMap;

import in.techware.lataxicustomer.model.BasicBean;
import in.techware.lataxicustomer.net.ServiceNames;
import in.techware.lataxicustomer.net.WebConnector;
import in.techware.lataxicustomer.net.parsers.OTPResendCodeParser;
import in.techware.lataxicustomer.net.utils.WSConstants;

public class OTPResendCodeInvoker extends BaseInvoker {

    public OTPResendCodeInvoker() {
        super();
    }

    public OTPResendCodeInvoker(HashMap<String, String> urlParams,
                               JSONObject postData) {
        super(urlParams, postData);
    }

    public BasicBean invokeRegistrationWS() {

        System.out.println("POSTDATA>>>>>>>" + postData);

        WebConnector webConnector;

        webConnector = new WebConnector(new StringBuilder(ServiceNames.OTP_RESEND_CODE), WSConstants.PROTOCOL_HTTP, null, postData);

        String wsResponseString = webConnector.connectToPOST_service();

        System.out.println(">>>>>>>>>>> response: " + wsResponseString);
        BasicBean basicBean = null;
        if (wsResponseString.equals("")) {

            return basicBean = null;
        } else {
            basicBean = new BasicBean();
            OTPResendCodeParser otpResendCodeParser = new OTPResendCodeParser();
            basicBean = otpResendCodeParser.parseRegistrationResponse(wsResponseString);
            return basicBean;
        }
    }
}
