package com.kitetify.me.kitefy.Material;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.kitetify.me.kitefy.DatabaseModels.MKite;
import com.kitetify.me.kitefy.FormHelper;
import com.kitetify.me.kitefy.Persistence.DBHelperMKite;
import com.kitetify.me.kitefy.Persistence.KitetifyDBHandler;
import com.kitetify.me.kitefy.R;

import java.util.Date;
import java.util.List;


public class NewKiteActivity extends AppCompatActivity {

    private Spinner /*spinKiteSelect,*/ spinConditionSelect;
    private TextView infoText;
    private static Button btnSave;
    private EditText brand, model, buyDate, lines, year, size, shape, windFrom, windTill, price, text;
   // private KitetifyDBHandler dbHandler = new KitetifyDBHandler(this);

    /*private DBHelperMBar dbBar;
    private List<MBar> dbBarList;
    private List<Integer> barIds;*/

    private DBHelperMKite dbKite;
    private List<MKite> dbKiteList;
    private List<Integer> kiteIds;

   // private List<String> spinKiteList;
    // if there is more, than 3 condition, a DB-Table should be created.
    private List<String> spinConditionList;
    private FormHelper formHelper = new FormHelper();
    private Integer updateKiteId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kite_form);

        spinConditionList = formHelper.getConditionSpinner();

        // Variablen uebergabe aus Update Activity
        Bundle mBundle = getIntent().getExtras();
        if(mBundle.getString("MaterialView").equals("UpdateKite")) {
            updateKiteId = mBundle.getInt("selectedUpdateKite");
            if(!updateKiteId.equals(-1)){
                referenceFormFieldsById();
                setFormFields();
                Log.e("update", updateKiteId + "");
            }
        } else {
            updateKiteId = -1;
        }


        dbPreparation(this);

        btnSave = (Button) findViewById(R.id.btnSave);
        addListenerOnButton(btnSave);

        // Kite SELECT
       /* Spinner spinKite = (Spinner) findViewById(R.id.spinKiteSelect);
        //spinKiteList = setSpinnerItems(R.id.spinKiteSelect);
        spinKiteList = formHelper.setSpinnerItems("Kite", dbKiteList);
        kiteIds      = formHelper.setSpinnerIds("Kite", dbKiteList);
        addItemOnSpinner(spinKite, spinKiteList);*/

        //Condition SELECT
        spinConditionSelect = (Spinner) findViewById(R.id.spinConditionSelect);
        addItemOnSpinner(spinConditionSelect, spinConditionList);
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

        referenceFormFieldsById();

        if(     !"".equals(brand.getText().toString())     &&
                !"".equals(model.getText().toString())     &&
                !"".equals(buyDate.getText().toString())   &&
                !"".equals(lines.getText().toString())     &&
                !"".equals(year.getText().toString())      &&
                !"".equals(price.getText().toString())   /*  &&
                !"".equals(text.getText().toString())*/
                ) {

            if(updateKiteId > -1){
                setUpdateEntry();
                Log.e("update", updateKiteId + "");
                Log.e("DB - Entry", "updaten erfolgreich");
                formHelper.setInfoText("Es wurde ein Kite geändert.", infoText);
                changeInfoTextMarginTop(60);
            } else {
                setNewEntry();
                Log.e("new", updateKiteId + "");
                Log.e("DB - Entry", "Speichern erfolgreich");
                formHelper.setInfoText("Es wurde ein Kite gespeichert.", infoText);
                changeInfoTextMarginTop(60);
            }

        } else {
            formHelper.setInfoText("Es konnte kein Kite gespeichert werden.", infoText);
        }


    }


    public void clearForm(){

        // spinKiteSelect.setSelection(0);
        brand.setText("");
        model.setText("");
        year.setText("");
        spinConditionSelect.setSelection(0);
        buyDate.setText("");
        lines.setText("");
        size.setText("");
        shape.setText("");
        windFrom.setText("");
        windTill.setText("");
        price.setText("");
        text.setText("");
    }

    public void dbPreparation(Context context){

        // dbHandler ;
        // dbHandler.getReadableDatabase();

       /* dbBar     = new DBHelperMBar(this);
        dbBarList = dbBar.getAllBarNames();*/

        dbKite     = new DBHelperMKite(this);
        dbKiteList = dbKite.getAllKiteNames();

    }

    public void changeInfoTextMarginTop(Integer i){
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) brand.getLayoutParams();
        params.setMargins(0, i, 0, 0);
        //substitute parameters for left, top, right, bottom
        brand.setLayoutParams(params);
    }
    public void setNewEntry(){

        MKite kite = getFormFields();

        // TODO bar 2 Kite Lösung finden
        //bar.set_kite(kiteIds.get(spinKiteSelect.getSelectedItemPosition())

        DBHelperMKite dbKite = new DBHelperMKite(this);
        long kId = dbKite.createKite(kite);

        MKite mKite = dbKite.getKite(kId);


        formHelper.setInfoText("" + mKite.get_name() + " " + mKite.get_type() + " " + mKite.get_size() + " " + mKite.get_year() + " gespeichert.", infoText);
        changeInfoTextMarginTop(30);
        clearForm();
    }

    public void setUpdateEntry(){

        MKite mKite = dbKite.getKite(updateKiteId);
        MKite kite  = getFormFields();

        // ids  mit Uebertragen
        kite.set_uID(mKite.get_uID());
        //bar.set_buyDate(mBar.get_buyDate());
        kite.set_mID(mKite.get_mID());
        kite.set_kID(mKite.get_kID());

        // TODO muss ein neuse Object angelegt werden?
        DBHelperMKite dbKite = new DBHelperMKite(this);
        dbKite.updateKite(kite);

    }

    public void setFormFields(){

        String t = "";
        DBHelperMKite dbKite = new DBHelperMKite(this);
        MKite mKite =  dbKite.getKite(updateKiteId);


        // TODO kiteIds.equals(mBar.get_kiteId);
        //spinConditionList.equals(mBar.get_condition());


        //  spinKiteSelect.setSelection(0);
        brand.setText(mKite.get_name());
        model.setText(mKite.get_type());
        year.setText(String.valueOf(mKite.get_year()));
        // spinConditionSelect.setSelection(spinConditionList.indexOf(mBar.get_condition()));
        spinConditionSelect.setSelection(2);
        Log.e("condition", "Cid=" + spinConditionList.indexOf(mKite.get_condition()) + "- kite.getCon " + mKite.get_condition() + " list.get " + spinConditionList.get(2));
        buyDate.setText(mKite.get_buyDate().toString());
        lines.setText(String.valueOf(mKite.get_lines()));
        price.setText(String.valueOf(mKite.get_price()));

        size.setText(String.valueOf(mKite.get_size()));
        shape.setText(mKite.get_shape());
        windFrom.setText(String.valueOf(mKite.get_windForm()));
        windTill.setText(String.valueOf(mKite.get_windTill()));


        if(!(mKite.get_text().equals("null"))){
            t = mKite.get_text();
        }
        text.setText(t);



    }

    public MKite getFormFields(){


        MKite kite = new MKite();
        kite.set_name(brand.getText().toString());
        kite.set_type(model.getText().toString());
        kite.set_year(Integer.parseInt(year.getText().toString()));
        kite.set_price(Float.valueOf(price.getText().toString()));
        kite.set_condition(spinConditionSelect.getSelectedItem().toString());
        kite.set_buyDate(new Date(2));
        kite.set_uID(1);

        kite.set_lines(Integer.valueOf(lines.getText().toString()));
        kite.set_size(Float.valueOf(size.getText().toString()));
        kite.set_shape(shape.getText().toString());
        kite.set_windForm(Float.valueOf(windFrom.getText().toString()));
        kite.set_windTill(Float.valueOf(windTill.getText().toString()));
        kite.set_text(text.getText().toString());

        return kite;
    }

    public void referenceFormFieldsById(){

        infoText        = (TextView) findViewById(R.id.infoText);
        brand           = (EditText) findViewById(R.id.inputBrand);
        model           = (EditText) findViewById(R.id.inputModel);
        year            = (EditText) findViewById(R.id.inputYear);
        // spinKiteSelect  = (Spinner)  findViewById(R.id.spinKiteSelect);
        spinConditionSelect  = (Spinner)  findViewById(R.id.spinConditionSelect);
        buyDate         = (EditText) findViewById(R.id.inputBuyDate);
        lines           = (EditText) findViewById(R.id.inputLines);
        size            = (EditText) findViewById(R.id.inputSize);
        shape           = (EditText) findViewById(R.id.inputShape);
        windFrom        = (EditText) findViewById(R.id.inputWindFrom);
        windTill        = (EditText) findViewById(R.id.inputWindTill);
        price           = (EditText) findViewById(R.id.inputPrice);
        text            = (EditText) findViewById(R.id.inputText);

    }
}
