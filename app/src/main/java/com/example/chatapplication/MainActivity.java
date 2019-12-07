package com.example.chatapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN = 9001;
    private static final String TAG = "GoogleActivity";


    SharedPreferences prefs=null;
    SharedPreferences.Editor editor = null;
    String uuid = UUID.randomUUID().toString();




    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SignInButton signInButton = findViewById(R.id.sign_in_button);
        mAuth=FirebaseAuth.getInstance();
        //signInButton.setSize(SignInButton.SIZE_STANDARD);
        prefs = this.getSharedPreferences("com.example.chatapplication", Context.MODE_PRIVATE);
        editor = prefs.edit();


        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.server_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient (MainActivity.this, gso);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                final GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);

                if(account!=null){
                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    DocumentReference reference = db.collection("Users").document(account.getId());
                    reference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()){
                                DocumentSnapshot document = task.getResult();
                                if(document.exists()){

                                    Intent intent = new Intent(MainActivity.this,TabsExample.class);
                                    String uniqueId = (String) document.getData().get("uniqueId");
                                    editor.putString("CurrentUser",uniqueId);
                                    editor.putString("PersonId",account.getId());
                                    editor.apply();
                                    startActivity(intent);
                                }else{


                                    UserDomain userDomain = new UserDomain(account.getGivenName(),account.getFamilyName(),account.getEmail(),account.getDisplayName(),null,account.getPhotoUrl().toString(), uuid);
                                    Intent intent = new Intent(MainActivity.this,Profile.class);
                                    intent.putExtra("userDomain",userDomain);
                                    intent.putExtra("personId_intent",account.getId());
                                    editor.putString("CurrentUser",uuid);
                                    editor.putString("PersonId",account.getId());
                                    editor.apply();
                                    startActivity(intent);
                                }
                            }
                        }
                    });

                }
            } catch (ApiException e) {

                Log.w(TAG, "Google sign in failed", e);
                updateUI(null);
            }

        }

    }


    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            updateUI(null);
                        }

                    }
                });
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


    private void signIn() {


        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void updateUI(FirebaseUser user) {



    }
}
/*
Client Id: 290050356453-o6esep8f0dj29na53t4i2kdik4d3mecd.apps.googleusercontent.com
Client Secrete: m14j3ce6VYQrF8oSpI13ddgB
*/
//JSON ClientId: 507080580814-k9mne56bjo1isgip1v5s9b64e2kifau0.apps.googleusercontent.com