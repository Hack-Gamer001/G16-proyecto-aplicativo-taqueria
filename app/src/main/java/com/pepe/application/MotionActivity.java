package com.pepe.application;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.constraintlayout.motion.widget.MotionLayout.TransitionListener;

public class MotionActivity extends AppCompatActivity {

    private MotionLayout motionLayout; // Declarar la variable MotionLayout

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_motion); // Asegúrate de usar el nombre correcto del archivo XML

        motionLayout = findViewById(R.id.motionLayout1); // Asigna la vista de MotionLayout a la variable

        motionLayout.setTransitionListener(new TransitionListener() {
            @Override
            public void onTransitionStarted(MotionLayout motionLayout, int startId, int endId) {
                // Código a ejecutar cuando comienza la transición
            }

            @Override
            public void onTransitionChange(MotionLayout motionLayout, int startId, int endId, float progress) {
                // Código a ejecutar durante la transición
            }

            @Override
            public void onTransitionCompleted(MotionLayout motionLayout, int currentId) {
                if (currentId == R.id.end) {
                    Intent intent = new Intent(MotionActivity.this, UserActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onTransitionTrigger(MotionLayout motionLayout, int triggerId, boolean positive, float progress) {
                // Código a ejecutar cuando se alcanza un punto de disparo en la transición
            }
        });
    }
}

