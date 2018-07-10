package com.android.jerry.dotapedia;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.io.Serializable;

@Entity(tableName = "dota_heroes")
public class Hero implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    private String mName;

    private String mIconPath;
    private String mCategory;
    private String mBiography;

    public Hero(@NonNull String mName) {
        this.mName = mName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getName() {
        return mName;
    }

    public void setName(@NonNull String mName) {
        this.mName = mName;
    }

    public String getIconPath() {
        return mIconPath;
    }

    public void setIconPath(String mIconPath) {
        this.mIconPath = mIconPath;
    }

    public String getCategory() {
        return mCategory;
    }

    public void setCategory(String mCategory) {
        this.mCategory = mCategory;
    }

    public String getBiography() {
        return mBiography;
    }

    public void setBiography(String mBiography) {
        this.mBiography = mBiography;
    }
}
