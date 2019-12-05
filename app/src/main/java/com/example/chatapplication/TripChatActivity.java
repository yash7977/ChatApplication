package com.example.chatapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.MessageQueue;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;

public class TripChatActivity extends AppCompatActivity {
    ImageView SendButton;
    TextView GroupName;

    EditText Message;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_chat);
        RecyclerView recyclerView = findViewById(R.id.TripChatrecyclerView);
        SendButton = findViewById(R.id.SendButton);
        Message=findViewById(R.id.Message);
        GroupName = findViewById(R.id.GroupName);


        SharedPreferences sharedPreferences;
        final String CurrentUser;

        sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(TripChatActivity.this);
        CurrentUser = sharedPreferences.getString("CurrentUser",null);

        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        Intent intent = getIntent();
        final String tripId = intent.getStringExtra("TripId");

        final DocumentReference docRef = db.collection("Trips").document(tripId);
        final ArrayList<MessagesDomain> messagesDomainArrayList = new ArrayList<>();



        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    GroupName.setText(document.get("name").toString());
                    ArrayList<HashMap> arr= (ArrayList<HashMap>) document.getData().get("messagesDomains");
                    for (int i=0;i<arr.size();i++){
                        MessagesDomain messagesDomain1 = new MessagesDomain(arr.get(i).get("uniqueId").toString(),arr.get(i).get("message").toString(),arr.get(i).get("sentBy").toString());
                        messagesDomainArrayList.add(messagesDomain1);
                    }






                } else {
                    Log.d("TAG", "get failed with ", task.getException());
                }
            }
        });

        LinearLayoutManager llm = new LinearLayoutManager(TripChatActivity.this);
        recyclerView.setLayoutManager(llm);
        final MessagesAdapter messagesAdapter = new MessagesAdapter(messagesDomainArrayList,this,tripId);
        recyclerView.setAdapter(messagesAdapter);

        SendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final MessagesDomain messagesDomain = new MessagesDomain();
                messagesDomain.setMessage(Message.getText().toString());
                messagesDomain.setSentBy(CurrentUser);
                messagesDomain.setUniqueId(UUID.randomUUID().toString());
                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {

                            db.collection("Trips").document(tripId).update("messagesDomains", FieldValue.arrayUnion(messagesDomain));
                            messagesDomainArrayList.add(messagesDomain);
                            messagesAdapter.notifyDataSetChanged();

                        } else {
                            Log.d("TAG", "get failed with ", task.getException());
                        }
                    }
                });
            }
        });



    }
}
