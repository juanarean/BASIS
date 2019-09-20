package com.basis.basis;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.basis.basis.db.ClientesDatabase;
import com.basis.basis.db.dao.ClientsDao;
import com.basis.basis.db.entity.ClientsEntity;

import java.util.List;

public class ClientsRepository {
    private ClientsDao clientsDao;

    public ClientsRepository(Application application) {
        ClientesDatabase db = ClientesDatabase.getInstance(application);
        clientsDao = db.clientsDao();
    }

    public LiveData<List<ClientsEntity>> getAll() { return clientsDao.getAll(); }

    public void insert (ClientsEntity clienteNuevo) {
        new insertAsyncTask(clientsDao).execute(clienteNuevo);
    }

    private static class insertAsyncTask extends AsyncTask<ClientsEntity, Void, Void> {
        private ClientsDao clientsDaoAsyncTask;

        insertAsyncTask(ClientsDao dao) {
            clientsDaoAsyncTask = dao;
        }

        @Override
        protected Void doInBackground(ClientsEntity... clientsEntities) {
            clientsDaoAsyncTask.insert(clientsEntities[0]);
            return null;
        }
    }
}
