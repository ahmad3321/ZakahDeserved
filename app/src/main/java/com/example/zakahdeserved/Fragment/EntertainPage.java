package com.example.zakahdeserved.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Spinner;

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
import java.util.Objects;


public class EntertainPage extends Fragment {

    private ViewPager viewPager;

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
                if (Objects.equals(Constants.PackageType, "تعديل")) {
                    switch (position) {
                        case 0:
                            // lock all the controls
                            ValidationController.lockThePage(Constants.view1);
                            // enables to_edit controls
                            ValidationController.EnableOnlyToEditFields(Constants.view1, "persons");
                            ValidationController.EnableOnlyToEditFields(Constants.view1, "families");
                            if (Constants.toEditFields.containsKey("persons") && Constants.toEditFields.get("persons").contains("IdentityFile")) {
                                Constants.view1.findViewById(R.id.btn_Image_Document_delete).setEnabled(true);
                                Constants.view1.findViewById(R.id.btn_Image_Document).setEnabled(true);
                            } else {
                                Constants.view1.findViewById(R.id.btn_Image_Document_delete).setEnabled(false);
                                Constants.view1.findViewById(R.id.btn_Image_Document).setEnabled(false);
                            }
                            break;
                        case 1:
                            ValidationController.lockThePage(Constants.view2);
                            ValidationController.EnableOnlyToEditFields(Constants.view2, "families");
                            break;
                        case 2:
                            ValidationController.lockThePage(Constants.view4);
                            ValidationController.EnableOnlyToEditFields(Constants.view4, "health_statuses");
                            Constants.view4.findViewById(R.id.button_add).setEnabled(true);

                            if (Constants.toEditFields.containsKey("health_statuses") && Constants.toEditFields.get("health_statuses").contains("MonthlyCost"))
                                ValidationController.EnableFieledInView(Constants.view4, "CoinType");
                            break;
                        case 3:
                            ValidationController.lockThePage(Constants.view5);
                            ValidationController.EnableOnlyToEditFields(Constants.view5, "husbands");
                            break;
                        case 4:
                            ValidationController.lockThePage(Constants.view6);
                            ValidationController.EnableOnlyToEditFields(Constants.view6, "housing_informations");
                            ValidationController.EnableOnlyToEditFields(Constants.view6, "water_types");
                            ValidationController.EnableOnlyToEditFields(Constants.view6, "incomes");
                            ValidationController.EnableOnlyToEditFields(Constants.view6, "aids");

                            Constants.view6.findViewById(R.id.button_add_waterType).setEnabled(true);
                            Constants.view6.findViewById(R.id.button_add_Incomes).setEnabled(true);
                            Constants.view6.findViewById(R.id.button_add_Aids).setEnabled(true);

                            Constants.view6.findViewById(R.id.RentValueCoinType).setEnabled(Constants.view6.findViewById(R.id.RentValue).isEnabled());
                            Constants.view6.findViewById(R.id.AmpValueCoinType).setEnabled(Constants.view6.findViewById(R.id.OneAmpValue).isEnabled());
                            Constants.view6.findViewById(R.id.ConsumptionValueCoinType).setEnabled(Constants.view6.findViewById(R.id.ConsumptionValue).isEnabled());

                            if (Constants.toEditFields.containsKey("water_types") && Constants.toEditFields.get("water_types").contains("MonthlyValue"))
                                ValidationController.EnableFieledInView(Constants.view6.findViewById(R.id.layout_list_waterType), "CoinType");
                            if (Constants.toEditFields.containsKey("incomes") && Constants.toEditFields.get("incomes").contains("IncomeValue"))
                                ValidationController.EnableFieledInView(Constants.view6.findViewById(R.id.layout_list_Incomes), "CoinType");
                            if (Constants.toEditFields.containsKey("aids") && Constants.toEditFields.get("aids").contains("AidValue"))
                                ValidationController.EnableFieledInView(Constants.view6.findViewById(R.id.layout_list_Aids), "CoinType");
                            break;
                        case 5:
                            ValidationController.lockThePage(Constants.view7);
                            ValidationController.EnableOnlyToEditFields(Constants.view7, "assets");
                            Constants.view7.findViewById(R.id.button_add_Asset).setEnabled(true);

                            if (Constants.toEditFields.containsKey("assets") && Constants.toEditFields.get("assets").contains("BenefitValue"))
                                ValidationController.EnableFieledInView(Constants.view7, "CoinType");
                            break;
                        case 6:
                            ValidationController.lockThePage(Constants.view8);
                            // من أجل التمييز بين الحقول الخاصة ب رب الأسرة والحقول الخاصة بأفراد الأسرة
                            // الجدول الذي يجوي الجقول الخاصة بأفراد الأسرة تم زيادة ! له فأصبح person! وكذلك جدول الحالات الصحية أصبحhealth_statuses!
                            ValidationController.EnableOnlyToEditFields(Constants.view8, "persons!");
                            ValidationController.EnableOnlyToEditFields(Constants.view8, "health_statuses!");

                            // enable images buttons
                            if (Constants.toEditFields.containsKey("persons!") && Constants.toEditFields.get("persons!").contains("IdentityFile")) {
                                ValidationController.EnableFieledInView(Constants.view8, "btn_Image_Document_Person");
                                ValidationController.EnableFieledInView(Constants.view8, "btn_Image_Document_Person_delete");
                                ValidationController.EnableFieledInView(Constants.view8, "CoinType");
                            }

                            Constants.view8.findViewById(R.id.button_add_Wifes).setEnabled(true);

                            //enable cointypes for helth status of each person
                            if (Constants.toEditFields.containsKey("health_statuses!") && Constants.toEditFields.get("health_statuses!").contains("MonthlyCost")) {
                                final ViewGroup viewGroup = Constants.view8.findViewById(R.id.layout_list_Wifes);
                                int count = viewGroup.getChildCount();
                                for (int i = 0; i < count; i++) {
                                    View v = viewGroup.getChildAt(i);
                                    ValidationController.EnableFieledInView(v.findViewById(R.id.layout_list_Wifes_HealthStatus), "CoinType");
                                }
                            }
                            break;
                        case 7:
                            ValidationController.lockThePage(Constants.view9);
                            ValidationController.EnableOnlyToEditFields(Constants.view9, "families");
                            ValidationController.EnableOnlyToEditFields(Constants.view9, "survey_conclusions");
                            Constants.view9.findViewById(R.id.button_submit_list).setEnabled(true);
                            Constants.view9.findViewById(R.id.buttonAdd_SurveyConclusions).setEnabled(true);
                            break;
                    }
                } else {
                    switch (position) {
                        case 1:
                            if (ValidationController.needToEnable[0] != 2) {
                                if (ValidationController.needToEnable[0] == 0)
                                    ValidationController.lockThePage(Constants.view2);
                                else
                                    ValidationController.UnlockThePage(Constants.view2);
                                ValidationController.needToEnable[0] = 2;
                            }
                            break;
                        case 2:
                            if (ValidationController.needToEnable[1] != 2) {
                                if (ValidationController.needToEnable[1] == 0)
                                    ValidationController.lockThePage(Constants.view4);
                                else
                                    ValidationController.UnlockThePage(Constants.view4);
                                ValidationController.needToEnable[1] = 2;
                                LinearLayout layoutList = Constants.view4.findViewById(R.id.layout_list);
                                for(int i=0;i<layoutList.getChildCount();i++){
                                    View view = layoutList.getChildAt(i);
                                    if(((Spinner)view.findViewById(R.id.HealthStatus)).getSelectedItem().equals("جيد")){ //good
                                        view.findViewById(R.id.HealthStatusEvaluation).setEnabled(false);
                                        view.findViewById(R.id.HealthStatusType).setEnabled(false);
                                        view.findViewById(R.id.HealthStatusDescription).setEnabled(false);
                                        view.findViewById(R.id.MonthlyCost).setEnabled(false);
                                        view.findViewById(R.id.CoinType).setEnabled(false);
                                    }
                                }
                            }
                            break;
                        case 3:
                            if (ValidationController.needToEnable[2] != 2) {
                                if (ValidationController.needToEnable[2] == 0)
                                    ValidationController.lockThePage(Constants.view5);
                                else
                                    ValidationController.UnlockThePage(Constants.view5);
                                ValidationController.needToEnable[2] = 2;
                            }
                            break;
                        case 4:
                            if (ValidationController.needToEnable[3] != 2) {
                                if (ValidationController.needToEnable[3] == 0)
                                    ValidationController.lockThePage(Constants.view6);
                                else
                                    ValidationController.UnlockThePage(Constants.view6);
                                ValidationController.needToEnable[3] = 2;

                            }
                            break;
                        case 5:
                            if (ValidationController.needToEnable[4] != 2) {
                                if (ValidationController.needToEnable[4] == 0)
                                    ValidationController.lockThePage(Constants.view7);
                                else
                                    ValidationController.UnlockThePage(Constants.view7);
                                ValidationController.needToEnable[4] = 2;
                            }
                            break;
                        case 6:
                            if (ValidationController.needToEnable[5] != 2) {
                                if (ValidationController.needToEnable[5] == 0)
                                    ValidationController.lockThePage(Constants.view8);
                                else
                                    ValidationController.UnlockThePage(Constants.view8);
                                ValidationController.needToEnable[5] = 2;
                                LinearLayout layout_list_Wifes = Constants.view8.findViewById(R.id.layout_list_Wifes);
                                for(int i=0;i<layout_list_Wifes.getChildCount();i++){
                                    View view_health = layout_list_Wifes.getChildAt(i);
                                    LinearLayout layout_list_health = view_health.findViewById(R.id.layout_list_Wifes_HealthStatus);
                                    for(int j=0;j<layout_list_health.getChildCount();j++){
                                        View view = layout_list_health.getChildAt(j);
                                        if(((Spinner)view_health.findViewById(R.id.HealthStatus)).getSelectedItem().equals("جيد")){ //good
                                            view.findViewById(R.id.HealthStatusEvaluation).setEnabled(false);
                                            view.findViewById(R.id.HealthStatusType).setEnabled(false);
                                            view.findViewById(R.id.HealthStatusDescription).setEnabled(false);
                                            view.findViewById(R.id.MonthlyCost).setEnabled(false);
                                            view.findViewById(R.id.CoinType).setEnabled(false);
                                        }
                                    }
                                }
                            }
                            break;
                        case 7:
                            if (ValidationController.needToEnable[6] != 2) {
                                if (ValidationController.needToEnable[6] == 0) {
                                    ValidationController.lockThePage(Constants.view9);
                                    Constants.view9.findViewById(R.id.button_submit_list).setEnabled(true);
                                }
                                else
                                    ValidationController.UnlockThePage(Constants.view9);
                                ValidationController.needToEnable[6] = 2;
                            }
                            break;
                    }
                }
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