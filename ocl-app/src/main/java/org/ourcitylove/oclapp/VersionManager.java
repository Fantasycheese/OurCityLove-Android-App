package org.ourcitylove.oclapp;

import android.app.Activity;
import android.support.annotation.StringRes;

import com.github.javiersantos.appupdater.AppUpdater;

/**
 * Created by Vegetable on 2016/9/23.
 */

public class VersionManager {
    private static AppUpdater apu;
    public static Activity act;
    private static String Title, Content, Update, Dismiss;

    public static void init(Activity ac){
        apu = new AppUpdater(ac)
                .showAppUpdated(false)
                .setButtonDoNotShowAgain("");
        act = ac;
    }

    public static void setTitle(@StringRes int Res){Title = act.getString(Res);}
    public static void setTitle(String Res){Title = Res;}

    public static void setContent(@StringRes int Res){Content = act.getString(Res);}
    public static void setContent(String Res){Content = Res;}

    public static void setUpdateButton(@StringRes int Res){Update = act.getString(Res);}
    public static void setUpdateButton(String Res){Update = Res;}

    public static void setDismissButton(@StringRes int Res){Dismiss = act.getString(Res);}
    public static void setDismissButton(String Res){apu.setButtonDismiss(Res);}

    public static void start(Activity ac) {
        apu.setTitleOnUpdateAvailable(Title)
           .setContentOnUpdateAvailable(Content)
           .setButtonUpdate(Update)
           .setButtonDismiss(Dismiss)
           .start();
}
}
