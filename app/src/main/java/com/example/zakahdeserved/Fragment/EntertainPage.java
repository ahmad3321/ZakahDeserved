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
import com.example.zakahdeserved.Utility.ValidationController;
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

        //Enable and disable controls in views as required
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Log.d("TABSELECT", "pos = " + position);
                if (!ValidationController.ENABLE_ALL_TABS) {
                    if (position == 1) ValidationController.lockThePage(Constants.view2);
                    else if (position == 2) ValidationController.lockThePage(Constants.view4);
                    else if (position == 3) ValidationController.lockThePage(Constants.view5);
                    else if (position == 4) ValidationController.lockThePage(Constants.view6);
                    else if (position == 5) ValidationController.lockThePage(Constants.view7);
                    else if (position == 6) ValidationController.lockThePage(Constants.view8);
                    else if (position == 7) ValidationController.lockThePage(Constants.view9);

                    //enable submit button after disable all controls
                    ValidationController.UnlockThePage(Constants.view9.findViewById(R.id.button_submit_list));
                } else {
                    if (position == 1) ValidationController.UnlockThePage(Constants.view2);
                    else if (position == 2) ValidationController.UnlockThePage(Constants.view4);
                    else if (position == 3) ValidationController.UnlockThePage(Constants.view5);
                    else if (position == 4) ValidationController.UnlockThePage(Constants.view6);
                    else if (position == 5) ValidationController.UnlockThePage(Constants.view7);
                    else if (position == 6) ValidationController.UnlockThePage(Constants.view8);
                    else if (position == 7) ValidationController.UnlockThePage(Constants.view9);
                }

                if (!ValidationController.ENABLE_FEMALE_TAB)
                    ValidationController.lockThePage(Constants.view5);
                else if (ValidationController.ENABLE_ALL_TABS)
                    ValidationController.UnlockThePage(Constants.view5);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
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