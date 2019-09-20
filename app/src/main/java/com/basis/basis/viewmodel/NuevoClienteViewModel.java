package com.basis.basis.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.basis.basis.ClientsRepository;
import com.basis.basis.db.entity.ClientsEntity;

import java.util.List;

public class NuevoClienteViewModel extends AndroidViewModel {
    private LiveData<List<ClientsEntity>> allClientes;
    private ClientsRepository clientsRepository;

    public NuevoClienteViewModel(Application application) {
        super(application);

        clientsRepository = new ClientsRepository(application);
        allClientes = clientsRepository.getAll();
    }

    //El fragment que necesita recibir la nueva lista de datos
    public  LiveData<List<ClientsEntity>> getAllClientes() { return allClientes; }

    //El fragment que inserta el nuevo cliente, lo pasa por este viewmodel
    public void insertarCliente(ClientsEntity nuevoClienteEntity) { clientsRepository.insert(nuevoClienteEntity); }

}
