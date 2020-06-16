package in.techware.lataxidriverapp.activity;

import android.os.Bundle;

import in.techware.lataxidriverapp.R;

public class ProTipsActivity extends BaseAppCompatNoDrawerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pro_tips);

        initViews();

        getSupportActionBar().setTitle("Pro Tips");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

    }

    private void initViews() {

    }
}
