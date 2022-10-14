package com.cakiweb.easyscholar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.GradientDrawable;
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
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import com.google.android.material.appbar.AppBarLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;


public class LibraryActivity extends  AppCompatActivity  {
	
	
	private Toolbar _toolbar;
	private AppBarLayout _app_bar;
	private CoordinatorLayout _coordinator;
	private String list = "";
	private HashMap<String, Object> map = new HashMap<>();
	
	private ArrayList<HashMap<String, Object>> results = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> listmap = new ArrayList<>();
	
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
		setContentView(R.layout.library_layout);
		initialize(_savedInstanceState);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		
			relative = (RelativeLayout) findViewById(R.id.relative);
		layout = (LinearLayout) findViewById(R.id.layout);
		prog = (LinearLayout) findViewById(R.id.prog);
		recyclerview1 = (RecyclerView) findViewById(R.id.recyclerview1);
		progressbar1 = (ProgressBar) findViewById(R.id.progressbar1);
		textview1 = (TextView) findViewById(R.id.textview1);
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
					prog.setVisibility(View.GONE);

					if (_response.contains("200")) {

						map = new Gson().fromJson(_response, new TypeToken<HashMap<String, Object>>(){}.getType());
						list = (new Gson()).toJson(map.get("resultSet"), new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());
						results = new Gson().fromJson(list, new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());
						Collections.reverse(results);

						_reftesh();
					} else {

						showMessage("Sorry No record Found!!!");
						finish();
					}
				} catch(Exception e) {

			showMessage("Error on response");
				}
			}
			
			@Override
			public void onErrorResponse(String _param1, String _param2) {
				final String _tag = _param1;
				final String _message = _param2;
				showMessage( "No internet !");
				textview1.setText("No internet !");
			}
		};
	}
	
	private void initializeLogic() {

		//api url
		showMessage("https://yppschool.com/erp/index.php/Api_request/api_list?method=libraryissues&student_id=2451");

		String method = "libraryissues";
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

	public void onBack(View view) {
		finish();
	}
	
	public class Recyclerview1Adapter extends Adapter<Recyclerview1Adapter.ViewHolder> {
		ArrayList<HashMap<String, Object>> _data;
		public Recyclerview1Adapter(ArrayList<HashMap<String, Object>> _arr) {
			_data = _arr;
		}
		
		@Override
		public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			LayoutInflater _inflater = (LayoutInflater)getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View _v = _inflater.inflate(R.layout.lib_custom, null);
			RecyclerView.LayoutParams _lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			_v.setLayoutParams(_lp);
			return new ViewHolder(_v);
		}
		
		@Override
		public void onBindViewHolder(ViewHolder _holder, final int _position) {
			View _view = _holder.itemView;
			
			final LinearLayout bg = (LinearLayout) _view.findViewById(R.id.bg);
			final LinearLayout linear2 = (LinearLayout) _view.findViewById(R.id.linear2);
			final LinearLayout ly1 = (LinearLayout) _view.findViewById(R.id.ly1);
			final LinearLayout ly2 = (LinearLayout) _view.findViewById(R.id.ly2);
			final LinearLayout ly3 = (LinearLayout) _view.findViewById(R.id.ly3);
			final LinearLayout ly4 = (LinearLayout) _view.findViewById(R.id.ly4);
			final LinearLayout ly5_status = (LinearLayout) _view.findViewById(R.id.ly5_status);
			final TextView textview3 = (TextView) _view.findViewById(R.id.textview3);
			final TextView book_name = (TextView) _view.findViewById(R.id.book_name);
			final TextView textview4 = (TextView) _view.findViewById(R.id.textview4);
			final TextView subtitle = (TextView) _view.findViewById(R.id.subtitle);
			final TextView textview5 = (TextView) _view.findViewById(R.id.textview5);
			final TextView issue_date = (TextView) _view.findViewById(R.id.issue_date);
			final TextView textview6 = (TextView) _view.findViewById(R.id.textview6);
			final TextView return_date = (TextView) _view.findViewById(R.id.return_date);
			final TextView return_status = (TextView) _view.findViewById(R.id.return_status);
			
			try {
				ly1.setElevation((float)15);
				ly3.setElevation((float)15);
				bg.setElevation((float)15);
				ly5_status.setElevation((float)15);
				book_name.setText(results.get((int)_position).get("title").toString());
				subtitle.setText(results.get((int)_position).get("subtitle").toString());
				issue_date.setText(results.get((int)_position).get("issue_date").toString());
				return_date.setText(results.get((int)_position).get("return_date").toString());
				if (results.get((int)_position).get("is_returned").toString().equals("1")) {
					return_status.setText("Successfully returned ");
					return_status.setTextColor(0xFF43A047);
					ly5_status.setBackground(new GradientDrawable(GradientDrawable.Orientation.BR_TL,new int[] {0xFFE8F5E9,0xFFF9FBE7}));
				}
				else {
					return_status.setText("Book not returned ");
					return_status.setTextColor(0xFFF44336);
					ly5_status.setBackground(new GradientDrawable(GradientDrawable.Orientation.BR_TL,new int[] {0xFFFFEBEE,0xFFFCE4EC}));
				}
			} catch(Exception e) {
				showMessage( "Error on Parameters ");
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
	
	@Deprecated
	public void showMessage(String _s) {
		Toast.makeText(getApplicationContext(), _s, Toast.LENGTH_SHORT).show();
	}
	

}
