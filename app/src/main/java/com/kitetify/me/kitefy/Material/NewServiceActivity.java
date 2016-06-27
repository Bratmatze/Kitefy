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

import com.kitetify.me.kitefy.DatabaseModels.Material;
import com.kitetify.me.kitefy.DatabaseModels.MaterialType;
import com.kitetify.me.kitefy.FormHelper;
import com.kitetify.me.kitefy.Persistence.DBHelperMaterial;
import com.kitetify.me.kitefy.Persistence.DBHelperMaterialType;
import com.kitetify.me.kitefy.R;

import java.util.Date;
import java.util.List;


public class NewServiceActivity extends AppCompatActivity{

    private Spinner /*spinKiteSelect,*/ spinMaterialTypeSelect;
    private TextView infoText;
    private static Button btnSave;
    private EditText brand, model, buyDate,  price;


    private DBHelperMaterial dbMaterial;
    private List<Material> dbMaterialList;
    private List<Integer> materialIds;

    private DBHelperMaterialType dbMaterialType;
    private List<MaterialType> dbMaterialTypeList;
    private List<String> spinMaterialTypeList;
    private List<Integer> materialTypeIds;

    // if there is more, than 3 condition, a DB-Table should be created.
    private FormHelper formHelper = new FormHelper();
    private Integer updateMaterialId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_form);

        //spinMaterialTypList = formHelper.getConditionSpinner();

        // Variablen uebergabe aus Update Activity
        Bundle mBundle = getIntent().getExtras();
        if(mBundle.getString("MaterialView").equals("UpdateService")) {
            updateMaterialId = mBundle.getInt("selectedUpdateMaterial");
            if(!updateMaterialId.equals(-1)){
                referenceFormFieldsById();
                setFormFields();
                Log.e("service", updateMaterialId + "");
            }
        } else {
            updateMaterialId = -1;
        }


        dbPreparation(this);

        btnSave = (Button) findViewById(R.id.btnSave);
        addListenerOnButton(btnSave);


        // MaterialType SELECT
       /* Spinner spinMaterialTypeSelect = (Spinner) findViewById(R.id.spinMaterialTypeSelect);
        spinMaterialTypeList = formHelper.setSpinnerItems("MaterialType", dbMaterialTypeList);
        materialTypeIds      = formHelper.setSpinnerIds("MaterialType", dbMaterialTypeList);
        Log.e("df", " sth: "+dbMaterialTypeList.get(0).get_name().toString() + " sth+: "+ dbMaterialTypeList.get(0).get_name().toString() );
        addItemOnSpinner(spinMaterialTypeSelect, spinMaterialTypeList);)*/

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
                !"".equals(price.getText().toString())
                ) {

            if(updateMaterialId > -1){
                setUpdateEntry();
                //Log.e("update", updateMaterialId + "");
                //Log.e("DB - Entry", "updaten erfolgreich");
                formHelper.setInfoText("Es wurde ein Kite geändert.", infoText);
                changeInfoTextMarginTop(60);
            } else {
                setNewEntry();
                //Log.e("new", updateMaterialId + "");
                //Log.e("DB - Entry", "Speichern erfolgreich");
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
        //spinMaterialTypeSelect.setSelection(0);
        buyDate.setText("");
        price.setText("");

    }

    public void dbPreparation(Context context){

        dbMaterial     = new DBHelperMaterial(this);
        dbMaterialList = dbMaterial.getAllMaterialNamesByType("service");

        dbMaterialType      = new DBHelperMaterialType(this);
        dbMaterialTypeList = dbMaterialType.getAllMaterialTypes();

    }

    public void changeInfoTextMarginTop(Integer i){
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) brand.getLayoutParams();
        params.setMargins(0, i, 0, 0);
        //substitute parameters for left, top, right, bottom
        brand.setLayoutParams(params);
    }
    public void setNewEntry(){

        Material mat = getFormFields();

        // TODO bar 2 Kite Lösung finden
        //bar.set_kite(kiteIds.get(spinKiteSelect.getSelectedItemPosition())

        DBHelperMaterial dbMaterial = new DBHelperMaterial(this);
        long mId = dbMaterial.createMaterial(mat, 4);

        Material mMat = dbMaterial.getMaterial(mId);


        formHelper.setInfoText("" + mMat.get_name() + " " + mMat.get_type() +  " gespeichert.", infoText);
        changeInfoTextMarginTop(30);
        clearForm();
    }

    public void setUpdateEntry(){

        Material mat   = dbMaterial.getMaterial(updateMaterialId);
        Material mMat  = getFormFields();


        // ids  mit Uebertragen
        mat.set_uID(mMat.get_uID());
        mat.set_buyDate(mMat.get_buyDate());
        //mat.set_mID(mMat.get_mID());

        mat.set_name(mMat.get_name());
        mat.set_type(mMat.get_type());
        mat.set_price(mMat.get_price());

        Log.e("ss", mat.get_mID() + " " + mat.get_name() + " " + mat.get_price());
        // TODO muss ein neuse Object angelegt werden?
        //DBHelperMaterial dbMaterial = new DBHelperMaterial(this);
        int z = dbMaterial.updateMaterial(mat);

        Log.e("s", z + "");
        //Material t = dbMaterial.getMaterial(z);




    }

    public void setFormFields(){

        DBHelperMaterial dbMaterial = new DBHelperMaterial(this);
        Material mat = dbMaterial.getMaterial(updateMaterialId);


        // TODO kiteIds.equals(mBar.get_kiteId);
        //spinConditionList.equals(mBar.get_condition());
       // Log.e(" - - - - ",mat.get_name());


        //  spinKiteSelect.setSelection(0);
        brand.setText(mat.get_name());
        model.setText(mat.get_type());
        // spinConditionSelect.setSelection(spinConditionList.indexOf(mBar.get_condition()));
        //spinMaterialTypeSelect.setSelection(spinMaterialTypList.indexOf(/*TODO mat.get_condition()*/);
        buyDate.setText(mat.get_buyDate().toString());
        price.setText(String.valueOf(mat.get_price()));


    }

    public Material getFormFields(){


        Material m = new Material();

        m.set_name(brand.getText().toString());
        m.set_type(model.getText().toString());
        m.set_price(Float.valueOf(price.getText().toString()));
        m.set_buyDate(new Date(2));
        m.set_uID(1);

        return m;
    }

    public void referenceFormFieldsById(){

        infoText        = (TextView) findViewById(R.id.infoText);
        brand           = (EditText) findViewById(R.id.inputBrand);
        model           = (EditText) findViewById(R.id.inputModel);
        // spinKiteSelect  = (Spinner)  findViewById(R.id.spinKiteSelect);
        // spinMaterialTypeSelect  = (Spinner)  findViewById(R.id.spinMaterialTypeSelect);
        buyDate         = (EditText) findViewById(R.id.inputBuyDate);
        price           = (EditText) findViewById(R.id.inputPrice);

       // Log.e("ALL FIELDS - referenceFormFieldsById", "" +brand.getText() + "  "+ model.getText()+ " " + buyDate.getText() + " " + price.getText() );

    }

}
