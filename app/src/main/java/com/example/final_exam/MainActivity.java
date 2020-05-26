package com.example.final_exam;

import androidx.appcompat.app.AppCompatActivity;
<<<<<<< HEAD
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.KeyEvent;

public class MainActivity extends AppCompatActivity {
    public Fragment previousFragment;
    public Fragment currentCentralFragment;
    private int container = R.id.fragment_container;
=======
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    ImageView addCitaMenu, listaCitaMenu, mapMenu;

    View.OnClickListener menuClickListener = view -> {
        int idIconMenu = view.getId();
        setSelected(idIconMenu);
    };
>>>>>>> a51851edaf8db1f8bca9a16bda1647978d96042b

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
<<<<<<< HEAD

        replaceFragment(new LoginFragment());
    }

    public void replaceFragment(Fragment f){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if(currentCentralFragment == null || !currentCentralFragment.getClass().equals(f.getClass())){
            previousFragment = currentCentralFragment;
            currentCentralFragment = f;
            fragmentTransaction.replace(container, f);
            fragmentTransaction.commit();
        }
    }

    //Hardware Back Button method
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK) {
            replaceFragment(previousFragment);
            return true;
        }

        return false;
=======
        setViewComponents();
        setTint( R.id.iv_list_cita_icon, R.color.red_accent_3);
        if (findViewById(R.id.fragment_container_menu) != null) {
            if (savedInstanceState != null) return;
            // Create a new Fragment to be placed in the activity layout
            ListaCitasFragment firstFragment = new ListaCitasFragment();

            // In case this activity was started with special instructions from an
            firstFragment.setArguments(getIntent().getExtras());

            // Add the fragment to the 'fragment_container' FrameLayout
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container_menu, firstFragment).commit();
        }
    }
    public void setViewComponents(){
        addCitaMenu = findViewById(R.id.iv_create_icon);
        listaCitaMenu = findViewById(R.id.iv_list_cita_icon);
        mapMenu = findViewById(R.id.iv_map_icon);
        mapMenu.setOnClickListener(menuClickListener);
        addCitaMenu.setOnClickListener(menuClickListener);
        listaCitaMenu.setOnClickListener(menuClickListener);
    }

    public void setSelected(int i) {
        setTint( R.id.iv_map_icon,  R.color.white);
        setTint( R.id.iv_create_icon,   R.color.white);
        setTint( R.id.iv_list_cita_icon,   R.color.white);
        setTint( i,  R.color.red_accent_3);
        switch (i) {
            case R.id.iv_map_icon:
                replaceFragment( new MapFragment());
                break;
            case R.id.iv_create_icon:
                replaceFragment( new CreateCitaFragment());
                break;
            case R.id.iv_list_cita_icon:
            default:
                replaceFragment( new ListaCitasFragment());
                break;
        }
    }

    public void setTint(int id, int color){
        ((ImageView)(findViewById(id))).setColorFilter(ContextCompat.getColor(this, color));
>>>>>>> a51851edaf8db1f8bca9a16bda1647978d96042b
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
