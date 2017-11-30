package com.example.milosevi.rxjavatest.entrylist.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.milosevi.rxjavatest.ImageLoader;
import com.example.milosevi.rxjavatest.R;
import com.example.milosevi.rxjavatest.model.Movie;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by miodrag.milosevic on 11/30/2017.
 */

public class MovieGridRecyclerViewAdapter extends RecyclerView.Adapter<MovieGridRecyclerViewAdapter.ViewHolder> {

    private final Context mContext;
    private ItemClickListener mClickListener;
    private List<Movie> mMovies = new ArrayList<>();

    public void setData(List<Movie> movies) {
        mMovies.clear();
        mMovies.addAll(movies);
        notifyDataSetChanged();
    }
    public void clearList() {
        mMovies.clear();
    }
    public void addData(List<Movie> movies) {
        mMovies.addAll(movies);
        notifyDataSetChanged();
    }

    // data is passed into the constructor
    public MovieGridRecyclerViewAdapter(Context context) {
        mContext = context.getApplicationContext();
    }

    // inflates the cell layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_grid, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the textview in each cell
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Movie movie = mMovies.get(position);
        ImageLoader.loadImageintoView(mContext, movie.getImageUrl(), holder.iconView);

        holder.versionNameView.setText(movie.getTitle());
        if( position %20 == 0){
            holder.versionNameView.setTextColor(mContext.getResources().getColor(R.color.colorYellow));
        } else {
            holder.versionNameView.setTextColor(mContext.getResources().getColor(R.color.colorAccent));

        }
        holder.linearLayout.setOnClickListener(view -> {
            if (mClickListener != null) {
                mClickListener.onItemClick(mMovies.get(position));
            }
        });
    }

    // total number of cells
    @Override
    public int getItemCount() {
        return mMovies.size();
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iconView;
        TextView versionNameView;
        LinearLayout linearLayout;

        ViewHolder(View itemView) {
            super(itemView);
            linearLayout = itemView.findViewById(R.id.grid_item_layout);
            iconView = itemView.findViewById(R.id.grid_item_image);
            versionNameView = itemView.findViewById(R.id.grid_item_label);

        }
    }

    // convenience method for getting data at click position
    Movie getItem(int id) {
        return mMovies.get(id);
    }

    // allows clicks events to be caught
    public void setOnItemClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(Movie movie);
    }
}
