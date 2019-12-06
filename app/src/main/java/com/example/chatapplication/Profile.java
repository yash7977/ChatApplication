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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.Gson;
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

    int REQUEST_IMAGE_CAPTURE=123;
    Bitmap imageupload=null;
    private FirebaseAuth mAuth;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String databaseProfileUrl;
    DocumentSnapshot document;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ProfilePicture = findViewById(R.id.ProfilePicture);
        FirstName = findViewById(R.id.FirstName);
        LastName = findViewById(R.id.LastName);
        Email = findViewById(R.id.Email);
        UserName = findViewById(R.id.UserName);
        Save = findViewById(R.id.Save);
        Password = findViewById(R.id.Password);
        ConfirmPassword = findViewById(R.id.ConfirmPassword);


        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(Profile.this);
        final SharedPreferences.Editor editor = sharedPreferences.edit();


        mAuth = FirebaseAuth.getInstance();

        Intent intent = getIntent();
        final UserDomain acct = (UserDomain) intent.getSerializableExtra("userDomain");
        System.out.println("PROFILEACTIVITY: "+acct.toString());
        final String personId_intent = intent.getStringExtra("personId_intent");
        final String uuid = UUID.randomUUID().toString();









//        personName = nul;
//        personGivenName = acct.getGivenName();
//        personFamilyName = acct.getFamilyName();
//        personEmail = acct.getEmail();
//        personId = personId_intent;
//        personPhoto = String.valueOf(acct.getProfice_pic());


        FirstName.setText(acct.getFirstName());
        LastName.setText(acct.getLastName());
        Email.setText(acct.getEmail());
        Picasso.get().load(acct.getProfice_pic()).into(ProfilePicture);
        UserName.setText(acct.getUserName());


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
//                if (FirstName.getText().equals(null)) {
//                    FirstName.setError("Cannot be null");
//                } if (LastName.getText().equals(null)) {
//                    LastName.setError("Cannot be null");
//                } if (Email.getText().equals(null)) {
//                    Email.setError("Cannot be null");
//                } if (UserName.getText().equals(null)) {
//                    UserName.setError("Cannot be null");
//                } if (Password.getText().equals(null)) {
//                    Password.setError("Cannot be null");
//                } if (ConfirmPassword.getText().equals(null)) {
//                    ConfirmPassword.setError("Cannot be null");
//                } if (!Password.getText().toString().equals(ConfirmPassword.getText().toString()) && !Password.getText().equals(null)) {
//                    Password.setError("Password Missmatch");
//                    ConfirmPassword.setError("Password Missmatch");
//                } else {


                DocumentReference docRef = db.collection("Users").document(acct.getUniqueId());

                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()){
                            document = task.getResult();
                            Bitmap bitmap = ((BitmapDrawable) ProfilePicture.getDrawable()).getBitmap();


                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                            byte[] data = baos.toByteArray();
                            FirebaseStorage storage = FirebaseStorage.getInstance();
                            final StorageReference storageRef = storage.getReference().child("ProfilePictures/" + personId_intent + ".png");
                            final UploadTask uploadTask = storageRef.putBytes(data);



                            storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    UserDomain userDomain = new UserDomain(FirstName.getText().toString(), LastName.getText().toString(), Email.getText().toString(), UserName.getText().toString(), Password.getText().toString(), uri.toString(), acct.getUniqueId());
                                    db.collection("Users").document(personId_intent).set(userDomain);
                                    Intent intent1 = new Intent(Profile.this, TabsExample.class);
                                    startActivity(intent1);
                                }
                            });
                        }
                    }
                });




//                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                        if (task.isSuccessful()) {
//                            document = task.getResult();
//                            if (document != null && document.exists()) {
//                                Bitmap bitmap = ((BitmapDrawable) ProfilePicture.getDrawable()).getBitmap();
//
//
//                                ByteArrayOutputStream baos = new ByteArrayOutputStream();
//                                bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
//                                byte[] data = baos.toByteArray();
//                                FirebaseStorage storage = FirebaseStorage.getInstance();
//                                final StorageReference storageRef = storage.getReference().child("ProfilePictures/" + personId + ".png");
//                                final UploadTask uploadTask = storageRef.putBytes(data);
//
//                                storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//
//                                    @Override
//                                    public void onSuccess(Uri uri) {
//                                        UserDomain userDomain = new UserDomain(FirstName.getText().toString(), LastName.getText().toString(), Email.getText().toString(), UserName.getText().toString(), Password.getText().toString(), uri.toString(), document.getString("uniqueId"));
//                                        db.collection("Users").document(personId).set(userDomain);
//                                    }
//                                });
//                                mAuth.createUserWithEmailAndPassword(Email.getText().toString(), Password.getText().toString())
//                                        .addOnCompleteListener(Profile.this, new OnCompleteListener<AuthResult>() {
//                                            @Override
//                                            public void onComplete(@NonNull Task<AuthResult> task) {
//                                                if (task.isSuccessful()) {
//                                                    // Sign in success, update UI with the signed-in user's information
//                                                    Log.d("TAG", "createUserWithEmail:success");
//                                                    FirebaseUser user = mAuth.getCurrentUser();
//                                                } else {
//                                                    // If sign in fails, display a message to the user.
//                                                    Log.w("TAG", "createUserWithEmail:failure", task.getException());
//                                                    Toast.makeText(Profile.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
//                                                }
//
//                                            }
//                                        });
//
//
//                            }
//                             else {
//                                Bitmap bitmap = ((BitmapDrawable) ProfilePicture.getDrawable()).getBitmap();
//                                ByteArrayOutputStream baos = new ByteArrayOutputStream();
//                                bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
//                                byte[] data = baos.toByteArray();
//                                FirebaseStorage storage = FirebaseStorage.getInstance();
//                                StorageReference storageRef = storage.getReference().child("ProfilePictures/" + personId + ".png");
//                                UploadTask uploadTask = storageRef.putBytes(data);
//
//                                mAuth.createUserWithEmailAndPassword(Email.getText().toString(), Password.getText().toString())
//                                        .addOnCompleteListener(Profile.this, new OnCompleteListener<AuthResult>() {
//                                            @Override
//                                            public void onComplete(@NonNull Task<AuthResult> task) {
//                                                if (task.isSuccessful()) {
//                                                    // Sign in success, update UI with the signed-in user's information
//                                                    Log.d("TAG", "createUserWithEmail:success");
//                                                    FirebaseUser user = mAuth.getCurrentUser();
//                                                } else {
//                                                    // If sign in fails, display a message to the user.
//                                                    Log.w("TAG", "createUserWithEmail:failure", task.getException());
//                                                    Toast.makeText(Profile.this, "Authentication failed.",Toast.LENGTH_SHORT).show();
//                                                }
//
//                                            }
//                                        });
//
//
//
//
//
//
//
//                                storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//
//                                    @Override
//                                    public void onSuccess(Uri uri) {
//
//
//                                        UserDomain userDomain = new UserDomain(FirstName.getText().toString(), LastName.getText().toString(), Email.getText().toString(), UserName.getText().toString(), Password.getText().toString(), uri.toString(), uuid);
//                                        db.collection("Users").document(personId).set(userDomain);
//
//                                        System.out.println("ProfileActivity: " +userDomain.email);
//                                        editor.putString("CurrentUser",userDomain.email);
//
//                                        editor.commit();
//
//
//                                    }
//                                });
//
//
//                            }
//                        } else {
//                            Log.d("TAG", "failure", task.getException());
//                        }
//                    }
//                });



            }


        //}

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
