package com.sanenchen.UsersManager.recyclerViewAdapter.passWordList;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sanenchen.UsersManager.R;
import java.util.List;

/**
 * RecyclerView recycler_view_all_pass的适配器
 * @author sanenchen
 * @version v1.0
 */
public class PassWordListAdapter extends RecyclerView.Adapter<PassWordListAdapter.ViewHolder>{
    private List<PassWordList> passWordListLists;
    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView item_pass_word_list_title, item_pass_word_list_user, item_pass_word_list_password;
        public ViewHolder(View view) {
            super(view);
            item_pass_word_list_title = view.findViewById(R.id.item_pass_word_list_title);
            item_pass_word_list_user = view.findViewById(R.id.item_pass_word_list_user);
            item_pass_word_list_password = view.findViewById(R.id.item_pass_word_list_password);
        }
    }
    public PassWordListAdapter(List<PassWordList> passWordListLists) {
        this.passWordListLists = passWordListLists;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_pass_word_list, parent, false);
        final ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PassWordList passWordList = passWordListLists.get(position);
        holder.item_pass_word_list_title.setText(passWordList.getTitle());
        holder.item_pass_word_list_user.setText(passWordList.getUser());
        holder.item_pass_word_list_password.setText(passWordList.getPassword());
    }

    @Override
    public int getItemCount() {
        return passWordListLists.size();
    }
}
