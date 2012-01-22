package simon.vestergaard.note.calender;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Random;

import simon.vestergaard.note.calender.R.drawable;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;




public class Note extends Activity implements OnClickListener,OnCheckedChangeListener {
	int Selected_Note_rowid;
	String Selected_category;
	
	//========================================================================date picker and time picker ================================
    private int pYear;
    private int pMonth;
    private int pDay;
    static final int TIME_DIALOG_ID = 1;
    static final int Dialog_add_selector=2;
    static final int Dialog_import_database = 3;
    
    /** This integer will uniquely define the dialog to be used for displaying date picker.*/
    static final int DATE_DIALOG_ID = 0;
     
    /** Callback received when the user "picks" a date in the dialog */
    private DatePickerDialog.OnDateSetListener pDateSetListener =
            new DatePickerDialog.OnDateSetListener() {
 
                public void onDateSet(DatePicker view, int year, 
                                      int monthOfYear, int dayOfMonth) {
                    pYear = year;
                    pMonth = monthOfYear;
                    pDay = dayOfMonth;

                   savedata();
                    showDialog(TIME_DIALOG_ID);
                   
                }
              
            };
     //================================================time picker stuff
            private TextView mTimeDisplay;
            private Button mPickTime;

            private int mHour;
            private int mMinute;
            

            private TimePickerDialog.OnTimeSetListener mTimeSetListener =
            	    new TimePickerDialog.OnTimeSetListener() {
            	        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            	            mHour = hourOfDay;
            	            mMinute = minute;
            	           
            	            setNotification();
            	        }

						private void setNotification() {
						
							// TODO Auto-generated method stub
							 Calendar cal = Calendar.getInstance();

					     	cal.set(pYear, pMonth, pDay,mHour, mMinute);
						
					     	Intent intent = new Intent(Note.this, AlarmReceiverActivity.class);
					        Random r = new Random( System.currentTimeMillis() );
						    int requestcode= (1 + r.nextInt(2)) * 10000 + r.nextInt(10000);
				
						 PendingIntent pendingIntent = PendingIntent.getActivity(Note.this,
					            requestcode, intent, PendingIntent.FLAG_CANCEL_CURRENT);
					      
					     
						    long curtime = System.currentTimeMillis();
					        long setTime = cal.getTimeInMillis();
					        
					        long timeleft = setTime - curtime;
					        
				        
					        DateFormat df2 = DateFormat.getDateInstance(DateFormat.SHORT);
					       	       
					        database.open();
					    	String data = database.getNoteData(Selected_category, Selected_Note_rowid);
					    	
					    	createNotification(requestcode, "title", "body", pendingIntent, intent, Selected_category, Selected_Note_rowid, data,cal);
					    	 
					       
						       database.saveRequstCodeForAlarm(Selected_category, requestcode);
						      database.saveAlarmTime(Selected_category, ""+df2.format(cal.getTime())+ " "+mHour+":"+mMinute );
						       database.close();
					        
					        Log.i("Note", "curtime="+curtime);
					        Log.i("Note", "timeleft="+timeleft);
					        Log.i("note", "settime="+setTime);
					
					       
					        
					      
					     
					     Toast.makeText(Note.this, df2.format(cal.getTime())+ " "+mHour+":"+mMinute , Toast.LENGTH_LONG).show();   
						}    	       
            	    };
	
            	  
	//========================================================================date picker and time picker ================================
	
	
	
	
	Button Bedit,Bback;
	EditText EditTextData;
	CheckBox cb1,cb2,cb3,cb4;
	
	TextView TVnoteName,TVcategory;
	 Main mainClass = new Main();
	 NoteSelector NoteSelectorClass = new NoteSelector();
	// LinearLayout background;
	 //RelativeLayout background2;
	DatabaseHandler database = new DatabaseHandler(Note.this);
	boolean editing = true;
	String data;
	private static final String KEY_PREFERANCES_THEMES="themess";
	NotificationAlarmHandler Notificationalarmhandler = new NotificationAlarmHandler();
	@Override
	protected void onCreate(Bundle savedInstanceState) {		//==================================on Create================================================
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.notescreen);
		
		
		initilize();

		
	}
	@Override
	public void onBackPressed(){
	    finish();
	    overridePendingTransition(R.anim.push_left_in,R.anim.push_left_out );
	}


	public int whatThemTouse(){
			final String PREFS_NAME = "MyPrefsFile";
			 SharedPreferences.Editor SettingsEditor = null;
			 SharedPreferences settings = null;
			 settings = getSharedPreferences(PREFS_NAME, 0);
			    SettingsEditor = settings.edit();
			drawable hej = new drawable();
			int hej2 = hej.them_blue_black;
			
			if(settings.getInt(KEY_PREFERANCES_THEMES, 0)==1){
				hej2 =hej.them_green_black;	
			}else if(settings.getInt(KEY_PREFERANCES_THEMES, 0)==2){
				hej2 =hej.them_pink_black;
			}else if(settings.getInt(KEY_PREFERANCES_THEMES, 0)==3){
				hej2 =hej.them_red_black;
			}else if(settings.getInt(KEY_PREFERANCES_THEMES, 0)==4){
				hej2 =hej.them_yellow_black;
			}else if(settings.getInt(KEY_PREFERANCES_THEMES, 0)==5){
				hej2 =hej.them_orange_black;
			}else if(settings.getInt(KEY_PREFERANCES_THEMES, 0)==7){
				hej2 =hej.them_black_black;
			}
				
			
			return hej2;
			
		}
	public void setActivityBackgroundColor(int i) {
	    View view = this.getWindow().getDecorView();
	    view.setBackgroundResource(i);
	}

	private void initilize() {
		// TODO Auto-generated method stub
		Selected_category = getIntent().getStringExtra("category");
		
		Selected_Note_rowid= getIntent().getIntExtra("selectedPosistion", 0);
		
		
		//Bback =(Button)findViewById(R.id.Bback);
	//	Bedit =(Button)findViewById(R.id.Bedit);
		EditTextData =(EditText)findViewById(R.id.ETdatatext);
		TVnoteName =(TextView) findViewById(R.id.TVnote);
		TVcategory =(TextView)findViewById(R.id.TVcategory);
	//	background =(LinearLayout)findViewById(R.id.backgroudNoteScreen1);
		//background2=(RelativeLayout)findViewById(R.id.backgroudNoteScreen2);
		//background2.setBackgroundColor(whatThemTouse());
		//background.setBackgroundColor(whatThemTouse());
		setActivityBackgroundColor(whatThemTouse());
		cb1 =(CheckBox)findViewById(R.id.checkBox1);
		cb2 =(CheckBox)findViewById(R.id.checkBox2);
		cb3 =(CheckBox)findViewById(R.id.checkBox3);
		cb4 =(CheckBox)findViewById(R.id.checkBox4);
		
		CheckIfCheckBoxesAreChecked();
		
		cb1.setOnCheckedChangeListener(this);
		cb2.setOnCheckedChangeListener(this);
		cb3.setOnCheckedChangeListener(this);
		cb4.setOnCheckedChangeListener(this);
		
	//	Bback.setOnClickListener(this);
	//	Bedit.setOnClickListener(this);
		
		//NoteSelectorClass.listDataNote.get(NoteSelectorClass.getSelectedPosition());
		
		TVnoteName.setText("Note:"+NoteSelector.noteName);
		TVcategory.setText("Category :"+mainClass.CategoryName);
		
		LoadDataFromDatabase();
		
		 final Calendar cal = Calendar.getInstance();
	        pYear = cal.get(Calendar.YEAR);
	        pMonth = cal.get(Calendar.MONTH);
	        pDay = cal.get(Calendar.DAY_OF_MONTH);
	        mMinute = cal.get(Calendar.MINUTE);
	        mHour = cal.get(Calendar.HOUR_OF_DAY);
	        
	}

	private void CheckIfCheckBoxesAreChecked() {
		// TODO Auto-generated method stub
	database.open();
		int code =database.getAlarmSetState(Selected_category);
		database.close();	
		
		if(code ==1){
			Log.i("Note", "CheckIfCheckBoxesAreChecked set :: code ="+code);
			cb1.setChecked(true);
			cb1.setText(R.string.CancelCountDownAlarm);
			
		}else if (code ==0){
			Log.i("Note", "CheckIfCheckBoxesAreChecked not set :: code ="+code);
			cb1.setChecked(false);
			
			
		}else{
			Log.i("Note", "CheckIfCheckBoxesAreChecked error code id ="+code);
		}
		
		code = 25;
	}
	

	private void LoadDataFromDatabase() {
		// TODO Auto-generated method stub
		database.open();
		data =database.getNoteData(Selected_category, NoteSelectorClass.getSelectedPosition() );
		database.close();
		if(data.equals(null)){
			
		}else{
		EditTextData.setText(data);
		}
		
		
		
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		savedata();
		super.onPause();
	}
	

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		savedata();
		super.onRestart();
	}
	

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		savedata();
		super.onDestroy();
	}

	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.Bback:
			Intent i = new Intent("simon.vestergaard.note.calender.NoteSelector");
			savedata();
			startActivity(i);
			Log.i("state", "save don");
			break;
		case R.id.Bedit:
			Log.i("Note", "Bedit was clikked ");

				if(editing){
			
			setContentView(R.layout.notescreenediting);
			editing = false;
			
		}else{
			
			setContentView(R.layout.notescreen);
			savedata();
			editing = true;
		}
				initilize();
			break;
		}
		
		
	}


	private void savedata() {
		// TODO Auto-generated method stub
		database.open();
		String data = EditTextData.getText().toString();
		database.saveNoteData(Selected_category, data);
		data= null;
		database.close();
	
		
	}

	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		// TODO Auto-generated method stub
		switch (buttonView.getId()){
		case R.id.checkBox1:
			 AlarmManager am1 = (AlarmManager)getSystemService(Activity.ALARM_SERVICE);
			
	if(isChecked){
		
		showDialog(DATE_DIALOG_ID);
	
cb1.setTag(this.getString(R.string.CancelCountDownAlarm));
	      
	}else{
		String ns = Context.NOTIFICATION_SERVICE;
		 Intent intent = new Intent(this, NotificationActivity.class);
		 NotificationManager mNotificationManager = (NotificationManager) getSystemService(ns);
		 database.open();
		 int requestcode =  (database.getReqeustcodeFromNote(Selected_category));
		 database.AlarmIsCanceled(Selected_category);
		 database.close();
		 
		  PendingIntent pendingIntent = PendingIntent.getActivity(this,
		            requestcode, intent, PendingIntent.FLAG_CANCEL_CURRENT);
		  mNotificationManager.cancel(requestcode);
		        am1.cancel(pendingIntent);
				 Toast.makeText(this, "Alarm Cancel", Toast.LENGTH_LONG).show();    
		
				 cb1.setText(this.getString(R.string.countDownAlarm));
	       }
	          
			 
			break;
		case R.id.checkBox2:
			Calendar cal = Calendar.getInstance();       
			savedata();
			Intent intent = new Intent(Intent.ACTION_EDIT);
			intent.setType("vnd.android.cursor.item/event");
			intent.putExtra("beginTime", cal.getTimeInMillis());
			//intent.putExtra("allDay", true);
		//	intent.putExtra("rrule", "FREQ=YEARLY");
			intent.putExtra("endTime", cal.getTimeInMillis()+60*60*1000);
			intent.putExtra("title", ""+R.string.app_name);
			intent.putExtra("Description",data  );
			database.close();
			startActivity(intent);
			break;
		case R.id.checkBox3:
			break;
		case R.id.checkBox4:
			finish();
			break;
		
		}
		
	}


	private static void addToCalendar(Context ctx, final String title, final long dtstart, final long dtend) {
	    final ContentResolver cr = ctx.getContentResolver();
	    Cursor cursor ;
	    if (Integer.parseInt(Build.VERSION.SDK) == 8 )
	        cursor = cr.query(Uri.parse("content://com.android.calendar/calendars"), new String[]{ "_id", "displayname" }, null, null, null);
	    else
	        cursor = cr.query(Uri.parse("content://calendar/calendars"), new String[]{ "_id", "displayname" }, null, null, null);
	    if ( cursor.moveToFirst()  ) {
	        final String[] calNames = new String[cursor.getCount()];
	        final int[] calIds = new int[cursor.getCount()];
	        for (int i = 0; i < calNames.length; i++) {
	            calIds[i] = cursor.getInt(0);
	            calNames[i] = cursor.getString(1);
	            cursor.moveToNext();
	        }
	 
	        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
	        builder.setSingleChoiceItems(calNames, -1, new DialogInterface.OnClickListener() {
	 
	            public void onClick(DialogInterface dialog, int which) {
	                ContentValues cv = new ContentValues();
	                cv.put("calendar_id", calIds[which]);
	                cv.put("title", title);
	                cv.put("dtstart", dtstart );
	                cv.put("hasAlarm", 1);
	                cv.put("dtend", dtend);
	 
	                Uri newEvent ;
	                if (Integer.parseInt(Build.VERSION.SDK) == 8 )
	                    newEvent = cr.insert(Uri.parse("content://com.android.calendar/events"), cv);
	                else
	                    newEvent = cr.insert(Uri.parse("content://com.android.calendar/events"), cv);
	 
	                if (newEvent != null) {
	                    long id = Long.parseLong( newEvent.getLastPathSegment() );
	                    ContentValues values = new ContentValues();
	                    values.put( "event_id", id );
	                    values.put( "method", 1 );
	                    values.put( "minutes", 15 ); // 15 minuti
	                    if (Integer.parseInt(Build.VERSION.SDK) == 8 )
	                        cr.insert( Uri.parse( "content://com.android.calendar/reminders" ), values );
	                    else
	                        cr.insert( Uri.parse( "content://calendar/reminders" ), values );
	 
	                }
	                dialog.cancel();
	            }
	 
	        });
	 
	        builder.create().show();
	    }
	    cursor.close();
	}
	
	
	void createNotification(int requestcode,String MessageTitle,String MessageBody
				,PendingIntent pendingintent1,Intent intent1,String category,int Noteposisition,String data,Calendar cal){
	
		
		 
		
		Intent intent = new Intent(this, AlarmReceiverActivity.class);
		intent.putExtra("category", Main.CategoryName);
		intent.putExtra("NotePosisition", NoteSelector.selectedPosition);
		intent.putExtra("RequestCode", requestcode);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
            requestcode, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager am = 
            (AlarmManager)getSystemService(Activity.ALARM_SERVICE);
        am.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(),
                pendingIntent);
	}
	 public void AddCategory(){

			
			
			// TODO Auto-generated method stub
			AlertDialog.Builder alert = new AlertDialog.Builder(this);

			alert.setTitle("catagory");
			alert.setMessage("pleas enter a title for youre catagory");

			// Set an EditText view to get user input
			final EditText input = new EditText(this);
			alert.setView(input);

			alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
			 String value = input.getText().toString();
			  // Do something with value!
			 database.open();
			 database.CreateCategory(""+value);
			database.close();
				value=null;
			
			  }
			});

			alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			  public void onClick(DialogInterface dialog, int whichButton) {
			    // Canceled.
			  }
			});
			
			alert.show();
		
	 }
	 public void AddNote(){

			
			// TODO Auto-generated method stub
			AlertDialog.Builder alert = new AlertDialog.Builder(this);

			alert.setTitle("New Note");
			alert.setMessage("pleas enter a Name for youre New Note");

			// Set an EditText view to get user input
			final EditText input = new EditText(this);
			alert.setView(input);

			alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
			 String value = input.getText().toString();
			  // Do something with value!
			 database.open();
			 database.CreateNote(value, ""+mainClass.dataFromPosition);
			database.close();
				value=null;
				
			  }
			});

			alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			  public void onClick(DialogInterface dialog, int whichButton) {
			    // Canceled.
			  }
			});

			alert.show();
	 }
	 /** Create a new dialog for date picker */
    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
        case DATE_DIALOG_ID:
        
            return new DatePickerDialog(this, 
                        pDateSetListener,
                        pYear, pMonth, pDay);
            
        case TIME_DIALOG_ID:
        	
            return new TimePickerDialog(this,
                    mTimeSetListener, mHour, mMinute, true);
            
        case Dialog_add_selector:
        	return new AlertDialog.Builder(Note.this)
        	.setTitle("What to Add")
        	.setPositiveButton("Add category", new DialogInterface.OnClickListener() {
				
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					Log.i("Main","positive Button In dialog Box");
					AddCategory();
				}
			})
        	.setNegativeButton("Add Note", new DialogInterface.OnClickListener() {
				
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					AddNote();
					
				}
			})
			.setMessage("Select what to add")
			.create();
 
        case Dialog_import_database:
        	return new AlertDialog.Builder(Note.this)
        	.setTitle(R.string.ImportDatabaseDialogTitle)
        	/*.setPositiveButton("OK", new DialogInterface.OnClickListener() {
				
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					Log.i("Main","positive Button In dialog Box");
					
				}
			})*/
        	.setNegativeButton("Cancel", null)
			.setMessage(R.string.ImportDatabaseMessagePart1)
			.create();
        	
        
      
        }
		return null;
    }
    @Override
	public boolean onCreateOptionsMenu(android.view.Menu menu) {
		// TODO Auto-generated method stub
				super.onCreateOptionsMenu(menu);
				MenuInflater blowUp = getMenuInflater();
				blowUp.inflate(R.menu.menu_for_note_screen, menu);
				return true;

		
	
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()){
		case R.id.TopBarEdit:
			Log.i("Note", "Bedit was clikked ");

			if(editing){
		
		setContentView(R.layout.notescreenediting);
		
		editing = false;
		
	}else{
		
		setContentView(R.layout.notescreen);
		savedata();
		editing = true;
	}
			initilize();
			break;
		case R.id.topbarADD:
			showDialog(Dialog_add_selector);
			break;
		case R.id.MenuExportDatabase:
			showDialog(Dialog_import_database);
			break;
		case R.id.MenuImportDatabase:
		showDialog(Dialog_import_database);
			
			break;
		case R.id.preferences:
			startActivity(new Intent(this,Preferances.class));
			overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
		
			break;
		case R.id.TopBarBack:
			 finish();
			 startActivity(new Intent(this,NoteSelector.class));
				overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
			
			break;
		
		
		}
		return false;
	}
}
