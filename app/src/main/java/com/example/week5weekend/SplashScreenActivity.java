package com.example.week5weekend;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class SplashScreenActivity extends AppCompatActivity {

    private static final int STOP = 0;
    private static final long SHOW_TIME = 2000;
    private ConstraintLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        initViews();
        changeScreen();
    }

    private void initViews() {
        layout = findViewById(R.id.splashLayout);
    }

    private void changeScreen() {
        Handler splashHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case STOP:
                        layout.setVisibility(View.GONE);
                        Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                }
                super.handleMessage(msg);
            }
        };
        Message msg = new Message();
        msg.what = STOP;
        splashHandler.sendMessageDelayed(msg, SHOW_TIME);
    }
}
