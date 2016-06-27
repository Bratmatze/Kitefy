package com.kitetify.me.kitefy;

import android.util.Log;
import android.widget.TextView;

import com.kitetify.me.kitefy.DatabaseModels.Forecast;
import com.kitetify.me.kitefy.DatabaseModels.MBar;
import com.kitetify.me.kitefy.DatabaseModels.MKite;
import com.kitetify.me.kitefy.DatabaseModels.Material;
import com.kitetify.me.kitefy.DatabaseModels.MaterialType;
import com.kitetify.me.kitefy.DatabaseModels.SurfSessionCharacter;
import com.kitetify.me.kitefy.DatabaseModels.WindCharacter;
import com.kitetify.me.kitefy.DatabaseModels.WindDirection;

import java.util.ArrayList;
import java.util.List;


public class FormHelper {

    List<String> ConditionSpinner = new ArrayList<String>() {{ add("neu");add("sogut wie neu"); add("gebraucht"); }};
    List<String> ConditionSpinnerFormHelper = new ArrayList<String>() {{ add("neu");add("sogut wie neu"); add("gebraucht"); }};


    public FormHelper() {
    }

    // works only for the size of Integer. Numbers which are bigger are not supported
    public static String formatFloatingNumber(double d)
    {
        if(d == (long) d)
            return String.format("%d",(long)d);
        else
            return String.format("%s",d);
    }

    public void setInfoText(String sit, TextView v)
    {
        v.setText(sit);
    }

    public List<String> getConditionSpinner() {
        return ConditionSpinner;
    }

    public List<String> setSpinnerItems(String spinId, List<?> ol) {

        List<String> list = new ArrayList<>();

        if ("Kite".equals(spinId)) {

            for (MKite oneKite: (List<MKite>) ol ) {
                list.add( oneKite.get_name() + " " +
                        oneKite.get_type() + " " +
                        oneKite.get_year() + " " +
                        formatFloatingNumber(oneKite.get_size()) + "qm");
            }
            return list;

        } else if ("Bar".equals(spinId)) {

            for (MBar oneBar:(List<MBar>) ol) {
                list.add(oneBar.get_name() + " " +oneBar.get_type()+ " "+oneBar.get_year());
            }
            return list;

        } else if ("Material".equals(spinId)) {

            for (Material oneM:(List<Material>) ol) {
                list.add(oneM.get_name() + " " +oneM.get_type());
            }
            return list;

        } else if ("MaterialType".equals(spinId)) {

            for (MaterialType oneM:(List<MaterialType>) ol) {
                list.add(oneM.get_name());
            }
            return list;

        } else if ("WindDirection".equals(spinId)) {

            for (WindDirection oneWind:(List<WindDirection>) ol) {
                list.add(oneWind.get_direction());
            }
            return list;

        } else if ("WindCharacter".equals(spinId)) {

            for (WindCharacter oneWindc: (List<WindCharacter>) ol) {
                list.add(oneWindc.get_name());
            }
            return list;

        } else if ("Forecast".equals(spinId)) {

            for (Forecast oneFC: (List<Forecast>) ol) {
                list.add(oneFC.get_name());
            }
            return list;

        } else if ("SurfSessionCharacter".equals(spinId)) {

            for (SurfSessionCharacter oneSSC: (List<SurfSessionCharacter>) ol) {
                list.add(oneSSC.get_name());
            }
            return list;


        } else if ("Condition".equals(spinId)) {

            for (String oneCond: (List<String>) ol) {
                list.add(oneCond);
            }
            return list;

        } else {
            list.add("none");
            return list;
        }
    }

    public List<Integer> setSpinnerIds(String spinId, List<?> ol) {

        List<Integer> idList = new ArrayList<>();

        if ("Kite".equals(spinId)) {

            for (MKite oneKite: (List<MKite>) ol ) {
                idList.add(oneKite.get_kID());
            }
            return idList;

        } else if ("Bar".equals(spinId)) {

            for (MBar oneBar:(List<MBar>) ol) {
                idList.add(oneBar.get_bID());
            }
            return idList;

        } else if ("Material".equals(spinId)) {

            for (Material oneBar:(List<Material>) ol) {
                idList.add(oneBar.get_mID());
            }
            return idList;

        } else if ("MaterialType".equals(spinId)) {

            for (MaterialType oneBar:(List<MaterialType>) ol) {
                idList.add(oneBar.get_mTypeID());
            }
            return idList;

        } else if ("WindDirection".equals(spinId)) {

            for (WindDirection oneWind:(List<WindDirection>) ol) {
                idList.add(oneWind.get_windID());
            }
            return idList;
        } else if ("WindCharacter".equals(spinId)) {

            for (WindCharacter oneWindc: (List<WindCharacter>) ol) {
                idList.add(oneWindc.get_wcID());
            }
            return idList;

        } else if ("Forecast".equals(spinId)) {

            for (Forecast oneFC: (List<Forecast>) ol) {
                idList.add(oneFC.get_fID());
            }
            return idList;

        } else if ("SurfSessionCharacter".equals(spinId)) {

            for (SurfSessionCharacter oneSSC: (List<SurfSessionCharacter>) ol) {
                idList.add(oneSSC.get_funID());
            }
            return idList;

        } else if ("Condition".equals(spinId)) {

            int i = 0;
            for (String oneCond: (List<String>) ol) {
                idList.add(i);
                i++;
            }
            Log.e("ConditionList", i + " Zahl ");
            return idList;

        } else {
            idList.add(-1);
            return idList;
        }
    }

}
