package com.kitetify.me.kitefy.Material;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.kitetify.me.kitefy.DatabaseModels.MKite;
import com.kitetify.me.kitefy.FormHelper;
import com.kitetify.me.kitefy.Persistence.DBHelperMKite;
import com.kitetify.me.kitefy.R;

import java.util.List;


public class UpdateKiteActivity extends AppCompatActivity {

    private Integer kiteIdSelect = -1;

    private Spinner spinKiteSelect;
    private static Button btnSave;
   // private KitetifyDBHandler dbHandler = new KitetifyDBHandler(this);

    private DBHelperMKite dbKite;
    private List<MKite> dbKiteList;
    private List<Integer> kiteIds;
    private List<String> spinKiteList;
    // if there is more, than 3 condition, a DB-Table should be created.
    private List<String> spinConditionList;
    FormHelper formHelper = new FormHelper();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_form);

        spinConditionList = formHelper.getConditionSpinner();

        dbPreparation(this);

        btnSave = (Button) findViewById(R.id.btnSave);
        addListenerOnButton(btnSave);
        btnSave.setText("Kite auswählen");

        // Kite SELECT
        spinKiteSelect = (Spinner) findViewById(R.id.spinSelect);
        //spinKiteList = setSpinnerItems(R.id.spinKiteSelect);
        spinKiteList   = formHelper.setSpinnerItems("Kite", dbKiteList);
        kiteIds        = formHelper.setSpinnerIds("Kite", dbKiteList);
        addItemOnSpinner(spinKiteSelect, spinKiteList);

        //Condition SELECT
        // Spinner spinCondition = (Spinner) findViewById(R.id.spinConditionSelect);
        // addItemOnSpinner(spinCondition, spinConditionList);

    }

    public void addListenerOnButton(Button btn){

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonClicked(v);
            }
        });
    }

    public void addItemOnSpinner(Spinner s, List<String> list){

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, list);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        s.setAdapter(dataAdapter);

    }
    public void buttonClicked(View v){

        kiteIdSelect = kiteIds.get(spinKiteSelect.getSelectedItemPosition());
        TextView infoText = (TextView) findViewById(R.id.infoText);
        infoText.setText("Selected id" + kiteIdSelect.toString() +  " " + spinKiteSelect.getSelectedItem());

        // fuer datenuebgabe zwischen 2 Activities
        Bundle mBundle = new Bundle();
        // varible einfuegen
        mBundle.putInt("selectedUpdateKite", kiteIdSelect);
        mBundle.putString("MaterialView", "UpdateKite");

        // neuen Intent erstellen
        Intent intent = new Intent(v.getContext(), com.kitetify.me.kitefy.Material.NewKiteActivity.class);
        // varibale zum intent heften
        intent.putExtras(mBundle);
        // activity wechseln
        startActivity(intent);

    }

    public void dbPreparation(Context context){

        // dbHandler ;
        //dbHandler.getReadableDatabase();

        dbKite     = new DBHelperMKite(this);
        dbKiteList = dbKite.getAllKiteNames();


    }
}
