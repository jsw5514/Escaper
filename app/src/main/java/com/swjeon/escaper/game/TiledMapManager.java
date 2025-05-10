package com.swjeon.escaper.game;

import android.content.Context;
import android.util.Log;

import com.swjeon.escaper.R;

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
    private final static int[] MAP_IDS = new int[]{R.raw.maze_1,R.raw.maze_2,R.raw.maze_3};
    private ArrayList<int[]> maps;
    TiledMapManager(Context appContext){
        this.context = appContext;
        loadMaps();
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
            return;
        }

        //json 문자열을 객체화
        ArrayList<JSONObject> mapDatas = new ArrayList<>();
        for(String rawMapData : rawMapDatas){
            try {
                mapDatas.add(new JSONObject(rawMapData));
            } catch (JSONException e) {
                Log.e(TAG,"error while parsing json string");
                return;
            }
        }

        //가져온 json 객체에서 맵 데이터 추출
        maps = new ArrayList<>();
        for(JSONObject mapData :mapDatas){
            try {
                JSONArray jsonMap = mapData.getJSONArray("layers").getJSONObject(0).getJSONArray("data");
                int[] map = new int[jsonMap.length()];
                for(int i = 0; i < map.length; i++){
                    map[i] = jsonMap.getInt(i);
                }
                maps.add(map);
            } catch (JSONException e) {
                Log.e(TAG,"error while converting json to integer");
                return;
            }
        }
    }
}
