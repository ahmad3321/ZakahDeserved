package com.example.zakahdeserved.Connection;

public class PackageRecord {

    public String ZakatID, PersonID, Program, FromEmployeeCode, ToEmployeeCode, Package;

    public PackageRecord(String zakatID, String personID, String program, String fromEmployeeCode, String toEmployeeCode, String aPackage) {
        ZakatID = zakatID;
        PersonID = personID;
        Program = program;
        FromEmployeeCode = fromEmployeeCode;
        Package = aPackage;
        ToEmployeeCode = toEmployeeCode;
    }
}
