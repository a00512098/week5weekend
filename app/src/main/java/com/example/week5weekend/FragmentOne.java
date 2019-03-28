package com.example.week5weekend;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import org.greenrobot.eventbus.EventBus;

public class FragmentOne extends Fragment implements View.OnClickListener {
    private Button startButton, stopButton;
    private Counter counter;
    private Handler handler;
    private Runnable runnable;
    private boolean isRunning;

    public FragmentOne() {
        counter = new Counter();
        setHandler();
    }

    private void setHandler() {
        isRunning = false;
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                Log.d("Log.d", "Timer: " + counter.getTime());
                EventBus.getDefault().post(counter);
                handleCounter(isRunning);
            }
        };
    }

    public static FragmentOne newInstance() {
        FragmentOne fragment = new FragmentOne();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_one, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        startButton = view.findViewById(R.id.startButton);
        stopButton = view.findViewById(R.id.stopButton);
        stopButton.setClickable(false);

        startButton.setOnClickListener(this);
        stopButton.setOnClickListener(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        handler.removeCallbacks(runnable);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.startButton:
                counter.startCounter();
                startButton.setClickable(false);
                stopButton.setClickable(true);
                isRunning = true;
                break;
            case R.id.stopButton:
                isRunning = false;
                startButton.setClickable(true);
                stopButton.setClickable(false);
                counter.stopCounter();
                break;
        }

        handleCounter(isRunning);
    }

    private void handleCounter(boolean isRunning) {
        if (isRunning) {
            handler.postDelayed(runnable, 1000);
        } else {
            EventBus.getDefault().post(counter);
        }
    }
}
