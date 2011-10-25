package simon.programming;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Menu extends ListActivity {

	String classes[] = {"Accelerate","SQLiteExample","ExternalData","InternalData","private2n2","private2n1","Sharedprefs"
			,"Flipper","startingPoint","TextPlay","Email","Camara","Splash","Data","private1","Slider","Tabs","SimpleBrowser"};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setListAdapter(new ArrayAdapter<String>(Menu.this, android.R.layout.simple_list_item_1,classes));
		
		
		
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		String cheese = classes[position];
		Class ourClass;
		try {
			ourClass = Class.forName("simon.programming."+cheese);
		
		Intent ourIntent = new Intent(Menu.this,ourClass);
		startActivity(ourIntent);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(android.view.Menu menu) {
		// TODO Auto-generated method stub
				super.onCreateOptionsMenu(menu);
				MenuInflater blowUp = getMenuInflater();
				blowUp.inflate(R.menu.cool_menu, menu);
				return true;

		
	
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()){
		
		case R.id.aboutUs:
			Intent i = new Intent("simon.programming.Aboutus");
			startActivity(i);
			
			break;
		case R.id.preferences:
			Intent p = new Intent("simon.programming.Prefs");
			startActivity(p);
			
			break;
		case R.id.exit:
			finish();
			
			break;
		
		}
		return false;
	}

	

}
