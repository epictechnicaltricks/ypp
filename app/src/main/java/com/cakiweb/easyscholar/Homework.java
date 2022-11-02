package com.cakiweb.easyscholar;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;

public class Homework extends AppCompatActivity {
    LinearLayout noData;
    TextView home_id;
    RecyclerView recyclerView;
    HomeWorkAdapter homeWorkAdapter;
    ArrayList<HomeWorkData> homeWorkData=new ArrayList<HomeWorkData>();
    String id,stu_id,class_id,api;

    private TextView select_teach;
    private TextView select_date,filter;

    boolean filter_teacher;
    boolean filter_date;
TextView show_all;

    private ArrayList<HashMap<String, Object>> teacher_map_list = new ArrayList<>();

    private RequestNetwork in;
    private RequestNetwork.RequestListener _in_request_listener;



    private ArrayList<HashMap<String, Object>> filter_map = new ArrayList<>();

    ImageView reset_teach_img,reset_date_img;
    LinearLayout teach_layout_, date_layout_;

    HorizontalScrollView hs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homework);


        in = new RequestNetwork(this);


        hs = findViewById(R.id.hscoll);


         reset_date_img = findViewById(R.id.reset_date_img_);
         reset_teach_img = findViewById(R.id.reset_teach_img_);

         teach_layout_ = findViewById(R.id.teach_layout_);
         date_layout_ = findViewById(R.id.date_layout_);



        home_id = findViewById(R.id.home_id);
        noData = findViewById(R.id.noDataHomework);
        noData.findViewById(R.id.noDataHomework).setVisibility(View.GONE);
        recyclerView=findViewById(R.id.homeworkRecycler);
        recyclerView.setHasFixedSize(true);

        homeWorkAdapter=new HomeWorkAdapter(homeWorkData,Homework.this);
        recyclerView.setAdapter(homeWorkAdapter);


        select_teach = findViewById(R.id.select_teach);
        select_date =  findViewById(R.id.select_date);

        filter = findViewById(R.id.filter);
        filter.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)40, 0xFFED1975));

        show_all = findViewById(R.id.show_all);
        show_all.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)40, 0xFF42A5F5));

        reset_date_img.setOnClickListener(view -> {

            disable_date_layout();

        });

        reset_teach_img.setOnClickListener(view ->{

            disable_teach_layout();

    });


/*
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                hs.scrollTo(0,500);
                //write your code here to be executed after 1 second
            }
        }, 1000);*/

        // auto scroll to last filter section

        filter.setOnClickListener(view -> filter_logic() );


        show_all.setOnClickListener(view -> {

            show_all.setVisibility(View.GONE);
            _request_api_hometask_classtask();

                 });

        select_date.setOnClickListener(view -> {

            _DateDialog(select_date);


            // this will show the date dialog

        });


        select_teach.setOnClickListener(view -> _show_teacher_Dialog());

        _in_request_listener = new RequestNetwork.RequestListener() {
            @Override
            public void onResponse(String _param1, String _param2, HashMap<String, Object> _param3) {
                final String _tag = _param1;
                final String _response = _param2;
                final HashMap<String, Object> _responseHeaders = _param3;
                teacher_map_list.clear();


                try {
                    if (_response.contains("200")) {

                        HashMap<String,Object> map;
                        map = new Gson().fromJson(_response, new TypeToken<HashMap<String, Object>>(){}.getType());
                        String list = (new Gson()).toJson(map.get("resultSet"), new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());
                        teacher_map_list = new Gson().fromJson(list, new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());
                        //Collections.sort();
                        if (teacher_map_list.size() < 1) {

                            Toast.makeText(Homework.this, "No data found.", Toast.LENGTH_SHORT).show();

                            }

                    }
                    else {

                        finish();
                        Toast.makeText(Homework.this, "SERVER MSG :\n\n" +_response, Toast.LENGTH_LONG).show();

                    }
                } catch(Exception e) {

                    Toast.makeText(Homework.this, e+"\n\nerror LINE 152 on homework.java", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onErrorResponse(String _param1, String _param2) {
                final String _tag = _param1;
                final String _message = _param2;

                finish();
                Toast.makeText(Homework.this, "No internet", Toast.LENGTH_LONG).show();

            }
        };


        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(Homework.this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);

//        for (int i=0; i<10; i++ ) {
//            HomeWorkData homeWorkData1=new HomeWorkData("20 Mar 2021","English","first","20-3-2021","first","15-3-2021","yes","Download the attachment","Home Work Submitted on Time");
//            homeWorkData.add(homeWorkData1);
//        }


        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        stu_id = sh.getString("student_id", "");
        class_id= sh.getString("class_id", "");
        api=sh.getString("api","");
        Intent intent = getIntent();
        id = intent.getStringExtra("val");

        ////////////////////////////////////////////
        _request_api_send_server("allteachers");


        // this api call for getting the list of teachers name from api
        ////////////////////////////////////


        if(id.equals("homework")){
            home_id.setText("Home Task");
        }else {
            home_id.setText("Class Task");
        }




        _request_api_hometask_classtask();



    }




    public void onBack(View view) {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void filter_logic() {

        if (!filter_teacher && !filter_date) {
            AlertDialog.Builder dia_ = new AlertDialog.Builder(this,AlertDialog.THEME_DEVICE_DEFAULT_DARK);
            dia_.setMessage("Please select at least one option to use Filter feature !");
            dia_.setPositiveButton("Close", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface _dialog, int _which) {

                }
            });
            dia_.create().show();
        }
        else {
         // THIS IS LOGIC AREA

            _api_filter();
            show_all.setVisibility(View.VISIBLE);

        }


    }











    private void disable_teach_layout() {


        disable_layout_color(teach_layout_);
        reset_teach_img.setVisibility(View.GONE);
        filter_teacher=false;
        select_teach.setText("Select Teacher");
        //filter.performClick();
    }
    private void disable_date_layout() {

        disable_layout_color(date_layout_);
        reset_date_img.setVisibility(View.GONE);
        filter_date=false;
        select_date.setText("Select Date");


    }
    private void enable_teach_layout() {
        enable_layout_color(teach_layout_);
        reset_teach_img.setVisibility(View.VISIBLE);
        filter_teacher=true;
    }

    private void enable_date_layout() {
       enable_layout_color(date_layout_);
        reset_date_img.setVisibility(View.VISIBLE);
        filter_date=true;
    }




    private void enable_layout_color(final LinearLayout _layout)
    {
        _layout.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)50, 0xFFFFF176));

    }

    private void disable_layout_color(final LinearLayout _layout)
    {
        _layout.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)50, 0xFFF4F2D9));

    }




    public void _DateDialog (final TextView _textview) {
        DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {

            @SuppressLint("SimpleDateFormat")
            final Calendar c = Calendar.getInstance();
            final String now_year = new SimpleDateFormat("yyyy").format(c.getTime());
            final int nowyear = Integer.parseInt(now_year);
           //c.set(nowyear, 0, 1);



            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                month++;

                // before =                output   1/9/2022
                // after the below logic = output 01/09/2022

                String tempMonth=""+month,tempDay=""+day;
                if(month<10) {
                    tempMonth = "/0" + month;

                    if(day<10) { tempDay = "0" + day; }

                    _textview.setText(tempDay + "" + tempMonth + "/" + year);


                }else {

                    if(day<10) { tempDay = "0" + day; }

                    _textview.setText(tempDay + "/" + tempMonth + "/" + year);

                }


               enable_date_layout();
                // this bool variable for check user is selected data or not

                //edited by shubhamjeet 27th oct 2022, 9:20pm


            }
        };
        showDatePicker(datePickerListener);
    }
    public void showDatePicker(DatePickerDialog.OnDateSetListener listener) {
        DatePickerDialog datePicker = new DatePickerDialog(this);


        // datePicker.getWindow().setBackgroundDrawable(new android.graphics.drawable.ColorDrawable(0));

        datePicker.setOnDateSetListener(listener);
        datePicker.show();
    }


    private void _request_api_hometask_classtask() {


    //when api call again it will disable filter of teacher and student
    disable_date_layout();
    disable_teach_layout();


    show_all.setVisibility(View.GONE);

            ProgressDialog loading = ProgressDialog.show(this,"Please wait...","Fetching...",false,true);
            //String url = HttpURL+"?method=homework&userId="+id;
            //https://yppschool.com/erp/index.php/Api_request/
            //api_list?method=homework&class_id=15&session_id='2022-2023'





            String url = api+"student_id="+stu_id+"&method="+id+"&class_id="+class_id+"&session_id='"+getCurrentYear()+"-"+(getCurrentYear()+1+"'");
            Toast.makeText(this, url, Toast.LENGTH_SHORT).show();

            StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    loading.dismiss();
                    showJSON(response);
                    //Toast.makeText(Homework.this,""+response,Toast.LENGTH_LONG).show();
                }
            },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(Homework.this,error.toString(),Toast.LENGTH_LONG).show();
                        }
                    });

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);

        }

    private void _api_filter() {
        ProgressDialog loading = ProgressDialog.show(this,"Wait a sec","Filtering...",false,false);
        //String url = HttpURL+"?method=homework&userId="+id;

        String url = api+"student_id="+stu_id+"&method="+id+"&class_id="+class_id+"&session_id='"+getCurrentYear()+"-"+(getCurrentYear()+1+"'");

        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);

        Toast.makeText(this, url, Toast.LENGTH_SHORT).show();

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    homeWorkData.clear();

                    if (response.contains("200")) {


                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray result = jsonObject.getJSONArray("resultSet");
                        for (int x = 0; x < result.length(); x++) {
                            JSONObject ob = result.getJSONObject(x);



                            if(filter_date&&filter_teacher) {

                                if(ob.getString("teacher").contentEquals(select_teach.getText()) && ob.getString("date").contentEquals(select_date.getText()))
                                {

                                    insert_filter_data(ob);

                                }

                            }
                            else if(filter_teacher) {

                                if(ob.getString("teacher").contentEquals(select_teach.getText()))
                                {
                                    insert_filter_data(ob);
                                }

                            }else if(filter_date) {

                                if(ob.getString("date").contentEquals(select_date.getText()))
                                {
                                    insert_filter_data(ob);
                                }

                            } else{

                                Toast.makeText(Homework.this, "No option selected.", Toast.LENGTH_LONG).show();
                            }

                        }


                    }
                    else {
                        noData.findViewById(R.id.noDataHomework).setVisibility(View.VISIBLE);
                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if(homeWorkData.size()<1)
                {
                    noData.findViewById(R.id.noDataHomework).setVisibility(View.VISIBLE);

                }else {

                    Collections.reverse(homeWorkData);
                    recyclerView.setAdapter(homeWorkAdapter);
                    homeWorkAdapter.notifyDataSetChanged();
                }


                loading.dismiss();


                //Toast.makeText(Homework.this,""+response,Toast.LENGTH_LONG).show();
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loading.dismiss();
                        Toast.makeText(Homework.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }


    private void insert_filter_data(JSONObject ob) throws JSONException {

        try{
            HomeWorkData homeWorkData1;

            if(id.equals("homework")){
                homeWorkData1 = new HomeWorkData(ob.getString("date")
                        , ob.getString("Subject")
                        , ob.getString("home_task")
                        , ob.getString("date")
                        , ob.getString("chapter")
                        , ob.getString("teacher")
                        , "na"
                        , "na"
                        , "Download the attachment"
                        , ob.getString("attend_status"));
            }
            else {
                homeWorkData1 = new HomeWorkData(ob.getString("date"), ob.getString("Subject"), ob.getString("class_task"), ob.getString("date"), ob.getString("chapter"), ob.getString("teacher"), "na", "na", "Download the attachment", ob.getString("attend_status"));
            }
            homeWorkData.add(homeWorkData1);

        }catch (Exception e)
        {
            Toast.makeText(this, e+"\n\nERROR 523 Homework.java", Toast.LENGTH_SHORT).show();
        }


    }


    public void _request_api_send_server (final String _method) {

        //////////////////////////////
            // TEACHER LIST API //
        /////////////////////////////

       HashMap<String,Object> api_map;
        api_map = new HashMap<>();
        api_map.put("method", _method);

        in.setParams(api_map, RequestNetworkController.REQUEST_PARAM);
        in.startRequestNetwork(RequestNetworkController.GET, api, "no tag", _in_request_listener);


    }



    public void _show_teacher_Dialog () {
        final AlertDialog p1_dialog = new AlertDialog.Builder(this).create();
        p1_dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        View inflate = getLayoutInflater().inflate(R.layout.cus, null);
        p1_dialog.setView(inflate);


        //

        LinearLayout linear1 = inflate.findViewById(R.id.linear1);
        EditText edittext1 = inflate.findViewById(R.id.edittext1);
        ImageView close = inflate.findViewById(R.id.close);
        _EnableType(edittext1);


        edittext1.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence _param1, int _param2, int _param3, int _param4) {
                final String _searchedText = _param1.toString();
                listview1.setAdapter(new Listview1Adapter(_Search("empname", _searchedText, teacher_map_list)));
                ((BaseAdapter)listview1.getAdapter()).notifyDataSetChanged();
            }

            @Override
            public void beforeTextChanged(CharSequence _param1, int _param2, int _param3, int _param4) {

            }

            @Override
            public void afterTextChanged(Editable _param1) {

            }
        });
        edittext1.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)50, (int)2, 0xFF000000, 0xFFECEFF1));
        linear1.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)50, 0xFFFFFFFF));
        edittext1.setVisibility(View.GONE);
        final ListView listview1 = new ListView(this);
        listview1.setDivider(null);
        listview1.setDividerHeight(0);
        listview1.setLayoutParams(new GridView.LayoutParams(GridLayout.LayoutParams.MATCH_PARENT, GridLayout.LayoutParams.WRAP_CONTENT));
        listview1.setAdapter(new Listview1Adapter(teacher_map_list));
        ((BaseAdapter)listview1.getAdapter()).notifyDataSetChanged();
        linear1.addView(listview1);
        listview1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> _param1, View _param2, int _param3, long _param4) {
                final int listview1P = _param3;

                Toast.makeText(Homework.this, "Teacher name selected", Toast.LENGTH_LONG).show();

                //
                //
                //
                ///    here set the teacher name for show on text view
                select_teach.setText(teacher_map_list.get((int)listview1P).get("empname").toString());
                enable_teach_layout();

                ///
                //
                //
                //
                p1_dialog.dismiss();
            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                p1_dialog.dismiss();
            }
        });

        p1_dialog.show();
        p1_dialog.setCancelable(true);
        //below block should always be at last of more block

    }

    //////////////////
    // THIS below listview1 is listview of teacher dialog
    /////////////////////////////////////////////////////////////

    private ListView listview1;
    public class Listview1Adapter extends
            BaseAdapter {
        ArrayList<HashMap<String, Object>> _data;
        public Listview1Adapter(ArrayList<HashMap<String, Object>> _arr) {
            _data = _arr;
        }
        @Override
        public int getCount() {
            return _data.size();
        }
        @Override
        public HashMap<String, Object> getItem(int _index) {
            return _data.get(_index);
        }
        @Override
        public long getItemId(int _index) {
            return _index;
        }
        @Override
        public View getView(final int _position, View _view, ViewGroup _viewGroup) {
            LayoutInflater _inflater = (LayoutInflater)getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View _v = _view;
            if (_v == null) {
                _v = _inflater.inflate(R.layout.lists, null);
            }

            final LinearLayout linear1 = _v.findViewById(R.id.linear1);
            final ImageView imageview1 = _v.findViewById(R.id.imageview1);
            final TextView textview1 = _v.findViewById(R.id.textview1);
            textview1.setText(_data.get(_position).get("empname").toString());

         /*   textview1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    //select_teach.setText(textview1.getText());

                   // p1_dialog.dismiss();

                }
            });*/


            return _v;
        }

    }





    public void _EnableType (final TextView _view) {
        _view.setShowSoftInputOnFocus(true);
        _view.setFocusable(true);
        _view.setFocusableInTouchMode(true);
    }


    public ArrayList<HashMap<String, Object>> _Search (final String _Key, final String _charseq, final ArrayList<HashMap<String, Object>> _listMap) {

        ////////
        ///
        //  this block for searching name of teachers using arraylist map data
        ////
        ////
        ///
        //
        String charseq = _charseq;
        String key = _Key;
        ArrayList<HashMap<String,Object>> map = new ArrayList<>();
        //String json_string = new Gson().toJson(_listMap);
        double list_number = 0;
        double list_number2 = 0;
        String json_string = new Gson().toJson(_listMap);

        map = new Gson().fromJson(json_string, new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());
        if (charseq.length() > 0) {
            list_number = map.size() - 1;
            list_number2 = map.size();
            for(int _repeat28 = 0; _repeat28 < (int)(list_number2); _repeat28++) {
                if (map.get((int)list_number).get(_Key).toString().toLowerCase().contains(charseq.toLowerCase())) {

                    listview1.setSmoothScrollbarEnabled(true);
                    listview1.smoothScrollToPosition((int)list_number);

                }
                else {
                    map.remove((int)(list_number));
                }
                list_number--;
            }
        }

        return map;

    }


    @SuppressLint("NotifyDataSetChanged")
    private void showJSON(String response) {

        // Toast.makeText(PaymentActivity.this,""+response,Toast.LENGTH_LONG).show();
        if (response.contains("200")) {


            homeWorkData.clear();
            noData.findViewById(R.id.noDataHomework).setVisibility(View.GONE);
            try {
                JSONObject jsonObject = new JSONObject(response);
                JSONArray result = jsonObject.getJSONArray("resultSet");
                for (int i = 0; i < result.length(); i++) {
                    JSONObject ob = result.getJSONObject(i);
                    HomeWorkData homeWorkData1;
                    if(id.equals("homework")){
                        homeWorkData1 = new HomeWorkData(ob.getString("date")
                                , ob.getString("Subject")
                                , ob.getString("home_task")
                                , ob.getString("date")
                                , ob.getString("chapter")
                                , ob.getString("teacher")
                                , "na"
                                , "na"
                                , "Download the attachment"
                                , ob.getString("attend_status"));
                    }
                    else {
                        homeWorkData1 = new HomeWorkData(ob.getString("date"), ob.getString("Subject"), ob.getString("class_task"), ob.getString("date"), ob.getString("chapter"), ob.getString("teacher"), "na", "na", "Download the attachment", ob.getString("attend_status"));
                    }
                    homeWorkData.add(homeWorkData1);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            Collections.reverse(homeWorkData);
            recyclerView.setAdapter(homeWorkAdapter);
            homeWorkAdapter.notifyDataSetChanged();
        }
        else {
            noData.findViewById(R.id.noDataHomework).setVisibility(View.VISIBLE);
        }
    }


    public int getCurrentYear(){

        Calendar c;
        c = Calendar.getInstance();

        @SuppressLint("SimpleDateFormat")
        String now_year = new SimpleDateFormat("yyyy").format(c.getTime());

        return Integer.parseInt(now_year);

    }

}









