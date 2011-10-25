package simon.programming;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.Time;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

public class Tabs extends Activity implements OnClickListener {
	TabHost th;
	TextView showResults;
	long start,stop;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tabs);
		
		Button newTab = (Button)findViewById(R.id.bAddTab);
		Button bStart = (Button)findViewById(R.id.bStartWatch);
		Button bstop = (Button)findViewById(R.id.bStopWatch);
		
		showResults =(TextView)findViewById(R.id.tvShowResults);

		newTab.setOnClickListener(this);
		bStart.setOnClickListener(this);
		bstop.setOnClickListener(this);
		
		
		 th = (TabHost)findViewById(R.id.tabhost);
		
		th.setup();
		TabSpec specs = th.newTabSpec("tag1");
		specs.setContent(R.id.tab1);
		specs.setIndicator("StopWatch");
		th.addTab(specs);
		 specs = th.newTabSpec("tag2");
		specs.setContent(R.id.tab2);
		specs.setIndicator("tab2");
		th.addTab(specs);
		 specs = th.newTabSpec("tag3");
		specs.setContent(R.id.tab3);
		specs.setIndicator("tab3");
		th.addTab(specs);
		
		
		start =0;
	}
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()){
		
		case R.id.bAddTab:					//laver en ny tab med et textView!!!!!!!!!!!!!!!!!!!!!!!rigtig good kode the new boston tut 86!!!!!!!!!
			
			TabSpec ourSpec = th.newTabSpec("tag1");
			ourSpec.setContent(new TabHost.TabContentFactory() {
				
				public View createTabContent(String tag) {
					// TODO Auto-generated method stub
					TextView text = new TextView(Tabs.this);
					text.setText("you've created a new tab ");
					
					Intent a = new Intent("simon.programming.Camara");
					startActivity(a);
					
					return (text);
				}
			});
			ourSpec.setIndicator("New");
			th.addTab(ourSpec);
			
			break;
		case R.id.bStartWatch:
			start = System.currentTimeMillis();
			break;
		case R.id.bStopWatch:
			stop = System.currentTimeMillis();
			if(start !=0){
				long result = stop - start;
				int millis = (int)result;
				int seconds = (int)result/1000;
				int minutes = seconds/60;
				
				millis = millis / 100;
				seconds = seconds/60;
				showResults.setText(String.format("%d:%02d:%02d", minutes,seconds,millis));
				
			}
			break;
		}
		
	}
}