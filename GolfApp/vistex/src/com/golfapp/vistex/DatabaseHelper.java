package com.golfapp.vistex;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper{
	
	//Database version 
	private static final int DATABASE_VERSION = 1;
	private static String DB_PATH = "/data/data/com.golfapp.vistex/databases/";
	//Database Name
	private static final String DATABASE_NAME = "GolfCourseDatabase.sqlite";
	 private SQLiteDatabase myDataBase;
	  private static  Context myContext;
	
	
	



	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		this.myContext = context;
	    DB_PATH="/data/data/"+context.getPackageName()+"/"+"databases/";
	   
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

		//create Player table with the db.execSQL command.
		///db.execSQL(CREATE_PLAYER_TABLE);
		//db.execSQL(CREATE_ROUNDS_TABLE);
		//db.execSQL(CREATE_SCORES_TABLE);
		
	}
	 public void createDataBase() throws IOException{
    	 
	    	boolean dbExist = checkDataBase();
	    	 
	    	if(dbExist){
	    	//do nothing - database already exist
	    	}else{
	    	 
	    	//By calling this method and empty database will be created into the default system path
	    	//of your application so we are gonna be able to overwrite that database with our database.
	    	this.getReadableDatabase();
	    	 
	    	try {
	    	 
	    	copyDataBase();
	    	 
	    	} catch (IOException e) {
	    	 
	    	throw new Error("Error copying database");
	    	 
	    	}
	    	}
	    	 
	    	}
	    public void openDataBase() throws SQLException{

	        //Open the database
	        try {
	            String myPath = DB_PATH + DATABASE_NAME;
	            setMyDataBase(SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY+SQLiteDatabase.NO_LOCALIZED_COLLATORS));
	        } catch (Exception e) {
	            // TODO: handle exception
	        }
	    }     

	    @Override
	    public synchronized void close() {
	            if(getMyDataBase() != null)
	                getMyDataBase().close();

	            super.close();
	    } 
	    // Upgrading database
	    private boolean checkDataBase(){
	    	 
	    	SQLiteDatabase checkDB = null;
	    	 
	    	try{
	    	String myPath = DB_PATH + DATABASE_NAME;
	    	checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
	    	 
	    	}catch(SQLiteException e){
	    	 
	    	//database does't exist yet.
	    	 
	    	}
	    	 
	    	if(checkDB != null){
	    	 
	    	checkDB.close();
	    	 
	    	}
	    	 
	    	return checkDB != null ? true : false;
	    	}
	    private void copyDataBase() throws IOException{
	    	 
	    	//Open your local db as the input stream
	    	InputStream myInput = myContext.getAssets().open(DATABASE_NAME );
	    	 
	    	// Path to the just created empty db
	    	String outFileName = DB_PATH + DATABASE_NAME ;
	    	 
	    	//Open the empty db as the output stream
	    	OutputStream myOutput = new FileOutputStream(outFileName);
	    	 
	    	//transfer bytes from the inputfile to the outputfile
	    	byte[] buffer = new byte[1024];
	    	int length;
	    	while ((length = myInput.read(buffer))>0){
	    	myOutput.write(buffer, 0, length);
	    	}
	    	 
	    	//Close the streams
	    	myOutput.flush();
	    	myOutput.close();
	    	myInput.close();
	    	 
	    	}
	    	 
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // Drop older PLAYER table if existed
       // db.execSQL("DROP TABLE IF EXISTS PLAYER");
       // db.execSQL("DROP TABLE IF EXISTS ROUNDS");
       // db.execSQL("DROP TABLE IF EXISTS SCORES");
 
        // create fresh tables table
        //this.onCreate(db);
		
	}

	public SQLiteDatabase getMyDataBase() {
		return myDataBase;
	}

	public void setMyDataBase(SQLiteDatabase myDataBase) {
		this.myDataBase = myDataBase;
	}

	

}
