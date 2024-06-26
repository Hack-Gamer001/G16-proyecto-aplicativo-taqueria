package com.pepe.application.ui.ListaPedidos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.pepe.application.R;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import com.pepe.application.DatabaseConnection;

import android.widget.Button;
import android.widget.Toast;


import android.util.Log;

// Importa tus clases de modelo y de base de datos si es necesario

public class ListaPedidokFragment extends Fragment {

    private RecyclerView recyclerView;
    private ListaPedidosAdapter listaPedidosAdapter;
    private List<ListaDePedidos> listaDePedidosList;

    private Button buttonEliminar;

    public ListaPedidokFragment() {
        // Constructor vacío
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lista_pedidok, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewPedidos);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Inicializa la lista antes de llamar al método para cargar los datos
        listaDePedidosList = new ArrayList<>();

        cargarListaDePedidos();  // Llama al método para cargar los datos

        listaPedidosAdapter = new ListaPedidosAdapter(listaDePedidosList);
        recyclerView.setAdapter(listaPedidosAdapter);

        // Configura el oyente de clics en el adaptador
        listaPedidosAdapter.setOnItemClickListener(new ListaPedidosAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                // Aquí obtienes el ID del pedido del elemento en la posición clicada
                int pedidoIDToDelete = listaDePedidosList.get(position).getPedidoID();

                // Llama al método para eliminar el pedido de la base de datos
                eliminarPedido(pedidoIDToDelete);
            }
        });

        return view;
    }

    private void cargarListaDePedidos() {
        Connection conexion = null;

        try {
            // Establecer la conexión con SQL Server
            conexion = DatabaseConnection.getConnection();

            if (conexion != null) {
                Log.d("DatabaseConnection", "Conexión exitosa");

                // Consulta para obtener datos de ListaDePedidos
                String query = "SELECT ListaID, ClienteID, PedidoID, Plato, Descripcion, Cantidad FROM ListaDePedidos";
                try (PreparedStatement preparedStatement = conexion.prepareStatement(query);
                     ResultSet resultSet = preparedStatement.executeQuery()) {

                    // Limpiar la lista actual
                    listaDePedidosList.clear();

                    // Llenar la lista con datos de la base de datos
                    while (resultSet.next()) {
                        int listaID = resultSet.getInt("ListaID");
                        int clienteID = resultSet.getInt("ClienteID");
                        int pedidoID = resultSet.getInt("PedidoID");
                        String plato = resultSet.getString("Plato");
                        String descripcion = resultSet.getString("Descripcion");
                        int cantidad = resultSet.getInt("Cantidad");

                        // Agregar un nuevo objeto ListaDePedidos a la lista
                        listaDePedidosList.add(new ListaDePedidos(listaID, clienteID, pedidoID, plato, descripcion, cantidad));
                    }

                    // Notificar al adaptador que los datos han cambiado
                    listaPedidosAdapter.notifyDataSetChanged();

                    // Mostrar un mensaje de éxito
                    mostrarMensaje("Datos cargados exitosamente");

                } catch (Exception e) {
                    // Manejar las excepciones de SQL
                    e.printStackTrace();
                    //mostrarMensaje("Error al ejecutar la consulta: " + e.getMessage());
                    Log.e("DatabaseConnection", "Error al ejecutar la consulta: " + e.getMessage());
                }

            } else {
                Log.e("DatabaseConnection", "Error al conectar a la base de datos");
            }

        } catch (Exception e) {
            // Manejar las excepciones generales
            e.printStackTrace();
            //mostrarMensaje("Error al cargar la lista de pedidos: " + e.getMessage());
            Log.e("DatabaseConnection", "Error al cargar la lista de pedidos: " + e.getMessage());

        } finally {
            // Cerrar la conexión
            DatabaseConnection.closeConnection(conexion);
        }
    }

    private void mostrarMensaje(String mensaje) {
        // Mostrar un mensaje utilizando Toast
        if (getContext() != null) {
            Toast.makeText(getContext(), mensaje, Toast.LENGTH_SHORT).show();
        }
    }
    // Método para obtener el ID del pedido que se desea eliminar (puedes implementarlo según tus necesidades)
    private int obtenerPedidoIDToDelete() {
        // Aquí asumimos que listaDePedidosList contiene al menos un pedido
        if (!listaDePedidosList.isEmpty()) {
            // Devolvemos el ID del primer pedido en la lista
            return listaDePedidosList.get(0).getPedidoID();
        } else {
            // Si la lista está vacía, devuelve un valor predeterminado o maneja la situación según tus necesidades
            return -1; // Cambia esto según tus necesidades
        }
    }


    // Método para eliminar un pedido de la base de datos
    private void eliminarPedido(int pedidoID) {
        Connection conexion = null;

        try {
            // Establecer la conexión con SQL Server
            conexion = DatabaseConnection.getConnection();

            if (conexion != null) {
                Log.d("DatabaseConnection", "Conexión exitosa");

                // Consulta para eliminar un pedido por ID
                String query = "DELETE FROM ListaDePedidos WHERE PedidoID = ?";
                try (PreparedStatement preparedStatement = conexion.prepareStatement(query)) {
                    preparedStatement.setInt(1, pedidoID);

                    // Ejecutar la consulta de eliminación
                    int filasAfectadas = preparedStatement.executeUpdate();

                    if (filasAfectadas > 0) {
                        // Mostrar un mensaje de éxito
                        mostrarMensaje("Pedido eliminado exitosamente");

                        // Recargar la lista después de la eliminación
                        cargarListaDePedidos();
                    } else {
                        mostrarMensaje("No se pudo eliminar el pedido");
                    }

                } catch (Exception e) {
                    // Manejar las excepciones de SQL
                    e.printStackTrace();
                    Log.e("DatabaseConnection", "Error al ejecutar la consulta de eliminación: " + e.getMessage());
                }

            } else {
                Log.e("DatabaseConnection", "Error al conectar a la base de datos");
            }

        } catch (Exception e) {
            // Manejar las excepciones generales
            e.printStackTrace();
            Log.e("DatabaseConnection", "Error al eliminar el pedido: " + e.getMessage());

        } finally {
            // Cerrar la conexión
            DatabaseConnection.closeConnection(conexion);
        }
    }



}