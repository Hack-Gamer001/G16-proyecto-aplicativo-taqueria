package com.pepe.application.ui.ListaPedidos;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.pepe.application.R;

import java.util.List;

public class ListaPedidosAdapter extends RecyclerView.Adapter<ListaPedidosAdapter.ViewHolder> {
    private List<ListaDePedidos> listaDePedidosList;
    private OnItemClickListener itemClickListener;

    public ListaPedidosAdapter(List<ListaDePedidos> listaDePedidosList) {
        this.listaDePedidosList = listaDePedidosList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lista_pedidos, parent, false);
        return new ViewHolder(view, itemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ListaDePedidos listaDePedidos = listaDePedidosList.get(position);

        // Configura las vistas en función de los datos de la listaDePedidos
        holder.textViewClienteID.setText("Cliente ID: " + listaDePedidos.getClienteID());
        holder.textViewPedidoID.setText("Pedido ID: " + listaDePedidos.getPedidoID());
        holder.textViewPlato.setText("Plato: " + listaDePedidos.getPlato());
        holder.textViewDescripcion.setText("Descripción: " + listaDePedidos.getDescripcion());
        holder.textViewCantidad.setText("Cantidad: " + listaDePedidos.getCantidad());

        // Configura el índice de posición en el botón "Eliminar"
        holder.buttonEliminar.setTag(position);
    }

    @Override
    public int getItemCount() {
        return listaDePedidosList.size();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.itemClickListener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewClienteID;
        private TextView textViewPedidoID;
        private TextView textViewPlato;
        private TextView textViewDescripcion;
        private TextView textViewCantidad;
        private Button buttonEliminar;

        public ViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);

            textViewClienteID = itemView.findViewById(R.id.textViewClienteID);
            textViewPedidoID = itemView.findViewById(R.id.textViewPedidoID);
            textViewPlato = itemView.findViewById(R.id.textViewPlato);
            textViewDescripcion = itemView.findViewById(R.id.textViewDescripcion);
            textViewCantidad = itemView.findViewById(R.id.textViewCantidad);
            buttonEliminar = itemView.findViewById(R.id.buttonEliminar);

            // Configura el clic del botón "Eliminar"
            buttonEliminar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        int position = (int) buttonEliminar.getTag();
                        listener.onItemClick(position);
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}
