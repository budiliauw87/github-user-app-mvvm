package com.liaudev.githubuser.splash;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.BounceInterpolator;

import androidx.appcompat.app.AppCompatActivity;

import com.liaudev.githubuser.databinding.ActivitySplashBinding;
import com.liaudev.githubuser.main.MainActivity;

public class SplashActivity extends AppCompatActivity {
    private ActivitySplashBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initFullScreen();
        initAnimation();
    }

    private void initFullScreen(){
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        //set statuscolor adaptive theme
        TypedValue typedValue = new TypedValue();
        getTheme().resolveAttribute(com.google.android.material.R.attr.colorOnPrimary, typedValue, true);
        int color = typedValue.data;
        window.setStatusBarColor(color);
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
    }

    private void initAnimation() {
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
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();

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
}