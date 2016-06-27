package com.kitetify.me.kitefy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.widget.TextView;

import com.kitetify.me.kitefy.DatabaseModels.User;
import com.kitetify.me.kitefy.Persistence.DBHelperUser;
import com.kitetify.me.kitefy.Persistence.KitetifyDBHandler;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private KitetifyDBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dbHandler = new KitetifyDBHandler(this, null, null, 1);
        dbHandler.setDBRefresh(dbHandler.getWritableDatabase());
        setContentView(R.layout.activity_main);

        final Button materialMenu = (Button) findViewById(R.id.materialMenu);
        final Button sessionMenu  = (Button)  findViewById(R.id.sessionMenu);
        final Button statisticMenu = (Button) findViewById(R.id.statistcMenu);

        materialMenu.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        buttonClicked(v, 0);
                    }
                }
        );

        sessionMenu.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        buttonClicked(v, 1);
                    }
                }
        );

        statisticMenu.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        buttonClicked(v, 2);
                    }
                }
        );



    }


    public void buttonClicked(View view, Integer m){
        Intent intent;

        if(m < 1 ) {
             intent = new Intent(view.getContext(), MaterialSelectViewActivity.class);
          //  userTest();
        } else if(m.equals(1)){
             intent = new Intent(view.getContext(), SurfSessionViewActivity.class);
        } else if (m.equals(2)) {
             intent = new Intent(view.getContext(), StatisticViewActivity.class);
        } else {
             intent = new Intent(view.getContext(), MaterialSelectViewActivity.class);
        }

        startActivityForResult(intent, 0);
    }

    public void userTest(){

        // Zeigt an, ob ein Nutzer in der Datenbank angelegt ist.
        TextView dbDummy = (TextView) findViewById(R.id.DatabaseTest);
        //String myName = dbHandler.getUser(1).get_name();
        DBHelperUser dbUser = new DBHelperUser(this);
        String myName2 = dbUser.getUser(1).get_name();

        User u = new User("Felix der Kater2");
        dbUser.createUser(u);

        //Update user
        User u1 = dbUser.getUser(0);
        u1.set_name("n.a.");
        dbUser.updateUser(u1);

        // delete user



        List<User> dbUsers = dbUser.getAllUsers();



        String b = "StartUser\n";

        for(int i = 0; i < dbUsers.size(); i++) {
            b += dbUsers.get(i).get_name();
            b += "(" + dbUsers.get(i).get_uID() + ")\n";
        }
/*        b += dbUsers.get(1).get_name();
        b += "("+dbUsers.get(1).get_uID()+")";*/



        dbDummy.setText("DBusername : " + myName2 + " --list:  " + b + "--");

    }
}
