package com.basis.basis.retrofit.Responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Clientes {

    @SerializedName("nombre")
    @Expose
    private String nombre;
    @SerializedName("direccion")
    @Expose
    private String direccion;

    /**
     * No args constructor for use in serialization
     *
     */
    public Clientes() {
    }

    /**
     *
     * @param direccion
     * @param nombre
     */
    public Clientes(String nombre, String direccion) {
        super();
        this.nombre = nombre;
        this.direccion = direccion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

}
