package com.swjeon.escaper.app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
            Log.e(TAG,"LeaderBoardActivity is not yet implemented");
            throw new RuntimeException("not yet implemented");
        }
    };
    private final View.OnClickListener listenerLeave = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };

}