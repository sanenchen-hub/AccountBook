package com.sanenchen.UsersManager.fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sanenchen.UsersManager.R;
import com.sanenchen.UsersManager.activity.MainActivity;
import com.sanenchen.UsersManager.activity.NewPasswordInformation;
import com.sanenchen.UsersManager.recyclerViewAdapter.passWordList.PassWordList;
import com.sanenchen.UsersManager.recyclerViewAdapter.passWordList.PassWordListAdapter;
import com.sanenchen.UsersManager.tools.DatabaseHelper;
import com.sanenchen.UsersManager.tools.SetRecyclerView;

import java.util.ArrayList;
import java.util.List;

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
        new SetRecyclerView(getContext(), view, 0);
        listenFAB();
        return view;
    }

    /**
     * 自动刷新
     */
    @Override
    public void onResume() {
        super.onResume();
        new SetRecyclerView(getContext(), viewThis, 0);
    }


    /**
     * 监听FAB，添加新密码
     */
    private void listenFAB() {
        FloatingActionButton FAB_home_add_pass = viewThis.findViewById(R.id.FAB_home_add_pass);
        FAB_home_add_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.add();
                startActivity(new Intent(getActivity(), NewPasswordInformation.class));
            }
        });
    }
}