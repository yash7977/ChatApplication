package com.example.chatapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

public class DisplayProfile extends AppCompatActivity {

    TextView profileName,userEmail,username;
    ImageView profilePic;
    Button backButton;
    SharedPreferences sharedPreferences;
    String userName,email,name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_profile);

        profileName=findViewById(R.id.profilename);
        userEmail=findViewById(R.id.displayemail);
        username=findViewById(R.id.displayUsername);
        profilePic=findViewById(R.id.displayimg);
        backButton=findViewById(R.id.displayback);

//        sharedPreferences = PreferenceManager
//                .getDefaultSharedPreferences(DisplayProfile.this);
//        String  CurrentUser = sharedPreferences.getString("CurrentUser",null);
//        System.out.println(CurrentUser);



        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });




    }
}
