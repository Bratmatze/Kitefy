package com.kitetify.me.kitefy.Persistence;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.kitetify.me.kitefy.DatabaseModels.MaterialPlus;

import java.util.ArrayList;
import java.util.List;

public class DBHelperMaterialPlus {

    private KitetifyDBHandler dbHandler;
    private Context ctx;

    public DBHelperMaterialPlus(Context ctx) {
        this.ctx = ctx;
        dbHandler = new KitetifyDBHandler(ctx.getApplicationContext());
    }


    /*
    * Ein WindCharacter Datensatz anlegen
    * Die SQLite Tabelle WindCharacter besteht nur aus ID (Autoincrement) und NAME,
    * daher reicht es für einen neuen Datensatz, nur ein neues WindCharacter-Objekt
    * mit NAMEN zu uebergeben.
    */
    /*public long createMaterialType(MaterialType mt){

        SQLiteDatabase db = dbHandler.getWritableDatabase();


        ContentValues values = new ContentValues();

        //
        values.put(KitetifyDBHandler.getMaterialTypeName(), mt.get_name());


        // insert row
        long mt_id = db.insert(KitetifyDBHandler.getTableMaterialType(), null, values);

        db.close();

        return mt_id;
    }*/
    /*
    * Eine Material-Art wird via ID aus der Datenbank selektieren und als Objekt
    * ausgegeben
    */
    public MaterialPlus getMaterialPlus(long m_id){

        SQLiteDatabase db = dbHandler.getWritableDatabase();

        String selectQuery = "SELECT  * FROM " + KitetifyDBHandler.getTableMaterialPlus() + " WHERE "
                + KitetifyDBHandler.getMaterialPlusMid() + " = " + m_id;

        Log.e("LOG", selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        MaterialPlus mp = new MaterialPlus();
        mp.set_mTypeID(c.getInt(c.getColumnIndex(KitetifyDBHandler.getMaterialPlusMtypeid())));
        mp.set_mID(c.getInt(c.getColumnIndex(KitetifyDBHandler.getMaterialPlusMid())));


        c.close();
        db.close();

        return mp;
    }

    /*
    * Alle MAterial-Arten werden aus der Datenbank selektiert und in eine Liste ausgeben
    */
    /*public List<MaterialType> getAllMaterialTypes() {
        List<MaterialType> mts = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + KitetifyDBHandler.getTableMaterialType();

        Log.e("LOG", selectQuery);


        SQLiteDatabase db = dbHandler.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                MaterialType mt = new MaterialType();
                mt.set_mTypeID(c.getInt(c.getColumnIndex(KitetifyDBHandler.getMaterialTypeId())));
                mt.set_name(c.getString(c.getColumnIndex(KitetifyDBHandler.getMaterialTypeName())));


                // adding to windCharacters list
                mts.add(mt);
            } while (c.moveToNext());
        }

        c.close();
        db.close();

        return mts;
    } */

    /*
    * Ein Material-Art-Eintrag ueber das MaterialType-Objekt in der Datenbank aendern.
    */
    /*public int updateMaterialType(MaterialType mt) {

        SQLiteDatabase db = dbHandler.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KitetifyDBHandler.getWindDirectionName(), mt.get_name());

        // updating row
        int x =  db.update(KitetifyDBHandler.getTableMaterialType(), values, KitetifyDBHandler.getMaterialTypeId() + " = ?",
                new String[] { String.valueOf(mt.get_mTypeID()) });

        db.close();
        return x;
    }*/

    /*
    * Eine Material-Art aus der Datenbank loeschen.
    */
    /*public void deleteMaterialType(long mt_id) {
        SQLiteDatabase db = dbHandler.getWritableDatabase();
        db.delete(KitetifyDBHandler.getTableMaterialType(), KitetifyDBHandler.getMaterialTypeId() + " = ?",
                new String[] { String.valueOf(mt_id) });
    }*/

}
