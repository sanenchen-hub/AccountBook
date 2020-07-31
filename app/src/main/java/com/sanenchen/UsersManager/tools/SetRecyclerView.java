package com.sanenchen.UsersManager.tools;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.FrameLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sanenchen.UsersManager.R;
import com.sanenchen.UsersManager.recyclerViewAdapter.passWordList.PassWordList;
import com.sanenchen.UsersManager.recyclerViewAdapter.passWordList.PassWordListAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 放入数据到RecyclerView
 */
public class SetRecyclerView {
    public SetRecyclerView(Context context, View view) {
        /*从数据库中获取数据*/
        DatabaseHelper databaseHelper = new DatabaseHelper(context, "main.db", null, 1);
        SQLiteDatabase database = databaseHelper.getWritableDatabase();
        Cursor cursor = database.query("PassWordBook", null, null, null,
                null, null, null);

        if (cursor.moveToFirst()) {//如果有数据
            FrameLayout text_view_home_tno_thing = view.findViewById(R.id.text_view_home_tno_thing);
            text_view_home_tno_thing.setVisibility(View.GONE);
            List<PassWordList> passWordListList = new ArrayList<>();
            do {
                /*将获取的数据放入List*/
                int id = cursor.getInt(cursor.getColumnIndex("id"));//查询ID
                String title = cursor.getString(cursor.getColumnIndex("title"));//查询标题
                String user = cursor.getString(cursor.getColumnIndex("user"));//查询用户名
                String password = cursor.getString(cursor.getColumnIndex("password"));//查询密码
                String remark = cursor.getString(cursor.getColumnIndex("remark"));//查询备注
                String url = cursor.getString(cursor.getColumnIndex("url"));//查询备注
                String createTime = cursor.getString(cursor.getColumnIndex("createTime"));//查询备注
                PassWordList passWordList = new PassWordList(id, title, user,
                        password, remark, url, createTime);
                passWordListList.add(passWordList);
            } while (cursor.moveToNext());
            /*初始化RecyclerView并且正式放入Adapter*/
            PassWordListAdapter adapter = new PassWordListAdapter(passWordListList, context);//初始化Adapter
            LinearLayoutManager manager = new LinearLayoutManager(context);
            manager.setOrientation(LinearLayoutManager.VERTICAL);
            RecyclerView recyclerView = view.findViewById(R.id.recycler_view_all_pass);
            recyclerView.setLayoutManager(manager);
            recyclerView.setAdapter(adapter);//设置Adapter
        } else {//数据库里没有数据的话
            FrameLayout text_view_home_tno_thing = view.findViewById(R.id.text_view_home_tno_thing);
            text_view_home_tno_thing.setVisibility(View.VISIBLE);
            RecyclerView recyclerView = view.findViewById(R.id.recycler_view_all_pass);
            recyclerView.setAdapter(null);//设置Adapter
        }
    }
}
