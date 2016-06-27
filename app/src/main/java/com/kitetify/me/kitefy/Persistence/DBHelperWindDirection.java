package com.kitetify.me.kitefy.Persistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.kitetify.me.kitefy.DatabaseModels.WindDirection;

import java.util.ArrayList;
import java.util.List;


public class DBHelperWindDirection {

    private KitetifyDBHandler dbHandler;
    private Context ctx;

    public DBHelperWindDirection(Context ctx) {
        this.ctx = ctx;
        dbHandler = new KitetifyDBHandler(ctx.getApplicationContext());
    }


    /*
    * Ein Windrichtungs-Datensatz anlegen
    *
    */
    public long createWindDirection(WindDirection wd){

        SQLiteDatabase db = dbHandler.getWritableDatabase();


        ContentValues values = new ContentValues();

        //
        values.put(KitetifyDBHandler.getWindDirectionName(), wd.get_name());
        values.put(KitetifyDBHandler.getWindDirectionDegree(), wd.get_degree());
        values.put(KitetifyDBHandler.getWindDirectionDirection(), wd.get_direction());

        // insert row
        long wd_id = db.insert(KitetifyDBHandler.getTableWinddirection(), null, values);

        db.close();

        return wd_id;
    }
    /*
    * Ein Windrichtung via ID aus der Datenbank selektieren und als WindDirection-Objekt
    * ausgeben
    */
    public WindDirection getWindDirection(Integer wd_id){

        SQLiteDatabase db = dbHandler.getWritableDatabase();

        String selectQuery = "SELECT  * FROM " + KitetifyDBHandler.getTableWinddirection() + " WHERE "
                + KitetifyDBHandler.getWindDirectionId() + " = " + wd_id;

        Log.e("LOG", selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        WindDirection wd = new WindDirection();
        wd.set_windID(c.getInt(c.getColumnIndex(KitetifyDBHandler.getWindDirectionId())));
        wd.set_name(c.getString(c.getColumnIndex(KitetifyDBHandler.getWindDirectionName())));
        wd.set_degree(c.getDouble(c.getColumnIndex(KitetifyDBHandler.getWindDirectionDegree())));
        wd.set_direction(c.getString(c.getColumnIndex(KitetifyDBHandler.getWindDirectionDirection())));


        c.close();
        // db.close();

        return wd;
    }

    /*
    * Alle Windrichtungen aus der Datenbank selektieren und in einer Liste ausgeben
    */
    public List<WindDirection> getAllWindDirections() {
        List<WindDirection> wds = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + KitetifyDBHandler.getTableWinddirection();

        Log.e("LOG", selectQuery);


        SQLiteDatabase db = dbHandler.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                WindDirection wd = new WindDirection();
                wd.set_windID(c.getInt(c.getColumnIndex(KitetifyDBHandler.getWindDirectionId())));
                wd.set_name(c.getString(c.getColumnIndex(KitetifyDBHandler.getWindDirectionName())));
                wd.set_degree(c.getDouble(c.getColumnIndex(KitetifyDBHandler.getWindDirectionDegree())));
                wd.set_direction(c.getString(c.getColumnIndex(KitetifyDBHandler.getWindDirectionDirection())));

                // adding to windCharacters list
                wds.add(wd);
            } while (c.moveToNext());
        }

        c.close();
        // db.close();

        return wds;
    }

    /*
    * Einen Windrichtngsrintrag ueber das WindDirection-Objekt in der Datenbank aendern.
    */
    public int updateWindDirection(WindDirection wd) {

        SQLiteDatabase db = dbHandler.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KitetifyDBHandler.getWindDirectionName(), wd.get_name());
        values.put(KitetifyDBHandler.getWindDirectionDegree(), wd.get_degree());
        values.put(KitetifyDBHandler.getWindDirectionDirection(), wd.get_direction());

        // updating row
        return db.update(KitetifyDBHandler.getTableWinddirection(), values, KitetifyDBHandler.getWindDirectionId() + " = ?",
                new String[] { String.valueOf(wd.get_windID()) });

        // db.close();
    }

    /*
    * Eine WIndrichtung aus der Datenbank loeschen.
    */
    public void deleteWindDirection(long w_id) {
        SQLiteDatabase db = dbHandler.getWritableDatabase();
        db.delete(KitetifyDBHandler.getTableWinddirection(), KitetifyDBHandler.getWindDirectionId() + " = ?",
                new String[] { String.valueOf(w_id) });
    }

}
