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

import com.kitetify.me.kitefy.DatabaseModels.MKite;
import com.kitetify.me.kitefy.FormHelper;
import com.kitetify.me.kitefy.Persistence.DBHelperMKite;
import com.kitetify.me.kitefy.R;

import java.util.Date;
import java.util.List;

public class DeleteKiteActivity extends AppCompatActivity {

    private Integer kiteIdSelect = -1;
    private EditText sellPrice, sellDate;
    private TextView infoText;
    private Spinner spinSelect;
    private Button btnDelete, btnSell;
    private FormHelper formHelper = new FormHelper();

    private DBHelperMKite dbKite;
    private List<MKite> dbKiteList;
    private List<Integer> kiteIds;
    private List<String> spinKiteList;

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

        // Bar SELECT
        spinSelect = (Spinner) findViewById(R.id.spinSelect);
        //spinKiteList = setSpinnerItems(R.id.spinKiteSelect);
        setSpinnerBar();

        if (kiteIds.isEmpty()) {
            noMaterialMessage();
        }


    }

    public void dbPreparation(Context context) {


        dbKite = new DBHelperMKite(this);
        dbKiteList = dbKite.getAllKiteNames();

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

    public void deleteAction(Integer _kiteID, DBHelperMKite _dbKite) {

        //Ein Object anlegen um die MaterialID zu erhalten.
        MKite kite = _dbKite.getKite(_kiteID);

        dbKite.deleteKite(kite.get_kID(), kite.get_mID());

        formHelper.setInfoText("Es wurde der Kite: " + kite.get_name() + " " + kite.get_type() + " " + kite.get_size() + " " + kite.get_year() + " gelöscht.", infoText);

    }

    public void updateAction(Integer _kiteID, DBHelperMKite _dbKite) {

        if (!"".equals(sellDate.getText().toString()) && !"".equals(sellPrice.getText().toString()))
        {

            //Ein Object anlegen um die MaterialID zu erhalten.
            MKite kite = _dbKite.getKite(_kiteID);



            kite.set_sellPrice(Float.valueOf(sellPrice.getText().toString()));
            //  bar.set_sellDate(new Date(Long.valueOf(sellDate.getText().toString())));
            kite.set_sellDate(new Date());
            kite.set_sellFlag(1);

            Log.e("SELLFLAG", "" + kite.get_name() + " - " + kite.get_sellDate() + " - " + kite.get_sellPrice() + " - " + kite.get_sellFlag());

            _dbKite.updateKite(kite);

            formHelper.setInfoText("Es wurde der Kite: "+kite.get_name()+" "+kite.get_type()+" "+kite.get_size()+" "+kite.get_year()+" verkauft.",infoText);
        }
        else{
            formHelper.setInfoText("Um eine Kite als Verkauft zu makieren, müssen die Felder Verkaufspreis und - Datum ausgefüllt werden.",infoText);
        }
    }


    public void buttonClick(View v, String changeMethode){

        if(!kiteIds.isEmpty()) {

            Integer kiteIdSelect = kiteIds.get(spinSelect.getSelectedItemPosition());
            //DBHelperMBar dbBar = new DBHelperMBar(this);

            if(changeMethode.equals("delete")){
                deleteAction(kiteIdSelect, dbKite);
            } else {
                updateAction(kiteIdSelect, dbKite);
                clearForm();
            }
            setSpinnerBar();

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
        formHelper.setInfoText("Es sind keine Kites in der Auswahl verfügbar", infoText);
    }

    public void clearForm(){

        spinSelect.setSelection(0);
        sellPrice.setText("");
        sellDate.setText("");
    }

    public void setSpinnerBar(){
        dbPreparation(this);
        spinKiteList   = formHelper.setSpinnerItems("Kite", dbKiteList);
        kiteIds        = formHelper.setSpinnerIds("Kite", dbKiteList);
        addItemOnSpinner(spinSelect, spinKiteList);
    }
}
