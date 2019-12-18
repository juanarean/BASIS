package com.basis.basis.data;

import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.basis.basis.common.MyApp;
import com.basis.basis.retrofit.AuthBasisClient;
import com.basis.basis.retrofit.AuthBasisService;
import com.basis.basis.retrofit.Responses.Clientes;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BasisRepository {

    AuthBasisService authBasisService;
    AuthBasisClient authBasisClient;
    LiveData<List<Clientes>> allClientes;

    public BasisRepository() {
        authBasisClient = AuthBasisClient.getInstance();
        authBasisService = authBasisClient.getAuthBasisService();
    }

    public LiveData<List<Clientes>> getAllClientes() {
        final MutableLiveData<List<Clientes>> data = new MutableLiveData<>();

        Call<List<Clientes>> call = authBasisService.getAllClients();
        call.enqueue(new Callback<List<Clientes>>() {
            @Override
            public void onResponse(Call<List<Clientes>> call, Response<List<Clientes>> response) {
                if(response.isSuccessful()) {
                    data.setValue(response.body());
                } else {
                    Toast.makeText(MyApp.getContext(),"Respuesta incorrecta", Toast.LENGTH_LONG).show();
                }

            }
            @Override
            public void onFailure(Call<List<Clientes>> call, Throwable t) {
                Toast.makeText(MyApp.getContext(),"Error en la conexion", Toast.LENGTH_LONG).show();
            }
        });

        return data;

    }
}
