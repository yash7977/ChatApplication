package com.example.chatapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.UUID;

public class CreateTripActivity extends AppCompatActivity {
    Button StartLocation;
    Button Destination;
    EditText TripName;
    Button CreateTrip;


    TripDomain tripDomain = new TripDomain();

    final ArrayList<String> resultArray = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_trip);


        StartLocation = findViewById(R.id.StartLocation);
        Destination = findViewById(R.id.Destination);
        TripName = findViewById(R.id.TripName);
        CreateTrip = findViewById(R.id.CreateTrip);

        StartLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateTripActivity.this,MapsActivity.class);
                intent.putExtra("requestCode","1");
                startActivityForResult(intent,1);
            }
        });

        Destination.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateTripActivity.this,MapsActivity.class);
                intent.putExtra("requestCode","2");
                startActivityForResult(intent,2);
            }
        });



        CreateTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tripDomain.UniqueId = String.valueOf(UUID.randomUUID());
                tripDomain.Name = TripName.getText().toString();
                FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();


                System.out.println( FirebaseAuth.getInstance().getCurrentUser().toString());

            }
        });




    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data.getStringExtra("resultCode").equals("1")){
            LatLng latLng =(LatLng) data.getExtras().get("latlong");
            System.out.println("RESULT");
            System.out.println(data.getStringExtra("resultCode")+data.getExtras().get("latlong").toString());
            tripDomain.start_postion= (LatLng) data.getExtras().get("latlong");

        }else{
            LatLng latLng =(LatLng) data.getExtras().get("latlong");
            System.out.println("RESULT");
            System.out.println(data.getStringExtra("resultCode")+data.getExtras().get("latlong").toString());
            tripDomain.destination = (LatLng) data.getExtras().get("latlong");
        }




    }
}
