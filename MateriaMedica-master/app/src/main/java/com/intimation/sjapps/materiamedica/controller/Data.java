package com.intimation.sjapps.materiamedica.controller;

import android.content.Context;
import android.database.Cursor;

import com.intimation.sjapps.materiamedica.model.MMSQLiteDbWrapper;
import com.intimation.sjapps.materiamedica.model.RemediesData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anil Kumar on 1/2/15.
 */
public class Data {

    private Data() {}

    private static Data mInstance;

    private List<RemediesData> mData, mFilteredData;

    public static Data getInstance() {
        if (mInstance == null)
            mInstance = new Data();
        return mInstance;
    }

    public List<RemediesData> loadData(Context context) {
        MMSQLiteDbWrapper model = new MMSQLiteDbWrapper();
        if (model.openLocalDb(context)) {
            Cursor cr = model.readAllData();
            mData = RemediesData.parseTable(cr);
            cr.close();
            model.closeLocalDb();
        }
        return mData;
    }

    public List<RemediesData> filterData(Context context, String search_str, boolean exact_match) {
        mFilteredData = new ArrayList<>();
        for (RemediesData r : mData) {
            if (exact_match ? r.equals(search_str) : r.contains(search_str))
                mFilteredData.add(r);
        }
        return mFilteredData;
    }

    public List<RemediesData> getData() {
        return mData;
    }
}
