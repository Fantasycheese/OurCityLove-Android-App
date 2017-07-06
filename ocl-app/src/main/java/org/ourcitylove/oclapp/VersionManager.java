//package org.ourcitylove.oclapp;
//
//import android.app.Activity;
//import android.app.AlertDialog;
//import android.content.ActivityNotFoundException;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.net.Uri;
//import android.support.annotation.StringRes;
//import android.widget.Toast;
//
//import com.github.javiersantos.appupdater.AppUpdaterUtils;
//import com.github.javiersantos.appupdater.enums.AppUpdaterError;
//import com.github.javiersantos.appupdater.objects.Update;
//
///**
// * Created by Vegetable on 2016/9/23.
// */
//
//public class VersionManager {
//
//    //    private AppUpdater apu;
//    private Activity act;
//    private String Title, Content, Update, Dismiss, NotShow = "";
//    private boolean ForceUpdate;
//
//    public VersionManager init(Activity ac) {
////        apu = new AppUpdater(ac)
////                .showAppUpdated(false);
//        act = ac;
//        setTitle(R.string.TitleOnUpdateAvailable);
//        setContent(act.getString(R.string.ContentOnUpdateAvailable, act.getString(R.string.app_name), "1.0.0"));
//        setUpdateButton(R.string.UpdateGo);
//        setDismissButton(R.string.UpdateNextTime);
//        setNotShowButton(R.string.DoNotShowAgain);
//        return this;
//    }
//
//    public VersionManager setTitle(@StringRes int Res) {
//        Title = act.getString(Res);
//        return this;
//    }
//
//    public VersionManager setTitle(String Res) {
//        Title = Res;
//        return this;
//    }
//
//    public VersionManager setContent(@StringRes int Res) {
//        Content = act.getString(Res);
//        return this;
//    }
//
//    public VersionManager setContent(String Res) {
//        Content = Res;
//        return this;
//    }
//
//    public VersionManager setUpdateButton(@StringRes int Res) {
//        Update = act.getString(Res);
//        return this;
//    }
//
//    public VersionManager setUpdateButton(String Res) {
//        Update = Res;
//        return this;
//    }
//
//    public VersionManager setDismissButton(@StringRes int Res) {
//        Dismiss = act.getString(Res);
//        return this;
//    }
//
//    public VersionManager setDismissButton(String Res) {
//        Dismiss = Res;
//        return this;
//    }
//
//    public VersionManager setNotShowButton(@StringRes int Res) {
//        NotShow = act.getString(Res);
//        return this;
//    }
//
//    public VersionManager setNotShowButton(String Res) {
//        NotShow = Res;
//        return this;
//    }
//
//    public VersionManager setForceUpdate() {
//        return setForceUpdate(false);
//    }
//
//    public VersionManager setForceUpdate(boolean ForceUpdate) {
//        this.ForceUpdate = ForceUpdate;
//        return this;
//    }
//
//    public void start() {
//        new AppUpdaterUtils(act)
//                .withListener(
//                        new AppUpdaterUtils.UpdateListener() {
//                            private boolean positive;
//                            private Toast toast;
//                            private AlertDialog alertDialog;
//
//                            @Override
//                            public void onSuccess(Update update, Boolean isUpdateAvailable) {
//                                if (!isUpdateAvailable) return;
//                                toast = Toast.makeText(act, R.string.msg_force_update, Toast.LENGTH_SHORT);
//                                toast.show();
//                                alertDialog = new AlertDialog.Builder(act)
//                                        .setTitle(Title)
//                                        .setMessage(Content)
//                                        .setPositiveButton(Update, (dialog, which) -> {
//                                            positive = true;
//                                            final String appPackageName = act.getPackageName(); // getPackageName() from Context or Activity object
//                                            try {
//                                                act.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
//                                            } catch (ActivityNotFoundException anfe) {
//                                                act.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
//                                            }
//                                        })
//                                        .create();
//                                if(!ForceUpdate)
//                                    alertDialog.setButton(DialogInterface.BUTTON_NEUTRAL, Dismiss, (dialog, which)-> alertDialog.dismiss());
//                                alertDialog.setCancelable(!ForceUpdate);
//                                alertDialog.show();
//
//                            }
//
//                            @Override
//                            public void onFailed(AppUpdaterError appUpdaterError) {
//
//                            }
//
//                            private void forceUpdate() {
//                                if (toast != null) toast.cancel();
//                                toast = Toast.makeText(act, R.string.msg_force_update, Toast.LENGTH_SHORT);
//                                toast.show();
//                                alertDialog.show();
//                            }
//                        })
//                .start();
//    }
//}
