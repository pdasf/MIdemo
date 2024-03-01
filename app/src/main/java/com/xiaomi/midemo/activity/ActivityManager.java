package com.xiaomi.midemo.activity;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

public class ActivityManager {
    public static final String MEDIA_DETAIL = "MEDIA_DETAIL";

    private static final List<Activity> activities = new ArrayList<>();

    public static void createAct(Activity activity) {
        activities.add(activity);
    }

    public static Activity getCurActivity() {
        Activity curActivity = null;
        if (activities.get(activities.size() - 1) != null){
            curActivity = activities.get(activities.size() - 1);
        }
        return curActivity;
    }

    public static void finishAll() {
        activities.forEach(activity -> {
            if (!activity.isDestroyed()) {
                activity.finish();
            }
        });
        System.exit(0);
    }
}
