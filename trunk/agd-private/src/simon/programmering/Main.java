package simon.programmering;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Gallery;
import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLDisplay;
import javax.microedition.khronos.opengles.GL10;

import simon.programmering.tools.GameActivity;
import simon.programmering.tools.GameListener;



public class Main extends GameActivity implements GameListener {
    /** Called when the activity is first created. */
    
    private FloatBuffer vertices;
    private FloatBuffer colors;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setGameListener(this);
    }

	public void setup(GameActivity activity, GL10 gl) {
		// TODO Auto-generated method stub
		 ByteBuffer buffer = ByteBuffer.allocateDirect( 3 * 4 * 3 );
	       buffer.order(ByteOrder.nativeOrder());
	       vertices = buffer.asFloatBuffer();
	       vertices.put( -0.6f );
	       vertices.put( -0.4f );
	       vertices.put( 0 );
	       vertices.put( 0.5f );
	       vertices.put( -0.5f );
	       vertices.put( 0 );	
	       vertices.put( 0 );
	       vertices.put( 0.5f );
	       vertices.put( 0 );	
	       vertices.rewind();
	       buffer = ByteBuffer.allocateDirect( 3 * 4 * 4 );
	       buffer.order(ByteOrder.nativeOrder());
	       colors = buffer.asFloatBuffer();	
	       colors.put( 1 );
	       colors.put( 0 );
	       colors.put( 0 );
	       colors.put( 1 );
	       colors.put( 0 );
	       colors.put( 1 );
	       colors.put( 0 );
	       colors.put( 1 );	
	       colors.put( 0 );
	       colors.put( 0 );
	       colors.put( 1 );
	       colors.put( 1 );	
	       colors.rewind();
	}

	public void mainLoopIteration(GameActivity activity, GL10 gl) {
		// TODO Auto-generated method stub
		 gl.glViewport(0, 0, activity.getViewportWidth(), activity.getViewportHeight());
	       gl.glEnableClientState(GL10.GL_VERTEX_ARRAY );    
	       gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertices);
	       gl.glDrawArrays(GL10.GL_TRIANGLES, 0, 3);
	       gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
	       gl.glColorPointer(4, GL10.GL_FLOAT, 0, colors);
	}
}