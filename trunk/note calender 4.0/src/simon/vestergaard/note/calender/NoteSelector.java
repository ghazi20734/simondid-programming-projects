package simon.vestergaard.note.calender;

import java.util.ArrayList;

import simon.vestergaard.note.calender.Main.FancyAdapterEditing;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ViewFlipper;


public class NoteSelector extends Activity implements OnClickListener, OnItemClickListener {

	//===============================================================animation stuff=============================================================================================================

	//================================================================animation stuff============================================================================================================

	
	public static String noteName=null;
	Button Bback,Bnew,Bedit;
	ListView LMain;
	TextView TVnote,TVcategory;
	FancyAdapter aa = null;
	FancyAdapterEditing aaa =null;
	public static ArrayList<String>listDataNote= new ArrayList<String>();
	public static ArrayList<Integer>listDataNoteRowIds= new ArrayList<Integer>();
	DatabaseHandler database = new DatabaseHandler(NoteSelector.this);
	 Main mainClass = new Main();
	ArrayList<String>listAlarmTime = new ArrayList<String>();
	boolean editingList = false;
	 public static int selectedPosition;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.noteselectorscreen);
		
		initilize();
	}
	
	public void onBackPressed(){
	    finish();
	    overridePendingTransition(R.anim.push_left_in,R.anim.push_left_out );
	}

	private void initilize() {
		// TODO Auto-generated method stub
	/*	Bback =(Button)findViewById(R.id.Bback);
		Bnew =(Button)findViewById(R.id.Bnew);*/
		LMain =(ListView) findViewById(R.id.LMain);
		TVcategory=(TextView)findViewById(R.id.TVcategory);
		TVnote =(TextView)findViewById(R.id.TVnote);
		//Bedit =(Button)findViewById(R.id.Bedit);
		
	//	Bedit.setOnClickListener(this);
		//Bback.setOnClickListener(this);
		//Bnew.setOnClickListener(this);
		LMain.setOnItemClickListener(this);
		
		aaa = new FancyAdapterEditing();
		aa = new FancyAdapter();
		if(editingList){
		LMain.setAdapter(aaa);	
		}else{
		LMain.setAdapter(aa);
		}
		FillListDataWithData();
		String caragorynameHeader =mainClass.getSelectedCategory();
		TVcategory.setText("Selected Category:"+caragorynameHeader);
		TVnote.setText(" Select A Note:");
		
	}



	private void FillListDataWithData() {
		// TODO Auto-generated method stub
		
		
		ArrayList<String> notes = new ArrayList<String>();
		database.open();
		listDataNote.clear();
		notes = database.getNotes(mainClass.dataFromPosition);
		listDataNoteRowIds = database.getNoteRowIds(mainClass.dataFromPosition);
		listAlarmTime = database.getAlarmTime(mainClass.dataFromPosition);
		database.close();
		for(int i=0; i<notes.size();i++){
			listDataNote.add(""+notes.get(i));	
			listAlarmTime.add(""+notes.get(i));	
		}
	
if(editingList){
			
			aaa.notifyDataSetChanged();
		}else{
			
			aa.notifyDataSetChanged();
		}
		
		
	}

	public void onItemClick(AdapterView<?> arg0, View view, int position, long arg3) {
		// TODO Auto-generated method stub
		Intent i = new Intent("simon.vestergaard.note.calender.Note");
		selectedPosition = position;
	    noteName = listDataNote.get(position);
		
		startActivity(new Intent(this,Note.class));
		overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
	}
	public int getSelectedPosition(){
		Log.i("NoteSelector"," selected position is = "+selectedPosition);
		return selectedPosition;
	}
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
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
		case R.id.Bback:
			
			Intent i = new Intent("simon.vestergaard.note.calender.Main");
			startActivity(new Intent(this,Main.class));
			overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
			break;
		case R.id.Bnew:
		
			
			break;
		}
		
	}
 public void AddNote(){

		
		// TODO Auto-generated method stub
		AlertDialog.Builder alert = new AlertDialog.Builder(this);

		alert.setTitle("New Note");
		alert.setMessage("pleas enter a Name for youre New Note");

		// Set an EditText view to get user input
		final EditText input = new EditText(this);
		alert.setView(input);

		alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface dialog, int whichButton) {
		 String value = input.getText().toString();
		  // Do something with value!
		 database.open();
		 database.CreateNote(value, ""+mainClass.dataFromPosition);
		database.close();
			value=null;
			FillListDataWithData();
			if(editingList){
				aaa.notifyDataSetChanged();
				}else{
			aa.notifyDataSetChanged();
			}
			Log.i("Main", "Badd clicked with data set change == " + listDataNote);
			
		  }
		});

		alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
		  public void onClick(DialogInterface dialog, int whichButton) {
		    // Canceled.
		  }
		});

		alert.show();
 }
	class FancyAdapter extends ArrayAdapter<String>{
    	FancyAdapter(){
    		super(NoteSelector.this, R.layout.row, listDataNote);
    		
    	}
    	public View getView (int position, View convertView,ViewGroup parent){
    		View row = convertView;
    		
    		if ( row ==null){
    			LayoutInflater inflater = getLayoutInflater();
    			row=inflater.inflate(R.layout.row, null);
    			
    		}
    		((TextView)row.findViewById(R.id.textView1)).setText(listDataNote.get(position));
    		
		   
		    	 
		    	 ((TextView)row.findViewById(R.id.textView2)).setText(""+listAlarmTime.get(position));
		   
		    	
		   
		     
    	
    		return row;
    	}
    }
	class FancyAdapterEditing extends ArrayAdapter<String>{
    	
		FancyAdapterEditing(){
    		super(NoteSelector.this, R.layout.rowed, listDataNote);
    	
    	}


		
	public View getView (final int position, View convertView,ViewGroup parent){
		View row = convertView;
		
		if ( row ==null){
			LayoutInflater inflater = getLayoutInflater();
		
				row=inflater.inflate(R.layout.rowed, null);
			
				
			}
		
		((TextView)row.findViewById(R.id.textView1)).setText(listDataNote.get(position));
		((TextView)row.findViewById(R.id.textView2)).setText(""+listAlarmTime.get(position));
		
			((Button)row.findViewById(R.id.Bdelete)).setOnClickListener(new OnClickListener() {
				
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Log.i("Main", "delete button phused");
					database.open();
					database.deleteNote(listDataNoteRowIds.get(position), mainClass.getSelectedCategory());
					database.close();
					if(editingList){
						
						aaa.notifyDataSetChanged();
					}else{
						
						aa.notifyDataSetChanged();
					}
					
				}
			});
		
	
		return row;
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
		case R.id.topbarADD:
			AddNote();
			break;
		case R.id.MenuImportDatabase:
		
			
			break;
		case R.id.preferences:
			
			break;
		
		
		}
		return false;
	}

}
