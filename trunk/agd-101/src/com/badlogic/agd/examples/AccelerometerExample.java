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

import android.graphics.Bitmap;
import android.graphics.Color;

import com.badlogic.agd.Game;
import com.badlogic.agd.Screen;

public class AccelerometerExample extends Game {   
    public Screen createStartScreen() {
        return new AccelerometerScreen(this);
    }
    
    class AccelerometerScreen extends Screen {
        Bitmap bob;
        public AccelerometerScreen(Game game) {
            super(game);
            bob = game.loadBitmap("bob.png");
        }
               
        public void update(float deltaTime) {            
            float x = -game.getAccelerometer()[0];
            float y = game.getAccelerometer()[1];
            x = (x / 10) * game.getFramebufferWidth() / 2 + game.getFramebufferWidth() / 2;
            y = (y / 10) * game.getFramebufferHeight() / 2 + game.getFramebufferHeight() / 2;
            game.clearFramebuffer(Color.BLUE);            
            game.drawBitmap(bob, (int)x - 64, (int)y - 64);                            
        }
        
        public void pause() { }                    
        public void resume() { }        
        public void dispose() { }          
    }
}
