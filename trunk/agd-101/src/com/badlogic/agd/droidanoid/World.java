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

import java.util.ArrayList;
import java.util.List;

/**
 * The World class is responsible for managing and updating the
 * game world. Keeps references to all game objects, updates them
 * and checks for collisions. Additionally calls a registered {@link CollisionListener}
 * to inform it of any events that happened in the game world.
 * @author mzechner
 *
 */
public class World {
    public static float MIN_X = 0, MIN_Y = 36;
    public static float MAX_X = 319, MAX_Y = 479;
    Ball ball = new Ball();
    Paddle paddle = new Paddle();
    List<Block> blocks = new ArrayList<Block>();
    boolean gameOver = false;
    int points = 0;
    CollisionListener listener;
    
    public World(CollisionListener listener) {
        generateBlocks();
        this.listener = listener;
    }
    
    private void generateBlocks() {
        blocks.clear();
        for(int y = 60, type = 0; y < 60 + 8 * Block.HEIGHT; y+= Block.HEIGHT, type++) {
            for(int x = 20; x < 320 - Block.WIDTH / 2; x+= Block.WIDTH) {                              
                blocks.add(new Block(x, y, type));
            }
        }
    }
    
    public void update(float deltaTime, float accelX) {
        if(blocks.size() == 0) generateBlocks();
        
        ball.x += ball.vx * deltaTime;
        ball.y += ball.vy * deltaTime;
        if(ball.x < MIN_X) { ball.vx = -ball.vx; ball.x = MIN_X; listener.collisionWall(); }
        if(ball.x > MAX_X - Ball.WIDTH) { ball.vx = -ball.vx; ball.x = MAX_X - Ball.WIDTH; listener.collisionWall(); }
        if(ball.y < MIN_Y) { ball.vy = -ball.vy; ball.y = MIN_Y; listener.collisionWall(); }
        if(ball.y > MAX_Y - Ball.HEIGHT) { gameOver = true; return; }
        
        paddle.x += -accelX * 50 * deltaTime;
        if(paddle.x < MIN_X) paddle.x = MIN_X;
        if(paddle.x + Paddle.WIDTH > MAX_X) paddle.x = MAX_X - Paddle.WIDTH;
        
        collideBallPaddle(deltaTime);
        collideBallBlocks(deltaTime);
    }
    
    private boolean collideRects(float x, float y, float width, float height,
                                 float x2, float y2, float width2, float height2) {
        if(x < x2 + width2 &&
           x + width  > x2 &&
           y < y2 + height2 &&
           y + width > y2)
            return true;
        return false;
    }             
    
    private void collideBallBlocks(float deltaTime) {
        for(int i = 0; i < blocks.size(); i++) {
            Block block = blocks.get(i);
            if(collideRects(ball.x, ball.y, Ball.WIDTH, Ball.HEIGHT,
                            block.x, block.y, Block.WIDTH, Block.HEIGHT)) {
                blocks.remove(i);
                float oldvx = ball.vx;
                float oldvy = ball.vy;
                reflectBall(ball, block);
                ball.x = ball.x - oldvx * deltaTime * 1.01f;
                ball.y = ball.y - oldvy * deltaTime * 1.01f;
                points += 10 - block.type;
                listener.collisionBlock();
                break;
            }
        }
    }
    
    private void reflectBall(Ball ball, Block block) {
        if(collideRects(ball.x, ball.y, Ball.WIDTH, Ball.HEIGHT, 
                        block.x, block.y, 1, 1)) {
            if(ball.vx > 0) ball.vx = -ball.vx;
            if(ball.vy > 0) ball.vy = -ball.vy;
            return;
        }
        if(collideRects(ball.x, ball.y, Ball.WIDTH, Ball.HEIGHT, 
                        block.x + Block.WIDTH, block.y, 1, 1)) {
            if(ball.vx < 0) ball.vx = -ball.vx;
            if(ball.vy > 0) ball.vy = -ball.vy;
            return;
        }
        
        if(collideRects(ball.x, ball.y, Ball.WIDTH, Ball.HEIGHT, 
                        block.x, block.y + Block.HEIGHT, 1, 1)) {
            if(ball.vx > 0) ball.vx = -ball.vx;
            if(ball.vy < 0) ball.vy = -ball.vy;
            return;
        }
        
        if(collideRects(ball.x, ball.y, Ball.WIDTH, Ball.HEIGHT, 
                        block.x + Block.WIDTH, block.y + Block.HEIGHT, 1, 1)) {
            if(ball.vx < 0) ball.vx = -ball.vx;
            if(ball.vy < 0) ball.vy = -ball.vy;
            return;
        }
        
        if(collideRects(ball.x, ball.y, Ball.WIDTH, Ball.HEIGHT,
                        block.x, block.y, Block.WIDTH, 1)) {
            ball.vy = -ball.vy;
            return;
        }
        
        if(collideRects(ball.x, ball.y, Ball.WIDTH, Ball.HEIGHT,
                        block.x, block.y + Block.HEIGHT, Block.WIDTH, 1)) {
            ball.vy = -ball.vy;
            return;
        }
        
        if(collideRects(ball.x, ball.y, Ball.WIDTH, Ball.HEIGHT,
                        block.x, block.y, 1, Ball.HEIGHT)) {
            ball.vx = -ball.vx;
            return;
        }
 
        if(collideRects(ball.x, ball.y, Ball.WIDTH, Ball.HEIGHT,
                block.x + Block.WIDTH, block.y, 1, Ball.HEIGHT)) {
            ball.vx = -ball.vx;
            return;
        }
    }
    
    private void collideBallPaddle(float deltaTime) {
        if(ball.y > paddle.y) return;
        if(ball.x >= paddle.x && 
           ball.x < paddle.x + Paddle.WIDTH &&
           ball.y + Ball.HEIGHT >= paddle.y) {
            ball.y = paddle.y - Ball.HEIGHT - 2;
            ball.vy = -ball.vy;   
            listener.collisionPaddle();
        }
    }       
}
