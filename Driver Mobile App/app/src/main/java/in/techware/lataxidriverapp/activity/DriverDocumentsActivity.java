package in.techware.lataxidriverapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.HapticFeedbackConstants;
import android.view.View;
import android.widget.ImageView;

import com.google.gson.Gson;

import java.util.HashMap;

import in.techware.lataxidriverapp.R;
import in.techware.lataxidriverapp.app.App;
import in.techware.lataxidriverapp.config.Config;
import in.techware.lataxidriverapp.listeners.DocumentStatusListener;
import in.techware.lataxidriverapp.model.DocumentBean;
import in.techware.lataxidriverapp.model.DocumentStatusBean;
import in.techware.lataxidriverapp.net.DataManager;
import in.techware.lataxidriverapp.util.AppConstants;

public class DriverDocumentsActivity extends BaseAppCompatNoDrawerActivity {

    private static final String TAG = "DriverDocA";

    private static final int REQUEST_DRIVER_LICENCE = 0;
    private static final int REQUEST_POLICE_CLEARANCE_CERTIFICATE = 1;
    private static final int REQUEST_FITNESS_CERTIFICATE = 2;
    private static final int REQUEST_VEHICLE_REGISTRATION = 3;
    private static final int REQUEST_VEHICLE_PERMIT = 4;
    private static final int REQUEST_COMMERCIAL_INSURANCE = 5;
    private static final int REQUEST_TAX_RECEIPT = 6;

    private ImageView ivDriverLicenceNext;
    private ImageView ivDriverLicenceSaved;
    private ImageView ivPoliceClearanceCertificateNext;
    private ImageView ivPoliceClearanceCertificateSaved;
    private ImageView ivFitnessCertificateNext;
    private ImageView ivFitnessCertificateSaved;
    private ImageView ivVehicleRegistrationNext;
    private ImageView ivVehicleRegistrationSaved;
    private ImageView ivVehiclePermitNext;
    private ImageView ivVehiclePermitSaved;
    private ImageView ivCommercialInsuranceNext;
    private ImageView ivCommercialInsuranceSaved;
    private ImageView ivTaxReceiptNext;
    private ImageView ivTaxReceiptSaved;
    private View.OnClickListener snackBarRefreshOnClickListener;
    private boolean isDriverLicenceUploaded;
    private boolean isPoliceClearanceCertificateUploaded;
    private boolean isFitnessCertificateUploaded;
    private boolean isVehicleRegistrationUploaded;
    private boolean isVehiclePermitUploaded;
    private boolean isCommercialInsuranceUploaded;
    private boolean isTaxReceiptUploaded;
    private DocumentStatusBean documentStatusBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_documents);

        initViews();

        getSupportActionBar().setTitle(R.string.label_documents);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_DRIVER_LICENCE && resultCode == RESULT_OK) {
            isDriverLicenceUploaded = true;
            ivDriverLicenceNext.setVisibility(View.GONE);
            ivDriverLicenceSaved.setVisibility(View.VISIBLE);
        }

        if (requestCode == REQUEST_POLICE_CLEARANCE_CERTIFICATE && resultCode == RESULT_OK) {
            isPoliceClearanceCertificateUploaded = true;
            ivPoliceClearanceCertificateNext.setVisibility(View.GONE);
            ivPoliceClearanceCertificateSaved.setVisibility(View.VISIBLE);
        }

        if (requestCode == REQUEST_FITNESS_CERTIFICATE && resultCode == RESULT_OK) {
            isFitnessCertificateUploaded = true;
            ivFitnessCertificateNext.setVisibility(View.GONE);
            ivFitnessCertificateSaved.setVisibility(View.VISIBLE);
        }

        if (requestCode == REQUEST_VEHICLE_REGISTRATION && resultCode == RESULT_OK) {
            isVehicleRegistrationUploaded = true;
            ivVehicleRegistrationNext.setVisibility(View.GONE);
            ivVehicleRegistrationSaved.setVisibility(View.VISIBLE);
        }

        if (requestCode == REQUEST_VEHICLE_PERMIT && resultCode == RESULT_OK) {
            isVehiclePermitUploaded = true;
            ivVehiclePermitNext.setVisibility(View.GONE);
            ivVehiclePermitSaved.setVisibility(View.VISIBLE);
        }

        if (requestCode == REQUEST_COMMERCIAL_INSURANCE && resultCode == RESULT_OK) {
            isCommercialInsuranceUploaded = true;
            ivCommercialInsuranceNext.setVisibility(View.GONE);
            ivCommercialInsuranceSaved.setVisibility(View.VISIBLE);
        }

        if (requestCode == REQUEST_TAX_RECEIPT && resultCode == RESULT_OK) {
            isTaxReceiptUploaded = true;
            ivTaxReceiptNext.setVisibility(View.GONE);
            ivTaxReceiptSaved.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (documentStatusBean == null) {
            setProgressScreenVisibility(true, true);
            getData(false);
        } else {
            getData(true);
        }
    }

    private void getData(boolean isSwipeRefreshing) {
        swipeView.setRefreshing(isSwipeRefreshing);
        if (App.isNetworkAvailable()) {
            fetchDocumentStatus();
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

        ivDriverLicenceNext = (ImageView) findViewById(R.id.iv_driver_docuements_driver_license_next);
        ivDriverLicenceSaved = (ImageView) findViewById(R.id.iv_driver_docuements_driver_license_saved);

        ivPoliceClearanceCertificateNext = (ImageView) findViewById(R.id.iv_driver_docuements_police_clearance_certificate_next);
        ivPoliceClearanceCertificateSaved = (ImageView) findViewById(R.id.iv_driver_docuements_police_clearance_certificate_saved);

        ivFitnessCertificateNext = (ImageView) findViewById(R.id.iv_driver_docuements_fitness_certificate_next);
        ivFitnessCertificateSaved = (ImageView) findViewById(R.id.iv_driver_docuements_fitness_certificate_saved);

        ivVehicleRegistrationNext = (ImageView) findViewById(R.id.iv_driver_docuements_vehicle_registration_next);
        ivVehicleRegistrationSaved = (ImageView) findViewById(R.id.iv_driver_docuements_vehicle_registration_saved);

        ivVehiclePermitNext = (ImageView) findViewById(R.id.iv_driver_docuements_vehicle_permit_next);
        ivVehiclePermitSaved = (ImageView) findViewById(R.id.iv_driver_docuements_vehicle_permit_saved);

        ivCommercialInsuranceNext = (ImageView) findViewById(R.id.iv_driver_docuements_commercial_insurance_next);
        ivCommercialInsuranceSaved = (ImageView) findViewById(R.id.iv_driver_docuements_commercial_insurance_saved);

        ivTaxReceiptNext = (ImageView) findViewById(R.id.iv_driver_docuements_tax_receipt_next);
        ivTaxReceiptSaved = (ImageView) findViewById(R.id.iv_driver_docuements_tax_receipt_saved);
    }

    private void fetchDocumentStatus() {

        HashMap<String, String> urlParams = new HashMap<>();
        urlParams.put("auth_token", Config.getInstance().getAuthToken());

        DataManager.fetchDocumentStatus(urlParams, new DocumentStatusListener() {
            @Override
            public void onLoadCompleted(DocumentStatusBean documentStatusBeanWS) {
                documentStatusBean = documentStatusBeanWS;
                populateDocumentStatus();
            }

            @Override
            public void onLoadFailed(String error) {
                swipeView.setRefreshing(false);
                setProgressScreenVisibility(true, false);
                Snackbar.make(coordinatorLayout, error, Snackbar.LENGTH_INDEFINITE)
                        .setAction(R.string.btn_retry, snackBarRefreshOnClickListener).show();

                if (App.getInstance().isDemo()) {
                    setProgressScreenVisibility(false, false);
                }
            }
        });

    }

    private void populateDocumentStatus() {


        for (DocumentBean bean : documentStatusBean.getDocuments()) {
            Log.i(TAG, "populateDocumentStatus: DocumentBean  : " + new Gson().toJson(bean));

            if (bean.getType() == AppConstants.DOCUMENT_TYPE_DRIVER_LICENCE) {
                if (bean.isUploaded() || (bean.getDocumentStatus() == AppConstants.DOCUMENT_STATUS_PENDING_APPROVAL
                        && bean.getDocumentStatus() == AppConstants.DOCUMENT_STATUS_APPROVED)) {
                    isDriverLicenceUploaded = true;
                    ivDriverLicenceNext.setVisibility(View.GONE);
                    ivDriverLicenceSaved.setVisibility(View.VISIBLE);
                } else {
                    isDriverLicenceUploaded = false;
                    ivDriverLicenceNext.setVisibility(View.VISIBLE);
                    ivDriverLicenceSaved.setVisibility(View.GONE);
                }
            } else if (bean.getType() == AppConstants.DOCUMENT_TYPE_POLICE_CLEARANCE_CERTIFICATE) {
                if (bean.isUploaded() || (bean.getDocumentStatus() == AppConstants.DOCUMENT_STATUS_PENDING_APPROVAL
                        && bean.getDocumentStatus() == AppConstants.DOCUMENT_STATUS_APPROVED)) {
                    isPoliceClearanceCertificateUploaded = true;
                    ivPoliceClearanceCertificateNext.setVisibility(View.GONE);
                    ivPoliceClearanceCertificateSaved.setVisibility(View.VISIBLE);
                } else {
                    isPoliceClearanceCertificateUploaded = false;
                    ivPoliceClearanceCertificateNext.setVisibility(View.VISIBLE);
                    ivPoliceClearanceCertificateSaved.setVisibility(View.GONE);
                }
            } else if (bean.getType() == AppConstants.DOCUMENT_TYPE_FITNESS_CERTIFICATE) {
                if (bean.isUploaded() || (bean.getDocumentStatus() == AppConstants.DOCUMENT_STATUS_PENDING_APPROVAL
                        && bean.getDocumentStatus() == AppConstants.DOCUMENT_STATUS_APPROVED)) {
                    isFitnessCertificateUploaded = true;
                    ivFitnessCertificateNext.setVisibility(View.GONE);
                    ivFitnessCertificateSaved.setVisibility(View.VISIBLE);
                } else {
                    isFitnessCertificateUploaded = false;
                    ivFitnessCertificateNext.setVisibility(View.VISIBLE);
                    ivFitnessCertificateSaved.setVisibility(View.GONE);
                }
            } else if (bean.getType() == AppConstants.DOCUMENT_TYPE_VEHICLE_REGISTRATION) {
                if (bean.isUploaded() || (bean.getDocumentStatus() == AppConstants.DOCUMENT_STATUS_PENDING_APPROVAL
                        && bean.getDocumentStatus() == AppConstants.DOCUMENT_STATUS_APPROVED)) {
                    isVehicleRegistrationUploaded = true;
                    ivVehicleRegistrationNext.setVisibility(View.GONE);
                    ivVehicleRegistrationSaved.setVisibility(View.VISIBLE);
                } else {
                    isVehicleRegistrationUploaded = false;
                    ivVehicleRegistrationNext.setVisibility(View.VISIBLE);
                    ivVehicleRegistrationSaved.setVisibility(View.GONE);
                }
            } else if (bean.getType() == AppConstants.DOCUMENT_TYPE_VEHICLE_PERMIT) {
                if (bean.isUploaded() || (bean.getDocumentStatus() == AppConstants.DOCUMENT_STATUS_PENDING_APPROVAL
                        && bean.getDocumentStatus() == AppConstants.DOCUMENT_STATUS_APPROVED)) {
                    isVehiclePermitUploaded = true;
                    ivVehiclePermitNext.setVisibility(View.GONE);
                    ivVehiclePermitSaved.setVisibility(View.VISIBLE);
                } else {
                    isVehiclePermitUploaded = false;
                    ivVehiclePermitNext.setVisibility(View.VISIBLE);
                    ivVehiclePermitSaved.setVisibility(View.GONE);
                }
            } else if (bean.getType() == AppConstants.DOCUMENT_TYPE_COMMERCIAL_INSURANCE) {
                if (bean.isUploaded() || (bean.getDocumentStatus() == AppConstants.DOCUMENT_STATUS_PENDING_APPROVAL
                        && bean.getDocumentStatus() == AppConstants.DOCUMENT_STATUS_APPROVED)) {
                    isCommercialInsuranceUploaded = true;
                    ivCommercialInsuranceNext.setVisibility(View.GONE);
                    ivCommercialInsuranceSaved.setVisibility(View.VISIBLE);
                } else {
                    isCommercialInsuranceUploaded = false;
                    ivCommercialInsuranceNext.setVisibility(View.VISIBLE);
                    ivCommercialInsuranceSaved.setVisibility(View.GONE);
                }
            } else if (bean.getType() == AppConstants.DOCUMENT_TYPE_TAX_RECEIPT) {
                if (bean.isUploaded() || (bean.getDocumentStatus() == AppConstants.DOCUMENT_STATUS_PENDING_APPROVAL
                        && bean.getDocumentStatus() == AppConstants.DOCUMENT_STATUS_APPROVED)) {
                    isTaxReceiptUploaded = true;
                    ivTaxReceiptNext.setVisibility(View.GONE);
                    ivTaxReceiptSaved.setVisibility(View.VISIBLE);
                } else {
                    isTaxReceiptUploaded = false;
                    ivTaxReceiptNext.setVisibility(View.VISIBLE);
                    ivTaxReceiptSaved.setVisibility(View.GONE);
                }
            }
        }

        swipeView.setRefreshing(false);
        setProgressScreenVisibility(false, false);
    }

    public void onDriverDocumentsDriverLicenseClick(View view) {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
        //mVibrator.vibrate(25);

        startActivityForResult(new Intent(DriverDocumentsActivity.this, DocumentUploadActivity.class)
                        .putExtra("type", AppConstants.DOCUMENT_TYPE_DRIVER_LICENCE),
                REQUEST_DRIVER_LICENCE);
    }

    public void onDriverDocumentsPoliceClearanceCertificateClick(View view) {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
        //mVibrator.vibrate(25);

        startActivityForResult(new Intent(DriverDocumentsActivity.this, DocumentUploadActivity.class)
                        .putExtra("type", AppConstants.DOCUMENT_TYPE_POLICE_CLEARANCE_CERTIFICATE),
                REQUEST_POLICE_CLEARANCE_CERTIFICATE);
    }

    public void onDriverDocumentsFitnessCertificateClick(View view) {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
        //mVibrator.vibrate(25);

        startActivityForResult(new Intent(DriverDocumentsActivity.this, DocumentUploadActivity.class)
                        .putExtra("type", AppConstants.DOCUMENT_TYPE_FITNESS_CERTIFICATE),
                REQUEST_FITNESS_CERTIFICATE);
    }

    public void onDriverDocumentsVehicleRegistrationClick(View view) {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
        //mVibrator.vibrate(25);

        startActivityForResult(new Intent(DriverDocumentsActivity.this, DocumentUploadActivity.class)
                        .putExtra("type", AppConstants.DOCUMENT_TYPE_VEHICLE_REGISTRATION),
                REQUEST_VEHICLE_REGISTRATION);
    }

    public void onDriverDocumentsVehiclePermitClick(View view) {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
        //mVibrator.vibrate(25);

        startActivityForResult(new Intent(DriverDocumentsActivity.this, DocumentUploadActivity.class)
                        .putExtra("type", AppConstants.DOCUMENT_TYPE_VEHICLE_PERMIT),
                REQUEST_VEHICLE_PERMIT);
    }

    public void onDriverDocumentsCommercialInsuranceClick(View view) {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
        //mVibrator.vibrate(25);

        startActivityForResult(new Intent(DriverDocumentsActivity.this, DocumentUploadActivity.class)
                        .putExtra("type", AppConstants.DOCUMENT_TYPE_COMMERCIAL_INSURANCE),
                REQUEST_COMMERCIAL_INSURANCE);
    }

    public void onDriverDocumentsTaxReceiptClick(View view) {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
        //mVibrator.vibrate(25);

        startActivityForResult(new Intent(DriverDocumentsActivity.this, DocumentUploadActivity.class)
                        .putExtra("type", AppConstants.DOCUMENT_TYPE_TAX_RECEIPT),
                REQUEST_TAX_RECEIPT);
    }

    public void onDriverDocumentsSubmitClick(View view) {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
        //mVibrator.vibrate(25);

        if (collectDriverDocuments()) {
            startActivity(new Intent(DriverDocumentsActivity.this, ProfilePhotoUploadActivity.class));
            finish();
        } else {
            Snackbar.make(coordinatorLayout, R.string.message_upload_all_the_required_document, Snackbar.LENGTH_SHORT)
                    .setAction(R.string.btn_refresh, snackBarRefreshOnClickListener).show();

        }
    }

    private boolean collectDriverDocuments() {

        if (!isDriverLicenceUploaded) {
            Snackbar.make(coordinatorLayout, R.string.message_please_upload_driver_licence, Snackbar.LENGTH_LONG)
                    .setAction(R.string.btn_dismiss, snackBarDismissOnClickListener).show();
            return false;
        }
        if (!isPoliceClearanceCertificateUploaded) {
            Snackbar.make(coordinatorLayout, R.string.message_please_upload_police_clearance_certificate, Snackbar.LENGTH_LONG)
                    .setAction(R.string.btn_dismiss, snackBarDismissOnClickListener).show();
            return false;
        }
        if (!isFitnessCertificateUploaded) {
            Snackbar.make(coordinatorLayout, R.string.message_please_upload_fitness_certificate, Snackbar.LENGTH_LONG)
                    .setAction(R.string.btn_dismiss, snackBarDismissOnClickListener).show();
            return false;
        }
        if (!isVehicleRegistrationUploaded) {
            Snackbar.make(coordinatorLayout, R.string.message_please_upload_vehicle_registration, Snackbar.LENGTH_LONG)
                    .setAction(R.string.btn_dismiss, snackBarDismissOnClickListener).show();
            return false;
        }
        if (!isVehiclePermitUploaded) {
            Snackbar.make(coordinatorLayout, R.string.message_please_upload_vehicle_permit, Snackbar.LENGTH_LONG)
                    .setAction(R.string.btn_dismiss, snackBarDismissOnClickListener).show();
            return false;
        }
        if (!isCommercialInsuranceUploaded) {
            Snackbar.make(coordinatorLayout, R.string.message_please_upload_commercial_insurance, Snackbar.LENGTH_LONG)
                    .setAction(R.string.btn_dismiss, snackBarDismissOnClickListener).show();
            return false;
        }
        if (!isTaxReceiptUploaded) {
            Snackbar.make(coordinatorLayout, R.string.message_please_upload_tax_receipt, Snackbar.LENGTH_LONG)
                    .setAction(R.string.btn_dismiss, snackBarDismissOnClickListener).show();
            return false;
        }

        return true;
    }
}
