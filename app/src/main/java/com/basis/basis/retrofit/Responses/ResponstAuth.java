
package com.basis.basis.retrofit.Responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponstAuth {

    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("usuario")
    @Expose
    private String usuario;
    @SerializedName("password")
    @Expose
    private String password;

    /**
     * No args constructor for use in serialization
     * 
     */
    public ResponstAuth() {
    }

    /**
     * 
     * @param usuario
     * @param token
     * @param password
     */
    public ResponstAuth(String token, String usuario, String password) {
        super();
        this.token = token;
        this.usuario = usuario;
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
