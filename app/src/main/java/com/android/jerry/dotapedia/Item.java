package com.android.jerry.dotapedia;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.io.Serializable;

@Entity(tableName = "dota_items")
public class Item implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    private String mName;

    private String mIconPath;
    private int mPrice;
    private String mAttributes;
    private String mEffects;
    private String mDescription;

    public Item(String name) {
        this.mName = name;
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

    public int getPrice() {
        return mPrice;
    }

    public void setPrice(int mPrice) {
        this.mPrice = mPrice;
    }

    public String getAttributes() {
        return mAttributes;
    }

    public void setAttributes(String mAttributes) {
        this.mAttributes = mAttributes;
    }

    public String getEffects() {
        return mEffects;
    }

    public void setEffects(String mEffects) {
        this.mEffects = mEffects;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String mDescription) {
        this.mDescription = mDescription;
    }
}
