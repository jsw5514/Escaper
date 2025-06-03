package com.swjeon.escaper.game.map;

import android.content.Context;
import android.util.Log;

import com.swjeon.escaper.R;
import com.swjeon.escaper.game.map.tileset.Converter;
import com.swjeon.escaper.game.map.tileset.TileSet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class TiledMapManager {
    private final String TAG = this.getClass().getSimpleName();
    private final Context context;
    private final static int[] MAP_IDS = new int[]{R.raw.maze_1,R.raw.maze_2,R.raw.maze_3}; //맵 json 파일 리소스 번호
    private ArrayList<TiledMapBg> maps;
    private final float MAP_TILE_WIDTH; //화면에서 타일 하나의 가로, 세로 길이
    private TileSet tileSet;
    public TiledMapManager(Context appContext, int tileSetImgId, int tileSetJsonId, float mapTileWidth){
        this.context = appContext;
        this.MAP_TILE_WIDTH = mapTileWidth;
        loadTileSet(tileSetImgId, tileSetJsonId);
        loadMaps();
    }

    private void loadTileSet(int tileSetImgId, int tileSetJsonId) {
        //json 파일을 문자열로 로드
        String rawTileSetData;
        try{
            InputStream inputStream = context.getResources().openRawResource(tileSetJsonId);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder jsonData = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                jsonData.append(line);
            }
            reader.close();
            inputStream.close();
            rawTileSetData = jsonData.toString();
        }
        catch (IOException e){
            Log.e(TAG,"error while loading tileset json");
            throw new RuntimeException(e);
        }

        //json 문자열을 객체화
        try {
            tileSet = Converter.fromJsonString(rawTileSetData);
            tileSet.setImageAndroidId(tileSetImgId);
        } catch (IOException e) {
            Log.e(TAG, "error while converting tileset json to object");
            throw new RuntimeException(e);
        }
    }

    private void loadMaps() {
        //json 파일을 문자열로 로드
        ArrayList<String> rawMapDatas;
        try{
            rawMapDatas = new ArrayList<>();
            for(int MAP_ID:MAP_IDS){
                InputStream inputStream = context.getResources().openRawResource(MAP_ID);
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder jsonData = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    jsonData.append(line);
                }
                reader.close();
                inputStream.close();
                rawMapDatas.add(jsonData.toString());
            }
        }
        catch (IOException e){
            Log.e(TAG,"error while loading json");
            throw new RuntimeException(e);
        }

        //json 문자열을 객체화
        ArrayList<JSONObject> jsonMapDatas = new ArrayList<>();
        for(String rawMapData : rawMapDatas){
            try {
                jsonMapDatas.add(new JSONObject(rawMapData));
            } catch (JSONException e) {
                Log.e(TAG,"error while parsing json string");
                throw new RuntimeException(e);
            }
        }

        //가져온 json 객체에서 맵 데이터 추출(단일 레이어, 단일 타일셋 맵을 가정)
        maps = new ArrayList<>();
        for(JSONObject jsonMapData :jsonMapDatas){
            try {
                JSONArray jsonMap = jsonMapData.getJSONArray("layers").getJSONObject(0).getJSONArray("data");
                int firstGid = jsonMapData.getJSONArray("tilesets").getJSONObject(0).getInt("firstgid");
                int mapWidth = jsonMapData.getJSONArray("layers").getJSONObject(0).getInt("width");
                int mapHeight = jsonMapData.getJSONArray("layers").getJSONObject(0).getInt("height");
                TiledMapBg.MapSize mapSize = new TiledMapBg.MapSize(mapWidth, mapHeight);
                int[] mapData = new int[jsonMap.length()];
                for(int i = 0; i < mapData.length; i++){
                    mapData[i] = jsonMap.getInt(i);
                }
                maps.add(new TiledMapBg(mapData, tileSet, mapSize, MAP_TILE_WIDTH, firstGid));
            } catch (JSONException e) {
                Log.e(TAG,"error while converting json to integer");
                throw new RuntimeException(e);
            }
        }
    }
    public TiledMapBg getMap(int mapIndex){
        return maps.get(mapIndex);
    }
}
