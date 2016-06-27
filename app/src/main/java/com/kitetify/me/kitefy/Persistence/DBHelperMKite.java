package com.kitetify.me.kitefy.Persistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.kitetify.me.kitefy.DatabaseModels.MKite;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class DBHelperMKite {

    private KitetifyDBHandler dbHandler;
    private Context ctx;

    public DBHelperMKite(Context ctx) {
        this.ctx = ctx;
        dbHandler = new KitetifyDBHandler(ctx.getApplicationContext());
    }


    /*
    * Ein WindCharacter Datensatz anlegen
    * Die SQLite Tabelle WindCharacter besteht nur aus ID (Autoincrement) und NAME,
    * daher reicht es für einen neuen Datensatz, nur ein neues WindCharacter-Objekt
    * mit NAMEN zu uebergeben.
    */
    public long createKite(MKite kite){

        SQLiteDatabase db = dbHandler.getWritableDatabase();


        ContentValues values = new ContentValues();
        ContentValues valMat = new ContentValues();
        ContentValues valMat2Kite = new ContentValues();

        //
        values.put(KitetifyDBHandler.getMkiteLines(), kite.get_lines());
        values.put(KitetifyDBHandler.getMkiteShape(), kite.get_shape());
        values.put(KitetifyDBHandler.getMkiteSize(), kite.get_size());
        values.put(KitetifyDBHandler.getMkiteWindfrom(), kite.get_windForm());
        values.put(KitetifyDBHandler.getMkiteWindtill(), kite.get_windTill());
        values.put(KitetifyDBHandler.getMkiteText(), kite.get_text());

        // insert row into Kite, than into Material
        long kite_id = db.insert(KitetifyDBHandler.getTableMkite(), null, values);

        // add kite to table material
        valMat.put(KitetifyDBHandler.getMaterialName(), kite.get_name());
        valMat.put(KitetifyDBHandler.getMaterialType(), kite.get_type());
        valMat.put(KitetifyDBHandler.getMaterialYear(), kite.get_year());
        valMat.put(KitetifyDBHandler.getMaterialPrice(), kite.get_price());
        valMat.put(KitetifyDBHandler.getMaterialCondition(), kite.get_condition());
        valMat.put(KitetifyDBHandler.getMaterialBuydate(), kite.get_buyDate().toString());
        valMat.put(KitetifyDBHandler.getMaterialUserId(), kite.get_uID());

        // only by selling Material
        valMat.put(KitetifyDBHandler.getMaterialSelldate(), kite.get_sellDate().toString());
        valMat.put(KitetifyDBHandler.getMaterialSellprice(), kite.get_sellPrice());
        valMat.put(KitetifyDBHandler.getMaterialSellflag(), kite.get_sellFlag());

        long material_id = db.insert(KitetifyDBHandler.getTableMaterial(), null, valMat);

        valMat2Kite.put(KitetifyDBHandler.getMaterialPlusMid(), material_id);
        valMat2Kite.put(KitetifyDBHandler.getMaterialPlusMkiteId(), kite_id);
        valMat2Kite.put(KitetifyDBHandler.getMaterialPlusMbarId(), 0);
        //{"1","sonstiges"},{"2","Kite"},{"3","Bar"},{"4","Wartung"},{"5","Board"},{"6","Noepren"},{"7","Trapez"}
        valMat2Kite.put(KitetifyDBHandler.getMaterialPlusMtypeid(), 2);

        long materialPlus_id = db.insert(KitetifyDBHandler.getTableMaterialPlus(), null, valMat2Kite);

        db.close();

        return kite_id;
    }
    /*
    * Ein Eintrag wird via ID aus der Datenbank selektieren und als Objekt
    * ausgegeben
    */
    public MKite getKite(long kite_id){

        SQLiteDatabase db = dbHandler.getWritableDatabase();

        String selectQuery = "SELECT  * FROM "+ KitetifyDBHandler.getTableMkite() +" AS K " +
                " JOIN "+ KitetifyDBHandler.getTableMaterialPlus() +" AS MP on  K."+ KitetifyDBHandler.getMkiteId() +"    = MP." + KitetifyDBHandler.getMaterialPlusMkiteId() +
                " JOIN "+ KitetifyDBHandler.getTableMaterial() +"     AS M  on  M."+ KitetifyDBHandler.getMaterialId() +" = MP." + KitetifyDBHandler.getMaterialPlusMid() +
                " WHERE K."+ KitetifyDBHandler.getMkiteId() +" = " + kite_id + "" +
                " AND M." +KitetifyDBHandler.getMaterialSellflag()+ " = 0";


        Log.e("LOG", selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        MKite kite = new MKite();
       // Material attributes
        kite.set_mID(c.getInt(c.getColumnIndex(KitetifyDBHandler.getMaterialId())));
        kite.set_name(c.getString(c.getColumnIndex(KitetifyDBHandler.getMaterialName())));
        kite.set_type(c.getString(c.getColumnIndex(KitetifyDBHandler.getMaterialType())));
        kite.set_year(c.getInt(c.getColumnIndex(KitetifyDBHandler.getMaterialYear())));
        kite.set_price(c.getFloat(c.getColumnIndex(KitetifyDBHandler.getMaterialPrice())));
        kite.set_condition(c.getString(c.getColumnIndex(KitetifyDBHandler.getMaterialCondition())));
        kite.set_buyDate(new Date(c.getLong(c.getColumnIndex(KitetifyDBHandler.getMaterialBuydate()))));
        kite.set_uID(c.getInt(c.getColumnIndex(KitetifyDBHandler.getMaterialUserId())));
       // special attributes
        kite.set_kID(c.getInt(c.getColumnIndex(KitetifyDBHandler.getMkiteId())));
        kite.set_lines(c.getInt(c.getColumnIndex(KitetifyDBHandler.getMkiteLines())));
        kite.set_size(c.getFloat(c.getColumnIndex(KitetifyDBHandler.getMkiteSize())));
        kite.set_windForm(c.getFloat(c.getColumnIndex(KitetifyDBHandler.getMkiteWindfrom())));
        kite.set_windTill(c.getFloat(c.getColumnIndex(KitetifyDBHandler.getMkiteWindtill())));
        kite.set_shape(c.getString(c.getColumnIndex(KitetifyDBHandler.getMkiteShape())));
        kite.set_text(c.getString(c.getColumnIndex(KitetifyDBHandler.getMkiteText())));

        kite.set_sellDate(new Date (c.getLong(c.getColumnIndex(KitetifyDBHandler.getMaterialSelldate()))));
        kite.set_sellPrice(c.getFloat(c.getColumnIndex(KitetifyDBHandler.getMaterialSellprice())));
        kite.set_sellFlag(c.getInt(c.getColumnIndex(KitetifyDBHandler.getMaterialSellprice())));


        c.close();
        db.close();

        return kite;
    }

    /*
    * Alle Eintraege werden aus der Datenbank selektiert und in einer Liste ausgeben
    */
    public List<MKite> getAllKites() {
        List<MKite> kites = new ArrayList<>();
        String selectQuery = "SELECT  * FROM "+ KitetifyDBHandler.getTableMkite() +" AS K " +
                " JOIN "+ KitetifyDBHandler.getTableMaterialPlus() +" AS MP on  K."+ KitetifyDBHandler.getMkiteId() +"    = MP." + KitetifyDBHandler.getMaterialPlusMkiteId() +
                " JOIN "+ KitetifyDBHandler.getTableMaterial() +"     AS M  on  M."+ KitetifyDBHandler.getMaterialId() +" = MP." + KitetifyDBHandler.getMaterialPlusMid() +
                " WHERE M." +KitetifyDBHandler.getMaterialSellflag()+ " = 0";


        Log.e("LOG", selectQuery);


        SQLiteDatabase db = dbHandler.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                MKite kite = new MKite();
                // Material attributes
                kite.set_mID(c.getInt(c.getColumnIndex(KitetifyDBHandler.getMaterialId())));
                kite.set_name(c.getString(c.getColumnIndex(KitetifyDBHandler.getMaterialName())));
                kite.set_type(c.getString(c.getColumnIndex(KitetifyDBHandler.getMaterialType())));
                kite.set_year(c.getInt(c.getColumnIndex(KitetifyDBHandler.getMaterialYear())));
                kite.set_price(c.getFloat(c.getColumnIndex(KitetifyDBHandler.getMaterialPrice())));
                kite.set_condition(c.getString(c.getColumnIndex(KitetifyDBHandler.getMaterialCondition())));
                kite.set_buyDate(new Date(c.getLong(c.getColumnIndex(KitetifyDBHandler.getMaterialBuydate()))));
                kite.set_uID(c.getInt(c.getColumnIndex(KitetifyDBHandler.getMaterialUserId())));
                // special attributes
                kite.set_kID(c.getInt(c.getColumnIndex(KitetifyDBHandler.getMkiteId())));
                kite.set_lines(c.getInt(c.getColumnIndex(KitetifyDBHandler.getMkiteLines())));
                kite.set_size(c.getFloat(c.getColumnIndex(KitetifyDBHandler.getMkiteSize())));
                kite.set_windForm(c.getFloat(c.getColumnIndex(KitetifyDBHandler.getMkiteWindfrom())));
                kite.set_windTill(c.getFloat(c.getColumnIndex(KitetifyDBHandler.getMkiteWindtill())));
                kite.set_shape(c.getString(c.getColumnIndex(KitetifyDBHandler.getMkiteShape())));
                kite.set_text(c.getString(c.getColumnIndex(KitetifyDBHandler.getMkiteText())));

                kite.set_sellDate(new Date(c.getLong(c.getColumnIndex(KitetifyDBHandler.getMaterialSelldate()))));
                kite.set_sellPrice(c.getFloat(c.getColumnIndex(KitetifyDBHandler.getMaterialSellprice())));
                kite.set_sellFlag(c.getInt(c.getColumnIndex(KitetifyDBHandler.getMaterialSellprice())));


                // adding to windCharacters list
                kites.add(kite);
            } while (c.moveToNext());
        }

        c.close();
        db.close();

        return kites;
    }

    public List<MKite> getAllKiteNames() {
        List<MKite> kites = new ArrayList<>();
        String selectQuery = "SELECT  K."+ KitetifyDBHandler.getMkiteId()+", " +
                " K."+ KitetifyDBHandler.getMkiteSize()+", " +
                " M."+ KitetifyDBHandler.getMaterialId()+", " +
                " M."+ KitetifyDBHandler.getMaterialName()+", " +
                " M."+ KitetifyDBHandler.getMaterialType()+", " +
                " M."+ KitetifyDBHandler.getMaterialYear()+" " +
                " FROM "+ KitetifyDBHandler.getTableMkite() +" AS K " +
                " JOIN "+ KitetifyDBHandler.getTableMaterialPlus() +" AS MP on  K."+ KitetifyDBHandler.getMkiteId() +"    = MP." + KitetifyDBHandler.getMaterialPlusMkiteId() +
                " JOIN "+ KitetifyDBHandler.getTableMaterial() +"     AS M  on  M."+ KitetifyDBHandler.getMaterialId() +" = MP." + KitetifyDBHandler.getMaterialPlusMid() +
                " WHERE M." +KitetifyDBHandler.getMaterialSellflag()+ " = 0";



        Log.e("LOG", selectQuery);


        SQLiteDatabase db = dbHandler.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                MKite kite = new MKite();
                // Material attributes
                kite.set_mID(c.getInt(c.getColumnIndex(KitetifyDBHandler.getMaterialId())));
                kite.set_name(c.getString(c.getColumnIndex(KitetifyDBHandler.getMaterialName())));
                kite.set_type(c.getString(c.getColumnIndex(KitetifyDBHandler.getMaterialType())));
                kite.set_year(c.getInt(c.getColumnIndex(KitetifyDBHandler.getMaterialYear())));
                // special attributes
                kite.set_kID(c.getInt(c.getColumnIndex(KitetifyDBHandler.getMkiteId())));
                kite.set_size(c.getFloat(c.getColumnIndex(KitetifyDBHandler.getMkiteSize())));


                // adding to windCharacters list
                kites.add(kite);
            } while (c.moveToNext());
        }

        c.close();
        db.close();

        return kites;
    }

    /*
    * Ein Eintrag wird ueber das Objekt in der Datenbank geaendert.
    */
    public int updateKite(MKite kite) {

        SQLiteDatabase db = dbHandler.getWritableDatabase();

        //ContentValues values = new ContentValues();
        //values.put(dbHandler.getSurfsessionCharacterName(), kite.get_name());

        ContentValues values = new ContentValues();
        ContentValues valMat = new ContentValues();
        ContentValues valMat2Kite = new ContentValues();

        // update kite in table MKite
        values.put(KitetifyDBHandler.getMkiteLines(), kite.get_lines());
        values.put(KitetifyDBHandler.getMkiteShape(), kite.get_shape());
        values.put(KitetifyDBHandler.getMkiteSize(), kite.get_size());
        values.put(KitetifyDBHandler.getMkiteWindfrom(), kite.get_windForm());
        values.put(KitetifyDBHandler.getMkiteWindtill(), kite.get_windTill());
        values.put(KitetifyDBHandler.getMkiteText(), kite.get_text());


        // add kite to table material
        valMat.put(KitetifyDBHandler.getMaterialName(), kite.get_name());
        valMat.put(KitetifyDBHandler.getMaterialType(), kite.get_type());
        valMat.put(KitetifyDBHandler.getMaterialYear(), kite.get_year());
        valMat.put(KitetifyDBHandler.getMaterialPrice(), kite.get_price());
        valMat.put(KitetifyDBHandler.getMaterialCondition(), kite.get_condition());
        valMat.put(KitetifyDBHandler.getMaterialBuydate(), kite.get_buyDate().toString());
        valMat.put(KitetifyDBHandler.getMaterialUserId(), kite.get_uID());

        // only by selling Material
        valMat.put(KitetifyDBHandler.getMaterialSelldate(), kite.get_sellDate().toString());
        valMat.put(KitetifyDBHandler.getMaterialSellprice(), kite.get_sellPrice());
        valMat.put(KitetifyDBHandler.getMaterialSellflag(), kite.get_sellFlag());


        // updating row
        db.update(KitetifyDBHandler.getTableMaterial(), valMat, KitetifyDBHandler.getMaterialId() + " = ?",
                new String[]{String.valueOf(kite.get_mID())});


        int x = db.update(KitetifyDBHandler.getTableMkite(), values, KitetifyDBHandler.getMkiteId() + " = ?",
                new String[] { String.valueOf(kite.get_kID()) });


        db.close();
        return x;
    }

    /*
    *  aus der Datenbank loeschen.
    */
    public void deleteKite(long kite_id, long material_id) {
        SQLiteDatabase db = dbHandler.getWritableDatabase();



        db.delete(KitetifyDBHandler.getTableMkite(), KitetifyDBHandler.getMkiteId() + " = ?",
                new String[] { String.valueOf(kite_id) });

        db.delete(KitetifyDBHandler.getTableMaterial(), KitetifyDBHandler.getMaterialId() + " = ?",
                new String[] { String.valueOf(material_id) });

        db.delete(KitetifyDBHandler.getTableMaterialPlus(),
                KitetifyDBHandler.getMaterialPlusMid() + " = ? AND "+ KitetifyDBHandler.getMaterialPlusMkiteId() +" = ? ",
                new String[] { String.valueOf(material_id), String.valueOf(kite_id) });

        db.close();

    }
}
