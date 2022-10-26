package com.cakiweb.easyscholar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {
    RelativeLayout login;
    TextView username,password;
    String strPassword, strEmail;
    String finalResult ;
    String url1 = "https://yppschool.com/erp/index.php/Api_request/api_list?";
    String url2 = "https://yppschool.com/erp/";
    //String HttpURL = "https://eazyscholar.com/dis/index.php/Api_request/api_list";
    //String url1 = "http://erpdis.dooninternational.in/erp/index.php/Api_request/api_list?";
    Boolean CheckEditText ;
    ProgressDialog progressDialog;
    HashMap<String,String> hashMap = new HashMap<>();
    HttpParse httpParse = new HttpParse();
    JsonHttpParse jsonHttpParse = new JsonHttpParse();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login=findViewById(R.id.login);
        username=findViewById(R.id.userName);
        password=findViewById(R.id.password);
    }

    public void onLogin(View view) {

        CheckEditTextIsEmptyOrNot();

        if (CheckEditText) {
            //UserLoginFunction("login",strEmail, strPassword);
            log();
        } else {
            Toast.makeText(LoginActivity.this, "Please fill all form fields.", Toast.LENGTH_LONG).show();
        }

    }
    public void CheckEditTextIsEmptyOrNot(){

        strEmail = username.getText().toString().trim();
        strPassword = password.getText().toString().trim();

        if(TextUtils.isEmpty(strEmail) || TextUtils.isEmpty(strPassword))
        {
            CheckEditText = false;
        }
        else {
            CheckEditText = true ;
        }
    }
    public void log(){
        ProgressDialog loading = ProgressDialog.show(this,"Please wait...","Fetching...",false,true);
        //String url = HttpURL+"?method=exam&userId="+id;
        String url = url1+"method=login&student_id="+strEmail+"&student_password="+strPassword;

        Toast.makeText(this, url, Toast.LENGTH_SHORT).show();

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String httpResponseMsg) {

                Toast.makeText(LoginActivity.this, httpResponseMsg, Toast.LENGTH_SHORT).show();

                loading.dismiss();

                //showJSON(response);
                //Toast.makeText(LoginActivity.this,httpResponseMsg,Toast.LENGTH_LONG).show();

                try{

                    if(httpResponseMsg.contains("200")){
                        try {
                            JSONObject jsonObject = new JSONObject(httpResponseMsg);
                            JSONArray array = jsonObject.getJSONArray("resultSet");
                            JSONObject ob = array.getJSONObject(0);
                            String id=ob.getString("student_id");
                            String name=ob.getString("student_name");
                            String class_id=ob.getString("class_id");
                            String sesssion_id=ob.getString("session_id");
                            String sesssion=ob.getString("session_name");


                            SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
                            SharedPreferences.Editor myEdit = sharedPreferences.edit();
                            myEdit.putString("student_id", id);
                            myEdit.putString("student_name",name);
                            myEdit.putString("class_id", class_id);
                            myEdit.putString("session_id", sesssion_id);
                            myEdit.putString("api", url1);
                            // myEdit.putString("api1","http://erpdis.dooninternational.in/erp/");
                            myEdit.putString("api1","https://yppschool.com/erp/");
                            myEdit.putString("session", sesssion);

                            myEdit.apply();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        finish();
                        startActivity(new Intent(LoginActivity.this,HomeActivity.class));
                    }
                    else{
                        Toast.makeText(LoginActivity.this, httpResponseMsg, Toast.LENGTH_SHORT).show();
                    }
                }catch(Exception e)
                {
                    loading.dismiss();
                    Toast.makeText(LoginActivity.this,e+"\n\n"+httpResponseMsg,Toast.LENGTH_LONG).show();

                }

            }
        },
                error -> Toast.makeText(LoginActivity.this,error.toString(),Toast.LENGTH_LONG).show());

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
//    public void UserLoginFunction(final String method,final String student_id, final String student_password){
//
//        class UserLoginClass extends AsyncTask<String,Void,String> {
//
//            @Override
//            protected void onPreExecute() {
//                super.onPreExecute();
//
//                progressDialog = ProgressDialog.show(LoginActivity.this,"Loading Data",null,true,true);
//            }
//
//            @Override
//            protected void onPostExecute(String httpResponseMsg) {
//
//                super.onPostExecute(httpResponseMsg);
//                progressDialog.dismiss();
//                Toast.makeText(LoginActivity.this, httpResponseMsg, Toast.LENGTH_SHORT).show();
//                if(httpResponseMsg.contains("200")){
//                    try {
//                        JSONObject jsonObject = new JSONObject(httpResponseMsg);
//                        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
//                        SharedPreferences.Editor myEdit = sharedPreferences.edit();
//                        myEdit.putString("json", jsonObject.toString());
//                        myEdit.apply();
//
//
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                    //finish();
//                    //startActivity(new Intent(LoginActivity.this,HomeActivity.class));
//                }
//                else{
//                    Toast.makeText(LoginActivity.this, "error", Toast.LENGTH_SHORT).show();
//                }
//
//            }
//
//            @Override
//            protected String doInBackground(String... params) {
//
//                hashMap.put("method",params[0]);
//                hashMap.put("student_id",params[1]);
//                hashMap.put("student_password",params[2]);
//                finalResult = httpParse.postRequest(hashMap, HttpURL);
//                return finalResult;
//            }
//        }
//        UserLoginClass userLoginClass = new UserLoginClass();
//        userLoginClass.execute(method,student_id,student_password);
//    }
}