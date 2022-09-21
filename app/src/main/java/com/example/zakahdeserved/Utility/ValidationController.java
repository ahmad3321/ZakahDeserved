package com.example.zakahdeserved.Utility;

import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;

import androidx.appcompat.widget.AppCompatSpinner;


public class ValidationController {
    public static boolean ENABLE_FEMALE_TAB = true;
    public static boolean ENABLE_ALL_TABS = true;

    public static void lockThePage(View view) {
        final ViewGroup viewGroup = (ViewGroup) view;
        try {
            int count = viewGroup.getChildCount();
            for (int i = 0; i < count; i++) {
                View v = viewGroup.getChildAt(i);

                if (v instanceof EditText || v instanceof Spinner || v instanceof AppCompatSpinner || v instanceof CheckBox)
                    v.setEnabled(false);

                else if (v instanceof LinearLayout || v instanceof ScrollView || v instanceof RelativeLayout || v instanceof FrameLayout)
                    lockThePage(v);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void UnlockThePage(View view) {
        final ViewGroup viewGroup = (ViewGroup) view;
        try {
            int count = viewGroup.getChildCount();
            for (int i = 0; i < count; i++) {
                View v = viewGroup.getChildAt(i);
                if (v instanceof EditText || v instanceof Spinner || v instanceof AppCompatSpinner || v instanceof CheckBox)
                    v.setEnabled(true);

                else if (v instanceof LinearLayout || v instanceof ScrollView || v instanceof RelativeLayout || v instanceof FrameLayout)
                    UnlockThePage(v);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
