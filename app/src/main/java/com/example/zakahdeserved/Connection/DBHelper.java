package com.example.zakahdeserved.Connection;

import java.util.HashMap;

public class DBHelper {


    public static String[] PersonsColumns = new String[]{"PersonID", "ZakatID", "Name", "LastName", "FatherName", "MotherFullName", "Gender",
            "IdentityNumber", "lst_IdentityTypes", "IdentityFile", "BirthPlace", "BirthDate", "AcademicQualification", "Relation",
            "WhoIs", "IsWorking", "Record", "MonthlyIncome", "CoinType"};

    public static String[] FamiliesColumns = new String[]{"ZakatID", "OrginalCity", "OrginalTown", "OrginalVillage", "City", "Town", "Village",
            "Neighborhood", "BuldingSymbol", "BuldingNumber", "AdressDetails", "KnownBy", "IfSmokers", "SmokersCount", "Job",
            "OrginalJob", "WantedJob", "Nationality", "ResidenceStatus", "ContactNumber1", "ContactNumber2",
            "RelationWithContact2", "Deserved", "Reson", "ExisitStatus", "ExisitStatusAbout"};

    public static String[] Helth_StatusesColumns = new String[]{"HealthStatusID", "PersonID", "HealthStatus",
            "HealthStatusEvaluation", "HealthStatusType", "HealthStatusDescription", "CoinType", "MonthlyCost"};

    public static String[] HusbandsColumns = new String[]{"ZakatID", "WifeSocialStatus", "HusbandName", "HusbandLastName", "HusbandFatherName",
            "HusbandMotherFullName", "IdentityNumber", "lst_IdentityTypes", "City", "Town", "Village", "BirthPlace",
            "BirthDate", "AcademicQualification", "Status", "EventDate", "Lockup", "TravelPlace", "TravelGoal", "Record",
            "Ifcondemnation", "CondemnationDuration", "ArrestDate"};

    public static String[] HousingInformationColumns = new String[]{"ZakatID", "HousingNature", "RentValueCoinType", "RentValue",
            "CoveredSpace", "RoomsCount", "FloorType", "RoofType", "WC", "CookingGas", "Mobiles", "Routers", "TVs", "Fridges", "Cars",
            "Motorcycles", "FurnitureEvaluation", "Sanitation", "Location", "GeneralDescription", "SolarPanelsCount", "SolarPanelsAmpCount",
            "AmpCount", "AmpValueCoinType", "OneAmpValue", "ConsumptionValueCoinType", "ConsumptionValue", "CookingGasOther"};

    public static String[] IncomesColumns = new String[]{"ID", "ZakatID", "IfIncome", "IncomeType", "IncomeTime", "IncomeValue", "CoinType"};

    public static String[] WaterTypesColumns = new String[]{"WaterTypeID", "ZakatID", "WaterType", "CoinType", "MonthlyValue"};

    public static String[] AidsColumns = new String[]{"AidID", "ZakatID", "AidType", "CoinType", "AidValue", "ReceivingTime", "From"};

    public static String[] AssetsColumns = new String[]{"AssetID", "ZakatID", "AssetType", "AssetAdress", "BenefitType", "BenefitValue",
            "GroundSpace", "ValueTime", "CoinType", "GroundNature", "MachineType", "AnimalType", "AnimalCount"};

    public static String[] SurveyConclusionColumns = new String[]{"ID", "ZakatID", "NeighborName", "IfRented", "IfIncome", "IfKidsWorking", "IfAssets", "IfPoor", "Why"};


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
    }
}
