package com.swjeon.escaper.app;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.swjeon.escaper.databinding.ActivityLeaderBoardBinding;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class LeaderBoardActivity extends AppCompatActivity {
    ActivityLeaderBoardBinding binding;
    ArrayList<String> leaderboard;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLeaderBoardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SharedPreferences prefs = getApplicationContext().getSharedPreferences("score list", MODE_PRIVATE);
        leaderboard = prefs.getAll().entrySet().stream()
                // Map.Entry<String,Object> → Map.Entry<String,Integer> 캐스팅
                .map(e -> new AbstractMap.SimpleEntry<>(
                        e.getKey(),
                        (Integer) e.getValue()
                ))
                // 점수 내림차순 정렬
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                // “유저이름: nnnn점” 문자열로 매핑
                .map(e -> e.getKey() + " : " + e.getValue() + "점")
                // ArrayList로 수집
                .collect(Collectors.toCollection(ArrayList::new));

        binding.leaderBoardList.setAdapter(adapter);
    }

    private final BaseAdapter adapter = new BaseAdapter() {
        @Override
        public int getCount() {
            return leaderboard.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int i, View convertView, ViewGroup parent) {
            String text = leaderboard.get(i);
            TextView tv = new TextView(LeaderBoardActivity.this);
            tv.setText(text);
            tv.setTextSize(20);
            tv.setGravity(Gravity.CENTER);
            int padding = (int) TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    10,
                    getApplicationContext().getResources().getDisplayMetrics());
            tv.setPadding(0, padding, 0, padding);
            return tv;
        }
    };
}