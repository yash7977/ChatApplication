package com.example.chatapplication;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

public class TabsExample extends AppCompatActivity {
    PageAdapter pageadapter;
    DrawerLayout mDrawerLayout;
    ActionBarDrawerToggle aToggle;



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




//        UserName = mDrawerLayout.findViewById(R.id.UserName);
//        ProfilePicture = mDrawerLayout.findViewById(R.id.ProfilePicture);


        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(TabsExample.this);
        if(acct!=null){
            Toast.makeText(this, acct.toString(), Toast.LENGTH_SHORT).show();
//            NavigationView navigationView =  (NavigationView) findViewById(R.id.navigationHeader);
//            navigationView.getHeaderView(0);
//            TextView UserName = navigationView.findViewById(R.id.NavigationBarHeader).findViewById(R.id.UserName);
//            UserName.setText("ABVC");

        }else{
            Toast.makeText(this, "EMPTY LOGIN", Toast.LENGTH_SHORT).show();
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
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.,menu);
//        return true;
//    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (aToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);


    }


}
