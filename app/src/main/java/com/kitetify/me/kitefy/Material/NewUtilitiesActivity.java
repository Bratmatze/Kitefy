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
import com.kitetify.me.kitefy.DatabaseModels.MaterialPlus;
import com.kitetify.me.kitefy.DatabaseModels.MaterialType;
import com.kitetify.me.kitefy.FormHelper;
import com.kitetify.me.kitefy.Persistence.DBHelperMaterial;
import com.kitetify.me.kitefy.Persistence.DBHelperMaterialType;
import com.kitetify.me.kitefy.Persistence.DBHelperMaterialPlus;
import com.kitetify.me.kitefy.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class NewUtilitiesActivity extends AppCompatActivity{

    private Spinner /*spinKiteSelect,*/ spinConditionSelect, spinMaterialTypeSelect;
    private TextView infoText;
    private static Button btnSave;
    private EditText brand, model, buyDate, year, price;


    private DBHelperMaterial dbMaterial;
    private List<Material> dbMaterialList;
    private List<Integer> materialIds;

    private DBHelperMaterialType dbMaterialType;
    private List<MaterialType> dbMaterialTypeList;
    private List<Integer> materialTypeIds, spinConditionIds;

    // private List<String> spinKiteList;
    // if there is more, than 3 condition, a DB-Table should be created.
    private List<String> spinConditionList, spinMaterialTypeList;
    private FormHelper formHelper;
    private Integer updateMaterialId = -1;

    // NewService/ NewUtil
    private static String identifierUtil = "util";
    private static String mainIdentifier = "Material";
    private String identifier;
    private Integer mTypeID;
    private String formType = "new";
    private String trueMessage;

    //private static List<String> conditionSpinner = new ArrayList<String>() {{ add("neu"); add("sogut wie neu"); add("gebraucht"); }};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_util_form);

        dbPreparation(this);

        formHelper = new FormHelper();
        referenceFormFieldsById();


       // List<String> spinCondList = formHelper.setSpinnerItems("Condition", formHelper.getConditionSpinner());


        //Condition Spinner SELECT
        spinConditionSelect = (Spinner) findViewById(R.id.spinConditionSelect);
        spinConditionList   = formHelper.setSpinnerItems("Condition", formHelper.getConditionSpinner());
        spinConditionIds    = formHelper.setSpinnerIds("Condition", formHelper.getConditionSpinner());
        addItemOnSpinner(spinConditionSelect, spinConditionList);

        // MaterialType Spinner SELECT
        spinMaterialTypeSelect = (Spinner) findViewById(R.id.spinMaterialTypeSelect);
        spinMaterialTypeList = formHelper.setSpinnerItems("MaterialType", dbMaterialTypeList);
        materialTypeIds      = formHelper.setSpinnerIds("MaterialType", dbMaterialTypeList);
        addItemOnSpinner(spinMaterialTypeSelect, spinMaterialTypeList);


        // Variablen uebergabe aus Update Activity
        Bundle mBundle = getIntent().getExtras();
        if(mBundle.getString("MaterialView").equals("UpdateUtil")) {
            updateMaterialId = mBundle.getInt("selectedUpdateMaterial");
            if(!updateMaterialId.equals(-1)){
                referenceFormFieldsById();
                setFormFields();
                Log.e("update", updateMaterialId + "");
            }
        } else {
            updateMaterialId = -1;
        }


        if(mBundle.getString("MaterialView").equals("UpdateUtil")) {
            updateMaterialId = mBundle.getInt("selectedUpdateMaterial");
            if(!updateMaterialId.equals(-1)){
                setSpinner();
                Log.e("update", updateMaterialId + "");
            }
        }

        btnSave = (Button) findViewById(R.id.btnSave);
        addListenerOnButton(btnSave);



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

       // referenceFormFieldsById();

        if(     !"".equals(brand.getText().toString())     &&
                !"".equals(model.getText().toString())     &&
                !"".equals(buyDate.getText().toString())   &&
                !"".equals(year.getText().toString())      &&
                !"".equals(price.getText().toString())
                ) {

            if(updateMaterialId > -1){
                setUpdateEntry();
                //Log.e("update", updateMaterialId + "");
               // Log.e("DB - Entry", "updaten erfolgreich");
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

        brand.setText("");
        model.setText("");
        year.setText("");
        //spinConditionSelect.setSelection(0);
        //spinMaterialTypeSelect.setSelection(0);
        buyDate.setText("");
        price.setText("");

    }

    public void dbPreparation(Context context){

        dbMaterial     = new DBHelperMaterial(context);
        dbMaterialList = dbMaterial.getAllMaterialNamesByType(identifierUtil);

        dbMaterialType      = new DBHelperMaterialType(context);
        dbMaterialTypeList  = dbMaterialType.getAllMaterialNamesByType(identifierUtil);

    }

    public void changeInfoTextMarginTop(Integer i){
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) spinMaterialTypeSelect.getLayoutParams();
        params.setMargins(0, i, 0, 0);
        //substitute parameters for left, top, right, bottom
        spinMaterialTypeSelect.setLayoutParams(params);
    }
    public void setNewEntry(){

        Material mat = getFormFields();
        MaterialType mt = getFormFieldMaterialType();


        // TODO bar 2 Kite Lösung finden
        //bar.set_kite(kiteIds.get(spinKiteSelect.getSelectedItemPosition())

        Log.e("New Util Entry:", "Condition= " + mat.get_condition());

        DBHelperMaterial dbMaterial = new DBHelperMaterial(this);
        long mId = dbMaterial.createMaterial(mat, mt.get_mTypeID());

        Material mMat = dbMaterial.getMaterial(mId);


        formHelper.setInfoText("" + mMat.get_name() + " " + mMat.get_type() + " gespeichert.", infoText);
        changeInfoTextMarginTop(30);
        clearForm();
    }

    public void setUpdateEntry(){

        Material mat = dbMaterial.getMaterial(updateMaterialId);
        Material mMat  = getFormFields();
        MaterialType mt = dbMaterialType.getMaterialType(mat.get_mID());
        MaterialType mMaty = getFormFieldMaterialType();

        // ids  mit Uebertragen
        // mat.set_uID(mMat.get_uID());
        mat.set_name(mMat.get_name());
        mat.set_type(mMat.get_type());
        mat.set_year(mMat.get_year());
        mat.set_buyDate(mMat.get_buyDate());
        mat.set_condition(mMat.get_condition());

        //mat.set_t(mMat.get_type());
        mat.set_year(mMat.get_year());
       // mat.set_mID(mMat.get_mID());

        mt.set_mTypeID(mMaty.get_mTypeID());


        Log.e("update", "matID:"+mat.get_mID() + "; mtID:"+mt.get_mTypeID() );

        // TODO muss ein neuse Object angelegt werden?
        //DBHelperMaterial dbMaterial = new DBHelperMaterial(this);
        dbMaterial.updateMaterial(mat);

        //DBHelperMaterialType dbHelperMaterialType = new DBHelperMaterial(this);
        dbMaterialType.updateMaterialType(mt);

    }

    public void setFormFields(){

        DBHelperMaterial dbMaterial = new DBHelperMaterial(this);
        Material mat =  dbMaterial.getMaterial(updateMaterialId);


        brand.setText(mat.get_name());
        model.setText(mat.get_type());
        year.setText(String.valueOf(mat.get_year()));

        buyDate.setText(mat.get_buyDate().toString());
        price.setText(String.valueOf(mat.get_price()));

    }
    public void setSpinner(){

        DBHelperMaterial dbMaterial = new DBHelperMaterial(this);
        Material mat =  dbMaterial.getMaterial(updateMaterialId);

        DBHelperMaterialPlus dbMaterialPlus = new DBHelperMaterialPlus(this);
        MaterialPlus mp = dbMaterialPlus.getMaterialPlus(mat.get_mID());

        DBHelperMaterialType dbMaterialType = new DBHelperMaterialType(this);
        MaterialType mt = dbMaterialType.getMaterialType(mp.get_mTypeID());

        //spinSelect.getSelectedItem()
        spinConditionSelect.setSelection(spinConditionList.indexOf(mat.get_condition()));

        Log.e("spinner", mt.get_name() + "<-mat " + spinMaterialTypeList.indexOf(mat.get_name()) + "<- index of spinConList ");
        spinMaterialTypeSelect.setSelection(spinMaterialTypeList.indexOf(mt.get_name()));

    }

    public Material getFormFields(){

        Material m = new Material();

        m.set_name(brand.getText().toString());
        m.set_type(model.getText().toString());
        m.set_year(Integer.valueOf(year.getText().toString()));
        m.set_price(Float.valueOf(price.getText().toString()));
//        m.set_condition(spinConditionSelect.getSelectedItem().toString());
       // spinConditionSelect.getAdapter();



        Log.e("allSpinn", spinConditionSelect.getSelectedItem().toString() + "; Material: " +spinMaterialTypeList + "; ");

        Log.e("CC", spinConditionSelect.getCount() + "" );
        if(spinConditionSelect.getCount() > 0){
            Log.e("getFormField - setSel", "not null");
        }else{
            Log.e("getFormField - setSel", "null");
            spinConditionSelect.setSelection(1);

        }

       /* if(spinMaterialTypeSelect.getCount() < 0){
            spinMaterialTypeSelect.setSelection(1);
            Log.e("getFormField - setSel", "null");
        }else{
            Log.e("getFormField - setSel", "not null");
        }*/




        String s = String.valueOf(spinMaterialTypeSelect.getSelectedItem());
        Log.e("getFormField", "condi:" + s + " mat: " +m.get_condition());

        m.set_buyDate(new Date(2));
        m.set_uID(1);

        return m;
    }

    public MaterialType getFormFieldMaterialType(){

        MaterialType mt = new MaterialType();

        mt.set_mTypeID(materialTypeIds.get(spinMaterialTypeSelect.getSelectedItemPosition()));
        mt.set_name(spinMaterialTypeList.get(spinMaterialTypeSelect.getSelectedItemPosition()));

        return mt;
    }

    public void referenceFormFieldsById(){

        infoText        = (TextView) findViewById(R.id.infoText);
        brand           = (EditText) findViewById(R.id.inputBrand);
        model           = (EditText) findViewById(R.id.inputModel);
        year            = (EditText) findViewById(R.id.inputYear);
        spinConditionSelect     = (Spinner)  findViewById(R.id.spinKiteSelect);
        spinMaterialTypeSelect  = (Spinner)  findViewById(R.id.spinMaterialTypeSelect);
        buyDate         = (EditText) findViewById(R.id.inputBuyDate);
        price           = (EditText) findViewById(R.id.inputPrice);


    }


}
