package com.example.milosevi.rxjavatest;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;

/**
 * Created by miodrag.milosevic on 11/30/2017.
 */

public abstract class EndlessScrollListener extends RecyclerView.OnScrollListener {

    // The minimum amount of items to have below your current scroll position
    // before loading more.
    private int mVisibleThreshold = 5;
    // The current offset index of data you have loaded
    private int currentPage = 0;
    // The total number of items in the dataset after the last load
    private int previousTotalItemCount = 0;
    // True if we are still waiting for the last set of data to load.
    private boolean loading = true;
    // Sets the starting page index
    private int startingPageIndex = 0;

    RecyclerView.LayoutManager mLayoutManager;


    public EndlessScrollListener(RecyclerView.LayoutManager layoutManager) {
        mLayoutManager = layoutManager;
    }

    public EndlessScrollListener(RecyclerView.LayoutManager layoutManager, int visibleThreshold) {
        mVisibleThreshold = visibleThreshold;
        mLayoutManager = layoutManager;
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        if (dy > 0) //check for scroll down
        {
            int visibleItemCount = mLayoutManager.getChildCount();
            int totalItemCount = mLayoutManager.getItemCount();
            int firstVisibleItemPosition = 0;
            if (mLayoutManager instanceof StaggeredGridLayoutManager) {
                int[] lastVisibleItemPositions = ((StaggeredGridLayoutManager) mLayoutManager).findFirstVisibleItemPositions(null);
                // get maximum element within the list
                firstVisibleItemPosition = getFirstVisiblePosition(lastVisibleItemPositions);
            } else if (mLayoutManager instanceof GridLayoutManager) {
                firstVisibleItemPosition = ((GridLayoutManager) mLayoutManager).findFirstVisibleItemPosition();
            } else if (mLayoutManager instanceof LinearLayoutManager) {
                firstVisibleItemPosition = ((LinearLayoutManager) mLayoutManager).findFirstVisibleItemPosition();
            }
            if (loading) {
                if (totalItemCount > previousTotalItemCount) {
                    loading = false;
                    previousTotalItemCount = totalItemCount;
                }
            }
            if (!loading && (totalItemCount - visibleItemCount)
                    <= (firstVisibleItemPosition + mVisibleThreshold)) {
                // End has been reached
                currentPage++;
                loading = onLoadMore(currentPage, totalItemCount, recyclerView);
                Log.i("Miki", "end called" + currentPage);
            }
        }
    }

    private int getFirstVisiblePosition(int[] lastVisibleItemPositions) {
//        TO BE DEFINED
        return 0;
    }

    // Call this method whenever performing new searches
    public void resetState() {
        currentPage = 0;
        previousTotalItemCount = 0;
        loading = true;
    }

    // Defines the process for actually loading more data based on page
    public abstract boolean onLoadMore(int page, int totalItemsCount, RecyclerView view);
}
