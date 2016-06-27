package com.kitetify.me.kitefy;

import android.app.ActionBar;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.kitetify.me.kitefy.InAppModels.StatisticKiteUsageOverview;
import com.kitetify.me.kitefy.Persistence.DBHelperRequests;

import java.util.ArrayList;
import java.util.List;

public class StatisticViewActivity extends AppCompatActivity {


    private DBHelperRequests dbRequests;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        dbPreparation(this);
        List<?> kiteUsageOverview = dbRequests.getKiteUsageOverview();

        // Inflater XML-Layout and setView
        //LayoutInflater layoutInflater = getLayoutInflater();

        View view = LayoutInflater.from(this).inflate(R.layout.statistic_view, null);
        setContentView(view);


        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.stat_lin_layout);

        /*LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT);*/


        TextView tv = new TextView(this);
        tv.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        tv.setText("Statistiken");

        linearLayout.addView(tv);


        Log.e("dbOK?", kiteUsageOverview.size() + "");



        TableLayout tbl = tableBuilder(kiteUsageOverview);
        //setContentView(tbl);
       // relativeLayout.addView(tbl);

        //testTable();


    }

    public void dbPreparation(Context context) {

        dbRequests = new DBHelperRequests(this);

    }

    public TableLayout tableBuilder(List<?> kov){

        // 1) Create a tableLayout and its params
        TableLayout.LayoutParams tableLayoutParams = new TableLayout.LayoutParams();
        TableLayout table = (TableLayout) findViewById(R.id.gen_tbl);
        //table.setId(R.id.gen_tbl);

        // 2) create tableRow params
        TableRow.LayoutParams tableRowParams = new TableRow.LayoutParams();
        tableRowParams.setMargins(1, 1, 1, 1);
        tableRowParams.weight = 1;

        ArrayList<String> column = new ArrayList<String>() {};


        int x = 0;
        int l = kov.size();
        if (kov.size() > 0){
            x =  kov.size();



            for(int i = 0; i < x; i++){
                // 3) create tableRow
                TableRow tr1 = new TableRow(this);

                StatisticKiteUsageOverview sto = (StatisticKiteUsageOverview) kov.get(i);

                column.add(""+i);
                column.add(sto.get_kite_names());
                column.add(sto.get_number_of_usages() + "");
                column.add(sto.get_hours_of_usages() + "");
                column.add(sto.get_avg_hours_of_sessions() + "");
                column.add(sto.get_avg_windspeed() + "");

                int c = 0;
                for (String col :  column)  {
                    // column als TextView nebeneinander ablegen
                    TextView tv = new TextView(this);
                    /*android:layout_width="match_parent"
                    android:layout_height="match_parent"
                    android:layout_marginRight="2dp"
                    android:focusable="false"
                    android:focusableInTouchMode="false"
                    android:padding="3dp"
                    android:gravity="right"
                    android:background="#FFF"*/
                    tv.setLayoutParams(new ViewGroup.LayoutParams(
                            LayoutParams.WRAP_CONTENT,
                            LayoutParams.WRAP_CONTENT
                    ));
                    tv.setFocusable(false);
                    tv.setPadding(3, 3, 3, 3);
                    if(c == 0){
                        tv.setGravity(Gravity.LEFT);
                        Log.e("Left", c+"");
                    } else {
                        tv.setGravity(Gravity.RIGHT);
                        Log.e("Right", c+"");
                    }
                    tv.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    tv.setText(col);

                    tr1.addView(tv, tableRowParams);

                    c++;

                }

                // 6) add tableRow to tableLayout
                table.addView(tr1, tableLayoutParams);
                if(!column.isEmpty()) {
                    column.clear();
                }
            }



        } else {

            Log.e("StatiELSE", ""+x + " "+l);

        }
        Log.e("Stati", ""+x + " "+l);
        return table;





    }

    public void testTable(){

        TableLayout t1 = (TableLayout) StatisticViewActivity.this.findViewById(R.id.gen_tbl);


        for (int i = 0; i < 3; i++) {
            TableRow tr1 = new TableRow(this);

            TextView tv1 = new TextView(this);
            tv1.setText("Row" + i);

            tr1.addView(tv1);


        }
        t1.requestLayout();
//        setContentView(t1);



/*

        TableLayout table = new TableLayout(this);

        table.setStretchAllColumns(true);
        table.setShrinkAllColumns(true);

        TableRow rowTitle = new TableRow(this);
        rowTitle.setGravity(Gravity.CENTER_HORIZONTAL);

        TableRow rowDayLabels = new TableRow(this);
        TableRow rowHighs = new TableRow(this);
        TableRow rowLows = new TableRow(this);
      //  TableRow rowConditions = new TableRow(this);
       //// rowConditions.setGravity(Gravity.CENTER);

        TextView empty = new TextView(this);

        // title column/row
        TextView title = new TextView(this);
        title.setText("Java Weather Table");

        title.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18);
        title.setGravity(Gravity.CENTER);
        title.setTypeface(Typeface.SERIF, Typeface.BOLD);

        TableRow.LayoutParams params = new TableRow.LayoutParams();
        params.span = 6;

        rowTitle.addView(title, params);

        // labels column
        TextView highsLabel = new TextView(this);
        highsLabel.setText("Day High");
        highsLabel.setTypeface(Typeface.DEFAULT_BOLD);

        TextView lowsLabel = new TextView(this);
        lowsLabel.setText("Day Low");
        lowsLabel.setTypeface(Typeface.DEFAULT_BOLD);

      //  TextView conditionsLabel = new TextView(this);
      //  conditionsLabel.setText("Conditions");
      //  conditionsLabel.setTypeface(Typeface.DEFAULT_BOLD);

        rowDayLabels.addView(empty);
        rowHighs.addView(highsLabel);
        rowLows.addView(lowsLabel);
       // rowConditions.addView(conditionsLabel);

        // day 1 column
        TextView day1Label = new TextView(this);
        day1Label.setText("Feb 7");
        day1Label.setTypeface(Typeface.SERIF, Typeface.BOLD);

        TextView day1High = new TextView(this);
        day1High.setText("28°F");
        day1High.setGravity(Gravity.CENTER_HORIZONTAL);

        TextView day1Low = new TextView(this);
        day1Low.setText("15°F");
        day1Low.setGravity(Gravity.CENTER_HORIZONTAL);

        //ImageView day1Conditions = new ImageView(this);
        //day1Conditions.setImageResource(R.drawable.hot);

        rowDayLabels.addView(day1Label);
        rowHighs.addView(day1High);
        rowLows.addView(day1Low);
//        rowConditions.addView(day1Low);

        // day2 column
        TextView day2Label = new TextView(this);
        day2Label.setText("Feb 8");
        day2Label.setTypeface(Typeface.SERIF, Typeface.BOLD);

        TextView day2High = new TextView(this);
        day2High.setText("26°F");
        day2High.setGravity(Gravity.CENTER_HORIZONTAL);
        TextView day2Low = new TextView(this);
        day2Low.setText("14°F");
        day2Low.setGravity(Gravity.CENTER_HORIZONTAL);

        //ImageView day2Conditions = new ImageView(this);
        //day2Conditions.setImageResource(R.drawable.pt_cloud);

        rowDayLabels.addView(day2Label);
        rowHighs.addView(day2High);
        rowLows.addView(day2Low);
       // rowConditions.addView(day2Low);

        // day3 column
        TextView day3Label = new TextView(this);
        day3Label.setText("Feb 9");
        day3Label.setTypeface(Typeface.SERIF, Typeface.BOLD);

        TextView day3High = new TextView(this);
        day3High.setText("23°F");
        day3High.setGravity(Gravity.CENTER_HORIZONTAL);

        TextView day3Low = new TextView(this);
        day3Low.setText("3°F");
        day3Low.setGravity(Gravity.CENTER_HORIZONTAL);

        //ImageView day3Conditions = new ImageView(this);
        //day3Conditions.setImageResource(R.drawable.snow);

        rowDayLabels.addView(day3Label);
        rowHighs.addView(day3High);
        rowLows.addView(day3Low);
       // rowConditions.addView(day3Low);

        // day4 column
        TextView day4Label = new TextView(this);
        day4Label.setText("Feb 10");
        day4Label.setTypeface(Typeface.SERIF, Typeface.BOLD);

        TextView day4High = new TextView(this);
        day4High.setText("17°F");
        day4High.setGravity(Gravity.CENTER_HORIZONTAL);

        TextView day4Low = new TextView(this);
        day4Low.setText("5°F");
        day4Low.setGravity(Gravity.CENTER_HORIZONTAL);

        //ImageView day4Conditions = new ImageView(this);
        //day4Conditions.setImageResource(R.drawable.lt_snow);

        rowDayLabels.addView(day4Label);
        rowHighs.addView(day4High);
        rowLows.addView(day4Low);
       // rowConditions.addView(day4Low);

        // day5 column
        TextView day5Label = new TextView(this);
        day5Label.setText("Feb 11");
        day5Label.setTypeface(Typeface.SERIF, Typeface.BOLD);

        TextView day5High = new TextView(this);
        day5High.setText("19°F");
        day5High.setGravity(Gravity.CENTER_HORIZONTAL);

        TextView day5Low = new TextView(this);
        day5Low.setText("6°F");
        day5Low.setGravity(Gravity.CENTER_HORIZONTAL);

        //ImageView day5Conditions = new ImageView(this);
        //day5Conditions.setImageResource(R.drawable.pt_sun);

        rowDayLabels.addView(day5Label);
        rowHighs.addView(day5High);
        rowLows.addView(day5Low);
      //  rowConditions.addView(day5Low);

        table.addView(rowTitle);
        table.addView(rowDayLabels);
        table.addView(rowHighs);
        table.addView(rowLows);
        //table.addView(rowConditions);

        setContentView(table);*/

    }

}

/*
public class MainActivity extends Activity {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String[] row = { "ROW1", "ROW2", "Row3", "Row4", "Row 5", "Row 6",
                "Row 7" };
        String[] column = { "COLUMN1", "COLUMN2", "COLUMN3", "COLUMN4",
                "COLUMN5", "COLUMN6" };
        int rl=row.length; int cl=column.length;

        Log.d("--", "R-Lenght--"+rl+"   "+"C-Lenght--"+cl);

        ScrollView sv = new ScrollView(this);
        TableLayout tableLayout = createTableLayout(row, column,rl, cl);
        HorizontalScrollView hsv = new HorizontalScrollView(this);

        hsv.addView(tableLayout);
        sv.addView(hsv);
        setContentView(sv);

    }

    public void makeCellEmpty(TableLayout tableLayout, int rowIndex, int columnIndex) {
        // get row from table with rowIndex
        TableRow tableRow = (TableRow) tableLayout.getChildAt(rowIndex);

        // get cell from row with columnIndex
        TextView textView = (TextView)tableRow.getChildAt(columnIndex);

        // make it black
        textView.setBackgroundColor(Color.BLACK);
    }
    public void setHeaderTitle(TableLayout tableLayout, int rowIndex, int columnIndex){

        // get row from table with rowIndex
        TableRow tableRow = (TableRow) tableLayout.getChildAt(rowIndex);

        // get cell from row with columnIndex
        TextView textView = (TextView)tableRow.getChildAt(columnIndex);

        textView.setText("Hello");
    }

    private TableLayout createTableLayout(String [] rv, String [] cv,int rowCount, int columnCount) {
        // 1) Create a tableLayout and its params
        TableLayout.LayoutParams tableLayoutParams = new TableLayout.LayoutParams();
        TableLayout tableLayout = new TableLayout(this);
        tableLayout.setBackgroundColor(Color.BLACK);

        // 2) create tableRow params
        TableRow.LayoutParams tableRowParams = new TableRow.LayoutParams();
        tableRowParams.setMargins(1, 1, 1, 1);
        tableRowParams.weight = 1;

        for (int i = 0; i < rowCount; i++) {
            // 3) create tableRow
            TableRow tableRow = new TableRow(this);
            tableRow.setBackgroundColor(Color.BLACK);

            for (int j= 0; j < columnCount; j++) {
                // 4) create textView
                TextView textView = new TextView(this);
                //  textView.setText(String.valueOf(j));
                textView.setBackgroundColor(Color.WHITE);
                textView.setGravity(Gravity.CENTER);

                String s1 = Integer.toString(i);
                String s2 = Integer.toString(j);
                String s3 = s1 + s2;
                int id = Integer.parseInt(s3);
                Log.d("TAG", "-___>"+id);
                if (i ==0 && j==0){
                    textView.setText("0==0");
                } else if(i==0){
                    Log.d("TAAG", "set Column Headers");
                    textView.setText(cv[j-1]);
                }else if( j==0){
                    Log.d("TAAG", "Set Row Headers");
                    textView.setText(rv[i-1]);
                }else {
                    textView.setText(""+id);
                    // check id=23
                    if(id==23){
                        textView.setText("ID=23");

                    }
                }

                // 5) add textView to tableRow
                tableRow.addView(textView, tableRowParams);
            }

            // 6) add tableRow to tableLayout
            tableLayout.addView(tableRow, tableLayoutParams);
        }

        return tableLayout;
    }
}*/