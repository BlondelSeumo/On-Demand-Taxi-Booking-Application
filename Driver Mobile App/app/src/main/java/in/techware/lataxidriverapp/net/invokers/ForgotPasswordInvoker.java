package in.techware.lataxidriverapp.net.invokers;

import org.json.JSONObject;

import java.util.HashMap;

import in.techware.lataxidriverapp.model.BasicBean;
import in.techware.lataxidriverapp.net.ServiceNames;
import in.techware.lataxidriverapp.net.WebConnector;
import in.techware.lataxidriverapp.net.parsers.BasicParser;
import in.techware.lataxidriverapp.net.utils.WSConstants;

/**
 * Created by Jemsheer K D on 27 December, 2016.
 * Package in.techware.lataxidriver.net.invokers
 * Project LaTaxiDriver
 */

public class ForgotPasswordInvoker extends BaseInvoker {

    public ForgotPasswordInvoker() {
        super();
    }

    public ForgotPasswordInvoker(HashMap<String, String> urlParams,
                                 JSONObject postData) {
        super(urlParams, postData);
    }

    public BasicBean invokeForgotPasswordWS() {

        System.out.println("POSTDATA>>>>>>>" + postData);

        WebConnector webConnector;

        webConnector = new WebConnector(new StringBuilder(ServiceNames.FORGOT_PASSWORD), WSConstants.PROTOCOL_HTTP, null, postData);

        //		webConnector= new WebConnector(new StringBuilder(ServiceNames.AUTH_EMAIL), WSConstants.PROTOCOL_HTTP, postData,null);
        //webConnector= new WebConnector(new StringBuilder(ServiceNames.MODELS), WSConstants.PROTOCOL_HTTP, null);
        String wsResponseString = webConnector.connectToPOST_service();
        //	String wsResponseString=webConnector.connectToGET_service();
        System.out.println(">>>>>>>>>>> response: " + wsResponseString);
        BasicBean basicBean = null;
        if (wsResponseString.equals("")) {
            /*registerBean=new RegisterBean();
            registerBean.setWebError(true);*/
            return basicBean = null;
        } else {
            basicBean = new BasicBean();
            BasicParser basicParser = new BasicParser();
            basicBean = basicParser.parseBasicResponse(wsResponseString);
            return basicBean;
        }
    }
}
