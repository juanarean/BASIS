package com.basis.basis.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.basis.basis.db.entity.BASentity;

import java.util.List;

@Dao
public interface BASdao {
    @Insert
    void insert(BASentity bas);

    @Update
    void update(BASentity bas);

    @Query("DELETE FROM bas_table")
    void deleteAll();

    @Query("DELETE FROM bas_table WHERE id = :idBAS")
    void deleteById(int idBAS);

    @Query("SELECT * FROM bas_table ORDER BY BAS ASC")
    LiveData<List<BASentity>> getAll();
}
