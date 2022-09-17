package com.example.zakahdeserved.Connection;

import java.util.HashMap;


/*
    this class used for storing data for all tables of a family
    يستخدم من أجل حفظ بيانات جميع الجداول المتعلقة بأحد المستفيدين
    استخدمه من أجل جلب البيانات من Local SQLiteDB وتجهيزها للعرض على الواجهات
 */

public class SQLiteRecord {
    String tableName;
    HashMap<String, Object> record;

    public SQLiteRecord(String tableName, HashMap<String, Object> record) {
        this.tableName = tableName;
        this.record = record;
    }

    public String getTableName() {
        return tableName;
    }

    public HashMap<String, Object> getRecord() {
        return record;
    }
}
