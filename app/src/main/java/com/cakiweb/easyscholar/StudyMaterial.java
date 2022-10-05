package com.cakiweb.easyscholar;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class StudyMaterial extends AppCompatActivity {
    SearchView searchView;
    TextView hintText;
    StudyMaterialAdapter adapter;
    List<StudyMaterialData> exampleList;
    RecyclerView recyclerView;
    LinearLayout ser,noData;
    String url = "https://yppschool.com/erp/index.php/Api_request/api_list";
    ProgressDialog progressDialog;
    HashMap<String, String> hashMap = new HashMap<>();
    HttpParse httpParse = new HttpParse();
    JsonHttpParse jsonHttpParse = new JsonHttpParse();
    String finalResult,class_id,session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.study_material);
        hintText=findViewById(R.id.texthint);
        ser=findViewById(R.id.ser);
        recyclerView=findViewById(R.id.materialRecycler);
        recyclerView.setHasFixedSize(true);
        noData = findViewById(R.id.noDataMaterial);
        noData.findViewById(R.id.noDataMaterial).setVisibility(View.GONE);
//        adapter=new StudyMaterialAdapter(exampleList,StudyMaterial.this);
//        recyclerView.setAdapter(adapter);
//
//
//        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(StudyMaterial.this,LinearLayoutManager.VERTICAL,false);
//        recyclerView.setLayoutManager(linearLayoutManager);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        class_id = sh.getString("class_id", "");
        session = sh.getString("session_id", "");
        fillExampleList();
        //UserLoginFunction("studymaterial", "studymaterial");
        setUpRecyclerView();
    }
    private void setUpRecyclerView() {
        recyclerView=findViewById(R.id.materialRecycler);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        adapter = new StudyMaterialAdapter(exampleList,StudyMaterial.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }
    private void fillExampleList() {
        exampleList = new ArrayList<>();
        exampleList.add(new StudyMaterialData("Chemestry", "elements","periodic table","09 Mar 2021"));
        exampleList.add(new StudyMaterialData( "geography", "gravity","earth quick","09 Mar 2021"));
        exampleList.add(new StudyMaterialData("math", "equations","polynomial","09 Mar 2021"));
        exampleList.add(new StudyMaterialData( "English", "My Mother","first par graph","09 Mar 2021"));
        exampleList.add(new StudyMaterialData("Physics", "Electricity","resistivity","09 Mar 2021"));
        exampleList.add(new StudyMaterialData("history", "age of kings","king","09 Mar 2021"));
        exampleList.add(new StudyMaterialData("Political Science", "rules for elections","voting","09 Mar 2021"));
        exampleList.add(new StudyMaterialData("biology", "Human Anatomy","heart","09 Mar 2021"));
        exampleList.add(new StudyMaterialData("hindi", "grammar","article","09 Mar 2021"));
    }

    public void onBack(View view) {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public void onSearch(View view) {
        searchView = findViewById(R.id.searchMaterial);
       // SearchView searchView = (SearchView) ser.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

        searchView.setOnCloseListener(() -> {

            hintText.setVisibility(View.VISIBLE);
            return false;
        });


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String newText) {
                hintText.setVisibility(View.GONE);
                adapter.getFilter().filter(newText);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });




    }

    public void UserLoginFunction(final String methode, final String student_id) {

        class UserLoginClass extends AsyncTask<String, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                progressDialog = ProgressDialog.show(StudyMaterial.this, "Loading Data", null, true, true);
            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {

                super.onPostExecute(httpResponseMsg);
                progressDialog.dismiss();
                Toast.makeText(StudyMaterial.this, httpResponseMsg, Toast.LENGTH_SHORT).show();

                if (httpResponseMsg.contains("200")) {

                    noData.findViewById(R.id.noDataMaterial).setVisibility(View.GONE);
                    try {
                        JSONObject jsonObject = new JSONObject(httpResponseMsg);
                        JSONArray result = jsonObject.getJSONArray("resultSet");
                        for (int i = 0; i < result.length(); i++) {
                            JSONObject ob = result.getJSONObject(i);
                            //exampleList.add(new StudyMaterialData("Chemestry", "elements","periodic table","09 Mar 2021"));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
//                    recyclerView.setAdapter(feeAdapter);
//                    feeAdapter.notifyDataSetChanged();
                } else {
                    noData.findViewById(R.id.noDataMaterial).setVisibility(View.VISIBLE);
                   //exampleList.add(new StudyMaterialData("Chemestry", "elements","periodic table","09 Mar 2021"));
                }

            }

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            protected String doInBackground(String... params) {
                String jsonInputString = "{\"method\":\"studymaterial\",\"class_id\":\"8\",\"session_id\":\"\",\"subject_id\":\"\",\"chapter_id\":\"\"}";
                finalResult = jsonHttpParse.postRequest(jsonInputString, url);
                return finalResult;
            }
        }
        UserLoginClass userLoginClass = new UserLoginClass();
        userLoginClass.execute();
    }
}