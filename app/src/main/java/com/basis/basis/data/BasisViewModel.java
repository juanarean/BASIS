package com.basis.basis.data;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.basis.basis.retrofit.Responses.Clientes;

import java.util.List;

public class BasisViewModel extends AndroidViewModel {
    private BasisRepository basisRepository;
    private LiveData<List<Clientes>> clientes;

    public BasisViewModel(@NonNull Application application) {
        super(application);
        basisRepository = new BasisRepository();
        clientes = basisRepository.getAllClientes();
    }

    public LiveData<List<Clientes>> getClientes() { return clientes; }
}
