package simon.programming;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Sharedprefs extends Activity implements OnClickListener {

	EditText sharedData;
	TextView dataResults;
	public static String filename = "MySharedString";
	SharedPreferences someData;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sharedpreferences);
		setupVariables();
		 someData = getSharedPreferences(filename, 0);
		
	}

	private void setupVariables() {
		// TODO Auto-generated method stub
		
		Button save = (Button)findViewById(R.id.bSave);
		Button load = (Button)findViewById(R.id.bLoad);
		sharedData = (EditText)findViewById(R.id.etSharedPrefs);
		dataResults =(TextView)findViewById(R.id.tvLoadSharedPrefs);
		save.setOnClickListener(this);
		load.setOnClickListener(this);
		
		
	}

	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		switch(v.getId()){
		
		case R.id.bSave:
			String stringData = sharedData.getText().toString();
			SharedPreferences.Editor editor =someData.edit();
			editor.putString("sharedString", stringData);
			editor.commit();
			InputMethodManager imm =(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(sharedData.getWindowToken(), 0);  //tutorial 93 the new boston http://www.youtube.com/watch?v=b4MYh6N4z6s&feature=mfu_in_order&list=UL
			break;
		case R.id.bLoad:
			
			someData = getSharedPreferences(filename, 0);
			String dataReturn = someData.getString("sharedString", "couldn't load data");
			dataResults.setText(dataReturn);

				
			break;
		
		}
		
	}
	
	

}
