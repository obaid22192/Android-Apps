package com.golfapp.vistex;

import java.util.ArrayList;

import android.R.integer;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebChromeClient.CustomViewCallback;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;
import android.provider.SyncStateContract.Constants;

public class generalcoursesearchActivity extends ActionBarActivity implements OnClickListener{

	
	
	private PlayerDAO DAO;
	private ArrayList<GolfCourse> listgolfcourses ;
	private EditText etsearch;
	private ImageButton search ;
	private ListView listview;
	private TextView Coursesfound;
	String  s; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_generalcoursesearch);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
		etsearch = (EditText) findViewById(R.id.etsearch);
		Coursesfound = (TextView) findViewById(R.id.tvcoursesfound);
		listview =(ListView) findViewById(R.id.listView);
		search = (ImageButton) findViewById(R.id.btnimagesearch);
		search.setOnClickListener(this);
		
	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v== search)
		{
			// listgolfcourses.clear();
			 DAO = new PlayerDAO(getApplicationContext());
			 String query ="SELECT  * FROM Golfcourse  WHERE city LIKE '%"+ etsearch.getText().toString() +"%' ;"; 
			 listgolfcourses= DAO.SearchedGolfcourses(query);
			 Coursesfound.setText(Integer.toString(listgolfcourses.size())+"   Courses Found" );
			 if(listgolfcourses.size() <1)
			 {
				 query = "SELECT  * FROM Golfcourse  WHERE Name LIKE '%"+ etsearch.getText().toString() +"%' ;"; 
				 listgolfcourses= DAO.SearchedGolfcourses(query);
				 Coursesfound.setText(Integer.toString(listgolfcourses.size())+"  Courses Found" );
			 }
		     
			 listview.setAdapter(new CourseAdapter(getApplicationContext(), listgolfcourses));
			 listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					// TODO Auto-generated method stub
					//Toast.makeText(getApplicationContext(), "You have pressed "+position+"ID Is:" +id, Toast.LENGTH_SHORT).show();
					Intent intent = new Intent(getApplicationContext(), selectroundorexplorecoureseActivity.class);
					intent.putExtra("MapSelected","no");
					intent.putExtra("SelectedCourse", listgolfcourses.get(position));
					startActivity(intent);
				}
			} );
			
		}
	}

}
 
