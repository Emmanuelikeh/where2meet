package com.example.where2meet;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.where2meet.databinding.ItemReviewBinding;

import java.util.List;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ViewHolder> {
    private Context context;

    private List<Reviews> reviews;


    public ReviewsAdapter(Context context, List<Reviews> reviews){
        this.context = context;
        this.reviews = reviews;
    }


    @NonNull
    @Override
    public ReviewsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemReviewBinding  itemReviewBinding = ItemReviewBinding.inflate(layoutInflater,parent, false);
        return new ViewHolder(itemReviewBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewsAdapter.ViewHolder holder, int position) {
        Reviews review = reviews.get(position);
        holder.bind(review);

    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder{

        private ItemReviewBinding itemReviewBinding;
        public ViewHolder(@NonNull ItemReviewBinding itemReviewBinding) {
            super(itemReviewBinding.getRoot());
            this.itemReviewBinding = itemReviewBinding;

        }


        public void bind(Reviews review) {
            itemReviewBinding.etCommentary.setText(review.getCommentary());
            itemReviewBinding.tvCreatedAt.setText(review.getCreatedAt());
        }
    }
}
