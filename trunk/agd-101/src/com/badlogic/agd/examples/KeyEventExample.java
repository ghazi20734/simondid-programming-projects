/*******************************************************************************
 * Copyright 2011 Mario Zechner
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package com.badlogic.agd.examples;

import java.util.List;

import android.util.Log;

import com.badlogic.agd.Game;
import com.badlogic.agd.KeyEvent;
import com.badlogic.agd.Screen;

public class KeyEventExample extends Game {    
    public Screen createStartScreen() {
        return new KeyEventScreen(this);
    }

    class KeyEventScreen extends Screen {
        public KeyEventScreen(Game game) {
            super(game);
        }
       
        public void update(float deltaTime) {
            List<KeyEvent> keyEvents = game.getKeyEvents();
            for(int i = 0; i < keyEvents.size(); i++) {
                KeyEvent event = keyEvents.get(i);
                Log.d("KeyEventScreen", "key: " + 
                                        event.type + ", " + 
                                        event.keyCode + ", " + 
                                        event.character);
            }
        }
        
        public void pause() { }                    
        public void resume() { }        
        public void dispose() { }
    }
}
