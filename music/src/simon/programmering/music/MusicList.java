package simon.programmering.music;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class MusicList extends ListActivity {

	boolean canW,canR;
	private String state;
	File yourFile;
	MediaPlayer mpSong;
	File[] sdDirectories;
	SharedPreferences getPrefs;
	Button test;
	
	ArrayList<String>musicNumberAfterFilter= new ArrayList<String>();
	
	//=====================================
	 private static final String MEDIA_PATH = new String("/sdcard/");
     private List<String> songs = new ArrayList<String>();
     private MediaPlayer mp = new MediaPlayer();
     private int currentPosition = 0;
     
     ListView listview;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
	     Log.i("state", "app starter");
	      
	        setup();
	       
	        
	        
	        Log.i("state", "xml setup don");														//============logging=============
	        checkState();
	        Log.i("state", "checkking storge state don");											//============logging=============
	    	File dir = Environment.getExternalStorageDirectory();
			 yourFile = new File(dir, getPrefs.getString("folderInput", "/musiktelefon"));
			Log.i("state", "the sdcard scans are about to begin");
			// updateSongList();
			 spefificFolderSelectionScan();
			
			//holeSdcardScan();
			 makelistview();
			 Log.i("state", "bunden af oncreate ");
	}	 
	private void makelistview() {
		// TODO Auto-generated method stub
		 for (int i = 0; i < musicNumberAfterFilter.size(); i++) {										//============logging=============
 			Log.i("filer efter filter", ""+musicNumberAfterFilter.get(i));
 			
 		} 
     	setListAdapter(new ArrayAdapter<String>(MusicList.this, android.R.layout.simple_list_item_1,musicNumberAfterFilter));
     	Log.i("state", " hole sdcard scan  finish");

	}
	class Mp3Filter implements FilenameFilter {
        public boolean accept(File dir, String name) {
        	Log.i("state", "filter hole sdcard");
        	musicNumberAfterFilter.add(name);
                return (name.endsWith(".mp3"));
        }
}
	public void updateSongList() {
        File home = new File(MEDIA_PATH);
        if (home.listFiles(new Mp3Filter()).length > 0) {
                for (File file : home.listFiles(new Mp3Filter())) {
                        songs.add(file.getName());
                }
 
                setListAdapter(new ArrayAdapter<String>(MusicList.this, android.R.layout.simple_list_item_1,songs));
               // setListAdapter(songList);
        }
}

	private void holeSdcardScan() {
		// TODO Auto-generated method stub
		
		FilenameFilter allMp3Filter = new FilenameFilter() {
			File f;
			    public boolean accept(File dir, String name) {

			        if(name.endsWith(".mp3")){
			        return true;
			        }

			        f = new File(dir.getAbsolutePath()+"/"+name);

			        return f.isDirectory();
			    }
			};
		
		File root = Environment.getExternalStorageDirectory();
		
		String rootpath = root.getPath();
		String listOfFileNamesAllMp3Filter[] = root.list(allMp3Filter);
		
		setListAdapter(new ArrayAdapter<String>(MusicList.this, android.R.layout.simple_list_item_1,listOfFileNamesAllMp3Filter));
		MediaStore s = new MediaStore();
	//	mpSong.setDataSource(s.a)
	}


	private void spefificFolderSelectionScan() {
		// TODO Auto-generated method stub
		File dir = Environment.getExternalStorageDirectory();
		 yourFile = new File(dir, getPrefs.getString("folderInput", "/musiktelefon"));
		 
		if( yourFile.isDirectory()){
			
			Log.i("state", "folder is a folder an the mp3 scan will start ");
			 Toast t = Toast.makeText(this, "folder input i right and works", Toast.LENGTH_LONG);
			 t.show();
			
	
		}else{
			Log.i("state", "invalidt user folder input not a folder2 ");
		//	yourFile = new File (dir, "/musiktelefon");
			getPrefs.edit().putString("folderInput", "/musiktelefon").commit();
			
			 yourFile = new File(dir, getPrefs.getString("folderInput", "/musiktelefon"));
			
			 Toast tt = Toast.makeText(this, "defualt music folder slected ", Toast.LENGTH_LONG);
			 tt.show();
			
			 Log.i("state", "fail fodler input has been set to " +yourFile.toString());
		}
		String[] a = yourFile.list();
		
		Log.i("number", "filer i specified mappe    "+a.length);								//============logging=============
		 String t = a.toString();
		 
				for (int i = 0; i < a.length; i++) {													//============logging=============
			Log.i("fodler-contains", ""+a[i]);
		}
		
		//===========================================================================================================

			
				//===========================================================================================================
		
	
		
		FileFilter filterDirectoriesOnly = new FileFilter() {
		    public boolean accept(File file) {
	        return file.getName().endsWith("mp3");
		    }
		};
	
		 Log.i("state", "filter don");															//============logging=============
		 sdDirectories = yourFile.listFiles(filterDirectoriesOnly);					// file fliter adding/starting
		Log.i("number", "alt tal filer efter filter 		::"+sdDirectories.length);			//============logging=============
		
		
		for (int i = 0; i < sdDirectories.length; i++) {										//============logging=============
			Log.i("filer efter filter", ""+sdDirectories[i]);
			
		} 
		
		for (int i = 0; i < sdDirectories.length; i++) {
			
			musicNumberAfterFilter.add(""+sdDirectories[i].getName());
		}
		 Log.i("state", "filter er blevet logged");
		
		 if(musicNumberAfterFilter.isEmpty()){
			 Toast tt = Toast.makeText(this, "there is no media files in the given folder true", Toast.LENGTH_LONG);
		 tt.show();
		 }else{
			
		 }
		 
	//setListAdapter(new ArrayAdapter<String>(MusicList.this, android.R.layout.simple_list_item_1,musicNumberAfterFilter));
	setListAdapter(new ArrayAdapter<String>(this, R.layout.list_item, musicNumberAfterFilter));
	
	
	}


	private void setup() {
		// TODO Auto-generated method stub
		mpSong = new MediaPlayer();
		getPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		listview = (ListView)findViewById(R.id.listView);
		
		Log.i("state", "setup don activity");
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		
		if(mpSong.isPlaying()){
			mpSong.stop();
			
			try {
				  mpSong = new MediaPlayer();
				  Log.i("state", "music afspilning 1");
			        mpSong.setDataSource(sdDirectories[position].toString());
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
			        mpSong.prepare();
			        Log.i("state", "music afspilning 4");
			    } catch (IllegalStateException e) {
			        // TODO Auto-generated catch block
			        e.printStackTrace();
			    } catch (IOException e) {
			        // TODO Auto-generated catch block
			        e.printStackTrace();
			    }
			    Log.i("state", "music afspilning 5");
			    mpSong.start();
			    Log.i("state", "music afspilning 6");
			    
			 
			 
			 Log.i("state", "musiken spilning slut");
			
		 }else{
			
			 try {
				  mpSong = new MediaPlayer();
				  
				  Log.i("state", "music afspilning 1");
			        mpSong.setDataSource(sdDirectories[position].toString());
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
			        mpSong.prepare();
			        Log.i("state", "music afspilning 4");
			    } catch (IllegalStateException e) {
			        // TODO Auto-generated catch block
			        e.printStackTrace();
			    } catch (IOException e) {
			        // TODO Auto-generated catch block
			        e.printStackTrace();
			    }
			    Log.i("state", "music afspilning 5");
			    mpSong.start();
			    Log.i("state", "music afspilning 6");
			    
			 
			 
			 Log.i("state", "musiken spilning slut");
			
		 }
		
	}
	
	
	
	private void checkState() {
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
			
			Toast t = Toast.makeText(MusicList.this, "programmet slutter da der ikke er adgang il sdcard på nogle måder",Toast.LENGTH_LONG);
			t.show();
			finish();
		}
		
		
		
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
			mpSong.stop();
			finish();
			
			break;
		
		}
		return false;
	}

	
	
}
