package com.cgit.medscan.Model;

import com.cgit.medscan.R;

import java.io.Serializable;

public class Form1ModelClass implements Serializable {
    String medName="";
    String ForUse ="";
    String category = "Syrup";
    int color = R.color.DarkKhaki;

    public String getMedName() {
        return medName;
    }

    public void setMedName(String medName) {
        this.medName = medName;
    }

    public String getForUse() {
        return ForUse;
    }

    public void setForUse(String forUse) {
        ForUse = forUse;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
