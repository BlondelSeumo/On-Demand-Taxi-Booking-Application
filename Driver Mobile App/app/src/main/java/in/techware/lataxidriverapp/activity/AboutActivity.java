package in.techware.lataxidriverapp.activity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import in.techware.lataxidriverapp.R;

public class AboutActivity extends BaseAppCompatNoDrawerActivity {

    private LinearLayout llAboutSoftwareLicences;
    private LinearLayout llAboutMapLicences;
    private TextView txtAboutVersionCode;
    private String version;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        getSupportActionBar().setTitle(R.string.label_about);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        initViews();
    }

    private void initViews() {

        txtAboutVersionCode = (TextView) findViewById(R.id.txt_about_app_version);

        PackageInfo pInfo = null;
        try {
            pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            version = pInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        txtAboutVersionCode.setText("v "+version);
    }

    public void onAboutSoftwareLicencesClick(View view) {

        Intent intent = new Intent(AboutActivity.this, SoftwareLicenseActivity.class);
        startActivity(intent);
    }

    public void onAboutMapLicencesClick(View view) {

        Intent intent = new Intent(AboutActivity.this, MapLicenseActivity.class);
        startActivity(intent);
    }
}
