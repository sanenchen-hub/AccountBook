package com.sanenchen.UsersManager.recyclerViewAdapter.passWordRound;

import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.sanenchen.UsersManager.R;

import java.util.List;

public class PassWordRoundAdapter extends RecyclerView.Adapter<PassWordRoundAdapter.ViewHolder>{
    private List<PassWordRound> passWordRoundLists;
    static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View view) {
            super(view);
        }
    }
    public PassWordRoundAdapter(List<PassWordRound> passWordRoundLists) {
        this.passWordRoundLists = passWordRoundLists;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_pass_word_round, parent, false);
        final ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    }

    @Override
    public int getItemCount() {
        return passWordRoundLists.size();
    }
}
