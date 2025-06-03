package com.swjeon.escaper.game.map;

import android.graphics.Point;

import java.util.ArrayList;

public class MapDataBundle {
    private TiledMap map;
    private Point playerStart;
    private ArrayList<EnemySpawnInfo> enemySpawnInfos;

    public MapDataBundle(TiledMap map, Point playerStart, ArrayList<EnemySpawnInfo> enemySpawnInfos) {
        this.map = map;
        this.playerStart = playerStart;
        this.enemySpawnInfos = enemySpawnInfos;
    }

    public TiledMap getMap() {
        return map;
    }

    public Point getPlayerStart() {
        return playerStart;
    }

    public ArrayList<EnemySpawnInfo> getEnemySpawnInfos() {
        return enemySpawnInfos;
    }
}
