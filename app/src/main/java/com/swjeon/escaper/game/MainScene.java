package com.swjeon.escaper.game;

import android.content.Context;
import android.graphics.Point;
import android.util.Log;
import android.view.MotionEvent;

import com.swjeon.escaper.R;
import com.swjeon.escaper.game.map.EnemySpawnInfo;
import com.swjeon.escaper.game.map.TiledMapManager;
import com.swjeon.escaper.game.object.CollisionChecker;
import com.swjeon.escaper.game.object.Enemy;
import com.swjeon.escaper.game.object.Item;
import com.swjeon.escaper.game.object.Player;
import com.swjeon.escaper.game.util.DBManager;
import com.swjeon.escaper.game.util.OnStageClearListener;

import java.time.LocalDateTime;
import java.util.ArrayList;

import kr.ac.tukorea.ge.spgp2025.a2dg.framework.objects.Score;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.scene.Scene;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.Metrics;

public class MainScene extends Scene implements OnStageClearListener {
    private final String TAG = this.getClass().getSimpleName();
    private TiledMapManager mapManager;
    private Player player;
    private CollisionChecker collisionChecker;
    private Score score;
    private Context context;
    private final float MAP_TILE_WIDTH = 100f;
    private int stage;

    public enum Layer{
         map, item, enemy, player, ui, controller;
        public static final int COUNT = values().length;
    }
    public MainScene(Context context){
        Log.d(TAG,"메인씬 생성됨");
        initLayers(Layer.COUNT);
        stage = 0;

        this.context=context;
        this.mapManager = new TiledMapManager(context, R.mipmap.tileset, R.raw.free_tile_set, MAP_TILE_WIDTH);
        add(Layer.map, mapManager.getMap(stage));

        setGObjPos();

        collisionChecker = new CollisionChecker(this);
        collisionChecker.setOnStageClearListener(this);
        add(Layer.controller, collisionChecker);

        score = new Score(R.mipmap.number_24x32, 1800f, 0f, 60f);
        score.setScore(0);
        add(Layer.ui, score);
    }

    @Override
    public void onStageClear() {
        score.add(1000); //스테이지 클리어 점수
        objectsAt(Layer.enemy).clear();
        objectsAt(Layer.item).clear();
        objectsAt(Layer.map).clear();

        if(++stage >= 3) {
            Log.w(TAG, "게임 클리어. 곧 메인씬이 pop 됨.");
            objectsAt(Layer.controller).clear();
            collisionChecker = null;

            //최종 점수 db에 기록
            LocalDateTime now = LocalDateTime.now();
            DBManager dbManager = DBManager.getInstance(context);
            dbManager.saveScore(now, score.getScore());

            pop();
            return;
        }
        add(Layer.map, mapManager.getMap(stage));
        setGObjPos();
    }

    private void setGObjPos() {
        if (player == null) {
            player = new Player(mapManager.getPlayerStart(stage), MAP_TILE_WIDTH);
            add(Layer.player, player);
        }
        else {
            Point startPos = mapManager.getPlayerStart(stage);
            player.setStartPos(startPos);
            player.setStaticTiledPosition(startPos);
        }

        ArrayList<EnemySpawnInfo> spawnInfos = mapManager.getEnemyInfos(stage);
        for (EnemySpawnInfo spawnInfo:spawnInfos){
            add(Layer.enemy, new Enemy(spawnInfo, MAP_TILE_WIDTH));
        }

        add(Layer.item, new Item(Item.Type.orange, 1, 1, MAP_TILE_WIDTH));
    }

    public boolean passWall = false;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (isEnableGodMode(event)) return true; //debug god mode
        if (pauseBt.onTouchEvent(event)){
            return true;
        }
        else {
            return player.onTouch(event);
        }
    }

    private boolean needConsumeEvent = false;
    private boolean isEnableGodMode(MotionEvent event) {
        //우하단 지점을 누를시 스테이지 클리어, 그 외 우측 아무 곳이나 누르면 벽 무시
        float[] touchedPos = Metrics.fromScreen(event.getX(), event.getY());
        if (event.getAction() == MotionEvent.ACTION_DOWN && touchedPos[0] > Metrics.width - 100f) {
            if (touchedPos[1] > Metrics.height - 100f){
                onStageClear();
            }
            else {
                passWall = true;
            }
            needConsumeEvent = true;
            return true;
        } else if (needConsumeEvent) {
            if(event.getAction() == MotionEvent.ACTION_UP) {
                needConsumeEvent = false;
            }
            return true;
        }
        return false;
    }

    public void resetScore() {
        score.setScore(0);
    }

    public void addScore(int num) {
        score.add(num);
    }
}
