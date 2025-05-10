package com.swjeon.escaper.app;

import android.os.Bundle;

import com.swjeon.escaper.BuildConfig;

import kr.ac.tukorea.ge.spgp2025.a2dg.framework.activity.GameActivity;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.scene.Scene;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.GameView;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.Metrics;

public class EscaperActivity extends GameActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GameView.drawsDebugStuffs = BuildConfig.DEBUG;
        Metrics.setGameSize(1000,1000);
        new Scene().push();
    }
}