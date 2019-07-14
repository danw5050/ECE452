package com.goose.tapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class usersNFCListRecyclerViewAdapter extends RecyclerView.Adapter<usersNFCListRecyclerViewAdapter.ViewHolder> {

    Context context;
    List<NFCDetails> MainImageUploadInfoList;

    public usersNFCListRecyclerViewAdapter(Context context, List<NFCDetails> TempList) {

        this.MainImageUploadInfoList = TempList;

        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.nfc_list_item, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        NFCDetails studentDetails = MainImageUploadInfoList.get(position);

        holder.nfcNameTextView.setText(studentDetails.getName());

        holder.nfcTagLocationTextView.setText(studentDetails.getTagLocation());

    }

    @Override
    public int getItemCount() {

        return MainImageUploadInfoList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public TextView nfcNameTextView;
        public TextView nfcTagLocationTextView;

        public ViewHolder(View itemView) {

            super(itemView);

            nfcNameTextView = (TextView) itemView.findViewById(R.id.nfcNameTextView);
            nfcTagLocationTextView = (TextView) itemView.findViewById(R.id.nfcTagLocationTextView);
        }
    }
}