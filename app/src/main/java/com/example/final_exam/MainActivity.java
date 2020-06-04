package com.example.final_exam;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Build;
import android.os.Bundle;

import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView menu;

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
                            replaceFragment( new CreateCitaFragment());
                            return true;
                    }
                    return false;
                }
            });
    }

    public void setTint(int id, int color){
        ((ImageView)(findViewById(id))).setColorFilter(ContextCompat.getColor(this, color));
    }

    public void replaceFragment(Fragment newFragment){
        // Create fragment and give it an argument specifying the article it should show

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack so the user can navigate back
        transaction.replace(R.id.fragment_container_menu, newFragment);
        transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();
    }


}
