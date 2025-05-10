package com.swjeon.escaper.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;

import com.swjeon.escaper.R;

import kr.ac.tukorea.ge.spgp2025.a2dg.framework.interfaces.IGameObject;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.res.BitmapPool;

public class TiledMap implements IGameObject {
    private Bitmap tileSet;
    private final int TILE_WIDTH = 32;
    private final int TILE_HEIGHT = 32;
    private final int TILE_SET_COLMNS = 32;
    private int[] tileDatas;
    public TiledMap(int[] tileDatas){
        this.tileDatas = tileDatas;
        this.tileSet = BitmapPool.get(R.mipmap.tileset);
    }

    @Override
    public void update() {//update는 하지 않을 예정이므로 삭제
    }

    @Override
    public void draw(Canvas canvas) {
        for(int i = 0; i < tileDatas.length; i++){
            int x = i % 21 * 10;
            int y = i / 21 * 10;
            drawTileAt(canvas, tileDatas[i], x, y);
        }
    }

    private void drawTileAt(Canvas canvas, int tileIndex, int x, int y) {
        int srcX = (tileIndex % TILE_SET_COLMNS) * TILE_WIDTH;
        int srcY = (tileIndex / TILE_SET_COLMNS) * TILE_HEIGHT;
        Rect srcRect = new Rect(srcX, srcY, srcX + TILE_WIDTH, srcY + TILE_HEIGHT);
        RectF dstRect = new RectF(x, y, x + TILE_WIDTH, y + TILE_HEIGHT);
        canvas.drawBitmap(tileSet, srcRect, dstRect, null);
    }
}
