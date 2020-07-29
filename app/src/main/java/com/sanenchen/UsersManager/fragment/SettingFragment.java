package com.sanenchen.UsersManager.fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.sanenchen.UsersManager.R;
import com.sanenchen.UsersManager.activity.AboutActivity;

/**
 * 碎片-设置界面
 * @author sanenchen
 * @version v1.0
 */
public class SettingFragment extends Fragment {
    View viewThis;//全局View
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        viewThis = view;
        ListenView();
        return view;
    }

    /**
     * 监听各个模块
     */
    private void ListenView() {
        /*监听“关于”按钮*/
        LinearLayout item_card_view_about = viewThis.findViewById(R.id.item_card_view_about);
        item_card_view_about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*启动界面*/
                startActivity(new Intent(getActivity(), AboutActivity.class));//启动关于Activity
            }
        });
    }
}