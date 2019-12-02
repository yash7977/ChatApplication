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

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.ViewHolder> {
    List<UserDomain> userDomainList;
    Context context;


        public UserListAdapter(@NonNull Context context, List<UserDomain> userDomains) {
        this.context=context;
        this.userDomainList=userDomains;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.user_list_view, parent, false);
            ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.Name.setText(userDomainList.get(position).getUserName()+" "+userDomainList.get(position).getLastName());
            holder.UserId.setText(userDomainList.get(position).getUserName());
            Picasso.get().load(userDomainList.get(position).profice_pic).into(holder.ProfilePicture);

    }

    @Override
    public int getItemCount() {
        return userDomainList.size();
    }


    public  class ViewHolder extends RecyclerView.ViewHolder {


        TextView Name ;
        TextView UserId ;
        ImageView ProfilePicture ;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Name =itemView.findViewById(R.id.Name);
            UserId = itemView.findViewById(R.id.UserId);
            ProfilePicture = itemView.findViewById(R.id.ProfilePicture);
        }
    }

}
