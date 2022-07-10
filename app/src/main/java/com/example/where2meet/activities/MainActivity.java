package com.example.where2meet.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.where2meet.R;
import com.example.where2meet.databinding.ActivityMainBinding;
import com.example.where2meet.fragments.CalendarFragment;
import com.example.where2meet.fragments.ProfileFragment;
import com.example.where2meet.fragments.SearchFragment;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.parse.ParseUser;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding activityMainBinding;
    final FragmentManager fragmentManager = getSupportFragmentManager();
    public static final int REQUEST_CODE = 100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = activityMainBinding.getRoot();
        setContentView(view);
        getLocation();

// listener for bottomNavigation items
        activityMainBinding.bottomNavigation.setOnItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment;
                switch (item.getItemId()) {
                    case R.id.action_profile:
                        fragment = new ProfileFragment();
                        break;
                    case R.id.action_search:
                        fragment = new SearchFragment();
                        break;
                    case R.id.action_calendar:
                        fragment = new CalendarFragment();
                        break;
                    default:
                        fragment = new ProfileFragment();
                        break;
                }
                fragmentManager.beginTransaction().replace(R.id.flContainer, fragment).commit();
                return true;
            }
        });
        //set default selection
        activityMainBinding.bottomNavigation.setSelectedItemId(R.id.action_profile);
    }

// points to specific menu resource file for this activity
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    //performs a particular action when a specific menu item is selected
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.logout){
            Toast.makeText(MainActivity.this, "logout successful", Toast.LENGTH_SHORT).show();
            onLogout();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

// log's out the current user and navigate back to the login screen
    public void onLogout() {
        ParseUser.logOut();
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
        finish();
    }

    private void getLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            Task<Location> fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this).getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {
                        Geocoder geocoder = new Geocoder(MainActivity.this, Locale.getDefault());
                        try {
                            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
//                            Bundle bundle = new Bundle();
//                            bundle.putString("currentLocation", addresses.get(0).getAddressLine(0));
//                            ProfileFragment profileFragment = new ProfileFragment();
//                            profileFragment.setArguments(bundle);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }}
                }
            });
        } else {
            askpermission();
        }
    }

    private void askpermission() {
        // Todo : appears the method of requesting permissions is deprecated, try to update as soon as you can
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == REQUEST_CODE){
            if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                getLocation();
            }
            else{
                Toast.makeText(this, "Required permissions", Toast.LENGTH_SHORT).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }



}