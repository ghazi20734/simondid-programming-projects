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

class SimpleScreen extends Screen {
    int x = 0, y = 0;
    Bitmap bitmap;
    
    public SimpleScreen(Game game) {
        super(game);
        bitmap = game.loadBitmap("mybitmap.png");
    }
    
    public void update(float deltaTime) {
        if(game.isTouchDown(0)) {
            x = game.getTouchX(0);
            y = game.getTouchY(0);
        }
        
        game.clearFramebuffer(Color.BLACK);
        game.drawBitmap(bitmap, x, y);
    }

    public void pause() { }      
    public void resume() { }
    public void dispose() { }        
}
