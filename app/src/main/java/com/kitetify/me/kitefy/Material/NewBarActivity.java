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

import com.kitetify.me.kitefy.DatabaseModels.MBar;
import com.kitetify.me.kitefy.DatabaseModels.MKite;
import com.kitetify.me.kitefy.FormHelper;
import com.kitetify.me.kitefy.Persistence.DBHelperMBar;
import com.kitetify.me.kitefy.Persistence.DBHelperMKite;
import com.kitetify.me.kitefy.Persistence.KitetifyDBHandler;
import com.kitetify.me.kitefy.R;

import java.util.Date;
import java.util.List;


public class NewBarActivity extends AppCompatActivity {


    private Spinner spinConditionSelect;
    private TextView infoText;
    private static Button btnSave;
    private EditText brand, model, buyDate, lines, length, width, price, year, text;
    private KitetifyDBHandler dbHandler = new KitetifyDBHandler(this);

    private DBHelperMBar dbBar;
    private List<MBar> dbBarList;
    private List<Integer> barIds;

    private DBHelperMKite dbKite;
    private List<MKite> dbKiteList;
    private List<Integer> kiteIds;

    private List<String> spinKiteList;
    // if there is more, than 3 condition, a DB-Table should be created.
    private List<String> spinConditionList;
    private FormHelper formHelper = new FormHelper();
    private Integer updateBarId = -1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_form);

        spinConditionList = formHelper.getConditionSpinner();

        // Variablen uebergabe aus Update Activity
        Bundle mBundle = getIntent().getExtras();
        if(mBundle.getString("MaterialView").equals("UpdateBar")) {
            updateBarId = mBundle.getInt("selectedUpdateBar");
            if(!updateBarId.equals(-1)){
                referenceFormFieldsById();
                setFormFields();
                Log.e("update", updateBarId + "");
            }
        } else {
            updateBarId = -1;
        }
       // it.setText("Selected Bar: " + updateBarid.toString() + "");


        dbPreparation(this);

        btnSave = (Button) findViewById(R.id.btnSave);
        addListenerOnButton(btnSave);

        // Kite SELECT
       /* Spinner spinKite = (Spinner) findViewById(R.id.spinKiteSelect);
        //spinKiteList     = setSpinnerItems(R.id.spinKiteSelect);
        spinKiteList       = formHelper.setSpinnerItems("Kite", dbKiteList);
        kiteIds            = formHelper.setSpinnerIds("Kite", dbKiteList);
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
                !"".equals(length.getText().toString())    &&
                !"".equals(width.getText().toString())     &&
                !"".equals(year.getText().toString())      &&
                !"".equals(price.getText().toString())   /*  &&
                !"".equals(text.getText().toString())*/
                ) {

            if(updateBarId > -1){
                setUpdateEntry();
                Log.e("update", updateBarId + "");
                Log.e("DB - Entry", "updaten erfolgreich");
                formHelper.setInfoText("Es wurde eine Bar geändert.", infoText);
                changeInfoTextMarginTop(60);
            } else {
                setNewEntry();
                Log.e("new", updateBarId + "");
                Log.e("DB - Entry", "Speichern erfolgreich");
                formHelper.setInfoText("Es wurde eine Bar gespeichert.", infoText);
                changeInfoTextMarginTop(60);
            }

        } else {
            formHelper.setInfoText("Es konnte keine Bar gespeichert werden.", infoText);
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
        length.setText("");
        width.setText("");
        price.setText("");
        text.setText("");
    }

    public void dbPreparation(Context context){

        // dbHandler ;
        // dbHandler.getReadableDatabase();

        dbBar     = new DBHelperMBar(this);
        dbBarList = dbBar.getAllBarNames();

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

        MBar bar = getFormFields();

        // TODO bar 2 Kite Lösung finden
        //bar.set_kite(kiteIds.get(spinKiteSelect.getSelectedItemPosition())

        DBHelperMBar dbBar = new DBHelperMBar(this);
        long bId = dbBar.createBar(bar);

        MBar mBar = dbBar.getBar(bId);
        //Log.e("db text bar", "" + mBar.get_bID() + mBar.get_name() + mBar.get_type() + mBar.get_year());


        formHelper.setInfoText("" + mBar.get_name() + " " + mBar.get_type() + " " + mBar.get_year() + " gespeichert.", infoText);
        changeInfoTextMarginTop(30);
        clearForm();
    }

    public void setUpdateEntry(){

        MBar mBar = dbBar.getBar(updateBarId);
        MBar bar  = getFormFields();

        // ids  mit Uebertragen
        bar.set_uID(mBar.get_uID());
        //bar.set_buyDate(mBar.get_buyDate());
        bar.set_mID(mBar.get_mID());
        bar.set_bID(mBar.get_bID());

        DBHelperMBar dbBar = new DBHelperMBar(this);
        dbBar.updateBar(bar);

    }

    public void setFormFields(){

        String t = "";
        DBHelperMBar dbBar = new DBHelperMBar(this);
        MBar mBar =  dbBar.getBar(updateBarId);

        // TODO kiteIds.equals(mBar.get_kiteId);
        //spinConditionList.equals(mBar.get_condition());

      //  spinKiteSelect.setSelection(0);
        brand.setText(mBar.get_name());
        model.setText(mBar.get_type());
        year.setText(String.valueOf(mBar.get_year()));
       // spinConditionSelect.setSelection(spinConditionList.indexOf(mBar.get_condition()));
        spinConditionSelect.setSelection(2);
        Log.e("condition", "Cid=" + spinConditionList.indexOf(mBar.get_condition()) + "- bar.getCon " + mBar.get_condition() + " list.get "+spinConditionList.get(2));
        buyDate.setText(mBar.get_buyDate().toString());
        lines.setText(String.valueOf(mBar.get_lines()));
        length.setText(String.valueOf(mBar.get_length()));
        width.setText(String.valueOf(mBar.get_width()));
        price.setText(String.valueOf(mBar.get_price()));

        if(!(mBar.get_text().equals("null"))){
            t = mBar.get_text();
        }
        text.setText(t);

    }

    public MBar getFormFields(){

        MBar bar = new MBar();
        bar.set_name(brand.getText().toString());
        bar.set_type(model.getText().toString());
        bar.set_year(Integer.parseInt(year.getText().toString()));
        bar.set_price(Float.valueOf(price.getText().toString()));
        bar.set_condition(spinConditionSelect.getSelectedItem().toString());
        bar.set_buyDate(new Date(2));
        bar.set_uID(1);

        bar.set_lines(Integer.valueOf(lines.getText().toString()));
        bar.set_length(Float.valueOf(length.getText().toString()));
        bar.set_width(Float.valueOf(width.getText().toString()));
        bar.set_text(text.getText().toString());

        return bar;
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
        length          = (EditText) findViewById(R.id.inputLength);
        width           = (EditText) findViewById(R.id.inputWidth);
        price           = (EditText) findViewById(R.id.inputPrice);
        text            = (EditText) findViewById(R.id.inputText);

    }

}
