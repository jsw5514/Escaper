package com.swjeon.escaper.game;

import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.Point;

import com.swjeon.escaper.R;
import com.swjeon.escaper.game.map.EnemySpawnInfo;
import com.swjeon.escaper.game.util.TiledSprite;

import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.GameView;

public class Enemy extends TiledSprite {
    EnemySpawnInfo spawnInfo;
    float speed = 200f;
    float distance = 0f;
    float[] pos = new float[2];
    float[] tan = new float[2];
    PathMeasure pm;
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

    @Override
    public void update() {
        if (pm == null) return; // 순찰경로가 없는 경우 바로 리턴
        distance += speed * GameView.frameTime;
        distance = distance % (pm.getLength() * 2);
        pm.getPosTan(distance, pos, tan);
        setPosition(pos[0],pos[1]);
        super.update();
    }
}
