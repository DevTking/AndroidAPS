package so.ttq.cracker;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

import java.util.List;

/**
 * Created by [dev.tking] on 2020/3/5.
 * Copyright (c) 2018 [dev.tking@outlook.com] All Rights Reserved.
 */
public class IntentKits {
    /**
     * 检测意图的安全性是否可行；
     * 例：开启一个照相机，但手机本身不带有相机应用，则返回false，表示手机内没有应用可以相应该意图
     *
     * @param context 上下文
     * @param intent  意图
     * @return 是否安全
     */
    public static int checkIntentResponse(Context context, Intent intent) {
        PackageManager packageManager = context.getPackageManager();
        List activities = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        return activities.size();
    }

    /**
     * 检测意图的安全性是否可行；
     * 例：开启一个照相机，但手机本身不带有相机应用，则返回false，表示手机内没有应用可以相应该意图
     *
     * @param context 上下文
     * @param intent  意图
     * @return 是否安全
     */
    public static boolean checkIntent(Context context, Intent intent) {
        return checkIntentResponse(context, intent) > 0;
    }

    /**
     * 创建一个意图每次都有意图选择器，适用于多个应用响应。
     *
     * @param context 上下文
     * @param title   选择器标题
     * @param intent  意图
     * @return 返回的Intent不等于Null, 表示安全可用，并且可以打开意图选择器，反之，意图不可用。
     */
    public static Intent createIntentChooser(Context context, String title, Intent intent) {
        Intent chooser = Intent.createChooser(intent, title);
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            return chooser;
        }
        return null;
    }

    public static Intent createSafeIntent(Context context, String title, Intent intent) {
        int size = checkIntentResponse(context, intent);
        if (size == 0) {
            return null;
        } else if (size == 1) {
            return intent;
        } else {
            return createIntentChooser(context, title, intent);
        }
    }
}
