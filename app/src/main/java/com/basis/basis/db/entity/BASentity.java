package com.basis.basis.db.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "bas_table")
public class BASentity {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id;
    private String BAS;

    public BASentity(String BAS) {
        this.BAS = BAS;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBAS() {
        return BAS;
    }

    public void setBAS(String BAS) {
        this.BAS = BAS;
    }
}
