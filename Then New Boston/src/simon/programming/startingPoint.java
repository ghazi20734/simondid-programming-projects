package simon.programming;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class startingPoint extends Activity {
    /** Called when the activity is first created. */
	
	/*varibal*/
	int counter;
	Button add;
	Button subtract;
	TextView display;
	
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        counter = 0;
    add = (Button) findViewById(R.id.ButtonAdd);
    subtract = (Button) findViewById(R.id.ButtonSubtract);    
    display =(TextView) findViewById(R.id.textview);
    
    
    add.setOnClickListener(new View.OnClickListener() {
		
		public void onClick(View v) {
			// TODO Auto-generated method stub
		counter++;
		display.setText("youre count is "+counter);
		
		}
	});
subtract.setOnClickListener(new View.OnClickListener() {
		
		public void onClick(View v) {
			// TODO Auto-generated method stub
		counter--;
		display.setText("youre count is "+counter);
		
		}
	});
    
    
    }
}