package com.example.user.myapplication.ui.search;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.user.myapplication.R;
import com.example.user.myapplication.models.SearchResult;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder>{

    private List<SearchResult> results;

    public SearchAdapter(List<SearchResult> results){
        this.results = results;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_search_item,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
         final SearchResult r = get(position);
         holder.itemNameTextView.setText(r.getMeta().get("2. Symbol"));
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.item_name)
        TextView itemNameTextView;

        public ViewHolder(View view){
            super(view);
            ButterKnife.bind(this,view);
        }

    }


    public SearchResult get(int position){
        return results.get(position);
    }

    public void updateList(List<SearchResult> results){
        this.results = results;
    }

}
