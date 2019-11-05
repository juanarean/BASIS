package com.basis.basis.retrofit;

import com.basis.basis.retrofit.Responses.Clientes;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface AuthBasisService {

    @GET("clientes/all")
    Call<List<Clientes>> getAllClients();
}
