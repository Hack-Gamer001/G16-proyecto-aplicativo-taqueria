package com.pepe.application.ui.Cliente;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "cliente")
public class cliente {
    @ColumnInfo(name = "idcliente")
    @PrimaryKey(autoGenerate = true)
    int idcliente;
    @ColumnInfo(name = "nombres")
    String nombres;
    @ColumnInfo(name = "dni")
    String dni;
    @ColumnInfo(name = "apellidos")
    String apellidos;

    @Ignore
    public cliente() {
    }

    public cliente(String nombres, String dni, String apellidos) {

        this.nombres = nombres;
        this.dni = dni;
        this.apellidos = apellidos;
    }

    public int getId() {
        return idcliente;
    }

    public void setId(int id) {
        this.idcliente = id;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }


}
