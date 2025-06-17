package com.swjeon.escaper.game.object;

import android.graphics.Point;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;

import com.swjeon.escaper.R;
import com.swjeon.escaper.game.util.TiledSprite;

import kr.ac.tukorea.ge.spgp2025.a2dg.framework.interfaces.IBoxCollidable;

public class Player extends TiledSprite implements IBoxCollidable {
    private final String TAG = getClass().getSimpleName();
    private int startX, startY;
    private int targetX, targetY; //이동 목표
    private float speed = 0.1f;
    private int beforeX;
    private int beforeY;

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
        beforeX = targetX;
        beforeY = targetY;
        targetX = (int) x;
        targetY = (int) y;
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

    public Point getTarget(){
        return new Point(targetX, targetY);
    }

    public void setStartPos(Point startPos) {
        startX = startPos.x;
        startY = startPos.y;
    }

    public void onCollideEnemy(){
        setStaticTiledPosition(startX, startY);
    }

    public void setStaticTiledPosition(Point pos){
        this.setStaticTiledPosition(pos.x, pos.y);
    }
    public void setStaticTiledPosition(int x, int y) {
        moveTiledPosition(x, y);
        setTiledPosition(x, y);
    }

    public void onCollideSolidTile(){
        targetX = beforeX;
        targetY = beforeY;
    }
}
