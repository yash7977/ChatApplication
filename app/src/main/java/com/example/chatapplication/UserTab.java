package com.example.chatapplication;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class UserTab extends Fragment{


    private RecyclerView mRecyclerView;
    ArrayList<UserDomain> list = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

       View view = inflater.inflate(R.layout.activity_online_user_tab, container, false);
       mRecyclerView=view.findViewById(R.id.OnlineUserRecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        FirebaseFirestore db = FirebaseFirestore.getInstance();


        db.collection("Users").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {

                    for (QueryDocumentSnapshot document : task.getResult()) {
                        UserDomain userDomain = new UserDomain(document.getData().get("firstName").toString(),document.getData().get("lastName").toString(),document.getData().get("email").toString(),document.getData().get("userName").toString(),document.getData().get("password").toString(),document.getData().get("profice_pic").toString(),document.getData().get("uniqueId").toString());

                        list.add(userDomain);
                    }
                    UserListAdapter userListAdapter = new UserListAdapter(getContext(),list);
                    mRecyclerView.setAdapter(userListAdapter);
                } else {
                    Log.d("TAG", "Error getting documents: ", task.getException());
                }
            }
        });




        return view;
    }


}
