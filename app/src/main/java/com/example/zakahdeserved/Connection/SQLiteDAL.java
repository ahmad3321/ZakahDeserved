package com.example.zakahdeserved.Connection;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.Selection;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.Nullable;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class SQLiteDAL extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "MyDB.db";
    public static final String TABLE_SPINNERS = "Spinners";
    public static final String TABLE_QUERIES = "Queries";

    String[] SpinnersColumns = new String[]{"SpinnerName", "ItemName"};
    String spinnersCreate = "CREATE TABLE '" + TABLE_SPINNERS + "' (" +
            "'ItemID'	INTEGER," +
            "'SpinnerName'	TEXT," +
            "'ItemName'	TEXT," +
            "PRIMARY KEY('ItemID' AUTOINCREMENT)" +
            ");";
//    Context context;

    public SQLiteDAL(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
//        this.context = context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
//        String spinnersCreate =


        String queriesCreate = "CREATE TABLE " + TABLE_QUERIES + " ('QueryID' REAL," +
                "'QueryContents' TEXT);";

        db.execSQL(spinnersCreate);
        db.execSQL(queriesCreate);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("Drop Table If Exists " + TABLE_SPINNERS);
        onCreate(db);
    }

    public void addSpinner(String spinnerName, List<String> items) {

        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        for (int i = 0; i < items.size(); i++) {
            contentValues.put("SpinnerName", spinnerName);
            contentValues.put("ItemName", items.get(i));
            db.insert(TABLE_SPINNERS, null, contentValues);
        }
    }

    public void fillSpinner(Context context, Spinner spinner) {

        ArrayList<String> items = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        String spinnerName = spinner.getResources().getResourceEntryName(spinner.getId());
        if (spinnerName.contains("1"))
            spinnerName = spinnerName.substring(0, spinnerName.indexOf("1"));


        Cursor cursor = db.query(false, TABLE_SPINNERS,
                new String[]{"ItemName"},
                "SpinnerName = ?" ,
                new String[]{spinnerName},
                null, null, null, null);

        if (cursor != null && cursor.moveToNext())/*if cursor has data*/ {
            cursor.moveToFirst();
            do {
                String value = cursor.getString(2);
                items.add(value);
            } while (cursor.moveToNext());
            cursor.close();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                context, android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    public void clearSpinners() {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("Delete from " + TABLE_SPINNERS);
    }

    public boolean addQuery(String query) {

        boolean success = false;

        try {
            SQLiteDatabase db = getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("QueryContents", query);
            db.insert(TABLE_QUERIES, null, contentValues);
            success = true;
        } catch (Exception ignored) {

        } finally {
            return success;
        }
    }

    public String getAllQueries() {
        StringBuilder strQueries = new StringBuilder();
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(false,
                TABLE_QUERIES,
                new String[]{"QueryContents"},
                null, null, null, null, null, null);

        if (cursor != null && cursor.moveToNext())/*if cursor has data*/ {
            cursor.moveToFirst();
            do {
                String value = cursor.getString(1);
                strQueries.append(value).append("\n");
            } while (cursor.moveToNext());
            cursor.close();
        }

        return strQueries.toString();
    }

    public void clearQueries(){

        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("Delete from " + TABLE_QUERIES);
    }

}
