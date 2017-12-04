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


    private static final int START_PAGE =1;
    // The minimum amount of items to have below your current scroll position
    // before loading more.
    private int mVisibleThreshold = 5;
    // The current offset index of data you have loaded
    private int mCurrentPage = START_PAGE;

    private RecyclerView.LayoutManager mLayoutManager;


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
                int[] firstVisibleItemPositions = ((StaggeredGridLayoutManager) mLayoutManager).findFirstVisibleItemPositions(null);
                // get maximum element within the list
                firstVisibleItemPosition = getFirstVisiblePosition(firstVisibleItemPositions);
            } else if (mLayoutManager instanceof GridLayoutManager) {
                firstVisibleItemPosition = ((GridLayoutManager) mLayoutManager).findFirstVisibleItemPosition();
            } else if (mLayoutManager instanceof LinearLayoutManager) {
                firstVisibleItemPosition = ((LinearLayoutManager) mLayoutManager).findFirstVisibleItemPosition();
            }

            if (!isLoading() && (totalItemCount - visibleItemCount)
                    <= (firstVisibleItemPosition + mVisibleThreshold)) {
                // End has been reached
                mCurrentPage++;
                onLoadMore(mCurrentPage, totalItemCount, recyclerView);
                Log.i("Miki", "end called " + mCurrentPage);
            }
        }
    }

    private int getFirstVisiblePosition(int[] lastVisibleItemPositions) {
//        TO BE DEFINED
        return 0;
    }

    // Call this method whenever performing new searches
    public void resetState() {
        mCurrentPage = START_PAGE;
    }

    // Defines the process for actually loading more data based on page
    public abstract void onLoadMore(int page, int totalItemsCount, RecyclerView view);

    public abstract boolean isLoading();//this might be calculated here inside, see whats better

    public abstract boolean isEndOfTheList();//TO DO handle this as well, probobly WebApi has indication!
}
