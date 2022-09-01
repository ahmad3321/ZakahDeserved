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

import com.example.zakahdeserved.ChildFragment.Tab1;
import com.example.zakahdeserved.ChildFragment.Tab2;
import com.example.zakahdeserved.ChildFragment.Tab4;
import com.example.zakahdeserved.ChildFragment.Tab5;
import com.example.zakahdeserved.ChildFragment.Tab6;
import com.example.zakahdeserved.ChildFragment.Tab7;
import com.example.zakahdeserved.ChildFragment.Tab8;
import com.example.zakahdeserved.ChildFragment.Tab9;
import com.example.zakahdeserved.R;
import com.example.zakahdeserved.Utility.Constants;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;


public class EntertainPage extends Fragment {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private FragmentActivity myContext;

    public EntertainPage() {
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

        View v = inflater.inflate(R.layout.entertain_page, null);

        viewPager = (ViewPager)v.findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout)v.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        return v;
    }


    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new Tab1(), "ForU");
        adapter.addFragment(new Tab2(), "Webseries");
        adapter.addFragment(new Tab3(), "Trailer");
        adapter.addFragment(new Tab4(), "Shortflim");
        adapter.addFragment(new Tab5(), "Husband");
        adapter.addFragment(new Tab6(), "Housing");
        adapter.addFragment(new Tab7(), "Assets");
        adapter.addFragment(new Tab8(), "Wifes");
        adapter.addFragment(new Tab9(), "SurveyConclusions");

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