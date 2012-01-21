package simon.vestergaard.note.calender;

import java.util.Random;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;


public class AlarmReceiverActivity extends Activity {
   
     DatabaseHandler database = new DatabaseHandler(AlarmReceiverActivity.this);
     String extraDataCategory;
     int extraDataNotePosistion;
     int extraDataRequestCode;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getExtraData(); 
	String ns = Context.NOTIFICATION_SERVICE;
    NotificationManager mNotificationManager = (NotificationManager) getSystemService(ns);
   
    int icon = R.drawable.ic_launcher;
    CharSequence tickerText = this.getString(R.string.appname);
  
    Random r = new Random( System.currentTimeMillis() );
    int requestcode= (1 + r.nextInt(2)) * 10000 + r.nextInt(10000);
    
    Notification notification = new Notification(icon, tickerText, System.currentTimeMillis());
 
    Context context = getApplicationContext();
    CharSequence contentTitle = this.getString(R.string.app_name);
    database.open();
    CharSequence contentText = this.getString(R.string.app_name)+" "+this.getString(R.string.notificationConText);
    database.close();
    Intent notificationIntent = new Intent(this, NotificationActivity.class);
   notificationIntent.putExtra("category", extraDataCategory);
   notificationIntent.putExtra("NotePosisition", extraDataNotePosistion);
    PendingIntent contentIntent = PendingIntent.getActivity(AlarmReceiverActivity.this, requestcode, notificationIntent, 0);
    
    notification.setLatestEventInfo(context, contentTitle, contentText, contentIntent);

    mNotificationManager.notify(requestcode, notification);
    }
 
    private void getExtraData() {
		// TODO Auto-generated method stub
    	Intent sender=getIntent();
        extraDataCategory=sender.getExtras().getString("category");
        extraDataNotePosistion=sender.getExtras().getInt("NotePosisition");
        extraDataRequestCode=sender.getExtras().getInt("RequestCode");
	}

	
      
}