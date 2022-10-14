package com.cakiweb.easyscholar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Objects;


public class AttendanceActivity extends  AppCompatActivity  { 
	
	

	private String list = "";
	private HashMap<String, Object> map = new HashMap<>();
	
	private ArrayList<HashMap<String, Object>> results = new ArrayList<>();
	private final ArrayList<HashMap<String, Object>> listmap = new ArrayList<>();
	
	private RelativeLayout relative;
	private LinearLayout layout;
	private LinearLayout prog;
	private RecyclerView recyclerview1;
	private ProgressBar progressbar1;
	private TextView textview1;
	
	private RequestNetwork api;
	private RequestNetwork.RequestListener _api_request_listener;

	String stu_id,class_id,session_id,api_URL;
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.attendance);
		initialize(_savedInstanceState);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		


		relative = findViewById(R.id.relative);
		layout = findViewById(R.id.layout);
		prog = findViewById(R.id.prog);
		recyclerview1 = findViewById(R.id.recyclerview1);
		progressbar1 = findViewById(R.id.progressbar1);
		textview1 = findViewById(R.id.textview1);
		api = new RequestNetwork(this);

		SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
		stu_id = sh.getString("student_id", "");
		class_id= sh.getString("class_id", "");
		api_URL=sh.getString("api","");
		session_id = sh.getString("session_id","");


		_api_request_listener = new RequestNetwork.RequestListener() {
			@Override
			public void onResponse(String _param1, String _param2, HashMap<String, Object> _param3) {
				final String _tag = _param1;
				final String _response = _param2;
				final HashMap<String, Object> _responseHeaders = _param3;
				try {
					if (_response.contains("200")) {
						map = new Gson().fromJson(_response, new TypeToken<HashMap<String, Object>>(){}.getType());
						list = (new Gson()).toJson(map.get("resultSet"), new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());
						results = new Gson().fromJson(list, new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());
						Collections.reverse(results);
						prog.setVisibility(View.GONE);
						_reftesh();
					}else {

						showMessage("Sorry No record Found!!!");
						finish();
					}
				} catch(Exception e) {
					showMessage("Error ");
				}
			}
			
			@Override
			public void onErrorResponse(String _param1, String _param2) {
				showMessage( "No internet !");
				textview1.setText("No internet !");
			}
		};
	}
	
	private void initializeLogic() {

		//https://yppschool.com/erp/index.php/Api_request/api_list?method=attendance&student_id=643

		showMessage("https://yppschool.com/erp/index.php/Api_request/api_list?method=attendance&student_id=643");

		String method = "attendance";
		API_request(method,stu_id,api_URL);


	}


	private  void API_request(String _method, String _student_id , String _api)
	{
		HashMap<String, Object> map2 = new HashMap<>();
		map2.put("method", _method);
		map2.put("student_id", _student_id);


		api.setParams(map2, RequestNetworkController.REQUEST_PARAM);

		api.startRequestNetwork(
				RequestNetworkController.GET,
				_api,
				"tag",
				_api_request_listener);

	}


	@Override
	protected void onActivityResult(int _requestCode, int _resultCode, Intent _data) {
		
		super.onActivityResult(_requestCode, _resultCode, _data);

	}
	
	public void _reftesh () {
		recyclerview1.setAdapter(new Recyclerview1Adapter(results));
		recyclerview1.setLayoutManager(new LinearLayoutManager(this));
	}
	
	
	public class Recyclerview1Adapter extends Adapter<Recyclerview1Adapter.ViewHolder> {
		ArrayList<HashMap<String, Object>> _data;
		public Recyclerview1Adapter(ArrayList<HashMap<String, Object>> _arr) {
			_data = _arr;
		}
		
		@Override
		public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			LayoutInflater _inflater = (LayoutInflater)getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View _v = _inflater.inflate(R.layout.attendance_custom_view, null);
			RecyclerView.LayoutParams _lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			_v.setLayoutParams(_lp);
			return new ViewHolder(_v);
		}
		
		@Override
		public void onBindViewHolder(ViewHolder _holder, final int _position) {
			View _view = _holder.itemView;
			
			final LinearLayout linear1 = _view.findViewById(R.id.linear1);
			final LinearLayout top = _view.findViewById(R.id.top);
			final LinearLayout bottom = _view.findViewById(R.id.bottom);
			final TextView textview1 = _view.findViewById(R.id.textview1);
			final TextView textview2 = _view.findViewById(R.id.textview2);
			final TextView date = _view.findViewById(R.id.date);
			final TextView status = _view.findViewById(R.id.status);
			
			if (_position == 0) {
				top.setVisibility(View.VISIBLE);
			}
			else {
				top.setVisibility(View.GONE);
			}
			top.setElevation((float)16);
			bottom.setElevation((float)20);
			date.setText(Objects.requireNonNull(results.get(_position).get("created_on")).toString());
			if (Objects.requireNonNull(results.get(_position).get("status")).toString().equals("1")) {

				bottom.setBackgroundColor(0xFFE8F5E9);

				status.setText("Present");
				status.setTextColor(0xFF43A047);
			}
			else {

				bottom.setBackgroundColor(0xFFFFEBEE);
				status.setText("Absent");
				status.setTextColor(0xFFF44336);
			}
		}
		
		@Override
		public int getItemCount() {
			return _data.size();
		}
		
		public class ViewHolder extends RecyclerView.ViewHolder{
			public ViewHolder(View v){
				super(v);
			}
		}
		
	}


	public void onBack(View view) {
		finish();
	}
	
	@Deprecated
	public void showMessage(String _s) {
		Toast.makeText(getApplicationContext(), _s, Toast.LENGTH_SHORT).show();
	}
	
	
}
