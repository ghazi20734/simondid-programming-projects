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

/**
 * Class representing the paddle, having a position in pixels.
 * @author mzechner
 *
 */
public class Paddle {
    public static float WIDTH = 56;
    public static float HEIGHT = 11;
    public float x = 160 - WIDTH / 2, y = World.MAX_Y - 40;    
}
