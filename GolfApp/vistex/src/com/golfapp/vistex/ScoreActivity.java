package com.golfapp.vistex;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.annotation.TargetApi;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class ScoreActivity extends ActionBarActivity {

	
	//declare a fragment manager.
	android.app.FragmentManager myFragmentManager = getFragmentManager();
	//create new ScoreHome Fragment
	Fragment myfragment = new ScoreHomeFragment();
	//a variable for the DAO object
	private PlayerDAO myDAO;
	
	//a round must actually be created as soon as the app starts.
	
//	//create a round
//	//get courseName from selected course ;
//	String courseName = "EindCountryClub";
//	//get date of play from system.
//	Calendar c = Calendar.getInstance();
//    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//    String formattedDate = df.format(c.getTime());
//    // formattedDate have current date/time
//	Round round = new Round(courseName, formattedDate);
	
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_score);
		


		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, myfragment).commit();
		}
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



}
