package com.example.victor.agrvai.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.victor.agrvai.R;

public class SplashActivity extends AppCompatActivity {
    private ImageView imgV2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        imgV2 = (ImageView) findViewById(R.id.imgV2);
        Animation myanim = AnimationUtils.loadAnimation(this, R.anim.mytrn);
        imgV2.startAnimation(myanim);
        final Intent i = new Intent(this, MainActivity.class);
        Thread timer = new Thread(){
            public void run() {
                try {
                    sleep(3500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finally {
                    startActivity(i);
                    finish();
                }
            }
        };
        timer.start();
    }
}
