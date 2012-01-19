package simon.vestergaard.note.calender;

import simon.vestergaard.note.calender.R.drawable;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

public class Preferances extends Activity implements OnClickListener, OnItemSelectedListener {
	private static final String KEY_PREFERANCES_THEMES="themes";
	Spinner themSpinner;
	int themposisition =0;
	LinearLayout background;
	public static final String PREFS_NAME = "MyPrefsFile";
	final String [] items=new String[]{"Blue and Black","Green and Black","Pink and Black","Red and Black","Yellow and Black","orange and Black",
			"Black and Black",}; // thems
	 SharedPreferences.Editor SettingsEditor = null;
	 SharedPreferences settings = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.preferancesscreen);
		
		initilize();
	}

	private void initilize() {
		// TODO Auto-generated method stub
		getSelectedThem();
		themSpinner =(Spinner)findViewById(R.id.ThemSpinner);
		background=(LinearLayout)findViewById(R.id.BackGroundPreferances);
		//background.setBackgroundColor(whatThemTouse());
		ArrayAdapter ad=new ArrayAdapter(this,android.R.layout.simple_spinner_item,items);
		 ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		 themSpinner.setSelection(themposisition);
		    themSpinner.setAdapter(ad);
		    themSpinner.setOnItemSelectedListener(this);
		   themSpinner.setSelection(themposisition);
		  
		  
		    
	}

	public void getSelectedThem(){
		try{
			  settings = getSharedPreferences(PREFS_NAME, 0);
			themposisition = settings.getInt("themPosisition", 0);
		Log.i("Preferances", "getting selected them posistion:"+themposisition);
		}catch(Exception e){
			e.printStackTrace();
			themposisition =0;
		}
	}
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		  settings = getSharedPreferences(PREFS_NAME, 0);
		    SettingsEditor = settings.edit();
			Log.i("Preferances", "posisition is:"+position);
			switch(position){
			case 0:
				Log.i("Preferances", "blue and black choosen");
				
				SettingsEditor.putInt("themess", 0);
				
				

				
				break;
		
			case 1:
				Log.i("Preferances", "Themes Green choosen");
				
				SettingsEditor.putInt("themess", 1);
				
				break;
			case 2:
Log.i("Preferances", "pink and black choosen");
				
				SettingsEditor.putInt("themess", 2);
				
				break;
			case 3:
				SettingsEditor.putInt("themess", 3);
				break;
			case 4:
				SettingsEditor.putInt("themess", 4);
				break;
			case 5:
				SettingsEditor.putInt("themess", 5);
				break;
			case 6:
				SettingsEditor.putInt("themess", 6);
				break;
			case 7:
				SettingsEditor.putInt("themess", 7);
				break;
			case 8:
				SettingsEditor.putInt("themess", 8);
				break;
			}
			
			SettingsEditor.putInt("themPosisition", position);
		SettingsEditor.commit();
		SettingsEditor.apply();
	}

	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub
		
	}

	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
	 public int whatThemTouse(){
			final String PREFS_NAME = "MyPrefsFile";
			 SharedPreferences.Editor SettingsEditor = null;
			 SharedPreferences settings = null;
			 settings = getSharedPreferences(PREFS_NAME, 0);
			    SettingsEditor = settings.edit();
			drawable hej = new drawable();
		
			int hej2;
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
			}else{
				hej2 = hej.them_blue_black;		
			}
				
			
			return hej2;
			
		}
	
}
