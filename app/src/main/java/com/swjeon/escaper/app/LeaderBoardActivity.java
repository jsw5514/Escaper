package com.swjeon.escaper.app;

import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.swjeon.escaper.databinding.ActivityLeaderBoardBinding;
import com.swjeon.escaper.game.util.DBManager;

import java.util.ArrayList;

public class LeaderBoardActivity extends AppCompatActivity {
    ActivityLeaderBoardBinding binding;
    ArrayList<String> leaderboard;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLeaderBoardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        DBManager dbManager = DBManager.getInstance(this);
        leaderboard = dbManager.getLeaderBoard();

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