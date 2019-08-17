package com.juanmcardenas.cyxandtest.ui;

import android.content.Context;

import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.MutableLiveData;

import com.juanmcardenas.cyxandtest.R;

/**
 * Created by Martin Cardenas on 2019-08-16.
 */
public class SecurityQuestionPicker {

    private static int itemSelected = -1;

    public void show(Context context, MutableLiveData<String> selectedQuestionLiveData) {
        if (context == null) {
            return;
        }
        String[] singleChoiceItems = context.getResources().getStringArray(R.array.security_questions_array);
        new AlertDialog.Builder(context)
                .setTitle(context.getString(R.string.select_security_question))
                .setSingleChoiceItems(singleChoiceItems, itemSelected, (dialog, which) -> itemSelected = which)
                .setPositiveButton(context.getString(R.string.ok), (dialog, which) -> {
                    if (selectedQuestionLiveData != null && itemSelected >= 0) {
                        selectedQuestionLiveData.setValue(singleChoiceItems[itemSelected]);
                    }
                    dialog.dismiss();
                })
                .setNegativeButton(context.getString(R.string.cancel), (dialog, which) -> dialog.dismiss())
                .create()
                .show();
    }
}
