package com.swjeon.escaper.game;

import com.swjeon.escaper.R;

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
    }

    @Override
    public boolean isTransparent() {
        return true;
    }
}
