package com.goose.tapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.CheckBox;
import android.widget.SeekBar;
import android.widget.TextView;

public class SeekbarModal {

    private SeekbarModalListener listener;

    SeekbarModal(Context context, String title, int initialState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);

        // Set up the text input
        final SeekBar input = new SeekBar(context);
        input.setMax(100);
        input.setProgress(initialState);
        builder.setView(input);

        // Set up the confirmation buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                listener.modalResponse(input.getProgress());
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                listener.modalCancel();
                dialog.cancel();
            }
        });

        builder.show();
    }

    public interface SeekbarModalListener {
        void modalResponse(int state);
        void modalCancel();
    }

    public void setSeekbarModalListener(SeekbarModalListener listener) {
        this.listener = listener;
    }
}