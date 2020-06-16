package in.techware.lataxidriverapp.net.parsers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import in.techware.lataxidriverapp.app.App;
import in.techware.lataxidriverapp.model.DocumentBean;
import in.techware.lataxidriverapp.model.DocumentStatusBean;

/**
 * Created by Jemsheer K D on 28 April, 2017.
 * Package in.techware.lataxidriver.net.parsers
 * Project LaTaxiDriver
 */

public class DocumentStatusParser {

    public DocumentStatusBean parseDocumentStatusResponse(String wsResponseString) {

        DocumentStatusBean documentStatusBean = new DocumentStatusBean();

        JSONObject jsonObj = null;

        try {
            jsonObj = new JSONObject(wsResponseString);


            if (jsonObj.has("error")) {
                JSONObject errorJSObj;
                try {
                    errorJSObj = jsonObj.getJSONObject("error");
                    if (errorJSObj != null) {
                        if (errorJSObj.has("message")) {
                            documentStatusBean.setErrorMsg(errorJSObj.optString("message"));
                        }
                    }
                } catch (Exception e) {
                }
                documentStatusBean.setStatus("error");
            }
            if (jsonObj.has("status")) {
                documentStatusBean.setStatus(jsonObj.optString("status"));
                if (jsonObj.optString("status").equals("error")) {
                    if (jsonObj.has("message")) {
                        documentStatusBean.setErrorMsg(jsonObj.optString("message"));
                    } else {
                        documentStatusBean.setErrorMsg("Something Went Wrong. Please Try Again Later!!!");
                    }
                }
                if (jsonObj.optString("status").equals("500")) {
                    if (jsonObj.has("error")) {
                        documentStatusBean.setErrorMsg(jsonObj.optString("error"));
                    }
                }
                if (jsonObj.optString("status").equals("404")) {
                    if (jsonObj.has("error")) {
                        documentStatusBean.setErrorMsg(jsonObj.optString("error"));
                    }
                }
                if (jsonObj.has("message")) {
                    documentStatusBean.setErrorMsg(jsonObj.optString("message"));
                }
            }


            if (jsonObj.has("message")) {
                documentStatusBean.setWebMessage(jsonObj.optString("message"));
            }

            if (jsonObj.has("error")) {
                documentStatusBean.setError(jsonObj.optString("error"));
            }

            if (jsonObj.has("message")) {
                documentStatusBean.setErrorMsg(jsonObj.optString("message"));
            }

            if (jsonObj.has("data")) {
                JSONObject dataObj = jsonObj.optJSONObject("data");
                if (dataObj != null) {
                    try {
                        if (dataObj.has("documents")) {
                            JSONArray documentArray = dataObj.optJSONArray("documents");
                            if (documentArray != null) {
                                ArrayList<DocumentBean> list = new ArrayList<>();
                                DocumentBean documentBean;
                                for (int i = 0; i < documentArray.length(); i++) {
                                    JSONObject documentObj = documentArray.optJSONObject(i);
                                    documentBean = new DocumentBean();
                                    if (documentObj.has("id")) {
                                        documentBean.setId(documentObj.optString("id"));
                                    }
                                    if (documentObj.has("type")) {
                                        documentBean.setType(documentObj.optInt("type"));
                                    }
                                    if (documentObj.has("name")) {
                                        documentBean.setName(documentObj.optString("name"));
                                    }
                                    if (documentObj.has("is_uploaded")) {
                                        documentBean.setUploaded(documentObj.optBoolean("is_uploaded"));
                                    }
                                    if (documentObj.has("document_status")) {
                                        documentBean.setDocumentStatus(documentObj.optInt("document_status"));
                                    }
                                    if (documentObj.has("document_url")) {
                                        documentBean.setDocumentURL(App.getImagePath(documentObj.optString("document_url")));
                                    }
                                    list.add(documentBean);
                                }
                                documentStatusBean.setDocuments(list);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return documentStatusBean;
    }

}
