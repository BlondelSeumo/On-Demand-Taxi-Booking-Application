package in.techware.lataxicustomer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.HapticFeedbackConstants;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;

import in.techware.lataxicustomer.R;
import in.techware.lataxicustomer.adapter.SearchResultsRecyclerAdapter;
import in.techware.lataxicustomer.config.Config;
import in.techware.lataxicustomer.listeners.LocationSaveListener;
import in.techware.lataxicustomer.listeners.SavedLocationListener;
import in.techware.lataxicustomer.model.LocationBean;
import in.techware.lataxicustomer.model.PlaceBean;
import in.techware.lataxicustomer.net.DataManager;
import in.techware.lataxicustomer.util.AppConstants;

public class SearchPageActivity extends BaseAppCompatNoDrawerActivity
        implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = "search";
    private static final int REQ_SEARCH_HOME = 0;
    private static final int REQ_SEARCH_WORK = 1;
    private static final int HOME_LOCATION = 0;
    private static final int WORK_LOCATION = 1;

    private int locationSelect = AppConstants.LOCATION_SELECTED_ONITEMCLICK;
    private Toolbar toolbarSearch;
    private EditText etxtSearchPlace;
    private String txtInput;
    private SearchResultsRecyclerAdapter adapterSearch;
    private RecyclerView rvSearchResults;
    private LinearLayout llHome;
    private LinearLayout llWork;
    private View viewLine;
    private TextView txtHome;
    private TextView txtWork;
    private int searchType;
    private GoogleApiClient mGoogleApiClient;
    private int addHome;
    private int addWork;
    private PlaceBean workLocationBean;
    private PlaceBean homeLocationBean;
    private PlaceBean placeBean;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_page);

        initViews();

        searchType = getIntent().getIntExtra("search_type", 0);

       /* if (searchType == AppConstants.SEARCH_SOURCE) {

            etxtSearchPlace.setHint(R.string.hint_enter_the_source);
        } else if (searchType == AppConstants.SEARCH_DESTINATION) {
            etxtSearchPlace.setHint(R.string.hint_enter_the_destination);
        } else {
            etxtSearchPlace.setHint(R.string.hint_enter_the_destination);
        }
*/
       /* getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);*/

       /* mGoogleApiClient = new GoogleApiClient
                .Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(this, this)
                .build();*/
        getSupportActionBar().hide();
        swipeView.setPadding(0, 0, 0, 0);

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mGoogleApiClient != null)
            mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
        super.onStop();
    }

    public void initViews() {

        Places.initialize(getApplicationContext(), getString(R.string.api_key));

        PlacesClient placesClient = Places.createClient(SearchPageActivity.this);

        setProgressScreenVisibility(true, true);

        fetchSavedLocation();

        coordinatorLayout.removeView(toolbar);

       /* toolbarSearch = (Toolbar) getLayoutInflater().inflate(R.layout.toolbar_search_page, toolbar);
        coordinatorLayout.addView(toolbarSearch, 0);
        setSupportActionBar(toolbarSearch);

        etxtSearchPlace = (EditText) toolbarSearch.findViewById(R.id.etxt_search_place);*/


        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.search_autocomplete_fragment);

        assert autocompleteFragment != null;
        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG));

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {

                placeBean = new PlaceBean();

                if (place != null) {
                    placeBean.setAddress(String.valueOf(place.getAddress()));
                    placeBean.setLatitude(String.valueOf(place.getLatLng().latitude));
                    placeBean.setLongitude(String.valueOf(place.getLatLng().longitude));
                    placeBean.setName(place.getName());
                }

                Intent intent = new Intent();
                intent.putExtra("bean", placeBean);
                intent.putExtra("locationSelect", locationSelect);
                setResult(RESULT_OK, intent);
                finish();
            }

            @Override
            public void onError(Status status) {

            }
        });


        rvSearchResults = (RecyclerView) findViewById(R.id.rv_search_results);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        rvSearchResults.setLayoutManager(layoutManager);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvSearchResults.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));

        txtHome = (TextView) findViewById(R.id.txt_search_page_add_home);
        txtWork = (TextView) findViewById(R.id.txt_search_page_add_work);

        llHome = (LinearLayout) findViewById(R.id.ll_home);
        llWork = (LinearLayout) findViewById(R.id.ll_work);
        viewLine = (View) findViewById(R.id.view_line);

        llHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);

                addHome = AppConstants.ADD_HOME;

                if (Config.getInstance().getAddHome() == null || Config.getInstance().getAddHome().equalsIgnoreCase("null")
                        || Config.getInstance().getAddHome().equalsIgnoreCase("")) {
                    Intent intent = new Intent(SearchPageActivity.this, SearchHomeWorkActivity.class);
                    intent.putExtra("search_type", addHome);
                    startActivityForResult(intent, REQ_SEARCH_HOME);

                } else {

                    locationSelect = AppConstants.LOCATION_SELECTED_ONHOMECLICK;

                    Intent intent = new Intent();

                    PlaceBean placeBean = new PlaceBean();
                    placeBean.setName(Config.getInstance().getAddHome());
                    placeBean.setLatitude(Config.getInstance().getHomeLatitude());
                    placeBean.setLongitude(Config.getInstance().getHomeLongitude());
                    intent.putExtra("bean", placeBean);
                    intent.putExtra("locationSelect", locationSelect);

                    setResult(RESULT_OK, intent);
                    finish();
                }

            }
        });
        llWork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);

                addWork = AppConstants.ADD_WORK;

                if (Config.getInstance().getAddWork() == null || Config.getInstance().getAddWork().equalsIgnoreCase("null")
                        || Config.getInstance().getAddWork().equalsIgnoreCase("")) {
                    Intent intent = new Intent(SearchPageActivity.this, SearchHomeWorkActivity.class);
                    intent.putExtra("search_type", addWork);
                    startActivityForResult(intent, REQ_SEARCH_WORK);
                } else {

                    locationSelect = AppConstants.LOCATION_SELECTED_ONWORKCLICK;
                    Intent intent = new Intent();
                    PlaceBean placeBean = new PlaceBean();
                    placeBean.setName(Config.getInstance().getAddWork());
                    placeBean.setLatitude(Config.getInstance().getWorkLatitude());
                    placeBean.setLongitude(Config.getInstance().getWorkLongitude());
                    intent.putExtra("bean", placeBean);
                    intent.putExtra("locationSelect", locationSelect);


                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });

       /* etxtSearchPlace.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                txtInput = etxtSearchPlace.getText().toString();

                Log.i(TAG, "onTextChanged: Text" + txtInput);

                txtInputResponse();

                swipeView.setRefreshing(true);
//                populateSearchResults();

                PlaceListTask placesListTask = new PlaceListTask(txtInput);
                placesListTask.setStrAddress(txtInput);
                placesListTask.execute();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });*/

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == REQ_SEARCH_HOME && resultCode == RESULT_OK) {

            homeLocationBean = (PlaceBean) data.getSerializableExtra("location");

            txtHome.setText(homeLocationBean.getName());

            Toast.makeText(getApplicationContext(), R.string.message_home_location_added,
                    Toast.LENGTH_LONG).show();

            performLocationSave(HOME_LOCATION);

        }

        if (requestCode == REQ_SEARCH_WORK && resultCode == RESULT_OK) {

            workLocationBean = (PlaceBean) data.getSerializableExtra("location");

            txtWork.setText(workLocationBean.getName());

            Toast.makeText(getApplicationContext(), R.string.message_work_location_added,
                    Toast.LENGTH_LONG).show();

            performLocationSave(WORK_LOCATION);

        }
    }

    private void performLocationSave(final int type) {

        final JSONObject postData = getLocationSaveJSObj(type);

        DataManager.performLocationSave(postData, new LocationSaveListener() {

            @Override
            public void onLoadCompleted(LocationBean locationBean) {

                swipeView.setRefreshing(false);

                if (type == HOME_LOCATION) {
                    Config.getInstance().setAddHome(homeLocationBean.getName());
                    Config.getInstance().setHomeLatitude(homeLocationBean.getLatitude());
                    Config.getInstance().setHomeLongitude(homeLocationBean.getLongitude());
                } else {
                    Config.getInstance().setAddWork(workLocationBean.getName());
                    Config.getInstance().setWorkLatitude(workLocationBean.getLatitude());
                    Config.getInstance().setWorkLongitude(workLocationBean.getLongitude());
                }

            }

            @Override
            public void onLoadFailed(String error) {
                swipeView.setRefreshing(false);
            }
        });
    }

    private JSONObject getLocationSaveJSObj(int type) {
        JSONObject postData = new JSONObject();

        try {

            if (type == HOME_LOCATION) {
                postData.put("type", HOME_LOCATION);
                postData.put("home", homeLocationBean.getName());
                postData.put("home_latitude", homeLocationBean.getLatitude());
                postData.put("home_longitude", homeLocationBean.getLongitude());
            } else {
                postData.put("type", WORK_LOCATION);
                postData.put("work", workLocationBean.getName());
                postData.put("work_latitude", workLocationBean.getLatitude());
                postData.put("work_longitude", workLocationBean.getLongitude());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return postData;
    }

   /* @Override
    public void onConnected(@Nullable Bundle bundle) {
        PendingResult<AutocompletePredictionBuffer> result = Places.GeoDataApi.getAutocompletePredictions(mGoogleApiClient, "infopark", null, null);

        result.setResultCallback(new ResultCallback<AutocompletePredictionBuffer>() {
            @Override
            public void onResult(@NonNull AutocompletePredictionBuffer autocompletePredictions) {
                Log.i(TAG, "onConnected: Result" + autocompletePredictions);


            }
        });
    }


    private class PlaceListTask extends AsyncTask<String, Integer, ArrayList<AutocompletePrediction>> {

        private String strAddress;

        public PlaceListTask(String strAddress) {
            super();
            this.strAddress = strAddress;
        }

        public String getStrAddress() {
            return strAddress;
        }

        public void setStrAddress(String strAddress) {
            this.strAddress = strAddress;
        }

        @Override
        protected ArrayList<AutocompletePrediction> doInBackground(String... params) {

            if (mGoogleApiClient.isConnected()) {
                Log.i(TAG, "Starting autocomplete query for: " + strAddress);

                PendingResult<AutocompletePredictionBuffer> results =
                        Places.GeoDataApi
                                .getAutocompletePredictions(mGoogleApiClient, strAddress, null,
                                        null);

                AutocompletePredictionBuffer autocompletePredictions = results
                        .await(60, TimeUnit.SECONDS);

                final com.google.android.gms.common.api.Status status = autocompletePredictions.getStatus();
                if (!status.isSuccess()) {
                    Log.e(TAG, "Error getting autocomplete prediction API call: " + status.toString());
                    autocompletePredictions.release();
                    return null;
                }

                Log.i(TAG, "Query completed. Received " + autocompletePredictions.getCount()
                        + " predictions.");

                return DataBufferUtils.freezeAndClose(autocompletePredictions);
            }
            Log.e(TAG, "Google API client is not connected for autocomplete query.");
            return null;

        }

        @Override
        protected void onPostExecute(ArrayList<AutocompletePrediction> result) {
            super.onPostExecute(result);

            if (result != null) {

                populateSearchResults(result);

            } else {
                swipeView.setRefreshing(false);

            }
        }
    }


    private void populateSearchResults(ArrayList<AutocompletePrediction> result) {

        if (adapterSearch == null) {
            adapterSearch = new SearchResultsRecyclerAdapter(this, mGoogleApiClient, result);

            adapterSearch.setSearchResultsRecyclerAdapterListener(new SearchResultsRecyclerAdapter.SearchResultsRecyclerAdapterListener() {

                public PlaceBean placeBean;

                @Override
                public void onItemSelected(Place place) {

                    locationSelect = AppConstants.LOCATION_SELECTED_ONITEMCLICK;

                    placeBean = new PlaceBean();

                    placeBean.setAddress(String.valueOf(place.getAddress()));
                    placeBean.setLatitude(String.valueOf(place.getLatLng().latitude));
                    placeBean.setLongitude(String.valueOf(place.getLatLng().longitude));
                    placeBean.setName((String) place.getName());

                    Intent intent = new Intent();
                    intent.putExtra("bean", placeBean);
                    intent.putExtra("locationSelect", locationSelect);
                    setResult(RESULT_OK, intent);
                    finish();

                }

                @Override
                public void onSnackBarShow(String message) {

                }
            });
            rvSearchResults.setAdapter(adapterSearch);
        } else {

            adapterSearch.setmResultList(result);
            adapterSearch.notifyDataSetChanged();
        }

        setProgressScreenVisibility(false, false);
        swipeView.setRefreshing(false);

    }

    private void txtInputResponse() {

        if (etxtSearchPlace.getText().toString().length() >= 1) {

            llHome.setVisibility(View.GONE);
            llWork.setVisibility(View.GONE);
            viewLine.setVisibility(View.GONE);
        } else {
            llHome.setVisibility(View.VISIBLE);
            llWork.setVisibility(View.VISIBLE);
            viewLine.setVisibility(View.VISIBLE);

        }
    }
*/

    public void onAddHomeClick(View view) {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);

        addHome = AppConstants.ADD_HOME;

        if (Config.getInstance().getAddHome() == null || Config.getInstance().getAddHome().equalsIgnoreCase("null")
                || Config.getInstance().getAddHome().equalsIgnoreCase("")) {
            Intent intent = new Intent(SearchPageActivity.this, SearchHomeWorkActivity.class);
            intent.putExtra("search_type", addHome);
            startActivityForResult(intent, REQ_SEARCH_HOME);

        } else {

            locationSelect = AppConstants.LOCATION_SELECTED_ONHOMECLICK;

            Intent intent = new Intent();

            PlaceBean placeBean = new PlaceBean();
            placeBean.setName(Config.getInstance().getAddHome());
            placeBean.setLatitude(Config.getInstance().getHomeLatitude());
            placeBean.setLongitude(Config.getInstance().getHomeLongitude());
            intent.putExtra("bean", placeBean);
            intent.putExtra("locationSelect", locationSelect);

            setResult(RESULT_OK, intent);
            finish();
        }
    }

    public void onAddWorkClick(View view) {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);

        addWork = AppConstants.ADD_WORK;

        if (Config.getInstance().getAddWork() == null || Config.getInstance().getAddWork().equalsIgnoreCase("null")
                || Config.getInstance().getAddWork().equalsIgnoreCase("")) {
            Intent intent = new Intent(SearchPageActivity.this, SearchHomeWorkActivity.class);
            intent.putExtra("search_type", addWork);
            startActivityForResult(intent, REQ_SEARCH_WORK);
        } else {

            locationSelect = AppConstants.LOCATION_SELECTED_ONWORKCLICK;

            Intent intent = new Intent();

            PlaceBean placeBean = new PlaceBean();
            placeBean.setName(Config.getInstance().getAddWork());
            placeBean.setLatitude(Config.getInstance().getWorkLatitude());
            placeBean.setLongitude(Config.getInstance().getWorkLongitude());
            intent.putExtra("bean", placeBean);
            intent.putExtra("locationSelect", locationSelect);


            setResult(RESULT_OK, intent);
            finish();
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

        Log.e(TAG, "onConnectionFailed: ConnectionResult.getErrorCode() = "
                + connectionResult.getErrorCode());

        // TODO(Developer): Check error code and notify the user of error state and resolution.
        Toast.makeText(this,
                "Could not connect to Google API Client: Error " + connectionResult.getErrorCode(),
                Toast.LENGTH_SHORT).show();

    }

    public void fetchSavedLocation() {

        HashMap<String, String> urlParams = new HashMap<>();

        DataManager.fetchSavedLocation(urlParams, new SavedLocationListener() {

            @Override
            public void onLoadCompleted(LocationBean locationBean) {
                swipeView.setRefreshing(false);
                setLocationBean(locationBean);
                populateSavedLocation(locationBean);

                setProgressScreenVisibility(false, false);
            }

            @Override
            public void onLoadFailed(String error) {
                swipeView.setRefreshing(false);

            }
        });
    }

    private void setLocationBean(LocationBean locationBean) {

        Config.getInstance().setAddHome(locationBean.getHomeLocation());
        Config.getInstance().setHomeLatitude(locationBean.getHomeLatitude());
        Config.getInstance().setHomeLongitude(locationBean.getHomeLongitude());
        Config.getInstance().setAddWork(locationBean.getWorkLocation());
        Config.getInstance().setWorkLatitude(locationBean.getWorkLatitude());
        Config.getInstance().setWorkLongitude(locationBean.getWorkLongitude());

    }

    private void populateSavedLocation(LocationBean locationBean) {

        homeLocationBean = new PlaceBean();
        workLocationBean = new PlaceBean();
        homeLocationBean.setName(locationBean.getHomeLocation());
        homeLocationBean.setLatitude(locationBean.getHomeLatitude());
        homeLocationBean.setLongitude(locationBean.getHomeLongitude());
        workLocationBean.setName(locationBean.getHomeLocation());
        workLocationBean.setLatitude(locationBean.getHomeLatitude());
        workLocationBean.setLongitude(locationBean.getHomeLongitude());

        if (locationBean.getHomeLatitude().equalsIgnoreCase("null")) {
            txtHome.setText(R.string.title_add_home);
        } else {
            txtHome.setText(locationBean.getHomeLocation());
        }

        if (locationBean.getWorkLatitude().equalsIgnoreCase("null")) {
            txtWork.setText(R.string.title_add_work);
        } else {
            txtWork.setText(locationBean.getWorkLocation());
        }
    }
}

