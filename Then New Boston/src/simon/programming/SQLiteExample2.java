package simon.programming;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SQLiteExample2 extends Activity implements OnClickListener{

	Button sqlUpdate , sqlView;
	EditText sqlName, sqlHotness;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sqliteexample);
		
		sqlUpdate =(Button)findViewById(R.id.bSQLUpdate);
		sqlName = (EditText)findViewById(R.id.etSQLName);
		sqlHotness =(EditText)findViewById(R.id.etSQLHotness);
		sqlView =(Button)findViewById(R.id.bSQLopenView);
		sqlView.setOnClickListener(this);
		sqlUpdate.setOnClickListener(this);
		
		
	}

	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
		switch(arg0.getId()){
		
		case R.id.bSQLUpdate:
			boolean didItWork = true;
			try{
			
			String name = sqlName.getText().toString();
			String hotness = sqlName.getText().toString();
			HotOrNot2 entry = new HotOrNot2(SQLiteExample2.this);
			entry.open();
			entry.createEntry(name,hotness);
			
			entry.close();
			
			}catch (Exception e){ 
				didItWork = false;
				String error = e.toString();
				Dialog d = new Dialog(this);
				d.setTitle("did not work");
				TextView tv = new TextView(this);
				tv.setText("success");
				d.setContentView(tv);
				d.show();
				}
			finally{
				if (didItWork){
				Dialog d = new Dialog(this);
				d.setTitle("heck yyea!");
				TextView tv = new TextView(this);
				tv.setText("success");
				d.setContentView(tv);
				d.show();
				}
			}
			break;
		case R.id.bSQLopenView:
			Intent i = new Intent("simon.programming.SQLVIEW");
			startActivity(i);
			
			break;
		
		}
		
	}

}
