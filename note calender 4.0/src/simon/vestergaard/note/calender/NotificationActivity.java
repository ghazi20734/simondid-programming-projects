package simon.vestergaard.note.calender;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class NotificationActivity extends Activity implements OnClickListener{
		final static int Dialog_Notification_id=1;
		Button BshowME,Bcancel;
		TextView tv;
		 String extraDataCategory;
	     int extraDataNotePosistion;
	     int extraDataRequestCode;
		DatabaseHandler database = new DatabaseHandler(this);
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		createDialog();
		initilize();
		
	}

	private void createDialog() {
		// TODO Auto-generated method stub
        //set up dialog
    /*    Dialog dialog = new Dialog(NotificationActivity.this);
        dialog.setContentView(R.layout.notificationdialog);
        dialog.setTitle(this.getString(R.string.app_name));
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setOnCancelListener(new )
        //there are a lot of settings, for dialog, check them all out!

        //set up text
        TextView text = (TextView) dialog.findViewById(R.id.TextView01);
        Log.i("NotificationActivity", "setting up text");
        database.open();
        text.setText(database.getNoteData(getIntent().getStringExtra("category"), getIntent().getIntExtra("NotePosisition", -1)));
        database.AlarmIsCanceled(getIntent().getStringExtra("category"));
database.close();
        //set up image view
        ImageView img = (ImageView) dialog.findViewById(R.id.ImageView01);
        img.setImageResource(R.drawable.ic_launcher);

        //set up button
        Button button = (Button) dialog.findViewById(R.id.Button01);
        button.setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
                finish();
            }
        });
        //now that the dialog is set up, it's time to show it    
        dialog.show();
        */
        showDialog(Dialog_Notification_id);
    }
	private void makeAlarmCancled() {
		// TODO Auto-generated method stub

		database.open();
		database.AlarmIsCanceled(getIntent().getStringExtra("category"));
		database.close();

		
	}
	private void initilize() {
	
	}
	private void startNote() {
		// TODO Auto-generated method stub
		
		startActivity(new Intent(this,Note.class).putExtra("category",getIntent().getStringExtra("category")).putExtra("selectedPosistion",getIntent().getIntExtra("NotePosisition", -1)));
		overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
	}
	   protected Dialog onCreateDialog(int id) {
	        switch (id) {
	        case Dialog_Notification_id:
	        	return new AlertDialog.Builder(NotificationActivity.this)
	        	.setPositiveButton(this.getString(R.string.showME), new DialogInterface.OnClickListener() {
					
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						makeAlarmCancled();
						startNote();
						
					}

					
					
				})
				.setNegativeButton(this.getString(R.string.DontShowMe),new DialogInterface.OnClickListener() {
					
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						
					}
				})
				.setTitle(R.string.app_name)
				.setIcon(R.drawable.ic_launcher)
				.show();
				
		
		}
			return null;
	}

	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		switch(v.getId()){
		case R.id.button1:
			Intent i = new Intent(this, Note.class);
			Log.i("Notification", "show me ");
			break;
		case R.id.button2:
			Log.i("Notification", "cancel ");
			finish();
			break;
		}
		
	}

}
