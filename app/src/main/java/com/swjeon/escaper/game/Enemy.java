package com.swjeon.escaper.game;

import com.swjeon.escaper.R;
import com.swjeon.escaper.game.util.TiledSprite;

public class Enemy extends TiledSprite {
    public Enemy(int x, int y, float tileWidth) {
        super(R.mipmap.enemy, x, y, tileWidth);
    }

}
