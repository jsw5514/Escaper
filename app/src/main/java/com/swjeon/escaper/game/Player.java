package com.swjeon.escaper.game;

import android.util.Log;
import android.view.MotionEvent;

import com.swjeon.escaper.game.util.TiledSprite;

public class Player extends TiledSprite {
    private final String TAG = getClass().getSimpleName();
    float startX;
    float startY;

    public Player(int x, int y, float tileWidth) {
        super(x, y, tileWidth);
        SPRITE_WIDTH = 100f;
    }

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
