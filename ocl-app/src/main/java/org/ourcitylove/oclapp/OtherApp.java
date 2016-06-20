package org.ourcitylove.oclapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

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
}