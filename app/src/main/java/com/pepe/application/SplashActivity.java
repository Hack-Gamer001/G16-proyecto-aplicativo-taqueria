package com.pepe.application;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    private ImageView imageViewLogo;
    private ImageView imageViewLogo2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN, android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        imageViewLogo = findViewById(R.id.imageViewlogo);
        imageViewLogo2 = findViewById(R.id.imageViewlogo2);

        // Agregar animaciones a los textos
        Animation animationUp = AnimationUtils.loadAnimation(this, R.anim.slide_up);
        Animation animationDown = AnimationUtils.loadAnimation(this, R.anim.slide_down);

        TextView textViewSistemas = findViewById(R.id.textViewSistemas);
        TextView textView3de = findViewById(R.id.textView3de);

        textViewSistemas.setAnimation(animationUp);
        textView3de.setAnimation(animationDown);

        // Animación de las imágenes en paralelo
        animateImagesInParallel(imageViewLogo, imageViewLogo2, 700, 2000);

        // Animación de las imágenes moviéndose de izquierda a derecha y de derecha a izquierda
        animateImagesLeftToRight(imageViewLogo, 2000);
        animateImagesRightToLeft(imageViewLogo2, 2000);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, MotionActivity.class);
                startActivity(intent);
                finish();
            }
        }, 3400);
    }

    private void animateImagesInParallel(final ImageView imageView1, final ImageView imageView2, int targetHeight, long duration) {
        ValueAnimator animator1 = ValueAnimator.ofInt(0, targetHeight);
        ValueAnimator animator2 = ValueAnimator.ofInt(0, targetHeight);

        animator1.addUpdateListener(new AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                imageView1.getLayoutParams().height = value;
                imageView1.requestLayout();
            }
        });

        animator2.addUpdateListener(new AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                imageView2.getLayoutParams().height = value;
                imageView2.requestLayout();
            }
        });

        animator1.setDuration(duration);
        animator2.setDuration(duration);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animator1, animator2);

        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                imageView1.setVisibility(View.VISIBLE);
                imageView2.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                // Aquí puedes realizar acciones al finalizar la animación si es necesario
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });

        animatorSet.start();
    }

    private void animateImagesLeftToRight(final ImageView imageView, long duration) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(imageView, "translationX", -1000f, 0f);
        animator.setDuration(duration);
        animator.start();
    }

    private void animateImagesRightToLeft(final ImageView imageView, long duration) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(imageView, "translationX", 1000f, 0f);
        animator.setDuration(duration);
        animator.start();
    }
}
