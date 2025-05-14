package com.swjeon.escaper.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;

import kr.ac.tukorea.ge.spgp2025.a2dg.framework.interfaces.IGameObject;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.res.BitmapPool;

public class TiledMap implements IGameObject {
    //tile set
    private final Bitmap tileSetImg;
    private TileSet tileSet;

    //map data
    private final int MAP_WIDTH = 21; //가로 타일 갯수
    private final int MAP_HEIGHT = 21; //세로 타일 갯수
    private final int MAP_TILE_WIDTH = 100; //화면에서의 타일 길이(타일은 정사각형으로 가로 세로 길이는 동일하다고 간주)
    private int[] tileDatas;

    //데이터 해석을 위한 정보
    private final int firstGid; //타일셋의 global id 시작값(1개의 타일셋만 사용한다는 가정 하에 기본값은 1)
    //tile fliped flag 상수
    private static final int FLIPPED_H = 0x80000000;
    private static final int FLIPPED_V = 0x40000000;
    private static final int FLIPPED_D = 0x20000000;
    public TiledMap(int[] tileDatas, TileSet tileSet){
        this(tileDatas, tileSet, 1); //명시적인 지정이 없으면 gid 시작을 기본값인 1로 사용
    }
    public TiledMap(int[] tileDatas, TileSet tileSet, int firstGid){
        this.tileDatas = tileDatas;
        this.tileSetImg = BitmapPool.get(tileSet.getImageAndroidId());
        this.tileSet = tileSet;
        this.firstGid = firstGid;
    }

    @Override
    public void update() {//update는 하지 않을 예정이므로 삭제
    }

    @Override
    public void draw(Canvas canvas) {
        for(int i = 0; i < tileDatas.length; i++){
            float x = i % MAP_WIDTH * MAP_TILE_WIDTH;
            float y = i / MAP_HEIGHT * MAP_TILE_WIDTH;
            drawTileAt(canvas, tileDatas[i], x, y);
        }
    }

    private void drawTileAt(Canvas canvas, int rawGid, float x, float y) {
        //gid 내 플래그 제거
        int gid = rawGid & ~(FLIPPED_H | FLIPPED_V | FLIPPED_D);
        //0-based 인덱스로 변경
        int tileIndex = gid - firstGid;
        if (tileIndex < 0) return;  //빈 공간일 때는 아무것도 그리지 않음

        //src Rect 계산
        int srcX = (tileIndex % (int)tileSet.getColumns()) * (int)tileSet.getTilewidth();
        int srcY = (tileIndex / (int)tileSet.getColumns()) * (int)tileSet.getTileheight();
        Rect src = new Rect(srcX, srcY, srcX + (int)tileSet.getTilewidth(), srcY + (int)tileSet.getTileheight());

        //dst Rect 계산
        RectF dst = new RectF(x, y, x + MAP_TILE_WIDTH, y + MAP_TILE_WIDTH);

        canvas.drawBitmap(tileSetImg, src, dst, null);
    }

}
