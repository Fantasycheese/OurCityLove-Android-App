package org.ourcitylove.oclapp;

import android.app.Activity;
import android.support.annotation.StringRes;

import com.github.javiersantos.appupdater.AppUpdater;

/**
 * Created by Vegetable on 2016/9/23.
 */

public class VersionManager {
    private static VersionManager vm;
    private static AppUpdater apu;
    public static Activity act;
    private static String Title, Content, Update, Dismiss;

    public static VersionManager init(Activity ac){
        vm = null == vm?new VersionManager():vm;
        vm.apu = new AppUpdater(ac)
                .showAppUpdated(false)
                .setButtonDoNotShowAgain("");
        vm.act = ac;
        return vm;
    }

    public static VersionManager setTitle(@StringRes int Res){vm.Title = vm.act.getString(Res);return vm;}
    public static VersionManager setTitle(String Res){vm.Title = Res;return vm;}

    public static VersionManager setContent(@StringRes int Res){vm.Content = vm.act.getString(Res);return vm;}
    public static VersionManager setContent(String Res){vm.Content = Res;return vm;}

    public static VersionManager setUpdateButton(@StringRes int Res){vm.Update = vm.act.getString(Res);return vm;}
    public static VersionManager setUpdateButton(String Res){vm.Update = Res;return vm;}

    public static VersionManager setDismissButton(@StringRes int Res){vm.Dismiss = vm.act.getString(Res);return vm;}
    public static VersionManager setDismissButton(String Res){vm.apu.setButtonDismiss(Res);return vm;}

    public static void start() {
        vm.apu.setTitleOnUpdateAvailable(Title)
           .setContentOnUpdateAvailable(Content)
           .setButtonUpdate(Update)
           .setButtonDismiss(Dismiss)
           .start();
}
}
