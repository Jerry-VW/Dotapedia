package com.android.jerry.dotapedia;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface ItemDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Item item);

    @Query("DELETE FROM dota_items")
    void deleteAll();

    @Query("SELECT * FROM dota_items ORDER BY mPrice")
    List<Item> getAllItems();

    @Query("SELECT COUNT(*) FROM dota_items")
    int getTableRowCount();

    // Search items by keywords
    @Query("SELECT * FROM dota_items WHERE mName LIKE :keyword OR mPrice LIKE :keyword OR mAttributes LIKE :keyword OR mEffects LIKE :keyword ORDER BY mPrice")
    List<Item> searchItemByKeywords(String keyword);
}
