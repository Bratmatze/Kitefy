package com.kitetify.me.kitefy;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.util.Log;

import com.kitetify.me.kitefy.DatabaseModels.Forecast;
import com.kitetify.me.kitefy.DatabaseModels.MBar;
import com.kitetify.me.kitefy.DatabaseModels.MKite;
import com.kitetify.me.kitefy.DatabaseModels.SurfSession;
import com.kitetify.me.kitefy.DatabaseModels.SurfSessionCharacter;
import com.kitetify.me.kitefy.DatabaseModels.WindCharacter;
import com.kitetify.me.kitefy.DatabaseModels.WindDirection;
import com.kitetify.me.kitefy.Persistence.DBHelperForecast;
import com.kitetify.me.kitefy.Persistence.DBHelperMBar;
import com.kitetify.me.kitefy.Persistence.DBHelperMKite;
import com.kitetify.me.kitefy.Persistence.DBHelperSurfSession;
import com.kitetify.me.kitefy.Persistence.DBHelperSurfSessionCharacter;
import com.kitetify.me.kitefy.Persistence.DBHelperWindCharacter;
import com.kitetify.me.kitefy.Persistence.DBHelperWindDirection;
import com.kitetify.me.kitefy.Persistence.KitetifyDBHandler;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SurfSessionViewActivity extends AppCompatActivity {

    private Spinner spinKiteSelect, spinBarSelect, spinWindDirectionSelect;
    private Spinner spinWindCharacterSelect, spinSurfSessionCharacterSelect, spinForecastSelect;
    private static Button btnSubmitSession;
    private EditText windAVG, windMAX, windMIN, mDate, duration, mSpot;
    private TextView surfSessionInfoText;
    private KitetifyDBHandler dbHandler = new KitetifyDBHandler(this);
    private FormHelper formHelper = new FormHelper();

    private DBHelperMBar dbBar;
    private List<MBar> dbBarList;
    private List<Integer> barIds;

    private DBHelperMKite dbKite;
    private List<MKite> dbKiteList;
    private List<Integer> kiteIds;

    private DBHelperWindDirection dbWind;
    private List<WindDirection> dbWindList;
    private List<Integer> windIds;

    private DBHelperWindCharacter dbWindCh;
    private List<WindCharacter> dbWindChList;
    private List<Integer> windChIds;

    private DBHelperSurfSessionCharacter dbSurfSessionCharacter;
    private List<SurfSessionCharacter> dbSurfSessionCharacterList;
    private List<Integer> sscIds;

    private DBHelperForecast dbForecast;
    private List<Forecast> dbForecastList;
    private List<Integer> fcIds;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dbPreparation(this);

        setContentView(R.layout.surf_session_view);
        //setContentView(R.layout.surf_session_form);

        Spinner k = (Spinner) findViewById(R.id.spinKiteSelect);
        Spinner b = (Spinner) findViewById(R.id.spinBarSelect);
        Spinner w = (Spinner) findViewById(R.id.spinWindDirectionSelect);
        Spinner wc = (Spinner) findViewById(R.id.spinWindCharacterSelect);
        Spinner f = (Spinner) findViewById(R.id.spinForecastSelect);
        //Table SurfSessionCharacter
        Spinner sc = (Spinner) findViewById(R.id.spinFunSelect);


        List<String> spinKiteList = formHelper.setSpinnerItems("Kite", dbKiteList);
        List<String> spinBarList = formHelper.setSpinnerItems("Bar", dbBarList);
        List<String> spinWindList = formHelper.setSpinnerItems("WindDirection", dbWindList);
        List<String> spinWindCList = formHelper.setSpinnerItems("WindCharacter", dbWindChList);
        List<String> spinForecastList = formHelper.setSpinnerItems("Forecast", dbForecastList);
        List<String> spinSCList = formHelper.setSpinnerItems("SurfSessionCharacter", dbSurfSessionCharacterList);

        Log.e("ssc:", dbSurfSessionCharacterList.size() + " spinList:"+ spinSCList.size());

        kiteIds   = formHelper.setSpinnerIds("Kite", dbKiteList);
        barIds    = formHelper.setSpinnerIds("Bar", dbBarList);
        windIds   = formHelper.setSpinnerIds("WindDirection", dbWindList);
        windChIds = formHelper.setSpinnerIds("WindCharacter", dbWindChList);
        fcIds     = formHelper.setSpinnerIds("Forecast", dbForecastList);
        sscIds    = formHelper.setSpinnerIds("SurfSessionCharacter", dbSurfSessionCharacterList);

        Log.e("ssc:", dbSurfSessionCharacterList.size() + " spinList:"+ spinSCList.size());

        addItemOnSpinner(k, spinKiteList);
        addItemOnSpinner(b, spinBarList);
        addItemOnSpinner(w, spinWindList);
        addItemOnSpinner(wc,spinWindCList);
        addItemOnSpinner(f, spinForecastList);
        //Table SurfSessionCharacter
        addItemOnSpinner(sc, spinSCList);
        //
        addListenerOnButton();

        clearSurfSessionInfoText();

    }

    public  void addListenerOnButton() {

        btnSubmitSession = (Button) findViewById(R.id.btnSubmitSession);

        btnSubmitSession.setOnClickListener(new View.OnClickListener() {
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


/*
    public void addListenerOnSpinnerItemSelection(Integer spinId) {
        Spinner s = (Spinner) findViewById(spinId);
        s.setOnItemSelectedListener(new CustomOnItemSelectListener());
    } */

    public void clearSurfSessionInfoText(){


        String sit = "";
        surfSessionInfoText = (TextView) findViewById(R.id.SurfSessionInfoText);
        surfSessionInfoText.setText(sit);


        Log.v("Count Items", sit);

    }

    public void buttonClicked(View v){

        spinKiteSelect  = (Spinner)  findViewById(R.id.spinKiteSelect);
        spinBarSelect   = (Spinner)  findViewById(R.id.spinBarSelect);
        windAVG         = (EditText) findViewById(R.id.inputWindAVG);
        windMAX         = (EditText) findViewById(R.id.inputWindMAX);
        windMIN         = (EditText) findViewById(R.id.inputWindMIN);
        duration        = (EditText) findViewById(R.id.inputDuration);
        mDate           = (EditText) findViewById(R.id.inputSurfSessionDate);
        mSpot           = (EditText) findViewById(R.id.inputSpot);
        spinWindDirectionSelect = (Spinner) findViewById(R.id.spinWindDirectionSelect);
        spinWindCharacterSelect = (Spinner) findViewById(R.id.spinWindCharacterSelect);
        spinSurfSessionCharacterSelect = (Spinner) findViewById(R.id.spinFunSelect);
        // hard coded without an ID
        spinForecastSelect = (Spinner) findViewById(R.id.spinForecastSelect);


        // nur fuer ausgabe auf der Console als Test
        String entry = "Kite:" + kiteIds.get(spinKiteSelect.getSelectedItemPosition()) + " " + spinKiteSelect.getSelectedItem().toString() + "\n" +
                "Bar:" + barIds.get(spinBarSelect.getSelectedItemPosition()) + " " + spinBarSelect.getSelectedItem().toString() + "\n" +
                "wind:" + windIds.get(spinWindDirectionSelect.getSelectedItemPosition()) + " " + spinWindDirectionSelect.getSelectedItem().toString() + "\n" +
                "WindCh:" + windChIds.get(spinWindCharacterSelect.getSelectedItemPosition()) + " " + spinWindCharacterSelect.getSelectedItem().toString() + "\n" +
                "windMIN:" + windMIN.getText().toString() + "\n" +
                "windAVG:" + windAVG.getText().toString() + "\n" +
                "windMAX:" + windMAX.getText().toString() + "\n" +
                "Datum:" + mDate.getText().toString() + "\n" +
                "Dauer:" + duration.getText().toString() + "\n" +
                "Spot:" + mSpot.getText().toString() + "\n" +
                "SurfSessionChar:" + sscIds.get(spinSurfSessionCharacterSelect.getSelectedItemPosition()) + " " + spinSurfSessionCharacterSelect.getSelectedItem().toString() + "\n" +
                "Forecast: no Id " + spinForecastSelect.getSelectedItem().toString() + "\n" +
                "";



        if(!"".equals(duration.getText().toString())  && !"".equals(windAVG.getText().toString()) && !"".equals(windMIN.getText().toString()) && !"".equals(windAVG.getText().toString())) {

            SurfSession newSurfSession = new SurfSession(
                    Float.valueOf(duration.getText().toString()),
                    fcIds.get(spinForecastSelect.getSelectedItemPosition()), // Forecast zum Test auf 1 gesetzt
                    sscIds.get(spinSurfSessionCharacterSelect.getSelectedItemPosition()),
                    1, // User ID zum Test auf 1 gesetzt
                    windChIds.get(spinWindCharacterSelect.getSelectedItemPosition()),
                    windIds.get(spinWindDirectionSelect.getSelectedItemPosition()),
                    Float.valueOf(windAVG.getText().toString()),
                    Float.valueOf(windMAX.getText().toString()),
                    Float.valueOf(windMIN.getText().toString()),

                    new Date(),
                    mSpot.getText().toString());

            DBHelperSurfSession dbSurfSession = new DBHelperSurfSession(this);
            long sid = dbSurfSession.createSurfSession(newSurfSession);

            SurfSession mySession = dbSurfSession.getSurfSession(sid);

            createSurfSessionEntry("Surfsession mit id:" + mySession.get_sID() + " wurde gespeichert");
            clearFormSurfSession();

        } else {
            createSurfSessionEntry("Mindestens ein Formularfeld wurde nicht ausgefuellt.");
        }
        Log.e("myEntrySession", entry );
    }
    public void clearFormSurfSession(){

        spinKiteSelect.setSelection(0);
        spinBarSelect.setSelection(0);
        windAVG.setText("");
        windMAX.setText("");
        windMIN.setText("");
        duration.setText("");
        mDate.setText("");
        mSpot.setText("");
        spinWindDirectionSelect.setSelection(0);
        spinWindCharacterSelect.setSelection(0);
        spinSurfSessionCharacterSelect.setSelection(0);
    }

    public void createSurfSessionEntry(String sit)
    {
        surfSessionInfoText = (TextView) findViewById(R.id.SurfSessionInfoText);
        surfSessionInfoText.setText(sit);
    }

    public void dbPreparation(Context context){

        // dbHandler ;
        dbHandler.getReadableDatabase();

        dbBar     = new DBHelperMBar(this);
        dbBarList = dbBar.getAllBarNames();

        dbKite     = new DBHelperMKite(this);
        dbKiteList = dbKite.getAllKiteNames();

        dbWind     = new DBHelperWindDirection(this);
        dbWindList = dbWind.getAllWindDirections();

        dbWindCh     = new DBHelperWindCharacter(this);
        dbWindChList = dbWindCh.getAllWindChars();

        dbSurfSessionCharacter     = new DBHelperSurfSessionCharacter(this);
        dbSurfSessionCharacterList =  dbSurfSessionCharacter.getAllSurfSessionCharacters();

        dbForecast     = new DBHelperForecast(this);
        dbForecastList = dbForecast.getAllForcasts();

    }

}
