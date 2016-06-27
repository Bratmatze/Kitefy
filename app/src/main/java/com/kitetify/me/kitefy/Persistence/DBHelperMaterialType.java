package com.kitetify.me.kitefy.Persistence;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.kitetify.me.kitefy.DatabaseModels.MaterialType;

import java.util.ArrayList;
import java.util.List;

public class DBHelperMaterialType {

    private KitetifyDBHandler dbHandler;
    private Context ctx;

    public DBHelperMaterialType(Context ctx) {
        this.ctx = ctx;
        dbHandler = new KitetifyDBHandler(ctx.getApplicationContext());
    }


    /*
    * Ein WindCharacter Datensatz anlegen
    * Die SQLite Tabelle WindCharacter besteht nur aus ID (Autoincrement) und NAME,
    * daher reicht es für einen neuen Datensatz, nur ein neues WindCharacter-Objekt
    * mit NAMEN zu uebergeben.
    */
    public long createMaterialType(MaterialType mt){

        SQLiteDatabase db = dbHandler.getWritableDatabase();


        ContentValues values = new ContentValues();

        //
        values.put(KitetifyDBHandler.getMaterialTypeName(), mt.get_name());


        // insert row
        long mt_id = db.insert(KitetifyDBHandler.getTableMaterialType(), null, values);

        db.close();

        return mt_id;
    }
    /*
    * Eine Material-Art wird via ID aus der Datenbank selektieren und als Objekt
    * ausgegeben
    */
    public MaterialType getMaterialType(Integer mt_id){

        SQLiteDatabase db = dbHandler.getWritableDatabase();

        String selectQuery = "SELECT  * FROM " + KitetifyDBHandler.getTableMaterialType() + " WHERE "
                + KitetifyDBHandler.getMaterialTypeId() + " = " + mt_id;

        Log.e("LOG", selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        MaterialType mt = new MaterialType();
        mt.set_mTypeID(c.getInt(c.getColumnIndex(KitetifyDBHandler.getMaterialTypeId())));
        mt.set_name(c.getString(c.getColumnIndex(KitetifyDBHandler.getMaterialTypeName())));


        c.close();
        db.close();

        return mt;
    }

    /*
    * Alle MAterial-Arten werden aus der Datenbank selektiert und in eine Liste ausgeben
    */
    public List<MaterialType> getAllMaterialTypes() {
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
    }

    public List<MaterialType> getAllMaterialNamesByType(String typOfMaterial) {
        List<MaterialType> mts = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + KitetifyDBHandler.getTableMaterialType() + " AS MT";

        if(typOfMaterial.equals("service")){
            selectQuery += " WHERE MT." +KitetifyDBHandler.getMaterialPlusMtypeid()+ " = 4" ;
        } else if (typOfMaterial.equals("util") ) {
            selectQuery += " WHERE MT." + KitetifyDBHandler.getMaterialPlusMtypeid() + " != 2 AND" +
                    " MT." + KitetifyDBHandler.getMaterialPlusMtypeid() + "     != 3 AND" +
                    " MT." + KitetifyDBHandler.getMaterialPlusMtypeid() + "     != 4 ";
        } else {
            selectQuery += " WHERE (MT." + KitetifyDBHandler.getMaterialPlusMtypeid() + " != 2  AND" +
                    " MT." + KitetifyDBHandler.getMaterialPlusMtypeid() + "     != 3 ";
        }


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
    }

    /*
    * Ein Material-Art-Eintrag ueber das MaterialType-Objekt in der Datenbank aendern.
    */
    public int updateMaterialType(MaterialType mt) {

        SQLiteDatabase db = dbHandler.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KitetifyDBHandler.getWindDirectionName(), mt.get_name());

        // updating row
        int x =  db.update(KitetifyDBHandler.getTableMaterialType(), values, KitetifyDBHandler.getMaterialTypeId() + " = ?",
                new String[] { String.valueOf(mt.get_mTypeID()) });

        db.close();
        return x;
    }

    /*
    * Eine Material-Art aus der Datenbank loeschen.
    */
    public void deleteMaterialType(long mt_id) {
        SQLiteDatabase db = dbHandler.getWritableDatabase();
        db.delete(KitetifyDBHandler.getTableMaterialType(), KitetifyDBHandler.getMaterialTypeId() + " = ?",
                new String[] { String.valueOf(mt_id) });
    }

}
