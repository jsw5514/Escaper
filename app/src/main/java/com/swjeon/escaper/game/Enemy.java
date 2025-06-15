package com.swjeon.escaper.game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PathMeasure;
import android.graphics.Point;
import android.graphics.RectF;

import com.swjeon.escaper.R;
import com.swjeon.escaper.game.map.EnemySpawnInfo;
import com.swjeon.escaper.game.util.TiledSprite;

import kr.ac.tukorea.ge.spgp2025.a2dg.framework.interfaces.IBoxCollidable;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.util.RectUtil;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.GameView;

public class Enemy extends TiledSprite implements IBoxCollidable {
    private final String TAG = this.getClass().getSimpleName();
    EnemySpawnInfo spawnInfo;
    private float speed = 500f;
    private float distance = 0f;
    private float[] pos = new float[2];
    private float[] tan = new float[2];
    private PathMeasure pm;
    public Enemy (EnemySpawnInfo spawnInfo, float tileWidth){
        this(spawnInfo.startPosition.x, spawnInfo.startPosition.y, tileWidth);
        this.spawnInfo = spawnInfo;
        pm = new PathMeasure(spawnInfo.patrolPath, false);
    }
    public Enemy(int x, int y, float tileWidth) {
        super(R.mipmap.enemy, x, y, tileWidth);
        spawnInfo = new EnemySpawnInfo();
        spawnInfo.startPosition = new Point(x, y);
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    private boolean isForward = true;
    @Override
    public void update() {
        if (pm == null) return; // 순찰경로가 없는 경우 바로 리턴
        if (isForward) { //forward
            distance += speed * GameView.frameTime;
            if (distance > pm.getLength()) {
                distance = pm.getLength();
                isForward = false;
            }
        }
        else { //backward
            distance -= speed * GameView.frameTime;
            if (distance < 0){
                distance = 0;
                isForward = true;
            }
        }
        pm.getPosTan(distance, pos, tan);
        setPosition(pos[0],pos[1]);
        super.update();
    }

    private static Paint paint;
    static {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10f);
        paint.setColor(Color.MAGENTA);
    }
    @Override
    public void draw(Canvas canvas) {
        canvas.save();
        canvas.translate(SPRITE_WIDTH/2,SPRITE_WIDTH/2);
        super.draw(canvas);
        if(GameView.drawsDebugStuffs) {
            canvas.drawPath(spawnInfo.patrolPath, paint);
        }
        canvas.restore();
    }

    RectF collisionRect = new RectF();
    @Override
    public RectF getCollisionRect() {
        collisionRect.set(dstRect.left + 50, dstRect.top + 50, dstRect.right + 50, dstRect.bottom + 50);
        return collisionRect;
    }
}
