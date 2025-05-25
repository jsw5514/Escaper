package com.swjeon.escaper.game;

import com.swjeon.escaper.R;

import kr.ac.tukorea.ge.spgp2025.a2dg.framework.objects.Sprite;

public class Player extends Sprite {
    private final float TILE_WIDTH; //타일 1개의 가로,세로 길이
    private final float PLAYER_WIDTH = 100f;
    public Player(int x, int y, float tileWidth) {
        super(R.mipmap.player);
        TILE_WIDTH = tileWidth;
        setPosition(x,y);
    }
    public void setPosition(int x, int y){
        //왼쪽 위를 가리키는 좌표를 중심을 가리키는 좌표로 변환
        float centerX = x + 0.5f;
        float centerY = y + 0.5f;
        setPosition(centerX * TILE_WIDTH, centerY * TILE_WIDTH, PLAYER_WIDTH, PLAYER_WIDTH); //정사각형 타일에 맞추므로 PLAYER_WIDTH = PLAYER_HEIGHT
    }
}
