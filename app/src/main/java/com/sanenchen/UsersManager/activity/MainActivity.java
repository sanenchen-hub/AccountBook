package com.sanenchen.UsersManager.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.sanenchen.UsersManager.R;
import com.sanenchen.UsersManager.fragment.FavouriteFragment;
import com.sanenchen.UsersManager.fragment.HomeFragment;
import com.sanenchen.UsersManager.fragment.SettingFragment;

/**
 * 主要负责一些碎片的切换
 */
public class MainActivity extends AppCompatActivity {
    private HomeFragment homeFragment;
    private FavouriteFragment favouriteFragment;
    private SettingFragment settingFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*调用方法*/
        setBottomBar();
        setToolBar();
    }

    private void setBottomBar() {
        BottomNavigationBar bottomNavigationBar = findViewById(R.id.bottom_navigation_bar);
        bottomNavigationBar
                .setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
                    @Override
                    public void onTabSelected(int position) {
                        FragmentManager fm = getSupportFragmentManager();
                        //开启事务
                        FragmentTransaction transaction = fm.beginTransaction();
                        switch (position) {
                            case 0:
                                if (homeFragment == null) {
                                    homeFragment = new HomeFragment();
                                }
                                transaction.replace(R.id.frame, homeFragment);
                                break;

                            case 1:
                                if (favouriteFragment == null) {
                                    favouriteFragment = new FavouriteFragment();
                                }
                                transaction.replace(R.id.frame, favouriteFragment);
                                break;

                            case 3:
                                if (settingFragment == null) {
                                    settingFragment = new SettingFragment();
                                }
                                transaction.replace(R.id.frame, settingFragment);
                                break;

                            default:
                                break;
                        }
                        transaction.commit();// 事务提交
                    }

                    @Override
                    public void onTabUnselected(int position) {
                    }

                    @Override
                    public void onTabReselected(int position) {
                    }
                })
                .setMode(BottomNavigationBar.MODE_DEFAULT)
                .setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC)
                .setActiveColor(R.color.colorPrimary) //选中颜色
                .setInActiveColor("#888888") //未选中颜色
                .setBarBackgroundColor(R.color.icons);//导航栏背景色
        bottomNavigationBar
                .addItem(new BottomNavigationItem(R.drawable.ic_baseline_home_24, "首页"))
                .addItem(new BottomNavigationItem(R.drawable.ic_baseline_favorite_24, "收藏"))
                .addItem(new BottomNavigationItem(R.drawable.ic_baseline_format_list_bulleted_24, "分类"))
                .addItem(new BottomNavigationItem(R.drawable.ic_baseline_settings_24, "设置"))
                .setFirstSelectedPosition(0)
                .initialise(); //initialise 一定要放在 所有设置的最后一项
        setDefaultFragment();//设置默认导航栏
    }

    /**
     * 设置默认碎片
     */
    private void setDefaultFragment() {
        FragmentManager fragmentManager = this.getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        homeFragment = new HomeFragment();
        transaction.replace(R.id.frame, homeFragment);
        transaction.commit();
    }

    /**
     * 设置ToolBar
     */
    private void setToolBar() {
        Toolbar toolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
    }
}