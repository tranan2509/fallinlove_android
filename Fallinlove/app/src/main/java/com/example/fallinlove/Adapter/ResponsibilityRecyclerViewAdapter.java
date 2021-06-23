package com.example.fallinlove.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.example.fallinlove.R;

import java.util.List;

public class ResponsibilityRecyclerViewAdapter extends RecyclerView.Adapter<ResponsibilityRecyclerViewAdapter.ViewHolder>{

    List<String> responsibilities;

    public ResponsibilityRecyclerViewAdapter(List<String> responsibilities){

        this.responsibilities = responsibilities;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);

        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View cartView = inflater.inflate(R.layout.responsibility_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(cartView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return responsibilities.size();
    }

    private void removeItem(int position) {
        responsibilities.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, responsibilities.size());
    }
}
