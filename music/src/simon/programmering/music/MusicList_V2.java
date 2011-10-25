package simon.programmering.music;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import simon.programmering.music.MusicList.Mp3Filter;


import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.DataSetObserver;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
public class MusicList_V2 extends Activity implements OnClickListener, OnItemClickListener, OnCompletionListener {

	
	
	Button bPause,bStart,bBack,bForward;
	private ListView listview;
	MediaPlayer mPlayer;
	ArrayList<String>listViewData= new ArrayList<String>(); // aka musicacfterfile filer
	ArrayList<String>listViewDataWithPath= new ArrayList<String>();
	File[] musicAfterFileFilter; // aka sdDirectories
	//========================storge chekking varibals start
	boolean canW,canR;
	private String state;
	//========================storage varibals stop
	File currentpathSpecifikFodler,externalStorgePath; // aka yourfile
	SharedPreferences preferences;
	String randomNumer,lastPlayedNumber;
	

	FancyAdapter aa;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_item);
		
		
		Log.i("state", "app is starting");
		setup();
		checkStoragState();
		specifikFolderScanner();
		
		
		
		
	}
	private void setup() {
		// TODO Auto-generated method stub
		Log.i("state", "setup start");
		mPlayer = new MediaPlayer();
		listview =(ListView) findViewById(R.id.listView);
		bBack = (Button)findViewById(R.id.bBack);
		bStart = (Button)findViewById(R.id.bStart);
		bForward = (Button)findViewById(R.id.bForward);
		bPause =(Button)findViewById(R.id.bPause);
		
		
		bBack.setOnClickListener(this);
		bStart.setOnClickListener(this);
		bForward.setOnClickListener(this);
		bPause.setOnClickListener(this);
		
		listview.setOnItemClickListener(this);
		
		mPlayer.setOnCompletionListener(this);
		
		
		externalStorgePath = Environment.getExternalStorageDirectory();
		preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		aa = new FancyAdapter();
		Log.i("state", "setup don");
	}
	private void specifikFolderScanner() {
		// TODO Auto-generated method stub
		
		Log.i("state", "specifik folder scanner has startet");
		currentpathSpecifikFodler = new File(externalStorgePath, preferences.getString("folderInput", "/musiktelefon"));
		
		
	if( currentpathSpecifikFodler.isDirectory()){
			
			Log.i("state", "folder is a folder an the mp3 scan will start ");
			 Toast t = Toast.makeText(this, "folder input i right and works", Toast.LENGTH_LONG);
			 t.show();
		
		}else{
			Log.i("state", "invalidt user folder input not a folder2 ");
	
			preferences.edit().putString("folderInput", "/musiktelefon").commit();
			
			 currentpathSpecifikFodler = new File(externalStorgePath, preferences.getString("folderInput", "/musiktelefon"));
			
			 Toast tt = Toast.makeText(this, "defualt music folder slected ", Toast.LENGTH_LONG);
			 tt.show();
			
			 Log.i("state", "fail fodler input has been set to " +currentpathSpecifikFodler.toString());
		}
	
	FileFilter specifikFolderFileFilterMP3 = new FileFilter() {
	    public boolean accept(File file) {
        return file.getName().endsWith("mp3");
	    }
	};
	
	musicAfterFileFilter = currentpathSpecifikFodler.listFiles(specifikFolderFileFilterMP3);
	

	for (int i = 0; i < musicAfterFileFilter.length; i++) {
		
		listViewData.add(""+musicAfterFileFilter[i].getName());
		listViewDataWithPath.add(""+musicAfterFileFilter[i]);
		Log.i("filer efter filter", ""+musicAfterFileFilter[i]);
	}
	Log.i("state", "current list view Data length :::" + listViewData.size());
	
	//listview.setAdapter(new ArrayAdapter<String>(MusicList_V2.this,android.R.layout.simple_list_item_1 , listViewData));
	listview.setAdapter(aa);
	
		Log.i("state", "specifik fodler scanner is don");
	}

	private void checkStoragState() {
		// TODO Auto-generated method stub
state = Environment.getExternalStorageState();
		
		if(state.equals(Environment.MEDIA_MOUNTED)){
			//read and write
		
			canW = canR = true;
			
		}else if (state.equals(Environment.MEDIA_MOUNTED_READ_ONLY)){
			// read only 
			
			canW = false;
			canR = true;

		}else {
			
			canW = canR = false;
			
			Toast t = Toast.makeText(MusicList_V2.this, "programmet slutter da der ikke er adgang til sdcard på nogle måder",Toast.LENGTH_LONG);
			Log.i("state", "don't have acces to the storage  in any why");
			t.show();
			finish();
		}
		
		
	}

	

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()){
		
		case R.id.bStart:
			if(mPlayer.isPlaying()){
			mPlayer.pause();
			}else{
				playThisSong(musicNumberSelector(listViewDataWithPath.size()));
			}
			
		
			break;
		case R.id.bPause:
			mPlayer.pause();
			break;
		case R.id.bForward:
			playThisSong(musicNumberSelector(listViewDataWithPath.size()));
			break;
		case R.id.bBack:
			playThisSong(lastPlayedNumber);
			break;
		
		}
		
	}


	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		
		if(mPlayer.isPlaying()){
			mPlayer.stop();
			
			try {
				  mPlayer = new MediaPlayer();
				  Log.i("state", "music afspilning 1");
			        mPlayer.setDataSource(listViewDataWithPath.get(arg2));
			        Log.i("state", "music afspilning 2");
			    } catch (IllegalArgumentException e) {
			        // TODO Auto-generated catch block
			        e.printStackTrace();
			    } catch (IllegalStateException e) {
			        // TODO Auto-generated catch block
			        e.printStackTrace();
			    } catch (IOException e) {
			        // TODO Auto-generated catch block
			        e.printStackTrace();
			    }
			    try {
			    	 Log.i("state", "music afspilning 3");
			        mPlayer.prepare();
			        Log.i("state", "music afspilning 4");
			    } catch (IllegalStateException e) {
			        // TODO Auto-generated catch block
			        e.printStackTrace();
			    } catch (IOException e) {
			        // TODO Auto-generated catch block
			        e.printStackTrace();
			    }
			    Log.i("state", "music afspilning 5");
			    mPlayer.start();
			    Log.i("state", "music afspilning 6");
			    
			 
			 
			 Log.i("state", "musiken spilning slut");
			
		 }else{
			
			 try {
				  mPlayer = new MediaPlayer();
				  
				  Log.i("state", "music afspilning 1");
			        mPlayer.setDataSource(listViewDataWithPath.get(arg2));
			        Log.i("state", "music afspilning 2");
			    } catch (IllegalArgumentException e) {
			        // TODO Auto-generated catch block
			        e.printStackTrace();
			    } catch (IllegalStateException e) {
			        // TODO Auto-generated catch block
			        e.printStackTrace();
			    } catch (IOException e) {
			        // TODO Auto-generated catch block
			        e.printStackTrace();
			    }
			    try {
			    	 Log.i("state", "music afspilning 3");
			        mPlayer.prepare();
			        Log.i("state", "music afspilning 4");
			    } catch (IllegalStateException e) {
			        // TODO Auto-generated catch block
			        e.printStackTrace();
			    } catch (IOException e) {
			        // TODO Auto-generated catch block
			        e.printStackTrace();
			    }
			    Log.i("state", "music afspilning 5");
			    mPlayer.start();
			    Log.i("state", "music afspilning 6");
			    
			 
			 
			 Log.i("state", "musiken spilning slut");
			
		 }
		
	}
	
	public String musicNumberSelector(int n){
		Random randomSelection = new Random();
		lastPlayedNumber = randomNumer;	//position er meget vigtig da den tager fra sidste gang randomnumber blev lavt
			 randomNumer = listViewDataWithPath.get(randomSelection.nextInt(n));
			Log.i("state", "musicNumberSelector :: randompath is == "+randomNumer);
			
		return randomNumer;
		
		
		
	}
	@Override
	public boolean onCreateOptionsMenu(android.view.Menu menu) {
		// TODO Auto-generated method stub
				super.onCreateOptionsMenu(menu);
				Log.i("state", "menu button was pushed");								//============logging=============
				MenuInflater blowUp = getMenuInflater();
				blowUp.inflate(R.menu.cool_menu, menu);
				return true;

		
	
	}

	public void playThisSong(String path){
		Log.i("state", "playThisSong has been called");
		mPlayer.pause();
		mPlayer.stop();
		mPlayer.release();
		mPlayer = new MediaPlayer();
		try {
			mPlayer.setDataSource(path);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			mPlayer.prepare();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mPlayer.start();
		
		
		Log.i("state", "playThisSong has endet");
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()){
		
		case R.id.aboutUs:
			Log.i("state", "menu about was pushed");									//============logging=============
			Intent i = new Intent(this, AboutUs.class);
			startActivity(i);
			
			break;
		case R.id.preferences:
			Log.i("state", "menu preferances was pushed");
			Intent a = new Intent(this, Prefs.class);
			startActivity(a);
			
			break;
		case R.id.exit:
			Log.i("state", "finsh app was pushed");										//============logging=============
			mPlayer.stop();
			finish();
			
			break;
		
		}
		return false;
	}
	 
	class FancyAdapter extends ArrayAdapter<String>{
	    	FancyAdapter(){
	    		super(MusicList_V2.this, android.R.layout.simple_list_item_2, listViewData);
	    		
	    	}
	    	public View getView (int position, View convertView,ViewGroup parent){
	    		View row = convertView;
	    		
	    		if ( row ==null){
	    			LayoutInflater inflater = getLayoutInflater();
	    			row=inflater.inflate(R.layout.custom_list_item, null);
	    			
	    		}
	    		((TextView)row.findViewById(R.id.firstText)).setText(listViewData.get(position));
	    		((TextView)row.findViewById(R.id.secondText)).setText(listViewDataWithPath.get(position));
	    	
	    		return row;
	    	}
	    }

	@Override
	public void onCompletion(MediaPlayer mp) {
		// TODO Auto-generated method stub
		Log.i("state", "onCompletion has been called");
		playThisSong(musicNumberSelector(listViewDataWithPath.size()));
		
	
			
			
		}
		
	

	
}
