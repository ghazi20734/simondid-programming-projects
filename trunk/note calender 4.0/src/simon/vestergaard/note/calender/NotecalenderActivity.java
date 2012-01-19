package simon.vestergaard.note.calender;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class NotecalenderActivity extends ListActivity {

	String classes[] = {"Main","NoteSelector" , "Note" , "MakeNote","ScreenTester","MainDropBoxTest","NoteTest","Preferances"};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setListAdapter(new ArrayAdapter<String>(NotecalenderActivity.this, android.R.layout.simple_list_item_1,classes));
		
		
		
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		
		String cheese = classes[position];
		
		Class ourClass = null;
		
		try {
			 ourClass = Class.forName("simon.vestergaard.note.calender."+cheese);
		
			Log.i("NotecalenderActivity", ""+cheese);
		
		Intent ourIntent = new Intent(NotecalenderActivity.this,ourClass);
		
		startActivity(ourIntent);
		
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	
	}


