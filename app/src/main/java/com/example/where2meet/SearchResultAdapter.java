package com.example.where2meet;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.where2meet.databinding.ItemSearchresultBinding;

import java.util.List;

public class SearchResultAdapter extends RecyclerView.Adapter<SearchResultAdapter.ViewHolder> {
    private Context context;

    private List<SearchResult> searchResults;



    public SearchResultAdapter(Context context, List<SearchResult> searchResults){
        this.context = context;
        this.searchResults = searchResults;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemSearchresultBinding itemSearchresultBinding = ItemSearchresultBinding.inflate(layoutInflater,parent,false);
        return new ViewHolder(itemSearchresultBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SearchResult searchResult = searchResults.get(position);
        holder.bind(searchResult);

    }

    @Override
    public int getItemCount() {
        return searchResults.size();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder {

        private ItemSearchresultBinding itemSearchresultBinding;

        public ViewHolder(@NonNull ItemSearchresultBinding itemSearchresultBinding) {
            super(itemSearchresultBinding.getRoot());
            this.itemSearchresultBinding = itemSearchresultBinding;
        }

        public void bind(SearchResult searchResult) {
            itemSearchresultBinding.ivName.setText(searchResult.getName());
            itemSearchresultBinding.tvAddress.setText(searchResult.getAddress());
            itemSearchresultBinding.tvDistance.setText(searchResult.getDistance());
            Glide.with(context).load(searchResult.getIcon()).override(100,200).centerCrop().into(itemSearchresultBinding.ivIcon);

        }
    }
    public void clear() {
        searchResults.clear();
        notifyDataSetChanged();
    }
}
