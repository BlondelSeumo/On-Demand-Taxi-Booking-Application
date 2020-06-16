package in.techware.lataxidriverapp.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.MPPointF;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import in.techware.lataxidriverapp.R;
import in.techware.lataxidriverapp.activity.TripHistoryActivity;
import in.techware.lataxidriverapp.app.App;
import in.techware.lataxidriverapp.listeners.WeeklyEarningsListener;
import in.techware.lataxidriverapp.model.DailyEarningBean;
import in.techware.lataxidriverapp.model.WeeklyEarningsBean;
import in.techware.lataxidriverapp.net.DataManager;
import in.techware.lataxidriverapp.util.AppConstants;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EarningsFragmentListener} interface
 * to handle interaction events.
 */
public class EarningsFragment extends BaseFragment {

    private EarningsFragmentListener mListener;
    private BarChart mChart;

    protected RectF mOnValueSelectedRectF = new RectF();
    private String TAG = "EarFrag";
    private LinearLayout llTripHistory;
    private LinearLayout llPayStatements;
    private ImageButton ibPrevious;
    private ImageButton ibNext;
    private WeeklyEarningsBean weeklyEarningsBean;
    private Calendar cal = Calendar.getInstance();
    private TextView txtTotalPayout;
    private TextView txtWeek;
    private Calendar calToday;
    private View.OnClickListener snackBarRefreshOnClickListener;

    public EarningsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        initBase(inflater, container, savedInstanceState);

        View rootView = inflater.inflate(R.layout.fragment_earnings, null);
        lytContent.addView(rootView);


        initView(rootView);
        initChart(rootView);

        cal = Calendar.getInstance();
        calToday = Calendar.getInstance();
        onRefresh();

        return lytBase;
    }

    public void onRefresh() {
        setProgressScreenVisibility(true, true);
        getData(false);
    }

    private void getData(boolean isSwipeRefreshing) {
        if (getActivity() != null)
            mListener.onSwipeRefreshChange(isSwipeRefreshing);
        if (App.isNetworkAvailable()) {
            fetchWeeklyEarnings();
        } else {
            Snackbar.make(coordinatorLayout, AppConstants.NO_NETWORK_AVAILABLE, Snackbar.LENGTH_INDEFINITE)
                    .setAction(R.string.btn_retry, snackBarRefreshOnClickListener).show();
        }
    }

    private void initView(View rootView) {

        snackBarRefreshOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
//                mVibrator.vibrate(25);
                setProgressScreenVisibility(true, true);
                getData(false);
            }
        };


        llTripHistory = (LinearLayout) rootView.findViewById(R.id.ll_earnings_trip_history);
        llPayStatements = (LinearLayout) rootView.findViewById(R.id.ll_earnings_pay_statements);

        txtTotalPayout = (TextView) rootView.findViewById(R.id.txt_earnings_total_payout);
        txtWeek = (TextView) rootView.findViewById(R.id.txt_earnings_week);

        ibPrevious = (ImageButton) rootView.findViewById(R.id.ib_earnings_previous_week);
        ibNext = (ImageButton) rootView.findViewById(R.id.ib_earnings_next_week);

        ibPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                //mVibrator.vibrate(25);
                cal.add(Calendar.WEEK_OF_YEAR, -1);

                if (calToday.get(Calendar.WEEK_OF_YEAR) == cal.get(Calendar.WEEK_OF_YEAR)) {
                    txtWeek.setText(R.string.sample_week);
                } else {
                    txtWeek.setText("Week " + cal.get(Calendar.WEEK_OF_YEAR) + ", " + cal.get(Calendar.YEAR));
                }

                if (getActivity() != null)
                    mListener.onSwipeRefreshChange(true);
                fetchWeeklyEarnings();
            }
        });

        ibNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                //mVibrator.vibrate(25);
                cal.add(Calendar.WEEK_OF_YEAR, 1);
                if (calToday.get(Calendar.WEEK_OF_YEAR) == cal.get(Calendar.WEEK_OF_YEAR)) {
                    txtWeek.setText(R.string.sample_week);
                } else {
                    txtWeek.setText("Week " + cal.get(Calendar.WEEK_OF_YEAR) + ", " + cal.get(Calendar.YEAR));
                }

                if (getActivity() != null)
                    mListener.onSwipeRefreshChange(true);
                fetchWeeklyEarnings();

            }
        });

        llTripHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                //mVibrator.vibrate(25);

                startActivity(new Intent(getActivity(), TripHistoryActivity.class));
            }
        });

        llPayStatements.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                //mVibrator.vibrate(25);

            }
        });

    }

    private void initChart(View rootView) {
        mChart = (BarChart) rootView.findViewById(R.id.chart_earnings);
        mChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry entry, Highlight highlight) {

                if (entry == null)
                    return;

                RectF bounds = mOnValueSelectedRectF;
                mChart.getBarBounds((BarEntry) entry, bounds);
                MPPointF position = mChart.getPosition(entry, YAxis.AxisDependency.LEFT);

                Log.i("bounds", bounds.toString());
                Log.i("position", position.toString());

                Log.i("x-index",
                        "low: " + mChart.getLowestVisibleX() + ", high: "
                                + mChart.getHighestVisibleX());

                MPPointF.recycleInstance(position);

            }

            @Override
            public void onNothingSelected() {

            }
        });

        mChart.setDrawBarShadow(false);
        mChart.setDrawValueAboveBar(true);

        mChart.getDescription().setEnabled(false);

        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        mChart.setMaxVisibleValueCount(9);

        // scaling can now only be done on x- and y-axis separately
        mChart.setPinchZoom(false);

        mChart.setDrawGridBackground(false);
        // mChart.setDrawYLabels(false);

        IAxisValueFormatter xAxisFormatter = new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axisBase) {

                Log.i(TAG, "getFormattedValue: Value : " + value);
                if (value == -1 || value == 7)
                    return "";
                else
                    return App.getDayOFWeekShort((int) value + 1);
            }
        };

        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTypeface(typeface);
        xAxis.setAxisMaximum(7);
        xAxis.setAxisMinimum(-1);
        xAxis.setDrawGridLines(true);
        xAxis.setLabelCount(7);
        xAxis.setValueFormatter(xAxisFormatter);

        IAxisValueFormatter custom = new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axisBase) {

                int temp = (int) value;
                return String.valueOf(temp);
            }
        };

        mChart.getAxisRight().setEnabled(false);

        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setTypeface(typeface);
        leftAxis.setLabelCount(7, true);
        leftAxis.setValueFormatter(custom);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(15f);
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

     /*   YAxis rightAxis = mChart.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setTypeface(typeface);
        rightAxis.setLabelCount(8, false);
        rightAxis.setValueFormatter(custom);
        rightAxis.setSpaceTop(15f);
        rightAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)*/

        Legend l = mChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setForm(Legend.LegendForm.SQUARE);
        l.setFormSize(8f);
        l.setTextSize(11f);
        l.setXEntrySpace(1f);
        // l.setExtra(ColorTemplate.VORDIPLOM_COLORS, new String[] { "abc",
        // "def", "ghj", "ikl", "mno" });
        // l.setCustom(ColorTemplate.VORDIPLOM_COLORS, new String[] { "abc",
        // "def", "ghj", "ikl", "mno" });

       /* XYMarkerView mv = new XYMarkerView(this, xAxisFormatter);
        mv.setChartView(mChart); // For bounds control
        mChart.setMarker(mv); // Set the marker to the chart*/

//        setData(5000);

    }

    private void fetchWeeklyEarnings() {

        HashMap<String, String> urlParams = new HashMap<>();
        urlParams.put("week_of_year", String.valueOf(cal.get(Calendar.WEEK_OF_YEAR)));
//        urlParams.put("week_start", String.valueOf(cal.getFirstDayOfWeek()));
        urlParams.put("year", String.valueOf(cal.get(Calendar.YEAR)));

        DataManager.fetchWeeklyEarnings(urlParams, new WeeklyEarningsListener() {

            @Override
            public void onLoadCompleted(WeeklyEarningsBean weeklyEarningsBeanWS) {
                Log.i(TAG, "onLoadCompleted: WEEKLY EARNINGS : " + new Gson().toJson(weeklyEarningsBeanWS));
                weeklyEarningsBean = weeklyEarningsBeanWS;
                if (getActivity() != null)
                    populateWeeklyEarnings();
            }

            @Override
            public void onLoadFailed(String error) {
                setProgressScreenVisibility(false, false);
                Snackbar.make(coordinatorLayout, error, Snackbar.LENGTH_INDEFINITE)
                        .setAction(R.string.btn_retry, snackBarRefreshOnClickListener).show();
                populateWeeklyEarnings();
                if (getActivity() != null) {
                    mListener.onSwipeRefreshChange(false);
                }
            }
        });
    }

    private void populateWeeklyEarnings() {

        if (weeklyEarningsBean != null) {
            ArrayList<BarEntry> yVals1 = new ArrayList<>();

            int count = 0;
            for (DailyEarningBean bean : weeklyEarningsBean.getDailyEarnings()) {

                float val = bean.getAmount();
                if (val < 25) {
                    yVals1.add(new BarEntry(count++, val, getResources().getDrawable(R.drawable.preset)));
                } else {
                    yVals1.add(new BarEntry(count++, val));
                }
            }

            BarDataSet set1;

            if (mChart.getData() != null &&
                    mChart.getData().getDataSetCount() > 0) {
                set1 = (BarDataSet) mChart.getData().getDataSetByIndex(0);
                set1.setValues(yVals1);
                mChart.getData().notifyDataChanged();
                mChart.notifyDataSetChanged();
                mChart.invalidate();
            } else {
                set1 = new BarDataSet(yVals1, getString(R.string.label_daily_earnings));

                set1.setDrawIcons(true);

                set1.setColor(R.color.gray_1);
                set1.setHighLightColor(R.color.colorPrimary);

                ArrayList<IBarDataSet> dataSets = new ArrayList<>();
                dataSets.add(set1);

                BarData data = new BarData(dataSets);
                data.setValueTextSize(8f);
                data.setValueTypeface(typeface);
                data.setBarWidth(.95f);

                mChart.setData(data);
                mChart.setFitBars(true);

                mChart.invalidate();
            }

            txtTotalPayout.setText(weeklyEarningsBean.getTotalPayout());
        } else {
            txtTotalPayout.setText(R.string.label_not_available);
        }

        setProgressScreenVisibility(false, false);
        if (getActivity() != null)
            mListener.onSwipeRefreshChange(false);

    }


    /*private void setData(float range) {

        ArrayList<BarEntry> yVals1 = new ArrayList<>();

        for (int i = 0; i < 7; i++) {
            float mult = (range + 1);
            float val = (float) (Math.random() * mult);

            if (Math.random() * 100 < 25) {
                yVals1.add(new BarEntry(i, val, getResources().getDrawable(R.drawable.preset)));
            } else {
                yVals1.add(new BarEntry(i, val));
            }
        }

        BarDataSet set1;

        if (mChart.getData() != null &&
                mChart.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) mChart.getData().getDataSetByIndex(0);
            set1.setValues(yVals1);
            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();
        } else {
            set1 = new BarDataSet(yVals1, "The year 2017");

            set1.setDrawIcons(true);

            set1.setColor(R.color.gray_1);
            set1.setHighLightColor(R.color.colorPrimary);

            ArrayList<IBarDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1);

            BarData data = new BarData(dataSets);
            data.setValueTextSize(8f);
            data.setValueTypeface(typeface);
            data.setBarWidth(.95f);

            mChart.setData(data);
            mChart.setFitBars(true);
        }
    }*/


    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            setListener(activity);
        }
    }

    private void setListener(Context context) {
        if (getActivity() instanceof EarningsFragmentListener) {
            mListener = (EarningsFragmentListener) getActivity();
        } else if (getParentFragment() instanceof EarningsFragmentListener) {
            mListener = (EarningsFragmentListener) getParentFragment();
        } else if (context instanceof EarningsFragmentListener) {
            mListener = (EarningsFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement EarningsFragmentListener");
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (getActivity() instanceof EarningsFragmentListener) {
            mListener = (EarningsFragmentListener) getActivity();
        } else if (getParentFragment() instanceof EarningsFragmentListener) {
            mListener = (EarningsFragmentListener) getParentFragment();
        } else if (context instanceof EarningsFragmentListener) {
            mListener = (EarningsFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement EarningsFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface EarningsFragmentListener {

        void onSwipeRefreshChange(boolean isRefreshing);

        void onSwipeEnabled(boolean isEnabled);

    }
}
