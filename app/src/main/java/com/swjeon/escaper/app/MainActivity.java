package com.swjeon.escaper.app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.swjeon.escaper.BuildConfig;
import com.swjeon.escaper.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private final String TAG = this.getClass().getSimpleName();
    private @NonNull ActivityMainBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //set listeners
        binding.btStart.setOnClickListener(listenerStart);
        binding.btLeaderBoard.setOnClickListener(listenerLeaderBoard);
        binding.btLeave.setOnClickListener(listenerLeave);

        if (BuildConfig.DEBUG){
            //call game activity
            Intent gameActivityIntent = new Intent(this, EscaperActivity.class);
            startActivity(gameActivityIntent);
        }
    }

    //listeners
    private final View.OnClickListener listenerStart = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //call game activity
            Intent gameActivityIntent = new Intent(v.getContext(), EscaperActivity.class);
            startActivity(gameActivityIntent);
        }
    };
    private final View.OnClickListener listenerLeaderBoard = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //call leader board activity
            Intent leaderBoardActivityIntent = new Intent(v.getContext(), LeaderBoardActivity.class);
            startActivity(leaderBoardActivityIntent);
        }
    };
    private final View.OnClickListener listenerLeave = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };

}