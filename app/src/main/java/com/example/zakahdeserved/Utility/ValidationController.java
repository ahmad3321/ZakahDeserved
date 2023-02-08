package com.example.zakahdeserved.Utility;

import android.text.InputFilter;
import android.text.Spanned;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatSpinner;

import com.example.zakahdeserved.Connection.DAL;
import com.example.zakahdeserved.Connection.DBHelper;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;


public class ValidationController {
    public static boolean ENABLE_FEMALE_TAB = true;
    //    public static boolean ENABLE_ALL_TABS = true;
    public static String ExceptionQuery = "";


    // 0:Disable the view, 1:Enable the view, 2:do nothing
    // only 7 views need to enable/disable, view1 always enabled
    public static int[] needToEnable = {2, 2, 2, 2, 2, 2, 2};

    public static void lockThePage(View view) {
        final ViewGroup viewGroup;
        try {
            viewGroup = (ViewGroup) view;
        } catch (Exception ex) {
            view.setEnabled(false);
            return;
        }
        try {
            int count = viewGroup.getChildCount();
            for (int i = 0; i < count; i++) {
                View v = viewGroup.getChildAt(i);
                if (v instanceof EditText || v instanceof Spinner || v instanceof AppCompatSpinner
                        || v instanceof CheckBox || v instanceof TextView)
                    v.setEnabled(false);

                else if (v instanceof LinearLayout || v instanceof ScrollView || v instanceof RelativeLayout || v instanceof FrameLayout)
                    lockThePage(v);

            }
        } catch (Exception e) {
            ValidationController.GetException(e.toString().replace("\"", ""), "lockThePage()", "", view.toString());
            e.printStackTrace();
        }
    }

    public static void UnlockThePage(View view) {
        final ViewGroup viewGroup;
        try {
            viewGroup = (ViewGroup) view;
        } catch (Exception ex) {
            view.setEnabled(true);
            return;
        }
        try {
            int count = viewGroup.getChildCount();
            for (int i = 0; i < count; i++) {
                View v = viewGroup.getChildAt(i);
                if (v instanceof EditText || v instanceof Spinner || v instanceof AppCompatSpinner
                        || v instanceof CheckBox || v instanceof TextView)
                    v.setEnabled(true);

                else if (v instanceof LinearLayout || v instanceof ScrollView || v instanceof RelativeLayout || v instanceof FrameLayout)
                    UnlockThePage(v);
            }
        } catch (Exception e) {
            ValidationController.GetException(e.toString().replace("\"", ""), "UnlockThePage()", "", view.toString());
            e.printStackTrace();
        }
    }

    public static String updateLabel(Date Date) {
        String date = "";
        String myFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        date = sdf.format(Date.getTime());
        return date;
    }

    public static void ClearView(View view) {
        final ViewGroup viewGroup = (ViewGroup) view;
        try {
            int count = viewGroup.getChildCount();
            for (int i = 0; i < count; i++) {
                View v = viewGroup.getChildAt(i);

                if (v instanceof EditText)
                    ((EditText) v).setText("");
                else if (v instanceof Spinner || v instanceof AppCompatSpinner)
                    ((Spinner) v).setSelection(0);
                else if (v instanceof CheckBox)
                    ((CheckBox) v).setChecked(false);

                else if (v instanceof LinearLayout || v instanceof ScrollView || v instanceof RelativeLayout || v instanceof FrameLayout)
                    ClearView(v);
            }
        } catch (Exception e) {
            ValidationController.GetException(e.toString().replace("\"", ""), "ClearView()", "", view.toString());
            e.printStackTrace();
        }
    }

    public static void GetException(String ExceptionDescrip, String ExceptionLine, String ExceptionContext, String Notes) {
        try {
            ExceptionQuery = "Insert into Exception Values(0,\"" + ExceptionDescrip + "\",\"" + ExceptionLine + "\"," +
                    "\"" + ExceptionContext + "\",\"" + Notes + "\");";
            if (ExceptionDescrip.length() > 10) {
                boolean isSucces = DAL.executeQueries(ExceptionQuery);
                if (!isSucces) {
                    Constants.SQLITEDAL.addQuery(ExceptionQuery, Constants.PackageID, Constants.ZakatID, Constants.PackagePersonID, "exception");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //تعديل
    public static void EnableOnlyToEditFields(View view, String tableName) {

        // check if table exists in to edit tables
        if (!Constants.toEditFields.containsKey(tableName))
            return;

        final ViewGroup viewGroup = (ViewGroup) view;
        try {
            int count = viewGroup.getChildCount();
            for (int i = 0; i < count; i++) {
                View v = viewGroup.getChildAt(i);

                if (v instanceof LinearLayout || v instanceof ScrollView || v instanceof RelativeLayout || v instanceof FrameLayout)
                    EnableOnlyToEditFields(v, tableName);
                else if (v instanceof EditText || v instanceof Spinner || v instanceof AppCompatSpinner || v instanceof CheckBox)
                    if (Objects.requireNonNull(Constants.toEditFields.get(tableName)).contains(v.getResources().getResourceEntryName(v.getId())))
                        v.setEnabled(true);
            }
        } catch (Exception e) {
            ValidationController.GetException(e.toString().replace("\"", ""), "UnlockThePage()", "", view.toString());
            e.printStackTrace();
        }
    }


    public static void EnableFieledInView(View view, String fieledName) {

        final ViewGroup viewGroup = (ViewGroup) view;
        try {
            int count = viewGroup.getChildCount();
            for (int i = 0; i < count; i++) {
                View v = viewGroup.getChildAt(i);

                if (v instanceof LinearLayout || v instanceof ScrollView || v instanceof RelativeLayout || v instanceof FrameLayout)
                    EnableFieledInView(v, fieledName);
                else if (v instanceof EditText || v instanceof Spinner || v instanceof AppCompatSpinner || v instanceof CheckBox || v instanceof Button)
                    if (Objects.equals(v.getResources().getResourceEntryName(v.getId()), fieledName))
                        v.setEnabled(true);
            }
        } catch (Exception e) {
            ValidationController.GetException(e.toString().replace("\"", ""), "UnlockThePage()", "", view.toString());
            e.printStackTrace();
        }
    }

    public static class InputFilterMinMax implements InputFilter {

        private int min, max;

        public InputFilterMinMax(int min, int max) {
            this.min = min;
            this.max = max;
        }

        public InputFilterMinMax(String min, String max) {
            this.min = Integer.parseInt(min);
            this.max = Integer.parseInt(max);
        }

        private boolean isInRange(int a, int b, int c) {
            return b > a ? c >= a && c <= b : c >= b && c <= a;
        }

        @Override
        public CharSequence filter(CharSequence charSequence, int start, int end, Spanned spanned, int dstart, int dend) {
            try {
                int input = Integer.parseInt(spanned.toString() + charSequence.toString());
                if (isInRange(min, max, input))
                    return null;
            } catch (NumberFormatException nfe) {
            }
            return "";
        }
    }
}
