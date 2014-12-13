package com.golfapp.vistex;

import java.io.IOException;
import java.util.ArrayList;

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
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.os.Build;


public class showallcoursesinlistActivity extends ActionBarActivity {

	private PlayerDAO DAO;
	private ListView listview;
	private TextView tvallcoursesfound;
	
	private ArrayList<GolfCourse> listgolfcourses ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_showallcoursesinlist);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
		listview =(ListView) findViewById(R.id.listViewallcourses);
		tvallcoursesfound = (TextView) findViewById(R.id.tvallcoursesfound);
		DAO = new PlayerDAO(getApplicationContext());
		listgolfcourses= DAO.getallGolfcourses();
		tvallcoursesfound.setText(Integer.toString(listgolfcourses.size())+"  Courses Found" );
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

}
