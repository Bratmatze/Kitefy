package com.kitetify.me.kitefy.Persistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.kitetify.me.kitefy.DatabaseModels.Material;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DBHelperMaterial {

    private KitetifyDBHandler dbHandler;
    private Context ctx;


    public DBHelperMaterial(Context ctx){
        this.ctx = ctx;
        dbHandler = new KitetifyDBHandler(ctx.getApplicationContext());
    }

    public long createMaterial(Material material, long mType_id){


       // dbHandler = new KitetifyDBHandler(ctx.getApplicationContext());
        SQLiteDatabase db = dbHandler.getWritableDatabase();


        ContentValues values = new ContentValues();
        ContentValues valPlus = new ContentValues();

        values.put(dbHandler.getMaterialName(), material.get_name());
        values.put(dbHandler.getMaterialType(), material.get_type());

        values.put(dbHandler.getMaterialYear(), material.get_year());
        values.put(dbHandler.getMaterialPrice(), material.get_price());
        values.put(dbHandler.getMaterialCondition(), material.get_condition());
        values.put(dbHandler.getMaterialBuydate(), material.get_buyDate().toString());
        values.put(dbHandler.getMaterialUserId(), material.get_uID());

        values.put(dbHandler.getMaterialCondition(), material.get_sellDate().toString());
        values.put(dbHandler.getMaterialBuydate(), material.get_sellPrice());
        values.put(dbHandler.getMaterialUserId(), material.get_sellFlag());

        Log.e("mat.j-createmat:", material.get_name() + " " + material.get_type() + " " + material.get_buyDate().toString() + " " + material.get_price());



        // insert row
        long material_id = db.insert(dbHandler.getTableMaterial(), null, values);

        valPlus.put(KitetifyDBHandler.getMaterialPlusMid(), material_id);
        valPlus.put(KitetifyDBHandler.getMaterialPlusMkiteId(), 0);
        valPlus.put(KitetifyDBHandler.getMaterialPlusMbarId(), 0);
        //{"1","sonstiges"},{"2","Kite"},{"3","Bar"},{"4","Wartung"},{"5","Board"},{"6","Noepren"},{"7","Trapez"}
        valPlus.put(KitetifyDBHandler.getMaterialPlusMtypeid(), mType_id);

        long materialPlus_id = db.insert(KitetifyDBHandler.getTableMaterialPlus(), null, valPlus);


        db.close();
        //Log.e("mid", material_id  + " mpid:" + materialPlus_id);
        return material_id;

    }

    public Material getMaterial(long material_id){

        SQLiteDatabase db = dbHandler.getWritableDatabase();

        /*
        * private int _mID;
    private String _name;
    private String _type;
    private int _year;
    private float _price;
    private float _sellPrice;
    private String _condition;
    private Date _buyDate;
    private Date _sellDate;
    private int _sellFlag;
    private int _uID;
        * */

        String selectQuery = "SELECT  " +
                " M."+ KitetifyDBHandler.getMaterialId() +", " +
                " M."+ KitetifyDBHandler.getMaterialName() +", " +
                " M."+ KitetifyDBHandler.getMaterialType() +", " +
                " M."+ KitetifyDBHandler.getMaterialYear() +", " +
                " M."+ KitetifyDBHandler.getMaterialPrice() +", " +
                " M."+ KitetifyDBHandler.getMaterialCondition() +", " +
                " M."+ KitetifyDBHandler.getMaterialBuydate() +", " +
                " M."+ KitetifyDBHandler.getMaterialSellprice() +", " +

                " M."+ KitetifyDBHandler.getMaterialSelldate() +", " +
                " M."+ KitetifyDBHandler.getMaterialSellflag() +", " +
                " M."+ KitetifyDBHandler.getMaterialUserId() + " "  +
                " FROM "+ KitetifyDBHandler.getTableMaterial() +" AS M " +
                " JOIN "+ KitetifyDBHandler.getTableMaterialPlus() +"     AS MP  on  M."+ KitetifyDBHandler.getMaterialId() +" = MP." + KitetifyDBHandler.getMaterialPlusMid() +
                " JOIN "+ KitetifyDBHandler.getTableMaterialType() +"     AS MT  on  MP."+ KitetifyDBHandler.getMaterialPlusMtypeid() +" = MT." + KitetifyDBHandler.getMaterialTypeId() +
                " WHERE M."+ KitetifyDBHandler.getMaterialId() +" = " + material_id + "" +
                " AND M." +KitetifyDBHandler.getMaterialSellflag()+ " = 0      AND " +
                " (MP." + KitetifyDBHandler.getMaterialPlusMtypeid() + " != 2  AND" +
                " MP." + KitetifyDBHandler.getMaterialPlusMtypeid() + " != 3)";


        Log.e("LOG", selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        Material m = new Material();
        // Material attributes
        m.set_mID(c.getInt(c.getColumnIndex(KitetifyDBHandler.getMaterialId())));
        m.set_name(c.getString(c.getColumnIndex(KitetifyDBHandler.getMaterialName())));
        m.set_type(c.getString(c.getColumnIndex(KitetifyDBHandler.getMaterialType())));
        m.set_year(c.getInt(c.getColumnIndex(KitetifyDBHandler.getMaterialYear())));
        m.set_price(c.getFloat(c.getColumnIndex(KitetifyDBHandler.getMaterialPrice())));
        m.set_condition(c.getString(c.getColumnIndex(KitetifyDBHandler.getMaterialCondition())));
        m.set_buyDate(new Date(c.getLong(c.getColumnIndex(KitetifyDBHandler.getMaterialBuydate()))));
        m.set_uID(c.getInt(c.getColumnIndex(KitetifyDBHandler.getMaterialUserId())));


        m.set_sellDate(new Date(c.getLong(c.getColumnIndex(KitetifyDBHandler.getMaterialSelldate()))));
        m.set_sellPrice(c.getFloat(c.getColumnIndex(KitetifyDBHandler.getMaterialSellprice())));
        m.set_sellFlag(c.getInt(c.getColumnIndex(KitetifyDBHandler.getMaterialSellprice())));

        Log.e("mat.j-getmat:", m.get_name() + " " + m.get_type() + " " + m.get_buyDate().toString() + " " + m.get_price());



        c.close();
        db.close();

        return m;
    }

    public List<Material> getAllMaterials() {
        List<Material> ms = new ArrayList<>();
        String selectQuery = "SELECT  * FROM "+ KitetifyDBHandler.getTableMaterial() +" AS M " +
                " JOIN "+ KitetifyDBHandler.getTableMaterialPlus() +"     AS MP  on  M."+ KitetifyDBHandler.getMaterialId() +" = MP." + KitetifyDBHandler.getMaterialPlusMid() +
                " WHERE M." +KitetifyDBHandler.getMaterialSellflag()+ " = 0    AND" +
                " (MP." + KitetifyDBHandler.getMaterialPlusMtypeid() + " != 2  AND" +
                " MP." + KitetifyDBHandler.getMaterialPlusMtypeid() + " != 3)";


        Log.e("LOG", selectQuery);


        SQLiteDatabase db = dbHandler.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Material m = new Material();
                // Material attributes
                m.set_mID(c.getInt(c.getColumnIndex(KitetifyDBHandler.getMaterialId())));
                m.set_name(c.getString(c.getColumnIndex(KitetifyDBHandler.getMaterialName())));
                m.set_type(c.getString(c.getColumnIndex(KitetifyDBHandler.getMaterialType())));
                m.set_year(c.getInt(c.getColumnIndex(KitetifyDBHandler.getMaterialYear())));
                m.set_price(c.getFloat(c.getColumnIndex(KitetifyDBHandler.getMaterialPrice())));
                m.set_condition(c.getString(c.getColumnIndex(KitetifyDBHandler.getMaterialCondition())));
                m.set_buyDate(new Date(c.getLong(c.getColumnIndex(KitetifyDBHandler.getMaterialBuydate()))));
                m.set_uID(c.getInt(c.getColumnIndex(KitetifyDBHandler.getMaterialUserId())));

                m.set_sellDate(new Date(c.getLong(c.getColumnIndex(KitetifyDBHandler.getMaterialSelldate()))));
                m.set_sellPrice(c.getFloat(c.getColumnIndex(KitetifyDBHandler.getMaterialSellprice())));
                m.set_sellFlag(c.getInt(c.getColumnIndex(KitetifyDBHandler.getMaterialSellprice())));


                // adding to windCharacters list
                ms.add(m);
            } while (c.moveToNext());
        }

        c.close();
        db.close();

        return ms;
    }

    public List<Material> getAllMaterialNames() {
        List<Material> ms = new ArrayList<>();
        String selectQuery = "SELECT  M."+ KitetifyDBHandler.getMaterialId()+", " +
                " M."+ KitetifyDBHandler.getMaterialName()+", " +
                " M."+ KitetifyDBHandler.getMaterialType()+", " +
                " M."+ KitetifyDBHandler.getMaterialYear()+" " +
                " FROM "+ KitetifyDBHandler.getTableMaterial() +" AS M " +
                " JOIN "+ KitetifyDBHandler.getTableMaterialPlus() +"     AS MP  on  M."+ KitetifyDBHandler.getMaterialId() +" = MP." + KitetifyDBHandler.getMaterialPlusMid() +
                " WHERE M." +KitetifyDBHandler.getMaterialSellflag()+ "   = 0  AND" +
                " (MP." + KitetifyDBHandler.getMaterialPlusMtypeid() + " != 2  AND" +
                " MP." + KitetifyDBHandler.getMaterialPlusMtypeid() + "  != 3)";



        Log.e("LOG", selectQuery);


        SQLiteDatabase db = dbHandler.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Material m = new Material();
                // Material attributes
                m.set_mID(c.getInt(c.getColumnIndex(KitetifyDBHandler.getMaterialId())));
                m.set_name(c.getString(c.getColumnIndex(KitetifyDBHandler.getMaterialName())));
                m.set_type(c.getString(c.getColumnIndex(KitetifyDBHandler.getMaterialType())));
                m.set_year(c.getInt(c.getColumnIndex(KitetifyDBHandler.getMaterialYear())));

                // adding to windCharacters list
                ms.add(m);
            } while (c.moveToNext());
        }

        c.close();
        db.close();

        return ms;
    }

    /*
    * typOfMaterial on Type of Material Could be util,service,all  kite and bar are special-materials
    * {"1","sonstiges"},{"2","Kite"},{"3","Bar"},{"4","Wartung"},{"5","Board"},{"6","Noepren"},{"7","Trapez"}
    * */
    public List<Material> getAllMaterialNamesByType(String typOfMaterial) {
        List<Material> ms = new ArrayList<>();
        String selectQuery = "SELECT  M."+ KitetifyDBHandler.getMaterialId()+", " +
                " M."+ KitetifyDBHandler.getMaterialName()+", " +
                " M."+ KitetifyDBHandler.getMaterialType()+", " +
                " M."+ KitetifyDBHandler.getMaterialYear()+" " +
                " FROM "+ KitetifyDBHandler.getTableMaterial() +" AS M " +
                " JOIN "+ KitetifyDBHandler.getTableMaterialPlus() +"     AS MP  on  M."+ KitetifyDBHandler.getMaterialId() +" = MP." + KitetifyDBHandler.getMaterialPlusMid() +
                " WHERE M." +KitetifyDBHandler.getMaterialSellflag()+ "   = 0 " ;
        if(typOfMaterial.equals("service")){
            selectQuery += " AND MP." +KitetifyDBHandler.getMaterialPlusMtypeid()+ " = 4" ;
        } else if (typOfMaterial.equals("util") ) {
            selectQuery += " AND (MP." + KitetifyDBHandler.getMaterialPlusMtypeid() + " != 2 AND" +
                            " MP." + KitetifyDBHandler.getMaterialPlusMtypeid() + "     != 3 AND" +
                            " MP." + KitetifyDBHandler.getMaterialPlusMtypeid() + "     != 4 )";
        } else {
            selectQuery += " AND (MP." + KitetifyDBHandler.getMaterialPlusMtypeid() + " != 2  AND" +
                            " MP." + KitetifyDBHandler.getMaterialPlusMtypeid() + "     != 3) ";
        }


        Log.e("LOG", selectQuery);


        SQLiteDatabase db = dbHandler.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Material m = new Material();
                // Material attributes
                m.set_mID(c.getInt(c.getColumnIndex(KitetifyDBHandler.getMaterialId())));
                m.set_name(c.getString(c.getColumnIndex(KitetifyDBHandler.getMaterialName())));
                m.set_type(c.getString(c.getColumnIndex(KitetifyDBHandler.getMaterialType())));
                m.set_year(c.getInt(c.getColumnIndex(KitetifyDBHandler.getMaterialYear())));

                // adding to windCharacters list
                ms.add(m);
            } while (c.moveToNext());
        }

        c.close();
        db.close();

        return ms;
    }

    /*
    * Ein Eintrag wird ueber das Objekt in der Datenbank geaendert.
    */
    public int updateMaterial(Material m) {

        SQLiteDatabase db = dbHandler.getWritableDatabase();

        ContentValues valMat = new ContentValues();

        // add kite to table material
        valMat.put(KitetifyDBHandler.getMaterialName(),      m.get_name());
        valMat.put(KitetifyDBHandler.getMaterialType(),      m.get_type());
        valMat.put(KitetifyDBHandler.getMaterialYear(),      m.get_year());
        valMat.put(KitetifyDBHandler.getMaterialPrice(),     m.get_price());
        valMat.put(KitetifyDBHandler.getMaterialCondition(), m.get_condition());
        valMat.put(KitetifyDBHandler.getMaterialBuydate(),   m.get_buyDate().toString());
        valMat.put(KitetifyDBHandler.getMaterialUserId(),    m.get_uID());

        // only by selling Material
        valMat.put(KitetifyDBHandler.getMaterialSelldate(),  m.get_sellDate().toString());
        valMat.put(KitetifyDBHandler.getMaterialSellprice(), m.get_sellPrice());
        valMat.put(KitetifyDBHandler.getMaterialSellflag(),  m.get_sellFlag());

        // updating row
        int x = db.update(KitetifyDBHandler.getTableMaterial(), valMat, KitetifyDBHandler.getMaterialId() + " = ?",
                new String[] { String.valueOf(m.get_mID())    });


        db.close();
        return x;
    }

    /*
    *  aus der Datenbank loeschen.
    */
    public void deleteMaterial(long material_id) {
        SQLiteDatabase db = dbHandler.getWritableDatabase();


        //TODO KITE und BAR via ID auch aus deren TABELLEN löschen

        /*
          if(MaterilTypeID == 2 ){
            DBHelperMKite dbK = new DBHelperMKiter();

          } elseif ( MaterilTypeID == 3) {

          } else {


        db.delete(KitetifyDBHandler.getTableMkite(), KitetifyDBHandler.getMkiteId() + " = ?",
                new String[] { String.valueOf(kite_id) });
        */
        db.delete(KitetifyDBHandler.getTableMaterial(), KitetifyDBHandler.getMaterialId() + " = ?",
                new String[] { String.valueOf(material_id) });

        db.delete(KitetifyDBHandler.getTableMaterialPlus(), KitetifyDBHandler.getMaterialPlusMid() + " = ?  ",
                new String[] { String.valueOf(material_id) });

        /* } */
        db.close();

    }

}
