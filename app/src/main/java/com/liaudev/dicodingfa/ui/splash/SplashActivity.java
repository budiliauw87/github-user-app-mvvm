package com.liaudev.dicodingfa.ui.splash;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.BounceInterpolator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.liaudev.dicodingfa.R;
import com.liaudev.dicodingfa.databinding.ActivitySplashBinding;
import com.liaudev.dicodingfa.ui.main.MainActivity;

/**
 * Created by Budiliauw87 on 2022-06-21.
 * budiliauw87.github.io
 * Budiliauw87@gmail.com
 */
public class SplashActivity extends AppCompatActivity {
    private ActivitySplashBinding binding;
    private boolean mShouldFinish = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Window window = getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(SplashActivity.this, R.color.white));
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        } else {
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }

        //animate
        ObjectAnimator logo = ObjectAnimator.ofFloat(binding.imgLogo, View.TRANSLATION_Y, -150f, 0).setDuration(2000);
        ObjectAnimator objSubTitle = ObjectAnimator.ofFloat(binding.tvSubTitle, View.ALPHA, 1f).setDuration(500);
        ObjectAnimator objVersion = ObjectAnimator.ofFloat(binding.tvVersion, View.TRANSLATION_Y, 150f, 0).setDuration(500);
        logo.setInterpolator(new BounceInterpolator());
        logo.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                //to activity
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                mShouldFinish = true;

            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        logo.start();
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(1000);
        animatorSet.playTogether(objSubTitle, objVersion);
        animatorSet.setInterpolator(new AccelerateInterpolator());
        animatorSet.start();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mShouldFinish)
            finish();
    }
}
