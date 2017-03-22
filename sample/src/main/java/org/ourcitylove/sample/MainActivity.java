package org.ourcitylove.sample;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import android.widget.Toast;

import com.karumi.dexter.Dexter;

import org.ourcitylove.oclapp.Firebase;
import org.ourcitylove.oclapp.LocationActivity;
import org.ourcitylove.oclapp.Network;
import org.ourcitylove.oclapp.RationalePermissionsListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.text_view)
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        fab.setOnClickListener(view -> startActivity(new Intent(this, MainActivity.class)));

        Firebase.trackScreen("MAIN");

        Network.checkConnectivity(this, "Please connect to network!");

        // test permission
        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CALL_PHONE
                )
                .withListener(
                        RationalePermissionsListener.Builder.with(this)
                                .withRunOnGranted(()->{
                                    Toast.makeText(this, "Permission Granted!", Toast.LENGTH_SHORT).show();
                                })
                                .build())
                .check();

        App.loc.lastAndUpdate(this)
                .subscribe(loc -> textView.setText(loc.toString()));
//        setLocationListener(loc -> textView.setText(loc.toString()));
    }
}

