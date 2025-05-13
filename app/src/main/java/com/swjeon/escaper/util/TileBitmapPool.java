package com.swjeon.escaper.util;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.util.HashMap;

import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.GameView;

public class TileBitmapPool {
    private static final String TAG = TileBitmapPool.class.getSimpleName();
    private static Integer tileSetResId = null;
    private static Bitmap tileSet = null;
    private static final HashMap<Integer, Bitmap> tileBitmaps = new HashMap<>();
    private static BitmapFactory.Options opts;
    //////////////////////////////////////////////////
    /// 하드코딩된 타일셋 메타데이터
    // Tiled 플래그 비트
    private static final int FLIPPED_H = 0x80000000;
    private static final int FLIPPED_V = 0x40000000;
    private static final int FLIPPED_D = 0x20000000;
    // Tiled firstgid (json 에서 tileset.firstgid)
    private static final int FIRST_GID = 1;
    //////////////////////////////////////////////////
    public static Bitmap get(int rawGid){
        //타일셋 로딩
        if(tileSet == null){ // lazy initialization
            if (opts == null) {
                opts = new BitmapFactory.Options();
                opts.inScaled = false;
            }
            if(tileSetResId == null){
                Log.e(TAG,"잘못된 타일 요청, 타일셋 리소스 id가 설정되지 않았습니다.");
                return null;
            }
            Resources res = GameView.view.getResources();
            tileSet = BitmapFactory.decodeResource(res, tileSetResId, opts);
        }
        if (rawGid == 0) return null; //타일이 지정되지 않았음(빈 타일)

        Bitmap bitmap = tileBitmaps.get(rawGid);
        if (bitmap != null) {
            //gid 내 플래그 제거
            int gid = rawGid & ~(FLIPPED_H | FLIPPED_V | FLIPPED_D);
            int localIndex = gid - FIRST_GID;
            if (localIndex < 0) return null; //타일이 지정되지 않았음(빈 타일)

//            // src 영역 계산
//            int col = localIndex % tilesPerRow;
//            int row = localIndex / tilesPerCol;
//            int srcX = col * tileWidth;
//            int srcY = row * tileHeight;
        }
        return bitmap;

//
//
//        // src 영역 계산
//        int col = localIndex % tilesPerRow;
//        int row = localIndex / tilesPerCol;
//        int srcX = col * tileWidth;
//        int srcY = row * tileHeight;
//
//        // 플래그 판별
//        boolean flipH = (rawGid & FLIPPED_H) != 0;
//        boolean flipV = (rawGid & FLIPPED_V) != 0;
//        boolean flipD = (rawGid & FLIPPED_D) != 0;
//
//        // Matrix 로 뒤집기/회전 처리
//        Matrix m = new Matrix();
//        // 1) src 영역 분리
//        m.postTranslate(-srcX, -srcY);
//        // 2) 대각선 플립: 90° 회전 후 H/V 스왑
//        if (flipD) {
//            m.postRotate(90, 0, 0);
//            boolean tmp = flipH; flipH = flipV; flipV = tmp;
//        }
//        // 3) H/V 뒤집기
//        float sx = flipH ? -1f : 1f;
//        float sy = flipV ? -1f : 1f;
//        m.postScale(sx, sy);
//        // 4) 다시 화면 좌표계(0,0)→(srcX,srcY) 보정
//        float dx = flipH ? tileWidth  : 0;
//        float dy = flipV ? tileHeight : 0;
//        m.postTranslate(srcX + dx, srcY + dy);
//
//        // 최종 뒤집힌 타일 Bitmap 생성
//        Bitmap tileBmp = Bitmap.createBitmap(
//                tileSet,
//                0, 0,
//                tileSet.getWidth(), tileSet.getHeight(),
//                m, false
//        );
//        // 잘라낸 부분만 반환하기 위해
//        Bitmap result = Bitmap.createBitmap(
//                tileBmp,
//                srcX, srcY,
//                tileWidth, tileHeight
//        );
//
//        // 캐시에 저장
//        cache.put(rawGid, result);
//        return result;
//
//        return null;
    }
    public static void setTileSetId(int mipmapResId) {
        tileSetResId = mipmapResId;
    }
}
