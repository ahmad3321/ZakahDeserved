package com.example.zakahdeserved.Utility;

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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;


public class ValidationController {
    public static boolean ENABLE_FEMALE_TAB = true;
    public static boolean ENABLE_ALL_TABS = true;
    public static String ExceptionQuery = "";

    //تعديل
//    public static ArrayList<String> subTables = new ArrayList<>() {
//        {
//            add("aids");
//            add("assets");
//            add("health_statuses");
//            add("incomes");
//            add("water_types");
//            add("survey_conclusions");
//            add("husbands");
//            add("housing_informations");
//        }
//    };

    public static void lockThePage(View view) {
        final ViewGroup viewGroup = (ViewGroup) view;
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
        final ViewGroup viewGroup = (ViewGroup) view;
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
                    Constants.SQLITEDAL.addQuery(ExceptionQuery, "", "exception");
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
}
