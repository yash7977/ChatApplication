package com.example.chatapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class Profile extends AppCompatActivity {
    TextView UserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        UserName = findViewById(R.id.UserName);

        UserDomain userDomain =(UserDomain) getIntent().getSerializableExtra("userDomain");
        UserName.setText(userDomain.firstName+" "+userDomain.lastName);
    }
}
