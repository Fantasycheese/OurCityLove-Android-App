package org.ourcitylove.oclapp;

import android.app.Activity;
import android.support.annotation.StringRes;

import com.github.javiersantos.appupdater.AppUpdater;

/**
 * Created by Vegetable on 2016/9/23.
 */

public class VersionManager {

    private AppUpdater apu;
    private Activity act;
    private String Title, Content, Update, Dismiss, NotShow = "";

    public VersionManager init(Activity ac){
        apu = new AppUpdater(ac)
                .showAppUpdated(false);
        act = ac;
        setTitle(R.string.TitleOnUpdateAvailable);
        setContent(act.getString(R.string.ContentOnUpdateAvailable, act.getString(R.string.app_name)));
        setUpdateButton(R.string.UpdateGo);
        setDismissButton(R.string.UpdateNextTime);
        setNotShowButton(R.string.DoNotShowAgain);
        return this;
    }

    public VersionManager setTitle(@StringRes int Res){Title = act.getString(Res);return this;}
    public VersionManager setTitle(String Res){Title = Res;return this;}

    public VersionManager setContent(@StringRes int Res){Content = act.getString(Res);return this;}
    public VersionManager setContent(String Res){Content = Res;return this;}

    public VersionManager setUpdateButton(@StringRes int Res){Update = act.getString(Res);return this;}
    public VersionManager setUpdateButton(String Res){Update = Res;return this;}

    public VersionManager setDismissButton(@StringRes int Res){Dismiss = act.getString(Res);return this;}
    public VersionManager setDismissButton(String Res){Dismiss = Res;return this;}

    public VersionManager setNotShowButton(@StringRes int Res){NotShow = act.getString(Res);return this;}
    public VersionManager setNotShowButton(String Res){NotShow = Res;return this;}

    public void start() {
        this.apu.setTitleOnUpdateAvailable(Title)
           .setContentOnUpdateAvailable(Content)
           .setButtonUpdate(Update)
           .setButtonDismiss(Dismiss)
           .setButtonDoNotShowAgain(NotShow)
           .start();
}
}
