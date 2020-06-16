package in.techware.lataxidriverapp.net.invokers;


import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import in.techware.lataxidriverapp.model.BasicBean;
import in.techware.lataxidriverapp.net.ServiceNames;
import in.techware.lataxidriverapp.net.WebConnector;
import in.techware.lataxidriverapp.net.parsers.BasicParser;
import in.techware.lataxidriverapp.net.utils.WSConstants;

public class ProfilePhotoUploadInvoker extends BaseInvoker {

    public ProfilePhotoUploadInvoker() {
        super();
    }

    public ProfilePhotoUploadInvoker(HashMap<String, String> urlParams,
                                     JSONObject postData) {
        super(urlParams, postData);

    }

    public BasicBean invokeProfilePhotoUploadWS(ArrayList<String> fileList) {

        System.out.println("POSTDATA>>>>>>>" + postData);

        WebConnector webConnector;

        webConnector = new WebConnector(new StringBuilder(ServiceNames.UPLOAD_PROFILE_PHOTO),
                WSConstants.PROTOCOL_HTTP, null, postData, fileList);

        String wsResponseString = webConnector.connectToMULTIPART_POST_service("profile_photo");

        System.out.println(">>>>>>>>>>> response: " + wsResponseString);
        BasicBean basicBean = null;
        if (wsResponseString.equals("")) {

            return basicBean = null;
        } else {
            basicBean = new BasicBean();
            BasicParser basicParser = new BasicParser();
            basicBean = basicParser.parseBasicResponse(wsResponseString);
            return basicBean;
        }
    }
}
