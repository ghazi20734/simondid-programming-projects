package simon.programming;

public class Main extends MapActivity {
    /** Called when the activity is first created. */
	
	MapView map;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        map = (MapView)findViewById(R.id.mvmain);
        map.setBuiltInZoomControls(true);
        Touchy t = new Touchy();
        List<Overlay> overlayList = map.getOverlays();
        overlayList.add(t);
        
        
    }

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
	class Touchy extends Overlay{
		public boolean onTouchEvent(MotionEvent e , MapView m){
			
			return false;
		}
	}
}