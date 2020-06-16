package in.techware.lataxicustomer.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Typeface;
import android.view.HapticFeedbackConstants;
import android.view.View;
import android.widget.LinearLayout;

import in.techware.lataxicustomer.R;
import in.techware.lataxicustomer.config.TypefaceCache;


/**
 * Created by Jemsheer K D on 22 December, 2016.
 * Package com.wakilishwa.dialogs
 * Project Wakilishwa
 */

public class SelectPhotoDialog {

    private final Activity mContext;
    private Typeface typeface;
    private SelectPhotoDialogActionListener selectPhotoDialogActionListener;
    private Dialog dialogSelectDialog;

    public SelectPhotoDialog(Activity mContext) {
        this.mContext = mContext;

        try {
            typeface = TypefaceCache.getInstance().getTypeface(mContext.getString(R.string.font_roboto_regular));
        } catch (Exception e) {
            e.printStackTrace();
        }
        setSelectPhotoDialog();
    }

    public void show() {
        dialogSelectDialog.show();
    }

    private void setSelectPhotoDialog() {

        dialogSelectDialog = new Dialog(mContext, R.style.ThemeDialogCustom_NoBackground);
        dialogSelectDialog.setContentView(R.layout.dialog_select_photo);
        dialogSelectDialog.setTitle(R.string.title_select_photo_from);

        LinearLayout llGallery = (LinearLayout) dialogSelectDialog.findViewById(R.id.ll_dialog_select_photo_gallery);
        LinearLayout llCamera = (LinearLayout) dialogSelectDialog.findViewById(R.id.ll_dialog_select_photo_camera);

        llGallery.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);

                selectPhotoDialogActionListener.onSelectGalleryClick();
                dialogSelectDialog.dismiss();
            }
        });
        llCamera.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                selectPhotoDialogActionListener.onSelectCameraClick();
                dialogSelectDialog.dismiss();
            }
        });
    }

    public interface SelectPhotoDialogActionListener {

        void onSelectGalleryClick();

        void onSelectCameraClick();
    }

    public SelectPhotoDialogActionListener getSelectPhotoDialogActionListener() {
        return selectPhotoDialogActionListener;
    }


    public void setSelectPhotoDialogActionListener(SelectPhotoDialogActionListener selectPhotoDialogActionListener) {
        this.selectPhotoDialogActionListener = selectPhotoDialogActionListener;
    }
}
