package com.example.zakahdeserved.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.zakahdeserved.ChildFragment.Programms_Tab1;
import com.example.zakahdeserved.ChildFragment.Programms_Tab2Fidaa;
import com.example.zakahdeserved.ChildFragment.Programms_Tab3hayat;
import com.example.zakahdeserved.ChildFragment.Programms_Tab4amal;

import com.example.zakahdeserved.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;


public class MusicPage extends Fragment {
    private TabLayout tabLayout;
    private ViewPager viewpager_programms;
    private FragmentActivity myContext;
    public MusicPage() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.music_page, container, false);
        viewpager_programms = (ViewPager)v.findViewById(R.id.viewpager_programms);
        setupViewPager(viewpager_programms);

        tabLayout = (TabLayout)v.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewpager_programms);
        return v;
    }
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new Programms_Tab1(), "البرامج");
        adapter.addFragment(new Programms_Tab2Fidaa(), "فداء");
        adapter.addFragment(new Programms_Tab3hayat(), "حياة");
        adapter.addFragment(new Programms_Tab4amal(), "أمل");
        viewPager.setAdapter(adapter);
    }
    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

}