package in.techware.lataxicustomer.net.invokers;

import org.json.JSONObject;

import java.util.HashMap;

import in.techware.lataxicustomer.model.FareBean;
import in.techware.lataxicustomer.net.ServiceNames;
import in.techware.lataxicustomer.net.WebConnector;
import in.techware.lataxicustomer.net.parsers.TotalFareParser;
import in.techware.lataxicustomer.net.utils.WSConstants;

public class TotalFareInvoker extends BaseInvoker {

    public TotalFareInvoker(HashMap<String, String> urlParams,
                            JSONObject postData) {
        super(urlParams, postData);
    }

    public FareBean invokeTotalFareWS() {

        WebConnector webConnector;

        webConnector = new WebConnector(new StringBuilder(ServiceNames.FARE_INFO), WSConstants.PROTOCOL_HTTP, urlParams, null);

        //webConnector= new WebConnector(new StringBuilder(ServiceNames.MODELS), WSConstants.PROTOCOL_HTTP, null);
//    String wsResponseString=webConnector.connectToPOST_service();
        String wsResponseString = webConnector.connectToGET_service(true);
        System.out.println(">>>>>>>>>>> response: " + wsResponseString);
        FareBean fareBean = null;
        if (wsResponseString.equals("")) {
            return fareBean = null;

        } else {
            fareBean = new FareBean();
            TotalFareParser totalFareParser = new TotalFareParser();
            fareBean = totalFareParser.parseFareInfoResponse(wsResponseString);
            return fareBean;
        }
    }
}
