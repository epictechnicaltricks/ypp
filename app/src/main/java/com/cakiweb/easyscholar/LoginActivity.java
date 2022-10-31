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

    private HashMap<String, Object> api_map = new HashMap<>();


    private RequestNetwork in;
    private RequestNetwork.RequestListener _in_request_listener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login=findViewById(R.id.login);
        username=findViewById(R.id.userName);
        password=findViewById(R.id.password);

        in = new RequestNetwork(this);

        _in_request_listener = new RequestNetwork.RequestListener() {
            @Override
            public void onResponse(String _param1, String _param2, HashMap<String, Object> _param3) {
                final String _tag = _param1;
                final String httpResponseMsg = _param2;
                final HashMap<String, Object> _responseHeaders = _param3;

                ProgressDialog loading = ProgressDialog.show(LoginActivity.this,"Please wait...","Fetching...",false,true);


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
                            //  myEdit.putString("student_name",name);
                            // dont remove coomment top "student_name"
                            myEdit.putString("class_id", class_id);
                            myEdit.putString("session_id", sesssion_id);
                            myEdit.putString("api", url1);
                            // myEdit.putString("api1","http://erpdis.dooninternational.in/erp/");
                            myEdit.putString("api1","https://yppschool.com/erp/");
                            myEdit.putString("session", sesssion);

                            myEdit.apply();

                            getAllinfo_by_id(id);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                    else{
                        loading.dismiss();
                        Toast.makeText(LoginActivity.this, httpResponseMsg, Toast.LENGTH_LONG).show();
                    }
                }catch(Exception e)
                {
                    loading.dismiss();
                    Toast.makeText(LoginActivity.this,e+"\n\n"+httpResponseMsg,Toast.LENGTH_LONG).show();

                }


            }

            @Override
            public void onErrorResponse(String _param1, String _param2) {
                final String _tag = _param1;
                final String _message = _param2;
                showMessage( _message);

            }
        };



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

        strEmail = username.getText().toString();
        strPassword = password.getText().toString();

        if(TextUtils.isEmpty(strEmail) || TextUtils.isEmpty(strPassword))
        {
            CheckEditText = false;
        }
        else {
            CheckEditText = true ;
        }
    }

    public void log(){

        LOGIN_api_send_server("login",strEmail.trim(),strPassword.trim());


         //String url = HttpURL+"?method=exam&userId="+id;
         // String url = url1+"method=login&student_id="+strEmail+"&student_password="+strPassword;


    }






    public void LOGIN_api_send_server (final String _method,final String stu_id,final String stu_pass) {


        api_map = new HashMap<>();
        api_map.put("method", _method);
        api_map.put("student_id", stu_id);
        api_map.put("student_password", stu_pass);

        in.setParams(api_map, RequestNetworkController.REQUEST_PARAM);
        in.startRequestNetwork(RequestNetworkController.GET, url1, "no tag", _in_request_listener);


    }


    private void getAllinfo_by_id(final String stu_id){

        ProgressDialog loading = ProgressDialog.show(this,"Please wait...","Fetching...",false,true);

        String info_url = url1+"method=getStudentData&student_id="+stu_id;

       StringRequest request = new StringRequest(info_url, _response -> {

           try {

               if(_response.contains("200")){

                   try{

                       JSONObject jsonObject = new JSONObject(_response);
                       JSONArray array = jsonObject.getJSONArray("resultSet");

                       JSONObject ob = array.getJSONObject(0);

                       String strname=ob.getString("student_first_name");
                       String strlast=ob.getString("student_last_name");
                       String profile_IMG = ob.getString("student_profile_photo");


                       SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
                       SharedPreferences.Editor myEdit = sharedPreferences.edit();
                       myEdit.putString("student_id", stu_id);
                       myEdit.putString("student_name",strname +" "+strlast);
                       myEdit.putString("profile_IMG",profile_IMG);

                       myEdit.apply();


                       loading.dismiss();


                       finish();
                       startActivity(new Intent(LoginActivity.this,HomeActivity.class));


                   }catch (Exception e) {
                       Toast.makeText(LoginActivity.this, e+"\n\nServer msg\n"+_response, Toast.LENGTH_SHORT).show();
                   }



               }else {

                   Toast.makeText(LoginActivity.this, _response, Toast.LENGTH_SHORT).show();
               }


           }catch (Exception e) {
               Toast.makeText(LoginActivity.this, e+"\n\n"+_response, Toast.LENGTH_SHORT).show();
           }

       }, volleyError -> Toast.makeText(this, volleyError.toString(), Toast.LENGTH_SHORT).show());


       RequestQueue requestQueue = Volley.newRequestQueue(this);
       requestQueue.add(request);


    }




    @Deprecated
    public void showMessage(String _s) {
        Toast.makeText(getApplicationContext(), _s, Toast.LENGTH_SHORT).show();
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