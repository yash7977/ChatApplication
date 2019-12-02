package com.example.chatapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class TripListAdapter extends RecyclerView.Adapter<TripListAdapter.ViewHolder> {

    List<TripDomain> tripDomainList;
    Context context;


    public TripListAdapter(List<TripDomain> tripDomainList, Context context) {
        this.tripDomainList = tripDomainList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.trip_list_view, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.TripName.setText(tripDomainList.get(position).getName());
        holder.TripDate.setText("DATE");
        Picasso.get().load(tripDomainList.get(position).getCoverPic()).into(holder.TripCover);

    }

    @Override
    public int getItemCount() {
        return tripDomainList.size();
    }


    public  class ViewHolder extends RecyclerView.ViewHolder {


        TextView TripName ;
        TextView TripDate ;
        ImageView TripCover ;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            TripName =itemView.findViewById(R.id.TripName);
            TripDate = itemView.findViewById(R.id.TripDate);
            TripCover = itemView.findViewById(R.id.TripCover);
        }
    }
}
