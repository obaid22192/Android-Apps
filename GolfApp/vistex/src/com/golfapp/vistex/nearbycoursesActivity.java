package com.golfapp.vistex;

import java.io.IOException;
import java.util.ArrayList;

import com.google.android.gms.internal.go;

import android.R.array;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.CorrectionInfo;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;


public class nearbycoursesActivity extends ActionBarActivity implements LocationListener {

	
	protected LocationManager locationManager;
    private Location currentLocation;
    private Location CourseLocation;
    ProgressDialog diloge = null;
    private PlayerDAO DAO;
    private ListView listview;
    private TextView tvallcoursesfound;
    private ArrayList<GolfCourse> listgolfcourses ;
    private ArrayList<GolfCourse> afterDistancelistofcourses;
    private ArrayList<DistanceCalculaor> ListDistanceCalculator;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nearbycoursesst);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
		
		currentLocation = new Location("");
		CourseLocation = new Location("");
		diloge = ProgressDialog.show(this,"Processing" , "Loading Courses");
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 20, (LocationListener) this);
		listview =(ListView) findViewById(R.id.listViewallcourses);
		tvallcoursesfound = (TextView) findViewById(R.id.tvallcoursesfound);
		ListDistanceCalculator = new ArrayList<DistanceCalculaor>();
		
		
		//Toast.makeText(getApplicationContext(),Integer.toString(ListDistanceCalculator.size()) , Toast.LENGTH_SHORT).show();
		
		
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
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		//Toast.makeText(getApplicationContext(), "Latitude:" + location.getLatitude() + ", Longitude:" + location.getLongitude(), Toast.LENGTH_SHORT).show();
		currentLocation.setLatitude(location.getLatitude());
		currentLocation.setLongitude(location.getLongitude());
		locationManager.removeUpdates(this);
		getallcoursesandcalculatedistance();
		
		diloge.dismiss();
		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}
	public void getallcoursesandcalculatedistance()
	{
		DAO = new PlayerDAO(getApplicationContext());
		listgolfcourses= DAO.getallGolfcourses();
		tvallcoursesfound.setText(Integer.toString(listgolfcourses.size())+"  Courses Found" );
		for(GolfCourse g: listgolfcourses)
		{
			calculatedistance(g);
		}
		listview.setAdapter(new CourseAdapter(getApplicationContext(), ListDistanceCalculator,1));
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
	public void calculatedistance(GolfCourse gcourse)
	{
		CourseLocation.setLatitude(gcourse.getLat());
		CourseLocation.setLongitude(gcourse.getLng());
		float distance =currentLocation.distanceTo(CourseLocation);
		ListDistanceCalculator.add(new DistanceCalculaor(gcourse, distance/1000));
		//return distance;
	}

}
