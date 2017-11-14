package com.example.milosevi.rxjavatest.entrylist.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.milosevi.rxjavatest.ImageLoader;
import com.example.milosevi.rxjavatest.R;
import com.example.milosevi.rxjavatest.model.Movie;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by milosevi on 10/9/17.
 */

public class MovieGridAdapter extends BaseAdapter {

    private  Context mContext;
    private List<Movie> mMovies = new ArrayList<>();

    public void setData(List<Movie > movies){
        mMovies.clear();
        mMovies.addAll(movies);
        notifyDataSetChanged();
    }

    // 1
    public MovieGridAdapter(Context context) {
        this.mContext = context.getApplicationContext();
    }

    // 2
    @Override
    public int getCount() {
        return mMovies.size();
    }

    // 3
    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        // Gets the AndroidFlavor object from the ArrayAdapter at the appropriate position
        Movie movie = mMovies.get(i);

        // Adapters recycle views to AdapterViews.
        // If this is a new View object we're getting, then inflate the layout.
        // If not, this view already has the layout inflated from a previous call to getView,
        // and we modify the View widgets as usual.
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.item_grid, viewGroup, false);
        }

        ImageView iconView = (ImageView) convertView.findViewById(R.id.grid_item_image);
        ImageLoader.loadImageintoView(mContext,movie.getImageUrl(),iconView);

        TextView versionNameView = (TextView) convertView.findViewById(R.id.grid_item_label);
        versionNameView.setText(movie.getTitle() );

        return convertView;
    }

    // 4
    @Override
    public Movie getItem(int position) {
        return mMovies.get(position);
    }

}
