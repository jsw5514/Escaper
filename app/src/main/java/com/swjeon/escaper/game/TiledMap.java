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
    private final int TILE_SET_COLUMNS = 32;
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
            float x = i % 21 * 100;
            float y = i / 21 * 100;
            drawTileAt(canvas, tileDatas[i], x, y);
        }
    }
    private static final int FLIPPED_H = 0x80000000;
    private static final int FLIPPED_V = 0x40000000;
    private static final int FLIPPED_D = 0x20000000;
    private static final int FIRST_GID  = 1;  // tiled 맵에 지정된 값

    private void drawTileAt(Canvas canvas, int rawGid, float x, float y) {
        //gid 내 플래그 제거
        int gid = rawGid & ~(FLIPPED_H | FLIPPED_V | FLIPPED_D);
        // 2) 0-based 인덱스로
        int tileIndex = gid - FIRST_GID;
        if (tileIndex < 0) return;  // 빈 공간, or 객체 레이어일 때 예외 처리

        // 3) src 계산
        int srcX = (tileIndex % TILE_SET_COLUMNS) * TILE_WIDTH;
        int srcY = (tileIndex / TILE_SET_COLUMNS) * TILE_HEIGHT;
        Rect src = new Rect(srcX, srcY, srcX + TILE_WIDTH, srcY + TILE_HEIGHT);

        // 4) dst 계산 (예: 셀 크기 == 타일 크기)
        RectF dst = new RectF(x, y, x + 100, y + 100);

        canvas.drawBitmap(tileSet, src, dst, null);
    }

}
