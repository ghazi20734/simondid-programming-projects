package simon.learning;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class Test_listview_customActivity extends Activity {
    /** Called when the activity is first created. */
	String data[] = {"SQliteExample","ExternalData","InternalData","private2n2","private2n1","Sharedprefs","Flipper","startingPoint","TextPlay","Email","Camara","Splash","Data","private1","Slider","Tabs","SimpleBrowser"};
	
	ArrayList<String> dataList = new ArrayList<String>();
	ArrayList<String> secondDataList = new ArrayList<String>();
	FancyAdapter aa = null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        ListView mylistview = (ListView)findViewById(R.id.listView1);
        
        aa = new FancyAdapter();
        
        
        
        for (int i = 0; i < data.length; i++) {
    		
    		dataList.add(data[i]);
    	}
for (int i = 0; i < data.length; i++) {
    		
    		secondDataList.add(data[i]);
    	}
        
      //  mylistview.setAdapter(new ArrayAdapter<String>(this, R.layout.custom_list_item,R.id.fristText, dataList));
        mylistview.setAdapter(aa);
        
    }
    class FancyAdapter extends ArrayAdapter<String>{
    	FancyAdapter(){
    		super(Test_listview_customActivity.this, android.R.layout.simple_list_item_2, dataList);
    		
    	}
    	public View getView (int position, View convertView,ViewGroup parent){
    		View row = convertView;
    		
    		if ( row ==null){
    			LayoutInflater inflater = getLayoutInflater();
    			row=inflater.inflate(R.layout.custom_list_item, null);
    			
    		}
    		((TextView)row.findViewById(R.id.firstText)).setText(dataList.get(position));
    		((TextView)row.findViewById(R.id.secondText)).setText(secondDataList.get(position));
    	
    		return row;
    	}
    }
}