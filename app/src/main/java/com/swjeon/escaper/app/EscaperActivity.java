package com.swjeon.escaper.app;

import android.os.Bundle;

import com.swjeon.escaper.BuildConfig;
import com.swjeon.escaper.game.MainScene;

import kr.ac.tukorea.ge.spgp2025.a2dg.framework.activity.GameActivity;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.GameView;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.Metrics;

public class EscaperActivity extends GameActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        GameView.drawsDebugStuffs = BuildConfig.DEBUG;
        Metrics.setGameSize(2100f,2100f);
        super.onCreate(savedInstanceState);
        new MainScene(getApplicationContext()).push();
    }
}