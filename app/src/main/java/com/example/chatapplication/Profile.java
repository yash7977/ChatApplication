package com.example.chatapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.util.UUID;

public class Profile extends AppCompatActivity {

    ImageView ProfilePicture;
    EditText FirstName;
    EditText LastName;
    EditText Email;
    EditText UserName;
    Button Save;
    EditText Password;
    EditText ConfirmPassword;



    String personName;
    String personGivenName;
    String personFamilyName;
    String personEmail;
    String personId;
    String personPhoto;

    int REQUEST_IMAGE_CAPTURE=123;
    Bitmap imageupload=null;
    UUID uuid;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ProfilePicture = findViewById(R.id.ProfilePicture);
        FirstName  = findViewById(R.id.FirstName);
        LastName = findViewById(R.id.LastName);
        Email = findViewById(R.id.Email);
        UserName = findViewById(R.id.UserName);
        Save = findViewById(R.id.Save);
        Password = findViewById(R.id.Password);
        ConfirmPassword =findViewById(R.id.ConfirmPassword);





        Intent intent = getIntent();
        GoogleSignInAccount acct = (GoogleSignInAccount) intent.getParcelableExtra("acct");



        personName = acct.getDisplayName();
        personGivenName = acct.getGivenName();
        personFamilyName = acct.getFamilyName();
        personEmail = acct.getEmail();
        personId = acct.getId();
        personPhoto = String.valueOf(acct.getPhotoUrl());


        FirstName.setText(personGivenName);
        LastName.setText(personFamilyName);
        Email.setText(personEmail);
        Picasso.get().load(personPhoto).into(ProfilePicture);


        ProfilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }
            }
        });








        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(FirstName.getText()==null){
                    FirstName.setError("Cannot be null");
                }
                if(LastName.getText()==null){
                    LastName.setError("Cannot be null");
                }

                if(Email.getText()==null){
                    Email.setError("Cannot be null");
                }

                if(UserName.getText()==null){
                    UserName.setError("Cannot be null");
                }

                if(Password.getText()==null){
                    Password.setError("Cannot be null");
                }

                if(ConfirmPassword.getText()==null){
                    ConfirmPassword.setError("Cannot be null");
                }

                if(!Password.getText().toString().equals(ConfirmPassword.getText().toString())){
                    Password.setError("Password Missmatch");
                    ConfirmPassword.setError("Password Missmatch");
                }









                DocumentReference docRef = db.collection("Users").document(personId);



                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document != null && document.exists()) {
                                Log.d("DOCREFFFF: ", document.getString("uniqueId"));//Print the name
                                Bitmap bitmap = ((BitmapDrawable) ProfilePicture.getDrawable()).getBitmap();
                                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                                byte[] data = baos.toByteArray();
                                FirebaseStorage storage = FirebaseStorage.getInstance();
                                StorageReference storageRef = storage.getReference().child("ProfilePictures/"+personId+".png");
                                UploadTask uploadTask = storageRef.putBytes(data);

                                UserDomain userDomain = new UserDomain(FirstName.getText().toString(), LastName.getText().toString(), Email.getText().toString(), UserName.getText().toString(), personPhoto, document.getString("uniqueId"),Password.getText().toString());
                                System.out.println("USERDOMAIN"+userDomain.toString());
                                db.collection("Users").document(personId).set(userDomain);


                            } else {
                                Bitmap bitmap = ((BitmapDrawable) ProfilePicture.getDrawable()).getBitmap();
                                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                                byte[] data = baos.toByteArray();
                                FirebaseStorage storage = FirebaseStorage.getInstance();
                                StorageReference storageRef = storage.getReference().child("ProfilePictures/"+personId+".png");
                                UploadTask uploadTask = storageRef.putBytes(data);
                                uuid= UUID.randomUUID();
                                UserDomain userDomain = new UserDomain(FirstName.getText().toString(), LastName.getText().toString(), Email.getText().toString(), UserName.getText().toString(), personPhoto, uuid.toString(),Password.getText().toString());
                                System.out.println("USERDOMAIN"+userDomain.toString());
                                db.collection("Users").document(personId).set(userDomain);
                            }
                        } else {
                            Log.d("TAG", "get failed with ", task.getException());
                        }
                    }
                });





                /*

                 */


            }
        });
    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");

            imageupload=imageBitmap;
            ProfilePicture.setImageBitmap(imageBitmap);

        }
    }






}
