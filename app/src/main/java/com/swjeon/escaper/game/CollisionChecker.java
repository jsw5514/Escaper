package com.swjeon.escaper.game;

import android.graphics.Canvas;
import android.util.Log;

import com.swjeon.escaper.game.map.Item;

import java.util.ArrayList;
import java.util.Iterator;

import kr.ac.tukorea.ge.spgp2025.a2dg.framework.interfaces.IGameObject;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.util.CollisionHelper;

public class CollisionChecker implements IGameObject {
    private final String TAG = this.getClass().getSimpleName();
    private final MainScene scene;
    public CollisionChecker(MainScene mainScene) {
        scene = mainScene;
    }

    @Override
    public void update() {
        Player player = scene.player;

        //플레이어 <-> 적 충돌체크
        ArrayList<IGameObject> enemies = scene.objectsAt(MainScene.Layer.enemy);
        Iterator<IGameObject> enemyIt = enemies.iterator();
        while (enemyIt.hasNext()) {
            Enemy enemy = (Enemy) enemyIt.next();
            boolean colidesE = CollisionHelper.collides(player, enemy);
            if (colidesE) {
                Log.i(TAG, "플레이어와 적이 충돌함");
                player.onCollideEnemy();
            }
        }

        //플레이어 <-> 아이템 충돌체크
        ArrayList<IGameObject> items = scene.objectsAt(MainScene.Layer.item);
        Iterator<IGameObject> itemIt = items.iterator();
        while (itemIt.hasNext()) {
            Item item = (Item) itemIt.next();
            boolean colidesI = CollisionHelper.collides(player, item);
            if (colidesI) {
                Log.i(TAG, "플레이어와 아이템이 충돌함");
                scene.remove(MainScene.Layer.item, item);
                //TODO 점수 획득
            }
        }

        //TODO 플레이어 <-> 특정 타일 충돌체크
    }

    @Override
    public void draw(Canvas canvas) {
    }
}
