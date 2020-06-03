package com.example.final_exam;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        if (findViewById(R.id.fragment_container_menu) != null) {

            if (savedInstanceState != null) return;

            // Create a new Fragment to be placed in the activity layout
            StartFragment firstFragment = new StartFragment();

            // In case this activity was started with special instructions from an
            // Intent, pass the Intent's extras to the fragment as arguments
            firstFragment.setArguments(getIntent().getExtras());

            // Add the fragment to the 'fragment_container' FrameLayout
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container_menu, firstFragment).commit();
        }
    }
    public void replaceFragment (Fragment newFragment){
        // Create fragment and give it an argument specifying the article it should show

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack so the user can navigate back
        transaction.replace(R.id.fragment_container_menu, newFragment);
        transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 9876) {
            if (resultCode == RESULT_OK) {
                String contents = data.getStringExtra("SCAN_RESULT");
                Bundle bundle = new Bundle();
                bundle.putString("content", contents);
                CreateCitaFragment fragment = new CreateCitaFragment();
                fragment.setArguments(bundle);
                this.replaceFragment(fragment);
            }
            if(resultCode == RESULT_CANCELED){
                Toast.makeText(this, getString(R.string.cancelado), Toast.LENGTH_SHORT).show();
            }
        }
    }
}
