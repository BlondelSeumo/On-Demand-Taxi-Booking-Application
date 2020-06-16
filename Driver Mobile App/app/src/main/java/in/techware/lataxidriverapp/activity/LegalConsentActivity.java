package in.techware.lataxidriverapp.activity;

import android.content.Intent;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.CheckBox;

import in.techware.lataxidriverapp.R;

public class LegalConsentActivity extends BaseAppCompatNoDrawerActivity {

    private CheckBox cbAgreement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_legal_concent);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().setStatusBarColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryDark));
        }

        getSupportActionBar().hide();
        swipeView.setPadding(0, 0, 0, 0);

        initViews();
    }

    private void initViews() {

        cbAgreement = (CheckBox) findViewById(R.id.cb_legal_consent);
//        cbAgreement.setTypeface(typeface);
    }

    public void onLegalConsentSubmitClick(View view) {

        if (cbAgreement.isChecked()) {
            startActivity(new Intent(LegalConsentActivity.this, DriverDocumentsActivity.class));
            finish();
        } else {
            Snackbar.make(coordinatorLayout, R.string.message_please_Accept_terms_and_conditions, Snackbar.LENGTH_LONG)
                    .setAction(R.string.btn_refresh, snackBarDismissOnClickListener).show();
        }
    }
}
