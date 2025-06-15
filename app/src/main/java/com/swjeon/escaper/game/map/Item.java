package com.swjeon.escaper.game.map;

import com.swjeon.escaper.R;
import com.swjeon.escaper.game.util.TiledSprite;

public class Item extends TiledSprite {
    public enum Type{
        orange, yellow
    }
    private final Type itemType;
    public Item(Type type, int x, int y, float tileWidth) {
        super(getMipmapId(type), x, y, tileWidth);
        itemType = type;
    }

    private static int getMipmapId(Type type) {
        int mipmapId;
        switch (type){
            case orange:
                mipmapId = R.mipmap.item1;
                break;
            case yellow:
                mipmapId = R.mipmap.item2;
                break;
            default:
                throw new RuntimeException("invalid item type");
        }
        return mipmapId;
    }

    public int getScore() {
        if (itemType == Type.orange)
            return 100;
        else
            return 200;
    }
}
