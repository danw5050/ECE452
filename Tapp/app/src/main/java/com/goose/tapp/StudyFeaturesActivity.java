package com.goose.tapp;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

public class StudyFeaturesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String Question = "What's the difference between Strategy and Visitor Design Patterns?";
        String Answer = "The strategy pattern is like a 1:many relationship." +
                "When there is one type of object and I want to apply multiple operations to it, I use the strategy pattern." +
                " For example, if I have a Video class that encapsulates a video clip, I might want to compress it in different ways." +
                "So I create a bunch of strategy classes." +
                "\n" + "I think of the visitor pattern as a many:many relationship." +
                "Let's say my application grows to to include not just video, but audio clips as well." +
                "If I stick with the strategy pattern, I have to duplicate my compression classes - one for video and one for audio";

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study_features);

        AlertDialog alertDialog = new AlertDialog.Builder(StudyFeaturesActivity.this).create();
        alertDialog.setTitle(Question);
        alertDialog.setMessage(Answer);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }
}
