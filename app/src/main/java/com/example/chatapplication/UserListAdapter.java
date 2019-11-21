package com.example.chatapplication;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class UserListAdapter extends ArrayAdapter<UserDomain> {
    List<UserDomain> userDomainList;
    Context context;

    public UserListAdapter(@NonNull Context context, List<UserDomain> userDomains) {

        super(context, R.layout.user_list_view);
        this.context=context;
        this.userDomainList=userDomains;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemRowView =inflater.inflate(R.layout.user_list_view,parent,false);

        TextView Name = itemRowView.findViewById(R.id.Name);
        TextView UserId = itemRowView.findViewById(R.id.UserId);
        ImageView ProfilePicture = itemRowView.findViewById(R.id.ProfilePicture);
        Name.setText(userDomainList.get(position).firstName);
        UserId.setText(userDomainList.get(position).userName);
        Picasso.get().load(userDomainList.get(position).profice_pic).into(ProfilePicture);
        return itemRowView;
    }


    @Override
    public int getCount()
    {
        return userDomainList.size();
    }
}
