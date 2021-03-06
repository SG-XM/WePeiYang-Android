package com.twt.service.module.welcome;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.twt.wepeiyang.commons.utils.CommonPrefUtil;
import com.twtstudio.retrox.auth.login.LoginActivity;
import com.twt.service.R;
import com.twt.service.base.BaseActivity;
import com.twt.service.home.HomeActivity;

import java.util.Arrays;

/**
 * Created by retrox on 2017/1/20.
 */

public class WelcomeActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        boolean isLogin = CommonPrefUtil.getIsLogin();
        boolean isFirstLogin = CommonPrefUtil.getIsFirstLogin();
//        if (true) {
//            Intent intent = new Intent(this, WelcomeSlideActivity.class);
//            startActivity(intent);
//        } else
        if (isLogin) {
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }

        //add dynamic shortcuts
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
            createDynamicShortcuts();
        }

        finish();
    }


    @TargetApi(25)
    private void createDynamicShortcuts() {
        ShortcutManager shortcutManager = getSystemService(ShortcutManager.class);

        ShortcutInfo dynamicShortcut1 = new ShortcutInfo.Builder(this, "shortcut_web")
                .setShortLabel("twt")
                .setLongLabel("Website")
                .setIcon(Icon.createWithResource(this, R.drawable.ic_twt_cloud))
                .setIntent(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.twt.edu.cn")))
                .build();

        ShortcutInfo dynamicShortcut2 = new ShortcutInfo.Builder(this, "shortcut_dynamic")
                .setShortLabel("GPA")
                .setLongLabel("GPA")
                .setIcon(Icon.createWithResource(this, R.drawable.ic_main_gpa))
                .setIntents(
                        // this dynamic shortcut set up a back stack using Intents, when pressing back, will go to MainActivity
                        // the last Intent is what the shortcut really opened
                        new Intent[]{
                                new Intent(Intent.ACTION_MAIN, Uri.EMPTY, this, HomeActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK),
                                new Intent("com.twt.gpa.action.DYNAMIC_OPEN")
                                // intent's action must be set
                        })
                .build();

        ShortcutInfo dynamicShortcut3 = new ShortcutInfo.Builder(this, "shortcut_dynamic_3")
                .setShortLabel("Schedule")
                .setLongLabel("Schedule")
                .setIcon(Icon.createWithResource(this, R.drawable.ic_main_schedule))
                //com.twt.schedule.action.DYNAMIC_OPEN
                .setIntents(
                        // this dynamic shortcut set up a back stack using Intents, when pressing back, will go to MainActivity
                        // the last Intent is what the shortcut really opened
                        new Intent[]{
                                new Intent(Intent.ACTION_MAIN, Uri.EMPTY, this, HomeActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK),
                                new Intent("com.twt.schedule.action.DYNAMIC_OPEN")
                                // intent's action must be set
                        })
                .build();
//        ShortcutInfo dynamicShortcut4 = new ShortcutInfo.Builder(this, "shortcut_dynamic_4")
//                .setShortLabel("Bike")
//                .setLongLabel("Bike")
//                .setIcon(Icon.createWithResource(this, R.drawable.ic_main_bike))
//                .setIntent(new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/life2015")))
//                .build();

        // the max limit for shortcuts is 5 (static + dynamic), if we add more than 5, exception will be thrown
        // Caused by: java.lang.IllegalArgumentException: Max number of dynamic shortcuts exceeded


        shortcutManager.setDynamicShortcuts(Arrays.asList(dynamicShortcut1, dynamicShortcut2, dynamicShortcut3));

    }
}
