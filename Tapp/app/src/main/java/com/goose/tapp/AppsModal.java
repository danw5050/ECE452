package com.goose.tapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

public class AppsModal implements AppsAdapter.OnItemClicked {

    private AppsModalListener listener;
    private AlertDialog alertDialog;

    AppsModal(Context context, String title) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);

        // Set up the apps input
        RecyclerView.LayoutManager recyclerViewLayoutManager = new GridLayoutManager(context, 1);
        RecyclerView recyclerView = new RecyclerView(context);
        recyclerView.setLayoutManager(recyclerViewLayoutManager);
        RecyclerView.Adapter adapter = new AppsAdapter(context, new ApkInfoExtractor(context).GetAllInstalledApkInfo());
        recyclerView.setAdapter(adapter);
        ((AppsAdapter) adapter).setOnClick(AppsModal.this);

        builder.setView(recyclerView);

        // Set up the confirmation buttons
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                listener.modalCancel();
                dialog.cancel();
            }
        });

        alertDialog = builder.create();
        alertDialog.show();
    }

    public interface AppsModalListener {
        void modalResponse(String appName, String packageName);
        void modalCancel();
    }

    public void setAppsModalListener(AppsModalListener listener) {
        this.listener = listener;
    }

    @Override
    public void onItemClick(String appName, String packageName) {
        listener.modalResponse(appName, packageName);
        alertDialog.dismiss();
    }
}