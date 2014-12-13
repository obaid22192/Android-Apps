package com.example.pizzaorder;

import org.w3c.dom.Text;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;

public class Activityenteryourinfo extends ActionBarActivity {

	
	Button next ;
	Editable name;// with String i was not possible to get values from Edittext box 
	Editable phone;
	 EditText tvname;
	EditText tvnumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enteryourinfo);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN); 
       next = (Button) findViewById(R.string.idbtnnext);
       tvname = (EditText) findViewById(R.id.edname);
       tvnumber = (EditText) findViewById(R.id.ednumber);
       name = tvname.getText() ;
       phone = tvnumber.getText();
       next.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			if(name.length() == 0 || phone.length() == 0)
			{
			Toast toast = 	Toast.makeText(getApplicationContext(), "Name or Phone num is missing",Toast.LENGTH_LONG);
			toast.show();
			}
			else
			{
				Intent intent = new Intent(getApplicationContext() , Activitytopping.class);
				
				intent.putExtra("name-user", name.toString());
				intent.putExtra("number-user", phone.toString());		
				startActivity(intent);
			}
			
			
		
		}
	});
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
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }
    }

}
