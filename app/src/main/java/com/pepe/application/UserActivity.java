package com.pepe.application;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class UserActivity extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText emailEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        // Inicializar los elementos de la interfaz
        usernameEditText = findViewById(R.id.usernameEditText);
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);
        registerButton = findViewById(R.id.registerButton);

        // Configurar el clic del botón de inicio de sesión
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = usernameEditText.getText().toString();
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                // Verificar las credenciales según el rol
                if (username.equals("Admin") && email.equals("Wilbert") && password.equals("1111")) {
                    // Admin
                    // Guardar el nombre y el correo en PreferenceManager
                    PreferenceManager preferenceManager = PreferenceManager.getInstance(UserActivity.this);
                    preferenceManager.setUsername(username);
                    preferenceManager.setEmail(email);

                    // Mensaje de éxito
                    Toast.makeText(UserActivity.this, "Rol y Nombre identificado", Toast.LENGTH_SHORT).show();

                    // Redirigir a la clase MotionActivity
                    Intent intent = new Intent(UserActivity.this, MainActivity.class);
                    startActivity(intent);
                } else if (username.equals("Taquero") && email.equals("Jaime") && password.equals("2222")) {
                    // Taquero
                    // Guardar el nombre y el correo en PreferenceManager
                    PreferenceManager preferenceManager = PreferenceManager.getInstance(UserActivity.this);
                    preferenceManager.setUsername(username);
                    preferenceManager.setEmail(email);

                    // Mensaje de éxito
                    Toast.makeText(UserActivity.this, "Rol y Nombre identificado", Toast.LENGTH_SHORT).show();

                    // Redirigir a la clase MotionActivity
                    Intent intent = new Intent(UserActivity.this, MainActivity.class);
                    startActivity(intent);
                } else if (username.equals("Mesero") && email.equals("Pepe") && password.equals("3333")) {
                    // Mesero 1
                    // Guardar el nombre y el correo en PreferenceManager
                    PreferenceManager preferenceManager = PreferenceManager.getInstance(UserActivity.this);
                    preferenceManager.setUsername(username);
                    preferenceManager.setEmail(email);

                    // Mensaje de éxito
                    Toast.makeText(UserActivity.this, "Rol y Nombre identificado", Toast.LENGTH_SHORT).show();

                    // Redirigir a la clase MotionActivity
                    Intent intent = new Intent(UserActivity.this, MainActivity.class);
                    startActivity(intent);
                } else if (username.equals("Mesero") && email.equals("Nicole") && password.equals("4444")) {
                    // Mesero 2
                    // Guardar el nombre y el correo en PreferenceManager
                    PreferenceManager preferenceManager = PreferenceManager.getInstance(UserActivity.this);
                    preferenceManager.setUsername(username);
                    preferenceManager.setEmail(email);

                    // Mensaje de éxito
                    Toast.makeText(UserActivity.this, "Rol y Nombre identificado", Toast.LENGTH_SHORT).show();

                    // Redirigir a la clase MotionActivity
                    Intent intent = new Intent(UserActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    // Credenciales incorrectas
                    Toast.makeText(UserActivity.this, "Credenciales incorrectas. Intenta de nuevo.", Toast.LENGTH_SHORT).show();
                }
            }
        });


        // Configurar el clic del botón de registro
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Limpiar campos
                usernameEditText.getText().clear();
                emailEditText.getText().clear();
                passwordEditText.getText().clear();

                // Crear un Intent para abrir la RegistroActivity
                //Intent intent = new Intent(UserActivity.this, RegistroActivity.class);
                //startActivity(intent);
            }
        });

    }
}



