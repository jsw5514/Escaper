package com.swjeon.escaper.util;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.HashMap;
import java.util.Objects;

import kr.ac.tukorea.ge.spgp2025.a2dg.framework.res.BitmapPool;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.GameView;

public class TileBitmapPool {
    private static final String TAG = TileBitmapPool.class.getSimpleName();
    private static int tileSet;
    private static final HashMap<FlipedBitmapId, Bitmap> tileBitmaps = new HashMap<>();
    private static BitmapFactory.Options opts;
    public static class FlipedBitmapId{
        final int mipmapResId;
        final boolean flipedH;
        final boolean flipedV;
        final boolean flipedD;
        public FlipedBitmapId(int mipmapResId, boolean flipedH, boolean flipedV, boolean flipedD){
            this.mipmapResId = mipmapResId;
            this.flipedH = flipedH;
            this.flipedV = flipedV;
            this.flipedD = flipedD;
        }
        @Override
        public boolean equals(@Nullable Object o) {
            if(o == null) return false;
            FlipedBitmapId that = (FlipedBitmapId) o;
            if(
                    (that.flipedH == this.flipedH) &&
                    (that.flipedV == this.flipedV) &&
                    (that.flipedD == this.flipedD)
            ){
                return true;
            }
            else {
                return false;
            }
        }
        @Override
        public int hashCode() {
            return Objects.hash(mipmapResId, flipedH, flipedV, flipedD);
        }
    }
    public static Bitmap get(int mipmapResId, boolean flipedH, boolean flipedV, boolean flipedD){
        if (!flipedH && !flipedV && !flipedD){ // 뒤집히지 않은 경우
            return BitmapPool.get(mipmapResId);// 일반 비트맵 풀에서 리턴
        }
        FlipedBitmapId id = new FlipedBitmapId(mipmapResId, flipedH, flipedV, flipedD);
        Bitmap bitmap = tileBitmaps.get(id); // 풀에서 발견한 경우 그대로 사용
        if(bitmap == null){
            if (opts == null) {
                opts = new BitmapFactory.Options();
                opts.inScaled = false;
            }
            Resources res = GameView.view.getResources();
            bitmap = BitmapFactory.decodeResource(res, mipmapResId, opts);
            Log.d(TAG, "Fliped Bitmap " + res.getResourceEntryName(mipmapResId) + "(" + mipmapResId + ") : " + bitmap.getWidth() + "x" + bitmap.getHeight()
                    + " Fliped: " + flipedH + "/" + flipedV + "/" + flipedD);
            tileBitmaps.put(id, bitmap);
        }
        return bitmap;
    }
}
