package com.example.zakahdeserved.Connection;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.Nullable;

import com.example.zakahdeserved.Utility.ValidationController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SQLiteDAL extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "MyDB.db";
    public static final String TABLE_SPINNERS = "Spinners";
    public static final String TABLE_QUERIES = "Queries";


    public SQLiteDAL(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 7);
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
                "'QueryContents' TEXT, 'ZakatID' TEXT, 'QueryType' TEXT);";


        String PersonCreate = "CREATE TABLE 'persons' " +
                "('PersonID' TEXT, 'ZakatID' TEXT, 'Name' TEXT, 'LastName' TEXT, 'FatherName' TEXT, 'MotherFullName' TEXT," +
                " 'IdentityNumber' TEXT, 'lst_IdentityTypes' TEXT, 'IdentityFile' TEXT, 'Gender' TEXT, 'BirthPlace' TEXT," +
                " 'BirthDate' TEXT, 'AcademicQualification' TEXT, 'Relation' TEXT, 'WhoIs' TEXT, 'Record' TEXT, 'IsWorking' TEXT," +
                " 'MonthlyIncome' TEXT);";

        String FamiliesCreate = "CREATE TABLE 'families' ('ZakatID' TEXT , 'OrginalCity' TEXT , 'OrginalTown' TEXT ," +
                " 'OrginalVillage' TEXT , 'City' TEXT , 'Town' TEXT , 'Village' TEXT , 'Neighborhood' TEXT , 'BuldingSymbol' TEXT ," +
                " 'BuldingNumber' TEXT , 'AdressDetails' TEXT , 'KnownBy' TEXT , 'IfSmokers' TEXT , 'SmokersCount' TEXT , 'Job' TEXT ," +
                " 'OrginalJob' TEXT , 'WantedJob' TEXT , 'Nationality' TEXT , 'ResidenceStatus' TEXT , 'ContactNumber1' TEXT ," +
                " 'ContactNumber2' TEXT , 'RelationWithContact2' TEXT , 'Deserved' TEXT , 'Reson' TEXT , 'ExisitStatus' TEXT , " +
                "'ExisitStatusAbout' TEXT ) ;";

        String HelthStatusCreate = "CREATE TABLE 'health_statuses' ('HealthStatusID' TEXT, 'PersonID' TEXT , " +
                "'HealthStatus' TEXT , 'HealthStatusEvaluation' TEXT , 'HealthStatusType' TEXT , 'HealthStatusDescription' TEXT ," +
                " 'CoinType' TEXT , 'MonthlyCost' TEXT ) ;";

        String HusbandCreate = "CREATE TABLE 'husbands' ('ZakatID' TEXT , 'WifeSocialStatus' TEXT , 'HusbandName' TEXT ," +
                " 'HusbandLastName' TEXT , 'HusbandFatherName' TEXT , 'HusbandMotherFullName' TEXT , 'IdentityNumber' TEXT ," +
                " 'lst_IdentityTypes' TEXT , 'City' TEXT , 'Town' TEXT , 'Village' TEXT , 'BirthPlace' TEXT , 'BirthDate' TEXT," +
                " 'AcademicQualification' TEXT , 'Status' TEXT , 'EventDate' TEXT, 'Lockup' TEXT , 'TravelPlace' TEXT , 'TravelGoal' TEXT ," +
                " 'Record' TEXT , 'Ifcondemnation' TEXT , 'CondemnationDuration' TEXT , 'ArrestDate' TEXT) ;";

        String HousingInformationsCreate = "CREATE TABLE 'housing_informations' ('ZakatID' TEXT , 'HousingNature' TEXT , " +
                "'RentValue' TEXT , 'CoveredSpace' TEXT , 'RoomsCount' TEXT , 'FloorType' TEXT ," +
                " 'RoofType' TEXT , 'WC' TEXT , 'CookingGas' TEXT , 'Mobiles' TEXT , 'Routers' TEXT , 'TVs' TEXT , " +
                "'Fridges' TEXT , 'Cars' TEXT , 'Motorcycles' TEXT , 'FurnitureEvaluation' TEXT , 'Sanitation' TEXT ," +
                " 'Location' TEXT , 'GeneralDescription' TEXT, 'SolarPanelsCount' TEXT , 'SolarPanelsAmpCount' TEXT ," +
                " 'AmpCount' TEXT , 'OneAmpValue' TEXT ," +
                " 'ConsumptionValue' TEXT , 'CookingGasOther' TEXT) ;";

        String IncomesCreate = "CREATE TABLE 'incomes' ('ID' TEXT  , 'ZakatID' TEXT , 'IfIncome' TEXT , 'IncomeType' TEXT ," +
                " 'IncomeTime' TEXT , 'IncomeValue' TEXT) ;";

        String WaterTypesCreate = "CREATE TABLE 'water_types' ( 'WaterTypeID' TEXT  , 'ZakatID' TEXT , 'WaterType' TEXT ," +
                " 'MonthlyValue' TEXT) ;";

        String AidsCreate = "CREATE TABLE 'aids' ( 'AidID' TEXT  , 'ZakatID' TEXT , 'AidType' TEXT ," +
                " 'AidValue' TEXT , 'ReceivingTime' TEXT , 'AidsFrom' TEXT ) ;";

        String AssetsCreate = "CREATE TABLE 'assets' ( 'AssetID' TEXT  , 'ZakatID' TEXT , 'AssetType' TEXT , 'AssetAdress' TEXT ," +
                " 'BenefitType' TEXT , 'BenefitValue' TEXT , 'GroundSpace' TEXT , 'ValueTime' TEXT ," +
                " 'GroundNature' TEXT , 'MachineType' TEXT , 'AnimalType' TEXT , 'AnimalCount' TEXT ) ;";

        String SurveyConclusionCreate = "CREATE TABLE 'survey_conclusions' ( 'ID' TEXT  , 'ZakatID' TEXT , 'NeighborName' TEXT ," +
                " 'IfRented' TEXT , 'IfIncome' TEXT , 'IfKidsWorking' TEXT , 'IfAssets' TEXT , 'IfPoor' TEXT , 'Why' TEXT) ;";


        String PackagesCreate = "CREATE TABLE 'Packages' ('PackageID' TEXT, 'ZakatID' TEXT, 'PersonID' TEXT, 'Program' TEXT, 'FromEmployeeCode' TEXT," +
                " 'ToEmployeeCode' TEXT, 'Package' TEXT) ;";
        try {
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
            db.execSQL(PackagesCreate);
        } catch (Exception ex) {
            ValidationController.GetException(ex.toString().replace("\"", ""), "", "onCreate in SQLliteDAL", "");

        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            db.execSQL("Drop Table If Exists " + TABLE_SPINNERS);
            db.execSQL("Drop Table If Exists " + TABLE_QUERIES);
            db.execSQL("Drop Table If Exists " + "persons");
            db.execSQL("Drop Table If Exists " + "families");
            db.execSQL("Drop Table If Exists " + "health_statuses");
            db.execSQL("Drop Table If Exists " + "husbands");
            db.execSQL("Drop Table If Exists " + "housing_informations");
            db.execSQL("Drop Table If Exists " + "incomes");
            db.execSQL("Drop Table If Exists " + "water_types");
            db.execSQL("Drop Table If Exists " + "aids");
            db.execSQL("Drop Table If Exists " + "assets");
            db.execSQL("Drop Table If Exists " + "survey_conclusions");
            db.execSQL("Drop Table If Exists " + "Packages");
            onCreate(db);
        } catch (Exception ex) {
            ValidationController.GetException(ex.toString().replace("\"", ""), "", "onUpgrade in SQLliteDAL", "");
        }
    }

    public void addSpinner(String spinnerName, HashMap<String, String> items) {
        try {
            SQLiteDatabase db = getWritableDatabase();
            ContentValues contentValues = new ContentValues();

            for (Map.Entry<String, String> entry : items.entrySet()) {
                contentValues.put("IncrementID", (String) null);
                contentValues.put("SpinnerName", spinnerName);
                contentValues.put("ItemID", entry.getKey());
                contentValues.put("ItemName", entry.getValue());

                db.insert(TABLE_SPINNERS, null, contentValues);
            }
        } catch (Exception ex) {
            ValidationController.GetException(ex.toString().replace("\"", ""), "", "addSpinner in SQLliteDAL", "spinnerName " + spinnerName);
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
            ValidationController.GetException(ex.toString().replace("\"", ""), "", "clearSpinners in SQLliteDAL", "");
            Log.d("SQLITEErr", ex.toString());
        }
    }

    public void addQuery(String query, String zakatID, String queryType) {

        try {
            SQLiteDatabase db = getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("QueryContents", query);
            contentValues.put("ZakatID", zakatID);
            contentValues.put("QueryType", queryType);
            db.insert(TABLE_QUERIES, null, contentValues);
        } catch (Exception ignored) {
            ValidationController.GetException(ignored.toString().replace("\"", ""), "", "addQuery in SQLliteDAL", "query");
        }
    }

    public String getAllQueries() {
        try {
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
        } catch (Exception ex) {
            ValidationController.GetException(ex.toString().replace("\"", ""), "", "getAllQueries in SQLliteDAL", "");
            return null;
        }
    }

    public List<String> getAllZakatID() {
        try {
            ArrayList<String> zakatIDs = new ArrayList<>();
            SQLiteDatabase db = getReadableDatabase();

            Cursor cursor = db.query(false,
                    "families",
                    new String[]{"ZakatID"},
                    null, null, null, null, null, null);

            if (cursor != null && cursor.moveToNext())/*if cursor has data*/ {
                cursor.moveToFirst();
                do {
                    String value = cursor.getString(0);
                    zakatIDs.add(value);
                } while (cursor.moveToNext());
                cursor.close();
            }
            return zakatIDs;
        } catch (Exception ex) {
            ValidationController.GetException(ex.toString().replace("\"", ""), "", "getAllQueries in SQLliteDAL", "");
            return null;
        }
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

    public void deleteQuery(String zakatID) {
        try {
            SQLiteDatabase db = getWritableDatabase();
            db.execSQL("Delete from " + TABLE_QUERIES + " where ZakatID = '" + zakatID + "';");
        } catch (Exception ex) {
            ValidationController.GetException(ex.toString().replace("\"", ""), "", "clearQueries in SQLliteDAL", "");
            Log.d("SQLITEErr", ex.toString());
        }
    }

    public void clearQueries() {
        try {
            SQLiteDatabase db = getWritableDatabase();
            db.execSQL("Delete from " + TABLE_QUERIES);
        } catch (Exception ex) {
            ValidationController.GetException(ex.toString().replace("\"", ""), "", "clearQueries in SQLliteDAL", "");
            Log.d("SQLITEErr", ex.toString());
        }
    }

    public void deletePackage(String ZakatID, String PersonId) {
        try {
            SQLiteDatabase db = getWritableDatabase();
            db.execSQL("delete from packages where ZakatID = '" + ZakatID + "' and PersonID = '" + PersonId + "';");
        } catch (Exception ex) {
            ValidationController.GetException(ex.toString().replace("\"", ""), "", "clearQueries in SQLliteDAL", "");
            Log.d("SQLITEErr", ex.toString());
        }
    }

    public PackageRecord getPackageRecord(String PackageID, String ZakatID, String PersonID) {
        try {
            SQLiteDatabase db = getReadableDatabase();

            Cursor cursor = db.rawQuery("SELECT * FROM Packages WHERE PackageID like '" + PackageID + "' and ZakatID = '" + ZakatID + "' and PersonID = '" + PersonID + "'; ", null);
            if (cursor != null && cursor.moveToNext()) {
                cursor.moveToFirst();
                return new PackageRecord(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3),
                        cursor.getString(4), cursor.getString(5), cursor.getString(6));
            }
            if (cursor != null) {
                cursor.close();
            }
            return null;
        } catch (Exception ex) {
            ValidationController.GetException(ex.toString().replace("\"", ""), "", "getPackageRecord in SQLliteDAL", "PackageID " + PackageID);
            return null;
        }
    }


    public ArrayList<SQLiteRecord> getHelthStatusForHeadFamily() {
        SQLiteDatabase db = getReadableDatabase();

        ArrayList<SQLiteRecord> Records = new ArrayList<>();

        Cursor cursor = db.rawQuery("select * from health_statuses where personid = (select personid from persons where whois = 'رب الأسرة');", null);

        if (cursor != null && cursor.moveToNext()) {
            cursor.moveToFirst();
            do {
                Records.add(getSQLiteRecord(cursor, "health_statuses", DBHelper.Helth_StatusesColumns));

            } while (cursor.moveToNext());
            cursor.close();
        }
        return Records;
    }


    public ArrayList<SQLiteRecord> getRecords(String tableName, String[] Columns, String ColName, String ColValue) {
        SQLiteDatabase db = getReadableDatabase();

        ArrayList<SQLiteRecord> Records = new ArrayList<>();
        String columns = String.join(",", Columns);

        Cursor cursor = db.rawQuery("SELECT " + columns + " FROM " + tableName + " WHERE " + ColName + " like '" + ColValue + "'; ", null);

        if (cursor != null && cursor.moveToNext()) {
            cursor.moveToFirst();
            do {
                Records.add(getSQLiteRecord(cursor, tableName, Columns));
            } while (cursor.moveToNext());
            cursor.close();
        }
        return Records;
    }

    public ArrayList<SQLiteRecord> getFamilyInfo(String ZaktID) {
        SQLiteDatabase db = getReadableDatabase();

        ArrayList<SQLiteRecord> sqliteRecords = new ArrayList<>();

        // get only رب الأسرة
        Cursor cursor = db.rawQuery("SELECT * FROM persons WHERE ZakatID like '" + ZaktID + "' and whois = 'رب الأسرة'; ", null);

        if (cursor != null && cursor.moveToNext()) {
            cursor.moveToFirst();
            do {
                sqliteRecords.add(getSQLiteRecord(cursor, "persons", DBHelper.PersonsColumns));
//                String personID = cursor.getString(1);
//                //****** Get persons helth statuses *************
//                Cursor interiorCursor = db.rawQuery("SELECT * FROM health_statuses WHERE PersonID like '" + personID + "'; ", null);
//
//                if (interiorCursor != null && interiorCursor.moveToNext())/*if cursor has data*/ {
//                    interiorCursor.moveToFirst();
//
//                    do {
//                        sqliteRecords.add(getSQLiteRecord(interiorCursor, "health_statuses", DBHelper.Helth_StatusesColumns));
//
//                    } while (interiorCursor.moveToNext());
//                    interiorCursor.close();
//                }
//                // ***** End helth statuses************

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

        return sqliteRecords;
    }

    private SQLiteRecord getSQLiteRecord(Cursor cursor, String table, String[] columns) {
        try {
            HashMap<String, Object> row = new HashMap<>();

            for (int i = 0; i < columns.length; i++)
                row.put(columns[i], cursor.getString(i));

            return new SQLiteRecord(table, row);
        } catch (Exception ex) {
            ValidationController.GetException(ex.toString().replace("\"", ""), "", "getSQLiteRecord in SQLliteDAL", "table " + table);
            return null;
        }
    }

    public void StorePackages(List<PackageRecord> lstPackages) {
        try {

            SQLiteDatabase db = getWritableDatabase();

            for (int i = 0; i < lstPackages.size(); i++) {
                ContentValues contentValues = new ContentValues();
                contentValues.put("PackageID", lstPackages.get(i).PackageID);
                contentValues.put("ZakatID", lstPackages.get(i).ZakatID);
                contentValues.put("PersonID", lstPackages.get(i).PersonID);
                contentValues.put("Program", lstPackages.get(i).Program);
                contentValues.put("FromEmployeeCode", lstPackages.get(i).FromEmployeeCode);
                contentValues.put("ToEmployeeCode", lstPackages.get(i).ToEmployeeCode);
                contentValues.put("Package", lstPackages.get(i).Package);

                db.insert("Packages", null, contentValues);
            }
        } catch (Exception ignored) {
            ValidationController.GetException(ignored.toString().replace("\"", ""), "", "StorePackages in SQLliteDAL", "lstPackages " + lstPackages);
        }
    }

    public void ClearAllRecords() {
        ClearTable("persons");
        ClearTable("families");
        ClearTable("health_statuses");
        ClearTable("husbands");
        ClearTable("housing_informations");
        ClearTable("incomes");
        ClearTable("water_types");
        ClearTable("aids");
        ClearTable("assets");
        ClearTable("survey_conclusions");
        ClearTable("Packages");
    }

    public void ClearTable(String tableName) {
        try {
            SQLiteDatabase db = getWritableDatabase();
            db.execSQL("Delete from " + tableName);
        } catch (Exception ex) {
            ValidationController.GetException(ex.toString().replace("\"", ""), "", "ClearTable in SQLliteDAL", "tableName " + tableName);
            Log.d("SQLITEErr", ex.toString());
        }
    }

    public void removeForm(String zakatID) {

        // remove fom health_statuses
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT PersonID FROM persons WHERE ZakatID like '" + zakatID + "'; ", null);

        StringBuilder strPersonIDs = new StringBuilder();
        if (cursor != null && cursor.moveToNext()) {
            cursor.moveToFirst();
            do {
                strPersonIDs.append("'").append(cursor.getString(0)).append("',");
            } while (cursor.moveToNext());
            cursor.close();
        }
        if (strPersonIDs.toString().endsWith(","))
            strPersonIDs.deleteCharAt(strPersonIDs.lastIndexOf(","));

        SQLiteDatabase writedb = getWritableDatabase();
        writedb.execSQL("Delete from health_statuses where PersonID in (" + strPersonIDs + ");");


        removeRcord("persons", zakatID);
        removeRcord("families", zakatID);
        removeRcord("husbands", zakatID);
        removeRcord("housing_informations", zakatID);
        removeRcord("incomes", zakatID);
        removeRcord("water_types", zakatID);
        removeRcord("aids", zakatID);
        removeRcord("assets", zakatID);
        removeRcord("survey_conclusions", zakatID);
        removeRcord("Packages", zakatID);
        removeRcord(TABLE_QUERIES, zakatID);
    }

    private void removeRcord(String tableName, String zakatID) {
        try {
            SQLiteDatabase db = getWritableDatabase();
            db.execSQL("Delete from " + tableName + " where ZakatID = '" + zakatID + "';");
        } catch (Exception ex) {
            ValidationController.GetException(ex.toString().replace("\"", ""), "", "clearQueries in SQLliteDAL", "");
            Log.d("SQLITEErr", ex.toString());
        }
    }

    public boolean insertAllRecords(ArrayList<SQLiteRecord> AllFamilyRecords) {
        SQLiteDatabase db = getWritableDatabase();
        boolean success = false;

        try {
            db.beginTransaction();

            for (SQLiteRecord sqLiteRecord : AllFamilyRecords) {
                ContentValues contentValues = new ContentValues();
                for (Map.Entry<String, Object> entry : sqLiteRecord.getRecord().entrySet()) {

                    contentValues.put(entry.getKey(), (String) entry.getValue());
                }
                db.insert(sqLiteRecord.tableName, null, contentValues);
            }
            db.setTransactionSuccessful();
            success = true;
        } catch (Exception ignored) {
            ValidationController.GetException(ignored.toString().replace("\"", ""), "", "insertAllRecords in SQLliteDAL", "AllFamilyRecords " + AllFamilyRecords);
        } finally {
            db.endTransaction();
        }
        return !success;
    }

    public ArrayList<PackageRecord> getPackages(String empCode) {

        SQLiteDatabase db = getReadableDatabase();

        ArrayList<PackageRecord> packageRecords = new ArrayList<>();

        Cursor cursor = db.rawQuery("SELECT * FROM Packages where ToEmployeeCode = '" + empCode + "';", null);

        if (cursor != null && cursor.moveToNext()) {
            cursor.moveToFirst();
            do {
                packageRecords.add(new PackageRecord(cursor.getString(0), cursor.getString(1),
                        cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6)));
            } while (cursor.moveToNext());
            cursor.close();
        }
        return packageRecords;
    }

    public ArrayList<ShowRecord> getShowRecords(String empCode) {
        SQLiteDatabase db = getReadableDatabase();

        ArrayList<ShowRecord> sqliteRecords = new ArrayList<>();

        Cursor cursorPackage = db.rawQuery("SELECT * FROM Packages where ToEmployeeCode = '" + empCode + "'; ", null);

        if (cursorPackage != null && cursorPackage.moveToNext()) {
            cursorPackage.moveToFirst();
            do {
                String _zakatID = cursorPackage.getString(1);
                Cursor cursorFamily = db.rawQuery("SELECT City,Town  FROM families where ZakatID = '" + _zakatID + "'; ", null);
                Cursor cursorFather = db.rawQuery("SELECT Name FROM persons where ZakatID = '" + _zakatID + "' and  WhoIs = 'رب الأسرة'; ", null);

                if (cursorFamily != null && cursorFamily.moveToNext() && cursorFather != null && cursorFather.moveToNext()) {
                    cursorFather.moveToFirst();
                    cursorFamily.moveToFirst();
                    sqliteRecords.add(new ShowRecord(cursorPackage.getString(0), _zakatID, cursorFamily.getString(0), cursorFamily.getString(1),
                            cursorFather.getString(0), cursorPackage.getString(6), cursorPackage.getString(3)));
                }
                if (cursorFamily != null) {
                    cursorFamily.close();
                }
                if (cursorFather != null) {
                    cursorFather.close();
                }
            } while (cursorPackage.moveToNext());
            cursorPackage.close();

        }
        return sqliteRecords;
    }

    public void ClearPackages() {
        try {
            SQLiteDatabase db = getWritableDatabase();
            db.execSQL("Delete from Packages");
        } catch (Exception ex) {
            ValidationController.GetException(ex.toString().replace("\"", ""), "", "ClearTable in SQLliteDAL", "tableName Packages");
            Log.d("SQLITEErr", ex.toString());
        }
    }
}
