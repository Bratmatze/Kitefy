package com.kitetify.me.kitefy.Material;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.kitetify.me.kitefy.DatabaseModels.MBar;
import com.kitetify.me.kitefy.FormHelper;
import com.kitetify.me.kitefy.Persistence.DBHelperMBar;
import com.kitetify.me.kitefy.Persistence.KitetifyDBHandler;
import com.kitetify.me.kitefy.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class UpdateBarActivity extends AppCompatActivity {

    private Integer barIdSelect = -1;

    private Spinner spinSelect;
    private static Button btnSave;
    private KitetifyDBHandler dbHandler = new KitetifyDBHandler(this);

    private DBHelperMBar dbBar;
    private List<MBar> dbBarList;
    private List<Integer> barIds;
    private List<String> spinBarList;
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

        // Bar SELECT
        spinSelect = (Spinner) findViewById(R.id.spinSelect);
        spinBarList   = formHelper.setSpinnerItems("Bar", dbBarList);
        barIds        = formHelper.setSpinnerIds("Bar", dbBarList);
        addItemOnSpinner(spinSelect, spinBarList);

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

        barIdSelect = barIds.get(spinSelect.getSelectedItemPosition());
        TextView infoText = (TextView) findViewById(R.id.infoText);
        infoText.setText("Selected id" + barIdSelect.toString() +  " " + spinSelect.getSelectedItem());

        // fuer datenuebgabe zwischen 2 Activities
        Bundle mBundle = new Bundle();
        // varible einfuegen
        mBundle.putInt("selectedUpdateBar", barIdSelect);
        mBundle.putString("MaterialView", "UpdateBar");

        // neuen Intent erstellen
        Intent intent = new Intent(v.getContext(), com.kitetify.me.kitefy.Material.NewBarActivity.class);
        // varibale zum intent heften
        intent.putExtras(mBundle);
        // activity wechseln
        startActivity(intent);

    }

    public void dbPreparation(Context context){

        // dbHandler ;
        //dbHandler.getReadableDatabase();

        dbBar     = new DBHelperMBar(this);
        dbBarList = dbBar.getAllBarNames();


    }


}
