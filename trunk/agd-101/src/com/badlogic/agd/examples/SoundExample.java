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

import com.badlogic.agd.Game;
import com.badlogic.agd.Screen;
import com.badlogic.agd.Sound;

public class SoundExample extends Game {
    
    public Screen createStartScreen() {
        return new SoundScreen(this);
    }

    class SoundScreen extends Screen {
        Sound sound;
        
        public SoundScreen(Game game) {
            super(game);
            sound = game.loadSound("explosion.ogg");
        }

        public void update(float deltaTime) {
            if(game.isTouchDown(0)) sound.play(1);
        }
        
        public void pause() { }
        public void resume() { }        
        public void dispose() { sound.dispose(); }        
    }
}
