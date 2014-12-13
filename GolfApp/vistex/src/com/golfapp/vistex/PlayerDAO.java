package com.golfapp.vistex;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

//a class for a Data Access Object
public class PlayerDAO {

	//Database Fields
	private SQLiteDatabase database;
	private DatabaseHelper dbhelper;

	//setup array for retrieving all columns.
	private static int i = 1;
    private static final String KEY_ID = "id";
    private static final String KEY_ID_SCORES = "player_id";
    private static final String KEY_ID_ROUNDS = "round_id";
    private static final String KEY_NAME = "name";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_HANDICAP = "handicap";
    private static final String KEY_NUMOFROUNDS = "numberofrounds";
    private static final String KEY_HOLE_NO = "hole_no";
    private static final String KEY_NOOFSTROKES = "no_of_strokes";
    
    
    private static final String[] COLUMNS = {KEY_ID,KEY_NAME,KEY_EMAIL,KEY_HANDICAP, KEY_NUMOFROUNDS};
    
    //constructor
    public PlayerDAO(Context context){
    	
    	//instantiate dbhelper.
    	dbhelper = new DatabaseHelper(context);
    	try {
			dbhelper.createDataBase();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    //a method to open Database.
    public void open() throws SQLException {
    	
    	//assign database object.
        database = dbhelper.getWritableDatabase();
      }
    
    //a method to close database
    public void close() {
        dbhelper.close();
      }
    
    //a method to write player to DB.
    public void WritePlayer(Player player){
    	
    	open();
    	//create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(KEY_ID, i);
        values.put(KEY_NAME, player.playerName); // get name 
        values.put(KEY_EMAIL, player.email); 
        values.put("numberofrounds", 0);// get email
        i+=1;
        //insert
        database.insert("PLAYER", // table
                null, //nullColumnHack
                values); // key/value -> keys = column names/ values = column values
        dbhelper.close(); 
    }
    
    //a method to get all players from database
    public List<Player> getAllPlayers(){
    	List<Player> players = new LinkedList<Player>();
    	
    	// 1. build the query
        String query = "SELECT  * FROM PLAYER";
        
        Cursor cursor = database.rawQuery(query, null);
  
        // 3. go over each row, build golfer and add it to list
        Player player = null;
        if (cursor.moveToFirst()) {
            do {
                player = new Player("x", "x"); //just feed bogus values to the object.
                player.setId(Integer.parseInt(cursor.getString(0)));
                player.setPlayerName(cursor.getString(1));
                player.setEmail(cursor.getString(2));
               // golfer.setPhone(cursor.getString(4));
          	  
                // Add golfer to golfers
                players.add(player);
            } while (cursor.moveToNext());
        }
  
        //Log.d("getAllGolfers()", golfers.toString());
  
        // return golfers
        return players;
    }
    
    //a method to insert scores
    public void InsertScore(int playerid, int holeNo, int noOfStrokes){
    	 
    	open();
    	//create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(KEY_ID_SCORES, playerid); // get id
        values.put(KEY_HOLE_NO, holeNo); // hole number
        values.put(KEY_NOOFSTROKES, noOfStrokes); //number of strokes i.e. score per hole
        //values.put(KEY_ID_ROUNDS, round_id);//get round id from rounds table and pass it as a parameter. Currently the absence of this raises an error because round_id can not be null (primary key);

        //insert
        database.insert("SCORES", // table
                null, //nullColumnHack
                values); // key/value -> keys = column names/ values = column values
        database.close();
    }
    
    //a method to get player id.
    public Player GetPlayerID(String playername){
    	
    	// 1. build the query
        Cursor cursor = 
                database.query("PLAYER", // a. table
                COLUMNS, // b. column names
                "name = ?", // c. selections or where condition 
                new String[] { playername }, // d. selections args
                null, // e. group by
                null, // f. having
                null, // g. order by
                null); // h. limit
     
        // 3. if we got results get the first one
        if (cursor != null)
            cursor.moveToFirst();
     
        // 4. build golfer object
        Player player = new Player("x", "x");
        player.setId(Integer.parseInt(cursor.getString(0)));
    	
    	return player;
    }
    
    // this method will get all golf courses from local database return array of golf course objects 
    public ArrayList<GolfCourse> getallGolfcourses()
    {
    	open();
    	ArrayList<GolfCourse> Courses = new ArrayList<GolfCourse>();
        String query = "SELECT  * FROM Golfcourse";
        Cursor cursor = database.rawQuery(query, null);
        GolfCourse golfcourses = null;
        if (cursor.moveToFirst()) {
            do {
              
            	golfcourses = new GolfCourse(Integer.parseInt(cursor.getString(0))
            												,cursor.getString(1)
            												,cursor.getString(2)
            												,cursor.getString(3)
            												,cursor.getString(4)
            												,cursor.getString(5)
            												,cursor.getString(6)
            												,cursor.getString(7)
            												,Double.parseDouble(cursor.getString(8))
            												,Double.parseDouble(cursor.getString(9)) 
            												,Integer.parseInt(cursor.getString(10)) );
              
                Courses.add(golfcourses);
            } while (cursor.moveToNext());
        }
  
        return Courses;
    	
    }//get all courses
    
    // this method we return search result
    public ArrayList<GolfCourse> SearchedGolfcourses(String query )
    {
    	open();
    	ArrayList<GolfCourse> Courses = new ArrayList<GolfCourse>();
       
        Cursor cursor = database.rawQuery(query, null);
        GolfCourse golfcourses = null;
        if (cursor.moveToFirst()) {
            do {
              
            	golfcourses = new GolfCourse(Integer.parseInt(cursor.getString(0))
            												,cursor.getString(1)
            												,cursor.getString(2)
            												,cursor.getString(3)
            												,cursor.getString(4)
            												,cursor.getString(5)
            												,cursor.getString(6)
            												,cursor.getString(7)
            												,Double.parseDouble(cursor.getString(8))
            												,Double.parseDouble(cursor.getString(9)) 
            												,Integer.parseInt(cursor.getString(10)) );
              
                Courses.add(golfcourses);
            } while (cursor.moveToNext());
        }
  
        return Courses;
    	
    }
    public void insertfavoritecourse(int courseid)
    {
    	open();
    	//create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        int temp = getplayerID();
        values.put("id_fav", temp+1);
        values.put("id_course", courseid);
        
        database.insert("favoritcourses", // table
                null, //nullColumnHack
                values); // key/value -> keys = column names/ values = column values
        dbhelper.close(); 
    	
    }
   public ArrayList<GolfCourse> getfavoritcourse()
   {
	   open();
   	ArrayList<GolfCourse> Courses = new ArrayList<GolfCourse>();
       String query = "SELECT G.* FROM Golfcourse G  join  favoritcourses f  where G.id_course = f.id_course";
       Cursor cursor = database.rawQuery(query, null);
       GolfCourse golfcourses = null;
       if (cursor.moveToFirst()) {
           do {
             
           	golfcourses = new GolfCourse(Integer.parseInt(cursor.getString(0))
           												,cursor.getString(1)
           												,cursor.getString(2)
           												,cursor.getString(3)
           												,cursor.getString(4)
           												,cursor.getString(5)
           												,cursor.getString(6)
           												,cursor.getString(7)
           												,Double.parseDouble(cursor.getString(8))
           												,Double.parseDouble(cursor.getString(9)) 
           												,Integer.parseInt(cursor.getString(10)) );
             
               Courses.add(golfcourses);
           } while (cursor.moveToNext());
       }
 
       return Courses;
   }
   public GolfCourse GetCourseBYId(int course_id)
   {
   	open();
   
      String query = "SELECT  * FROM Golfcourse WHERE id_course = " + course_id;
       Cursor cursor = database.rawQuery(query, null);
       GolfCourse golfcourse = null;
       if (cursor.moveToFirst()) {
           
             
           	golfcourse = new GolfCourse(Integer.parseInt(cursor.getString(0))
           												,cursor.getString(1)
           												,cursor.getString(2)
           												,cursor.getString(3)
           												,cursor.getString(4)
           												,cursor.getString(5)
           												,cursor.getString(6)
           												,cursor.getString(7)
           												,Double.parseDouble(cursor.getString(8))
           												,Double.parseDouble(cursor.getString(9)) 
           												,Integer.parseInt(cursor.getString(10)) );
           
       }
 
       return golfcourse;
   	
   }
   public Cursor Getholesinfo(int course_id )
   {
   	open();
   
       String query = "select * from holes_info where id_course = " + course_id;
       Cursor cursor = database.rawQuery(query, null);
       
       return cursor;
   	
   }
   public Cursor GetHoleLocationbyid(int Holeid , int course_id)
   {
   	open();
   
       String query = "select * from holes_info where holenumber = " + Holeid +" and id_course ="+course_id;
       Cursor cursor = database.rawQuery(query, null);
       
       return cursor;
   	
   }
public int checkplayers()
{
	open();
	Cursor mCount= database.rawQuery("select count(*) from PLAYER where numberofrounds = 0", null);
	mCount.moveToFirst();
	int count= mCount.getInt(0);
	mCount.close();	
	return count;
}
public int getplayerID()
{
	open();
	Cursor mCount= database.rawQuery("select max(id) from PLAYER", null);
	mCount.moveToFirst();
	int count= mCount.getInt(0);
	mCount.close();	
	return count;
}
public int getRoundid()
{
	open();
	Cursor mCount= database.rawQuery("select max(round_id) from ROUNDS", null);
	mCount.moveToFirst();
	int count= mCount.getInt(0);
	mCount.close();	
	return count;
}
public Cursor Getallplayers()
{
	open();

   String query = "SELECT  * FROM PLAYER  where numberofrounds = 0 " ;
    Cursor cursor = database.rawQuery(query, null);
   
    return cursor;
	
}
public void CreateRound(int courseid)
{
	open();
	//create ContentValues to add key "column"/value
    ContentValues values = new ContentValues();
    
    Cursor curtem = Getallplayers();
    if (curtem.moveToFirst()) {
        do {
        	int temp = getRoundid();
        	values.put("round_id", temp+1);
            values.put("id_course", courseid);
            values.put("id", curtem.getString(0));
            values.put("ended", 0);
            database.insert("ROUNDS", // table
                    null, //nullColumnHack
                    values); // key/value -> keys = column names/ values = column values
           
        } while (curtem.moveToNext());
        
    
    dbhelper.close(); 
	
}
   
}
public void endround()
{
	
	String strSQL = "UPDATE ROUNDS SET ended = 1 WHERE ended = 0";

	database.execSQL(strSQL);	
}

}//class
