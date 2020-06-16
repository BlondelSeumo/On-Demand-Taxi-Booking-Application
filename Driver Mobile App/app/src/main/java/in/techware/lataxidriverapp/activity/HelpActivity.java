package in.techware.lataxidriverapp.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.text.Html;
import android.view.HapticFeedbackConstants;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import in.techware.lataxidriverapp.R;
import in.techware.lataxidriverapp.app.App;
import in.techware.lataxidriverapp.listeners.BasicListener;
import in.techware.lataxidriverapp.listeners.HelpListener;
import in.techware.lataxidriverapp.model.BasicBean;
import in.techware.lataxidriverapp.model.HelpBean;
import in.techware.lataxidriverapp.net.DataManager;
import in.techware.lataxidriverapp.util.AppConstants;

public class HelpActivity extends BaseAppCompatNoDrawerActivity {

    private View.OnClickListener snackBarRefreshOnClickListener;
    private HelpBean helpBean;
    private HelpBean tempHelpBean;
    private TextView txtTitle;
    private TextView txtContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        if (getIntent().hasExtra("bean"))
            tempHelpBean = (HelpBean) getIntent().getSerializableExtra("bean");
        else {
            Toast.makeText(getApplicationContext(), R.string.message_something_went_wrong, Toast.LENGTH_LONG).show();
            finish();
        }
        initViews();


        getSupportActionBar().setTitle(R.string.label_help);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

    }

    @Override
    protected void onResume() {
        super.onResume();

        if (helpBean == null) {
            setProgressScreenVisibility(true, true);
            getData(false);
        } else {
            getData(true);
        }

    }

    private void getData(boolean isSwipeRefreshing) {
        swipeView.setRefreshing(isSwipeRefreshing);
        if (App.isNetworkAvailable()) {
            fetchHelp();
        } else {
            Snackbar.make(coordinatorLayout, AppConstants.NO_NETWORK_AVAILABLE, Snackbar.LENGTH_INDEFINITE)
                    .setAction(R.string.btn_retry, snackBarRefreshOnClickListener).show();
        }
    }

    private void initViews() {
        snackBarRefreshOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
//                mVibrator.vibrate(25);
                setProgressScreenVisibility(true, true);
                getData(false);
            }
        };


        txtTitle = (TextView) findViewById(R.id.txt_help_title);
        txtContent = (TextView) findViewById(R.id.txt_help_content);


    }

    private void fetchHelp() {

        HashMap<String, String> urlParams = new HashMap<>();
        urlParams.put("id", tempHelpBean.getId());

/*        if (isLoadMore) {
            urlParams.put("page", String.valueOf(currentPage + 1));
        }*/

        DataManager.fetchHelp(urlParams, new HelpListener() {
            @Override
            public void onLoadCompleted(HelpBean helpBeanWS) {

                helpBean = helpBeanWS;
                populateHelp();

            }

            @Override
            public void onLoadFailed(String error) {
                Snackbar.make(coordinatorLayout, error, Snackbar.LENGTH_INDEFINITE)
                        .setAction(R.string.btn_retry, snackBarRefreshOnClickListener).show();
                swipeView.setRefreshing(false);
                setProgressScreenVisibility(true, false);
                if (App.getInstance().isDemo()) {
                    helpBean = new HelpBean();
                    helpBean.setId(tempHelpBean.getId());
                    helpBean.setHelpful(true);
                    helpBean.setTitle(tempHelpBean.getTitle());
                    helpBean.setContent(getString(R.string.sample_lorem_ipsum_large));
                    helpBean.setIcon(tempHelpBean.getIcon());
                    populateHelp();
                }
            }
        });

    }

    private void populateHelp() {


        txtTitle.setText(helpBean.getTitle());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            txtContent.setText(Html.fromHtml(helpBean.getContent(), Html.FROM_HTML_OPTION_USE_CSS_COLORS));
        } else {
            txtContent.setText(Html.fromHtml(helpBean.getContent()));
        }

        swipeView.setRefreshing(false);
        setProgressScreenVisibility(false, false);
    }

    public void onHelpHelpfulClick(View view) {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
        //mVibrator.vibrate(25);

        if (App.isNetworkAvailable()) {
            performHelpPageReview(true);
        } else {
            Snackbar.make(coordinatorLayout, AppConstants.NO_NETWORK_AVAILABLE, Snackbar.LENGTH_LONG)
                    .setAction(R.string.btn_dismiss, snackBarDismissOnClickListener).show();
        }


    }

    public void onHelpNotHelpfulClick(View view) {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
        //mVibrator.vibrate(25);

        if (App.isNetworkAvailable()) {
            performHelpPageReview(false);
        } else {
            Snackbar.make(coordinatorLayout, AppConstants.NO_NETWORK_AVAILABLE, Snackbar.LENGTH_LONG)
                    .setAction(R.string.btn_dismiss, snackBarDismissOnClickListener).show();
        }
    }

    private void performHelpPageReview(boolean isHelpful) {

        swipeView.setRefreshing(true);

        JSONObject postData = getHelpPageReviewJSObj(isHelpful);

        DataManager.performHelpPageReview(postData, new BasicListener() {
            @Override
            public void onLoadCompleted(BasicBean basicBean) {
                swipeView.setRefreshing(false);
                Snackbar.make(coordinatorLayout, R.string.message_your_feedback_recorded, Snackbar.LENGTH_LONG)
                        .setAction(R.string.btn_dismiss, snackBarDismissOnClickListener).show();

            }

            @Override
            public void onLoadFailed(String error) {
                swipeView.setRefreshing(false);
                Snackbar.make(coordinatorLayout, error, Snackbar.LENGTH_LONG)
                        .setAction(R.string.btn_retry, snackBarRefreshOnClickListener).show();
            }
        });

    }

    private JSONObject getHelpPageReviewJSObj(boolean isHelpful) {

        JSONObject postData = new JSONObject();

        try {
            postData.put("id", helpBean.getId());
            postData.put("is_helpful", isHelpful);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return postData;
    }
}
