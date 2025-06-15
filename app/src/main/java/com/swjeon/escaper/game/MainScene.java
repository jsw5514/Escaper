package com.swjeon.escaper.game;

import android.content.Context;
import android.graphics.Point;
import android.util.Log;
import android.view.MotionEvent;

import com.swjeon.escaper.R;
import com.swjeon.escaper.game.map.EnemySpawnInfo;
import com.swjeon.escaper.game.map.Item;
import com.swjeon.escaper.game.map.TiledMapManager;
import com.swjeon.escaper.game.util.OnStageClearListener;

import java.util.ArrayList;
import java.util.Iterator;

import kr.ac.tukorea.ge.spgp2025.a2dg.framework.interfaces.IGameObject;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.scene.Scene;

public class MainScene extends Scene implements OnStageClearListener {
    private final String TAG = this.getClass().getSimpleName();
    TiledMapManager mapManager;
    Player player;
    CollisionChecker collisionChecker;
    Context context;
    private final float MAP_TILE_WIDTH = 100f;
    private int stage;

    public enum Layer{
         map, item, enemy, player, controller;
        public static final int COUNT = values().length;
    }
    public MainScene(Context context){
        Log.d(TAG,"메인씬 생성됨");
        initLayers(Layer.COUNT);
        stage = 0;

        this.context=context;
        this.mapManager = new TiledMapManager(context, R.mipmap.tileset, R.raw.free_tile_set, MAP_TILE_WIDTH);
        add(Layer.map, mapManager.getMap(stage));

        setGObjPos();

        collisionChecker = new CollisionChecker(this);
        collisionChecker.setOnStageClearListener(this);
        add(Layer.controller, collisionChecker);
    }

    @Override
    public void onStageClear() {
        objectsAt(Layer.enemy).clear();
        objectsAt(Layer.item).clear();
        objectsAt(Layer.map).clear();

        if(++stage >= 3) {
            Log.w(TAG, "게임 클리어. 곧 메인씬이 pop 됨.");
            objectsAt(Layer.controller).clear();
            collisionChecker = null;
            pop();
            return;
        }
        add(Layer.map, mapManager.getMap(stage));
        setGObjPos();
    }

    private void setGObjPos() {
        if (player == null) {
            player = new Player(mapManager.getPlayerStart(stage), MAP_TILE_WIDTH);
            add(Layer.player, player);
        }
        else {
            Point startPos = mapManager.getPlayerStart(stage);
            player.setStartPos(startPos);
            player.setStaticTiledPosition(startPos);
        }

        ArrayList<EnemySpawnInfo> spawnInfos = mapManager.getEnemyInfos(stage);
        for (EnemySpawnInfo spawnInfo:spawnInfos){
            add(Layer.enemy, new Enemy(spawnInfo, MAP_TILE_WIDTH));
        }

        add(Layer.item, new Item(Item.Type.orange, 1, 1, MAP_TILE_WIDTH));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return player.onTouch(event);
    }
}
