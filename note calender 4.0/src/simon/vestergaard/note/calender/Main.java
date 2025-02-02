package simon.vestergaard.note.calender;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;

import com.google.android.apps.analytics.GoogleAnalyticsTracker;

import simon.vestergaard.note.calender.R.drawable;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;






public class Main extends Activity implements OnClickListener, OnItemClickListener{
	private static final String KEY_PREFERANCES_THEMES="themess";

	static final int Dialog_Back_Up_good =1;
	static final int Dialog_Back_Up_Fail =2;
	static final int Dialog_import_database=3;
	Button Bsync,Badd,Bedit;
	ListView LMain;
	TextView header;
	LinearLayout BackGround;
	RelativeLayout background2;
	public static String CategoryName=null;
	public static String dataFromPosition;
	ArrayList<String>listData= new ArrayList<String>();
	ArrayList<String>listDataWithNumberOffNotes= new ArrayList<String>();
	ArrayList<Integer> listDataRowIds= new ArrayList<Integer>();
	boolean backup=true;
	boolean editingList=false;
	DatabaseHandler database = new DatabaseHandler(Main.this);
	GoogleAnalyticsTracker tracker;
	FancyAdapter aa=null;
	FancyAdapterEditing aaa=null;
	
	ProgressDialog MyDialog;
	private static Context context;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		 setContentView(R.layout.mainscreen);
	  
		
		initilize();

		
		
		
	//LMain.setAdapter( new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,listData));
		
	
	}
	 public static Context getAppContext() {
	        return Main.context;
	    }

	 public void onBackPressed(){
		    finish();
		    overridePendingTransition(R.anim.push_left_in,R.anim.push_left_out );
		}
	
	 public int whatThemTouse(){
			final String PREFS_NAME = "MyPrefsFile";
			 SharedPreferences.Editor SettingsEditor = null;
			 SharedPreferences settings = null;
			 settings = getSharedPreferences(PREFS_NAME, 0);
			    SettingsEditor = settings.edit();
			drawable hej = new drawable();
			int hej2 = hej.them_blue_black;
			
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
			}
				
		
			return hej2;
			
		}
	 public void setActivityBackgroundColor(int i) {
		    View view = this.getWindow().getDecorView();
		    view.setBackgroundResource(i);
		}
	private void initilize() {
		// TODO Auto-generated method stub
		LMain =(ListView)findViewById(R.id.LMain);
	

				setActivityBackgroundColor(whatThemTouse());
		
				
		aa = new FancyAdapter();
		aaa = new FancyAdapterEditing();
		LMain.setOnItemClickListener(this);
		if(editingList){
		
			LMain.setAdapter(aaa);
			
		}else{
			
			LMain.setAdapter(aa);
			
		}
		
		FillListDataWithData();
		Main.context = getApplicationContext();
		
	}
	


	private void FillListDataWithData() {
		// TODO Auto-generated method stub
		
		database.open();
		listData.clear();
		ArrayList<String> categorys = new ArrayList<String>();
		categorys =database.getCategorys();
		listDataRowIds = database.getCategorysRowIDs();
		for(int i=0; i<categorys.size();i++){
			
			listData.add(""+categorys.get(i));	
			listDataWithNumberOffNotes.add(""+database.getNumberOFFNotes(categorys.get(i)));
		}
	
		categorys.clear();
		if(editingList){
			
			aaa.notifyDataSetChanged();
		}else{
			
			aa.notifyDataSetChanged();
		}
		
		
		database.close();
		
		
	}



	class allItems extends ArrayAdapter<String>{
    	
		allItems(){
    		super(Main.this, R.layout.row, listData);
    	
    	}


		
	public View getView (int position, View convertView,ViewGroup parent){
		View row = convertView;
		
		if ( row ==null){
			LayoutInflater inflater = getLayoutInflater();
		
				row=inflater.inflate(R.layout.row, null);	
			
		}
		((TextView)row.findViewById(R.id.textView1)).setText("alle NOtes");
		//((TextView)row.findViewById(R.id.textView2)).setText(listDataWithNumberOffNotes.get(position));
	
	
		return row;
	}
}


	class FancyAdapter extends ArrayAdapter<String>{
    	
    		FancyAdapter(){
        		super(Main.this, R.layout.row, listData);
        	
        	}

	
			
    	public View getView (int position, View convertView,ViewGroup parent){
    		View row = convertView;
    		
    		if ( row ==null){
    			LayoutInflater inflater = getLayoutInflater();
    		
    				row=inflater.inflate(R.layout.row, null);	
    			
    		}
    		((TextView)row.findViewById(R.id.textView1)).setText(listData.get(position));
    		((TextView)row.findViewById(R.id.textView2)).setText(listDataWithNumberOffNotes.get(position));
    	
    	
    		return row;
    	}
}
    	
    	class FancyAdapterEditing extends ArrayAdapter<String>{
        	
    		FancyAdapterEditing(){
        		super(Main.this, R.layout.rowed, listData);
        	
        	}


			
    	public View getView (final int position, View convertView,ViewGroup parent){
    		View row = convertView;
    		
    		if ( row ==null){
    			LayoutInflater inflater = getLayoutInflater();
    		
    				row=inflater.inflate(R.layout.rowed, null);
    			
    				
    			}
    		
    		((TextView)row.findViewById(R.id.textView1)).setText(listData.get(position));
    		((TextView)row.findViewById(R.id.textView2)).setText(listDataWithNumberOffNotes.get(position));
    		
    			((Button)row.findViewById(R.id.Bdelete)).setOnClickListener(new OnClickListener() {
					
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Log.i("Main", "delete button phused");
						database.open();
						database.deleteCategory(listDataRowIds.get(position), listData.get(position));
						database.close();
						if(editingList){
							editingList = false;
							aaa.notifyDataSetChanged();
							initilize();
						}else{
							editingList = true;
							aa.notifyDataSetChanged();
							initilize();
						}
						
					}
				});
    		
    	
    		return row;
    	}
    	}
    	
    
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		dataFromPosition = null;
		if(editingList){
			
			aaa.notifyDataSetChanged();
		}else{
			
			aa.notifyDataSetChanged();
		}
		
		
		initilize();
		
	}



	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()){
		case R.id.Bedit:
			
			if(editingList){
			editingList = false;
			aaa.notifyDataSetChanged();
			initilize();
			Bedit.setText(R.string.Edit);
			}else{
				editingList = true;
				aa.notifyDataSetChanged();
				initilize();
				Bedit.setText(R.string.Don);
			}
			break;
		case R.id.Badd:
		
			
		AddCategory();
		
			break;
		case R.id.Bsync:
			Log.i("Main", "B backup was pushed");
			BackUpDatabase bs = new BackUpDatabase();
			bs.execute("hej");
			
			break;
	}
	}



	



	public void onItemClick(AdapterView<?> arg0, View view, int position, long arg3) {
		// TODO Auto-generated method stub
		
		
         
	
		dataFromPosition = listData.get(position);
		CategoryName = listData.get(position);
		
		Intent i = new Intent("simon.vestergaard.note.calender.NoteSelector");
		Log.i("Main", "position data position="+position+ "  datafromPosition ="+dataFromPosition);
		startActivity(new Intent(this,NoteSelector.class));
		overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
	}
	public String getSelectedCategory(){
		Log.i("Main", "dataFromPosition ="+dataFromPosition);
		return this.dataFromPosition;
	}
	private class BackUpDatabase extends AsyncTask<String, FileInputStream , Long>{



		@Override
		protected Long doInBackground(String... params) {
		    // TODO Auto-generated method stub
			Log.i("Main", "AsyncTask is runnering in doInBackground");
		 File data = Environment.getDataDirectory();


		     String currentDBPath = "\\simon.vestergaard.note.calender\\databases\\NoteCalender";
		     String backupDBPath = "NoteCalender";
		
		     // File fi = new File(database.getDatabasePath());
		     File locationfrom = new File(data + ""+data+"/simon.vestergaard.note.calender"+ "/databases",backupDBPath);
		     
		     File dest = new File(Environment.getExternalStorageDirectory()+"/NoteClander");
		  
		     try {
		    	 if(Environment.getExternalStorageDirectory().canWrite()){
		    	Log.i("Main", "backing up note database");
		    		 FileUtils.copyFileToDirectory(locationfrom, dest);
		    	backup = true;
		    	 }else{
		    		 Log.i("can't write to sd card", "can't write to sd card");
		    		 Toast.makeText(Main.this,""+R.string.FaileBackUp, Toast.LENGTH_LONG).show();
		    		 backup = false;
		    	 }
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		    return null;
		}
		private class ImportDatabase extends AsyncTask<String, FileInputStream , Long>{



			@Override
			protected Long doInBackground(String... params) {
			    // TODO Auto-generated method stub
				Log.i("Main", "AsyncTask is runnering in doInBackground");
			 File data = Environment.getDataDirectory();


			     String currentDBPath = "\\simon.vestergaard.note.calender\\databases\\NoteCalender";
			     String backupDBPath = "NoteCalender";
			
			     // File fi = new File(database.getDatabasePath());
			     File locationfrom = new File(data + ""+data+"/simon.vestergaard.note.calender"+ "/databases",backupDBPath);
			     
			     File dest = new File(Environment.getExternalStorageDirectory()+"/NoteClander");
			  
			     try {
			    	 if(Environment.getExternalStorageDirectory().canWrite()){
			    	Log.i("Main", "backing up note database");
			    		 FileUtils.copyFileToDirectory(locationfrom, dest);
			    	backup = true;
			    	 }else{
			    		 Log.i("can't write to sd card", "can't write to sd card");
			    		 Toast.makeText(Main.this,""+R.string.FaileBackUp, Toast.LENGTH_LONG).show();
			    		 backup = false;
			    	 }
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
			    return null;
			}
		}
		protected void onPostExecute(Long result) {
			Toast.makeText(Main.this, ""+R.string.BackUpDon, Toast.LENGTH_LONG).show();
			if(backup){
				showDialog(Dialog_Back_Up_good);
			}else{
				showDialog(Dialog_Back_Up_Fail);
			}
		}

	
			
		}
	 @Override
	    protected Dialog onCreateDialog(int id) {
	        switch (id) {
	        case Dialog_Back_Up_good:
	        	return new AlertDialog.Builder(Main.this)
	        	.setTitle(R.string.BackUpDialogTitle)
	        	.setPositiveButton("OK", null)
				.setMessage(R.string.BackUpDialogMessageGood)
				.create();
	        case Dialog_Back_Up_Fail:
	        	return new AlertDialog.Builder(Main.this)
	        	.setTitle(R.string.BackUpDialogTitle)
	        	.setPositiveButton("OK", null)
				.setMessage(R.string.BackUpDialogMessageFail)
				.create();
	        case Dialog_import_database:
	        	return new AlertDialog.Builder(Main.this)
	        	.setTitle(R.string.ImportDatabaseDialogTitle)
	        	/*.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						Log.i("Main","positive Button In dialog Box");
						
					}
				})*/
	        	.setNegativeButton("Cancel", null)
				.setMessage(R.string.ImportDatabaseMessagePart1)
				.create();
	        }
	        	
	        
	        return null;
	    }
	 public void AddCategory(){

			
			
			// TODO Auto-generated method stub
			AlertDialog.Builder alert = new AlertDialog.Builder(this);

			alert.setTitle("catagory");
			alert.setMessage("pleas enter a title for youre catagory");

			// Set an EditText view to get user input
			final EditText input = new EditText(this);
			alert.setView(input);

			alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
			 String value = input.getText().toString();
			  // Do something with value!
			 database.open();
			 database.CreateCategory(""+value);
			database.close();
				value=null;
				FillListDataWithData();
				if(editingList){
				
					aaa.notifyDataSetChanged();
					}else{
						aa.notifyDataSetChanged();
					}
				Log.i("Main", "Badd clicked with data set change == " + listData);
				
			  }
			});

			alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			  public void onClick(DialogInterface dialog, int whichButton) {
			    // Canceled.
			  }
			});
			
			alert.show();
		
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
	protected void onDestroy() {
		// TODO Auto-generated method stub
			tracker.stop();
		super.onDestroy();
	}
		@Override
		public boolean onOptionsItemSelected(MenuItem item) {
			// TODO Auto-generated method stub
			switch (item.getItemId()){
			
			case R.id.topbarADD:
				AddCategory();
				break;
			
			case R.id.TopBarEdit:

				if(editingList){
				editingList = false;
				aaa.notifyDataSetChanged();
				initilize();
				}else{
					editingList = true;
					aa.notifyDataSetChanged();
					initilize();

				}
				break;
			case R.id.MenuImportDatabase:
				
			showDialog(Dialog_import_database);
				
				break;
			case R.id.MenuExportDatabase:
				showDialog(Dialog_import_database);
				break;
			case R.id.preferences:
				startActivity(new Intent(this,Preferances.class));
				overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
			
				
				break;
				
			
			
			}
			return false;
		}
	
	}


