package com.swjeon.escaper.game.object;

import android.graphics.Canvas;
import android.graphics.Point;
import android.util.Log;

import com.swjeon.escaper.game.MainScene;
import com.swjeon.escaper.game.map.TiledMap;
import com.swjeon.escaper.game.util.OnStageClearListener;

import java.util.ArrayList;
import java.util.Iterator;

import kr.ac.tukorea.ge.spgp2025.a2dg.framework.interfaces.IGameObject;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.util.CollisionHelper;

public class CollisionChecker implements IGameObject {
    private final String TAG = this.getClass().getSimpleName();
    private final MainScene scene;
    private OnStageClearListener listener;
    public CollisionChecker(MainScene mainScene) {
        scene = mainScene;
    }

    public void setOnStageClearListener(OnStageClearListener onStageClearListener) {
        listener = onStageClearListener;
    }

    @Override
    public void update() {
        //Log.d(TAG,"충돌체크 중");
        Player player = scene.getPlayer();

        //플레이어 <-> 적 충돌체크
        ArrayList<IGameObject> enemies = scene.objectsAt(MainScene.Layer.enemy);
        Iterator<IGameObject> enemyIt = enemies.iterator();
        while (enemyIt.hasNext()) {
            Enemy enemy = (Enemy) enemyIt.next();
            boolean colidesE = CollisionHelper.collides(player, enemy);
            if (colidesE) {
                Log.i(TAG, "플레이어와 적이 충돌함");
                player.onCollideEnemy();
                scene.resetScore();
            }
        }

        //플레이어 <-> 아이템 충돌체크
        ArrayList<IGameObject> items = scene.objectsAt(MainScene.Layer.item);
        Iterator<IGameObject> itemIt = items.iterator();
        ArrayList<Item> needToRemove = new ArrayList<>();
        while (itemIt.hasNext()) {
            Item item = (Item) itemIt.next();
            boolean colidesI = CollisionHelper.collides(player, item);
            if (colidesI) {
                Log.i(TAG, "플레이어와 아이템이 충돌함");
                scene.addScore(item.getScore());
                needToRemove.add(item);
            }
        }
        for (Item item:needToRemove) {
            scene.remove(MainScene.Layer.item, item);
        }

        if (scene.passWall) return; //check debug god mode
        //플레이어 <-> 특정 타일 충돌체크
        TiledMap map = (TiledMap) scene.objectsAt(MainScene.Layer.map).get(0);
        Point target = player.getTarget();
        if (map.isSolidTile(target.x, target.y)) {
            player.onCollideSolidTile();
        }
        if (map.isClearTile(target.x, target.y)) {
            notifyStageClear();
        }
    }

    private void notifyStageClear() {
        if (listener != null) {
            listener.onStageClear();
        }
    }

    @Override
    public void draw(Canvas canvas) {
    }
}
