package com.golfapp.vistex;


import com.google.android.gms.internal.ex;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Html;
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
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

public class selectroundorexplorecoureseActivity extends ActionBarActivity implements OnClickListener {
	private TextView cityText;
	private TextView condDescr;
	private TextView temp;
	private TextView press;
	private TextView windSpeed;
	private TextView windDeg;
    private TextView hum;
	private ImageView imgView;

	private TextView tvcoursename;
	private TextView tvcountry;
	private TextView tvcity;
	private TextView tvholecount;
	private Button AddFav;
	private PlayerDAO DAO;
	private int  SelectedcourseID;
	private GolfCourse SelectedCourse;
	private Button startround;
	private Button btnexplore;
	Fragment myFragment;
	android.app.FragmentTransaction transaction;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_selectroundorexplorecourse);
        String city = " ";
        
        
        
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
		
        btnexplore = (Button) findViewById(R.id.btncourseexplore);
        btnexplore.setOnClickListener(this);
		startround = (Button) findViewById(R.id.btnstartround);
		startround.setOnClickListener(this);
		tvcoursename = (TextView) findViewById(R.id.tvcoursename);
		tvcountry = (TextView) findViewById(R.id.tvcountry);
		tvcity = (TextView) findViewById(R.id.tvcityzip);
		tvholecount = (TextView) findViewById(R.id.tvholes);
		AddFav  = (Button) findViewById(R.id.btnaddtofavorites);
		GetselectedCourseandshow();// to get and show text on screen 
		AddFav.setOnClickListener(this);
		
		cityText = (TextView) findViewById(R.id.cityText);
		condDescr = (TextView) findViewById(R.id.condDescr);
		temp = (TextView) findViewById(R.id.temp);
		hum = (TextView) findViewById(R.id.hum);
		press = (TextView) findViewById(R.id.press);
		windSpeed = (TextView) findViewById(R.id.windSpeed);
		windDeg = (TextView) findViewById(R.id.windDeg);
		imgView = (ImageView) findViewById(R.id.condIcon);
		
		JSONWeatherTask task = new JSONWeatherTask();
		//task.execute(new String[]{city});
		task.execute(new String[] {city});
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
	public void GetselectedCourseandshow()
	{
		if(getIntent().getStringExtra("MapSelected").equals("yes"))
		{
			
		    DAO = new PlayerDAO(getApplicationContext());
		    SelectedCourse =DAO.GetCourseBYId(Integer.parseInt(getIntent().getStringExtra("id_course")));
		   
		}
		else
		{
		SelectedCourse = (GolfCourse) getIntent().getSerializableExtra("SelectedCourse");
		}
		SelectedcourseID = SelectedCourse.getIdcourse();
		tvcoursename.setText(SelectedCourse.getName());
		tvcountry.setText(SelectedCourse.getCountry());
		tvcity.setText(Html.fromHtml(SelectedCourse.getAddress()+ "<br />"+ SelectedCourse.getZipcode()+"<br />"+ SelectedCourse.getCity()));
		tvholecount.setText(Integer.toString(SelectedCourse.getHolecount())+ "   ");
		
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v == AddFav)
		{
	     DAO = new PlayerDAO(getApplicationContext());
	     DAO.insertfavoritecourse(SelectedcourseID);
		}
		if(v ==startround)
		{
			Intent scoreavtivity = new Intent(getApplicationContext(), AddplayerActivity.class);
			scoreavtivity.putExtra("SelectedCourse", SelectedCourse);
			startActivity(scoreavtivity);
		}
		if(v==btnexplore)
		{
			Intent intent = new Intent(getApplicationContext(), exploreActivity.class);
			intent.putExtra("SelectedCourse",SelectedCourse);
			startActivity(intent);
		}
		
	}
	private class JSONWeatherTask extends AsyncTask<String, Void, Weather> {

		@Override
		protected Weather doInBackground(String... params) {
			Weather weather = new Weather();
			String data =  ( (new WeatherHttpClient()).getWeatherData(Double.toString(SelectedCourse.getLat())+"&"+Double.toString(SelectedCourse.getLng())));
			
			try{
				weather = JSONWeatherParser.getWeather(data);
				//Retrieve the icon
				String icon=weather.currentCondition.getIcon();
				weather.iconData = ( (new WeatherHttpClient()).getImage(weather.currentCondition.getIcon()));
			}
			catch(Exception e){
				e.printStackTrace();
			}
			return weather;
			
		}
		protected void onPostExecute(Weather weather) {
			super.onPostExecute(weather);
		
			if (weather.iconData != null && weather.iconData.length > 0) {
				Bitmap img = BitmapFactory.decodeByteArray(weather.iconData, 0, weather.iconData.length);
				imgView.setImageBitmap(img);
			}
			
			cityText.setText(weather.location.getCity() + "," + weather.location.getCountry());
			condDescr.setText(weather.currentCondition.getCondition() + "(" + weather.currentCondition.getDescr() + ")");
			temp.setText("" + Math.round((weather.temperature.getTemp() - 273.15)) + "Celcius");
			hum.setText("" + weather.currentCondition.getHumidity() + "%");
			press.setText("" + weather.currentCondition.getPressure() + " hPa");
			windSpeed.setText("" + weather.wind.getSpeed() + " mps");
			windDeg.setText("" + weather.wind.getDeg() + "Degree");

		}

	
}

}

