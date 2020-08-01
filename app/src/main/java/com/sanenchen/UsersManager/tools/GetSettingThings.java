package com.sanenchen.UsersManager.tools;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

/**
 * 获取用户个性化
 * @author sanenchen
 * @version v1.0
 */
public class GetSettingThings {
    Context mContent;
    SharedPreferences data;

    public GetSettingThings(Context context) {
        mContent = context;
        data = context.getSharedPreferences(new SHA224().SHA224("data"), MODE_PRIVATE);
    }

    /**
     * 获取是否开启指纹
     */
    public boolean checkFingerPrintSetting() {
        return data.getBoolean(new SHA224().SHA224("check_finger_print"), false);
    }

    /**
     * 获取是否开启首页密码显示功能
     */

    public boolean checkShowPassword() {
        return data.getBoolean(new SHA224().SHA224("check_password_show"), false);
    }

    /**
     * 获取是否开启允许在所有界面截屏功能
     */

    public boolean checkCanScreenshot() {
        return data.getBoolean(new SHA224().SHA224("check_can_screenshot"), false);
    }
}
