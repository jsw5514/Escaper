package com.swjeon.escaper.game.util;

import kr.ac.tukorea.ge.spgp2025.a2dg.framework.objects.Sprite;

public class TiledSprite extends Sprite {
    protected final float TILE_WIDTH; //타일 1개의 가로,세로 길이
    protected float SPRITE_WIDTH;
    protected float tiledX,tiledY;
    public TiledSprite(int mipmapId, int x, int y, float tileWidth) {
        super(mipmapId);
        TILE_WIDTH = tileWidth;
        SPRITE_WIDTH = TILE_WIDTH;
        setTiledPosition(x,y);
    }
    public void setTiledPosition(float x, float y){
        tiledX = x;
        tiledY = y;
        //왼쪽 위를 가리키는 좌표를 중심을 가리키는 좌표로 변환
        float centerX = x + 0.5f;
        float centerY = y + 0.5f;
        setPosition(centerX * TILE_WIDTH, centerY * TILE_WIDTH, SPRITE_WIDTH, SPRITE_WIDTH); //정사각형 타일에 맞추므로 SPRITE_WIDTH = SPRITE_HEIGHT
    }
    public float[] getTiledPosition(){
        return new float[]{tiledX, tiledY};
    }
}
