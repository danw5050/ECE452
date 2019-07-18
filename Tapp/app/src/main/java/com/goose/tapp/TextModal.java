package com.goose.tapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.InputType;
import android.widget.EditText;

public class TextModal {

    private TextModalListener listener;

    TextModal(Context context, String title, String hint, String initialText) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);

        // Set up the text input
        final EditText input = new EditText(context);
        input.setHint(hint);
        input.setText(initialText);

        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        builder.setView(input);

        // Set up the confirmation buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                listener.modalResponse(input.getText().toString());
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

    public interface TextModalListener {
        void modalResponse(String text);
    }

    public void setTextModalListener(TextModalListener listener) {
        this.listener = listener;
    }
}
