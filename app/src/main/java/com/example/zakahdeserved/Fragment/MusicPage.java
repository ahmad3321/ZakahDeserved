package com.example.zakahdeserved.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.zakahdeserved.ChildFragment.Programms_Tab1;
import com.example.zakahdeserved.ChildFragment.Programms_Tab2Fidaa;
import com.example.zakahdeserved.ChildFragment.Programms_Tab3hayat;
import com.example.zakahdeserved.ChildFragment.Programms_Tab4amal;
import com.example.zakahdeserved.ChildFragment.Programms_Tab5Student;
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
        setupViewPager(viewpager_programms,"KTLAL");
        tabLayout = (TabLayout)v.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewpager_programms);
        setProgramTabs("");
        return v;
    }
    private void setupViewPager(ViewPager viewPager,String Programm) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        switch (Programm){
            case "KTLAL":
                adapter.addFragment(new Programms_Tab1(), "البرامج");
            case "Fidaa":
                adapter.addFragment(new Programms_Tab2Fidaa(), "فداء");
            case "Hayat":
                adapter.addFragment(new Programms_Tab3hayat(), "حياة");
            case "Amal":
                adapter.addFragment(new Programms_Tab4amal(), "أمل");
            case "Student":
                adapter.addFragment(new Programms_Tab5Student(), "وقل ربي زدني علما");
        }
        /*adapter.addFragment(new Programms_Tab1(), "البرامج");
        adapter.addFragment(new Programms_Tab2Fidaa(), "فداء");
        adapter.addFragment(new Programms_Tab3hayat(), "حياة");
        adapter.addFragment(new Programms_Tab4amal(), "أمل");
        adapter.addFragment(new Programms_Tab5Student(), "وقل ربي زدني علما");*/
        viewPager.setAdapter(adapter);
    }
    private void setProgramTabs(String Program) {
        ((LinearLayout) tabLayout.getTabAt(0).view).setVisibility(View.GONE);
        ((LinearLayout) tabLayout.getTabAt(1).view).setVisibility(View.GONE);
        ((LinearLayout) tabLayout.getTabAt(2).view).setVisibility(View.GONE);
        ((LinearLayout) tabLayout.getTabAt(3).view).setVisibility(View.GONE);
        ((LinearLayout) tabLayout.getTabAt(4).view).setVisibility(View.GONE);
        switch (Program){
            case "KTLAL":
                ((LinearLayout) tabLayout.getTabAt(0).view).setVisibility(View.VISIBLE);
            case "Fidaa":
                ((LinearLayout) tabLayout.getTabAt(1).view).setVisibility(View.VISIBLE);
            case "Hayat":
                ((LinearLayout) tabLayout.getTabAt(2).view).setVisibility(View.VISIBLE);
            case "Amal":
                ((LinearLayout) tabLayout.getTabAt(3).view).setVisibility(View.VISIBLE);
            case "Student":
                ((LinearLayout) tabLayout.getTabAt(4).view).setVisibility(View.VISIBLE);
        }
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