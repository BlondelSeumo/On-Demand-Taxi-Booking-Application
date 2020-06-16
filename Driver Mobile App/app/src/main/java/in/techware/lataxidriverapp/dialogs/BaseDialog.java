package in.techware.lataxidriverapp.dialogs;

import android.app.Dialog;
import android.content.DialogInterface;

/**
 * Created by Jemsheer K D on 03 August, 2018.
 * Package in.techware.carrefour.dialogs
 * Project Carrefour
 */
public class BaseDialog {

    private Dialog dialog;
    private BaseDialogListener baseDialogListener;


    public Dialog getDialog() {
        return dialog;
    }

    public void setDialog(Dialog dialog) {
        this.dialog = dialog;
    }

    public void setCanceledOnTouchOutside(boolean isCanceledOnTouchOutside) {
        if (dialog != null) {
            dialog.setCanceledOnTouchOutside(isCanceledOnTouchOutside);
        }
    }

    public void setCancellable(boolean isCancellable) {
        if (dialog != null) {
            dialog.setCancelable(isCancellable);
        }
    }

    private void setDismissListener() {
        if (dialog != null) {
            dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialogInterface) {
                    if (baseDialogListener != null)
                        baseDialogListener.actionFailed();
                }
            });
            dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialogInterface) {
                    if (baseDialogListener != null)
                        baseDialogListener.actionFailed();
                }
            });
        }
    }

    public interface BaseDialogListener {
        void actionFailed();
    }

    public BaseDialogListener getBaseDialogListener() {
        return baseDialogListener;
    }


    public void setBaseDialogListener(
            BaseDialogListener baseDialogListener) {
        this.baseDialogListener = baseDialogListener;
    }
}
