package com.example.milosevi.rxjavatest.details.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.milosevi.rxjavatest.R;
import com.example.milosevi.rxjavatest.details.model.Review;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by milosevi on 10/12/17.
 */

public class ReviewsAdapter extends RecyclerView.Adapter
                <ReviewsAdapter.ListItemViewHolder> {
    //    private ReviewsAdapter.OnRecyclerItemClickListener mOnItemClickListener;
    private List<Review> items;

    public ReviewsAdapter() {

        this.items = new ArrayList<>();
    }

    @Override
    public ReviewsAdapter.ListItemViewHolder onCreateViewHolder(
            ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.item_reviews,
                        viewGroup,
                        false);
        return new ReviewsAdapter.ListItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(
            ReviewsAdapter.ListItemViewHolder viewHolder, int position) {
        Review model = items.get(position);
        viewHolder.author.setText(model.getAuthor());
        viewHolder.content.setText(model.getContent());
//        viewHolder.trailerLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (mOnItemClickListener != null){
//                    mOnItemClickListener.onItemClick(items.get(position));
//                }
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public final static class ListItemViewHolder
            extends RecyclerView.ViewHolder {
        public TextView author;
        public TextView content;
        public LinearLayout trailerLayout;

        public ListItemViewHolder(View itemView) {
            super(itemView);
//            trailerLayout = itemView.findViewById(R.id.trailer_layout);
            author = (TextView) itemView.findViewById(R.id.review_name);
            content = (TextView) itemView.findViewById(R.id.review_text);
        }
    }

    public void setReviews(List<Review> reviews) {
        items.clear();
        if (reviews != null) {
            items.addAll(reviews);
        }
        notifyDataSetChanged();
    }

//    public void setOnItemClickListener(TrailerAdapter.OnRecyclerItemClickListener listener) {
//        mOnItemClickListener = listener;
//    }

    public interface OnRecyclerItemClickListener {
        void onItemClick(Review review);
    }
}