package simon.programming;

import simon.framework.Screen;
import simon.framework.impl.AndroidGame;

public class MrNomGame extends AndroidGame {
    
    public Screen getStartScreen() {
        return new LoadingScreen(this); 
    }
}