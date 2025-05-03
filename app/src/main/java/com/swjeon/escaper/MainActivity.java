package com.swjeon.escaper;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.swjeon.escaper.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private final String TAG = this.getClass().getSimpleName();
    private @NonNull ActivityMainBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
            Log.e(TAG,"GameActivity is not yet implemented");
            throw new RuntimeException("not yet implemented");
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