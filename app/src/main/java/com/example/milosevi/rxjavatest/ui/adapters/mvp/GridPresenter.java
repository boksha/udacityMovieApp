package com.example.milosevi.rxjavatest.ui.adapters.mvp;

/**
 * Created by milosevi on 10/10/17.
 */

public class GridPresenter implements GridContract.Presenter {

    GridContract.Repository mRepository;

    public GridPresenter(GridContract.Repository repository){
        mRepository = repository;
    }

    @Override
    public void onItemClicked(int position) {

    }

    @Override
    public void onMenuItemClicked(@MenuMode int menuMode) {
        if (menuMode == MENU_ITEM_MOST_POPULAR){
            mRepository.getMostPopular();
        } else if (menuMode == MENU_ITEM_TOP_RATED){
            mRepository.getTopRated();
        }
    }

    @Override
    public void onSearchButtonClicked(String searchWord) {
            mRepository.getMoviesWithWord(searchWord);
    }
}
