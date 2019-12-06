package com.example.chatapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.List;

public class TripListAdapter extends RecyclerView.Adapter<TripListAdapter.ViewHolder> {

    List<TripDomain> tripDomainList;
    Context context;
    FirebaseFirestore db = FirebaseFirestore.getInstance();


     String CurrentUser;
    SharedPreferences prefs;

    public TripListAdapter(List<TripDomain> tripDomainList, Context context) {
        this.tripDomainList = tripDomainList;
        this.context = context;

        prefs = context.getSharedPreferences(
                "com.example.chatapplication", Context.MODE_PRIVATE);
        CurrentUser = prefs.getString("CurrentUser",null);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.trip_list_view, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.TripName.setText(tripDomainList.get(position).getName());
        holder.TripDate.setText("DATE");
        Picasso.get().load(tripDomainList.get(position).CoverPic).into(holder.TripCover);
        if(tripDomainList.get(position).getUsers().contains(CurrentUser)){
            holder.JoinTrip.setVisibility(View.GONE);
        }else{
            holder.JoinTrip.setVisibility(View.VISIBLE);
        }

        holder.JoinTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                tripDomainList.get(position).getUsers().add(CurrentUser);
                DocumentReference documentReference = db.collection("Trips").document(tripDomainList.get(position).UniqueId);
                documentReference.update("users",tripDomainList.get(position).getUsers());
                holder.JoinTrip.setVisibility(View.GONE);
            }
        });


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (tripDomainList.get(position).getUsers().contains(CurrentUser)) {
                    Intent intent = new Intent(context, TripChatActivity.class);
                    intent.putExtra("TripId",tripDomainList.get(position).getUniqueId());
                    context.startActivity(intent);
                }else {
                    Toast.makeText(context, "Not A Member", Toast.LENGTH_SHORT).show();
                }
            }

        });


    }

    @Override
    public int getItemCount() {
        return tripDomainList.size();
    }


    public  class ViewHolder extends RecyclerView.ViewHolder {


        TextView TripName ;
        TextView TripDate ;
        ImageView TripCover ;
        Button JoinTrip;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            TripName =itemView.findViewById(R.id.TripName);
            TripDate = itemView.findViewById(R.id.TripDate);
            TripCover = itemView.findViewById(R.id.TripCover);
            JoinTrip = itemView.findViewById(R.id.JoinTrip);


        }




    }



}
