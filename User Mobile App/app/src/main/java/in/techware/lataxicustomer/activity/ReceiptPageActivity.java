package in.techware.lataxicustomer.activity;

import android.os.Bundle;
import android.widget.TextView;

import in.techware.lataxicustomer.R;
import in.techware.lataxicustomer.model.TripDetailsBean;

public class ReceiptPageActivity extends BaseAppCompatNoDrawerActivity {

    private TextView txtBaseFare;
    private TextView txtKilometerFare;
    private TextView txtMinuteFare;
    private TextView txtSubTotalFare;
    private TextView txtPromotionFare;
    private TextView txtTotalFare;
    private TextView txtKilometer;
    private TextView txtMinutes;
    private TripDetailsBean bean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt_page);

        bean = (TripDetailsBean) getIntent().getSerializableExtra("bean");

        initVIews();

        populateFareDetails();

        getSupportActionBar().setTitle(R.string.title_receipt);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

    }

    public void initVIews() {

        txtBaseFare = (TextView) findViewById(R.id.txt_receipt_page_base_fare);
        txtKilometerFare = (TextView) findViewById(R.id.txt_receipt_page_kilometer_fare);
        txtMinuteFare = (TextView) findViewById(R.id.txt_receipt_page_minute_fare);
        txtSubTotalFare = (TextView) findViewById(R.id.txt_receipt_page_subtotal_fare);
        txtPromotionFare = (TextView) findViewById(R.id.txt_receipt_page_promotion_fare);
        txtTotalFare = (TextView) findViewById(R.id.txt_receipt_page_total_fare);
        txtKilometer = (TextView) findViewById(R.id.txt_receipt_page_kilometer);
        txtMinutes = (TextView) findViewById(R.id.txt_receipt_page_minute);

    }

    public void populateFareDetails() {

        txtBaseFare.setText(bean.getBaseFare());
        txtKilometerFare.setText(bean.getKilometerFare());
        txtMinuteFare.setText(bean.getMinutesFare());
        txtSubTotalFare.setText(bean.getSubTotalFare());
        txtPromotionFare.setText(bean.getPromotionFare());
        txtTotalFare.setText(bean.getTotalFare());
//        txtKilometer.setText(bean.getKilometer());
//        txtMinutes.setText(bean.getMinute());
    }
}
