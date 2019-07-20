package com.goose.tapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.goose.tapp.NFCDetails.NFCDetails;

import java.util.List;

public class UsersNFCListRecyclerViewAdapter extends RecyclerView.Adapter<UsersNFCListRecyclerViewAdapter.ViewHolder> {

    Context context;
    List<NFCDetails> NFCDetailsList;
    private static ClickListener clickListener;

    public UsersNFCListRecyclerViewAdapter(Context context, List<NFCDetails> TempList) {

        this.NFCDetailsList = TempList;

        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Create the view for the recyclerlist item
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.nfc_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // Create the item view
        NFCDetails studentDetails = NFCDetailsList.get(position);
        holder.nfcNameTextView.setText(studentDetails.getName());
        holder.nfcTagLocationTextView.setText(studentDetails.getTagLocation());
    }

    @Override
    public int getItemCount() {
        return NFCDetailsList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener, View.OnLongClickListener {
        // Populate the recyclerview item
        public TextView nfcNameTextView;
        public TextView nfcTagLocationTextView;

        public ViewHolder(View itemView) {

            super(itemView);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
            nfcNameTextView = itemView.findViewById(R.id.nfcNameTextView);
            nfcTagLocationTextView = itemView.findViewById(R.id.nfcTagLocationTextView);
        }

        @Override
        public void onClick(View v) {
            clickListener.onItemClick(getAdapterPosition(), v);
        }

        @Override
        public boolean onLongClick(View v) {
            clickListener.onItemLongClick(getAdapterPosition(), v);
            return false;
        }
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        UsersNFCListRecyclerViewAdapter.clickListener = clickListener;
    }

    public interface ClickListener {
        void onItemClick(int position, View v);
        void onItemLongClick(int position, View v);
    }
}