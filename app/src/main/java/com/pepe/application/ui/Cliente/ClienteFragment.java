package com.pepe.application.ui.Cliente;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import com.pepe.application.R;
import java.sql.Connection;
import java.sql.PreparedStatement;
import com.pepe.application.DatabaseConnection;


public class ClienteFragment extends Fragment {

    private EditText etNombre, etApellidos, etDNI;

    // Corregir el nombre de la variable
    private Button btnIngresar, btnLimpiar;

    private MediaPlayer mediaPlayer; // Agregar instancia de MediaPlayer

    public ClienteFragment() {
        // Constructor vacío requerido
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflar el diseño del fragmento
        View view = inflater.inflate(R.layout.fragment_cliente, container, false);

        // Vincular elementos de la interfaz de usuario con variables Java
        etNombre = view.findViewById(R.id.etNombre);
        etApellidos = view.findViewById(R.id.etApellidos);
        etDNI = view.findViewById(R.id.etDNI);

        // Corregir el nombre de la variable
        btnIngresar = view.findViewById(R.id.btnIngresar);
        btnLimpiar = view.findViewById(R.id.btnLimpiar); // Agregado

        // Inicializar el MediaPlayer con el sonido
        mediaPlayer = MediaPlayer.create(getActivity(), R.raw.ingresandodatos);

        // Configurar un Listener para el botón Ingresar
        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Reproducir el sonido al hacer clic en el botón
                playSound();

                // Obtener datos de los EditText
                String nombre = etNombre.getText().toString();
                String apellidos = etApellidos.getText().toString();
                String dni = etDNI.getText().toString();

                // Verificar datos de entrada
                if (nombre.isEmpty() || apellidos.isEmpty() || dni.isEmpty()) {
                    Toast.makeText(getActivity(), "Los datos de entrada no pueden ser nulos", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Insertar datos en la base de datos
                insertarCliente(nombre, apellidos, dni);

                // Limpiar campos después de insertar
                limpiarCampos();
            }
        });

        // Configurar un Listener para el botón Limpiar
        btnLimpiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                limpiarCampos();
            }
        });

        return view;
    }


    private void insertarCliente(String nombre, String apellidos, String dni) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Connection conexion = null;

        try {
            // Establecer la conexión con SQL Server
            conexion = DatabaseConnection.getConnection();

            // Cambiar el nombre de las variables y ajustar la consulta SQL
            String query = "INSERT INTO Cliente (Nombre, Apellido, DNI) VALUES (?, ?, ?)";
            try (PreparedStatement preparedStatement = conexion.prepareStatement(query)) {
                preparedStatement.setString(1, nombre);
                preparedStatement.setString(2, apellidos);
                preparedStatement.setString(3, dni);
                preparedStatement.executeUpdate();
            }

            // Mostrar un mensaje de éxito
            mostrarMensaje("Datos enviados con éxito");

        } catch (Exception e) {
            // Manejar las excepciones, por ejemplo, mostrar un mensaje de error
            e.printStackTrace();
            mostrarMensaje("Error al enviar los datos: " + e.getMessage());

        } finally {
            // Cerrar la conexión
            DatabaseConnection.closeConnection(conexion);
        }
    }
    private void limpiarCampos() {
        etNombre.setText("");
        etApellidos.setText("");
        etDNI.setText("");
    }
    private void mostrarMensaje(String mensaje) {
        Toast.makeText(getActivity(), mensaje, Toast.LENGTH_SHORT).show();
    }

    // Método para reproducir el sonido
    private void playSound() {
        if (mediaPlayer != null) {
            mediaPlayer.start();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Liberar recursos cuando se destruye la vista
        releaseMediaPlayer();
    }

    // Método para liberar el MediaPlayer
    private void releaseMediaPlayer() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}

