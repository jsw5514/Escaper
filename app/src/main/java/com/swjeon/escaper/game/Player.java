package com.swjeon.escaper.game;

import android.util.Log;
import android.view.MotionEvent;

import com.swjeon.escaper.R;

import kr.ac.tukorea.ge.spgp2025.a2dg.framework.objects.Sprite;

public class Player extends Sprite {
    private final String TAG = getClass().getSimpleName();
    private final float TILE_WIDTH; //타일 1개의 가로,세로 길이
    private final float PLAYER_WIDTH = 100f;
    private int tiledX,tiledY;
    public Player(int x, int y, float tileWidth) {
        super(R.mipmap.player);
        TILE_WIDTH = tileWidth;
        setTiledPosition(x,y);
    }
    public void setTiledPosition(int x, int y){
        tiledX = x;
        tiledY = y;
        //왼쪽 위를 가리키는 좌표를 중심을 가리키는 좌표로 변환
        float centerX = x + 0.5f;
        float centerY = y + 0.5f;
        setPosition(centerX * TILE_WIDTH, centerY * TILE_WIDTH, PLAYER_WIDTH, PLAYER_WIDTH); //정사각형 타일에 맞추므로 PLAYER_WIDTH = PLAYER_HEIGHT
    }
    public int[] getTiledPosition(){
        return new int[]{tiledX, tiledY};
    }

    /* move player */
    float startX;
    float startY;
    enum MoveDirection{
        up, down, left, right
    }
    public boolean onTouch(MotionEvent event){
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                startX = event.getX();
                startY = event.getY();
                return true;
            case MotionEvent.ACTION_MOVE:
                return true;
            case MotionEvent.ACTION_UP:
                float horizontalMove = event.getX() - startX;
                float verticalMove= event.getY() - startY;
                if(Math.abs(verticalMove) > Math.abs(horizontalMove)){
                    //vertical move
                    if(verticalMove >= 0){
                        move(MoveDirection.down);
                    }
                    else{
                        move(MoveDirection.up);
                    }
                }
                else{
                    //horizontal move
                    if(horizontalMove >= 0){
                        move(MoveDirection.right);
                    }
                    else{
                        move(MoveDirection.left);
                    }
                }
                return true;
            default:
                return false;
        }
    }

    private void move(MoveDirection moveDirection) {
        Log.d(TAG,"Move to " + moveDirection);
        switch (moveDirection){
            case up:
                setTiledPosition(tiledX, tiledY - 1);
                break;
            case down:
                setTiledPosition(tiledX, tiledY + 1);
                break;
            case left:
                setTiledPosition(tiledX - 1, tiledY);
                break;
            case right:
                setTiledPosition(tiledX + 1, tiledY);
                break;
            default:
                throw new RuntimeException("invalid move direction");
        }
    }
}
