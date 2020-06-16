package in.techware.lataxidriverapp.net.invokers;

import org.json.JSONObject;

import java.util.HashMap;

import in.techware.lataxidriverapp.model.DocumentStatusBean;
import in.techware.lataxidriverapp.net.ServiceNames;
import in.techware.lataxidriverapp.net.WebConnector;
import in.techware.lataxidriverapp.net.parsers.DocumentStatusParser;
import in.techware.lataxidriverapp.net.utils.WSConstants;

/**
 * Created by Jemsheer K D on 28 April, 2017.
 * Package in.techware.lataxidriver.net.invokers
 * Project LaTaxiDriver
 */

public class DocumentStatusInvoker extends BaseInvoker {

    public DocumentStatusInvoker() {
        super();
    }

    public DocumentStatusInvoker(HashMap<String, String> urlParams,
                                 JSONObject postData) {
        super(urlParams, postData);
    }

    public DocumentStatusBean invokeDocumentStatusWS() {

        WebConnector webConnector;

        webConnector = new WebConnector(new StringBuilder(ServiceNames.DOCUMENT_STATUS), WSConstants.PROTOCOL_HTTP, urlParams, null);

        //webConnector= new WebConnector(new StringBuilder(ServiceNames.MODELS), WSConstants.PROTOCOL_HTTP, null);
//    String wsResponseString=webConnector.connectToPOST_service();
        String wsResponseString = webConnector.connectToGET_service();
        System.out.println(">>>>>>>>>>> response: " + wsResponseString);
        DocumentStatusBean documentStatusBean = null;
        if (wsResponseString.equals("")) {
            /*registerBean=new RegisterBean();
            registerBean.setWebError(true);*/
            return documentStatusBean = null;
        } else {
            documentStatusBean = new DocumentStatusBean();
            DocumentStatusParser documentStatusParser = new DocumentStatusParser();
            documentStatusBean = documentStatusParser.parseDocumentStatusResponse(wsResponseString);
            return documentStatusBean;
        }
    }
}
