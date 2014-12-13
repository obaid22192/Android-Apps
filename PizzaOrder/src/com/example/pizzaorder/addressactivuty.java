package com.example.pizzaorder;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.telephony.gsm.SmsManager;
import android.text.Editable;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;

@SuppressWarnings("deprecation")
public class addressactivuty extends ActionBarActivity {
	Editable Streetname;// with String it was not possible to get values from
						// Edittext box
	Editable HouseNR;
	Editable City;
	String Toppingstxt;
	String Cheestxt;
	String Shapetxt;
	String Name;
	String phone;
	TextView FinalOrder;
	Button Porder;
	EditText Etstreetname;
	EditText Etcity;
	EditText EtHouseNr;
    ImageView image;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_address);

		Porder = (Button) findViewById(R.string.idbtnporder);
		Etstreetname = (EditText) findViewById(R.string.idstreet);
		EtHouseNr = (EditText) findViewById(R.string.idhno);
		Etcity = (EditText) findViewById(R.string.idcity);
		 image = (ImageView) findViewById(R.string.idpizzaimg);
		Toppingstxt = getIntent().getStringExtra("order-toppings");
		Cheestxt = getIntent().getStringExtra("order-chees");
		Shapetxt = getIntent().getStringExtra("order-shape");
		Name = getIntent().getStringExtra("nameof-user");
		phone = getIntent().getStringExtra("user-phone");

		FinalOrder = (TextView) findViewById(R.string.idtvorder);

		FinalOrder.setText("Name \t" + Name + "\n" + Cheestxt + "\n"
				+ "SHAPE : \t" + Shapetxt + "\n" + "Toppings\t" + Toppingstxt);
		if(Shapetxt.equals("Square"))
		{
			image.setImageResource(R.drawable.squarepizza);
		}
		else
		{
			image.setImageResource(R.drawable.round);
		}
		Porder.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Streetname = Etstreetname.getText();
				HouseNR = EtHouseNr.getText();
				City = Etcity.getText();
				if(Streetname.length() ==0 || HouseNR.length() ==0 || City.length()==0)
				{
					Toast toast = Toast.makeText(getApplicationContext(), "Fill in all adrress fields", Toast.LENGTH_LONG);
					toast.show();
				}
				else
				{

				SendText("Name :  " + Name + ",   " + Cheestxt + "SHAPE :    "
						+ Shapetxt + ",  Toppings   " + Toppingstxt, phone);
				}

			}

		});
	}

	public void SendText(String text, String PhoneNumber) {
		String SENT = "Message_Sent";
		String DELIVERED = "Message_Delivered";
		PendingIntent sentPI = PendingIntent.getBroadcast(this, 0, new Intent(SENT),0);
		PendingIntent deliveredPI = PendingIntent.getBroadcast(this, 0, new Intent(DELIVERED), 0);
		
		registerReceiver(new BroadcastReceiver() {
			
			@Override
			public void onReceive(Context context, Intent intent) {
				switch (getResultCode())
				{
				case Activity.RESULT_OK:
				Toast.makeText(getBaseContext(), "SMS sent",
				Toast.LENGTH_SHORT).show();
				break;
				case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
				Toast.makeText(getBaseContext(), "Generic failure",
				Toast.LENGTH_SHORT).show();
				break;
				case SmsManager.RESULT_ERROR_NO_SERVICE:
				Toast.makeText(getBaseContext(), "No service",
				Toast.LENGTH_SHORT).show();
				break;
				case SmsManager.RESULT_ERROR_NULL_PDU:
				Toast.makeText(getBaseContext(), "Null PDU",
				Toast.LENGTH_SHORT).show();
				break;
				case SmsManager.RESULT_ERROR_RADIO_OFF:
				Toast.makeText(getBaseContext(), "Radio off",
				Toast.LENGTH_SHORT).show();
				break;
				}
				
			}
		},new  IntentFilter(SENT));
		registerReceiver(new BroadcastReceiver() {
			
			@Override
			public void onReceive(Context context, Intent intent) {
				switch (getResultCode())
				{
				case Activity.RESULT_OK:
					
				Toast.makeText(getBaseContext(), "SMS delivered",
				Toast.LENGTH_SHORT).show();
				
				break;
				case Activity.RESULT_CANCELED:
				Toast.makeText(getBaseContext(), "SMS not delivered",
				Toast.LENGTH_SHORT).show();
				break;
				}
				
			}
		}, new IntentFilter(DELIVERED));
		
		
		SmsManager sms = SmsManager.getDefault();
		sms.sendTextMessage(PhoneNumber, null, text, sentPI, deliveredPI);
		Toast toast = Toast.makeText(getApplicationContext(), "Order Sent ",
				Toast.LENGTH_LONG);
		toast.show();
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
