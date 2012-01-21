package simon.vestergaard.note.calender;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Random;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;



public class NotificationAlarmHandler extends Activity {
	Main mainClass = new Main();
	DatabaseHandler database = new DatabaseHandler(NotificationAlarmHandler.this);
	Context c;
	  private int pYear;
	    private int pMonth;
	    private int pDay;
	    static final int TIME_DIALOG_ID = 1;
	    /** This integer will uniquely define the dialog to be used for displaying date picker.*/
	    static final int DATE_DIALOG_ID = 0;
	public void AlarmSetup(Context c1){
		c =c1;
		showDialog(DATE_DIALOG_ID);
		
	}
	

	//========================================================================date picker and time picker ================================
  
     
    /** Callback received when the user "picks" a date in the dialog */
    private DatePickerDialog.OnDateSetListener pDateSetListener =
            new DatePickerDialog.OnDateSetListener() {
 
                public void onDateSet(DatePicker view, int year, 
                                      int monthOfYear, int dayOfMonth) {
                    pYear = year;
                    pMonth = monthOfYear;
                    pDay = dayOfMonth;
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
            	           
            	            setAlarm();
            	        }

						private void setAlarm() {
							AlarmManager am = (AlarmManager)getSystemService(Activity.ALARM_SERVICE);
							// TODO Auto-generated method stub
							 Calendar cal = Calendar.getInstance();
						 	Log.i("Note", "timePicker");
						 	Log.i("Note", "year = "+pYear);
						 	Log.i("Note", "month = "+pMonth+1);
						 	Log.i("Note", "day = "+pDay);
						 	Log.i("Note", " hour= "+mHour);
						 	Log.i("Note", " minut = "+mMinute);
					 
					     	cal.set(pYear, pMonth, pDay,mHour, mMinute);
						 Log.i("Note", "cal time = " +cal.getTime());
						 
						 
					        Intent intent = new Intent(c, AlarmReceiverActivity.class);
					        Random r = new Random( System.currentTimeMillis() );
						    int requestcode= (1 + r.nextInt(2)) * 10000 + r.nextInt(10000);
						    Bundle hej = new Bundle();
						  hej.putString("category", mainClass.getSelectedCategory());
						  hej.getString("selected_note", ""+NoteSelector.selectedPosition);
						  intent.putExtra("hej", hej);
						    PendingIntent pendingIntent = PendingIntent.getActivity(NotificationAlarmHandler.this,
					            requestcode, intent, PendingIntent.FLAG_CANCEL_CURRENT);
					      
					     
					
					        long curtime = System.currentTimeMillis();
					        long setTime = cal.getTimeInMillis();
					        long timeleft = setTime - curtime;
					        am.set(AlarmManager.RTC_WAKEUP, setTime,
					        		pendingIntent);
					      
					        
					        DateFormat df2 = DateFormat.getDateInstance(DateFormat.SHORT);
					        
					        database.open();
						       String category = mainClass.getSelectedCategory();
						       database.saveRequstCodeForAlarm(category, requestcode);
						      database.saveAlarmTime(category, ""+df2.format(cal.getTime())+ " "+mHour+":"+mMinute );
						       database.close();
					        
					        Log.i("Note", "curtime="+curtime);
					        Log.i("Note", "timeleft="+timeleft);
					        Log.i("note", "settime="+setTime);
					
					       
					        
					      
					     
					     Toast.makeText(c, df2.format(cal.getTime())+ " "+mHour+":"+mMinute , Toast.LENGTH_LONG).show();   
						}    	       
            	    };
            	    
            	    
            	    
            	    @Override
			protected Dialog onCreateDialog(int id, Bundle args) {
				// TODO Auto-generated method stub
            	    	final Calendar cal = Calendar.getInstance();
            	        pYear = cal.get(Calendar.YEAR);
            	        pMonth = cal.get(Calendar.MONTH);
            	        pDay = cal.get(Calendar.DAY_OF_MONTH);
            	    	 switch (id) {
             	        case DATE_DIALOG_ID:
             	        
             	            return new DatePickerDialog(c, 
             	                        pDateSetListener,
             	                        pYear, pMonth, pDay);
             	            
             	        case TIME_DIALOG_ID:
             	        	
             	            return new TimePickerDialog(c,
             	                    mTimeSetListener, mHour, mMinute, true);
             	        }
             	        	
             	        
             	        return null;
			}



            	  
	//========================================================================date picker and time picker ================================
	

}
	
