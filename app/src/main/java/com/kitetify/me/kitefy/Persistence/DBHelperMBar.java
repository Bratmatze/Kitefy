package com.kitetify.me.kitefy.Persistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.kitetify.me.kitefy.DatabaseModels.MBar;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DBHelperMBar {

    private KitetifyDBHandler dbHandler;
    private Context ctx;

    public DBHelperMBar(Context ctx) {
        this.ctx = ctx;
        dbHandler = new KitetifyDBHandler(ctx.getApplicationContext());
    }


    /*
    * Ein WindCharacter Datensatz anlegen
    * Die SQLite Tabelle WindCharacter besteht nur aus ID (Autoincrement) und NAME,
    * daher reicht es für einen neuen Datensatz, nur ein neues WindCharacter-Objekt
    * mit NAMEN zu uebergeben.
    */
    public long createBar(MBar bar){

        SQLiteDatabase db = dbHandler.getWritableDatabase();


        ContentValues values = new ContentValues();
        ContentValues valMat = new ContentValues();
        ContentValues valMat2Bar = new ContentValues();

        //
        values.put(KitetifyDBHandler.getMbarLines(), bar.get_lines());
        values.put(KitetifyDBHandler.getMbarLenght(), bar.get_length());
        values.put(KitetifyDBHandler.getMbarWidth(), bar.get_width());
        values.put(KitetifyDBHandler.getMbarText(), bar.get_text());

        // insert row into Bar, than into Material
        long bar_id = db.insert(KitetifyDBHandler.getTableMbar(), null, values);

        // add bar to table material
        valMat.put(KitetifyDBHandler.getMaterialName(), bar.get_name());
        valMat.put(KitetifyDBHandler.getMaterialType(), bar.get_type());
        valMat.put(KitetifyDBHandler.getMaterialYear(), bar.get_year());
        valMat.put(KitetifyDBHandler.getMaterialPrice(), bar.get_price());
        valMat.put(KitetifyDBHandler.getMaterialCondition(), bar.get_condition());
        valMat.put(KitetifyDBHandler.getMaterialBuydate(), bar.get_buyDate().toString());
        valMat.put(KitetifyDBHandler.getMaterialUserId(), bar.get_uID());

        // only by selling Material
        valMat.put(KitetifyDBHandler.getMaterialSelldate(), bar.get_sellDate().toString());
        valMat.put(KitetifyDBHandler.getMaterialSellprice(), bar.get_sellPrice());
        valMat.put(KitetifyDBHandler.getMaterialSellflag(), bar.get_sellFlag());


        long material_id = db.insert(KitetifyDBHandler.getTableMaterial(), null, valMat);

        valMat2Bar.put(KitetifyDBHandler.getMaterialPlusMid(), material_id);
        valMat2Bar.put(KitetifyDBHandler.getMaterialPlusMbarId(), bar_id);
        valMat2Bar.put(KitetifyDBHandler.getMaterialPlusMkiteId(), 0);
        //{"1","sonstiges"},{"2","Kite"},{"3","Bar"},{"4","Wartung"},{"5","Board"},{"6","Noepren"},{"7","Trapez"}
        valMat2Bar.put(KitetifyDBHandler.getMaterialPlusMtypeid(), 3);


        long materialPlus_id = db.insert(KitetifyDBHandler.getTableMaterialPlus(), null, valMat2Bar);

        db.close();

        return bar_id;
    }
    /*
    * Ein Eintrag wird via ID aus der Datenbank selektieren und als Objekt
    * ausgegeben
    */
    public MBar getBar(long bar_id){

        SQLiteDatabase db = dbHandler.getReadableDatabase();

        String selectQuery = "SELECT  * FROM "+ KitetifyDBHandler.getTableMbar() +" AS B " +
                " JOIN "+ KitetifyDBHandler.getTableMaterialPlus() +" AS MP on  B."+ KitetifyDBHandler.getMbarId() +"    = MP." + KitetifyDBHandler.getMaterialPlusMbarId() +
                " JOIN "+ KitetifyDBHandler.getTableMaterial() +"     AS M  on  M."+ KitetifyDBHandler.getMaterialId() +" = MP." + KitetifyDBHandler.getMaterialPlusMid() +
                " WHERE B."+ KitetifyDBHandler.getMbarId() +" = " + bar_id +  " AND  M." + KitetifyDBHandler.getMaterialSellflag() + " = 0 ";


        Log.e("LOG", selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        MBar bar = new MBar();
        // Material attributes
        bar.set_mID(c.getInt(c.getColumnIndex(KitetifyDBHandler.getMaterialId())));
        bar.set_name(c.getString(c.getColumnIndex(KitetifyDBHandler.getMaterialName())));
        bar.set_type(c.getString(c.getColumnIndex(KitetifyDBHandler.getMaterialType())));
        bar.set_year(c.getInt(c.getColumnIndex(KitetifyDBHandler.getMaterialYear())));
        bar.set_price(c.getFloat(c.getColumnIndex(KitetifyDBHandler.getMaterialPrice())));
        bar.set_condition(c.getString(c.getColumnIndex(KitetifyDBHandler.getMaterialCondition())));
        bar.set_buyDate(new Date(c.getLong(c.getColumnIndex(KitetifyDBHandler.getMaterialBuydate()))));
        bar.set_uID(c.getInt(c.getColumnIndex(KitetifyDBHandler.getMaterialUserId())));


        // special attributes
        bar.set_bID(c.getInt(c.getColumnIndex(KitetifyDBHandler.getMbarId())));
        bar.set_lines(c.getInt(c.getColumnIndex(KitetifyDBHandler.getMbarLines())));
        bar.set_length(c.getFloat(c.getColumnIndex(KitetifyDBHandler.getMbarLenght())));
        bar.set_width(c.getFloat(c.getColumnIndex(KitetifyDBHandler.getMbarWidth())));
        bar.set_text(c.getString(c.getColumnIndex(KitetifyDBHandler.getMbarText())));

        bar.set_sellDate(new Date (c.getLong(c.getColumnIndex(KitetifyDBHandler.getMaterialSelldate()))));
        bar.set_sellPrice(c.getFloat(c.getColumnIndex(KitetifyDBHandler.getMaterialSellprice())));
        bar.set_sellFlag(c.getInt(c.getColumnIndex(KitetifyDBHandler.getMaterialSellprice())));


        c.close();
        db.close();

        return bar;
    }

    /*
    * Alle Eintraege werden aus der Datenbank selektiert und in einer Liste ausgeben
    */
    public List<MBar> getAllBars() {
        List<MBar> bars = new ArrayList<>();
        String selectQuery = "SELECT  * FROM "+ KitetifyDBHandler.getTableMbar() +" AS B " +
                " JOIN "+ KitetifyDBHandler.getTableMaterialPlus() +" AS MP on  B."+ KitetifyDBHandler.getMbarId() +"    = MP." + KitetifyDBHandler.getMaterialPlusMbarId() +
                " JOIN "+ KitetifyDBHandler.getTableMaterial() +"     AS M  on  M."+ KitetifyDBHandler.getMaterialId() +" = MP." + KitetifyDBHandler.getMaterialPlusMid() +
                " WHERE  M." + KitetifyDBHandler.getMaterialSellflag() + " = 0 ";


        Log.e("LOG", selectQuery);


        SQLiteDatabase db = dbHandler.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                MBar bar = new MBar();
                // Material attributes
                bar.set_mID(c.getInt(c.getColumnIndex(KitetifyDBHandler.getMaterialId())));
                bar.set_name(c.getString(c.getColumnIndex(KitetifyDBHandler.getMaterialName())));
                bar.set_type(c.getString(c.getColumnIndex(KitetifyDBHandler.getMaterialType())));
                bar.set_year(c.getInt(c.getColumnIndex(KitetifyDBHandler.getMaterialYear())));
                bar.set_price(c.getFloat(c.getColumnIndex(KitetifyDBHandler.getMaterialPrice())));
                bar.set_condition(c.getString(c.getColumnIndex(KitetifyDBHandler.getMaterialCondition())));
                bar.set_buyDate(new Date(c.getLong(c.getColumnIndex(KitetifyDBHandler.getMaterialBuydate()))));
                bar.set_uID(c.getInt(c.getColumnIndex(KitetifyDBHandler.getMaterialUserId())));
                // special attributes
                bar.set_bID(c.getInt(c.getColumnIndex(KitetifyDBHandler.getMbarId())));
                bar.set_lines(c.getInt(c.getColumnIndex(KitetifyDBHandler.getMbarLines())));
                bar.set_length(c.getFloat(c.getColumnIndex(KitetifyDBHandler.getMbarLenght())));
                bar.set_width(c.getFloat(c.getColumnIndex(KitetifyDBHandler.getMbarWidth())));
                bar.set_text(c.getString(c.getColumnIndex(KitetifyDBHandler.getMbarText())));

                bar.set_sellDate(new Date(c.getLong(c.getColumnIndex(KitetifyDBHandler.getMaterialSelldate()))));
                bar.set_sellPrice(c.getFloat(c.getColumnIndex(KitetifyDBHandler.getMaterialSellprice())));
                bar.set_sellFlag(c.getInt(c.getColumnIndex(KitetifyDBHandler.getMaterialSellprice())));


                // adding to windCharacters list
                bars.add(bar);
            } while (c.moveToNext());
        }

        c.close();
        db.close();

        return bars;
    }

    public List<MBar> getAllBarNames() {
        List<MBar> bars = new ArrayList<>();
        String selectQuery = "SELECT  B."+ KitetifyDBHandler.getMbarId()+", " +
                " M."+ KitetifyDBHandler.getMaterialId()+", " +
                " M."+ KitetifyDBHandler.getMaterialName()+", " +
                " M."+ KitetifyDBHandler.getMaterialType()+", " +
                " M."+ KitetifyDBHandler.getMaterialYear()+" " +
                " FROM "+ KitetifyDBHandler.getTableMbar() +" AS B " +
                " JOIN "+ KitetifyDBHandler.getTableMaterialPlus() +" AS MP on  B."+ KitetifyDBHandler.getMbarId() +"     = MP." + KitetifyDBHandler.getMaterialPlusMbarId() +
                " JOIN "+ KitetifyDBHandler.getTableMaterial() +"     AS M  on  M."+ KitetifyDBHandler.getMaterialId() +" = MP." + KitetifyDBHandler.getMaterialPlusMid() +
                " WHERE  M." + KitetifyDBHandler.getMaterialSellflag() + " = 0 ";


        Log.e("LOG", selectQuery);


        SQLiteDatabase db = dbHandler.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                MBar bar = new MBar();
                // Material attributes
                bar.set_mID(c.getInt(c.getColumnIndex(KitetifyDBHandler.getMaterialId())));
                bar.set_name(c.getString(c.getColumnIndex(KitetifyDBHandler.getMaterialName())));
                bar.set_type(c.getString(c.getColumnIndex(KitetifyDBHandler.getMaterialType())));
                bar.set_year(c.getInt(c.getColumnIndex(KitetifyDBHandler.getMaterialYear())));
                // special attributes
                bar.set_bID(c.getInt(c.getColumnIndex(KitetifyDBHandler.getMbarId())));

                // adding to windCharacters list
                bars.add(bar);
            } while (c.moveToNext());
        }

        c.close();
        db.close();

        return bars;
    }

    /*
    * Ein Eintrag wird ueber das Objekt in der Datenbank geaendert.
    */
    public int updateBar(MBar bar) {

        SQLiteDatabase db = dbHandler.getWritableDatabase();

        ContentValues values = new ContentValues();
        ContentValues valMat = new ContentValues();

        // update bar in table Mbar
        values.put(KitetifyDBHandler.getMbarLines(), bar.get_lines());
        values.put(KitetifyDBHandler.getMbarLenght(), bar.get_length());
        values.put(KitetifyDBHandler.getMbarWidth(), bar.get_width());
        values.put(KitetifyDBHandler.getMbarText(), bar.get_text());


        // add bar to table material
        valMat.put(KitetifyDBHandler.getMaterialName(), bar.get_name());
        valMat.put(KitetifyDBHandler.getMaterialType(), bar.get_type());
        valMat.put(KitetifyDBHandler.getMaterialYear(), bar.get_year());
        valMat.put(KitetifyDBHandler.getMaterialPrice(), bar.get_price());
        valMat.put(KitetifyDBHandler.getMaterialCondition(), bar.get_condition());
        valMat.put(KitetifyDBHandler.getMaterialBuydate(), bar.get_buyDate().toString());
        valMat.put(KitetifyDBHandler.getMaterialUserId(), bar.get_uID());

        // only by selling Material
        valMat.put(KitetifyDBHandler.getMaterialSelldate(), bar.get_sellDate().toString());
        valMat.put(KitetifyDBHandler.getMaterialSellprice(), bar.get_sellPrice());
        valMat.put(KitetifyDBHandler.getMaterialSellflag(), bar.get_sellFlag());


        // updating row
        db.update(KitetifyDBHandler.getTableMaterial(), valMat, KitetifyDBHandler.getMaterialId() + " = ?",
                new String[]{String.valueOf(bar.get_mID())});


        int x = db.update(KitetifyDBHandler.getTableMbar(), values, KitetifyDBHandler.getMbarId() + " = ?",
                new String[] { String.valueOf(bar.get_bID()) });

        db.close();

        return x;
    }

  /*  public int sellBar(MBar bar) {

        SQLiteDatabase db = dbHandler.getWritableDatabase();

        ContentValues values = new ContentValues();
        ContentValues valMat = new ContentValues();

        // update bar in table Mbar
        values.put(KitetifyDBHandler.getMbarLines(), bar.get_lines());
        values.put(KitetifyDBHandler.getMbarLenght(), bar.get_length());
        values.put(KitetifyDBHandler.getMbarWidth(), bar.get_width());
        values.put(KitetifyDBHandler.getMbarText(), bar.get_text());


        // add bar to table material
        valMat.put(KitetifyDBHandler.getMaterialName(), bar.get_name());
        valMat.put(KitetifyDBHandler.getMaterialType(), bar.get_type());
        valMat.put(KitetifyDBHandler.getMaterialYear(), bar.get_year());
        valMat.put(KitetifyDBHandler.getMaterialPrice(), bar.get_price());
        valMat.put(KitetifyDBHandler.getMaterialCondition(), bar.get_condition());
        valMat.put(KitetifyDBHandler.getMaterialBuydate(), bar.get_buyDate().toString());
        valMat.put(KitetifyDBHandler.getMaterialUserId(), bar.get_uID());
        valMat.put(KitetifyDBHandler.getMaterialSelldate(), bar.get_sellDate().toString());
        valMat.put(KitetifyDBHandler.getMaterialSellprice(), bar.get_sellPrice());
        valMat.put(KitetifyDBHandler.getMaterialSellflag(), 1);


        // updating row
        db.update(KitetifyDBHandler.getTableMaterial(), valMat, KitetifyDBHandler.getMaterialId() + " = ?",
                new String[]{String.valueOf(bar.get_mID())});


        int x = db.update(KitetifyDBHandler.getTableMbar(), values, KitetifyDBHandler.getMbarId() + " = ?",
                new String[] { String.valueOf(bar.get_bID()) });

        db.close();

        return x;
    }*/

    /*
    *  aus der Datenbank loeschen.
    */
    public void deleteBar(long bar_id, long material_id) {
        SQLiteDatabase db = dbHandler.getWritableDatabase();

        db.delete(KitetifyDBHandler.getTableMbar(), KitetifyDBHandler.getMbarId() + " = ?",
                new String[] { String.valueOf(bar_id) });

        db.delete(KitetifyDBHandler.getTableMaterial(), KitetifyDBHandler.getMaterialId() + " = ?",
                new String[] { String.valueOf(material_id) });

        db.delete(KitetifyDBHandler.getTableMaterialPlus(),
                KitetifyDBHandler.getMaterialPlusMid() + " = ? AND "+ KitetifyDBHandler.getMaterialPlusMbarId() +" = ? ",
                new String[] { String.valueOf(material_id), String.valueOf(bar_id) });

        db.close();
    }

}
