package in.techware.lataxidriverapp.activity;

import android.os.Bundle;

import in.techware.lataxidriverapp.R;

public class MapLicenseActivity extends BaseAppCompatNoDrawerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_license);

        getSupportActionBar().setTitle(R.string.label_map_licences);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

    }
}
