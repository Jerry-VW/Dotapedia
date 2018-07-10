package com.android.jerry.dotapedia;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.List;

public class DotapediaViewModel extends AndroidViewModel {

    private DotapediaRepository mRepository;

    public DotapediaViewModel(@NonNull Application application) {
        super(application);
        mRepository = new DotapediaRepository(application);
    }

    public MutableLiveData<List<Item>> getLiveItemFeed() {
        return mRepository.getSearchItems();
    }

    public MutableLiveData<List<Hero>> getLiveHeroFeed() {
        return mRepository.getSearchHeroes();
    }

    // Pass search string to data repository
    void searchThis(String userString) {
        mRepository.searchItems(userString);
        mRepository.searchHeroes(userString);
        Log.i("search>ViewModel:", userString);
    }

    // TODO in development
    public void insert(Item item) {
        mRepository.insert(item);
    }

    public void intertHero(Hero hero) {
        mRepository.insertHero(hero);
    }
}
