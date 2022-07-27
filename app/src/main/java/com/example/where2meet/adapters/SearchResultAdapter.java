package com.example.where2meet.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.where2meet.activities.DetailActivity;
import com.example.where2meet.SearchResult;
import com.example.where2meet.databinding.ItemSearchresultBinding;
import com.parse.ParseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.parceler.Parcels;

import java.util.List;

public class SearchResultAdapter extends RecyclerView.Adapter<SearchResultAdapter.ViewHolder> {
    private Context context;
    private List<SearchResult> searchResults;

    public SearchResultAdapter(Context context, List<SearchResult> searchResults) {
        this.context = context;
        this.searchResults = searchResults;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemSearchresultBinding itemSearchresultBinding = ItemSearchresultBinding.inflate(layoutInflater, parent, false);
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

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ItemSearchresultBinding itemSearchresultBinding;
        public ViewHolder(@NonNull ItemSearchresultBinding itemSearchresultBinding) {
            super(itemSearchresultBinding.getRoot());
            this.itemSearchresultBinding = itemSearchresultBinding;
            itemView.setOnClickListener(this);
        }
        public void bind(SearchResult searchResult) {
            itemSearchresultBinding.ivName.setText(searchResult.getName());
            itemSearchresultBinding.tvAddress.setText(searchResult.getAddress());
            itemSearchresultBinding.tvDistance.setText(searchResult.getDistance());
            Glide.with(context).load(searchResult.getIcon()).override(100, 150).into(itemSearchresultBinding.ivIcon);
            checkVisited(searchResult);
        }
        private void checkVisited(SearchResult searchResult) {
            ParseUser currentUser = ParseUser.getCurrentUser();
            JSONArray visited = currentUser.getJSONArray("Visited");
        if (visited != null) {
            for (int i = 0; i < visited.length(); i++) {
                try {
                    if (visited.get(i).toString().equals(searchResult.getFormattedAddress())) {
                        itemSearchresultBinding.llVisitedRegister.setVisibility(View.VISIBLE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        }
        @Override
        public void onClick(View v) {
            Toast.makeText(context, "this clicks", Toast.LENGTH_SHORT).show();
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                SearchResult searchResult = searchResults.get(position);
                Intent i = new Intent(context, DetailActivity.class);
                i.putExtra(SearchResult.class.getSimpleName(), Parcels.wrap(searchResult));
                context.startActivity(i);
            }
        }
    }

    public void clear() {
        searchResults.clear();
        notifyDataSetChanged();
    }
}
