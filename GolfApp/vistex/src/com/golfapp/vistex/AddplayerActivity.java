package com.golfapp.vistex;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.os.Build;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class AddplayerActivity extends ActionBarActivity {

	
	//declare a fragment manager.
	android.app.FragmentManager myFragmentManager = getFragmentManager();
	GolfCourse SelectedCourse;
	Fragment myfragment = new CreatePlayerFragment();
	//a variable for the DAO object
	 private PlayerDAO myDAO;
	 
	
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_score);
		
	   	 Bundle bundle = new Bundle();
	     //String myMessage = getIntent(). ("id_course");
	     SelectedCourse = (GolfCourse) getIntent().getSerializableExtra("SelectedCourse");
	     bundle.putSerializable("SelectedCourse", SelectedCourse);
	     
	     myfragment.setArguments(bundle);
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
