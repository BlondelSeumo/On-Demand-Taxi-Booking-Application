package in.techware.lataxicustomer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import java.util.Arrays;

import in.techware.lataxicustomer.R;
import in.techware.lataxicustomer.adapter.SearchResultsRecyclerAdapter;
import in.techware.lataxicustomer.model.PlaceBean;
import in.techware.lataxicustomer.util.AppConstants;

public class SearchHomeWorkActivity extends BaseAppCompatNoDrawerActivity
        implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = "";
    private Toolbar toolbarHomeWorkSearch;
    private EditText etxtSearchHomwWork;
    private int searchType;
    private TextView toolbarName;
    private EditText etxtSearch;
    private String txtinput;
    private GoogleApiClient mGoogleApiClient;
    private SearchResultsRecyclerAdapter adapterSearch;
    private int locationSelect;
    private RecyclerView rvSearchResults;
    private PlaceBean placeBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_home_work);

        searchType = getIntent().getIntExtra("search_type", 0);

        if (searchType == AppConstants.ADD_HOME) {

            getSupportActionBar().setTitle(R.string.title_add_home);
        } else {
            getSupportActionBar().setTitle(R.string.title_add_work);
        }


        initViews();


        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);


       /* mGoogleApiClient = new GoogleApiClient
                .Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(this, this)
                .build();*/


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

    private void initViews() {


        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.search_home_work_autocomplete_fragment);

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
                intent.putExtra("location", placeBean);
                intent.putExtra("locationSelect", locationSelect);
                setResult(RESULT_OK, intent);
                finish();
            }

            @Override
            public void onError(Status status) {

            }
        });


//        toolbarName = (TextView) findViewById(R.id.txt_toolbar_name);
       /* etxtSearch = (EditText) findViewById(R.id.etxt_search_home_work);

        rvSearchResults = (RecyclerView) findViewById(R.id.rv_search_home_work_results);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        rvSearchResults.setLayoutManager(layoutManager);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvSearchResults.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));

        etxtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                txtinput = etxtSearch.getText().toString();

                swipeView.setRefreshing(true);
//                populateSearchResults();

                PlaceListTask placesListTask = new PlaceListTask(txtinput);
                placesListTask.setStrAddress(txtinput);
                placesListTask.execute();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });*/
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

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

        Log.e(TAG, "onConnectionFailed: ConnectionResult.getErrorCode() = "
                + connectionResult.getErrorCode());

        // TODO(Developer): Check error code and notify the user of error state and resolution.
        Toast.makeText(this,
                "Could not connect to Google API Client: Error " + connectionResult.getErrorCode(),
                Toast.LENGTH_SHORT).show();

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

                // Submit the query to the autocomplete API and retrieve a PendingResult that will
                // contain the results when the query completes.
                PendingResult<AutocompletePredictionBuffer> results =
                        Places.GeoDataApi
                                .getAutocompletePredictions(mGoogleApiClient, strAddress, null,
                                        null);

                // This method should have been called off the main UI thread. Block and wait for at most 60s
                // for a result from the API.
                AutocompletePredictionBuffer autocompletePredictions = results
                        .await(60, TimeUnit.SECONDS);

                // Confirm that the query completed successfully, otherwise return null
                final com.google.android.gms.common.api.Status status = autocompletePredictions.getStatus();
                if (!status.isSuccess()) {
//                Toast.makeText(getContext(), "Error contacting API: " + status.toString(),
//                        Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "Error getting autocomplete prediction API call: " + status.toString());
                    autocompletePredictions.release();
                    return null;
                }

                Log.i(TAG, "Query completed. Received " + autocompletePredictions.getCount()
                        + " predictions.");

                // Freeze the results immutable representation that can be stored safely.
                return DataBufferUtils.freezeAndClose(autocompletePredictions);
            }
            Log.e(TAG, "Google API client is not connected for autocomplete query.");
            return null;

        }

        @Override
        protected void onPostExecute(ArrayList<AutocompletePrediction> result) {
            super.onPostExecute(result);

            Log.i(TAG, "onPostExecute: Result" + result);

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

                PlaceBean placeBean;

                @Override
                public void onItemSelected(Place place) {

                    locationSelect = AppConstants.LOCATION_SELECTED_ONITEMCLICK;

                    placeBean = new PlaceBean();

                    placeBean.setAddress(String.valueOf(place.getAddress()));
                    placeBean.setLatitude(String.valueOf(place.getLatLng().latitude));
                    placeBean.setLongitude(String.valueOf(place.getLatLng().longitude));
                    placeBean.setName((String) place.getName());

                    Intent intent = new Intent();
                    intent.putExtra("location", placeBean);
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

    }*/
}
