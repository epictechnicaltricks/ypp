package com.cakiweb.easyscholar;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MarkSheet extends AppCompatActivity {
    BarChart barChart,barChart2;
    private static final int MAX_X_VALUE = 5;
    private static final int MAX_Y_VALUE = 100;
    private static final int MIN_Y_VALUE = 6;
    private static final int GROUPS = 2;
    private static final String GROUP_1_LABEL = "Term 1";
    private static final String GROUP_2_LABEL = "Term 2";
    String HttpURL ="http://192.168.1.212/mark.php?month=1&year=2021";

    private static final float BAR_SPACE = 0.05f;
    private static final float BAR_WIDTH = 0.2f;
    final String[] sub = {"Math", "Eng", "His", "Geo", "Sci"};
    final String[] total = {"Mark"};
    List<Integer> v = new ArrayList<Integer>();
    List<Integer> f = new ArrayList<Integer>();

    int[] a={0};
    int[] b={0};
    int[] z={0,0,0,0,0};
    int[] d={0,0,0,0,0};
    ArrayList barEntries = new ArrayList<>();
//    ArrayList values1 = new ArrayList<>();
//    ArrayList values2 = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mark_sheet);
        barChart = findViewById(R.id.barChat);
        barChart2 = findViewById(R.id.barChat2);

        ProgressDialog loading = ProgressDialog.show(this,"Please wait...","Fetching...",false,false);
        String url = HttpURL;
        //String url = "https://cakitech.com/soumya/index.php";
        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loading.dismiss();
               // showJSON(response);
                //Toast.makeText(MarkSheet.this,""+response,Toast.LENGTH_LONG).show();


                if (response.contains("200")) {
                    try {

                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray result = jsonObject.getJSONArray("resultSet");
                        // JSONObject t1 = result.getJSONObject(0);
                        // JSONObject t2 = result.getJSONObject(1);


                        for (int i = 0; i < result.length(); i++) {
                            JSONObject ob = result.getJSONObject(i);
                            v.add( Integer.valueOf(ob.getString("total_mark")));
                            f.add( Integer.valueOf(ob.getString("math")));
                            f.add( Integer.valueOf(ob.getString("english")));
                            f.add( Integer.valueOf(ob.getString("history")));
                            f.add( Integer.valueOf(ob.getString("geography")));
                            f.add( Integer.valueOf(ob.getString("science")));
                        }

                        a[0]=v.get(0);
                        b[0]=v.get(1);
                        for(int i = 0 ; i < 5;i++){
                            z[i]=f.get(i);
                            d[i]=f.get(i+5);
                        }

                        BarData data = createChartData();
                        configureChartAppearance();
                        prepareChartData(data);
                        BarData data2 = createChartData2();
                        configureChartAppearance2();
                        prepareChartData2(data2);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                else {
                    Toast.makeText(MarkSheet.this,""+response,Toast.LENGTH_LONG).show();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MarkSheet.this,error.getMessage().toString(),Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);


    }


    public void onBack(View view) {
        finish();
    }
    private void showJSON(String response) {

       // Toast.makeText(MarkSheet.this,""+response,Toast.LENGTH_LONG).show();

    }

    private void configureChartAppearance() {
        barChart.setPinchZoom(false);
        barChart.setDrawBarShadow(false);
        barChart.setDrawGridBackground(false);

        barChart.getDescription().setEnabled(false);

        XAxis xAxis = barChart.getXAxis();
        xAxis.setGranularity(1f);
        xAxis.setCenterAxisLabels(true);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(sub));

        YAxis leftAxis = barChart.getAxisLeft();
        leftAxis.setDrawGridLines(false);
        leftAxis.setSpaceTop(35f);
        leftAxis.setAxisMinimum(0f);

        barChart.getAxisRight().setEnabled(false);
        barChart.getXAxis().setAxisMinimum(0);
        barChart.getXAxis().setAxisMaximum(MAX_X_VALUE);
    }

    private BarData createChartData() {
        Util u = new Util();

        ArrayList<BarEntry> values1 = new ArrayList<>();
        ArrayList<BarEntry> values2 = new ArrayList<>();


        for (int i = 0; i < d.length; i++) {
//            values1.add(new BarEntry(i, u.randomFloatBetween(MIN_Y_VALUE, MAX_Y_VALUE)));
//            values2.add(new BarEntry(i, u.randomFloatBetween(MIN_Y_VALUE, MAX_Y_VALUE)));
            values1.add(new BarEntry(i,d[i]));
            values2.add(new BarEntry(i,z[i]));
        }

        BarDataSet set1 = new BarDataSet(values1, GROUP_1_LABEL);
        BarDataSet set2 = new BarDataSet(values2, GROUP_2_LABEL);

        set1.setColor(ColorTemplate.MATERIAL_COLORS[0]);
        set2.setColor(ColorTemplate.MATERIAL_COLORS[3]);


        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);
        dataSets.add(set2);


        BarData data = new BarData(dataSets);

        return data;
    }

    private void prepareChartData(BarData data) {
        barChart.setData(data);

        barChart.getBarData().setBarWidth(BAR_WIDTH);

        float groupSpace = 1f - ((BAR_SPACE + BAR_WIDTH) * GROUPS);
        barChart.groupBars(0, groupSpace, BAR_SPACE);

        barChart.invalidate();
    }


    private void configureChartAppearance2() {
        barChart2.setPinchZoom(false);
        barChart2.setDrawBarShadow(false);
        barChart2.setDrawGridBackground(false);

        barChart2.getDescription().setEnabled(false);

        XAxis xAxis = barChart2.getXAxis();
        xAxis.setGranularity(1f);
        xAxis.setCenterAxisLabels(true);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(total));

        YAxis leftAxis = barChart2.getAxisLeft();
        leftAxis.setDrawGridLines(false);
        leftAxis.setSpaceTop(35f);
        leftAxis.setAxisMinimum(0f);

        barChart2.getAxisRight().setEnabled(false);

        barChart2.getXAxis().setAxisMinimum(0);
        barChart2.getXAxis().setAxisMaximum(1);
    }

    private BarData createChartData2() {
        Util u = new Util();

        ArrayList<BarEntry> values1 = new ArrayList<>();
        ArrayList<BarEntry> values2 = new ArrayList<>();


        for (int i = 0; i < 1; i++) {
            values1.add(new BarEntry(i,a[i]));
            values2.add(new BarEntry(i,b[i]));
        }

        BarDataSet set1 = new BarDataSet(values1, GROUP_1_LABEL);
        BarDataSet set2 = new BarDataSet(values2, GROUP_2_LABEL);

        set1.setColor(ColorTemplate.MATERIAL_COLORS[0]);
        set2.setColor(ColorTemplate.MATERIAL_COLORS[3]);


        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);
        dataSets.add(set2);


        BarData data = new BarData(dataSets);

        return data;
    }

    private void prepareChartData2(BarData data) {
        barChart2.setData(data);

        barChart2.getBarData().setBarWidth(BAR_WIDTH);

        float groupSpace = 1f - ((BAR_SPACE + BAR_WIDTH) * GROUPS);
        barChart2.groupBars(0, groupSpace, BAR_SPACE);

        barChart2.invalidate();
    }
}