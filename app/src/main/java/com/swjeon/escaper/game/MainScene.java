package com.swjeon.escaper.game;

import android.content.Context;

import com.swjeon.escaper.R;

import kr.ac.tukorea.ge.spgp2025.a2dg.framework.scene.Scene;

public class MainScene extends Scene {
    TiledMapManager mapManager;
    Context context;
    public enum Layer{
        map;
        public static final int COUNT = values().length;
    }
    public MainScene(Context context){
        initLayers(Layer.COUNT);

        this.context=context;
        this.mapManager = new TiledMapManager(context, R.mipmap.tileset, R.raw.free_tile_set);

        add(Layer.map, mapManager.getMap(0));
    }
}
