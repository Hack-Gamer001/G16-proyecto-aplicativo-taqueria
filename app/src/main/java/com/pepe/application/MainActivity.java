package com.pepe.application;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.pepe.application.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    private TextView navHeaderTitle;
    private TextView navHeaderSubtitle;
    private MediaPlayer mediaPlayer; // Agregar instancia de MediaPlayer

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);
        mediaPlayer = MediaPlayer.create(this, R.raw.audioinicio);//Hola will dxddxdxdx hay otro audio de inicio solo borra el xd del nombre
        playAudio();

        // Código para el botón FAB (FloatingActionButton)
        binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Agregar un Intent para abrir SplashActivity
                Intent intent = new Intent(MainActivity.this, SplashActivity.class);
                startActivity(intent);
            }
        });

        // Obtener referencias a las vistas del encabezado de la barra de navegación
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        View headerView = navigationView.getHeaderView(0);
        navHeaderTitle = headerView.findViewById(R.id.textView);
        navHeaderSubtitle = headerView.findViewById(R.id.textView2);

        // Inicializar la barra de navegación y el controlador de navegación
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_cliente, R.id.nav_pedido, R.id.nav_lista_pedidos , R.id.nav_redes, R.id.nav_sonido, R.id.nav_gps, R.id.mapap)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        // Obtener el nombre y correo de PreferenceManager y actualizar los TextViews
        String username = PreferenceManager.getInstance(this).getUsername();
        String email = PreferenceManager.getInstance(this).getEmail();

        navHeaderTitle.setText(username);
        navHeaderSubtitle.setText(email);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    // Agregar el método stopMediaPlayer para detener el MediaPlayer
    public void stopMediaPlayer() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
    private void playAudio() {
        if (mediaPlayer != null) {
            mediaPlayer.start();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Detener y liberar recursos cuando la actividad se destruye
        stopMediaPlayer();
    }
}
