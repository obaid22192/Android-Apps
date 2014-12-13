package com.golfapp.vistex;
import java.util.ArrayList;
import java.util.List;

public class Player {
	
	public String playerName;
	public String email;
	public double handicap;
	public int numberofRoundsPlayed;
	public int id;
	
	//a list of the players last 10 netscores.
	List<Integer> scores;
	
	//getters and setters.
	public String getPlayerName() {
		return playerName;
	}
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public double getHandicap() {
		return handicap;
	}
	public void setHandicap(double handicap) {
		this.handicap = handicap;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getNumberofRoundsPlayed() {
		return numberofRoundsPlayed;
	}
	public void setNumberofRoundsPlayed(int numberofRoundsPlayed) {
		this.numberofRoundsPlayed = numberofRoundsPlayed;
	}
	
	//a constructor
	public Player(String playername, String email){
		
		this.playerName = playername;
		this.email = email;
	}
	//constructor overload
	public Player(String playername, String email, int roundsPlayed){
		this.playerName = playername;
		this.email = email;
		this.numberofRoundsPlayed = roundsPlayed;
		
	}
	
	//A method to calculate this player's handicap.
	public void CalculateHandicap(ArrayList<Integer> scores, int coursePar){
		
		scores = new ArrayList<Integer>();
		int scoresTotal = 0;
		coursePar = 70; //this value must come from the database;
		
		//this is just sample data. You must get this from the database later.
		scores.add(60);
		scores.add(76);
		scores.add(58);
		scores.add(70);
		scores.add(60);
		scores.add(60);
		scores.add(85);
		scores.add(46);
		scores.add(60);
		scores.add(60);
		
		//TODO: Average scores.
		for(int i:scores){
			scoresTotal +=  i;
		}
		this.handicap = (scoresTotal/10) - coursePar;
	}
	
}//class
