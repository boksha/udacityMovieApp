package com.example.milosevi.rxjavatest.details.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.milosevi.rxjavatest.R;
import com.example.milosevi.rxjavatest.details.model.Trailer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by milosevi on 10/11/17.
 */

public class TrailerAdapter  extends
            RecyclerView.Adapter
                    <TrailerAdapter.ListItemViewHolder> {
    private OnRecyclerItemClickListener mOnItemClickListener;
        private List<Trailer> items;

    public TrailerAdapter() {

            this.items = new ArrayList<>();
        }

        @Override
        public ListItemViewHolder onCreateViewHolder(
                ViewGroup viewGroup, int viewType) {
            View itemView = LayoutInflater.
                    from(viewGroup.getContext()).
                    inflate(R.layout.item_trailers_rv,
                            viewGroup,
                            false);
            return new ListItemViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(
                ListItemViewHolder viewHolder, int position) {
            Trailer model = items.get(position);
            viewHolder.trailerName.setText(model.getName());
            viewHolder.trailerLayout.setOnClickListener(view -> {
                    if (mOnItemClickListener != null){
                        mOnItemClickListener.onItemClick(items.get(position));
                    }
                });
        }

        @Override
        public int getItemCount() {
            return items.size();
        }

        public final static class ListItemViewHolder
                extends RecyclerView.ViewHolder {
            public TextView trailerName;
            public TextView playBtn;
            public LinearLayout trailerLayout;

            public ListItemViewHolder(View itemView) {
                super(itemView);
                trailerLayout =  itemView.findViewById(R.id.trailer_layout);
                trailerName = (TextView) itemView.findViewById(R.id.trailer_name);
                playBtn = (TextView) itemView.findViewById(R.id.play_trailer);
            }
        }

        public void setTrailers(List<Trailer> trailers){
            items.clear();
            if (trailers != null) {
                items.addAll(trailers);
            }
            notifyDataSetChanged();
        }

        public void setOnItemClickListener(OnRecyclerItemClickListener listener){
            mOnItemClickListener = listener;
        }

    public interface OnRecyclerItemClickListener {
        void onItemClick(Trailer trailer);
    }
}
