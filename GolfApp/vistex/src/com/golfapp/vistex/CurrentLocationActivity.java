package com.golfapp.vistex;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;

import java.io.IOException;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;


public class CurrentLocationActivity extends FragmentActivity implements
GooglePlayServicesClient.ConnectionCallbacks,
GooglePlayServicesClient.OnConnectionFailedListener
{

	   LocationClient mLocationClient;
	   private TextView addressLabel;
	   private TextView locationLabel;
	   private Button getLocationBtn;
	   private Button disconnectBtn;
	   private Button connectBtn;


	
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_getlocation);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
		 locationLabel = (TextView) findViewById(R.id.locationLabel);
	      addressLabel = (TextView) findViewById(R.id.addressLabel);
	      getLocationBtn = (Button) findViewById(R.id.getLocation);

	      getLocationBtn.setOnClickListener(new View.OnClickListener() {
	         public void onClick(View view) {
	            displayCurrentLocation();
	         }
	      });
	      disconnectBtn = (Button) findViewById(R.id.disconnect);  
	      disconnectBtn.setOnClickListener(new View.OnClickListener() {
	         public void onClick(View view) {
	            mLocationClient.disconnect();
	            locationLabel.setText("Got disconnected....");
	         }
	      });
	      connectBtn = (Button) findViewById(R.id.connect);  
	      connectBtn.setOnClickListener(new View.OnClickListener() {
	         public void onClick(View view) {
	            mLocationClient.connect();
	            locationLabel.setText("Got connected....");
	         }
	      });	
	      // Create the LocationRequest object
	      mLocationClient = new LocationClient(this, this, this);
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
	   protected void onStart() {
	      super.onStart();
	      // Connect the client.
	      mLocationClient.connect();
	      locationLabel.setText("Got connected....");
	   }
	   @Override
	   protected void onStop() {
	      // Disconnect the client.
	      mLocationClient.disconnect();
	      super.onStop();
	      locationLabel.setText("Got disconnected....");
	   }
	   @Override
	   public void onConnected(Bundle dataBundle) {
	      // Display the connection status
	      Toast.makeText(this, "Connected", Toast.LENGTH_SHORT).show();
	   }
	   @Override
	   public void onDisconnected() {
	      // Display the connection status
	      Toast.makeText(this, "Disconnected. Please re-connect.",
	      Toast.LENGTH_SHORT).show();
	   }
	   @Override
	   public void onConnectionFailed(ConnectionResult connectionResult) {
	      // Display the error code on failure
	      Toast.makeText(this, "Connection Failure : " + 
	      connectionResult.getErrorCode(),
	      Toast.LENGTH_SHORT).show();
	   }
	   public void displayCurrentLocation() {
	      // Get the current location's latitude & longitude
	      Location currentLocation = mLocationClient.getLastLocation();
	      String msg = "Current Location: " +
	      Double.toString(currentLocation.getLatitude()) + "," +
	      Double.toString(currentLocation.getLongitude());
	     
	      // Display the current location in the UI
	      locationLabel.setText(msg);
	      
	      // To display the current address in the UI
	    // (new GetAddressTask(this)).execute(currentLocation);
	   }

}
