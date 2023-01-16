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
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatSpinner;

import com.example.zakahdeserved.Connection.DAL;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;


public class ValidationController {
    public static boolean ENABLE_FEMALE_TAB = true;
    public static boolean ENABLE_ALL_TABS = true;
    public static String ExceptionQuery = "";

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

//    public static void EnableToEditFields(View view, String tableName) {
//        if (!Constants.toEditFields.containsKey(tableName))
//            return;
//
//        final ViewGroup viewGroup = (ViewGroup) view;
//        try {
//            int count = viewGroup.getChildCount();
//            for (int i = 0; i < count; i++) {
//                View v = viewGroup.getChildAt(i);
//
//                if (v instanceof LinearLayout || v instanceof ScrollView || v instanceof RelativeLayout || v instanceof FrameLayout)
//                    EnableToEditFields(v, tableName);
//
//                else
//                    v.setEnabled(Objects.requireNonNull(Constants.toEditFields.get(tableName)).contains(v.getResources().getResourceEntryName(v.getId())));
//            }
//        } catch (Exception e) {
//            ValidationController.GetException(e.toString().replace("\"", ""), "UnlockThePage()", "", view.toString());
//            e.printStackTrace();
//        }
//    }
}
