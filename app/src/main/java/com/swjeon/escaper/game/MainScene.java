package com.swjeon.escaper.game;

import android.content.Context;

import kr.ac.tukorea.ge.spgp2025.a2dg.framework.scene.Scene;

public class MainScene extends Scene {
    TiledMapManager mapManager;
    Context context;
    public MainScene(Context context){
        this.context=context;

        this.mapManager = new TiledMapManager(context);
    }
}
