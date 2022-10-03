package com.example.zakahdeserved.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

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

        viewPager = v.findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        Constants.tabLayout = v.findViewById(R.id.tabs);
        Constants.tabLayout.setupWithViewPager(viewPager);

        return v;
    }


    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new Tab1(), "التقييم الأولي");
        adapter.addFragment(new Tab2(), "المعلومات الشخصية");
        adapter.addFragment(new Tab4(), "الحالة الصحية");
        adapter.addFragment(new Tab5(), "الزوج");
        adapter.addFragment(new Tab6(), "السكن");
        adapter.addFragment(new Tab7(), "الممتلكات");
        adapter.addFragment(new Tab8(), "أفراد الأسرة");
        adapter.addFragment(new Tab9(), "الجوار");
        viewPager.setOffscreenPageLimit(8);
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