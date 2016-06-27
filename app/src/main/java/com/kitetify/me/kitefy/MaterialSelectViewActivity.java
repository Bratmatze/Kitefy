package com.kitetify.me.kitefy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MaterialSelectViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.material_select_view);

        final Button btnKiteNew         = (Button)  findViewById(R.id.btnNewKite);
        final Button btnKiteUpdate      = (Button)  findViewById(R.id.btnUpdateKite);
        final Button btnKiteDelete      = (Button)  findViewById(R.id.btnDeleteKite);

        final Button btnBarNew          = (Button)  findViewById(R.id.btnNewBar);
        final Button btnBarUpdate       = (Button)  findViewById(R.id.btnUpdateBar);
        final Button btnBarDelete       = (Button)  findViewById(R.id.btnDeleteBar);

        final Button btnUtilNew         = (Button)  findViewById(R.id.btnNewUtil);
        final Button btnUtilUpdate      = (Button)  findViewById(R.id.btnUpdateUtil);
        final Button btnUtilDelete      = (Button)  findViewById(R.id.btnDeleteUtil);

        final Button btnServiceNew      = (Button)  findViewById(R.id.btnNewService);
        final Button btnServiceUpdate   = (Button)  findViewById(R.id.btnUpdateService);
        final Button btnServiceDelete   = (Button)  findViewById(R.id.btnDeleteService);


        addListenerOnButton(btnKiteNew, "new Kite");
        addListenerOnButton(btnKiteUpdate, "update Kite");
        addListenerOnButton(btnKiteDelete, "delete Kite");

        addListenerOnButton(btnBarNew, "new Bar");
        addListenerOnButton(btnBarUpdate, "update Bar");
        addListenerOnButton(btnBarDelete, "delete Bar");

        addListenerOnButton(btnUtilNew, "new Util");
        addListenerOnButton(btnUtilUpdate, "update Util");
        addListenerOnButton(btnUtilDelete, "delete Util");

        addListenerOnButton(btnServiceNew, "new Service");
        addListenerOnButton(btnServiceUpdate, "update Service");
        addListenerOnButton(btnServiceDelete, "delete Service");

    }



    public void buttonClicked(View view, String m){
        Intent intent;
        Bundle mBundle = new Bundle();

        if(m.equals("new Kite")){

            intent = new Intent(view.getContext(), com.kitetify.me.kitefy.Material.NewKiteActivity.class);
            mBundle.putString("MaterialView", "NewKite");
            // variable an das intent heften
            intent.putExtras(mBundle);

        } else if (m.equals("update Kite")) {
            intent = new Intent(view.getContext(), com.kitetify.me.kitefy.Material.UpdateKiteActivity.class);
            mBundle.putString("MaterialView", "UpdateKite");
            intent.putExtras(mBundle);

        } else if (m.equals("delete Kite")) {
            intent = new Intent(view.getContext(), com.kitetify.me.kitefy.Material.DeleteKiteActivity.class);
        } else if (m.equals("new Bar")) {

            intent = new Intent(view.getContext(), com.kitetify.me.kitefy.Material.NewBarActivity.class);
            mBundle.putString("MaterialView", "NewBar");
            intent.putExtras(mBundle);

        } else if (m.equals("update Bar")) {
            intent = new Intent(view.getContext(), com.kitetify.me.kitefy.Material.UpdateBarActivity.class);
            mBundle.putString("MaterialView", "UpdateBar");
            intent.putExtras(mBundle);

        } else if (m.equals("delete Bar")) {
            intent = new Intent(view.getContext(), com.kitetify.me.kitefy.Material.DeleteBarActivity.class);
        } else if (m.equals("new Util")) {

            intent = new Intent(view.getContext(), com.kitetify.me.kitefy.Material.NewUtilitiesActivity .class);
            mBundle.putString("MaterialView", "NewUtil");
            intent.putExtras(mBundle);

        } else if (m.equals("update Util")) {

            intent = new Intent(view.getContext(), com.kitetify.me.kitefy.Material.UpdateUtilitiesActivity.class);
            mBundle.putString("MaterialView", "UpdateUtil");
            intent.putExtras(mBundle);

        } else if (m.equals("delete Util")) {
            intent = new Intent(view.getContext(), com.kitetify.me.kitefy.Material.DeleteUtilitiesActivity.class);
        } else if (m.equals("new Service")) {

            intent = new Intent(view.getContext(), com.kitetify.me.kitefy.Material.NewServiceActivity.class);
            mBundle.putString("MaterialView", "NewService");
            intent.putExtras(mBundle);

        } else if (m.equals("update Service")) {

            intent = new Intent(view.getContext(), com.kitetify.me.kitefy.Material.UpdateServiceActivity.class);
            mBundle.putString("MaterialView", "UpdateService");
            intent.putExtras(mBundle);

        } else if (m.equals("delete Service")) {
            intent = new Intent(view.getContext(), com.kitetify.me.kitefy.Material.DeleteServiceActivity.class);
        } else {
            intent = new Intent(view.getContext(), com.kitetify.me.kitefy.MaterialSelectViewActivity.class);
        }

        startActivityForResult(intent, 0);
    }

    public void addListenerOnButton(Button btn, String btnName){

        final String m = btnName;
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonClicked(v, m);
            }
        });
    }
}
