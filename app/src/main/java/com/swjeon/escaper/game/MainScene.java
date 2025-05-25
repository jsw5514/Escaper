package com.swjeon.escaper.game;

import android.content.Context;
import android.view.MotionEvent;

import com.swjeon.escaper.R;

import kr.ac.tukorea.ge.spgp2025.a2dg.framework.scene.Scene;

public class MainScene extends Scene {
    TiledMapManager mapManager;
    Player player;
    Context context;
    private final float MAP_TILE_WIDTH = 100f;
    private int stage = 0;
    public enum Layer{
         map, player;
        public static final int COUNT = values().length;
    }
    public MainScene(Context context){
        initLayers(Layer.COUNT);

        this.context=context;
        this.mapManager = new TiledMapManager(context, R.mipmap.tileset, R.raw.free_tile_set, MAP_TILE_WIDTH);
        add(Layer.map, mapManager.getMap(stage));

        player = new Player(1,19, MAP_TILE_WIDTH);
        add(Layer.player, player);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return player.onTouch(event);
    }
}
