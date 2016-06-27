package com.kitetify.me.kitefy.Persistence;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.kitetify.me.kitefy.DatabaseModels.Forecast;

import java.util.ArrayList;
import java.util.List;


public class DBHelperForecast   {

    private KitetifyDBHandler dbHandler;
    private Context ctx;

    public DBHelperForecast(Context ctx) {
        this.ctx = ctx;
        dbHandler = new KitetifyDBHandler(ctx.getApplicationContext());
    }



    public Forecast getForecast(Integer fc_id){

        SQLiteDatabase db = dbHandler.getWritableDatabase();

        String selectQuery = "SELECT  * FROM " + KitetifyDBHandler.getTableForecast() + " WHERE "
                + KitetifyDBHandler.getForecastId() + " = " + fc_id;

        Log.e("LOG", selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        Forecast fc = new Forecast();
        fc.set_fID(c.getInt(c.getColumnIndex(KitetifyDBHandler.getForecastId())));
        fc.set_name(c.getString(c.getColumnIndex(KitetifyDBHandler.getForecastName())));


        c.close();
        // db.close();

        return fc;
    }

    /*
    * Alle MAterial-Arten werden aus der Datenbank selektiert und in eine Liste ausgeben
    */
    public List<Forecast> getAllForcasts() {
        List<Forecast> fcs = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + KitetifyDBHandler.getTableForecast();

        Log.e("LOG", selectQuery);


        SQLiteDatabase db = dbHandler.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Forecast fc = new Forecast();
                fc.set_fID(c.getInt(c.getColumnIndex(KitetifyDBHandler.getForecastId())));
                fc.set_name(c.getString(c.getColumnIndex(KitetifyDBHandler.getForecastName())));


                // adding to windCharacters list
                fcs.add(fc);
            } while (c.moveToNext());
        }

        c.close();
        // db.close();

        return fcs;
    }

}
