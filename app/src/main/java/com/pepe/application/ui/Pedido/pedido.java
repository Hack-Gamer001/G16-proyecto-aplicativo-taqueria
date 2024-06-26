package com.pepe.application.ui.Pedido;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "pedido")
public class pedido {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "idpedido")
    private int idPedido;

    @ColumnInfo(name = "plato")
    private String plato;

    @ColumnInfo(name = "descripcion")
    private String descripcion;

    @ColumnInfo(name = "cantidad")
    private int cantidad;

    @Ignore
    public pedido() {
        // Constructor vacío necesario para Room
    }

    public pedido(String plato, String descripcion, int cantidad) {
        this.plato = plato;
        this.descripcion = descripcion;
        this.cantidad = cantidad;
    }

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
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
