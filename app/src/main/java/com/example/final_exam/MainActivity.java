package com.example.final_exam;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.KeyEvent;

public class MainActivity extends AppCompatActivity {
    public Fragment previousFragment;
    public Fragment currentCentralFragment;
    private int container = R.id.fragment_container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
    }
}
