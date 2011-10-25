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
 * Class representing a block, having a position and type. The
 * type can be 0 to 7 depending on where on the playing field
 * the block is located. See {@link World#generateBlocks()}.
 * @author mzechner
 *
 */
public class Block {
    public static float WIDTH = 40;
    public static float HEIGHT = 18;
    public int type;
    public float x, y;
    
    public Block(float x, float y, int type) {
        this.x = x;
        this.y = y;
        this.type = type;
    }
}
