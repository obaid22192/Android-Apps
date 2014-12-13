package com.golfapp.vistex;
import java.util.ArrayList;
import java.util.List;

import com.google.android.gms.maps.*;
import com.google.android.gms.maps.GoogleMap.OnCameraChangeListener;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerDragListener;
import com.google.android.gms.maps.model.*;

import android.R.integer;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Point;
import android.hardware.Camera;
import android.hardware.Camera.OnZoomChangeListener;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewDebug.ExportedProperty;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ZoomButtonsController.OnZoomListener;
import android.os.Build;
import android.app.Activity;

public class exploreActivity extends ActionBarActivity implements OnMarkerClickListener ,LocationListener, OnMapClickListener  ,OnCameraChangeListener{
    
    private PlayerDAO DAO;
    ProgressDialog diloge = null;
    protected LocationManager locationManager;
    private ArrayList<GolfCourse> listgolfcourses ;
    private GoogleMap map;
	private GolfCourse SelectedCourse = null;
	private ArrayList<Marker> markerlist = null;
	private float previousZoomLevel = -1.0f;
	private boolean isZooming = false;
	private Cursor cursor = null;
	private Location currentLocation;
	float distance;
	private Location corselocation;
	private Boolean isFar = false;
	private Spinner allholes;
	private int holecounter=0 ;
	private int  currentselectedhole;
	private Location currentselectedholeLocation;
	Polyline p = null;	
	private TextView tvdistance;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_explore);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
		markerlist = new ArrayList<Marker>();
	   
		SelectedCourse = (GolfCourse) getIntent().getSerializableExtra("SelectedCourse");
		 map = ((MapFragment) getFragmentManager()
	                .findFragmentById(R.id.map)).getMap();
           currentselectedhole =1;
	        map.moveCamera(CameraUpdateFactory.newLatLngZoom(
	                new LatLng(SelectedCourse.getLat(), SelectedCourse.getLng()), 14));
           
	        // Other supported types include: MAP_TYPE_NORMAL,
	        // MAP_TYPE_TERRAIN, MAP_TYPE_HYBRID and MAP_TYPE_NONE
	        map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
	        currentLocation = new Location("");
	        corselocation = new Location("");
	        currentselectedholeLocation = new Location("");
	        PlaceMarkersOnmap();
	        map.setOnCameraChangeListener(this);
	        map.setOnMapClickListener(this);
	        diloge = ProgressDialog.show(this,"Processing" , "Finding Your Location");
	        
	        corselocation.setLatitude(SelectedCourse.getLat());
	        corselocation.setLongitude(SelectedCourse.getLng());
	        setCurrentlocation();
	        tvdistance = (TextView) findViewById(R.id.tvdistance);
	       
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.exploremenu, menu);
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
		switch (id) {
		case R.id.action_hole:
			
			addItemsOnSpinner2();
			allholes.performClick();
			
			return true;
		case R.id.action_playround:
			Intent scoreavtivity = new Intent(getApplicationContext(), ScoreActivity.class);
			startActivity(scoreavtivity);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
		
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
		if(p !=null)
		{
		     p.remove();
		}
		if(isFar ==false)
		{
		
		     getholelocationbyid();
			 p=  map.addPolyline(new PolylineOptions().geodesic(true)
		                .add(new LatLng(currentselectedholeLocation.getLatitude(), currentselectedholeLocation.getLongitude())) 
		                .add(new LatLng(point.latitude,point.longitude)) 
		                .add(new LatLng(currentLocation.getLatitude(), currentLocation.getLatitude())));
			    float dis = currentLocation.distanceTo(currentselectedholeLocation);
				tvdistance.setText(Float.toString(dis)+" Meters");
		 
		}
		else
		{
			getholelocationbyid();
			 p=  map.addPolyline(new PolylineOptions().geodesic(true)
		                .add(new LatLng(currentselectedholeLocation.getLatitude(), currentselectedholeLocation.getLongitude())) 
		                .add(new LatLng(point.latitude,point.longitude)) 
		                );
			 Location temp = new Location("");
			 temp.setLatitude(point.latitude);
			 temp.setLongitude(point.longitude);
			 float dis =temp.distanceTo(currentselectedholeLocation);
			 tvdistance.setText(Float.toString(dis)+" Meters");
		}
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
	public void PlaceMarkersOnmap()
	{
		
		
			 DAO = new PlayerDAO(getApplicationContext());
			 cursor = DAO.Getholesinfo(SelectedCourse.getIdcourse());
		     Marker m = null;
			 if (cursor.moveToFirst()) {
		           do {
				
		        	   holecounter++;
		               m =   map.addMarker(new MarkerOptions()
		                .icon(BitmapDescriptorFactory.fromResource(R.drawable.flag4))
		                .anchor(0.5f, 1.0f) // Anchors the marker on the bottom left
		                
		                //.snippet(Integer.toString(g.getIdcourse()))
		                .position(new LatLng(Double.parseDouble(cursor.getString(1)), Double.parseDouble(cursor.getString(2)))))
		               
		              
		                ; 
		               markerlist.add(m);
		           } while (cursor.moveToNext());
		           
		       }
		
	}

	
	

	@Override
	public void onCameraChange(CameraPosition position) {
		// TODO Auto-generated method stub
		for(Marker m: markerlist )
		{
			map.addMarker( new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.flag4)) .anchor(0.5f, 1.0f).position(m.getPosition()));
			
			m.remove();
			
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		currentLocation.setLatitude(location.getLatitude());
		currentLocation.setLongitude(location.getLongitude());
		setCurrentlocation();
		locationManager.removeUpdates(this);
		distance =currentLocation.distanceTo(corselocation);
	//	Toast.makeText(getApplicationContext(), "Latitude:" +currentLocation.getLatitude() + ", Longitude:" + currentLocation.getLongitude() + " " +Float.toString(distance), Toast.LENGTH_SHORT).show();
		diloge.dismiss();
		if((distance/100) > 1 )
		{
			AlertDialog alertDialog = new AlertDialog.Builder(exploreActivity.this).create();
			alertDialog.setTitle("Location Allert");
			alertDialog.setMessage( "Vistex takes current location of golfer in the course to calculate distences from selected hole to the golfer  But You are "+distance/1000+" KM away from course you have to tab on map to get distance from selected hole to tabed area ");

			alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
					isFar = true;
					
					}
			});
			

			// Showing Alert Message
			alertDialog.show();
			isFar = true;
		}
		if(isFar==false)
		{	        getholelocationbyid();
					p=  map.addPolyline(new PolylineOptions().geodesic(true)
	                .add(new LatLng(currentselectedholeLocation.getLatitude(), currentselectedholeLocation.getLongitude())) 
	                .add(new LatLng(currentLocation.getLatitude(), currentLocation.getLatitude())));
					float dis = currentLocation.distanceTo(currentselectedholeLocation);
					tvdistance.setText(Float.toString(dis)+" Meters");
		}   
	       
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
		@SuppressWarnings("deprecation")
		public void setCurrentlocation(){
			locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
			locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 20, (LocationListener) this);
			
		}
		 public void addItemsOnSpinner2() {
			
			 allholes = (Spinner) findViewById(R.id.spallholes);
				List<String> list = new ArrayList<String>();
				for(int i = 0 ; i < holecounter; i++)
				{
					list.add(Integer.toString(i+1));
				}
				
			      ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
					android.R.layout.simple_spinner_item, list);
				dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				allholes.setAdapter(dataAdapter);
				 allholes.setOnItemSelectedListener(new OnItemSelectedListener() 
				    {
				       @Override
						public void onItemSelected(AdapterView<?> parent,
								View view, int position, long id) {
							// TODO Auto-generated method stub
				    	
						currentselectedhole = position+1;	
						if(p!=null)
						{
							p.remove();
						}
						Toast.makeText(getApplicationContext(), "Tab On Map to Get Infromation", Toast.LENGTH_SHORT).show();
						}

						@Override
						public void onNothingSelected(AdapterView<?> parent) {
							// TODO Auto-generated method stub
							
						}           
				    });
			  }
		public void getholelocationbyid()
		{
			 DAO = new PlayerDAO(getApplicationContext());
			 Cursor cursorhole = DAO.GetHoleLocationbyid(currentselectedhole, SelectedCourse.getIdcourse());
			 if (cursorhole.moveToFirst()) {
		           do {
				currentselectedholeLocation.setLatitude(Double.parseDouble(cursorhole.getString(1)));
				currentselectedholeLocation.setLongitude(Double.parseDouble(cursorhole.getString(2)));
		        	  
		           } while (cursorhole.moveToNext());
		           
		       }
		}
		

}
