package com.android.jerry.dotapedia;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;

import java.util.List;

public class DotapediaRepository {

    private ItemDao mItemDao;
    private static MutableLiveData<List<Item>> mSearchItems = new MutableLiveData<>();

    private HeroDao mHeroDao;
    private static MutableLiveData<List<Hero>> mSearchHeroes = new MutableLiveData<>();

    DotapediaRepository(Application application) {
        DotapediaRoomDatabase db = DotapediaRoomDatabase.getDatabase(application);
        // get DAO for items and heroes
        mItemDao = db.itemDao();
        mHeroDao = db.heroDao();
        // Initial search for show-all
        searchItems("");
        searchHeroes("");
    }

    public MutableLiveData<List<Item>> getSearchItems() {
        return mSearchItems;
    }

    public MutableLiveData<List<Hero>> getSearchHeroes() {
        return mSearchHeroes;
    }

    // Pre-process keywords for Dao by adding '%'
    void searchItems(String keyWord) {
        String processedKey = "%" + keyWord + "%";
        new searchItemAsyncTask(mItemDao).execute(processedKey);
    }

    void searchHeroes(String keyWord) {
        String processedKey = "%" + keyWord + "%";
        new searchHeroAsyncTask(mHeroDao).execute(processedKey);
    }

    // AsyncTask for searching, no need to do things on post execute so arg[2] is Void in AsyncTask
    private static class searchItemAsyncTask extends AsyncTask<String, Void, Void> {
        private ItemDao mAsyncTaskItemDao;

        searchItemAsyncTask(ItemDao dao) {
            super();
            mAsyncTaskItemDao = dao;
        }

        @Override
        protected Void doInBackground(String... strings) {
            mSearchItems.postValue(mAsyncTaskItemDao.searchItemByKeywords(strings[0]));
            return null;
        }
    }

    private static class searchHeroAsyncTask extends AsyncTask<String, Void, Void> {
        private HeroDao mAsyncTaskHeroDao;

        searchHeroAsyncTask(HeroDao dao) {
            super();
            mAsyncTaskHeroDao = dao;
        }

        @Override
        protected Void doInBackground(String... strings) {
            mSearchHeroes.postValue(mAsyncTaskHeroDao.searchHeroByKeywords(strings[0]));
            return null;
        }
    }

    // TODO in development.
    // insert methods for new item/hero activity
    public void insert(Item item) {
        new insertAsyncTask(mItemDao).execute(item);
    }

    public void insertHero(Hero hero) {
        new insertAsyncTaskForHero(mHeroDao).execute(hero);
    }

    // AsyncTask for item
    private static class insertAsyncTask extends AsyncTask<Item, Void, Void> {
        private ItemDao mAsyncTaskItemDao;

        insertAsyncTask(ItemDao dao) {
            mAsyncTaskItemDao = dao;
        }

        @Override
        protected Void doInBackground(final Item... params) {
            mAsyncTaskItemDao.insert(params[0]);
            return null;
        }
    }

    // AsyncTask for hero
    private static class insertAsyncTaskForHero extends AsyncTask<Hero, Void, Void> {
        private HeroDao mAsyncTaskHeroDao;

        insertAsyncTaskForHero(HeroDao dao) {
            mAsyncTaskHeroDao = dao;
        }

        @Override
        protected Void doInBackground(final Hero... params) {
            mAsyncTaskHeroDao.insert(params[0]);
            return null;
        }
    }
}
