package com.kitetify.me.kitefy.Persistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.kitetify.me.kitefy.DatabaseModels.WindCharacter;

import java.util.ArrayList;
import java.util.List;

public class DBHelperWindCharacter {


    private KitetifyDBHandler dbHandler;
    private Context ctx;

    public DBHelperWindCharacter(Context ctx) {
        this.ctx = ctx;
        dbHandler = new KitetifyDBHandler(ctx.getApplicationContext());
    }


    /*
    * Ein WindCharacter Datensatz anlegen
    * Die SQLite Tabelle WindCharacter besteht nur aus ID (Autoincrement) und NAME,
    * daher reicht es für einen neuen Datensatz, nur ein neues WindCharacter-Objekt
    * mit NAMEN zu uebergeben.
    */
    public long createWindChar(WindCharacter wc){

        SQLiteDatabase db = dbHandler.getWritableDatabase();


        ContentValues values = new ContentValues();

        //
        values.put(KitetifyDBHandler.getWindCharacterName(), wc.get_name());

        // insert row
        long windChar_id = db.insert(KitetifyDBHandler.getTableWindcharacter(), null, values);

        db.close();

        return windChar_id;
    }
    /*
    * Ein WindCharacter via ID aus der Datenbank selektieren und als WindCharacter-Objekt
    * ausgeben
    */
    public WindCharacter getWindCharacter(Integer windChar_id){

        SQLiteDatabase db = dbHandler.getWritableDatabase();

        String selectQuery = "SELECT  * FROM " + KitetifyDBHandler.getTableWindcharacter() + " WHERE "
                + KitetifyDBHandler.getWindCharacterId() + " = " + windChar_id;

        Log.e("LOG", selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        WindCharacter windChar = new WindCharacter();
        windChar.set_wcID(c.getInt(c.getColumnIndex(KitetifyDBHandler.getWindCharacterId())));
        windChar.set_name(c.getString(c.getColumnIndex(KitetifyDBHandler.getWindCharacterName())));

        c.close();
        // db.close();

        return windChar;
    }

    /*
    * Alle WindCharacter aus der Datenbank selektieren und eine USER-Objekt-Liste ausgeben
    */
    public List<WindCharacter> getAllWindChars() {
        List<WindCharacter> windCharacters = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + KitetifyDBHandler.getTableWindcharacter();

        Log.e("LOG", selectQuery);


        SQLiteDatabase db = dbHandler.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                WindCharacter wc = new WindCharacter();
                wc.set_wcID(c.getInt((c.getColumnIndex(KitetifyDBHandler.getWindCharacterId()))));
                wc.set_name(c.getString(c.getColumnIndex(KitetifyDBHandler.getWindCharacterName())));

                // adding to windCharacters list
                windCharacters.add(wc);
            } while (c.moveToNext());
        }

        c.close();
        // db.close();

        return windCharacters;
    }

    /*
    * Einen WindCharacter-Eintrag ueber das WindCharacter-Objekt in der Datenbank aendern.
    */
    public int updateWindCharacter(WindCharacter wc) {

        SQLiteDatabase db = dbHandler.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KitetifyDBHandler.getWindCharacterName(), wc.get_name());

        // updating row
        return db.update(KitetifyDBHandler.getTableWindcharacter(), values, KitetifyDBHandler.getWindCharacterId() + " = ?",
                new String[] { String.valueOf(wc.get_wcID()) });

        // db.close();
    }

    /*
    * Ein WindCharacter aus der Datenbank loeschen.
    */
    public void deleteWindCharacter(long wc_id) {
        SQLiteDatabase db = dbHandler.getWritableDatabase();
        db.delete(KitetifyDBHandler.getTableWindcharacter(), KitetifyDBHandler.getWindCharacterId() + " = ?",
                new String[] { String.valueOf(wc_id) });
    }
}
