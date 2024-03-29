package com.example.zakahdeserved.Connection;

public class PackageRecord {
    public String getZakatID() {
        return ZakatID;
    }

    public String getPersonID() {
        return PersonID;
    }

    public String getPackage() {
        return Package;
    }

    public String getProgram() {
        return Program;
    }

    public String PackageID;
    public String ZakatID;

    public String getPackageID() {
        return PackageID;
    }

    public String PersonID;
    public String Program;
    public String FromEmployeeCode;
    public String ToEmployeeCode;
    public String Package;

    public PackageRecord(String packageID, String zakatID, String personID, String program,
                         String fromEmployeeCode, String toEmployeeCode, String aPackage) {
        PackageID = packageID;
        ZakatID = zakatID;
        PersonID = personID;
        Program = program;
        FromEmployeeCode = fromEmployeeCode;
        ToEmployeeCode = toEmployeeCode;
        Package = aPackage;
    }

}
