package com.example.zakahdeserved.Models;

import java.util.ArrayList;

public class PdfRecord {
    ArrayList<byte[]> ImagesByte;
    String identityNumber;
    int tag;

    public PdfRecord(ArrayList<byte[]> imagesByte, String identityNumber, int tag) {
        ImagesByte = imagesByte;
        this.identityNumber = identityNumber;
        this.tag = tag;
    }

    public ArrayList<byte[]> getImagesByte() {
        return ImagesByte;
    }

    public void setImagesByte(ArrayList<byte[]> imagesByte) {
        ImagesByte = imagesByte;
    }

    public String getIdentityNumber() {
        return identityNumber;
    }

    public void setIdentityNumber(String identityNumber) {
        this.identityNumber = identityNumber;
    }

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }
}
