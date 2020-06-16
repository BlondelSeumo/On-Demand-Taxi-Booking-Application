package in.techware.lataxicustomer.net.invokers;


import org.json.JSONObject;

import java.util.HashMap;

import in.techware.lataxicustomer.model.SearchResultsBean;
import in.techware.lataxicustomer.net.ServiceNames;
import in.techware.lataxicustomer.net.WebConnector;
import in.techware.lataxicustomer.net.parsers.SearchResultsParser;
import in.techware.lataxicustomer.net.utils.WSConstants;

public class SearchResultsInvoker extends BaseInvoker {

    public SearchResultsInvoker(HashMap<String, String> urlParams,
                          JSONObject postData) {
        super(urlParams, postData);
    }

    public SearchResultsBean invokeSearchresultsWS() {

        WebConnector webConnector;

        webConnector = new WebConnector(new StringBuilder(ServiceNames.SEARCH_RESULTS), WSConstants.PROTOCOL_HTTP, urlParams, null);

        //webConnector= new WebConnector(new StringBuilder(ServiceNames.MODELS), WSConstants.PROTOCOL_HTTP, null);
//    String wsResponseString=webConnector.connectToPOST_service();
        String wsResponseString = webConnector.connectToGET_service(true);
        System.out.println(">>>>>>>>>>> response: " + wsResponseString);
        SearchResultsBean searchResultsBean = null;
        if (wsResponseString.equals("")) {
            return searchResultsBean = null;
        } else {
            searchResultsBean = new SearchResultsBean();
            SearchResultsParser searchResultsParser = new SearchResultsParser();
            searchResultsBean = searchResultsParser.parseSearchResultsResponse(wsResponseString);
            return searchResultsBean;
        }
    }
}
