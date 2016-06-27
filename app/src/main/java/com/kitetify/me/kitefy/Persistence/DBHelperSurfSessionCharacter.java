package com.kitetify.me.kitefy.Persistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.kitetify.me.kitefy.DatabaseModels.SurfSessionCharacter;

import java.util.ArrayList;
import java.util.List;



public class DBHelperSurfSessionCharacter {

    private KitetifyDBHandler dbHandler;
    private Context ctx;

    public DBHelperSurfSessionCharacter(Context ctx) {
        this.ctx = ctx;
        dbHandler = new KitetifyDBHandler(ctx.getApplicationContext());
    }


    /*
    * Ein WindCharacter Datensatz anlegen
    * Die SQLite Tabelle WindCharacter besteht nur aus ID (Autoincrement) und NAME,
    * daher reicht es für einen neuen Datensatz, nur ein neues WindCharacter-Objekt
    * mit NAMEN zu uebergeben.
    */
    public long createSurfSessionCharacter(SurfSessionCharacter suSeCh){

        SQLiteDatabase db = dbHandler.getWritableDatabase();


        ContentValues values = new ContentValues();

        //
        values.put(KitetifyDBHandler.getSurfsessionCharacterName(), suSeCh.get_name());


        // insert row
        long fun_id = db.insert(KitetifyDBHandler.getTableSurfsessionCharacter(), null, values);

        db.close();

        return fun_id;
    }
    /*
    * Ein Eintrag wird via ID aus der Datenbank selektieren und als Objekt
    * ausgegeben
    */
    public SurfSessionCharacter getSurfSessionCharackter(Integer fun_id){

        SQLiteDatabase db = dbHandler.getWritableDatabase();

        String selectQuery = "SELECT  * FROM " + KitetifyDBHandler.getTableSurfsessionCharacter() + " WHERE "
                + KitetifyDBHandler.getSurfsessionFunid() + " = " + fun_id;

        Log.e("LOG", selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        SurfSessionCharacter suSeCh = new SurfSessionCharacter();
        suSeCh.set_funID(c.getInt(c.getColumnIndex(KitetifyDBHandler.getSurfsessionCharacterFunid())));
        suSeCh.set_name(c.getString(c.getColumnIndex(KitetifyDBHandler.getSurfsessionCharacterName())));


        c.close();
        // db.close();

        return suSeCh;
    }

    /*
    * Alle Eintraege werden aus der Datenbank selektiert und in einer Liste ausgeben
    */
    public List<SurfSessionCharacter> getAllSurfSessionCharacters() {
        List<SurfSessionCharacter> suSeCh_s = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + KitetifyDBHandler.getTableSurfsessionCharacter();

        Log.e("LOG", selectQuery);


        SQLiteDatabase db = dbHandler.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                SurfSessionCharacter suSeCh = new SurfSessionCharacter();
                suSeCh.set_funID(c.getInt(c.getColumnIndex(KitetifyDBHandler.getSurfsessionCharacterFunid())));
                suSeCh.set_name(c.getString(c.getColumnIndex(KitetifyDBHandler.getSurfsessionCharacterName())));


                // adding to windCharacters list
                suSeCh_s.add(suSeCh);
            } while (c.moveToNext());
        }

        c.close();
        // db.close();

        return suSeCh_s;
    }

    /*
    * Ein Eintrag wird ueber das Objekt in der Datenbank geaendert.
    */
    public int updateSurfSessionCharacter(SurfSessionCharacter suSeCh) {

        SQLiteDatabase db = dbHandler.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KitetifyDBHandler.getSurfsessionCharacterName(), suSeCh.get_name());

        // updating row
        return db.update(KitetifyDBHandler.getTableSurfsessionCharacter(), values, KitetifyDBHandler.getSurfsessionCharacterFunid() + " = ?",
                new String[] { String.valueOf(suSeCh.get_funID()) });

        // db.close();
    }

    /*
    *  aus der Datenbank loeschen.
    */
    public void deleteSurfSessionCharacter(long fun_id) {
        SQLiteDatabase db = dbHandler.getWritableDatabase();
        db.delete(KitetifyDBHandler.getTableSurfsessionCharacter(), KitetifyDBHandler.getSurfsessionCharacterFunid() + " = ?",
                new String[] { String.valueOf(fun_id) });
    }


}
