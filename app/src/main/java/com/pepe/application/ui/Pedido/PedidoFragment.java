package com.pepe.application.ui.Pedido;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import com.pepe.application.R;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.pepe.application.DatabaseConnection;

public class PedidoFragment extends Fragment {

    private CheckBox checkBoxTacos, checkBoxHamburguesas;
    private EditText etCantidad;
    private Button btnReducir, btnIncrementar, btnIngresar;
    private Spinner spinnerClientes, spinnerDescripcionPlato;

    private MediaPlayer mediaPlayer;

    public PedidoFragment() {
        // Constructor vacío requerido por Fragment
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflar el diseño para este fragmento
        View rootView = inflater.inflate(R.layout.fragment_pedido, container, false);

        // Vincular elementos de la interfaz de usuario con variables Java
        checkBoxTacos = rootView.findViewById(R.id.checkBoxTacos);
        checkBoxHamburguesas = rootView.findViewById(R.id.checkBoxHamburguesas);
        etCantidad = rootView.findViewById(R.id.etCantidad);
        btnReducir = rootView.findViewById(R.id.btnReducir);
        btnIncrementar = rootView.findViewById(R.id.btnIncrementar);
        btnIngresar = rootView.findViewById(R.id.btnIngresar);
        spinnerClientes = rootView.findViewById(R.id.spinnerClientes);
        spinnerDescripcionPlato = rootView.findViewById(R.id.etDescripcionPlato);

        mediaPlayer = MediaPlayer.create(getActivity(), R.raw.ingresadopedido);

        // Establecer el valor inicial en 1 para el EditText etCantidad
        etCantidad.setText("1");

        // Configurar el Listener para el botón Ingresar
        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playSound();
                // Obtener datos de los elementos del fragmento
                String platoSeleccionado = obtenerPlatoSeleccionado();
                String descripcionPlato = obtenerDescripcionPlatoSeleccionado();
                String cantidad = etCantidad.getText().toString();

                // Verificar datos de entrada
                if (descripcionPlato.isEmpty() || cantidad.isEmpty()) {
                    mostrarMensaje("Los datos de entrada no pueden ser nulos");
                    return;
                }

                // Insertar datos en la base de datos
                insertarPedido(platoSeleccionado,descripcionPlato, cantidad);

                // Restablecer el valor inicial en 1 después de ingresar los datos
                etCantidad.setText("1");
            }
        });

        // Configurar el Listener para el botón Incrementar
        btnIncrementar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                actualizarCantidad(1);
            }
        });

        // Configurar el Listener para el botón Reducir
        btnReducir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                actualizarCantidad(-1);
            }
        });

        // Cargar la lista de clientes en el Spinner
        cargarClientesEnSpinner();


        // Cargar la lista de platos en el Spinner
        cargarPlatosEnSpinner();

        return rootView;
    }
    private String obtenerDescripcionPlatoSeleccionado() {
        // Lógica para obtener el plato seleccionado del Spinner
        return spinnerDescripcionPlato.getSelectedItem().toString();
    }

    private void cargarPlatosEnSpinner() {
        // Lista de opciones para el Spinner (puedes obtenerla de tu base de datos o proporcionarla de otra manera)
        List<String> opcionesPlato = Arrays.asList("De Pollo", "De Carne", "Mixto", "Combinado");

        // Configurar un adaptador para el Spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, opcionesPlato);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Establecer el adaptador en el Spinner
        spinnerDescripcionPlato.setAdapter(adapter);
    }


    private void cargarClientesEnSpinner() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Connection conexion = null;
        List<String> nombresClientes = new ArrayList<>();

        try {
            // Establecer la conexión con SQL Server
            conexion = DatabaseConnection.getConnection();

            // Consulta para obtener nombres y apellidos de clientes
            String query = "SELECT ClienteID, Nombre, Apellido FROM Cliente";
            try (PreparedStatement preparedStatement = conexion.prepareStatement(query);
                 ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int clienteID = resultSet.getInt("ClienteID");
                    String nombre = resultSet.getString("Nombre");
                    String apellido = resultSet.getString("Apellido");
                    // Agregar el nombre completo al listado
                    nombresClientes.add(nombre + " " + apellido + "|" + clienteID);
                }
            }

            // Configurar el adaptador para el Spinner
            ArrayAdapter<String> adapter = new ArrayAdapter<>(
                    //requireContext(), // Si usas FragmentCompat
                    getActivity(), // Cambiado a getActivity() si estás usando androidx.fragment.app.Fragment
                    android.R.layout.simple_spinner_item,
                    nombresClientes
            );
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerClientes.setAdapter(adapter);

        } catch (Exception e) {
            // Manejar las excepciones, por ejemplo, mostrar un mensaje de error
            e.printStackTrace();
            mostrarMensaje("Error al cargar los clientes: " + e.getMessage());

        } finally {
            // Cerrar la conexión
            DatabaseConnection.closeConnection(conexion);
        }
    }

    private void mostrarMensaje(String mensaje) {
        Toast.makeText(getActivity(), mensaje, Toast.LENGTH_SHORT).show();
    }

    private void actualizarCantidad(int incremento) {
        try {
            int cantidad = Integer.parseInt(etCantidad.getText().toString()) + incremento;
            if (cantidad > 0) {
                etCantidad.setText(String.valueOf(cantidad));
            }
        } catch (NumberFormatException e) {
            // Manejar la excepción si la entrada no es un número
            mostrarMensaje("Ingrese un número válido para la cantidad");
        }
    }

    private void insertarPedido( String platoSeleccionado, String descripcionPlato, String cantidad) {
        Connection conexion = null;

        try {
            // Establecer la conexión con SQL Server
            conexion = DatabaseConnection.getConnection();

            // Consulta SQL para insertar un nuevo pedido
            String query = "INSERT INTO Pedido (Plato, Descripcion, Cantidad) VALUES (?, ?, ?)";
            try (PreparedStatement preparedStatement = conexion.prepareStatement(query)) {
                preparedStatement.setString(1, platoSeleccionado);
                preparedStatement.setString(2, descripcionPlato); // Utilizar la descripción del plato seleccionado
                preparedStatement.setInt(3, Integer.parseInt(cantidad));
                preparedStatement.executeUpdate();
            }

            // Mostrar un mensaje de éxito
            mostrarMensaje("Pedido ingresado con éxito");

        } catch (Exception e) {
            // Manejar las excepciones, por ejemplo, mostrar un mensaje de error
            e.printStackTrace();
            mostrarMensaje("Error al ingresar el pedido: " + e.getMessage());

        } finally {
            // Cerrar la conexión
            DatabaseConnection.closeConnection(conexion);
        }
    }
    private String obtenerPlatoSeleccionado() {
        if (checkBoxTacos.isChecked()) {
            return "Tacos";
        } else if (checkBoxHamburguesas.isChecked()) {
            return "Hamburguesas";
        } else {
            // Puedes manejar este caso según tus necesidades
            return "";
        }
    }

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
