package com.example.chatapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ChatActivity extends AppCompatActivity {

    RecyclerView ChatRecyclerView;
    ArrayList<UserDomain> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        ChatRecyclerView=findViewById(R.id.ChatRecyclerView);
        ChatRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        FirebaseFirestore db = FirebaseFirestore.getInstance();




        db.collection("Users").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {

                    for (QueryDocumentSnapshot document : task.getResult()) {
                        UserDomain userDomain = new UserDomain(document.getData().get("firstName").toString(),document.getData().get("lastName").toString(),document.getData().get("email").toString(),document.getData().get("userName").toString(),document.getData().get("password").toString(),document.getData().get("profice_pic").toString(),document.getData().get("uniqueId").toString());

                        list.add(userDomain);
                    }
                    //System.out.println("//////////"+list.size());
                    UserListAdapter userListAdapter = new UserListAdapter(ChatActivity.this,list);
                    ChatRecyclerView.setAdapter(userListAdapter);

                } else {
                    Log.d("TAG", "Error getting documents: ", task.getException());
                }
            }
        });




    }

}



