package com.swjeon.escaper.game.map;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;

import com.swjeon.escaper.game.map.tileset.TileSet;

import kr.ac.tukorea.ge.spgp2025.a2dg.framework.interfaces.IGameObject;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.res.BitmapPool;

public class TiledMap implements IGameObject {
    //tile set
    private final Bitmap tileSetImg;
    private final TileSet tileSet;

    //map data
    public static class MapSize{
        public int width;
        public int height;
        public MapSize(int width, int height){
            this.width = width;
            this.height = height;
        }
    }
    private final MapSize mapSize; //가로, 세로 타일 갯수
    private final int[] tileDatas; //타일 배치 정보
    private final float mapTileWidth; //화면에서의 타일 하나 길이(타일은 정사각형으로 가로 세로 길이는 동일하다고 간주)

    //데이터 해석을 위한 정보
    private final int firstGid; //타일셋의 global id 시작값(1개의 타일셋만 사용한다는 가정 하에 기본값은 1)
    //tile fliped flag 상수
    private static final int FLIPPED_H = 0x80000000;
    private static final int FLIPPED_V = 0x40000000;
    private static final int FLIPPED_D = 0x20000000;

    //for draw
    Rect srcRect = new Rect();
    RectF dstRect = new RectF();
    public TiledMap(int[] tileDatas, TileSet tileSet, MapSize mapSize, float mapTileWidth){
        this(tileDatas, tileSet, mapSize, mapTileWidth, 1);
    }
    public TiledMap(int[] tileDatas, TileSet tileSet, MapSize mapSize, float mapTileWidth, int firstGid){
        this.tileDatas = tileDatas;
        this.tileSetImg = BitmapPool.get(tileSet.getImageAndroidId());
        this.tileSet = tileSet;
        this.mapSize = mapSize;
        this.mapTileWidth = mapTileWidth;
        this.firstGid = firstGid;
    }

    @Override
    public void update() {//update는 하지 않을 예정이므로 삭제
    }

    @Override
    public void draw(Canvas canvas) {
        for(int i = 0; i < tileDatas.length; i++){
            float x = i % mapSize.width * mapTileWidth;
            float y = i / mapSize.height * mapTileWidth;
            drawTileAt(canvas, tileDatas[i], x, y);
        }
    }

    private void drawTileAt(Canvas canvas, int rawGid, float x, float y) {
        //gid 내 플래그 제거
        int gid = rawGid & ~(FLIPPED_H | FLIPPED_V | FLIPPED_D);

        //플래그 확인
        boolean isFlippedH = (rawGid & FLIPPED_H) != 0;
        boolean isFlippedV = (rawGid & FLIPPED_V) != 0;
        boolean isFlippedD = (rawGid & FLIPPED_D) != 0;

        //0-based 인덱스로 변경
        int tileIndex = gid - firstGid;
        if (tileIndex < 0) return;  //빈 공간일 때는 아무것도 그리지 않음

        //src Rect 계산
        int srcX = (tileIndex % (int)tileSet.getColumns()) * (int)tileSet.getTilewidth();
        int srcY = (tileIndex / (int)tileSet.getColumns()) * (int)tileSet.getTileheight();
        srcRect.set(srcX, srcY, srcX + (int)tileSet.getTilewidth(), srcY + (int)tileSet.getTileheight());


        //flip 플래그 적용하여 dst Rect 계산
        canvas.save();
        canvas.translate(x + mapTileWidth / 2f, y + mapTileWidth / 2f); //그려야 하는 타일의 중심으로 원점 이동
        if(isFlippedD){ //대각선 뒤집기 처리
            canvas.rotate(90);
        }
        float sx = isFlippedH ? -1f : 1f; //세로 뒤집기 처리
        float sy = isFlippedV ? -1f : 1f; //가로 뒤집기 처리
        canvas.scale(sx, sy);
        dstRect.set(-mapTileWidth /2f, -mapTileWidth /2f, mapTileWidth /2f, mapTileWidth /2f); //현재 타일의 정중앙에 있으므로 왼쪽 위부터 그려야한다.
        canvas.drawBitmap(tileSetImg, srcRect, dstRect, null);
        canvas.restore();
    }

}
