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

import com.kitetify.me.kitefy.DatabaseModels.MBar;
import com.kitetify.me.kitefy.FormHelper;
import com.kitetify.me.kitefy.Persistence.DBHelperMBar;
import com.kitetify.me.kitefy.Persistence.DBHelperMKite;
import com.kitetify.me.kitefy.R;

import java.util.Date;
import java.util.List;


public class DeleteBarActivity extends AppCompatActivity {

    private Integer barIdSelect = -1;
    private EditText sellPrice, sellDate;
    private TextView infoText;
    private Spinner spinSelect;
    private Button btnDelete, btnSell;
    private FormHelper formHelper = new FormHelper();

    private DBHelperMBar dbBar;
    private List<MBar> dbBarList;
    private List<Integer> barIds;
    private List<String> spinBarList;

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

        if (barIds.isEmpty()) {
            noMaterialMessage();
        }


    }

    public void dbPreparation(Context context) {


        dbBar = new DBHelperMBar(this);
        dbBarList = dbBar.getAllBarNames();

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

    public void deleteAction(Integer _barID, DBHelperMBar _dbBar) {

        //Ein Object anlegen um die MaterialID zu erhalten.
        MBar bar = _dbBar.getBar(_barID);

        dbBar.deleteBar(bar.get_bID(), bar.get_mID());

        formHelper.setInfoText("Es wurde die Bar: " + bar.get_name() + " " + bar.get_type() + " " + bar.get_year() + " gelöscht.", infoText);

    }

    public void updateAction(Integer _barID, DBHelperMBar _dbBar) {

        if (!"".equals(sellDate.getText().toString()) && !"".equals(sellPrice.getText().toString()))
        {

            //Ein Object anlegen um die MaterialID zu erhalten.
            MBar bar = _dbBar.getBar(_barID);



            bar.set_sellPrice(Float.valueOf(sellPrice.getText().toString()));
          //  bar.set_sellDate(new Date(Long.valueOf(sellDate.getText().toString())));
            bar.set_sellDate(new Date());
            bar.set_sellFlag(1);

            Log.e("SELLFLAG", "" + bar.get_name() + " - " + bar.get_sellDate()  + " - " + bar.get_sellPrice()  + " - " + bar.get_sellFlag());

            _dbBar.updateBar(bar);

            formHelper.setInfoText("Es wurde die Bar: "+bar.get_name()+" "+bar.get_type()+" "+bar.get_year()+" verkauft.",infoText);
        }
        else{
            formHelper.setInfoText("Um eine Bar als Verkauft zu makieren, müssen die Felder Verkaufspreis und - Datum ausgefüllt werden.",infoText);
        }
    }


    public void buttonClick(View v, String changeMethode){

        if(!barIds.isEmpty()) {

            Integer barIdSelect = barIds.get(spinSelect.getSelectedItemPosition());
            //DBHelperMBar dbBar = new DBHelperMBar(this);

            if(changeMethode.equals("delete")){
                deleteAction(barIdSelect, dbBar);
            } else {
                updateAction(barIdSelect, dbBar);
                clearForm();
            }
            setSpinnerBar();

        } else{
            noMaterialMessage();
        }

    }

    public void referenceFormFieldsById(){

        infoText        = (TextView) findViewById(R.id.infoText);
        spinSelect   = (Spinner)  findViewById(R.id.spinSelect);
        sellPrice       = (EditText) findViewById(R.id.inputSellPrice);
        sellDate        = (EditText) findViewById(R.id.inputSellDate);

    }
    public void noMaterialMessage(){
        formHelper.setInfoText("Es sind keine Bars in der Auswahl verfügbar", infoText);
    }

    public void clearForm(){

        spinSelect.setSelection(0);
        sellPrice.setText("");
        sellDate.setText("");
    }

    public void setSpinnerBar(){
        dbPreparation(this);
        spinBarList   = formHelper.setSpinnerItems("Bar", dbBarList);
        barIds        = formHelper.setSpinnerIds("Bar", dbBarList);
        addItemOnSpinner(spinSelect, spinBarList);
    }
}
