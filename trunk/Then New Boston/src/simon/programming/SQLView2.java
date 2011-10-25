package simon.programming;


import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class SQLView2 extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sqlview);
		TextView tv = (TextView) findViewById(R.id.tvSQLinfo);
		HotOrNot2 info = new HotOrNot2(this);
		
		info.open();
		String data = info.getData();
		info.close();
		tv.setText(data);
		
	}
}