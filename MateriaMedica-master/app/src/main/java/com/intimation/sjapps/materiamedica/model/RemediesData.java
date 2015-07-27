package com.intimation.sjapps.materiamedica.model;

import android.database.Cursor;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by Anil Kumar on 1/3/15.
 */
public class RemediesData {
    private int id;
    private String title, sub_title;
    private LinkedHashMap<String, String> description;
    private static final String[] COLUMNS_IN_ORDER = { "id", "Name", "Subheading", "ImgPath", "Generaldesc", "isVerified"
            , "Mind"
            , "Head"
            , "Eyes"
            , "Ears"
            , "Nose"
            , "Face"
            , "Mouth"
            , "Throat"
            , "Stomach"
            , "Abdomen"
            , "Stool"
            , "Urine"
            , "Male"
            , "Female"
            , "Respiratory"
            , "Heart"
            , "Back"
            , "Extremities"
            , "Fever"
            , "Sleep"
            , "Skin"
            , "Modalities"
            , "Rgeneral"
            , "Rcompliment"
            , "Rantidote"
            , "Rcompare"
            , "Rcompatible"
            , "Rincompatible"
            , "Rinimical"
            , "Rectum"
            , "Urinary"
            , "Sexual"
            , "Uses"
            , "Tissues"
            , "Nerves"
            , "Bones"
            , "Tongue"
            , "Circulatory"
            , "Blood"
            , "Spine"
            , "Bowels"
            , "Teeth"
            , "Breast"
            , "Kidney"
            , "Gastro"
            , "Spleen"
            , "Neck"
            , "Chest"
            , "PhysiologicDosage"
            , "AlimentaryCanal"
            , "Liver"
            , "Dose"
    };

    RemediesData() {}

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return sub_title;
    }

    public LinkedHashMap<String, String> getDescription() {
        return description;
    }

    public boolean contains(String search_str) {
        String values = description.values().toString();
        if (values.contains(search_str))
            return true;
        return false;
    }

    public boolean equals(String search_str) {
        String values = description.values().toString();
        if (values.contains(" " + search_str + " "))
            return true;
        return false;
    }

    public static List<RemediesData> parseTable(Cursor cr) {
        ArrayList<RemediesData> remediesList = new ArrayList<>();
        if (cr != null && cr.moveToFirst()) {
            do {
                remediesList.add(parseRow(cr));
            } while (cr.moveToNext());
        }

        return remediesList;
    }

    private static RemediesData parseRow(Cursor cr) {
        RemediesData row = new RemediesData();
        row.id = cr.getInt(cr.getColumnIndex("id"));
        row.title = cr.getString(cr.getColumnIndex("Name"));
        row.sub_title = cr.getString(cr.getColumnIndex("Subheading"));

        row.description = new LinkedHashMap<>();
        String column_name, value;
        for (int i=4; i<COLUMNS_IN_ORDER.length; i++) {
            column_name = COLUMNS_IN_ORDER[i];
            value = cr.getString(cr.getColumnIndex(column_name));
            if (value != null
                    && !value.isEmpty()
                    && !column_name.equals("isVerified"))
                row.description.put(column_name, value);
        }

        return row;
    }
}
