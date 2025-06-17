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
import com.swjeon.escaper.game.util.GetNameDialog;
import com.swjeon.escaper.game.util.OnStageClearListener;

import java.time.LocalDateTime;
import java.util.ArrayList;

import kr.ac.tukorea.ge.spgp2025.a2dg.framework.objects.Button;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.objects.Score;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.scene.Scene;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.Metrics;

public class MainScene extends Scene implements OnStageClearListener {
    private final String TAG = this.getClass().getSimpleName();
    private TiledMapManager mapManager;
    private Player player;
    public Player getPlayer() {
        return player;
    }

    private CollisionChecker collisionChecker;
    private Score score;
    private Button pauseBt;
    private Context context;
    private final float MAP_TILE_WIDTH = 100f;
    private int stage;

    public enum Layer{
         map, item, enemy, ui, controller;
        public static final int COUNT = values().length;
    }
    public MainScene(Context context){
        Log.d(TAG,"메인씬 생성됨");
        initLayers(Layer.COUNT);
        stage = 0;

        this.context=context;
        this.mapManager = new TiledMapManager(context, R.mipmap.tileset, R.raw.free_tile_set, MAP_TILE_WIDTH);
        add(Layer.map, mapManager.getMap(stage));

        collisionChecker = new CollisionChecker(this);
        collisionChecker.setOnStageClearListener(this);
        add(Layer.controller, collisionChecker);

        score = new Score(R.mipmap.number_24x32, 1800f, 0f, 60f);
        score.setScore(0);
        add(Layer.ui, score);

        pauseBt = new Button(R.mipmap.icon_pause_transparent, 150f, 200f, 400f, 400f, new Button.OnTouchListener() {
            @Override
            public boolean onTouch(boolean pressed) {
                if (pressed) {
                    new PauseScene().push();
                    return true;
                }
                return false;
            }
        });
        add(Layer.ui, pauseBt);

        setGObjPos(); //player, enemy, item 위치 초기화
    }

    @Override
    public void onStageClear() {
        score.add(1000); //스테이지 클리어 점수
        if(++stage >= 3) {
            Log.w(TAG, "게임 클리어. 곧 메인씬이 pop 됨.");
            objectsAt(Layer.controller).clear();
            collisionChecker = null;

            //최종 점수 db에 기록
            GetNameDialog dialog = new GetNameDialog(context, false);
            dialog.setOnNameSetListener(new GetNameDialog.OnNameSetListener() {
                @Override
                public void onNameSet(String name) {
                    DBManager dbManager = DBManager.getInstance(context);
                    dbManager.saveScore(name, score.getScore());
                    Scene.pop();
                }
            });
            dialog.show();
            return;
        }

        objectsAt(Layer.enemy).clear();
        objectsAt(Layer.item).clear();
        objectsAt(Layer.map).clear();

        add(Layer.map, mapManager.getMap(stage));
        setGObjPos();
    }

    private void setGObjPos() {
        if (player == null) {
            player = new Player(mapManager.getPlayerStart(stage), MAP_TILE_WIDTH);
            add(Layer.ui, player);
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

    @Override
    protected int getTouchLayerIndex() {
        return Layer.ui.ordinal();
    }
    public boolean passWall = false;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (isEnableGodMode(event)) { //debug god mode
            return true;
        }
        else{
            return super.onTouchEvent(event);
        }
    }

    private boolean needConsumeEvent = false;
    private boolean isEnableGodMode(MotionEvent event) {
        //우하단 지점을 누를시 스테이지 클리어, 그 외 우측 아무 곳이나 누르면 이후 이동시 벽 무시
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
