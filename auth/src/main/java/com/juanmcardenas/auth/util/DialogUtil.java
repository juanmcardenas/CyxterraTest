package com.juanmcardenas.auth.util;

import android.content.Context;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.view.ContextThemeWrapper;

import com.juanmcardenas.auth.R;

/**
 * Created by Martin Cardenas on 2019-08-16.
 */
public class DialogUtil {

    public static void showLocationPermissionRequestDialog(Context context) {
        new AlertDialog.Builder(new ContextThemeWrapper(context, R.style.CyxDialog))
                .setTitle(R.string.location_permission_dialog_title)
                .setMessage(R.string.location_permission_dialog_messate)
                .setPositiveButton(R.string.ok, (dialog, which) -> dialog.dismiss())
                .create()
                .show();
    }

    public static void showErrorDialog(Context context, String errorCode) {
        new AlertDialog.Builder(new ContextThemeWrapper(context, R.style.CyxDialog))
                .setTitle(R.string.error_title)
                .setMessage(R.string.operation_not_successfull + " " + errorCode)
                .setPositiveButton(R.string.ok, (dialog, which) -> dialog.dismiss())
                .create()
                .show();
    }

}
