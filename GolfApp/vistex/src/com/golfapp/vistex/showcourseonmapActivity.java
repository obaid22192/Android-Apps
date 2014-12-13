package com.golfapp.vistex;
import java.util.ArrayList;

import com.google.android.gms.maps.*;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerDragListener;
import com.google.android.gms.maps.model.*;

import android.R.integer;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Point;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;
import android.app.Activity;

public class showcourseonmapActivity extends ActionBarActivity implements OnMarkerClickListener , OnMapClickListener {
    private String Actionflag;
    private PlayerDAO DAO;
    private ArrayList<GolfCourse> listgolfcourses ;
    private GoogleMap map;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_showcourseonmap);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
		map = ((MapFragment) getFragmentManager()
                .findFragmentById(R.id.map)).getMap();

		map.setOnMapClickListener(this);
		//map.setMyLocationEnabled(true);
      Actionflag = getIntent().getStringExtra("actionflag");
      PlaceMarkersOnmap(Actionflag);

		
		
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
	public void onMapClick(LatLng point) {
		// TODO Auto-generated method stub
		Intent Showallcourses = new Intent(getApplicationContext(), showallcoursesinlistActivity.class);
		startActivity(Showallcourses);
		
	}

	@Override
	public boolean onMarkerClick(Marker marker) {
		// TODO Auto-generated method stub
		LatLng point = marker.getPosition();
		Intent intent = new Intent(getApplicationContext(), selectroundorexplorecoureseActivity.class);
		intent.putExtra("MapSelected","yes");
		intent.putExtra("id_course",marker.getSnippet());
		startActivity(intent);
		//Toast.makeText(getApplicationContext(),marker.getSnippet(), Toast.LENGTH_SHORT).show();
		
		return false;
	}
	public void PlaceMarkersOnmap(String flag)
	{
		if(flag.equals("showall"))
		{
		
			DAO = new PlayerDAO(getApplicationContext());
			listgolfcourses= DAO.getallGolfcourses();
		
			for(GolfCourse g : listgolfcourses)
			{
				map.moveCamera(CameraUpdateFactory.newLatLngZoom(
		                new LatLng(g.getLat(), g.getLng()), 16));

		        // You can customize the marker image using images bundled with
		        // your app, or dynamically generated bitmaps. 
		        map.addMarker(new MarkerOptions()
		                .icon(BitmapDescriptorFactory.fromResource(R.drawable.courseflag))
		                .anchor(0.0f, 1.0f) // Anchors the marker on the bottom left
		                .snippet(Integer.toString(g.getIdcourse()))
		                .position(new LatLng(g.getLat(), g.getLng())))
		               
		                .setTitle(g.getName()+"  " +g.getCity())
		                ; 
		               
		        map.setOnMarkerClickListener(this);
				
			}
			
		}
		
	}

}
