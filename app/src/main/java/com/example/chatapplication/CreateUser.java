package com.example.chatapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateUser extends AppCompatActivity {

    EditText FirstName;
    EditText LastName;
    EditText UserName;
    EditText PassWord;
    EditText ConfirmPassword;
    Button CreateAccount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);

        final FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();

        FirstName = findViewById(R.id.FirstName);
        LastName = findViewById(R.id.LastName);
        UserName = findViewById(R.id.UserName);
        PassWord = findViewById(R.id.Password);
        ConfirmPassword = findViewById(R.id.ConfiremPassword);
        CreateAccount = findViewById(R.id.CreateAccount);

        CreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(PassWord.getText().toString()!=ConfirmPassword.getText().toString()){
                    Toast.makeText(CreateUser.this, "PassWord Missmatch", Toast.LENGTH_SHORT).show();
                }else{
                    UserDomain userDomain = new UserDomain(FirstName.getText().toString(),LastName.getText().toString(),UserName.getText().toString(),PassWord.getText().toString());
                    DatabaseReference mDatabaseReference = mDatabase.getReference().child("name");
                    mDatabaseReference.setValue(userDomain);
                }
            }
        });
    }
}
