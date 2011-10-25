package simon.programming;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class private2n2 extends Activity implements OnClickListener {

	Button bReturn;
	TextView spot1,spot2,spot3;
	String gotBread1,gotBread2,gotBread3;
	public static String filename = "MySharedString";
	SharedPreferences someData;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.private2);
		
		initialize();
		someData = getSharedPreferences(filename, 0);
		
		
		String dataReturn1 = someData.getString("save1", "couldn't load data");
		spot1.setText(dataReturn1);
		String dataReturn2 = someData.getString("save2", "couldn't load data");
		spot2.setText(dataReturn2);
		String dataReturn3 = someData.getString("save3", "couldn't load data");
		spot3.setText(dataReturn3);
	


	}

	private void initialize() {
		// TODO Auto-generated method stub
		spot1 = (TextView) findViewById(R.id.tvSpot1);
		spot2 = (TextView)findViewById(R.id.tvSpot2);
		spot3 = (TextView)findViewById(R.id.tvSpot3);
		bReturn = (Button)findViewById(R.id.bReturn);
		bReturn.setOnClickListener(this);
		
	}

	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()){
		
		case R.id.bReturn:
			Intent i = new Intent(private2n2.this, private2n1.class);
			startActivity(i);
			break;
		}
		
	}

}