package com.android.jerry.dotapedia;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface HeroDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Hero hero);

    @Query("DELETE FROM dota_heroes")
    void deleteAll();

    @Query("SELECT * FROM dota_heroes ORDER BY mName")
    List<Hero> getAllHeroes();

    @Query("SELECT COUNT(*) FROM dota_heroes")
    int getTableRowCount();

    // Search items by keywords
    @Query("SELECT * FROM dota_heroes WHERE mName LIKE :keyword OR mCategory LIKE :keyword ORDER BY mName")
    List<Hero> searchHeroByKeywords(String keyword);
}
