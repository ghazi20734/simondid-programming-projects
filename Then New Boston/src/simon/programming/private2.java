package simon.programming;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class private2 extends Activity implements OnClickListener {

	Button bReturn;
	TextView spot1,spot2,spot3;
	String gotBread1,gotBread2,gotBread3;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.private2);
		
		initialize();
		Bundle gotBasket = getIntent().getExtras();
		gotBread1 = gotBasket.getString("key1");
		gotBread2 = gotBasket.getString("key2");
		gotBread3 = gotBasket.getString("key3");

		spot1.setText(gotBread1);
		spot2.setText(gotBread2);
		spot3.setText(gotBread3);


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
			Intent i = new Intent(private2.this, private1.class);
			startActivity(i);
			break;
		}
		
	}

}