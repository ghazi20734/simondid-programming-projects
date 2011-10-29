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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setListAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, tests));
        
        
    }
	public void onListItemClick(ListView list, View view , int Position,long id){
		super.onListItemClick(list, view, Position, id);
		String testName = tests[Position];
		try{
			Class clazz = Class.forName("simon.programming."+testName);
			Intent intent = new Intent(this,clazz);
			
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}