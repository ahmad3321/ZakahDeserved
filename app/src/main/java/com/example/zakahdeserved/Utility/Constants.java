package com.example.zakahdeserved.Utility;

import android.view.View;

import androidx.security.crypto.MasterKey;

import com.example.zakahdeserved.Connection.SQLiteDAL;
import com.example.zakahdeserved.Connection.SQLiteRecord;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Constants {
    public static final int STATISTICAL_JOB_TITLE = 0;
    public static final int DISTRIBUTION_JOcB_TITLE = 1;

    public static SQLiteDAL SQLITEDAL;
    public static MasterKey SHAREDPREFERENCES_KEY;

    public static View view1, view2, view4, view5, view6, view8, view7, view9;

    public static List<String> dynamisLists = new ArrayList<>(List.of(
            "lst_campaigntypes", "lst_directorates", "lst_identitytypes", "lst_jobtitles", "lst_universities"));

    public static String ZakatID;
    public static int PersonID;


    // this boolean indicates if we are loading the data from SQLite to fragments
    // true => load the data to fragments, show the family informations (modify / update)
    // false => dont't load data from SQLite, data will be intered (insert)
    //عندما يكون هناك استعلام عن الاستمارة من أجل التحديث أو التعديل, يشير هذا المتغير إلى جلب البيانات ونعبئتها في الواجهة عند تشغيل الواجهة
    public static boolean loadingData = false;

    public static ArrayList<SQLiteRecord> familyInfo;
}
