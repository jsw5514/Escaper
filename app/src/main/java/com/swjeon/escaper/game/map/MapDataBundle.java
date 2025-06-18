package com.swjeon.escaper.game.map;

import android.graphics.Point;

import java.util.ArrayList;

public class MapDataBundle {
    private TiledMap map;
    private Point playerStart;
    private ArrayList<EnemySpawnInfo> enemySpawnInfos;
    private ArrayList<ItemSpawnInfo> itemSpawnInfos;

    public MapDataBundle(TiledMap map, Point playerStart, ArrayList<EnemySpawnInfo> enemySpawnInfos, ArrayList<ItemSpawnInfo> itemPos) {
        this.map = map;
        this.playerStart = playerStart;
        this.enemySpawnInfos = enemySpawnInfos;
        this.itemSpawnInfos = itemPos;
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

    public ArrayList<ItemSpawnInfo> getItemSpawnInfos() {
        return itemSpawnInfos;
    }
}
