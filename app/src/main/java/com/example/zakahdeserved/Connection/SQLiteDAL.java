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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SQLiteDAL extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "MyDB.db";
    public static final String TABLE_SPINNERS = "Spinners";
    public static final String TABLE_QUERIES = "Queries";


    public SQLiteDAL(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String spinnersCreate = "CREATE TABLE '" + TABLE_SPINNERS + "' (" +
                "'IncrementID'	INTEGER PRIMARY KEY," +
                "'SpinnerName'	TEXT," +
                "'ItemID' TEXT," +
                "'ItemName'	TEXT" +
                ");";

        String queriesCreate = "CREATE TABLE " + TABLE_QUERIES + " ('QueryID' INTEGER PRIMARY KEY," +
                "'QueryContents' TEXT);";


        String PersonCreate = "CREATE TABLE 'persons' " +
                "('PersonID' TEXT, 'ZakatID' TEXT, 'Name' TEXT, 'LastName' TEXT, 'FatherName' TEXT, 'MotherFullName' TEXT," +
                " 'Gender' TEXT, 'IdentityNumber' TEXT, 'lst_IdentityTypes' TEXT, 'IdentityFile' TEXT, 'BirthPlace' TEXT," +
                " 'BirthDate' TEXT, 'AcademicQualification' TEXT, 'Relation' TEXT, 'WhoIs' TEXT, 'IsWorking' TEXT, 'Record' TEXT," +
                " 'MonthlyIncome' TEXT, 'CoinType' TEXT);";

        String FamiliesCreate = "CREATE TABLE 'families' ('ZakatID' TEXT , 'OrginalCity' TEXT , 'OrginalTown' TEXT ," +
                " 'OrginalVillage' TEXT , 'City' TEXT , 'Town' TEXT , 'Village' TEXT , 'Neighborhood' TEXT , 'BuldingSymbol' TEXT ," +
                " 'BuldingNumber' TEXT , 'AdressDetails' TEXT , 'KnownBy' TEXT , 'IfSmokers' TEXT , 'SmokersCount' TEXT , 'Job' TEXT ," +
                " 'OrginalJob' TEXT , 'WantedJob' TEXT , 'Nationality' TEXT , 'ResidenceStatus' TEXT , 'ContactNumber1' TEXT ," +
                " 'ContactNumber2' TEXT , 'RelationWithContact2' TEXT , 'Deserved' TEXT , 'Reson' TEXT , 'ExisitStatus' TEXT , " +
                "'ExisitStatusAbout' TEXT ) ;";

        String HelthStatusCreate = "CREATE TABLE 'health_statuses' ('HealthStatusID' TEXT  , 'PersonID' TEXT , " +
                "'HealthStatus' TEXT , 'HealthStatusEvaluation' TEXT , 'HealthStatusType' TEXT , 'HealthStatusDescription' TEXT ," +
                " 'CoinType' TEXT , 'MonthlyCost' TEXT ) ;";

        String HusbandCreate = "CREATE TABLE 'husbands' ('ZakatID' TEXT , 'WifeSocialStatus' TEXT , 'HusbandName' TEXT ," +
                " 'HusbandLastName' TEXT , 'HusbandFatherName' TEXT , 'HusbandMotherFullName' TEXT , 'IdentityNumber' TEXT ," +
                " 'lst_IdentityTypes' TEXT , 'City' TEXT , 'Town' TEXT , 'Village' TEXT , 'BirthPlace' TEXT , 'BirthDate' TEXT," +
                " 'AcademicQualification' TEXT , 'Status' TEXT , 'EventDate' TEXT, 'Lockup' TEXT , 'TravelPlace' TEXT , 'TravelGoal' TEXT ," +
                " 'Record' TEXT , 'Ifcondemnation' TEXT , 'CondemnationDuration' TEXT , 'ArrestDate' TEXT) ;";

        String HousingInformationsCreate = "CREATE TABLE 'housing_informations' ('ZakatID' TEXT , 'HousingNature' TEXT , " +
                "'RentValueCoinType' TEXT , 'RentValue' TEXT , 'CoveredSpace' TEXT , 'RoomsCount' TEXT , 'FloorType' TEXT ," +
                " 'RoofType' TEXT , 'WC' TEXT , 'CookingGas' TEXT , 'Mobiles' TEXT , 'Routers' TEXT , 'TVs' TEXT , " +
                "'Fridges' TEXT , 'Cars' TEXT , 'Motorcycles' TEXT , 'FurnitureEvaluation' TEXT , 'Sanitation' TEXT ," +
                " 'Location' TEXT , 'GeneralDescription' TEXT, 'SolarPanelsCount' TEXT , 'SolarPanelsAmpCount' TEXT ," +
                " 'AmpCount' TEXT , 'AmpValueCoinType' TEXT , 'OneAmpValue' TEXT , 'ConsumptionValueCoinType' TEXT ," +
                " 'ConsumptionValue' TEXT , 'CookingGasOther' TEXT) ;";

        String IncomesCreate = "CREATE TABLE 'incomes' ('ID' TEXT  , 'ZakatID' TEXT , 'IfIncome' TEXT , 'IncomeType' TEXT ," +
                " 'IncomeTime' TEXT , 'IncomeValue' TEXT , 'CoinType' TEXT) ;";

        String WaterTypesCreate = "CREATE TABLE 'water_types' ( 'WaterTypeID' TEXT  , 'ZakatID' TEXT , 'WaterType' TEXT ," +
                " 'CoinType' TEXT , 'MonthlyValue' TEXT) ;";

        String AidsCreate = "CREATE TABLE 'aids' ( 'AidID' TEXT  , 'ZakatID' TEXT , 'AidType' TEXT , 'CoinType' TEXT ," +
                " 'AidValue' TEXT , 'ReceivingTime' TEXT , 'From' TEXT ) ;";

        String AssetsCreate = "CREATE TABLE 'assets' ( 'AssetID' TEXT  , 'ZakatID' TEXT , 'AssetType' TEXT , 'AssetAdress' TEXT ," +
                " 'BenefitType' TEXT , 'BenefitValue' TEXT , 'GroundSpace' TEXT , 'ValueTime' TEXT , 'CoinType' TEXT ," +
                " 'GroundNature' TEXT , 'MachineType' TEXT , 'AnimalType' TEXT , 'AnimalCount' TEXT ) ;";

        String SurveyConclusionCreate = "CREATE TABLE 'survey_conclusions' ( 'ID' TEXT  , 'ZakatID' TEXT , 'NeighborName' TEXT ," +
                " 'IfRented' TEXT , 'IfIncome' TEXT , 'IfKidsWorking' TEXT , 'IfAssets' TEXT , 'IfPoor' TEXT , 'Why' TEXT) ;";

        db.execSQL(spinnersCreate);
        db.execSQL(queriesCreate);
        db.execSQL(PersonCreate);
        db.execSQL(FamiliesCreate);
        db.execSQL(HelthStatusCreate);
        db.execSQL(HusbandCreate);
        db.execSQL(HousingInformationsCreate);
        db.execSQL(IncomesCreate);
        db.execSQL(WaterTypesCreate);
        db.execSQL(AidsCreate);
        db.execSQL(AssetsCreate);
        db.execSQL(SurveyConclusionCreate);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("Drop Table If Exists " + TABLE_SPINNERS);
        onCreate(db);
    }

    public void addSpinner(String spinnerName, HashMap<String, String> items) {

        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        for (Map.Entry<String, String> entry : items.entrySet()) {
            contentValues.put("IncrementID", (String) null);
            contentValues.put("SpinnerName", spinnerName);
            contentValues.put("ItemID", entry.getKey());
            contentValues.put("ItemName", entry.getValue());

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
                "SpinnerName = ?",
                new String[]{spinnerName},
                null, null, null, null);

        if (cursor != null && cursor.moveToFirst())/*if cursor has data*/ {
            do {
                String value = cursor.getString(0);
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
        try {
            SQLiteDatabase db = getWritableDatabase();
            db.execSQL("Delete from " + TABLE_SPINNERS);
        } catch (Exception ex) {
            Log.d("SQLITEErr", ex.toString());
        }
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
                String value = cursor.getString(0);
                strQueries.append(value).append("\n");
            } while (cursor.moveToNext());
            cursor.close();
        }

        return strQueries.toString();
    }

    public String getFirstValue(String query) {
        SQLiteDatabase db = getReadableDatabase();

        String value = "";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor != null && cursor.moveToNext()) {
            cursor.moveToFirst();

            value = cursor.getString(0);
            cursor.close();
        }
        return value;
    }

    public void clearQueries() {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("Delete from " + TABLE_QUERIES);
    }

    public ArrayList<SQLiteRecord> getFamilyInfo(String ZaktID) {
        SQLiteDatabase db = getReadableDatabase();

        ArrayList<SQLiteRecord> sqliteRecords = new ArrayList<>();

        // get all persons in this family
        Cursor cursor = db.rawQuery("SELECT * FROM persons WHERE ZakatID like '" + ZaktID + "'; ", null);

        // Persons Info (person & Helth_statuses)
        if (cursor != null && cursor.moveToNext()) {
            cursor.moveToFirst();
            do {

                sqliteRecords.add(getSQLiteRecord(cursor, "persons", DBHelper.PersonsColumns));

                String personID = cursor.getString(1);
                //****** Get persons helth statuses *************
                Cursor interiorCursor = db.rawQuery("SELECT * FROM health_statuses WHERE PersonID like '" + personID + "'; ", null);

                if (interiorCursor != null && interiorCursor.moveToNext())/*if cursor has data*/ {
                    interiorCursor.moveToFirst();

                    do {
                        sqliteRecords.add(getSQLiteRecord(interiorCursor, "health_statuses", DBHelper.Helth_StatusesColumns));

                    } while (interiorCursor.moveToNext());
                    interiorCursor.close();
                }
                // ***** End helth statuses************

            } while (cursor.moveToNext());
            cursor.close();
        }


        //get family Info (family table)
        cursor = db.rawQuery("SELECT * FROM families WHERE ZakatID like '" + ZaktID + "'; ", null);

        if (cursor != null && cursor.moveToNext()) {
            cursor.moveToFirst();
            do {
                sqliteRecords.add(getSQLiteRecord(cursor, "families", DBHelper.FamiliesColumns));

            } while (cursor.moveToNext());
            cursor.close();
        }

        //get husband Info (husband table)
        cursor = db.rawQuery("SELECT * FROM husbands WHERE ZakatID like '" + ZaktID + "'; ", null);

        if (cursor != null && cursor.moveToNext()) {
            cursor.moveToFirst();
            do {
                sqliteRecords.add(getSQLiteRecord(cursor, "husbands", DBHelper.HusbandsColumns));

            } while (cursor.moveToNext());
            cursor.close();
        }

        //get housing informations Info (housing_informations table)
        cursor = db.rawQuery("SELECT * FROM housing_informations WHERE ZakatID like '" + ZaktID + "'; ", null);

        if (cursor != null && cursor.moveToNext()) {
            cursor.moveToFirst();
            do {
                sqliteRecords.add(getSQLiteRecord(cursor, "housing_informations", DBHelper.HousingInformationColumns));

            } while (cursor.moveToNext());
            cursor.close();
        }

        //get housing informations Info (housing_informations table)
        cursor = db.rawQuery("SELECT * FROM housing_informations WHERE ZakatID like '" + ZaktID + "'; ", null);

        if (cursor != null && cursor.moveToNext()) {
            cursor.moveToFirst();
            do {
                sqliteRecords.add(getSQLiteRecord(cursor, "housing_informations", DBHelper.HousingInformationColumns));

            } while (cursor.moveToNext());
            cursor.close();
        }

        //get incomes Info (incomes table)
        cursor = db.rawQuery("SELECT * FROM incomes WHERE ZakatID like '" + ZaktID + "'; ", null);

        if (cursor != null && cursor.moveToNext()) {
            cursor.moveToFirst();
            do {
                sqliteRecords.add(getSQLiteRecord(cursor, "incomes", DBHelper.IncomesColumns));

            } while (cursor.moveToNext());
            cursor.close();
        }

        //get water types Info (water_types table)
        cursor = db.rawQuery("SELECT * FROM water_types WHERE ZakatID like '" + ZaktID + "'; ", null);

        if (cursor != null && cursor.moveToNext()) {
            cursor.moveToFirst();
            do {
                sqliteRecords.add(getSQLiteRecord(cursor, "water_types", DBHelper.WaterTypesColumns));

            } while (cursor.moveToNext());
            cursor.close();
        }

        //get aids Info (aids table)
        cursor = db.rawQuery("SELECT * FROM aids WHERE ZakatID like '" + ZaktID + "'; ", null);

        if (cursor != null && cursor.moveToNext()) {
            cursor.moveToFirst();
            do {
                sqliteRecords.add(getSQLiteRecord(cursor, "aids", DBHelper.AidsColumns));

            } while (cursor.moveToNext());
            cursor.close();
        }

        //get assets Info (assets table)
        cursor = db.rawQuery("SELECT * FROM assets WHERE ZakatID like '" + ZaktID + "'; ", null);

        if (cursor != null && cursor.moveToNext()) {
            cursor.moveToFirst();
            do {
                sqliteRecords.add(getSQLiteRecord(cursor, "assets", DBHelper.AssetsColumns));

            } while (cursor.moveToNext());
            cursor.close();
        }

        //get survey conclusions Info (survey_conclusions table)
        cursor = db.rawQuery("SELECT * FROM survey_conclusions WHERE ZakatID like '" + ZaktID + "'; ", null);

        if (cursor != null && cursor.moveToNext()) {
            cursor.moveToFirst();
            do {
                sqliteRecords.add(getSQLiteRecord(cursor, "survey_conclusions", DBHelper.AssetsColumns));

            } while (cursor.moveToNext());
            cursor.close();
        }

        return sqliteRecords;
    }

    private SQLiteRecord getSQLiteRecord(Cursor cursor, String table, String[] columns) {

        HashMap<String, Object> row = new HashMap<>();

        for (int i = 0; i < columns.length; i++)
            row.put(columns[i], cursor.getString(i));

        return new SQLiteRecord(table, row);
    }

}
