package simon.programming;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;

public class Splash extends Activity {

	MediaPlayer ourSong;
	
	@Override
	protected void onCreate(Bundle TravisLoveBacon) {
		// TODO Auto-generated method stub
		super.onCreate(TravisLoveBacon);
		setContentView(R.layout.splash);
	
		ourSong = MediaPlayer.create(Splash.this, R.raw.damianmarley);
		SharedPreferences getPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		boolean musik = getPrefs.getBoolean("checkbox", true);
		if(musik){
		ourSong.start();
		}
		Thread timer = new Thread(){
			
			public void run(){

				try{

					sleep(5000);
					
				}catch(InterruptedException e){
					e.printStackTrace();
				}finally{

					Intent openStartingPoint = new Intent("simon.programming.Menu");

					startActivity(openStartingPoint);
				}
			}
			
		};
		timer.start();
		
		
		
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		ourSong.release();
		finish();
		
	}
	

}
