package com.example.pizzaorder;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.os.Build;

public class Activitytopping extends ActionBarActivity {

	Button CheckOut;
	String Toppings;
	String Shape;
	String Chees;
	String name;
	String phone;
	private RadioGroup radiogroupchees;
	private RadioGroup radiogroupshape;
	private RadioButton groupcheesRB;
	private RadioButton groupShapeRB;
	private CheckBox pep;
	private CheckBox mush;
	private CheckBox vegg;
	private CheckBox anch;
	private CheckBox ham;
	private CheckBox grilled;
	private CheckBox italian;
	private CheckBox black;
	private CheckBox green;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_topping);

		CheckOut = (Button) findViewById(R.string.idplaceorder);
		radiogroupchees = (RadioGroup) findViewById(R.id.radioGroup1);
		radiogroupshape = (RadioGroup) findViewById(R.id.radioGroup2);
		pep = (CheckBox) findViewById(R.string.idpepperoni);
		mush = (CheckBox) findViewById(R.string.idmush);
		vegg = (CheckBox) findViewById(R.string.idvegg);
		anch = (CheckBox) findViewById(R.string.idanch);
		ham = (CheckBox) findViewById(R.string.idham);
		grilled = (CheckBox) findViewById(R.string.idgrill);
		italian = (CheckBox) findViewById(R.string.iditalian);
		black = (CheckBox) findViewById(R.string.idblackol);
		green = (CheckBox) findViewById(R.string.idgreen);

		CheckOut.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(),
						addressactivuty.class);
				GenrateStrings();
				if (Toppings == " ") {
					Toast toast = Toast.makeText(getApplicationContext(),
							"Select at least One Topping ", Toast.LENGTH_LONG);
					toast.show();
				} else {
					name = getIntent().getStringExtra("name-user");
					phone = getIntent().getStringExtra("number-user");
					intent.putExtra("order-toppings", Toppings);
					intent.putExtra("order-shape", Shape);
					intent.putExtra("order-chees", Chees);
					intent.putExtra("nameof-user", name);
					intent.putExtra("user-phone", phone);
					startActivity(intent);
				}
			}
		});
	}

	public void GenrateStrings() {
		Toppings = " ";
		if (pep.isChecked()) {
			Toppings += "Pepperoni,";

		}
		if (mush.isChecked()) {
			Toppings += "Mushrooms,";
		}
		if (vegg.isChecked()) {
			Toppings += "Veggies,";
		}
		if (anch.isChecked()) {
			Toppings += "Anchovies,";
		}
		if (ham.isChecked()) {
			Toppings += "Ham,";
		}
		if (grilled.isChecked()) {
			Toppings += "Grilled Chicken,";
		}
		if (italian.isChecked()) {
			Toppings += "Italian Sausage,";
		}
		if (black.isChecked()) {
			Toppings += "Black Olives,";
		}
		if (green.isChecked()) {
			Toppings += "Green Olives,";
		}

		int SelectedbtncheesID = radiogroupchees.getCheckedRadioButtonId();
		int SelectedshaperbID = radiogroupshape.getCheckedRadioButtonId();

		groupcheesRB = (RadioButton) findViewById(SelectedbtncheesID);
		groupShapeRB = (RadioButton) findViewById(SelectedshaperbID);
		Chees = (String) groupcheesRB.getText();
		Shape = (String) groupShapeRB.getText();

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
