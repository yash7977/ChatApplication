package com.example.chatapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class GroupTab extends Fragment {


    private RecyclerView mRecyclerView;
    ArrayList<TripDomain> list = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.activity_groups_tab, container, false);
        mRecyclerView=view.findViewById(R.id.GroupsList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        FirebaseFirestore db = FirebaseFirestore.getInstance();


        db.collection("Trips").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {

                    for (QueryDocumentSnapshot document : task.getResult()) {
                        System.out.println("DOCUMENT:    "+document.getData().toString());

                        TripDomain tripDomain = new TripDomain();
                        tripDomain.setUniqueId(document.getData().get("uniqueId").toString());
                        tripDomain.setName(document.getData().get("name").toString());
                        //tripDomain.setCoverPic(document.getData().get("coverPic").toString());
                        list.add(tripDomain);
                    }
                    //System.out.println("//////////"+list.size());
                    TripListAdapter tripListAdapter = new TripListAdapter(list,getContext());
                    mRecyclerView.setAdapter(tripListAdapter);

                } else {
                    Log.d("TAG", "Error getting documents: ", task.getException());
                }
            }
        });
        return view;
    }
}


/*
String UniqueId;
    String Name;
    String CoverPic;
    String createdBy;
    LatLng start_postion;
    LatLng destination;
    ArrayList<MessagesDomain> messagesDomains;
    ArrayList<String> users;
 */