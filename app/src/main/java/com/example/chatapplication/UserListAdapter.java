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





//    public UserListAdapter(@NonNull Context context, List<UserDomain> userDomains) {
//
//        super(context, R.layout.user_list_view);
//        this.context=context;
//        this.userDomainList=userDomains;
//    }

//    @NonNull
//    @Override
//    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//        LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View itemRowView =inflater.inflate(R.layout.user_list_view,parent,false);
//
//        TextView Name = itemRowView.findViewById(R.id.Name);
//        TextView UserId = itemRowView.findViewById(R.id.UserId);
//        ImageView ProfilePicture = itemRowView.findViewById(R.id.ProfilePicture);
//        Name.setText(userDomainList.get(position).firstName+" "+userDomainList.get(position).getLastName());
//        UserId.setText(userDomainList.get(position).userName);
//        //System.out.println(userDomainList.get(position).toString());
//        if(!String.valueOf(userDomainList.get(position).profice_pic).equals(null)){
//            Picasso.get().load(String.valueOf(userDomainList.get(position).profice_pic)).into(ProfilePicture);
//            System.out.println("ListView: "+userDomainList.get(position).profice_pic);
//        }
//
//        return itemRowView;
//    }
//
//
//    @Override
//    public int getCount()
//    {
//        return userDomainList.size();
//    }
}
