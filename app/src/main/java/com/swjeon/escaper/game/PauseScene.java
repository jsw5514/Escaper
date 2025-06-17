package com.swjeon.escaper.game;

import android.view.MotionEvent;

import com.swjeon.escaper.R;

import kr.ac.tukorea.ge.spgp2025.a2dg.framework.objects.Button;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.objects.Sprite;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.scene.Scene;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.Metrics;

public class PauseScene extends Scene {
    enum PauseSceneLayers {
        bg, ui
    }
    PauseScene() {
        initLayers(PauseSceneLayers.values().length);

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

        Button exitBt = new Button(R.mipmap.bt_exit, 1500f, 1050f, 500f, 500f, new Button.OnTouchListener() {
            @Override
            public boolean onTouch(boolean pressed) {
                if (pressed) {
                    popAll();
                    return true;
                }
                return false;
            }
        });
        add(PauseSceneLayers.ui, exitBt);
    }

    @Override
    public boolean isTransparent() {
        return true;
    }

    @Override
    protected int getTouchLayerIndex() {
        return PauseSceneLayers.ui.ordinal();
    }
}
