package com.example.internship_stop_watch;

//import static com.example.internship_stop_watch.R.id.play;
//import static com.example.internship_stop_watch.R.id.refesh;
//import static com.example.internship_stop_watch.R.id.textview;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;

import java.text.MessageFormat;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    public TextView view;
    Button reset, start, stop;
    int sec,min,millisec;
    long milli,start_time,timebuffer,updatetime=0l;
    Handler handler;
    private final Runnable runnable=new Runnable() {
        @Override
        public void run() {
            milli= SystemClock.uptimeMillis()-start_time;
            updatetime=timebuffer+milli;
            sec=(int) (updatetime/1000);
            min=sec/60;
            sec=sec%60;
            millisec=(int) (updatetime%1000);
            view.setText(MessageFormat.format("{0}:{1}:{2}",min,String.format(Locale.getDefault(),"%02d",sec),String.format(Locale.getDefault(),"%02d",millisec)));
            handler.postDelayed(this, 0);
        }
    };
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        view=findViewById(R.id.textview);
        reset=findViewById(R.id.button2);
        start=findViewById(R.id.button);
        stop=findViewById(R.id.button3);
        handler = new Handler(Looper.getMainLooper());
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                start_time=SystemClock.uptimeMillis();
                handler.postDelayed(runnable,0);
                reset.setEnabled(false);
                stop.setEnabled(true);
                start.setEnabled(false);
                System.out.println("start");

            }
        });
 
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timebuffer+=milli;
                handler.removeCallbacks(runnable);
                reset.setEnabled(true);
                stop.setEnabled(false);
                start.setEnabled(true);
                System.out.println("stop");

            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("clear");
                clear();
            }
            private void clear(){ milli = 0L;
                start_time = 0L;
                timebuffer = 0L;
                updatetime = 0L;
                sec = 0;
                min = 0;
                millisec = 0;

                view.setText("00:00:00");}
                // Update the TextView with "00:00:00"

        });
//        view.setText("00:00:00");
    }
}