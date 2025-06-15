package com.swjeon.escaper.game;

import android.graphics.Point;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;

import com.swjeon.escaper.R;
import com.swjeon.escaper.game.util.TiledSprite;

import kr.ac.tukorea.ge.spgp2025.a2dg.framework.interfaces.IBoxCollidable;

public class Player extends TiledSprite implements IBoxCollidable {
    private final String TAG = getClass().getSimpleName();
    private float startX, startY;
    private float targetX, targetY; //이동 목표
    private float speed = 0.1f;

    public Player(Point pos, float tileWidth) {
        this(pos.x, pos.y, tileWidth);
    }

    public Player(int x, int y, float tileWidth) {
        super(R.mipmap.player, x, y, tileWidth);
        startX = targetX = x;
        startY = targetY = y;
        SPRITE_WIDTH = 100f;
    }

    enum MoveDirection{
        up, down, left, right
    }

    float touchStartX, touchStartY;
    public boolean onTouch(MotionEvent event){
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                touchStartX = event.getX();
                touchStartY = event.getY();
                return true;
            case MotionEvent.ACTION_MOVE:
                return true;
            case MotionEvent.ACTION_UP:
                float horizontalMove = event.getX() - touchStartX;
                float verticalMove= event.getY() - touchStartY;
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
                moveTiledPosition(tiledX, tiledY - 1);
                break;
            case down:
                moveTiledPosition(tiledX, tiledY + 1);
                break;
            case left:
                moveTiledPosition(tiledX - 1, tiledY);
                break;
            case right:
                moveTiledPosition(tiledX + 1, tiledY);
                break;
            default:
                throw new RuntimeException("invalid move direction");
        }
    }

    public void moveTiledPosition(float x, float y) {
        targetX = x;
        targetY = y;
    }

    @Override
    public void update() {
        if (tiledX < targetX){
            tiledX += speed;
            if (tiledX > targetX) tiledX = targetX;
        }
        else if (tiledX > targetX){
            tiledX -= speed;
            if (tiledX < targetX) tiledX = targetX;
        }
        if (tiledY < targetY) {
            tiledY += speed;
            if (tiledY > targetY) tiledY = targetY;
        }
        else if (tiledY > targetY) {
            tiledY -= speed;
            if (tiledY < targetY) tiledY = targetY;
        }
        setTiledPosition(tiledX, tiledY);
    }

    @Override
    public RectF getCollisionRect() {
        return dstRect;
    }

    public void onCollideEnemy(){
        moveTiledPosition(startX, startY);
        setTiledPosition(startX, startY);
    }
}
