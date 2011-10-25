package simon.programming;
/*
 * dette er en anden version af private1 og 2 som gemmer daten på en anden måde
 */
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

	
public class private2n1 extends Activity implements OnClickListener {

	public static String filename = "MySharedString";
	SharedPreferences someData;
	
	Button bsave1,bsave2,bsave3,bDon;
	EditText input;
	
	 SharedPreferences.Editor editor;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Log.d("tag", "1");
		setContentView(R.layout.private1);
		Log.d("tag", "2");
		initialize();
		Log.d("tag", "3");
		someData = getSharedPreferences(filename, 0);
		Log.d("tag", "4");
		  editor =someData.edit();

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
	
		
		
		
	}

	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()){
		case R.id.bSave1:
			String data1 = input.getText().toString();
			editor.putString("save1", data1);
			
			
		break;
		case R.id.bSave2:
			String data2 = input.getText().toString();
			editor.putString("save2", data2);
			
			
		break;
		case R.id.bSave3:
			String data3 = input.getText().toString();
			editor.putString("save3", data3);
		break;
		case R.id.bDon:
			editor.commit();
		
			Intent a = new Intent(private2n1.this,private2n2.class);
			startActivity(a);
			
		break;
		}
		
	}

}
