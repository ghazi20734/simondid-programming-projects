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
import com.badlogic.agd.Music;
import com.badlogic.agd.Screen;

public class MusicExample extends Game {
    public Screen createStartScreen() {
        return new MusicScreen(this);
    }    
    
    class MusicScreen extends Screen {
        Music music;
        boolean isPlaying = false;
        
        public MusicScreen(Game game) {
            super(game);
            music = game.loadMusic("music.ogg");
            music.setLooping(true); music.play();
            isPlaying = true;            
        }

        public void update(float deltaTime) {
            if(game.isTouchDown(0)) {
                if(music.isPlaying()) {
                    music.pause(); isPlaying = false;
                }
                else {
                    music.play(); isPlaying = true;
                }
            }
        }

        public void pause() { music.pause(); }               
        public void resume() { if(isPlaying) music.play(); }               
        public void dispose() { music.dispose(); }        
    }    
}
