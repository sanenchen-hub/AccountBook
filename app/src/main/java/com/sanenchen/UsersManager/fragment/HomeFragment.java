package com.sanenchen.UsersManager.fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sanenchen.UsersManager.R;
import com.sanenchen.UsersManager.activity.NewPasswordInformation;
import com.sanenchen.UsersManager.recyclerViewAdapter.passWordList.PassWordList;
import com.sanenchen.UsersManager.recyclerViewAdapter.passWordList.PassWordListAdapter;
import com.sanenchen.UsersManager.tools.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * 主页碎片
 *
 * @author sanenchen
 * @version v1.0
 */
public class HomeFragment extends Fragment {
    View viewThis;//全局View

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        viewThis = view;
        /*调用方法*/
        setRecyclerView();
        listenFAB();
        return view;
    }

    /**
     * 获取数据并将数据放入RecyclerView
     */
    private void setRecyclerView() {
        /*从数据库中获取数据*/
        DatabaseHelper databaseHelper = new DatabaseHelper(getActivity(), "main.db", null, 1);
        SQLiteDatabase database = databaseHelper.getWritableDatabase();
        Cursor cursor = database.query("PassWordBook", null, null, null,
                null, null, null);

        if (cursor.moveToFirst()) {//如果有数据
            List<PassWordList> passWordListList;
            do {
                /*将获取的数据放入List*/
                passWordListList = new ArrayList<>();
                int id = cursor.getInt(cursor.getColumnIndex("id"));//查询ID
                String title = cursor.getString(cursor.getColumnIndex("title"));//查询标题
                String user = cursor.getString(cursor.getColumnIndex("user"));//查询用户名
                String password = cursor.getString(cursor.getColumnIndex("password"));//查询密码
                String other = cursor.getString(cursor.getColumnIndex("other"));//查询备注
                PassWordList passWordList = new PassWordList(id, title, user,
                        password, other);
                passWordListList.add(passWordList);
            } while (cursor.moveToNext());
            /*初始化RecyclerView并且正式放入Adapter*/
            PassWordListAdapter adapter = new PassWordListAdapter(passWordListList);//初始化Adapter
            StaggeredGridLayoutManager layoutManager =
                    new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);//RecyclerView瀑布流显示
            RecyclerView recyclerView = viewThis.findViewById(R.id.recycler_view_all_pass);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapter);//设置Adapter
        } else {//数据库里没有数据的话
            ImageView text_view_home_tno_things = viewThis.findViewById(R.id.text_view_home_tno_things);
            text_view_home_tno_things.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 监听FAB，添加新密码
     */
    private void listenFAB() {
        FloatingActionButton FAB_home_add_pass = viewThis.findViewById(R.id.FAB_home_add_pass);
        FAB_home_add_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), NewPasswordInformation.class));
            }
        });
    }

}