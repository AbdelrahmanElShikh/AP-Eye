package com.example.apeye.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.apeye.R;
import com.example.apeye.ui.login.Login;
import com.example.apeye.ui.login.UserInformation;
import com.example.apeye.ui.main.fragments.Fragment_Check;
import com.example.apeye.ui.main.fragments.Fragment_Com;
import com.example.apeye.ui.main.fragments.Fragment_Lib;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;


public class MainActivity extends AppCompatActivity {

    public static boolean FirstRun = true;
    public static MainActivity mainActivity;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        mainActivity = MainActivity.this;
        setContentView(R.layout.activity_main);
        firebaseAuth = FirebaseAuth.getInstance();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(this.getResources().getColor(R.color.colorPrimaryDark));

        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        if (FirstRun) {

            System.out.println("opeing frag check from main");
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new Fragment_Check()).commit();
        } else {

            System.out.println("opeing frag com from main");
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new Fragment_Com()).commit();
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment selectedFragment = null;

                    switch (menuItem.getItemId()) {
                        case R.id.nav_camera:
                            selectedFragment = new Fragment_Check();
                            break;

                        case R.id.nav_comm:
                            selectedFragment = new Fragment_Com();
                            break;

                        case R.id.nav_lib:
                            selectedFragment = new Fragment_Lib();
                            break;
                    }

                    assert selectedFragment != null;

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container
                            , selectedFragment).commit();

                    return true;
                }
            };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(firebaseAuth.getCurrentUser()!=null)
            getMenuInflater().inflate(R.menu.com_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.logout:
                logout();
                return true;

            case R.id.acc_setting:
                Intent intent = new Intent(MainActivity.this, UserInformation.class);
                startActivity(intent);
            default:
                return false;
        }
    }

    private void logout() {
        firebaseAuth.signOut();

        sendToLogin();
    }

    private void sendToLogin() {
        Intent intent = new Intent(MainActivity.this, Login.class);
        startActivity(intent);
        this.finish();
    }
}
