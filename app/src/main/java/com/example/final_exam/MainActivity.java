package com.example.final_exam;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView menu;
    Fragment current;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setViewComponents();

        if (findViewById(R.id.fragment_container_menu) != null) {
            if (savedInstanceState != null) return;
            // Create a new Fragment to be placed in the activity layout
            ListaCitasFragment firstFragment = new ListaCitasFragment(getApplicationContext());

            // In case this activity was started with special instructions from an
            firstFragment.setArguments(getIntent().getExtras());

            // Add the fragment to the 'fragment_container' FrameLayout
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container_menu, firstFragment).commit();
        }
    }

    public void setViewComponents(){
        menu = findViewById(R.id.bottom_navigation);
        menu.setOnNavigationItemSelectedListener(
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.page_1:
                            replaceFragment( new ListaCitasFragment(getApplicationContext()));
                            return true;
                        case R.id.page_2:
                            replaceFragment( new MapFragment());
                            return true;
                        case R.id.page_3:
                            replaceFragment( new CreateCitaLoggedInFragment());
                            return true;
                    }
                    return false;
                }
            });
    }

    public void replaceFragment(Fragment newFragment){
        current = newFragment;
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container_menu, newFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void setSelectedMenu(int a){
        menu.setSelectedItemId(a);
    }

    @Override
    public void onBackPressed() {
        if(current instanceof ListaCitasFragment){
            Intent i = new Intent(this, StartActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            this.startActivity(i);
            return;
        }
        setSelectedMenu(R.id.page_1);
    }
}
