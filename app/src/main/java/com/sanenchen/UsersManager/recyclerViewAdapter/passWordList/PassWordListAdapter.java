package com.sanenchen.UsersManager.recyclerViewAdapter.passWordList;

import android.app.AlarmManager;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.sanenchen.UsersManager.R;
import com.sanenchen.UsersManager.activity.MainActivity;
import com.sanenchen.UsersManager.fragment.FavouriteFragment;
import com.sanenchen.UsersManager.fragment.HomeFragment;
import com.sanenchen.UsersManager.tools.DatabaseHelper;
import com.sanenchen.UsersManager.tools.GetSettingThings;
import com.sanenchen.UsersManager.tools.SHA224;
import com.sanenchen.UsersManager.tools.SetRecyclerView;

import java.util.List;

import static android.content.Context.ALARM_SERVICE;

/**
 * RecyclerView recycler_view_xxx_pass的适配器
 *
 * @author sanenchen
 * @version v1.0
 */
public class PassWordListAdapter extends RecyclerView.Adapter<PassWordListAdapter.ViewHolder> {
    private List<PassWordList> passWordListLists;
    private Context mContext;
    private int mMessageWhat;

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView item_pass_word_list_title, item_pass_word_list_user, item_pass_word_list_password,
                item_pass_word_list_remark, item_pass_word_list_url, item_pass_word_list_create_date;
        FrameLayout item_pass_word_list_url_layout, item_pass_word_list_remark_layout;
        CardView card_view_item_pass;

        public ViewHolder(View view) {
            super(view);
            item_pass_word_list_title = view.findViewById(R.id.item_pass_word_list_title);
            item_pass_word_list_user = view.findViewById(R.id.item_pass_word_list_user);
            item_pass_word_list_password = view.findViewById(R.id.item_pass_word_list_password);
            item_pass_word_list_remark = view.findViewById(R.id.item_pass_word_list_remark);
            item_pass_word_list_url = view.findViewById(R.id.item_pass_word_list_url);
            item_pass_word_list_create_date = view.findViewById(R.id.item_pass_word_list_create_date);
            item_pass_word_list_url_layout = view.findViewById(R.id.item_pass_word_list_url_layout);
            item_pass_word_list_remark_layout = view.findViewById(R.id.item_pass_word_list_remark_layout);
            card_view_item_pass = view.findViewById(R.id.card_view_item_pass);
        }
    }

    public PassWordListAdapter(List<PassWordList> passWordListLists, Context context, int MessageWhat) {
        this.passWordListLists = passWordListLists;
        mContext = context;
        mMessageWhat = MessageWhat;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_pass_word_list, parent, false);
        final ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.card_view_item_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                final AlertDialog dialog = builder.create();
                View dialogView = View.inflate(mContext, R.layout.alert_dialog_adapter, null);
                dialog.setView(dialogView);
                dialog.show();
                /*监听按钮*/
                Button copy_user = dialogView.findViewById(R.id.copy_user);
                Button copy_password = dialogView.findViewById(R.id.copy_password);
                Button copy_del = dialogView.findViewById(R.id.copy_del);
                Button move_favourite = dialogView.findViewById(R.id.move_favourite);
                final PassWordList passWordList = passWordListLists.get(viewHolder.getAdapterPosition());
                copy_user.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //获取剪贴板管理器：
                        ClipboardManager cm = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
                        ClipData mClipData = ClipData.newPlainText("user", passWordList.getUser());
                        cm.setPrimaryClip(mClipData);
                        Toast.makeText(mContext, "已复制", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
                copy_password.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //获取剪贴板管理器：
                        ClipboardManager cm = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
                        ClipData mClipData = ClipData.newPlainText("password", passWordList.getPassword());
                        cm.setPrimaryClip(mClipData);
                        Toast.makeText(mContext, "已复制", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
                copy_del.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //获取剪贴板管理器：
                        DatabaseHelper databaseHelper = new DatabaseHelper(mContext, "main.db", null, 1);
                        SQLiteDatabase database = databaseHelper.getWritableDatabase();
                        database.delete("PassWordBook", "id = ?", new String[]{passWordList.getId() + ""});
                        new SetRecyclerView(mContext, HomeFragment.viewThis, mMessageWhat);
                        Toast.makeText(mContext, "已删除", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
                if (passWordList.getCheckLove() == 0) {
                    move_favourite.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            DatabaseHelper databaseHelper = new DatabaseHelper(mContext, "main.db", null, 1);
                            SQLiteDatabase database = databaseHelper.getWritableDatabase();
                            ContentValues values = new ContentValues();
                            values.put("checkLove", 1);
                            database.update("PassWordBook", values, "id = ?", new String[]{passWordList.getId() + ""});
                            new SetRecyclerView(mContext, HomeFragment.viewThis, mMessageWhat);
                            Toast.makeText(mContext, "已添加", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                    });
                } else {
                    move_favourite.setText("移出收藏夹");
                    move_favourite.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            DatabaseHelper databaseHelper = new DatabaseHelper(mContext, "main.db", null, 1);
                            SQLiteDatabase database = databaseHelper.getWritableDatabase();
                            ContentValues values = new ContentValues();
                            values.put("checkLove", 0);
                            database.update("PassWordBook", values, "id = ?", new String[]{passWordList.getId() + ""});
                            if (mMessageWhat == 0) {
                                new SetRecyclerView(mContext, HomeFragment.viewThis, mMessageWhat);
                            } else {
                                new SetRecyclerView(mContext, FavouriteFragment.viewThis, mMessageWhat);
                            }

                            Toast.makeText(mContext, "已移出", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                    });
                }
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PassWordList passWordList = passWordListLists.get(position);
        String getPassword = "";
        SharedPreferences preferences = mContext.getSharedPreferences(new SHA224().SHA224("data"), Context.MODE_PRIVATE);
        if (!new GetSettingThings(mContext).checkShowPassword()) {
            int totalPassLength;
            totalPassLength = passWordList.getPassword().length();
            for (int i = 0; i < totalPassLength; i++) {
                if (i == 0) {
                    getPassword = (String) passWordList.getPassword().subSequence(0, 1);
                } else if (i == totalPassLength - 1) {
                    getPassword = getPassword + passWordList.getPassword().substring(totalPassLength - 1);
                } else {
                    getPassword = getPassword + "*";
                }

            }
        } else {
            getPassword = passWordList.getPassword();
        }

        holder.item_pass_word_list_title.setText(passWordList.getTitle());
        holder.item_pass_word_list_user.setText(passWordList.getUser());
        holder.item_pass_word_list_password.setText(getPassword);
        holder.item_pass_word_list_create_date.setText(passWordList.getCreateTime());
        if (!passWordList.getRemark().equals("")) {
            holder.item_pass_word_list_remark_layout.setVisibility(View.VISIBLE);
            holder.item_pass_word_list_remark.setText(passWordList.getRemark());
        }
        if (!passWordList.getUrl().equals("")) {
            holder.item_pass_word_list_url_layout.setVisibility(View.VISIBLE);
            holder.item_pass_word_list_url.setText(passWordList.getUrl());
        }
    }

    @Override
    public int getItemCount() {
        return passWordListLists.size();
    }
}
