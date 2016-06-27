package com.kitetify.me.kitefy.Persistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.kitetify.me.kitefy.DatabaseModels.SurfSession;

import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Matze on 23.02.16.
 *
 private int _sID;
 private int _wID;
 private int _funID;
 private int _uID;
 private int _wcID;
 private float _windMin;
 private float _windMax;
 private float _windAvg;
 private float _forecast;
 private float _duration;
 private Date _date;
 private String _spot;
 */
public class DBHelperSurfSession {

    private KitetifyDBHandler dbHandler;
    private Context ctx;

    public DBHelperSurfSession(Context ctx) {
        this.ctx = ctx;
        dbHandler = new KitetifyDBHandler(ctx.getApplicationContext());
    }


    /*
    *  Datensatz anlegen
    */
    public long createSurfSession(SurfSession suSe){

        SQLiteDatabase db = dbHandler.getWritableDatabase();


        ContentValues values = new ContentValues();

        //
        values.put(KitetifyDBHandler.getSurfsessionWid(), suSe.get_wID());
        values.put(KitetifyDBHandler.getSurfsessionFunid(), suSe.get_funID());
        values.put(KitetifyDBHandler.getSurfsessionUid(), suSe.get_uID());
        values.put(KitetifyDBHandler.getSurfsessionWcid(), suSe.get_wcID());
        values.put(KitetifyDBHandler.getSurfsessionWindmin(), suSe.get_windMin());
        values.put(KitetifyDBHandler.getSurfsessionWindmax(), suSe.get_windMax());
        values.put(KitetifyDBHandler.getSurfsessionWindavg(), suSe.get_windAvg());
        values.put(KitetifyDBHandler.getSurfsessionForecast(), suSe.get_forecast());

        values.put(KitetifyDBHandler.getSurfsessionDuration(), suSe.get_duration());
        values.put(KitetifyDBHandler.getSurfsessionDate(), suSe.get_date().toString());
        values.put(KitetifyDBHandler.getSurfsessionSpot(), suSe.get_spot());


        // insert row
        long s_id = db.insert(KitetifyDBHandler.getTableSurfsession(), null, values);

        db.close();

        return s_id;
    }
    /*
    * Eine Material-Art wird via ID aus der Datenbank selektieren und als Objekt
    * ausgegeben
    */
    public SurfSession getSurfSession(long s_id){

        SQLiteDatabase db = dbHandler.getWritableDatabase();

        String selectQuery = "SELECT  * FROM " + KitetifyDBHandler.getTableSurfsession() + " WHERE "
                + KitetifyDBHandler.getSurfsessionId() + " = " + s_id;

        Log.e("LOG", selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        SurfSession suSe = new SurfSession();
        // ids
        suSe.set_sID(c.getInt(c.getColumnIndex(KitetifyDBHandler.getSurfsessionId())));
        suSe.set_wID(c.getInt(c.getColumnIndex(KitetifyDBHandler.getSurfsessionWid())));
        suSe.set_uID(c.getInt(c.getColumnIndex(KitetifyDBHandler.getSurfsessionUid())));
        suSe.set_wcID(c.getInt(c.getColumnIndex(KitetifyDBHandler.getSurfsessionWcid())));
        suSe.set_funID(c.getInt(c.getColumnIndex(KitetifyDBHandler.getSurfsessionFunid())));
        // wind
        suSe.set_windMin(c.getFloat(c.getColumnIndex(KitetifyDBHandler.getSurfsessionWindmin())));
        suSe.set_windMax(c.getFloat(c.getColumnIndex(KitetifyDBHandler.getSurfsessionWindmax())));
        suSe.set_windAvg(c.getFloat(c.getColumnIndex(KitetifyDBHandler.getSurfsessionWindavg())));
        // rest
        suSe.set_forecast(c.getFloat(c.getColumnIndex(KitetifyDBHandler.getSurfsessionForecast())));
        suSe.set_duration(c.getFloat(c.getColumnIndex(KitetifyDBHandler.getSurfsessionDuration())));
        suSe.set_date(new Date(c.getLong(c.getColumnIndex(KitetifyDBHandler.getSurfsessionDate()))));
        suSe.set_spot(c.getString(c.getColumnIndex(KitetifyDBHandler.getSurfsessionSpot())));

         c.close();
         db.close();

        return suSe;
    }

    /*
    * Alle Eintraege werden aus der Datenbank selektiert und in einer Liste ausgeben
    */
    public List<SurfSession> getAllSurfSessions() {
        List<SurfSession> suSes = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + KitetifyDBHandler.getTableSurfsession();

        Log.e("LOG", selectQuery);


        SQLiteDatabase db = dbHandler.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                SurfSession suSe = new SurfSession();
                // ids
                suSe.set_sID(c.getInt(c.getColumnIndex(KitetifyDBHandler.getSurfsessionId())));
                suSe.set_wID(c.getInt(c.getColumnIndex(KitetifyDBHandler.getSurfsessionWid())));
                suSe.set_uID(c.getInt(c.getColumnIndex(KitetifyDBHandler.getSurfsessionUid())));
                suSe.set_wcID(c.getInt(c.getColumnIndex(KitetifyDBHandler.getSurfsessionWcid())));
                suSe.set_funID(c.getInt(c.getColumnIndex(KitetifyDBHandler.getSurfsessionFunid())));
                // wind
                suSe.set_windMin(c.getFloat(c.getColumnIndex(KitetifyDBHandler.getSurfsessionWindmin())));
                suSe.set_windMax(c.getFloat(c.getColumnIndex(KitetifyDBHandler.getSurfsessionWindmax())));
                suSe.set_windAvg(c.getFloat(c.getColumnIndex(KitetifyDBHandler.getSurfsessionWindavg())));
                // rest
                suSe.set_forecast(c.getFloat(c.getColumnIndex(KitetifyDBHandler.getSurfsessionForecast())));
                suSe.set_duration(c.getFloat(c.getColumnIndex(KitetifyDBHandler.getSurfsessionDuration())));
                suSe.set_date(new Date(c.getLong(c.getColumnIndex(KitetifyDBHandler.getSurfsessionDate()))));
                suSe.set_spot(c.getString(c.getColumnIndex(KitetifyDBHandler.getSurfsessionSpot())));


                // adding to windCharacters list
                suSes.add(suSe);
            } while (c.moveToNext());
        }

        c.close();
        // db.close();

        return suSes;
    }

    /*
    * Ein Eintrag wird ueber das Objekt in der Datenbank geaendert.
    */
    public int updateSurfSession(SurfSession suSe) {

        SQLiteDatabase db = dbHandler.getWritableDatabase();

        ContentValues values = new ContentValues();

        //
        values.put(KitetifyDBHandler.getSurfsessionWid(), suSe.get_wID());
        values.put(KitetifyDBHandler.getSurfsessionFunid(), suSe.get_funID());
        values.put(KitetifyDBHandler.getSurfsessionUid(), suSe.get_uID());
        values.put(KitetifyDBHandler.getSurfsessionWcid(), suSe.get_wcID());
        values.put(KitetifyDBHandler.getSurfsessionWindmin(), suSe.get_windMin());
        values.put(KitetifyDBHandler.getSurfsessionWindmax(), suSe.get_windMax());
        values.put(KitetifyDBHandler.getSurfsessionWindavg(), suSe.get_windAvg());
        values.put(KitetifyDBHandler.getSurfsessionForecast(), suSe.get_forecast());

        values.put(KitetifyDBHandler.getSurfsessionDuration(), suSe.get_duration());
        values.put(KitetifyDBHandler.getSurfsessionDate(), suSe.get_date().toString());
        values.put(KitetifyDBHandler.getSurfsessionSpot(), suSe.get_spot());

        // updating row
        return db.update(KitetifyDBHandler.getTableSurfsession(), values, KitetifyDBHandler.getSurfsessionId() + " = ?",
                new String[] { String.valueOf(suSe.get_sID()) });

        // db.close();
    }

    /*
    *  aus der Datenbank loeschen.
    */
    public void deleteSurfSession(long s_id) {
        SQLiteDatabase db = dbHandler.getWritableDatabase();
        db.delete(KitetifyDBHandler.getTableSurfsession(), KitetifyDBHandler.getSurfsessionId() + " = ?",
                new String[] { String.valueOf(s_id) });
    }


}
