package com.basis.basis.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.basis.basis.db.entity.ClientsEntity;

import java.util.List;

@Dao
public interface ClientsDao {
    @Insert
    void insert(ClientsEntity job);

    @Update
    void update(ClientsEntity job);

    @Query("DELETE FROM clientes")
    void deleteAll();

    @Query("DELETE FROM clientes WHERE id = :idBAS")
    void deleteById(int idBAS);

    @Query("SELECT * FROM clientes ORDER BY Cliente ASC")
    LiveData<List<ClientsEntity>> getAll();
}
