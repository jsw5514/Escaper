package com.swjeon.escaper.game;

import android.content.Context;
import android.view.MotionEvent;

import com.swjeon.escaper.R;
import com.swjeon.escaper.game.map.TiledMapManager;

import kr.ac.tukorea.ge.spgp2025.a2dg.framework.scene.Scene;

public class MainScene extends Scene {
    TiledMapManager mapManager;
    Player player;
    Context context;
    private final float MAP_TILE_WIDTH = 100f;
    private int stage;
    public enum Layer{
         map, enemy, player;
        public static final int COUNT = values().length;
    }
    public MainScene(Context context){
        initLayers(Layer.COUNT);
        stage = 0;

        this.context=context;
        this.mapManager = new TiledMapManager(context, R.mipmap.tileset, R.raw.free_tile_set, MAP_TILE_WIDTH);
        add(Layer.map, mapManager.getMap(stage));

        setGObjPos();
    }

    private void setGObjPos() {
        if (player == null) {
            player = new Player(mapManager.getPlayerStart(stage), MAP_TILE_WIDTH);
        }
        add(Layer.player, player);

        add(Layer.enemy, new Enemy(19, 1, MAP_TILE_WIDTH));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return player.onTouch(event);
    }
}
