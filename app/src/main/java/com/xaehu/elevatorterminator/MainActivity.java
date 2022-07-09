package com.xaehu.elevatorterminator;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends Activity {

    private final String TAG = "MainActivity";
    private ImageView image;
    private CountDownTimer countDownTimer;
    private final int[] resArray = {R.mipmap.number_5
            ,R.mipmap.number_4
            ,R.mipmap.number_3
            ,R.mipmap.number_2
            ,R.mipmap.number_1
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(TAG, "onCreate: ");
        image = findViewById(R.id.image);
        image.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onPause() {
        Log.i(TAG, "onPause: ");
        super.onPause();
        if(countDownTimer != null){
            countDownTimer.cancel();
        }
    }

    @Override
    protected void onResume() {
        Log.i(TAG, "onResume: ");
        super.onResume();
        image.setImageResource(R.mipmap.number_5);
        image.setVisibility(View.VISIBLE);
        countDownTimer = new CountDownTimer(6500,1300) {
            @Override
            public void onTick(long millisUntilFinished) {
                Log.i(TAG, "onTick: m = "+millisUntilFinished);
                int i = (int) ((6600 - millisUntilFinished)/1300);
                Log.i(TAG, "onTick: i = "+i);
                if(i == 0){
                    image.setImageResource(R.mipmap.number_5);
                }else if(i < resArray.length && i > 0){
                    image.animate()
                            .alpha(0.0f)
                            .setDuration(200)
                            .setListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    super.onAnimationEnd(animation);
                                    image.setImageResource(resArray[i]);
                                    image.animate().alpha(1f).setDuration(200);
                                }
                            });
                }
            }

            @Override
            public void onFinish() {
                image.setVisibility(View.GONE);
            }
        }.start();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onDestroy() {
        Log.i(TAG, "onDestroy: ");
        super.onDestroy();
        if(countDownTimer != null){
            countDownTimer.cancel();
            countDownTimer = null;
        }
    }
}