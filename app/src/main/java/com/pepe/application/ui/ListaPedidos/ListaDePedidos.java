package com.pepe.application.ui.ListaPedidos;

public class ListaDePedidos {
    private int listaID;
    private int clienteID;
    private int pedidoID;
    private String plato;
    private String descripcion;
    private int cantidad;

    public ListaDePedidos(int listaID, int clienteID, int pedidoID, String plato, String descripcion, int cantidad) {
        this.listaID = listaID;
        this.clienteID = clienteID;
        this.pedidoID = pedidoID;
        this.plato = plato;
        this.descripcion = descripcion;
        this.cantidad = cantidad;
    }

    // Getters y Setters aquí...

    @Override
    public String toString() {
        return "ListaDePedidos{" +
                "listaID=" + listaID +
                ", clienteID=" + clienteID +
                ", pedidoID=" + pedidoID +
                ", plato='" + plato + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", cantidad=" + cantidad +
                '}';
    }

    public int getClienteID() {
        return clienteID;
    }

    public int getPedidoID() {
        return pedidoID;
    }

    public String getPlato() {
        return plato;
    }

    public void setPlato(String plato) {
        this.plato = plato;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
