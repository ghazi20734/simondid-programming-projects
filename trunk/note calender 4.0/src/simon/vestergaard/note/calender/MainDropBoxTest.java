package simon.vestergaard.note.calender;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.DropboxAPI.Entry;
import com.dropbox.client2.android.AndroidAuthSession;
import com.dropbox.client2.exception.DropboxException;
import com.dropbox.client2.exception.DropboxUnlinkedException;
import com.dropbox.client2.session.AccessTokenPair;
import com.dropbox.client2.session.AppKeyPair;
import com.dropbox.client2.session.Session.AccessType;




public class MainDropBoxTest extends Activity implements OnClickListener, OnItemClickListener{

	//============================shared preferences===
	public static final String PREFS_NAME = "notepfres";
	//============================shared preferences===
	
	Button Bsync,Badd;
	ListView LMain;
	TextView header;
	
	public static String dataFromPosition;
	ArrayList<String>listData= new ArrayList<String>();
	ArrayList<String>listDataWithNumberOffNotes= new ArrayList<String>();
	
	DatabaseHandler database = new DatabaseHandler(MainDropBoxTest.this);
	
	FancyAdapter aa = null;
	//========================dropbox stuff===============================
	final static private String APP_KEY = "v2gsd0hi4phdyxh";
	final static private String APP_SECRET = "tj1oskilj7pucbz";
	final static private AccessType ACCESS_TYPE = AccessType.APP_FOLDER;
	final static private String KEY_PREFS_STORE_KEY="sharedprefskey";
	final static private String KEY_PREFS_STORE_secret="sharedprefsSecret";
	
	private DropboxAPI<AndroidAuthSession> mDBApi;
	long hoook;
	//========================dropbox stuff===============================
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		 setContentView(R.layout.dropboxtest);
	  
		
		initilize();
		
		
		
		
	//LMain.setAdapter( new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,listData));
		
	
	}



	@Override
	public void onBackPressed(){
	    finish();
	    overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
	}


	private void initilize() {
		// TODO Auto-generated method stub
		LMain =(ListView)findViewById(R.id.LMain);
		Bsync =(Button)findViewById(R.id.Bsync);
		Badd = (Button)findViewById(R.id.Badd);
		header =(TextView)findViewById(R.id.TVmainHeader);
		Badd.setOnClickListener(this);
		aa = new FancyAdapter();
		
		LMain.setOnItemClickListener(this);
		Bsync.setOnClickListener(this);
		
		LMain.setAdapter(aa);
		FillListDataWithData();
	
		
	}



	private void FillListDataWithData() {
		// TODO Auto-generated method stub
		
		database.open();
		listData.clear();
		ArrayList<String> categorys = new ArrayList<String>();
		categorys =database.getCategorys();
		for(int i=0; i<categorys.size();i++){
			listData.add(""+categorys.get(i));	
			listDataWithNumberOffNotes.add(""+database.getNumberOFFNotes(categorys.get(i)));
		}
	
		categorys.clear();
		aa.notifyDataSetChanged();
	
		database.close();
		
		
	}



	

	



	class FancyAdapter extends ArrayAdapter<String>{
    	FancyAdapter(){
    		super(MainDropBoxTest.this,R.layout.row, listData);
    		
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
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		dataFromPosition = null;
		aa.notifyDataSetChanged();
		
		
		//========================dropbox stuff===============================
		AppKeyPair appKeys = new AppKeyPair(APP_KEY, APP_SECRET);
		AndroidAuthSession session = new AndroidAuthSession(appKeys, ACCESS_TYPE);
		mDBApi = new DropboxAPI<AndroidAuthSession>(session);

		
		 if (mDBApi.getSession().authenticationSuccessful()) {
		        try {
		            // MANDATORY call to complete auth.
		            // Sets the access token on the session
		            mDBApi.getSession().finishAuthentication();

		            AccessTokenPair tokens = mDBApi.getSession().getAccessTokenPair();

		            // Provide your own storeKeys to persist the access token pair
		            // A typical way to store tokens is using SharedPreferences
		            storeKeys(tokens.key, tokens.secret);
		        } catch (IllegalStateException e) {
		            Log.i("DbAuthLog", "Error authenticating", e);
		        }
		    }
		//========================dropbox stuff===============================
	}


	//========================dropbox stuff===============================
	private void storeKeys(String key, String secret) {
		// TODO Auto-generated method stub
		 SharedPreferences settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
	        SharedPreferences.Editor prefEditor = settings.edit();
prefEditor.putString(KEY_PREFS_STORE_KEY, key);
prefEditor.putString(KEY_PREFS_STORE_secret, secret);
prefEditor.commit();
	}
	//========================dropbox stuff===============================
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()){
		
		case R.id.Badd:
		
			
		
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
				aa.notifyDataSetChanged();
				Log.i("Main", "Badd clicked with data set change == " + listData);
				
			  }
			});

			alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			  public void onClick(DialogInterface dialog, int whichButton) {
			    // Canceled.
			  }
			});
			
			alert.show();
		
			break;
		case R.id.Bsync:
			
			//========================dropbox stuff===============================
			AppKeyPair appKeys = new AppKeyPair(APP_KEY, APP_SECRET);
			AndroidAuthSession session = new AndroidAuthSession(appKeys, ACCESS_TYPE);
			mDBApi = new DropboxAPI<AndroidAuthSession>(session);
AccessTokenPair access = getStoredKeys();
			
mDBApi.getSession().setAccessTokenPair(access);
		
//uploadfile fs = new uploadfile();
Log.i("mainDropBoxTest", "button sync");
			if(mDBApi.getSession().authenticationSuccessful()){
				BackUpDatabase backupdatabase = new BackUpDatabase();
		backupdatabase.execute();
		Log.i("mainDropBoxTest", "button sync don");
	
			}else{
			
				
				mDBApi.getSession().startAuthentication(MainDropBoxTest.this);
				BackUpDatabase backupdatabase = new BackUpDatabase();
				backupdatabase.execute();
				Log.i("mainDropBoxTest", "button sync don");
			}
			//========================dropbox stuff===============================
			
			break;
		
	}
	}
	 public String convertStreamToString(InputStream is)
	            throws IOException {
	        /*
	         * To convert the InputStream to String we use the
	         * Reader.read(char[] buffer) method. We iterate until the
	         * Reader return -1 which means there's no more data to
	         * read. We use the StringWriter class to produce the string.
	         */
	        if (is != null) {
	            Writer writer = new StringWriter();

	            char[] buffer = new char[1024];
	            try {
	                Reader reader = new BufferedReader(
	                        new InputStreamReader(is, "UTF-8"));
	                int n;
	                while ((n = reader.read(buffer)) != -1) {
	                    writer.write(buffer, 0, n);
	                }
	            } finally {
	                is.close();
	            }
	            return writer.toString();
	        } else {        
	            return "";
	        }
	    }

	 private boolean copyFile(File src,File dst)throws IOException{
	        if(src.getAbsolutePath().toString().equals(dst.getAbsolutePath().toString())){

	            return true;

	        }else{
	            InputStream is=new FileInputStream(src);
	            OutputStream os=new FileOutputStream(dst);
	            byte[] buff=new byte[1024];
	            int len;
	            while((len=is.read(buff))>0){
	                os.write(buff,0,len);
	            }
	            is.close();
	            os.close();
	        }
	        return true;
	    }
	 public static byte[] getBytes(InputStream is) throws IOException {

		    int len;
		    int size = 1024;
		    byte[] buf;

		    if (is instanceof ByteArrayInputStream) {
		      size = is.available();
		      buf = new byte[size];
		      len = is.read(buf, 0, size);
		    } else {
		      ByteArrayOutputStream bos = new ByteArrayOutputStream();
		      buf = new byte[size];
		      while ((len = is.read(buf, 0, size)) != -1)
		        bos.write(buf, 0, len);
		      buf = bos.toByteArray();
		    }
		    return buf;
		  }

	private class BackUpDatabase extends AsyncTask<String, FileInputStream , Long>{



		@Override
		protected Long doInBackground(String... params) {
		    // TODO Auto-generated method stub
	
		 File data = Environment.getDataDirectory();


		     String currentDBPath = "\\simon.vestergaard.note.calender\\databases\\NoteCalender";
		     String backupDBPath = "NoteCalender";
		
		     // File fi = new File(database.getDatabasePath());
		     File locationfrom = new File(data + ""+data+"/simon.vestergaard.note.calender"+ "/databases",backupDBPath);
		     
		     File dest = new File(Environment.getExternalStorageDirectory()+"/NoteClander");
		     AppKeyPair appKeys = new AppKeyPair(APP_KEY, APP_SECRET);
				AndroidAuthSession session = new AndroidAuthSession(appKeys, ACCESS_TYPE);
				mDBApi = new DropboxAPI<AndroidAuthSession>(session);
				AccessTokenPair access = getStoredKeys();
				mDBApi.getSession().setAccessTokenPair(access);
				
				FileInputStream fin = null;
				try {
					fin = new FileInputStream(locationfrom);
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		     byte[] hej = null;
			
			
		    
		     
		    	 	// FileUtils.copyFileToDirectory(locationfrom, dest);
		    		 String fileContents = "Hello World!";
		    		 ByteArrayInputStream inputStream = new ByteArrayInputStream(fileContents.getBytes());
		    		 try {
		    			 Log.i("mainDropBoxTest", "button sync uplaod start");
		    			 String okay= getBytes(fin).toString();
		 				long hejiii = okay.length();
		    		    Entry newEntry = mDBApi.putFile("/testings.db", fin,
		    		            okay.length(), null, null);
		    		    Log.i("mainDropBoxTest", "button sync don upload don");
		    		    Log.i("DbExampleLog", "The uploaded file's rev is: " + newEntry.rev);
		    		 } catch (DropboxUnlinkedException e) {
		    		    // User has unlinked, ask them to link again here.
		    		    Log.e("DbExampleLog", "User has unlinked.");
		    		 } catch (DropboxException e) {
		    		    Log.e("DbExampleLog", "Something went wrong while uploading." +e);
		    		 } catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		    		 
		    		
				
		    	
			
		    return null;
		}

	
	

	
	
}
	



	private AccessTokenPair getStoredKeys() {
		// TODO Auto-generated method stub
		 SharedPreferences settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
	        SharedPreferences.Editor prefEditor = settings.edit();
	        String key = settings.getString(KEY_PREFS_STORE_KEY, "");
	        String secret = settings.getString(KEY_PREFS_STORE_secret, "");

		       AccessTokenPair hej = new AccessTokenPair(key,secret);

	        
	        return hej;
	}



	public void onItemClick(AdapterView<?> arg0, View view, int position, long arg3) {
		// TODO Auto-generated method stub
		
		
         
	
		dataFromPosition = listData.get(position);
		Intent i = new Intent("simon.vestergaard.note.calender.NoteSelector");
		Log.i("Main", "position data position="+position+ "  datafromPosition ="+dataFromPosition);
		startActivity(new Intent(this,NoteSelector.class));
		overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
	}
	public String getSelectedCategory(){
		Log.i("Main", "dataFromPosition ="+dataFromPosition);
		return this.dataFromPosition;
	}

}
