package com.sanenchen.UsersManager.fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.Toast;

import com.sanenchen.UsersManager.R;
import com.sanenchen.UsersManager.activity.AboutActivity;
import com.sanenchen.UsersManager.activity.CreatePassActivity;
import com.sanenchen.UsersManager.tools.SHA224;

import static android.content.Context.FINGERPRINT_SERVICE;
import static android.content.Context.KEYGUARD_SERVICE;
import static android.content.Context.MODE_PRIVATE;

/**
 * 碎片-设置界面
 *
 * @author sanenchen
 * @version v1.0
 */
public class SettingFragment extends Fragment {
    View viewThis;//全局View

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        viewThis = view;
        intView();
        ListenView();
        return view;
    }

    /**
     * 初始化View
     */
    private void intView() {
        SharedPreferences preferences = getActivity().getSharedPreferences(new SHA224().SHA224("data"),MODE_PRIVATE);
        /*指纹Switch并监听*/
        Switch switch_finger_print = viewThis.findViewById(R.id.switch_finger_print);
        if (preferences.getBoolean(new SHA224().SHA224("check_finger_print"), false)) {
            switch_finger_print.setChecked(true);
        }
        switch_finger_print.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences.Editor editor = getActivity().getSharedPreferences(new SHA224().SHA224("data"), MODE_PRIVATE).edit();
                editor.putBoolean(new SHA224().SHA224("check_finger_print"), isChecked);
                editor.apply();//保存文件
            }
        });
        if (!checkFingerprintSupport(false)) {
            switch_finger_print.setEnabled(false);
            switch_finger_print.setChecked(false);
            FrameLayout frameLayout = viewThis.findViewById(R.id.frameLayout);
            frameLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkFingerprintSupport(true);
                }
            });
        }
        /*主页显示密码Switch并监听*/
        Switch switch_password_show = viewThis.findViewById(R.id.switch_password_show);
        if (preferences.getBoolean(new SHA224().SHA224("check_password_show"), false)) {
            switch_finger_print.setChecked(true);
        }
        switch_password_show.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences.Editor editor = getActivity().getSharedPreferences(new SHA224().SHA224("data"), MODE_PRIVATE).edit();
                editor.putBoolean(new SHA224().SHA224("check_password_show"), isChecked);
                editor.apply();//保存文件
            }
        });
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
                SharedPreferences.Editor editor = getActivity().getSharedPreferences("something", MODE_PRIVATE).edit();
                editor.clear();
                editor.putBoolean("testBack", true);
                editor.apply();
                startActivity(new Intent(getActivity(), AboutActivity.class));//启动关于Activity
            }
        });
        /*监听更改安全密码按钮*/
        LinearLayout item_card_view_change_password = viewThis.findViewById(R.id.item_card_view_change_password);
        item_card_view_change_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText et = new EditText(getActivity());
                et.setTypeface(Typeface.DEFAULT);
                et.setInputType(InputType.TYPE_CLASS_NUMBER);
                et.setTransformationMethod(new PasswordTransformationMethod());
                et.setTransformationMethod(PasswordTransformationMethod.getInstance());
                new AlertDialog.Builder(getActivity()).setTitle("请输入原密码")
                        .setView(et)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //判断原密码正确性
                                SharedPreferences sharedPreferences = getActivity()
                                        .getSharedPreferences(new SHA224().SHA224("data"), Context.MODE_PRIVATE);
                                if (new SHA224().SHA224(et.getText().toString())
                                        .equals(sharedPreferences.getString(new SHA224().SHA224("password"), null))) {
                                    SharedPreferences.Editor editor = getActivity()
                                            .getSharedPreferences(new SHA224().SHA224("data"), Context.MODE_PRIVATE).edit();
                                    editor.putString(new SHA224().SHA224("password"), null);
                                    editor.apply();
                                    //启动新建密码界面
                                    startActivity(new Intent(getActivity(), CreatePassActivity.class));
                                    getActivity().finish();
                                } else {
                                    Toast.makeText(getActivity(), "原密码错误", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }).setNegativeButton("取消", null).show();
            }
        });


    }

    /**
     * 判断是否支持指纹验证
     * 用到的权限：USE_FINGERPRINT
     */
    private Boolean checkFingerprintSupport(Boolean show) {
        FingerprintManager manager = (FingerprintManager) getActivity().getSystemService(FINGERPRINT_SERVICE);
        KeyguardManager mKeyManager = (KeyguardManager) getActivity().getSystemService(KEYGUARD_SERVICE);
        try {
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {
                if (show) {
                    Toast.makeText(getActivity(), "没有指纹识别权限", Toast.LENGTH_SHORT).show();
                }
                return false;
            }
            //判断硬件是否支持指纹识别
            if (!manager.isHardwareDetected()) {
                if (show) {
                    Toast.makeText(getActivity(), "此手机不支持指纹识别", Toast.LENGTH_SHORT).show();
                }
                return false;
            }
            //判断 是否开启锁屏密码
            if (!mKeyManager.isKeyguardSecure()) {
                if (show) {
                    Toast.makeText(getActivity(), "没有开启锁屏密码", Toast.LENGTH_SHORT).show();
                }
                return false;
            }
            //判断是否有指纹录入
            if (!manager.hasEnrolledFingerprints()) {
                if (show) {
                    Toast.makeText(getActivity(), "没有录入指纹", Toast.LENGTH_SHORT).show();
                }
                return false;
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}