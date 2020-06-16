package in.techware.lataxidriverapp.activity;

import android.os.Bundle;

import in.techware.lataxidriverapp.R;

public class SoftwareLicenseActivity extends BaseAppCompatNoDrawerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_software_license);

        getSupportActionBar().setTitle(R.string.label_software_licences);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

    }
}
