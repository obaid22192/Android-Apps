package com.golfapp.vistex;
import com.google.android.gms.internal.fb;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class CreatePlayerFragment extends Fragment implements OnClickListener{
	
	//variables for the player object
	Player player;
	//variables for the xml components
	private TextView playername, email;
	private Button createPlayer;
	private Button btnStartRound;
	private int id_course;
	private GolfCourse SelectedCourse;
	//a variable for the DAO object
	private PlayerDAO myDAO;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {

		//associate variables with XML resources.
		playername = (TextView)getView().findViewById(R.id.editText_PlayerName);
		email = (TextView)getView().findViewById(R.id.editText_Email);
		createPlayer = (Button)getView().findViewById(R.id.btn_CreatePlayer);
		btnStartRound = (Button) getView().findViewById(R.id.btnroundstart);
		btnStartRound.setOnClickListener(this);
		createPlayer.setOnClickListener(this);
		Bundle bundle = this.getArguments();
        String myValue = bundle.getString("message");
        SelectedCourse = (GolfCourse) bundle.getSerializable("SelectedCourse");
        id_course = SelectedCourse.getIdcourse();
      //  Toast.makeText(getActivity(),Integer.toString(id_course), Toast.LENGTH_LONG).show();
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View myView = inflater.inflate(R.layout.createplayerlayout, container, false);
		
		return myView;
	}

	@Override
	public void onClick(View v) {
		myDAO = new PlayerDAO(this.getActivity());
		
		if(v==createPlayer){
			int count = myDAO.checkplayers();
			if(count<4)
			{
			String pname = playername.getText().toString();
			String pemail = email.getText().toString();
			
			//create a player object.
			player = new Player(pname, pemail);
			//add player to round
			
			Toast.makeText(this.getActivity(), "Player "+player.playerName+" created", Toast.LENGTH_SHORT).show();
			
			//write player data to database
			//create the database.
		    
		    //open the database.
		    myDAO.open();
			myDAO.WritePlayer(player);
			
			
			}
			else 
			{
				Toast.makeText(this.getActivity(), "It is not possible to Add more than 4 players", Toast.LENGTH_SHORT).show();
			}
		}
		if(v== btnStartRound)
		{
			myDAO.CreateRound(id_course);
			Intent intent = new Intent(getActivity(), playroundActivity.class);
			intent.putExtra("SelectedCourse", SelectedCourse);
			startActivity(intent);
		}
		
	}
	
	

}//class
