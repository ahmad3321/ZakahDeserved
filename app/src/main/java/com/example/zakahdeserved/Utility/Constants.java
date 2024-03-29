package com.example.zakahdeserved.Utility;

import android.view.View;

import androidx.security.crypto.MasterKey;

import com.example.zakahdeserved.Connection.SQLiteDAL;
import com.example.zakahdeserved.Connection.SQLiteRecord;
import com.example.zakahdeserved.Connection.ShowRecord;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

public class Constants {
    public static final int STATISTICAL_JOB_TITLE = 1;
    public static final int DISTRIBUTION_JOcB_TITLE = 2;

    public static SQLiteDAL SQLITEDAL;
    public static MasterKey SHAREDPREFERENCES_KEY;

    public static TabLayout tabLayout;

    public static View view1, view2, view4, view5, view6, view8, view7, view9;

    public static List<String> dynamisLists = new ArrayList<>(List.of(
            "lst_CampaignTypes", "lst_Directorates", "lst_IdentityTypes", "lst_JobTitles", "lst_Universities"));


    public static double DollarPrise = 18.8;

    public static String ZakatID = "aa0";
    public static int IncrementPersonID = 0;
    public static String PackageID = "";
    public static String PackagePersonID = "";
    public static String PackageType = "";
    public static String PackageProgram = "";


    // this boolean indicates if we are loading the data from SQLite to fragments
    // true => load the data to fragments, show the family informations (modify / update)
    // false => dont't load data from SQLite, data will be intered (insert)
    //عندما يكون هناك استعلام عن الاستمارة من أجل التحديث أو التعديل, يشير هذا المتغير إلى جلب البيانات ونعبئتها في الواجهة عند تشغيل الواجهة
//    public static boolean loadingData = false;

    public static ArrayList<SQLiteRecord> familyInfo;

    public static HashMap<String, ArrayList<String>> toEditFields = new HashMap<>();

    public static HashMap<String, String> imagesFiles = new HashMap<>();

    public static int femaleCount = 1/*start from 1 for "رب الأسرة"*/, maleCount = 0, allMembersCount = 0;
    public static String Idintity_Number = "";
}
