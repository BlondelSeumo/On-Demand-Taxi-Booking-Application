package in.techware.lataxicustomer.net.invokers;


import org.json.JSONObject;

import java.util.HashMap;

import in.techware.lataxicustomer.model.DriverRatingBean;
import in.techware.lataxicustomer.net.ServiceNames;
import in.techware.lataxicustomer.net.WebConnector;
import in.techware.lataxicustomer.net.parsers.DriverRatingParser;
import in.techware.lataxicustomer.net.utils.WSConstants;

public class DriverRatingInvoker extends BaseInvoker{

    public DriverRatingInvoker() {
        super();
    }

    public DriverRatingInvoker(HashMap<String, String> urlParams,
                               JSONObject postData) {
        super(urlParams, postData);
    }

    public DriverRatingBean invokeDriverRatingWS() {

        System.out.println("POSTDATA>>>>>>>" + postData);

        WebConnector webConnector;

        webConnector = new WebConnector(new StringBuilder(ServiceNames.DRIVER_RATING), WSConstants.PROTOCOL_HTTP, null, postData);

        String wsResponseString = webConnector.connectToPOST_service();

        System.out.println(">>>>>>>>>>> response: " + wsResponseString);
        DriverRatingBean driverRatingBean = null;
        if (wsResponseString.equals("")) {

            return driverRatingBean = null;

        } else {
            driverRatingBean = new DriverRatingBean();
            DriverRatingParser driverRatingParser = new DriverRatingParser();
            driverRatingBean = driverRatingParser.parseDriverRatingResponse(wsResponseString);
            return driverRatingBean;
        }
    }
}
