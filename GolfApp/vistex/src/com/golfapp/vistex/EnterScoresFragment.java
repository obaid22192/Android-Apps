package com.golfapp.vistex;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class EnterScoresFragment extends Fragment implements OnItemSelectedListener, OnClickListener{

	
	//variables to hold selected values
	int hole, score, playerID;
	String playername;
	
	//an adapter for my spinners
	ArrayAdapter<Integer> spinneradapter;
	ArrayAdapter<String> stringspinneradapter;
	
	//a variables for my controls
	Spinner scoreSpinner, playerSpinner, holeSpinner;
	Button saveScore;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View myView = inflater.inflate(R.layout.enterscorelayout, container, false);

		return myView;
	}
	
	
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {

		//add scores to scorespinner
		addScoresToSpinner();
		//populate the player spinner with players from database.
	    addPlayersToSpinner();
	    //add hole numbers to holespinner.
	    addHolesToSpinner();
	    //instantiate the button.
	    saveScore = (Button)getView().findViewById(R.id.SaveScores);
	    
		//create listeners for the spinners.
	    holeSpinner.setOnItemSelectedListener(this);
		playerSpinner.setOnItemSelectedListener(this);
		scoreSpinner.setOnItemSelectedListener(this);
		saveScore.setOnClickListener(this);
		
		
		super.onActivityCreated(savedInstanceState);
	}

	public void addHolesToSpinner(){
		
		holeSpinner = (Spinner)getView().findViewById(R.id.selectHole);
		List<Integer> list = new ArrayList<Integer>();
		for(int i = 1; i<=18; i++){
			list.add(i);
		}
		
		spinneradapter = new ArrayAdapter<Integer>(this.getActivity(), android.R.layout.simple_spinner_dropdown_item,list);
		spinneradapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		holeSpinner.setAdapter(spinneradapter);
	}

	public void addPlayersToSpinner(){
		
		//read players from Database into a list.
	    PlayerDAO myDAO = new PlayerDAO(this.getActivity());
	    //open the database.
	    myDAO.open();
	    List<Player> players = myDAO.getAllPlayers();
	    myDAO.close();
	    
		playerSpinner = (Spinner)getView().findViewById(R.id.selectPlayer);
		
		//add player names to a new list;
		List<String> nameList = new ArrayList<String>();
			for(Player p: players){
				nameList.add(p.playerName);
			}
		
		//create new adapter and populate spinner.
		stringspinneradapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_dropdown_item,nameList);
		stringspinneradapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		playerSpinner.setAdapter(stringspinneradapter);
		
	}

	public void addScoresToSpinner(){
		
		//TODO:Implement enum to get numeric values for the list below.
		
		scoreSpinner = (Spinner)getView().findViewById(R.id.selectScore);
		List<Integer> list = new ArrayList<Integer>();
		list.add(1);
		list.add(2);
		list.add(3);
		list.add(4);
		list.add(5);
		list.add(6);
		list.add(7);
		list.add(8);
		list.add(9);
		list.add(10);
		
		spinneradapter = new ArrayAdapter<Integer>(this.getActivity(), android.R.layout.simple_spinner_dropdown_item,list);
		spinneradapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		scoreSpinner.setAdapter(spinneradapter);
	}
	
	
	@Override
	public void onItemSelected(AdapterView<?> arg0, View v, int arg2,
			long arg3) {

		if(arg0==holeSpinner){
			hole = (Integer) arg0.getItemAtPosition(arg2);
		}
		
		if(arg0==playerSpinner){
			playername = arg0.getItemAtPosition(arg2).toString();
			//get player id from database;
			PlayerDAO myDAO = new PlayerDAO(this.getActivity());
		    //open the database.
		    myDAO.open();
		    Player player;
		    player = myDAO.GetPlayerID(playername);
		    //assign it to class variable for later saving.
		    playerID = player.getId();
		    
		    myDAO.close();
			
		}
		
		if(arg0==scoreSpinner){
			score = (Integer) arg0.getItemAtPosition(arg2);
		}
		
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}


	//This button saves the entered score to the database.
	@Override
	public void onClick(View v) {
		
		PlayerDAO myDAO = new PlayerDAO(this.getActivity());
		myDAO.open();
		myDAO.InsertScore(playerID, hole, score);
		
		//Just a toast to see if player id is correctly generated.
//		String id = String.valueOf(playerID);
//		Toast.makeText(this.getActivity(), id, Toast.LENGTH_SHORT).show();
		
	}

}//class
