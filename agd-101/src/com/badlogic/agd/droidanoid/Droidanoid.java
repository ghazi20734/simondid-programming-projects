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
package com.badlogic.agd.droidanoid;

import com.badlogic.agd.Game;
import com.badlogic.agd.Music;
import com.badlogic.agd.Screen;

/**
 * The Game implementation of Droidanoid. Responsible for
 * creating the MainMenuScreen and instantiating and managing
 * the Music instance.
 * @author mzechner
 *
 */
public class Droidanoid extends Game {
    Music music;
    
    public Screen createStartScreen() {
        music = this.loadMusic("music.ogg");
        music.setLooping(true);
        return new MainMenuScreen(this);
    }       
    
    public void onPause() {
        super.onPause();
        music.pause();
    }
    
    public void onResume() {
        super.onResume();
        music.play();
    }
}
