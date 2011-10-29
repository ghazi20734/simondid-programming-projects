package simon.programming;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class AndroidBasicsStarter extends ListActivity{
    /** Called when the activity is first created. */
	String tests[] = { "LifeCycleTest", "SingleTouchTest", "MultiTouchTest",
    		"KeyTest", "AccelerometerTest", "AssetsTest",
    		"ExternalStorageTest", "SoundPoolTest", "MediaPlayerTest","FullScreenTest", "RenderViewTest", "ShapeTest", "BitmapTest",
    		"FontTest", "SurfaceViewTest" };
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setListAdapter(new ArrayAdapter<String>(AndroidBasicsStarter.this, android.R.layout.simple_list_item_1,tests));
		
		
		
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		String cheese = tests[position];
		Class ourClass;
		try {
			ourClass = Class.forName("simon.programming."+cheese);
		
		Intent ourIntent = new Intent(AndroidBasicsStarter.this,ourClass);
		startActivity(ourIntent);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	}