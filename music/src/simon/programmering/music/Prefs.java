package simon.programmering.music;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.util.Log;

public class Prefs extends PreferenceActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Log.i("state", "prefs activity startet");
		addPreferencesFromResource(R.xml.prefs);
	}

	
	
	

}
