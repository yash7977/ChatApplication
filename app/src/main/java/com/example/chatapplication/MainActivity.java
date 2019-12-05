package com.example.chatapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class MainActivity extends AppCompatActivity {
    GoogleSignInClient mGoogleSignInClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SignInButton signInButton = findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.server_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient (MainActivity.this, gso);


        mGoogleSignInClient.silentSignIn()
                .addOnCompleteListener(
                        this,
                        new OnCompleteListener<GoogleSignInAccount>() {
                            @Override
                            public void onComplete(@NonNull Task<GoogleSignInAccount> task) {
                                handleSignInResult(task);
                            }
                        });




        findViewById(R.id.sign_in_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, 1);

            }
        });







    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);;
        if (acct != null) {


            System.out.println("Token "+acct.getIdToken());
            System.out.println("Profile Name: "+acct.getDisplayName() );
            Intent intent = new Intent(MainActivity.this, TabsExample.class);
            intent.putExtra("acct",acct);

            startActivity(intent);
        }
    }


    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {

            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            String idToken = account.getDisplayName();
            System.out.println("IDNAME: "+idToken);
        } catch (ApiException e) {
            Log.w("TAG", "signInResult:failed code=" + e.getStatusCode());
        }
    }
}
/*
Client Id: 290050356453-o6esep8f0dj29na53t4i2kdik4d3mecd.apps.googleusercontent.com
Client Secrete: m14j3ce6VYQrF8oSpI13ddgB
*/
//JSON ClientId: 507080580814-k9mne56bjo1isgip1v5s9b64e2kifau0.apps.googleusercontent.com