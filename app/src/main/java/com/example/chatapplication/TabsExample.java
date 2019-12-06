package com.example.chatapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.auth.User;
import com.google.firebase.firestore.model.Document;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class TabsExample extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    PageAdapter pageadapter;
    DrawerLayout mDrawerLayout;
    ActionBarDrawerToggle aToggle;
    NavigationView navigationView;
    View hview;
    ImageView profileImage;
    TextView profileName;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();
    SharedPreferences prefs=null;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    DocumentReference documentReference = null;
    DocumentSnapshot document;
    String personId;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabs_example);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Users"));
        tabLayout.addTab(tabLayout.newTab().setText("Trips"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        mDrawerLayout = findViewById(R.id.drawerLayout);
        aToggle = new ActionBarDrawerToggle(this,mDrawerLayout,R.string.open,R.string.close);




        mDrawerLayout.addDrawerListener(aToggle);
        aToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        NavigationView navigationView=findViewById(R.id.navigationHeader);
        navigationView.setNavigationItemSelectedListener(this);
        hview=navigationView.getHeaderView(0);

        profileImage=hview.findViewById(R.id.ProfilePicture);
        profileName=hview.findViewById(R.id.UserName);

        prefs = this.getSharedPreferences("com.example.chatapplication", Context.MODE_PRIVATE);
        personId = prefs.getString("PersonId",null);
        documentReference = db.collection("Users").document(personId);


        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    document = task.getResult();
                    Picasso.get().load(document.getData().get("profice_pic").toString()).into(profileImage);
                    profileName.setText(document.getData().get("userName").toString());
                }
            }
        });







        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(TabsExample.this);
        if(acct!=null){
            Toast.makeText(this, acct.toString(), Toast.LENGTH_SHORT).show();

        }else{
            Toast.makeText(this, "Empty Login", Toast.LENGTH_SHORT).show();
        }


        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
         pageadapter = new PageAdapter(getSupportFragmentManager(), tabLayout.getTabCount());


        viewPager.setAdapter(pageadapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });



    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (aToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);


    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.nav_account:
                Toast.makeText(this,"edit account", Toast.LENGTH_SHORT).show();
                Intent displayProfileIntent=new Intent(TabsExample.this,DisplayProfile.class);
                startActivity(displayProfileIntent);
                break;

            case R.id.nav_editProfile:
               final Intent editIntent=new Intent(TabsExample.this,Profile.class);
                documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()){
                            document = task.getResult();
                            System.out.println("DOCUMENTDATA: "+document.getData().toString());
                            UserDomain userDomain = new UserDomain(document.getData().get("firstName").toString(),document.getData().get("lastName").toString(),document.getData().get("email").toString(),document.getData().get("userName").toString(),document.getData().get("password").toString(),document.getData().get("profice_pic").toString(),document.getData().get("uniqueId").toString());
                            System.out.println("TABSEXAMPLEUSERDOMAIN: "+ userDomain.toString());
                            editIntent.putExtra("userDomain",userDomain);
                            editIntent.putExtra("personId_intent",personId);
                            startActivity(editIntent);
                        }
                    }
                });


                break;

            case R.id.nav_logout:
                Toast.makeText(this,"logdedout", Toast.LENGTH_SHORT).show();

                break;
            case R.id.create_trip:

                Intent intent=new Intent(TabsExample.this,CreateTripActivity.class);
                startActivity(intent);

                break;


        }
        return false;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
    }
}
