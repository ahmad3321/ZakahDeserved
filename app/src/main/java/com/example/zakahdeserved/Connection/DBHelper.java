package com.example.zakahdeserved.Connection;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;

import androidx.appcompat.widget.AppCompatSpinner;

import com.example.zakahdeserved.Utility.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;

public class DBHelper {


    //لا نضمن AutoIncrement ID في الحقول حتى لا يظهر في تعليمة الإدخال فيعطي خطأ

    public static String[] PersonsColumns = new String[]{"PersonID", "ZakatID", "Name", "LastName", "FatherName", "MotherFullName",
            "IdentityNumber", "lst_IdentityTypes", "IdentityFile", "Gender", "BirthPlace", "BirthDate", "AcademicQualification", "Relation",
            "WhoIs", "Record", "IsWorking", "MonthlyIncome"};

    public static String[] FamiliesColumns = new String[]{"ZakatID", "OrginalCity", "OrginalTown", "OrginalVillage", "City", "Town", "Village",
            "Neighborhood", "BuldingSymbol", "BuldingNumber", "AdressDetails", "KnownBy", "IfSmokers", "SmokersCount", "Job",
            "OrginalJob", "WantedJob", "Nationality", "ResidenceStatus", "ContactNumber1", "ContactNumber2",
            "RelationWithContact2", "Deserved", "Reson", "ExisitStatus", "ExisitStatusAbout"};

    public static String[] Helth_StatusesColumns = new String[]{"PersonID", "HealthStatus",
            "HealthStatusEvaluation", "HealthStatusType", "HealthStatusDescription", "MonthlyCost"};

    public static String[] HusbandsColumns = new String[]{"ZakatID", "WifeSocialStatus", "HusbandName", "HusbandLastName", "HusbandFatherName",
            "HusbandMotherFullName", "IdentityNumber", "lst_IdentityTypes", "City", "Town", "Village", "BirthPlace",
            "BirthDate", "AcademicQualification", "Status", "EventDate", "Lockup", "TravelPlace", "TravelGoal", "Record",
            "Ifcondemnation", "CondemnationDuration", "ArrestDate"};

    public static String[] HousingInformationColumns = new String[]{"ZakatID", "HousingNature", "RentValue",
            "CoveredSpace", "RoomsCount", "FloorType", "RoofType", "WC", "CookingGas", "Mobiles", "Routers", "TVs", "Fridges", "Cars",
            "Motorcycles", "FurnitureEvaluation", "Sanitation", "Location", "GeneralDescription", "SolarPanelsCount", "SolarPanelsAmpCount",
            "AmpCount", "OneAmpValue", "ConsumptionValue", "CookingGasOther"};

    public static String[] IncomesColumns = new String[]{"ZakatID", "IfIncome", "IncomeType", "IncomeTime", "IncomeValue"};

    public static String[] WaterTypesColumns = new String[]{"ZakatID", "WaterType", "MonthlyValue"};

    public static String[] AidsColumns = new String[]{"ZakatID", "AidType", "AidValue", "ReceivingTime", "AidsFrom"};

    public static String[] AssetsColumns = new String[]{"ZakatID", "AssetType", "AssetAdress", "BenefitType", "BenefitValue",
            "GroundSpace", "ValueTime", "GroundNature", "MachineType", "AnimalType", "AnimalCount"};

    public static String[] SurveyConclusionColumns = new String[]{"ZakatID", "NeighborName", "IfRented", "IfIncome", "IfKidsWorking", "IfAssets", "IfPoor", "Why"};

    public static String[] FedaProgramColumns = new String[]{"PersonID", "ArrestPlace", "ArrestDate", "ArrestReson", "PrisonName",
            "StockValue", "Mediator", "RecipientName", "IdentityNumber", "lst_IdentityTypes", "ReceivedDate", "ReleaseDate", "Files"};

    public static String[] KtlalProgramesColumns = new String[]{"PersonID", "Program", "lst_CampaignTypes", "CoinType", "StockValue",
            "StockCount", "IdentityNumber", "lst_IdentityTypes", "RecipientName", "DistributionPlace", "DistributionDate", "DistributionMember"};

    public static String[] HayatProgramesColumns = new String[]{"PersonID", "BeneficiaryName", "StatusType", "DoctorOrInstitution",
            "MedicalService", "ServiceProvider", "ServiceProviderName", "CoinType", "StockValue", "ReceivedDate", "Result", "Files"};

    public static String[] AmalProgramesColumns = new String[]{"BeneficiaryName", "ServiceType", "Domain", "Description",
            "ServiceProvider", "CoinType", "StockValue", "ReceivedDate", "Result", "PersonID"};

    public static String[] AmalProgramesFormDataColumns = new String[]{"PersonID", "PreviousExperiences", "MonthlyDuration",
            "RequiredExperiences", "ExperiencesEffect", "BeneficiaryContribution"};

    public static String[] StudentsProgramesColumns = new String[]{"PersonID", "BeneficiaryName", "lst_Universities", "College",
            "Department", "Specialization", "UniversityID", "UniversityYear", "PracticalCoursesCount", "EducationSystem", "UniversitySituation",
            "DistanceToFamily", "DistanceToCollege", "Transportation", "TransportationCoinType", "TransportationCost", "GrantType", "CoinType",
            "StockValue", "ReceivedDate", "GraduationExpectedYear", "Average", "CollegeTuition", "CollegeTuitionCoinType", "DiscountValue",
            "DiscountValueCoinType"};

    public static HashMap<String, Object> PersonsTable = new HashMap<>();
    public static HashMap<String, Object> FamiliesTable = new HashMap<>();
    public static HashMap<String, Object> Helth_StatusesTable = new HashMap<>();
    public static HashMap<String, Object> HusbandsTable = new HashMap<>();
    public static HashMap<String, Object> HousingInformaionTable = new HashMap<>();
    public static HashMap<String, Object> WaterTypesTable = new HashMap<>();
    public static HashMap<String, Object> IncomesTable = new HashMap<>();
    public static HashMap<String, Object> AidsTable = new HashMap<>();
    public static HashMap<String, Object> AssetsTable = new HashMap<>();
    public static HashMap<String, Object> SurveyConclusionTable = new HashMap<>();
    public static HashMap<String, Object> FedaProgramTable = new HashMap<>();
    public static HashMap<String, Object> KtlalProgramsTable = new HashMap<>();
    public static HashMap<String, Object> HayatProgramTable = new HashMap<>();
    public static HashMap<String, Object> AmalProgramTable = new HashMap<>();
    public static HashMap<String, Object> AmalProgramFormDataTable = new HashMap<>();
    public static HashMap<String, Object> StudentsProgramTable = new HashMap<>();

    // Call this function at the start of app
    public static void initDatabaseTables() {
        for (String col : PersonsColumns)
            PersonsTable.put(col, "");

        for (String col : FamiliesColumns)
            FamiliesTable.put(col, "");

        for (String col : Helth_StatusesColumns)
            Helth_StatusesTable.put(col, "");

        for (String col : HusbandsColumns)
            HusbandsTable.put(col, "");

        for (String col : HousingInformationColumns)
            HousingInformaionTable.put(col, "");

        for (String col : WaterTypesColumns)
            WaterTypesTable.put(col, "");

        for (String col : IncomesColumns)
            IncomesTable.put(col, "");

        for (String col : AidsColumns)
            AidsTable.put(col, "");

        for (String col : AssetsColumns)
            AssetsTable.put(col, "");

        for (String col : SurveyConclusionColumns)
            SurveyConclusionTable.put(col, "");

        for (String col : FedaProgramColumns)
            FedaProgramTable.put(col, "");

        for (String col : KtlalProgramesColumns)
            KtlalProgramsTable.put(col, "");

        for (String col : HayatProgramesColumns)
            HayatProgramTable.put(col, "");

        for (String col : AmalProgramesColumns)
            AmalProgramTable.put(col, "");

        for (String col : AmalProgramesFormDataColumns)
            AmalProgramFormDataTable.put(col, "");

        for (String col : StudentsProgramesColumns)
            StudentsProgramTable.put(col, "");
    }

    public static void getfamilyFormFromSQLite(String ZakatID) {
        Constants.familyInfo = Constants.SQLITEDAL.getFamilyInfo(ZakatID);
    }

    public static void loadDataToControls(View view, SQLiteRecord sqLiteRecord) {
        final ViewGroup viewGroup = (ViewGroup) view;
        try {
            int count = viewGroup.getChildCount();
            for (int i = 0; i < count; i++) {
                View v = viewGroup.getChildAt(i);

                if (v instanceof EditText) {
                    String name = v.getResources().getResourceEntryName(v.getId());
                    ((EditText) v).setText(Objects.requireNonNull(sqLiteRecord.getRecord().get(name)).toString());

                } else if (v instanceof Spinner || v instanceof AppCompatSpinner) {
                    String name = v.getResources().getResourceEntryName(v.getId());
                    Object value = sqLiteRecord.getRecord().get(name);//getValueOfControl(name, familyInfo, false);

                    if (Constants.dynamisLists.contains(name))
                        ((Spinner) v).setSelection(Integer.parseInt(value.toString()) - 1);
                    else
                        ((Spinner) v).setSelection(((ArrayAdapter<String>) ((Spinner) v).getAdapter()).getPosition(value.toString()));

                } else if (v instanceof CheckBox) {
                    String name = v.getResources().getResourceEntryName(v.getId());
                    Object value = sqLiteRecord.getRecord().get(name);//getValueOfControl(name, familyInfo, false);
                    ((CheckBox) v).setChecked(Boolean.getBoolean(value.toString()));

                } else if (v instanceof LinearLayout || v instanceof ScrollView || v instanceof RelativeLayout || v instanceof FrameLayout) {
                    loadDataToControls(v, sqLiteRecord);
//                    Log.d("LinearLayout", v.getResources().getResourceEntryName(v.getId()));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    private static Object getValueOfControl(String controlName, SQLiteRecord tableRecord, boolean deleteRecord) {
//        Object value;
////        Optional<SQLiteRecord> row = familyInfo.stream()
////                .filter(x -> x.getRecord().containsKey(controlName))
////                .findFirst();
////        if (row.isPresent()) {
//        value = tableRecord.getRecord().get(controlName);
//
////            if (deleteRecord)
////                familyInfo.remove(row.get());
////        } else
////            value = 0;
//
//        return value;
//    }


    public static void loadDataToControls(View view, ArrayList<SQLiteRecord> familyInfo) {
        final ViewGroup viewGroup = (ViewGroup) view;
        try {
            int count = viewGroup.getChildCount();
            for (int i = 0; i < count; i++) {
                View v = viewGroup.getChildAt(i);

                if (v instanceof EditText) {
                    String name = v.getResources().getResourceEntryName(v.getId());
                    ((EditText) v).setText(getValueOfControl(name, familyInfo, false).toString());

                } else if (v instanceof Spinner || v instanceof AppCompatSpinner) {
                    String name = v.getResources().getResourceEntryName(v.getId());
                    Object value = getValueOfControl(name, familyInfo, false);

                    if (Constants.dynamisLists.contains(name))
                        ((Spinner) v).setSelection(Integer.parseInt(value.toString()) - 1);
                    else
                        ((Spinner) v).setSelection(((ArrayAdapter<String>) ((Spinner) v).getAdapter()).getPosition(value.toString()));

                } else if (v instanceof CheckBox) {
                    String name = v.getResources().getResourceEntryName(v.getId());
                    Object value = getValueOfControl(name, familyInfo, false);
                    ((CheckBox) v).setChecked(Boolean.getBoolean(value.toString()));

                } else if (v instanceof LinearLayout || v instanceof ScrollView || v instanceof RelativeLayout || v instanceof FrameLayout) {
                    loadDataToControls(v, familyInfo);
//                    Log.d("LinearLayout", v.getResources().getResourceEntryName(v.getId()));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Object getValueOfControl(String controlName, ArrayList<SQLiteRecord> familyInfo, boolean deleteRecord) {
        Object value;
        Optional<SQLiteRecord> row = familyInfo.stream()
                .filter(x -> x.getRecord().containsKey(controlName))
                .findFirst();
        if (row.isPresent()) {
            value = row.get().getRecord().get(controlName);

            if (deleteRecord)
                familyInfo.remove(row.get());
        } else
            value = 0;

        return value;
    }
}
