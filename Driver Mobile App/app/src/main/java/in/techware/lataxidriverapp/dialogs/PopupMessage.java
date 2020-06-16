package in.techware.lataxidriverapp.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.view.HapticFeedbackConstants;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import in.techware.lataxidriverapp.R;
import in.techware.lataxidriverapp.config.TypefaceCache;


/**
 * Created by Jemsheer K D on 1/10/2015.
 * Package ${PACKAGE_NAME}
 * Project Carrefour
 */
public class PopupMessage extends BaseDialog {

    private PopupActionListener popupActionListener;

    //    private final Vibrator mVibrator;
    private Typeface typeface;
    private final Activity mContext;
    private Dialog dialogPopupMessage;
    private TextView txtMessage;
    private final Handler mHandler = new Handler();
    private AlertDialog alertDialog;

    public PopupMessage(Activity mContext) {
        this.mContext = mContext;

        try {
            typeface = TypefaceCache.getInstance().getTypeface(mContext.getString(R.string.font_roboto_regular));
        } catch (Exception e) {
            e.printStackTrace();
        }
//        mVibrator = (Vibrator) mContext.getSystemService(Context.VIBRATOR_SERVICE);

        setMessageDialog();
    }

    public void dismiss() {
        if (alertDialog != null)
            alertDialog.dismiss();
    }

    public void show(String message, long time) {

/*
        setMessageDialog();
        txtMessage.setText(message);
        dialogPopupMessage.show();

        try {
            if (time != 0)
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dialogPopupMessage.dismiss();
                    }
                }, time * 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
*/
        setMessageDialog(message);
        alertDialog.show();
    }

    public void show(String title, String message, long time) {

        setMessageDialog(title, message);
        alertDialog.show();

        try {
            if (time != 0)
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        alertDialog.dismiss();
                    }
                }, time * 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void show(String message, long time, String positiveButtonLabel) {

        setMessageDialog(message, time, positiveButtonLabel);
        alertDialog.show();

        try {
            if (time != 0)
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        alertDialog.dismiss();
                    }
                }, time * 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void show(String title, String message, long time, String positiveButtonLabel) {

        setMessageDialog(title, message, time, positiveButtonLabel);
        alertDialog.show();

        try {
            if (time != 0)
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        alertDialog.dismiss();
                    }
                }, time * 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void show(String message, long time, String positiveButtonLabel, String negativeButtonLabel) {

        setMessageDialog(message, time, positiveButtonLabel, negativeButtonLabel);
        alertDialog.show();

        try {
            if (time != 0)
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        alertDialog.dismiss();
                    }
                }, time * 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void show(String title, String message, long time, String positiveButtonLabel, String negativeButtonLabel) {

        setMessageDialog(title, message, time, positiveButtonLabel, negativeButtonLabel);
        alertDialog.show();

        try {
            if (time != 0)
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        alertDialog.dismiss();
                    }
                }, time * 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setMessageDialog() {

        dialogPopupMessage = new Dialog(mContext, R.style.ThemeDialogCustom);
        dialogPopupMessage.setContentView(R.layout.dialog_message_popup);

        attachDialogToBase(dialogPopupMessage);

        Button btnOK = (Button) dialogPopupMessage.findViewById(R.id.btn_dialog_message_popup_ok);
        txtMessage = (TextView) dialogPopupMessage.findViewById(R.id.txt_dialog_message_popup);

        btnOK.setTypeface(typeface);

        btnOK.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                //mVibrator.vibrate(25);
                try {
                    popupActionListener.actionCompletedSuccessfully(true);
                } catch (Exception e) {
                }
                dialogPopupMessage.dismiss();
            }
        });
    }

    private void attachDialogToBase(Dialog dialog) {
        setDialog(dialog);

        setBaseDialogListener(new BaseDialogListener() {
            @Override
            public void actionFailed() {
                try {
                    popupActionListener.actionFailed();
                } catch (Exception e) {
                }
            }
        });
    }

    private void setMessageDialog(String message) {

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext, R.style.ThemeDialogCustom);
        builder.setMessage(message);
        builder.setCancelable(true);
        builder.setPositiveButton(mContext.getString(R.string.btn_ok), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                //mVibrator.vibrate(25);
                try {
                    popupActionListener.actionCompletedSuccessfully(true);
                } catch (Exception e) {
                }
                dialog.dismiss();
            }
        });

        alertDialog = builder.create();
        attachDialogToBase(alertDialog);

    }


    private void setMessageDialog(String title, String message) {

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext, R.style.ThemeDialogCustom);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setCancelable(true);
        builder.setPositiveButton(mContext.getString(R.string.btn_ok), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                //mVibrator.vibrate(25);
                try {
                    popupActionListener.actionCompletedSuccessfully(true);
                } catch (Exception e) {
                }
                dialog.dismiss();
            }
        });
        alertDialog = builder.create();
        attachDialogToBase(alertDialog);

    }

    private void setMessageDialog(String message, long time, String positiveButtonLabel) {

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext, R.style.ThemeDialogCustom);
        builder.setMessage(message);
        builder.setCancelable(true);
        builder.setPositiveButton(positiveButtonLabel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                //mVibrator.vibrate(25);
                try {
                    popupActionListener.actionCompletedSuccessfully(true);
                } catch (Exception e) {
                }
                dialog.dismiss();
            }
        });
/*
        builder.setNegativeButton(mContext.getString(R.string.btn_cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int paramInt) {
                //mVibrator.vibrate(25);
                try {
                    popupActionListener.actionFailed();
                } catch (Exception e) {
                }
                dialog.dismiss();
            }
        });
*/
        alertDialog = builder.create();
        attachDialogToBase(alertDialog);

    }

    private void setMessageDialog(String title, String message, long time, String positiveButtonLabel) {

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext, R.style.ThemeDialogCustom);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setCancelable(true);
        builder.setPositiveButton(positiveButtonLabel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                //mVibrator.vibrate(25);
                try {
                    popupActionListener.actionCompletedSuccessfully(true);
                } catch (Exception e) {
                }
                dialog.dismiss();
            }
        });
/*
        builder.setNegativeButton(mContext.getString(R.string.btn_cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int paramInt) {
                //mVibrator.vibrate(25);
                try {
                    popupActionListener.actionFailed();
                } catch (Exception e) {
                }
                dialog.dismiss();
            }
        });
*/
        alertDialog = builder.create();
        attachDialogToBase(alertDialog);

    }

    private void setMessageDialog(String message, long time, String positiveButtonLabel, String negativeButtonLabel) {

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext, R.style.ThemeDialogCustom);
        builder.setMessage(message);
        builder.setCancelable(true);
        builder.setPositiveButton(positiveButtonLabel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                //mVibrator.vibrate(25);
                try {
                    popupActionListener.actionCompletedSuccessfully(true);
                } catch (Exception e) {
                }
                dialog.dismiss();
            }
        });
        builder.setNegativeButton(negativeButtonLabel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int paramInt) {
                //mVibrator.vibrate(25);
                try {
                    popupActionListener.actionFailed();
                } catch (Exception e) {
                }
                dialog.dismiss();
            }
        });
        alertDialog = builder.create();
        attachDialogToBase(alertDialog);

    }

    private void setMessageDialog(String title, String message, long time, String positiveButtonLabel, String negativeButtonLabel) {

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext, R.style.ThemeDialogCustom);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setCancelable(true);
        builder.setPositiveButton(positiveButtonLabel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                //mVibrator.vibrate(25);
                try {
                    popupActionListener.actionCompletedSuccessfully(true);
                } catch (Exception e) {
                }
                dialog.dismiss();
            }
        });
        builder.setNegativeButton(negativeButtonLabel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int paramInt) {
                //mVibrator.vibrate(25);
                try {
                    popupActionListener.actionFailed();
                } catch (Exception e) {
                }
                dialog.dismiss();
            }
        });
        alertDialog = builder.create();
        attachDialogToBase(alertDialog);

    }

    public interface PopupActionListener {
        void actionCompletedSuccessfully(boolean result);

        void actionFailed();
    }

    public PopupActionListener getPopupActionListener() {
        return popupActionListener;
    }


    public void setPopupActionListener(PopupActionListener popupActionListener) {
        this.popupActionListener = popupActionListener;
    }


}
