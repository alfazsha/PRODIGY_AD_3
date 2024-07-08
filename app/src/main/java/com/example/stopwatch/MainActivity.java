package com.example.stopwatch;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.MessageFormat;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    Button start,stop,reset;
    TextView timer;
    int seconds, minutes, milliSeconds;
    long millisecondTime, startTime, timeBuff, updateTime = 0L ;
    Handler handler;
    private final Runnable runnable = new Runnable() {
        @Override
        public void run() {
            millisecondTime = SystemClock.uptimeMillis() - startTime;
            updateTime = timeBuff + millisecondTime;
            seconds = (int) (updateTime / 1000);
            minutes = seconds / 60;
            seconds = seconds % 60;
            milliSeconds = (int) (updateTime % 1000);

            timer.setText(MessageFormat.format("{0}:{1}:{2}", String.format(Locale.getDefault(), "%02d", minutes), String.format(Locale.getDefault(), "%02d", seconds), String.format(Locale.getDefault(),"%03d", milliSeconds)));
            handler.postDelayed(this, 0);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        initial
        timer = findViewById(R.id.timer);
        start = findViewById(R.id.start);
        stop = findViewById(R.id.stop);
        reset = findViewById(R.id.reset);

        handler = new Handler(Looper.getMainLooper());

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startTime = SystemClock.uptimeMillis();
                handler.postDelayed(runnable, 0);
                reset.setEnabled(false);
                stop.setEnabled(true);
                start.setEnabled(false);
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timeBuff += millisecondTime;
                handler.removeCallbacks(runnable);
                reset.setEnabled(true);
                stop.setEnabled(false);
                start.setEnabled(true);
            }
        });

        reset.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                millisecondTime = 0L ;
                startTime = 0L ;
                timeBuff = 0L ;
                updateTime = 0L ;
                seconds = 0 ;
                minutes = 0 ;
                milliSeconds = 0 ;
                timer.setText("00:00:000");
            }
        });
        timer.setText("00:00:000");
    }
}