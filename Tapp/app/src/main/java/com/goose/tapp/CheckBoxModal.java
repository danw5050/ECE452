package com.goose.tapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.InputType;
import android.widget.CheckBox;
import android.widget.EditText;

public class CheckBoxModal {

    private CheckBoxModalListener listener;

    CheckBoxModal(Context context, String title, String checkBoxText, Boolean initialState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);

        // Set up the text input
        final CheckBox input = new CheckBox(context);
        input.setChecked(initialState);
        input.setText(checkBoxText);
        builder.setView(input);

        // Set up the confirmation buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                listener.modalResponse(input.isChecked());
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    public interface CheckBoxModalListener {
        void modalResponse(Boolean state);
    }

    public void setCheckBoxModalListener(CheckBoxModalListener listener) {
        this.listener = listener;
    }
}
