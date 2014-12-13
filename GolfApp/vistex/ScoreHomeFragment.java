package com.vistex.app;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.pdf.PdfDocument;
import android.graphics.pdf.PdfDocument.Page;
import android.graphics.pdf.PdfDocument.PageInfo;
import android.os.Build;
import android.os.Bundle;
//import android.print.pdf.PrintedPdfDocument;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


public class ScoreHomeFragment extends Fragment implements OnClickListener{
	
	//declare buttons
	Button enterScores, addPlayer, publishRound;
	Button showPlayers;
	
	//declare a fragment object;
	Fragment myFragment;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View myView = inflater.inflate(R.layout.scorehomelayout, container, false);
		
		return myView;
	}

	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
				
			//associate the button objects with layout elements and set the listeners
			enterScores = (Button)getView().findViewById(R.id.EnterScores);
			enterScores.setOnClickListener(this);
			addPlayer = (Button)getView().findViewById(R.id.btn_addPlayer);
			addPlayer.setOnClickListener(this);
			publishRound = (Button)getView().findViewById(R.id.btn_ShareResults);
			publishRound.setOnClickListener(this);
			//showPlayers = (Button)getView().findViewById(R.id.btn_ShowPlayers);
			//showPlayers.setOnClickListener(this);
			
			super.onActivityCreated(savedInstanceState);
	}

	//eventHandler for button clicks.
	@Override
	public void onClick(View view) {
		
		//a transaction variable to handle fragment communication
		FragmentTransaction transaction;
		
		//if the event is raise by the EnterScores button
		if(view.getId()==R.id.EnterScores){
			myFragment = new EnterScoresFragment();
			
			//begin an exchange with the new EnterScoreFragment object.
			transaction = getFragmentManager().beginTransaction();
			transaction.replace(R.id.container, myFragment);
			transaction.addToBackStack(null);//this puts the fragment into a stack such that we return to it when the back button is pressed.
			transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE); //fades the fragment in. Nice!!
			transaction.commit();
		}
			
		//if the even is raised by the addPlayer button
			if(view.getId()==R.id.btn_addPlayer){
				myFragment = new CreatePlayerFragment();
				
				//begin an exchange with the new EnterScoreFragment object.
				transaction = getFragmentManager().beginTransaction();
				transaction.replace(R.id.container, myFragment);
				transaction.addToBackStack(null);//this puts the fragment into a stack such that we return to it when the back button is pressed.
				transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE); //fades the fragment in. Nice!!
				transaction.commit();
		}
			
		//if the even is raised by the addPlayer button
		if(view.getId()==R.id.btn_ShareResults){
			

			
			//TODO: Save round results to DB
			//TODO: Retrieve entire Round results from DB
			//TODO: Write Results into a text file
			//TODO: Open local email client with results text file as attachment.
			
			String textFromFile = "This is the Round Summary";
			//Create a PDF document.
//			CreatePDF(textFromFile); //fails without kitkat.
			
			//create an email intent.
			Intent intent = new Intent(Intent.ACTION_SEND);
			intent.setType("plain/text");
			intent.putExtra(Intent.EXTRA_EMAIL, new String[] { "some@email.address" });
			intent.putExtra(Intent.EXTRA_SUBJECT, "subject");
			intent.putExtra(Intent.EXTRA_TEXT, "mail body");
			startActivity(Intent.createChooser(intent, ""));
			
		}
		
		//just a method to show that db access is working.
//		if(view.getId()==R.id.btn_ShowPlayers){
//		    PlayerDAO myDAO = new PlayerDAO(this.getActivity());
//		    //open the database.
//		    myDAO.open();
//		    List<Player> players = myDAO.getAllPlayers();
//		    //display each player in the textview
//		    TextView tv = new TextView(getActivity());
//		    tv=(TextView)getView().findViewById(R.id.textView_ShowPlayers);
//		    for(Player player: players){
//		    	String id = String.valueOf(player.id).toString();
//		    	tv.setText(id + player.playerName + player.email);
//		    }
//		}
		
	}//onClick method.

//	@TargetApi(Build.VERSION_CODES.KITKAT)
//	@SuppressLint("NewApi")
//	private void CreatePDF(String textFromFile) {
//
//		// Create a shiny new (but blank) PDF document in memory
//				PdfDocument document = new PdfDocument();
//
//				// crate a page description
//				PageInfo pageInfo = new PageInfo.Builder(300, 300, 1).create();
//
//				// create a new page from the PageInfo
//				Page page = document.startPage(pageInfo);
//
//				// repaint the "textfromfile" text into the page
//				TextView tv=new TextView(getActivity());
//				tv.setText(textFromFile);
//				tv.draw(page.getCanvas());
//
//				// do final processing of the page
//				document.finishPage(page);
//				
//				ByteArrayOutputStream os = new ByteArrayOutputStream();
//				try {
//					document.writeTo(os);
//					document.close();
//					os.close();
//				} catch (IOException e) {
//					throw new RuntimeException("Error generating file", e);
//				}
//				
//
//				Intent intent = new Intent(Intent.ACTION_SEND);
//				intent.setAction(Intent.ACTION_SEND);
//				intent.setType("application/pdf");
//				// Assuming it may go via eMail:
//		        intent.putExtra(android.content.Intent.EXTRA_SUBJECT,"Here is a PDF from PdfSend");
//				intent.putExtra(
//					getClass().getPackage().getName() + "." + "SendPDF", 
//					os.toByteArray());
//				startActivity(intent);
//		
//	}
	
	

}//class
