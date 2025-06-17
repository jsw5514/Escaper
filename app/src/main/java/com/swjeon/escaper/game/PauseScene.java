package com.swjeon.escaper.game;

import android.content.Context;

import com.swjeon.escaper.R;
import com.swjeon.escaper.game.util.DBManager;
import com.swjeon.escaper.game.util.GetNameDialog;

import kr.ac.tukorea.ge.spgp2025.a2dg.framework.objects.Button;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.objects.Sprite;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.scene.Scene;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.GameView;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.Metrics;

public class PauseScene extends Scene {
    private final int gameScore;
    public enum PauseSceneLayers {
        bg, ui
    }
    PauseScene(int score) {
        initLayers(PauseSceneLayers.values().length);
        gameScore = score;

        //50% 반투명한 배경 설정
        Sprite bg = new Sprite(R.mipmap.trans_50b);
        float w = Metrics.width, h = Metrics.height;
        bg.setPosition(w/2, h/2, w, h);
        add(PauseSceneLayers.bg, bg);

        //버튼 설정
        Button resumeBt = new Button(R.mipmap.bt_resume, 600f, 1050f, 500f, 500f, new Button.OnTouchListener() {
            @Override
            public boolean onTouch(boolean pressed) {
                if (pressed) {
                    pop();
                    return true;
                }
                return false;
            }
        });
        add(PauseSceneLayers.ui, resumeBt);

        Button exitBt = new Button(R.mipmap.bt_exit, 1500f, 1050f, 500f, 500f, OnGameExitListener);
        add(PauseSceneLayers.ui, exitBt);
    }
    private final Button.OnTouchListener OnGameExitListener = new Button.OnTouchListener() {
        @Override
        public boolean onTouch(boolean pressed) {
            if (pressed) {
                Context context = GameView.view.getContext();
                GetNameDialog dialog = new GetNameDialog(context, true);
                dialog.setOnNameSetListener(new GetNameDialog.OnNameSetListener() {
                    @Override
                    public void onNameSet(String name) {
                        if (name != null) {
                            DBManager dbManager = DBManager.getInstance(context);
                            dbManager.saveScore(name, gameScore);
                        }
                        Scene.popAll();
                    }
                });
                dialog.show();
                return true;
            }
            return false;
        }
    };

    @Override
    public boolean isTransparent() {
        return true;
    }

    @Override
    protected int getTouchLayerIndex() {
        return PauseSceneLayers.ui.ordinal();
    }
}
