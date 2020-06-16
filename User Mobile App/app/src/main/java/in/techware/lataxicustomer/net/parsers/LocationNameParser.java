package in.techware.lataxicustomer.net.parsers;

import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LocationNameParser {


    public String parseLocationNameResponse(String wsResponseString) {

        String address = "";

        JSONObject jsonObj = null;

        try {
            jsonObj = new JSONObject(wsResponseString);

            String Status = jsonObj.optString("status");
            if (Status.equalsIgnoreCase("OK")) {
                JSONArray Results = jsonObj.getJSONArray("results");
                JSONObject zero = Results.getJSONObject(0);
                JSONArray address_components = zero.getJSONArray("address_components");

                for (int i = 0; i < address_components.length(); i++) {
                    JSONObject zero2 = address_components.getJSONObject(i);
                    String long_name = zero2.optString("long_name");
                    JSONArray mtypes = zero2.getJSONArray("types");
                    String Type = mtypes.optString(0);

                    if (!TextUtils.isEmpty(long_name) || !long_name.equals(null) || long_name.length() > 0 || !long_name.equals("")) {
                        if (Type.equalsIgnoreCase("street_number")) {
                            address = long_name + ", ";
                        } else if (Type.equalsIgnoreCase("route")) {
                            address += long_name + ", ";
                        } else if (Type.equalsIgnoreCase("sublocality")) {
                            address += long_name + ", ";
                        } else if (Type.equalsIgnoreCase("locality")) {
                            // Address2 = Address2 + long_name + ", ";
                            address += long_name + ", ";
                        } /*else if (Type.equalsIgnoreCase("administrative_area_level_2")) {
                            address += long_name + ", ";
                        } else if (Type.equalsIgnoreCase("administrative_area_level_1")) {
                            address += long_name + ", ";
                        } else if (Type.equalsIgnoreCase("country")) {
                            address += long_name;
                        }*/
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

        return address;

    }

}
