package com.example.chatapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;

public class GroupTab extends Fragment {

    View view1;
    private RecyclerView mRecyclerView;
    ArrayList<TripDomain> list = new ArrayList<>();
//    Context mContext;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


//        mContext = getActivity().getApplicationContext();

         view1= inflater.inflate(R.layout.activity_groups_tab, container, false);
        mRecyclerView=view1.findViewById(R.id.GroupsList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        FirebaseFirestore db = FirebaseFirestore.getInstance();


        db.collection("Trips").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {

                    for (QueryDocumentSnapshot document : task.getResult()) {


                        TripDomain tripDomain = new TripDomain();
                        tripDomain.users=new ArrayList<>();
                        tripDomain.setUniqueId(document.getData().get("uniqueId").toString());
                        tripDomain.setName(document.getData().get("name").toString());
                        tripDomain.setCoverPic(document.getData().get("coverPic").toString());
                        System.out.println("TRIPDOMAIN UESRE "+Arrays.asList(document.getData().get("users")).get(0));
                        if(document.getData().get("users")!=null){
                            for (int i=0;i<((ArrayList) document.getData().get("users")).size();i++){
                                tripDomain.users.add(((ArrayList) document.getData().get("users")).get(i).toString());
                            }
                        }




                        list.add(tripDomain);
                    }
                    TripListAdapter tripListAdapter = new TripListAdapter(list,getContext());
                    mRecyclerView.setAdapter(tripListAdapter);


                } else {
                    Log.d("TAG", "Error getting documents: ", task.getException());
                }
            }
        });
        return view1;
    }
}

