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
package com.badlogic.agd.workshop;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap;
import android.graphics.Color;

import com.badlogic.agd.Game;
import com.badlogic.agd.Screen;
import com.badlogic.agd.droidanoid.Ball;
import com.badlogic.agd.droidanoid.Block;
import com.badlogic.agd.droidanoid.Paddle;

public class MoverExample extends Game {    
    public Screen createStartScreen() {
        return new MoverScreen(this);
    }   
    
    class MoverScreen extends Screen {
        Bitmap bitmap, paddleImage, blocksImage;
        float x, y; 
        float vx = 100, vy = 100;
        Paddle paddle;
        List<Block> blocks = new ArrayList<Block>();

        public MoverScreen(Game game) {
            super(game);
            bitmap = game.loadBitmap("bob32.png");
            paddleImage = game.loadBitmap("paddle.png");
            blocksImage = game.loadBitmap("blocks.png");
            paddle = new Paddle();
            generateBlocks();
        }
        
        private void generateBlocks() {
            blocks.clear();
            for(int y = 60, type = 0; y < 60 + 8 * Block.HEIGHT; y+= Block.HEIGHT, type++) {
                for(int x = 20; x < 320 - Block.WIDTH / 2; x+= Block.WIDTH) {                              
                    blocks.add(new Block(x, y, type));
                }
            }
        }
        
        private void collideBallPaddle(float deltaTime) {
            if(y > paddle.y) return;
            if(x >= paddle.x && 
               x < paddle.x + Paddle.WIDTH &&
               y + Ball.HEIGHT >= paddle.y) {
                y = paddle.y - Ball.HEIGHT - 2;
                vy = -vy;                   
            }
        }     
        
        float accumulator = 0;
        
        public void update(float deltaTime) {
            game.clearFramebuffer(Color.BLACK);
            
            accumulator += deltaTime;
            while(accumulator > 1 /60f) {
                x = x + vx * deltaTime;
                y = y + vy * deltaTime;                       
                if(x + 32 > 319) { vx = -vx; x = 319 - 32; }
                if(y + 32 > 479) { vy = -vy; y = 479 - 32; }
                if(x < 0) { vx = -vx; x = 0; }
                if(y < 0) { vy = -vy; y = 0; }
                
                float accelX = -game.getAccelerometer()[0];            
                paddle.x = paddle.x + 50 * accelX * deltaTime;
                collideBallPaddle(deltaTime);
                accumulator -= 1 /60f;
            }
            
            game.drawBitmap(bitmap, (int)x, (int)y);
            game.drawBitmap(paddleImage, (int)paddle.x, (int)paddle.y);
            for(int i = 0; i < blocks.size(); i++) {
                Block block = blocks.get(i);
                game.drawBitmap(blocksImage, (int)block.x, (int)block.y,
                                0, (int)(block.type * Block.HEIGHT),
                                (int)Block.WIDTH, (int)Block.HEIGHT);
            }
        }

        public void pause() {
            
        }
        
        public void resume() {
            
        }

        public void dispose() {
            
        }        
    }
}
