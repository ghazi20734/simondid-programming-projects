package simon.vestergaard.note.calender;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class NotificationActivity extends Activity implements OnClickListener{
		
		Button BshowME,Bcancel;
		TextView tv;

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
        Dialog dialog = new Dialog(NotificationActivity.this);
        dialog.setContentView(R.layout.notificationdialog);
        dialog.setTitle("This is my custom dialog box");
        dialog.setCancelable(true);
        //there are a lot of settings, for dialog, check them all out!

        //set up text
        TextView text = (TextView) dialog.findViewById(R.id.TextView01);
        Log.i("NotificationActivity", "setting up text");
        database.open();
        text.setText(database.getNoteData(getIntent().getStringExtra("category"), getIntent().getIntExtra("NotePosisition", -1)));
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
    }

	

	private void initilize() {
		// TODO Auto-generated method stub
		/*BshowME =(Button)findViewById(R.id.NotificationButtonDialog);
		Bcancel = (Button)findViewById(R.id.button2);
		
		BshowME.setOnClickListener(this);
		Bcancel.setOnClickListener(this);
		
		*/
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
