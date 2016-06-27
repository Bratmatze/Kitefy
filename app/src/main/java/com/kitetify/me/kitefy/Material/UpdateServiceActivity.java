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

import com.kitetify.me.kitefy.DatabaseModels.Material;
import com.kitetify.me.kitefy.FormHelper;
import com.kitetify.me.kitefy.Persistence.DBHelperMaterial;
import com.kitetify.me.kitefy.Persistence.KitetifyDBHandler;
import com.kitetify.me.kitefy.R;

import java.util.List;


public class UpdateServiceActivity extends AppCompatActivity {

    private Integer materialIdSelect = -1;

    private Spinner spinSelect;
    private static Button btnSave;
    private KitetifyDBHandler dbHandler = new KitetifyDBHandler(this);

    private DBHelperMaterial dbMaterial;
    private List<Material> dbMaterialList;
    private List<Integer> materialIds;
    private List<String> spinMaterialList;
    // if there is more, than 3 condition, a DB-Table should be created.
    FormHelper formHelper = new FormHelper();

    private static String identifier = "service";
    private static String mainIdentifier = "Material";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_form);

        dbPreparation(this);

        btnSave = (Button) findViewById(R.id.btnSave);
        addListenerOnButton(btnSave);

        // Material SELECT
        spinSelect         = (Spinner) findViewById(R.id.spinSelect);
        spinMaterialList   = formHelper.setSpinnerItems(mainIdentifier, dbMaterialList);
        materialIds        = formHelper.setSpinnerIds(mainIdentifier, dbMaterialList);
        addItemOnSpinner(spinSelect, spinMaterialList);

        //Condition SELECT
        // Spinner spinCondition = (Spinner) findViewById(R.id.spinConditionSelect);
        // addItemOnSpinner(spinCondition, spinConditionList);

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("msg", "The onResume() event");

        dbPreparation(this);

        btnSave = (Button) findViewById(R.id.btnSave);
        addListenerOnButton(btnSave);

        // Material SELECT
        Spinner spinSelect         = (Spinner) findViewById(R.id.spinSelect);
        spinMaterialList   = formHelper.setSpinnerItems(mainIdentifier, dbMaterialList);
        materialIds        = formHelper.setSpinnerIds(mainIdentifier, dbMaterialList);
        addItemOnSpinner(spinSelect, spinMaterialList);

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

        materialIdSelect = materialIds.get(spinSelect.getSelectedItemPosition());
        TextView infoText = (TextView) findViewById(R.id.infoText);
        infoText.setText("Selected id" + materialIdSelect.toString() +  " " + spinSelect.getSelectedItem());

        Bundle mBundle = new Bundle();
        mBundle.putInt("selectedUpdateMaterial", materialIdSelect);
        mBundle.putString("MaterialView", "UpdateService");

        Intent intent = new Intent(v.getContext(), com.kitetify.me.kitefy.Material.NewServiceActivity.class);
        intent.putExtras(mBundle);
        startActivity(intent);

    }

    public void dbPreparation(Context context){

        dbMaterial     = new DBHelperMaterial(this);
        dbMaterialList = dbMaterial.getAllMaterialNamesByType(identifier);

    }

}
