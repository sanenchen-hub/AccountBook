package com.sanenchen.UsersManager.fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sanenchen.UsersManager.R;
import com.sanenchen.UsersManager.activity.MainActivity;
import com.sanenchen.UsersManager.activity.NewPasswordInformation;
import com.sanenchen.UsersManager.activity.PassWordActivity;
import com.sanenchen.UsersManager.recyclerViewAdapter.passWordList.PassWordList;
import com.sanenchen.UsersManager.recyclerViewAdapter.passWordList.PassWordListAdapter;
import com.sanenchen.UsersManager.tools.DatabaseHelper;
import com.sanenchen.UsersManager.tools.SetRecyclerView;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * 主页碎片
 *
 * @author sanenchen
 * @version v1.0
 */
public class HomeFragment extends Fragment {
    public static View viewThis;//全局View

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        viewThis = view;
        /*调用方法*/
        new SetRecyclerView(getContext(), view);
        listenFAB();
        return view;
    }

    /**
     * 自动刷新
     */
    @Override
    public void onResume() {
        super.onResume();
        new SetRecyclerView(getContext(), viewThis);
    }

    /**
     * 获取数据并将数据放入RecyclerView
     */
    public void setRecyclerView() {
        /*从数据库中获取数据*/
        DatabaseHelper databaseHelper = new DatabaseHelper(getActivity(), "main.db", null, 1);
        SQLiteDatabase database = databaseHelper.getWritableDatabase();
        Cursor cursor = database.query("PassWordBook", null, null, null,
                null, null, null);

        if (cursor.moveToFirst()) {//如果有数据
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
            PassWordListAdapter adapter = new PassWordListAdapter(passWordListList, getActivity());//初始化Adapter
            LinearLayoutManager manager = new LinearLayoutManager(getActivity());
            manager.setOrientation(LinearLayoutManager.VERTICAL);
            RecyclerView recyclerView = viewThis.findViewById(R.id.recycler_view_all_pass);
            recyclerView.setLayoutManager(manager);
            recyclerView.setAdapter(adapter);//设置Adapter
        } else {//数据库里没有数据的话
            FrameLayout text_view_home_tno_thing = viewThis.findViewById(R.id.text_view_home_tno_thing);
            text_view_home_tno_thing.setVisibility(View.VISIBLE);
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