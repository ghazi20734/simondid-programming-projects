package simon.programmering.music;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Random;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.SlidingDrawer;
import android.widget.TextView;

public class MusicActivity extends Activity {
    /** Called when the activity is first created. */
	MediaPlayer mpSong;
	private String state;
	private TextView canWrite,canRead;
	boolean canW,canR;
	File yourFile;
	String [] name;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Log.i("state", "app starter");
        setup();
        Log.i("state", "xml setup don");
        checkState();
        Log.i("state", "checkking storge state don");
    	File dir = Environment.getExternalStorageDirectory();
		 yourFile = new File(dir, "/musiktelefon");
	
		String[] a = yourFile.list();
		
		Log.i("number", "filer i specified mappe    "+a.length);
		 String t = a.toString();
		 
		for (int i = 0; i < a.length; i++) {
			Log.i("fodler-contains", ""+a[i]);
		}
		
		
		
		
	
		
		FileFilter filterDirectoriesOnly = new FileFilter() {
		    public boolean accept(File file) {
	        return file.getName().endsWith("mp3");
		    }
		};
	
		 Log.i("state", "filter don");
		File[] sdDirectories = yourFile.listFiles(filterDirectoriesOnly);
		Log.i("number", "alt tal filer efter filter 		::"+sdDirectories.length);
		
		
		for (int i = 0; i < sdDirectories.length; i++) {
			Log.i("filer efter filter", ""+sdDirectories[i]);
		}
		 Log.i("state", "filter er blevet logged");
		 
		 Random generator = new Random();
		 
		 int randomNumber = generator.nextInt(sdDirectories.length);
		 
		 Log.i("number", "random number er "+randomNumber);
		 
		 
		 
		 String musicposisition = sdDirectories[randomNumber].toString();
		
		 
		 
		 Log.i("path", ""+musicposisition);
		 Log.i("state", "musiken starter");
		  try {
			  mpSong = new MediaPlayer();
			  Log.i("state", "music afspilning 1");
		        mpSong.setDataSource(musicposisition);
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

	private void setup() {
		// TODO Auto-generated method stub
		canWrite = (TextView)findViewById(R.id.tvCanWrite);
		canRead = (TextView)findViewById(R.id.tvCanRead);
		
		
	}

	private void checkState() {
		// TODO Auto-generated method stub
	state = Environment.getExternalStorageState();
		
		if(state.equals(Environment.MEDIA_MOUNTED)){
			//read and write
		canRead.setText("true");
			canWrite.setText("true");
			canW = canR = true;
			
		}else if (state.equals(Environment.MEDIA_MOUNTED_READ_ONLY)){
			// read only 
			canRead.setText("true");
			canWrite.setText("false");
			canW = false;
			canR = true;

		}else {
			canWrite.setText("false");
			canRead.setText("false");
			canW = canR = false;
		}
		
		
		
	}



	
}