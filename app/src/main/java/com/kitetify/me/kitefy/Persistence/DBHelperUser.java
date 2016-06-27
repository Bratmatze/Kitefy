package com.kitetify.me.kitefy.Persistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.kitetify.me.kitefy.DatabaseModels.User;

import java.util.ArrayList;
import java.util.List;


public class DBHelperUser {

    private KitetifyDBHandler dbHandler;
    private Context ctx;

    public DBHelperUser(Context ctx) {
        this.ctx = ctx;
        dbHandler = new KitetifyDBHandler(ctx.getApplicationContext());
    }


    /*
    * User Datensatz anlegen
    * Die SQLite Tabelle User besteht nur aus ID (Autoincrement) und NAME,
    * daher reicht es für einen neuen Datensatz, nur ein neues User-Objekt
    * mit NAMEN zu uebergeben.
    */
    public long createUser(User user){

        SQLiteDatabase db = dbHandler.getWritableDatabase();


        ContentValues values = new ContentValues();

        //
        values.put(KitetifyDBHandler.getUserName(), user.get_name());

        // insert row
        long user_id = db.insert(KitetifyDBHandler.getTableUser(), null, values);

        db.close();

        return user_id;
    }
    /*
    * Einen USER via ID aus der Datenbank selektieren und als User-Objekt
    * ausgeben
    */
    public User getUser(Integer user_id){

        SQLiteDatabase db = dbHandler.getWritableDatabase();

        String selectQuery = "SELECT  * FROM " + KitetifyDBHandler.getTableUser() + " WHERE "
                + KitetifyDBHandler.getUserId() + " = " + user_id;

        Log.e("LOG", selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        User u = new User();
        u.set_uID(c.getInt(c.getColumnIndex(KitetifyDBHandler.getUserId())));
        u.set_name(c.getString(c.getColumnIndex(KitetifyDBHandler.getUserName())));

        c.close();
       // db.close();

        return u;
    }

    /*
    * Alle USER aus der Datenbank selektieren und eine USER-Objekt-Liste ausgeben
    */
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + KitetifyDBHandler.getTableUser();

        Log.e("LOG", selectQuery);


        SQLiteDatabase db = dbHandler.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                User u = new User();
                u.set_uID(c.getInt((c.getColumnIndex(KitetifyDBHandler.getUserId()))));
                u.set_name(c.getString(c.getColumnIndex(KitetifyDBHandler.getUserName())));

                // adding to user list
                users.add(u);
            } while (c.moveToNext());
        }

        c.close();
       // db.close();

        return users;
    }

    /*
    * Einen USER-Eintrag ueber das User-Objekt in der Datenbank aendern.
    */
    public int updateUser(User u) {

        SQLiteDatabase db = dbHandler.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KitetifyDBHandler.getUserName(), u.get_name());

        // updating row
        return db.update(KitetifyDBHandler.getTableUser(), values, KitetifyDBHandler.getUserId() + " = ?",
                new String[] { String.valueOf(u.get_uID()) });

       // db.close();
    }

    /*
    * Einen USER aus der Datenbank loeschen.
    */
    public void deleteUser(long user_id) {
        SQLiteDatabase db = dbHandler.getWritableDatabase();
        db.delete(KitetifyDBHandler.getTableUser(), KitetifyDBHandler.getUserId() + " = ?",
                new String[] { String.valueOf(user_id) });
    }

}
