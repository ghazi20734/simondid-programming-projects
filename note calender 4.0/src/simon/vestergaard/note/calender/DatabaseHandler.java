package simon.vestergaard.note.calender;

import java.util.ArrayList;

import android.R.integer;
import android.R.menu;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.util.Log;

public class DatabaseHandler {
	
	
	
	
	public static final String KEY_ROWID = "_id";
	
	//create catogory KEYs
	private static final String KEY_catagory ="category";
	private static final String KEY_category_name = "categoryName";
	
	//create note KEYs
	private static final String KEY_note ="note";
	private static final String KEY_note_name="noteName";
	private static final String KEY_note_data="noteData";
	private static final String KEY_note_options="noteOptions";
	private static final String KEY_note_belong_to_category="noteBelongToCategory";
	
	private static final String KEY_TIME_SINS_MODIFIED="TimeSinsModified";
	private static final String KEY_ALARM_REQUEST_CODE="AlarmRequstCODE";
	private static final String KEY_ALARM_DATA_AND_TIME="AlarmDataAndTime";
	private static final String KEY_IS_ALARM_SET="IsAlarmSet";
	
	private static final String DATABASE_NAME = "NoteApp";
	private static final String DATABASE_TABLE = "mainTable";
	private static final String DATABASE_TABLE_categorys ="tableCategorys";
	private static final int DATABASE_VERSION = 21;

	
	private DbHelper ourHelper;
	private final Context ourContext;
	private static Context StaticourContext;
	private SQLiteDatabase ourDatabase;
	
	String SelectedNoterowid;
	String SelectedCategoryRowId;
	static ProgressDialog pbar;
	private static class DbHelper extends SQLiteOpenHelper{

		public DbHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
			
			// TODO Auto-generated constructor stub
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			
			db.execSQL("CREATE TABLE " + DATABASE_TABLE + " (" +
					KEY_ROWID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
					KEY_catagory + " TEXT, " +
					KEY_category_name + " TEXT, "+
					KEY_note + " TEXT, " +
					KEY_note_name + " TEXT, "+
					KEY_note_data + " TEXT, " +
					KEY_note_options + " TEXT, "+
					KEY_note_belong_to_category + " TEXT);"
					
			);	
			
			db.execSQL("CREATE TABLE " + DATABASE_TABLE_categorys + " (" +
					KEY_ROWID +  " INTEGER PRIMARY KEY AUTOINCREMENT, " +
					KEY_catagory + " TEXT, " +
					KEY_category_name + " TEXT, "+
					KEY_TIME_SINS_MODIFIED +" TEXT, "+
					KEY_ALARM_REQUEST_CODE +" INTEGER, "+
					KEY_ALARM_DATA_AND_TIME +" TEXT);"
					);
		}
		
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)  {
			// TODO Auto-generated method stub
			Log.i("DatabaseHandler", "onUpgrade is getting runed");
			
			/**
			 * når man upgrader databasen add man bare de colums man skal bruge 
			 * 	db.execSQL("ALTER TABLE " + table name  + " ADD COLUMN "+" colum name "+ "TEXT"+";");
			 * 
			 */
		
		//	db.execSQL("ALTER TABLE " + DATABASE_TABLE_categorys.toString() + " ADD COLUMN "+""+ "TEXT"+";");
			
				}		
				
				
			
			//======================private update database====================================
			//======================privat update=================================
	}
				

		
	
	
	public DatabaseHandler(Context c){
		ourContext = c;
		StaticourContext =c;
	}

	public DatabaseHandler open() throws SQLException{
		ourHelper = new DbHelper(ourContext);
		ourDatabase = ourHelper.getWritableDatabase();
		return this;
	}
	 public void close(){
		 ourHelper.close();
	 }
	 //======================private update database====================================
	 public void updateDatabase() {
			// TODO Auto-generated method stub
		 String[] columns = new String[]{ KEY_ROWID, KEY_catagory, KEY_category_name};
			Cursor c = ourDatabase.query(DATABASE_TABLE_categorys, columns, null, null, null, null, null);
			String result = "";
			ArrayList<String> test = new ArrayList<String>();
			
			int iRow = c.getColumnIndex(KEY_ROWID);
			int iName = c.getColumnIndex(KEY_category_name);
			int iHotness = c.getColumnIndex(KEY_catagory);
			
			for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
				result = c.getString(iName) + "\n";
				test.add(result);
			}
		}		
	//======================private update database====================================
	 public long CreateCategory(String categoryName) {
			// TODO Auto-generated method stub
		
			ContentValues cv = new ContentValues();
		cv.clear();
			cv.put(KEY_category_name, categoryName);
			cv.put(KEY_catagory, KEY_catagory);
			
			ourDatabase.execSQL("DROP TABLE IF EXISTS " + categoryName);
			ourDatabase.execSQL("CREATE TABLE " + categoryName + " (" +
					KEY_ROWID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
					KEY_note + " TEXT, " +
					KEY_note_name + " TEXT, "+
					KEY_note_data + " TEXT, " +
					KEY_note_options + " TEXT, "+
					KEY_note_belong_to_category + " TEXT, "+
					KEY_IS_ALARM_SET +" INTEGER, "+
					KEY_ALARM_REQUEST_CODE +" INTEGER, "+
					KEY_ALARM_DATA_AND_TIME +" TEXT);"
					
			);	
			return ourDatabase.insert(DATABASE_TABLE_categorys, null, cv);
			
		}
	 public long CreateNote(String NoteName,String selected_category) {
			// TODO Auto-generated method stub
		 
		 
		
			ContentValues cv = new ContentValues();
			cv.clear();
			cv.put(KEY_note_name, NoteName);
			cv.put(KEY_note_belong_to_category, selected_category);
			
			
			return ourDatabase.insert(selected_category, null, cv);
		}
		public ArrayList<String> getCategorys() {
			// TODO Auto-generated method stub
			String[] columns = new String[]{ KEY_ROWID, KEY_catagory, KEY_category_name};
			Cursor c = ourDatabase.query(DATABASE_TABLE_categorys, columns, null, null, null, null, null);
			String result = "";
			int result2;
			ArrayList<String> test = new ArrayList<String>();
			ArrayList<String> testrows = new ArrayList<String>();
			int iRow = c.getColumnIndex(KEY_ROWID);
			int iName = c.getColumnIndex(KEY_category_name);
			int iHotness = c.getColumnIndex(KEY_catagory);
			
			for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
				result = c.getString(iName) + "\n";
				test.add(result);
				
			}
		
			
			
			return test;
		}
		public ArrayList<Integer> getCategorysRowIDs() {
			String[] columns = new String[]{ KEY_ROWID, KEY_catagory, KEY_category_name};
			Cursor c = ourDatabase.query(DATABASE_TABLE_categorys, columns, null, null, null, null, null);
			int result2;
			
			ArrayList<Integer> testrows = new ArrayList<Integer>();
			int iRow = c.getColumnIndex(KEY_ROWID);
			
			for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
				
				result2 = c.getInt(iRow);
				testrows.add(result2);
			}
		
			
			
			return testrows;
		}
		public ArrayList<Integer> getNoteRowIds(String Table) {
			String[] columns = new String[]{ KEY_ROWID};
			Cursor c = ourDatabase.query(Table, columns, null, null, null, null, null);
			int result2;
			
			ArrayList<Integer> testrows = new ArrayList<Integer>();
			int iRow = c.getColumnIndex(KEY_ROWID);
			
			for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
				
				result2 = c.getInt(iRow);
				testrows.add(result2);
			}
		
			
			
			return testrows;
		}
		
		public String getDatabasePath(){
			String path =ourContext.getDatabasePath(DATABASE_NAME).toString();
			return path;
		}
		public ArrayList<String> getNotes(String selected_category) {
			// TODO Auto-generated method stub
			String[] columns = new String[]{ KEY_ROWID, KEY_note_name, KEY_note_belong_to_category};
			Cursor c1 = ourDatabase.query(selected_category, columns, null, null, null, null, null);
			
			String result = "";
			ArrayList<String> test = new ArrayList<String>();
			
			int iRow = c1.getColumnIndex(KEY_ROWID);
			int iName = c1.getColumnIndex(KEY_note_name);
			int iHotness = c1.getColumnIndex(KEY_note_belong_to_category);
			
			for (c1.moveToFirst(); !c1.isAfterLast(); c1.moveToNext()){
				result = c1.getString(iName) + "\n";
				test.add(result);
			}
			
			return test;
		}

		public String getNoteData(String selected_category,int SelectedPosition){
			String[] columns = new String[]{ KEY_ROWID, KEY_note_data};
			Cursor c1 = ourDatabase.query(selected_category, columns, null, null, null, null, null);
			String result;
			ArrayList<String> test = new ArrayList<String>();
			int iName = c1.getColumnIndex(KEY_note_data);
			int iRowID = c1.getColumnIndex(KEY_ROWID);
			
				c1.moveToPosition(SelectedPosition);
				
				result = c1.getString(iName);
				if(result==null){
					result="";
				}
				SelectedNoterowid = c1.getString(iRowID)+"\n";
				
			return result;
			
		}
		public void saveNoteData(String selected_category,String data){

			
			ContentValues args = new ContentValues();
			args.put(KEY_note_data, data);
			
		
			int testss =ourDatabase.update(selected_category, args, KEY_ROWID+"="+SelectedNoterowid, null);
			
			Log.i("DatabaseHandler", "category = "+selected_category +" Data ="+ data +"  rowid = "+ SelectedNoterowid+" behanled rows ="+testss);
			
		}
	
		public int getNumberOFFNotes(String selected_category ){
			String[] columns = new String[]{ KEY_note_name};
			Cursor c = ourDatabase.query(selected_category, columns, null, null, null, null, null);
			int result = 0;
			
			
			int iName = c.getColumnIndex(KEY_note_name);
			
			
			for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
				String hej =c.getString(iName) +"\n";
				result++;
			}
			
			return result;
			
		}
		public void deleteCategory(int Rowid,String tableName){
			ourDatabase.delete(DATABASE_TABLE_categorys, KEY_ROWID + "=" + Rowid, null);
			ourDatabase.execSQL("DROP TABLE IF EXISTS "+tableName);
		
		}
		public void deleteNote(int Rowid,String categoryName){
			ourDatabase.delete(categoryName, KEY_ROWID + "=" + Rowid, null);
		}
		public void saveRequstCodeForAlarm(String selected_category,int requestCode){
			ContentValues args = new ContentValues();
			args.put(KEY_ALARM_REQUEST_CODE, requestCode);
			int testss =ourDatabase.update(selected_category, args, KEY_ROWID+"="+SelectedNoterowid, null);
			Log.i("DatabaseHandler", "category = "+selected_category +" requestCode set to ="+ requestCode +"  rowid = "+ SelectedNoterowid+" behanled rows ="+testss);
			AlarmIsSet(selected_category);
		}
		public void saveAlarmTime(String selected_category, String string){
			ContentValues args = new ContentValues();
			args.put(KEY_ALARM_DATA_AND_TIME, string);
			ourDatabase.update(selected_category, args, KEY_ROWID+"="+SelectedNoterowid, null);
		}
		public ArrayList<String> getAlarmTime(String selected_category){
			String[] columns = new String[]{KEY_ALARM_DATA_AND_TIME};
			Cursor c = ourDatabase.query(selected_category, columns, null, null, null, null, null);
			String result = null;
			ArrayList<String> test = new ArrayList<String>();
			
			int iName = c.getColumnIndex(KEY_ALARM_DATA_AND_TIME);
		
			for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
			
				result = c.getString(iName);
				
				 String hej = c.getString(iName)+"/N";
				
				 
				if(result==null){
					result =" ";
					Log.i("DatabaseHandler", "getAlarmTime just catched an exception");
				}else{
					result = "Alarm set to : " +result;
				}
				
				
				 test.add(result);
				 
			 }
		
			Log.i("DatabaseHandler", "alarmtime is's ="+test);
			return test;
			
		}
		public void AlarmIsSet(String selected_category){
			ContentValues args = new ContentValues();
			args.put(KEY_IS_ALARM_SET, 1);
			ourDatabase.update(selected_category, args, KEY_ROWID+"="+SelectedNoterowid, null);
			Log.i("DatabaseHandler", "Alarm is set ");
			
		}
		public void AlarmIsCanceled(String selected_category){
			ContentValues args = new ContentValues();
			args.put(KEY_IS_ALARM_SET, 0);
			ourDatabase.update(selected_category, args, KEY_ROWID+"="+SelectedNoterowid, null);
			Log.i("DatabaseHandler", "Alarm is cancled ");
		}
		public int getAlarmSetState(String selected_category){
			
			String[] columns = new String[]{KEY_IS_ALARM_SET};
			Cursor c = ourDatabase.query(selected_category, columns, null, null, null, null, null);
			int result = 25;
			ArrayList<String> test = new ArrayList<String>();
			
			int iName = c.getColumnIndex(KEY_IS_ALARM_SET);
			int what = NoteSelector.selectedPosition+1;
			for (c.moveToFirst(); !c.isAfterLast(); c.move(what)){
				 result = c.getInt(iName);
				 
			 }
		
			
			return result;
		}
		public int getReqeustcodeFromNote(String selected_category){
			String[] columns = new String[]{KEY_ALARM_REQUEST_CODE};
			Cursor c = ourDatabase.query(selected_category, columns, null, null, null, null, null);
			int result = 0;
			ArrayList<String> test = new ArrayList<String>();
			
			int iName = c.getColumnIndex(KEY_ALARM_REQUEST_CODE);
			
			 while(c.moveToNext()){
				 result = c.getInt(iName);
				 
			 }
		
			
			return result;
			
		}
		
}
