package com.golfapp.vistex;

import java.util.ArrayList;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.TextureView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;

public class CoursesearchoptionsActivity extends ActionBarActivity implements OnClickListener {
	
	private Button btnGeneralcoursesearch;
	private Button btnFav;
	private Button btnMapView;
	private Button btnnearBY;
	private GolfCourse golfcourse;
	private PlayerDAO DAO;
	private ArrayList<GolfCourse> listgolfcourses ;
	private ListView listview;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_coursesearchoptions);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
		btnFav  = (Button) findViewById(R.id.btnfavorite);
		btnFav.setOnClickListener(this);
		btnnearBY = (Button) findViewById(R.id.btnnearby);
		btnnearBY.setOnClickListener(this);
		btnMapView = (Button) findViewById(R.id.btnviewmap);
		btnMapView.setOnClickListener(this);
		btnGeneralcoursesearch = (Button) findViewById(R.id.gcoursesearch);
		btnGeneralcoursesearch.setOnClickListener(this);
		listview =(ListView) findViewById(R.id.listView);
		
		
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
		if(v==btnGeneralcoursesearch)
		{
		   //DAO = new PlayerDAO(getApplicationContext());
		   // listgolfcourses= DAO.getallGolfcourses();
		   //String  s= Integer.toString(listgolfcourses.size()) ;
		   //GolfCourse g = (GolfCourse) listgolfcourses.get(0);
		   //s = g.getName();
		   //CourseAdapter Cadapter = new CourseAdapter(this, listgolfcourses);
		   // listview.setAdapter(Cadapter);
			Intent Showallcourses = new Intent(getApplicationContext(), generalcoursesearchActivity.class);
			startActivity(Showallcourses);
		}
		if(v == btnFav)
		{
			Intent Showallcourses = new Intent(getApplicationContext(), FavoritecoursesActivity.class);
			startActivity(Showallcourses);
			
		}
		if(v == btnMapView)
		{
			Intent Showallcourses = new Intent(getApplicationContext(), showcourseonmapActivity.class);
			Showallcourses.putExtra("actionflag", "showall");
			startActivity(Showallcourses);
			
		}
		if(v == btnnearBY)
		{
			Intent Showallcourses = new Intent(getApplicationContext(),nearbycoursesActivity.class);
			startActivity(Showallcourses);
		}
		
		
	}

}

	


