package in.techware.lataxidriverapp.net.invokers;

import org.json.JSONObject;

import java.util.HashMap;

import in.techware.lataxidriverapp.model.RatingDetailsBean;
import in.techware.lataxidriverapp.net.ServiceNames;
import in.techware.lataxidriverapp.net.WebConnector;
import in.techware.lataxidriverapp.net.parsers.RatingDetailsParser;
import in.techware.lataxidriverapp.net.utils.WSConstants;

/**
 * Created by Jemsheer K D on 18 May, 2017.
 * Package in.techware.lataxidriver.net.invokers
 * Project LaTaxiDriver
 */

public class RatingDetailsInvoker extends BaseInvoker {

    public RatingDetailsInvoker() {
        super();
    }

    public RatingDetailsInvoker(HashMap<String, String> urlParams,
                                JSONObject postData) {
        super(urlParams, postData);
    }

    public RatingDetailsBean invokeRatingDetailsWS() {

        WebConnector webConnector;

        webConnector = new WebConnector(new StringBuilder(ServiceNames.RATING_DETAILS),
                WSConstants.PROTOCOL_HTTP, urlParams, null);

        //webConnector= new WebConnector(new StringBuilder(ServiceNames.MODELS), WSConstants.PROTOCOL_HTTP, null);
//    String wsResponseString=webConnector.connectToPOST_service();
        String wsResponseString = webConnector.connectToGET_service();
        System.out.println(">>>>>>>>>>> response: " + wsResponseString);
        RatingDetailsBean ratingDetailsBean = null;
        if (wsResponseString.equals("")) {
            /*registerBean=new RegisterBean();
            registerBean.setWebError(true);*/
            return ratingDetailsBean = null;
        } else {
            ratingDetailsBean = new RatingDetailsBean();
            RatingDetailsParser ratingDetailsParser = new RatingDetailsParser();
            ratingDetailsBean = ratingDetailsParser.parseRatingDetailsResponse(wsResponseString);
            return ratingDetailsBean;
        }
    }
}
