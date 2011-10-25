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

import android.graphics.Bitmap;

import com.badlogic.agd.Game;

/**
 * The WorldRenderer class takes a {@link World} instance and draws
 * its current state with the help of a Game instance.
 * @author mzechner
 *
 */
public class WorldRenderer {
    Game game;
    World world;
    Bitmap ballImage;
    Bitmap paddleImage;
    Bitmap blocksImage;
    
    public WorldRenderer(Game game, World world) {
        this.game = game;
        this.world = world;
        this.ballImage = game.loadBitmap("ball.png");
        this.paddleImage = game.loadBitmap("paddle.png");
        this.blocksImage = game.loadBitmap("blocks.png");
    }
    
    public void render() {
        game.drawBitmap(ballImage, (int)world.ball.x, (int)world.ball.y);
        game.drawBitmap(paddleImage, (int)world.paddle.x, (int)world.paddle.y);
        for(int i = 0; i < world.blocks.size(); i++) {
            Block block = world.blocks.get(i);
            game.drawBitmap(blocksImage, (int)block.x, (int)block.y,
                            0, (int)(block.type * Block.HEIGHT),
                            (int)Block.WIDTH, (int)Block.HEIGHT);
        }
    }       
}
