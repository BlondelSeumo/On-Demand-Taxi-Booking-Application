package in.techware.lataxidriverapp.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import in.techware.lataxidriverapp.R;
import in.techware.lataxidriverapp.app.App;


/**
 * Created by Jemsheer K D on 20 July, 2018.
 * Package in.techware.dearest.util
 * Project Dearest
 */
public class TimeUtil {


    public static final int DATE_FORMAT_0 = 0;
    public static final int DATE_FORMAT_1 = 1;
    public static final int DATE_FORMAT_2 = 2;
    public static final int DATE_FORMAT_3 = 3;
    public static final int DATE_FORMAT_4 = 4;
    public static final int DATE_FORMAT_5 = 5;
    public static final int DATE_FORMAT_6 = 6;

    public static final int TIME_FORMAT_0 = 0;
    public static final int TIME_FORMAT_1 = 1;

    public static final long HOURS_24 = 86400000;
    public static final long SECOND = 1000;
    public static final long MINUTE = 60000;
    public static final long HOUR = 3600000;

    public static Calendar getUserTime(long GMTTime) {
        Calendar calTemp = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        calTemp.setTimeInMillis(GMTTime * 1000);
        calTemp.setTimeZone(Calendar.getInstance().getTimeZone());
        return calTemp;
    }

    public static Calendar getUserTime(String GMTTime) {
        Calendar calTemp = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-mm-yyyy", App.getCurrentLocale());
            sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
            calTemp.setTime(sdf.parse(GMTTime));
            return calTemp;
        } catch (ParseException ignored) {
        }

        return calTemp;
    }

    public static String getUserTimeFromUnix(String GMTTime) {
        if (GMTTime.equalsIgnoreCase("-62169984000") || GMTTime.equalsIgnoreCase("false") || GMTTime.equalsIgnoreCase("true"))
            return "";
        try {
            Calendar calTemp = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
            calTemp.setTimeInMillis(Long.valueOf(GMTTime) * 1000);
            calTemp.setTimeZone(Calendar.getInstance().getTimeZone());
            GMTTime = new SimpleDateFormat("MMM dd, yyyy hh:mm a", App.getCurrentLocale())
                    .format(new Date(calTemp.getTimeInMillis()));
            return GMTTime;
        } catch (Exception e) {
            //	e.printStackTrace();
            return GMTTime;
        }
    }

    public static String getUserTimeFromChatUnix(String GMTTime) {
        if (GMTTime.equalsIgnoreCase("-62169984000") || GMTTime.equalsIgnoreCase("false") || GMTTime.equalsIgnoreCase("true"))
            return "";
        try {
            Calendar calTemp = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
            calTemp.setTimeInMillis(Long.valueOf(GMTTime));
            calTemp.setTimeZone(Calendar.getInstance().getTimeZone());
            GMTTime = new SimpleDateFormat("MMM dd, yyyy hh:mm a", App.getCurrentLocale())
                    .format(new Date(calTemp.getTimeInMillis()));
            return GMTTime;
        } catch (Exception e) {
            //	e.printStackTrace();
            return GMTTime;
        }
    }

    public static String getUserDateFromUnix(String GMTTime) {
        if (GMTTime.equalsIgnoreCase("-62169984000") || GMTTime.equalsIgnoreCase("false")
                || GMTTime.equalsIgnoreCase("true"))
            return "";
        try {
            Calendar calTemp = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
            calTemp.setTimeInMillis(Long.valueOf(GMTTime) * 1000);
            calTemp.setTimeZone(Calendar.getInstance().getTimeZone());
            GMTTime = new SimpleDateFormat("MMM dd, yyyy", App.getCurrentLocale())
                    .format(new Date(calTemp.getTimeInMillis()));
            return GMTTime;
        } catch (Exception e) {
            //	e.printStackTrace();
            return GMTTime;
        }
    }

    public static String getDateFromUnix(int format, boolean isOriginalGMT, boolean isResultGMT,
                                         long timeInMills, boolean isShorteningNeeded) {
        if (timeInMills <= -62169984000L)
            return "";
        String resultDate = "";
        try {
            Calendar calTemp;
            if (isOriginalGMT) {
                calTemp = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
            } else {
                calTemp = Calendar.getInstance();
            }
            calTemp.setTimeInMillis(timeInMills);

            if (isShorteningNeeded && calTemp.before(Calendar.getInstance()) &&
                    Calendar.getInstance().getTimeInMillis() - calTemp.getTimeInMillis() < HOURS_24) {
                return getTimeDifference(calTemp.getTimeInMillis());
            } else {

                SimpleDateFormat sdf;
                switch (format) {
                    case DATE_FORMAT_0:
                        sdf = new SimpleDateFormat("MMM dd, yyyy", App.getCurrentLocale());
                        sdf.setTimeZone(getTimeZone(isResultGMT));
                        resultDate = sdf.format(new Date(calTemp.getTimeInMillis()));
                        break;
                    case DATE_FORMAT_1:
                        sdf = new SimpleDateFormat("dd/MM/yyyy", App.getCurrentLocale());
                        sdf.setTimeZone(getTimeZone(isResultGMT));
                        resultDate = sdf.format(new Date(calTemp.getTimeInMillis()));
                        break;
                    case DATE_FORMAT_2:
                        sdf = new SimpleDateFormat("dd MMM, yyyy hh:mm a", App.getCurrentLocale());
                        sdf.setTimeZone(getTimeZone(isResultGMT));
                        resultDate = sdf.format(new Date(calTemp.getTimeInMillis()));
                        break;
                    case DATE_FORMAT_3:
                        sdf = new SimpleDateFormat("dd/MM/yyyy, hh:mm a", App.getCurrentLocale());
                        sdf.setTimeZone(getTimeZone(isResultGMT));
                        resultDate = sdf.format(new Date(calTemp.getTimeInMillis()));
                        break;
                    case DATE_FORMAT_4:
                        sdf = new SimpleDateFormat("dd MMM, yyyy", App.getCurrentLocale());
                        sdf.setTimeZone(getTimeZone(isResultGMT));
                        resultDate = sdf.format(new Date(calTemp.getTimeInMillis()));
                        break;

                    case DATE_FORMAT_5:
                        sdf = new SimpleDateFormat("dd MMM, yyyy", App.getCurrentLocale());
                        sdf.setTimeZone(getTimeZone(isResultGMT));
                        resultDate = sdf.format(new Date(calTemp.getTimeInMillis()));
                        break;

                    case DATE_FORMAT_6:
                        sdf = new SimpleDateFormat("dd/MM/yyyy,\nhh:mm a", App.getCurrentLocale());
                        sdf.setTimeZone(getTimeZone(isResultGMT));
                        resultDate = sdf.format(new Date(calTemp.getTimeInMillis()));
                        break;

                    default:
                        sdf = new SimpleDateFormat("MMM dd, yyyy", App.getCurrentLocale());
                        sdf.setTimeZone(getTimeZone(isResultGMT));
                        resultDate = sdf.format(new Date(calTemp.getTimeInMillis()));
                        break;
                }

                return resultDate;
            }
        } catch (Exception e) {
            //	e.printStackTrace();
            return resultDate;
        }
    }

    public static String getTimeFromUnix(int format, boolean isOriginalGMT, boolean isResultGMT, long time) {
        if (time <= -62169984000L)
            return "";
        String resultTime = "";
        try {
            Calendar calTemp;
            if (isOriginalGMT) {
                calTemp = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
            } else {
                calTemp = Calendar.getInstance();
            }
            calTemp.setTimeInMillis(time);

            SimpleDateFormat sdf;
            switch (format) {
                case TIME_FORMAT_0:
                    sdf = new SimpleDateFormat("hh:mm a", App.getCurrentLocale());
                    sdf.setTimeZone(getTimeZone(isResultGMT));
                    resultTime = sdf.format(new Date(calTemp.getTimeInMillis()));
                    break;

                case TIME_FORMAT_1:
                    sdf = new SimpleDateFormat("hh:mm\na", App.getCurrentLocale());
                    sdf.setTimeZone(getTimeZone(isResultGMT));
                    resultTime = sdf.format(new Date(calTemp.getTimeInMillis()));
                    break;

                default:
                    sdf = new SimpleDateFormat("hh:mm a", App.getCurrentLocale());
                    sdf.setTimeZone(getTimeZone(isResultGMT));
                    resultTime = sdf.format(new Date(calTemp.getTimeInMillis()));
                    break;
            }

            return resultTime;
        } catch (Exception e) {
            //	e.printStackTrace();
            return resultTime;
        }
    }

    private static TimeZone getTimeZone(boolean isResultGMT) {
        return isResultGMT ? TimeZone.getTimeZone("UTC") : Calendar.getInstance().getTimeZone();
    }

    public static String getTimeDifference(long time) {

        Calendar calNow = Calendar.getInstance();
        long difference = calNow.getTimeInMillis() - time;

        if (difference < MINUTE) {
            return App.getInstance().getString(R.string.label_1_min_ago);
        } else if (difference < HOUR) {
            return (difference / MINUTE) + AppConstants.SPACE + App.getInstance().getString(R.string.label_min_ago);
        } else if (difference < HOURS_24) {
            return (difference / HOUR) + AppConstants.SPACE + App.getInstance().getString(R.string.label_hour_ago);
        } else {
            return "";
        }
    }

    public static String getMonthName(int month) {
        switch (month) {
            case 1:
                return "January";
            case 2:
                return "February";
            case 3:
                return "March";
            case 4:
                return "April";
            case 5:
                return "May";
            case 6:
                return "June";
            case 7:
                return "July";
            case 8:
                return "August";
            case 9:
                return "September";
            case 10:
                return "October";
            case 11:
                return "November";
            case 12:
                return "December";
            default:
                return "ERROR";
        }
    }

    public static String getDayOFWeek(int dayOfWeek) {

        switch (dayOfWeek) {
            case Calendar.SUNDAY:
                return "Sunday";
            case Calendar.MONDAY:
                return "Monday";
            case Calendar.TUESDAY:
                return "Tuesday";
            case Calendar.WEDNESDAY:
                return "Wednesday";
            case Calendar.THURSDAY:
                return "Thursday";
            case Calendar.FRIDAY:
                return "Friday";
            case Calendar.SATURDAY:
                return "Saturday";
            default:
                return "Error";
        }
    }

    public static String getDayOFWeekShort(int dayOfWeek) {

        switch (dayOfWeek) {
            case Calendar.SUNDAY:
                return "Sun";
            case Calendar.MONDAY:
                return "Mon";
            case Calendar.TUESDAY:
                return "Tue";
            case Calendar.WEDNESDAY:
                return "Wed";
            case Calendar.THURSDAY:
                return "Thu";
            case Calendar.FRIDAY:
                return "Fri";
            case Calendar.SATURDAY:
                return "Sat";
            default:
                return "Error";
        }
    }

    public static Calendar getGMTCal(Calendar localCal) {
        Calendar tempCal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        tempCal.set(Calendar.YEAR, localCal.get(Calendar.YEAR));
        tempCal.set(Calendar.MONTH, localCal.get(Calendar.MONTH));
        tempCal.set(Calendar.DAY_OF_MONTH, localCal.get(Calendar.DAY_OF_MONTH));
        return tempCal;
    }

}
