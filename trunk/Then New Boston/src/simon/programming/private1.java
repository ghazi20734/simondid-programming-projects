package simon.programming;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class private1 extends Activity implements OnClickListener {

	
	Button bsave1,bsave2,bsave3,bDon;
	EditText input;
	String inputData1,inputData2,inputData3;
	Intent a;
	Bundle basket1,basket2,basket3;
	String bread;
	Bundle basket;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.private1);
		initialize();


	}

	private void initialize() {
		// TODO Auto-generated method stub
		input = (EditText)findViewById(R.id.edInput);
		bsave1 = (Button) findViewById(R.id.bSave1);
		bsave2 = (Button)findViewById(R.id.bSave2);
		bsave3 = (Button)findViewById(R.id.bSave3);
		bDon = (Button) findViewById(R.id.bDon);
		bDon.setOnClickListener(this);
		bsave1.setOnClickListener(this);
		bsave2.setOnClickListener(this);
		bsave3.setOnClickListener(this);
		basket = new Bundle();
		
		
		
	}

	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()){
		case R.id.bSave1:
			bread = input.getText().toString();
			
			basket.putString("key1", bread);
			a = new Intent(private1.this,private2.class);
			a.putExtras(basket);
			input.setText("");
			
		break;
		case R.id.bSave2:
			 bread = input.getText().toString();
			
			basket.putString("key2", bread);
			a = new Intent(private1.this,private2.class);
			a.putExtras(basket);
			input.setText("");
			
		break;
		case R.id.bSave3:
			 bread = input.getText().toString();
			
			basket.putString("key3", bread);
			a = new Intent(private1.this,private2.class);
			a.putExtras(basket);
			input.setText("");
			
		break;
		case R.id.bDon:
		
			
			startActivity(a);
			
		break;
		}
		
	}

}
