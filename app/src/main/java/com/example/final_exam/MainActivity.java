package com.example.final_exam;

import androidx.appcompat.app.AppCompatActivity;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    ImageView addCitaMenu, listaCitaMenu, mapMenu;
    private int[] icons = {R.id.iv_list_cita_icon,R.id.iv_map_icon,R.id.iv_create_icon};
    private static final int NUM_PAGES = 3;
    private ViewPager mPager;
    private PagerAdapter pagerAdapter;

    View.OnClickListener menuClickListener = view -> {
        int idIconMenu = view.getId();
        setSelected(idIconMenu);
    };

    ViewPager.OnPageChangeListener scrollListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) { }

        @Override
        public void onPageSelected(int position) {
            changeIconColors(icons[position]);
        }

        @Override
        public void onPageScrollStateChanged(int state) { }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setViewComponents();
        mPager = (ViewPager) findViewById(R.id.pager);
        pagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(pagerAdapter);
        mPager.addOnPageChangeListener(scrollListener);

        changeIconColors(icons[0]);
    }

    public void setViewComponents(){
        addCitaMenu = findViewById(R.id.iv_create_icon);
        listaCitaMenu = findViewById(R.id.iv_list_cita_icon);
        mapMenu = findViewById(R.id.iv_map_icon);
        mapMenu.setOnClickListener(menuClickListener);
        addCitaMenu.setOnClickListener(menuClickListener);
        listaCitaMenu.setOnClickListener(menuClickListener);
    }

    private void changeIconColors(int i){
        setTint( R.id.iv_map_icon,  R.color.white);
        setTint( R.id.iv_create_icon,   R.color.white);
        setTint( R.id.iv_list_cita_icon,   R.color.white);
        setTint( i,   R.color.red_accent_3);
    }
    public void setSelected(int i) {
        changeIconColors(i);
        switch (i) {
            case R.id.iv_map_icon:
                mPager.setCurrentItem(1);
                break;
            case R.id.iv_create_icon:
                mPager.setCurrentItem(2);
                break;
            case R.id.iv_list_cita_icon:
            default:
                mPager.setCurrentItem(0);
                break;
        }
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

    @Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
    }

    /**
     * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
     * sequence.
     */
    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }
        @Override
        public Fragment getItem(int position) {
            Log.d("PAGER", ""+position);
            switch (position) {
                case 1:
                    return new MapFragment();
                case 2:
                    return new CreateCitaFragment();
                case 0:
                default:
                    return new ListaCitasFragment();
            }
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }
}

