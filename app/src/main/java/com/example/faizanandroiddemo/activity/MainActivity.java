package com.example.faizanandroiddemo.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.room.Room;
import androidx.viewpager.widget.ViewPager;

import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.faizanandroiddemo.R;
import com.example.faizanandroiddemo.fragment.fragmentHome;
import com.example.faizanandroiddemo.fragment.fragmentProfile;
import com.example.faizanandroiddemo.model.User;
import com.example.faizanandroiddemo.model.UserDataBase;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.tabs.TabLayout;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity  {
    // UI Elements
    private ViewPager viewpager;
    private TabLayout tabs;


    // list
    private List<User> userList = new ArrayList<>();

    //variables
    private int[] tabIcons = {
            R.drawable.ic_home,
            R.drawable.ic_profile,
    };

    // interface
    public FragmentRefreshListener getFragmentRefreshListener() {
        return fragmentRefreshListener;
    }

    public void setFragmentRefreshListener(FragmentRefreshListener fragmentRefreshListener) {
        this.fragmentRefreshListener = fragmentRefreshListener;
    }

    private FragmentRefreshListener fragmentRefreshListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    public void init() {
        viewpager = findViewById(R.id.viewpager);
        tabs = findViewById(R.id.tabs);
        setupViewPager(viewpager);

    }



    private void setupViewPager(ViewPager viewpager) {
        MyPagerAdapter adapter = new MyPagerAdapter(getSupportFragmentManager());

        adapter.addFrag(new fragmentHome(), "");
        adapter.addFrag(new fragmentProfile(), "");
        viewpager.setAdapter(adapter);


        // set tabs icon
        tabs.setupWithViewPager(viewpager);
        tabs.getTabAt(0).setIcon(tabIcons[0]);
        tabs.getTabAt(1).setIcon(tabIcons[1]);

        // set tabs divider
        View root = tabs.getChildAt(0);
        if (root instanceof LinearLayout) {
            ((LinearLayout) root).setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
            GradientDrawable drawable = new GradientDrawable();
            drawable.setColor(getResources().getColor(R.color.white));
            drawable.setSize(2, 1);
            ((LinearLayout) root).setDividerPadding(10);
            ((LinearLayout) root).setDividerDrawable(drawable);
        }

    }


    private class MyPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public MyPagerAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
//            return mFragmentList.get(position);

            switch (position) {
                case 0:
                    Log.i("MainActivity", "LIST_SIZE: " + userList.size());
                    Bundle bundle = new Bundle();
                    fragmentHome weatherFragment = new fragmentHome();
                    bundle.putSerializable("USERLIST", (Serializable) userList);
                    weatherFragment.setArguments(bundle);
                    return weatherFragment;
                case 1:
                    return new fragmentProfile();
                default:
                    return new fragmentHome();
            }

        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    public interface FragmentRefreshListener{
        void onRefresh(List<User> userList);
    }
}