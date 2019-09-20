package com.basis.basis.db.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "clientes")
public class ClientsEntity {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id;
    private String Cliente;
    private int Longitud;
    private int Latitud;

    public ClientsEntity(String Cliente, int Longitud, int Latitud) {
        this.Cliente = Cliente;
        this.Longitud = Longitud;
        this.Latitud = Latitud;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCliente() {
        return Cliente;
    }

    public void setCliente(String cliente) {
        Cliente = cliente;
    }

    public int getLongitud() {
        return Longitud;
    }

    public void setLongitud(int longitud) {
        Longitud = longitud;
    }

    public int getLatitud() {
        return Latitud;
    }

    public void setLatitud(int latitud) {
        Latitud = latitud;
    }
}
