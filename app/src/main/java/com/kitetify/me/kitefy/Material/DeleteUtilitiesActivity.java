package com.kitetify.me.kitefy.Material;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.kitetify.me.kitefy.DatabaseModels.Material;
import com.kitetify.me.kitefy.FormHelper;
import com.kitetify.me.kitefy.Persistence.DBHelperMaterial;
import com.kitetify.me.kitefy.R;

import java.util.Date;
import java.util.List;

/**
 * Created by Matze on 03.03.16.
 */
public class DeleteUtilitiesActivity extends AppCompatActivity {

    private Integer materilaIdSelect = -1;
    private EditText sellPrice, sellDate;
    private TextView infoText;
    private Spinner spinSelect;
    private Button btnDelete, btnSell;
    private FormHelper formHelper = new FormHelper();

    private DBHelperMaterial dbMaterial;
    private List<Material> dbMaterialList;
    private List<Integer> materialIds;
    private List<String> spinMaterialList;
    private static String identifier = "util";
    private static String mainIdentifier = "Material";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_form);

        referenceFormFieldsById();

        dbPreparation(this);

        btnDelete = (Button) findViewById(R.id.btnDelete);
        addListenerOnButton(btnDelete, "delete");

        btnSell = (Button) findViewById(R.id.btnSell);
        addListenerOnButton(btnSell, "sell");

        // Material SELECT
        spinSelect = (Spinner) findViewById(R.id.spinSelect);
        //spinKiteList = setSpinnerItems(R.id.spinKiteSelect);
        setSpinner();

        if (materialIds.isEmpty()) {
            noMaterialMessage();
        }


    }

    public void dbPreparation(Context context) {


        dbMaterial = new DBHelperMaterial(this);
        dbMaterialList = dbMaterial.getAllMaterialNamesByType(identifier);

    }

    public void addListenerOnButton(Button btn, final String changeMethod) {

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (changeMethod.equals("delete")) {
                    buttonClick(v, changeMethod);
                } else {
                    buttonClick(v, changeMethod);
                }
            }
        });
    }

    public void addItemOnSpinner(Spinner s, List<String> list) {

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, list);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        s.setAdapter(dataAdapter);

    }

    public void deleteAction(Integer _materialID, DBHelperMaterial _dbMaterial) {

        //Ein Object anlegen um die MaterialID zu erhalten.
        Material m = _dbMaterial.getMaterial(_materialID);

        dbMaterial.deleteMaterial(m.get_mID());

        formHelper.setInfoText("Es wurde das Zubehör: " + m.get_name() + " " + m.get_type() + " " + m.get_year() + " gelöscht.", infoText);

    }

    public void updateAction(Integer _materialID, DBHelperMaterial _dbMaterial) {

        if (!"".equals(sellDate.getText().toString()) && !"".equals(sellPrice.getText().toString()))
        {

            //Ein Object anlegen um die MaterialID zu erhalten.
            Material m = _dbMaterial.getMaterial(_materialID);



            m.set_sellPrice(Float.valueOf(sellPrice.getText().toString()));
            //  bar.set_sellDate(new Date(Long.valueOf(sellDate.getText().toString())));
            m.set_sellDate(new Date());
            m.set_sellFlag(1);

            Log.e("SELLFLAG", "" + m.get_name() + " - " + m.get_sellDate() + " - " + m.get_sellPrice() + " - " + m.get_sellFlag());

            _dbMaterial.updateMaterial(m);

            formHelper.setInfoText("Es wurde das Zubehör: "+m.get_name()+" "+m.get_type()+" "+m.get_year()+" verkauft.",infoText);
        }
        else{
            formHelper.setInfoText("Um ein Zubhör als Verkauft zu makieren, müssen die Felder Verkaufspreis und - Datum ausgefüllt werden.",infoText);
        }
    }


    public void buttonClick(View v, String changeMethode){

        if(!materialIds.isEmpty()) {

            Integer materialIdSelect = materialIds.get(spinSelect.getSelectedItemPosition());
            //DBHelperMBar dbBar = new DBHelperMBar(this);

            if(changeMethode.equals("delete")){
                deleteAction(materialIdSelect, dbMaterial);
            } else {
                updateAction(materialIdSelect, dbMaterial);
                clearForm();
            }
            setSpinner();

        } else{
            noMaterialMessage();
        }

    }

    public void referenceFormFieldsById(){

        infoText        = (TextView) findViewById(R.id.infoText);
        spinSelect      = (Spinner)  findViewById(R.id.spinSelect);
        sellPrice       = (EditText) findViewById(R.id.inputSellPrice);
        sellDate        = (EditText) findViewById(R.id.inputSellDate);

    }
    public void noMaterialMessage(){
        formHelper.setInfoText("Es ist ein Zubehör in der Auswahl verfügbar", infoText);
    }

    public void clearForm(){

        spinSelect.setSelection(0);
        sellPrice.setText("");
        sellDate.setText("");
    }

    public void setSpinner(){
        dbPreparation(this);
        spinMaterialList   = formHelper.setSpinnerItems(mainIdentifier, dbMaterialList);
        materialIds        = formHelper.setSpinnerIds(mainIdentifier, dbMaterialList);
        addItemOnSpinner(spinSelect, spinMaterialList);
    }
}
