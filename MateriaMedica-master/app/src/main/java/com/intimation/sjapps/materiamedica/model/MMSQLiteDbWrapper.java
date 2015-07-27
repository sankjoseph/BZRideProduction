package com.intimation.sjapps.materiamedica.model;

import android.content.Context;
import android.database.Cursor;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by Anil Kumar on 1/1/15.
 */
public class MMSQLiteDbWrapper {
    static final String TABLE_REMEDIES = "tblRemedies";
    MMSQLiteDb mDb;

    public boolean openLocalDb(Context ctx){
        boolean dbcreated = true;
        mDb = new MMSQLiteDb(ctx);
        try{
            mDb.createDataBase();
            mDb.openDataBase();
        } catch (IOException e) {
            dbcreated = false;
        } catch(SQLException sqle){
            dbcreated = false;
        }
        return dbcreated;
    }

    public void closeLocalDb(){
        if(mDb!=null){
            mDb.close();
        }
    }

    public Cursor readAllData(){
        return mDb.readAllRecords(TABLE_REMEDIES, null);
    }

    public Cursor filterData(String search_str, boolean exact_match){
        return mDb.filterRecords(TABLE_REMEDIES, null, search_str, exact_match);
    }

    public Cursor readDataFor(String col_identifier, String id){
        return mDb.readRecord(TABLE_REMEDIES, null, col_identifier, new String[]{id});
    }
}
