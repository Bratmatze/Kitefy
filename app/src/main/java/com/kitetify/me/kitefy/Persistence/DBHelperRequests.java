package com.kitetify.me.kitefy.Persistence;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.kitetify.me.kitefy.DatabaseModels.Material;
import com.kitetify.me.kitefy.InAppModels.StatisticKiteUsageOverview;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Erzeugt fuer
 */
public class DBHelperRequests {


    private KitetifyDBHandler dbHandler;
    private Context ctx;


    public DBHelperRequests(Context ctx){
        this.ctx = ctx;
        dbHandler = new KitetifyDBHandler(ctx.getApplicationContext());
    }


    public List<StatisticKiteUsageOverview> getKiteUsageOverview(){

        List<StatisticKiteUsageOverview> kiteOverviewList = new ArrayList<>();



        /*
SELECT
COUNT(duration) AS "Kite Nutzung",
SUM(duration) AS "Nutzung Dauer",
ROUND(AVG(duration),2) AS "Session Dauer",
ROUND(AVG(windAvg),2) AS "Wind AVG",
"Alle Kite"  AS "Kites",
"alle Typen"  AS "type",
"ab 2010"  AS "year",
"alle groessen" AS "size"
FROM `surfSession` WHERE 1
UNION
SELECT
COUNT(duration) AS "Kite Nutzung",
SUM(duration) AS "Nutzung Dauer",
ROUND(AVG(duration),2) AS "Session Dauer",
ROUND(AVG(windAvg),2) AS "Wind AVG",
material.name   AS "Kites",
material.type  AS "type",
material.year  AS "year",
mKite.size AS "size"
FROM `surfSession`
Inner JOIN `materialToSurfSession`  ON materialToSurfSession.sID = surfSession.sID
inner JOIN `material` ON  materialToSurfSession.mID = material.mID
inner JOIN `materialPlus`ON material.mID = materialPlus.mID
inner JOIN materialType ON materialPlus.mTypeID = materialType.mTypeID
inner JOIN  mKite ON materialPlus.mKiteID = mKite.kID
WHERE materialType.name = "Kite" AND mKite.kID = 1
UNION
SELECT
COUNT(duration) AS "Kite Nutzung",
SUM(duration) AS "Nutzung Dauer",
ROUND(AVG(duration),2) AS "Session Dauer",
ROUND(AVG(windAvg),2) AS "Wind AVG",
material.name   AS "Kites",
material.type  AS "type",
material.year  AS "year",
mKite.size AS "size"
FROM `surfSession`
Inner JOIN `materialToSurfSession`  ON materialToSurfSession.sID = surfSession.sID
inner JOIN `material` ON  materialToSurfSession.mID = material.mID
inner JOIN `materialPlus`ON material.mID = materialPlus.mID
inner JOIN materialType ON materialPlus.mTypeID = materialType.mTypeID
inner JOIN  mKite ON materialPlus.mKiteID = mKite.kID
WHERE materialType.name = "Kite" AND mKite.kID = 2

";*/

        // Column Indexes
        String kiteUsage = "Kite Nutzung";
        String duration  = "Nutzung Dauer";
        String session   = "Session Dauer";
        String windspeed = "Wind AVG";

        String kites = "Kites";
        String type = "type";
        String year = "year";
        String size = "size";

        String allSelect = "" +
                " COUNT(s."+KitetifyDBHandler.getSurfsessionDuration()+")        AS '"+kiteUsage+"', " +
                " SUM(s."+KitetifyDBHandler.getSurfsessionDuration()+")          AS '"+duration+"', " +
                " ROUND(AVG(s."+KitetifyDBHandler.getSurfsessionDuration()+"),2) AS '"+session+"' , " +
                " ROUND(AVG(s."+KitetifyDBHandler.getSurfsessionWindavg()+"),2)  AS '"+windspeed+"' , " ;

        String kiteSelect = "" +
                " m."+KitetifyDBHandler.getMaterialName()+"  AS '"+kites+"' , " +
                " m."+KitetifyDBHandler.getMaterialType()+"  AS '"+type+"' , " +
                " m."+KitetifyDBHandler.getMaterialYear()+"  AS '"+year+"', " +
                " k."+KitetifyDBHandler.getMkiteSize()+"     AS '"+size+"' " ;

        String joinSelect = ""+
                " FROM "+ KitetifyDBHandler.getTableSurfsession() +" as s  " +
                " Inner JOIN "+ KitetifyDBHandler.getTableMaterialToSurfsession() +" AS m2s  ON  s."+ KitetifyDBHandler.getMaterialToSurfsessionSid()+"  = m2s."+KitetifyDBHandler.getSurfsessionId()+" " +
                " inner JOIN "+KitetifyDBHandler.getTableMaterial()+"     AS m    ON m2s."+ KitetifyDBHandler.getMaterialToSurfsessionMid() +" = m."+KitetifyDBHandler.getMaterialId()+" " +
                " inner JOIN "+KitetifyDBHandler.getTableMaterialPlus()+" AS mp   ON m."+KitetifyDBHandler.getMaterialId()+" = mp."+KitetifyDBHandler.getMaterialPlusMid()+" " +
                " inner JOIN "+KitetifyDBHandler.getTableMaterialType()+"  AS mt   ON mp."+KitetifyDBHandler.getMaterialPlusMtypeid()+" = mt."+KitetifyDBHandler.getMaterialTypeId()+" " +
                " inner JOIN "+KitetifyDBHandler.getTableMkite()+"  AS k  ON mp."+KitetifyDBHandler.getMaterialPlusMkiteId()+" = k."+KitetifyDBHandler.getMkiteId()+" " +
                " WHERE mt."+KitetifyDBHandler.getMaterialTypeName()+" = 'Kite' ";

        String groupBySelect = " GROUP BY k."+KitetifyDBHandler.getMkiteId()+" ";


        String selectQuery =" SELECT " +
                        allSelect +
                " 'Alle Kite'     AS '"+kites+"', " +
                " 'alle Typen'    AS '"+type+"', " +
                " 'ab 2010'       AS '"+year+"', " +
                " 'alle groessen' AS '"+size+"'  " +
                        joinSelect +
                        groupBySelect +
                " UNION " +
                " SELECT " +
                        allSelect +
                        kiteSelect +
                        joinSelect +
                "  AND k."+ KitetifyDBHandler.getMkiteId() +" = 1 " +
                        groupBySelect;
              /*  " UNION " +
                " SELECT " +
                        allSelect +
                        kiteSelect +
                        joinSelect +
                " AND k."+ KitetifyDBHandler.getMkiteId() +" = 2";*/

        String nameBuilder = "";


        Log.e("LOG", selectQuery);


        SQLiteDatabase db = dbHandler.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                StatisticKiteUsageOverview ov = new StatisticKiteUsageOverview();
                // Material attributes
                nameBuilder = c.getString(c.getColumnIndex(kites)) + " " +
                        c.getString(c.getColumnIndex(type)) + " " +
                        c.getString(c.getColumnIndex(year)) + " " +
                        c.getString(c.getColumnIndex(size))+ "qm";

                ov.set_kite_names(nameBuilder);
                ov.set_number_of_usages(     c.getInt(c.getColumnIndex(kiteUsage)));
                ov.set_hours_of_usages(      c.getDouble(c.getColumnIndex(duration)));
                ov.set_avg_hours_of_sessions(c.getDouble(c.getColumnIndex(session)));
                ov.set_avg_windspeed(        c.getDouble(c.getColumnIndex(windspeed)));

                // adding to windCharacters list
                kiteOverviewList.add(ov);
            } while (c.moveToNext());
        }

        c.close();
        db.close();

        return kiteOverviewList;
    }

}
