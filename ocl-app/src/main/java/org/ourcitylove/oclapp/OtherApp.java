package org.ourcitylove.oclapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.telephony.PhoneNumberUtils;

@SuppressWarnings("unused")
public class OtherApp {
    public static void googleMapNavigate(Context context, String direct){
        Uri uri=Uri.parse("google.navigation:q=" + direct);
        Intent intent=new Intent(Intent.ACTION_VIEW,uri);
        intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
        context.startActivity(intent);
    }

    public static void call(Context context, String tel) {
        tel = tel.replaceAll("[()-]", "");
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + tel));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static void callByWait(Context context, String tel) {
        tel = tel.replaceAll("()-]", "").replaceAll("#", String.format("%s", PhoneNumberUtils.WAIT));
        Intent intent = new Intent();
        intent.setAction("android.intent.action.CALL");
        intent.setData(Uri.parse("tel:" + tel));
        intent.setFlags(268435456);
        context.startActivity(intent);
    }

    public static void callBySpeed(Context context, String tel) {
        tel = tel.replaceAll("()-]", "").replaceAll("#", String.format("%s", PhoneNumberUtils.PAUSE));
        Intent intent = new Intent();
        intent.setAction("android.intent.action.CALL");
        intent.setData(Uri.parse("tel:" + tel));
        intent.setFlags(268435456);
        context.startActivity(intent);
    }
}
