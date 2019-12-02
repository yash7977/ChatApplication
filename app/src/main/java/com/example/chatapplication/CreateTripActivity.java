package com.example.chatapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.UUID;

public class CreateTripActivity extends AppCompatActivity {
    Button StartLocation;
    Button Destination;
    EditText TripName;
    Button CreateTrip;
    ImageView TripCoverPhoto;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    int REQUEST_IMAGE_CAPTURE=123;
    Bitmap imageupload=null;



    TripDomain tripDomain = new TripDomain();


    final ArrayList<String> resultArray = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_trip);





        TripCoverPhoto=findViewById(R.id.TripCoverPhoto);
        StartLocation = findViewById(R.id.StartLocation);
        Destination = findViewById(R.id.Destination);
        TripName = findViewById(R.id.TripName);
        CreateTrip = findViewById(R.id.CreateTrip);
        tripDomain.users = new ArrayList<>();
        tripDomain.messagesDomains = new ArrayList<>();


        Gson gson = new Gson();
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(this);
        final String CurrentUser = sharedPreferences.getString("CurrentUser",null);


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

        TripCoverPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }
            }
        });





        CreateTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String uniqueKey = String.valueOf(UUID.randomUUID());

                Bitmap bitmap = ((BitmapDrawable) TripCoverPhoto.getDrawable()).getBitmap();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                byte[] TripCoverByte = baos.toByteArray();
                FirebaseStorage storage = FirebaseStorage.getInstance();
                final StorageReference storageRef = storage.getReference().child("TripsCover/" + uniqueKey + ".png");
                UploadTask uploadTask = storageRef.putBytes(TripCoverByte);


                uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {

                            @Override
                            public void onSuccess(Uri uri) {
                                tripDomain.setCoverPic(uri.toString());
                                tripDomain.setName(TripName.getText().toString());
                                tripDomain.setCreatedBy(CurrentUser);
                                tripDomain.setUniqueId(uniqueKey);
                                tripDomain.users.add(CurrentUser);
                                db.collection("Trips").document(tripDomain.UniqueId).set(tripDomain)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Intent intent = new Intent(CreateTripActivity.this,TabsExample.class);
                                                System.out.println("TripDomain"+tripDomain.toString());
                                                startActivity(intent);
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.w("TAG", "Error writing document", e);
                                            }
                                        });

                            }
                        });
                    }
                });






            }
        });




    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (data.hasExtra("data") && requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");

            imageupload=imageBitmap;
            TripCoverPhoto.setImageBitmap(imageBitmap);

        }




        else if(data.getStringExtra("resultCode").equals("1")){
            LatLng latLng =(LatLng) data.getExtras().get("latlong");
            System.out.println("RESULT");
            System.out.println(data.getStringExtra("resultCode")+data.getExtras().get("latlong").toString());
            tripDomain.start_postion= (LatLng) data.getExtras().get("latlong");

        }
        else{
            LatLng latLng =(LatLng) data.getExtras().get("latlong");
            System.out.println("RESULT");
            System.out.println(data.getStringExtra("resultCode")+data.getExtras().get("latlong").toString());
            tripDomain.destination = (LatLng) data.getExtras().get("latlong");
        }




    }
}
