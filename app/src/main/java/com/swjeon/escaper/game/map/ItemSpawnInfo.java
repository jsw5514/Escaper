package com.swjeon.escaper.game.map;

import com.swjeon.escaper.game.object.Item;

public class ItemSpawnInfo {
    public int x;
    public int y;
    public Item.Type type;

    public ItemSpawnInfo(int itemX, int itemY, Item.Type type) {
        x = itemX;
        y = itemY;
        this.type = type;
    }
}
