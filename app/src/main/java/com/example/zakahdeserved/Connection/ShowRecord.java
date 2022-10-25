package com.example.zakahdeserved.Connection;

public class ShowRecord {
    public String ZakatID, City, Town, Name, PackageType, Program;

    public String getZakatID() {
        return ZakatID;
    }

    public String getCity() {
        return City;
    }

    public String getTown() {
        return Town;
    }

    public String getName() {
        return Name;
    }

    public String getPackageType() {
        return PackageType;
    }

    public String getProgram() {
        return Program;
    }

    public ShowRecord(String zakatID, String city, String town, String name, String packageType, String program) {
        ZakatID = zakatID;
        City = city;
        Town = town;
        Name = name;
        PackageType = packageType;
        Program = program;
    }
}
