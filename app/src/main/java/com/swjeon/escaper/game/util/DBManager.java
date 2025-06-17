package com.swjeon.escaper.game.util;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.swjeon.escaper.app.MainActivity;

import java.time.LocalDateTime;
import java.util.ArrayList;

import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.GameView;

public class DBManager extends SQLiteOpenHelper {

    private static DBManager instance;
    private DBManager(Context context){
        super(context, "score.db", null, 1);
    }
    public static DBManager getInstance(Context context) {
        if(instance == null){
            instance = new DBManager(context);
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String sql =  "CREATE TABLE score (" +
                                "timestamp TEXT PRIMARY KEY," +
                                "value INTEGER" +
                            ")";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public void saveScore(String username, int score) {
        final String sql = "INSERT INTO score (timestamp, value) VALUES(?,?)";
        Object[] parms = new Object[]{username, score};
        getWritableDatabase().execSQL(sql,parms);
    }

    public ArrayList<String> getLeaderBoard() {
        ArrayList<String> result = new ArrayList<>();
        final String sql = "SELECT timestamp, value FROM score " +
                "ORDER BY value DESC";
        Cursor cursor = getReadableDatabase().rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do{
                String timestamp = cursor.getString(cursor.getColumnIndexOrThrow("timestamp"));
                int score = cursor.getInt(cursor.getColumnIndexOrThrow("value"));
                result.add(timestamp + " : " + score + "점");
            } while (cursor.moveToNext());
        }
        return result;
    }
}
