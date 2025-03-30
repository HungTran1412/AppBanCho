package dev.mhung.ltmobile.petapplication.model;

import android.app.Activity;
import android.content.Intent;

public class SwitchScreen {
    public static void switchScreen(Activity activity, Class<?> targetActivity){
        Intent intent = new Intent(activity, targetActivity);
        activity.startActivity(intent);
    }
}
