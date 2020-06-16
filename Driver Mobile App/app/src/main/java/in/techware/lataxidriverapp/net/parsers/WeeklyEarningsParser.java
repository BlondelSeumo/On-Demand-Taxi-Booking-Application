package in.techware.lataxidriverapp.net.parsers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import in.techware.lataxidriverapp.model.DailyEarningBean;
import in.techware.lataxidriverapp.model.WeeklyEarningsBean;

/**
 * Created by Jemsheer K D on 16 May, 2017.
 * Package in.techware.lataxidriver.net.parsers
 * Project LaTaxiDriver
 */

public class WeeklyEarningsParser {

    public WeeklyEarningsBean parseWeeklyEarningsResponse(String wsResponseString) {

        WeeklyEarningsBean weeklyEarningsBean = new WeeklyEarningsBean();

        JSONObject jsonObj = null;

        try {
            jsonObj = new JSONObject(wsResponseString);


            if (jsonObj.has("error")) {
                JSONObject errorJSObj;
                try {
                    errorJSObj = jsonObj.getJSONObject("error");
                    if (errorJSObj != null) {
                        if (errorJSObj.has("message")) {
                            weeklyEarningsBean.setErrorMsg(errorJSObj.optString("message"));
                        }
                    }
                } catch (Exception e) {
                }
                weeklyEarningsBean.setStatus("error");
            }
            if (jsonObj.has("status")) {
                weeklyEarningsBean.setStatus(jsonObj.optString("status"));
                if (jsonObj.optString("status").equals("error")) {
                    if (jsonObj.has("message")) {
                        weeklyEarningsBean.setErrorMsg(jsonObj.optString("message"));
                    } else {
                        weeklyEarningsBean.setErrorMsg("Something Went Wrong. Please Try Again Later!!!");
                    }
                }
                if (jsonObj.optString("status").equals("500")) {
                    if (jsonObj.has("error")) {
                        weeklyEarningsBean.setErrorMsg(jsonObj.optString("error"));
                    }
                }
                if (jsonObj.optString("status").equals("404")) {
                    if (jsonObj.has("error")) {
                        weeklyEarningsBean.setErrorMsg(jsonObj.optString("error"));
                    }
                }
                if (jsonObj.has("message")) {
                    weeklyEarningsBean.setErrorMsg(jsonObj.optString("message"));
                }
            }


            if (jsonObj.has("message")) {
                weeklyEarningsBean.setWebMessage(jsonObj.optString("message"));
            }

            if (jsonObj.has("error")) {
                weeklyEarningsBean.setError(jsonObj.optString("error"));
            }

            if (jsonObj.has("message")) {
                weeklyEarningsBean.setErrorMsg(jsonObj.optString("message"));
            }

            if (jsonObj.has("data")) {
                JSONObject dataObj = jsonObj.optJSONObject("data");
                if (dataObj != null) {
                    try {
                        if (dataObj.has("week_of_the_year")) {
                            weeklyEarningsBean.setWeekOfYear(dataObj.optInt("week_of_the_year"));
                        }
                        if (dataObj.has("week_start")) {
                            weeklyEarningsBean.setWeekStart(dataObj.optLong("week_start"));
                        }
                        if (dataObj.has("week_end")) {
                            weeklyEarningsBean.setWeekEnd(dataObj.optLong("week_end"));
                        }
                        if (dataObj.has("year")) {
                            weeklyEarningsBean.setYear(dataObj.optInt("year"));
                        }
                        if (dataObj.has("total_payout")) {
                            weeklyEarningsBean.setTotalPayout(dataObj.optString("total_payout"));
                        }
                        if (dataObj.has("weekly_earnings")) {
                            JSONArray weeklyEarningsArray = dataObj.optJSONArray("weekly_earnings");
                            if (weeklyEarningsArray != null) {
                                ArrayList<DailyEarningBean> list = new ArrayList<>();
                                DailyEarningBean dailyEarningBean;
                                for (int i = 0; i < weeklyEarningsArray.length(); i++) {
                                    JSONObject dailyEarningObj = weeklyEarningsArray.optJSONObject(i);
                                    dailyEarningBean = new DailyEarningBean();
                                    if (dailyEarningObj.has("day")) {
                                        dailyEarningBean.setDay(dailyEarningObj.optInt("day"));
                                    }
                                    if (dailyEarningObj.has("amount")) {
                                        dailyEarningBean.setAmount((float) dailyEarningObj.optDouble("amount"));
                                    }
                                    if (dailyEarningObj.has("amount_payable")) {
                                        dailyEarningBean.setAmountPayable(dailyEarningObj.optString("amount_payable"));
                                    }
                                    if (dailyEarningObj.has("hours_online")) {
                                        dailyEarningBean.setHoursOnline(dailyEarningObj.optInt("hours_online"));
                                    }

                                    list.add(dailyEarningBean);
                                }
                                weeklyEarningsBean.setDailyEarnings(list);
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
        return weeklyEarningsBean;
    }

}
