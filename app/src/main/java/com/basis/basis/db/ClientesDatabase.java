package com.basis.basis.db;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.basis.basis.db.dao.ClientsDao;
import com.basis.basis.db.entity.ClientsEntity;

@Database(entities = {ClientsEntity.class}, version = 1)
public abstract class ClientesDatabase extends RoomDatabase {
    public abstract ClientsDao clientsDao();
    private static volatile ClientesDatabase INSTANCE;

    public static ClientesDatabase getInstance(final Context context) {
        if(INSTANCE == null) {
            synchronized (ClientesDatabase.class) {
                if(INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            ClientesDatabase.class, "clientes_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
