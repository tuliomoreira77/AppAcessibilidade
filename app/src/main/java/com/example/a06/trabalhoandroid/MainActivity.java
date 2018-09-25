package com.example.a06.trabalhoandroid;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.session.MediaController;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import java.util.Locale;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    TextToSpeech tts;
    TtsData ttsData  = null;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        checkPermission(Manifest.permission.CAMERA);
        checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
        checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_lay);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        setVolumeControlStream(AudioManager.STREAM_MUSIC);

        Intent ttsIntent = new Intent();
        ttsIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
        startActivityForResult(ttsIntent, 1000);

        tts = new TextToSpeech(getBaseContext(), new TextToSpeech.OnInitListener() {
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    tts.setLanguage(Locale.getDefault());
                    if(ttsData != null)
                        ttsData.setInit(true);
                }
            }
        });

        ttsData = new TtsData(tts);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.const_lay, new Scanner()).commit();
    }

    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        if (requestCode == 1000) {
            if (resultCode != TextToSpeech.Engine.CHECK_VOICE_DATA_PASS) {
                Intent installIntent = new Intent();
                installIntent.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
                startActivity(installIntent);
            }
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_lay);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.scanner) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.const_lay, new Scanner()).commit();
        } else if (id == R.id.color_scanner) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.const_lay, new ColorScaner()).commit();
        } else if (id == R.id.generator) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.const_lay, new Generator()).commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_lay);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void checkPermission(String s)
    {
        int permissionCheck = ContextCompat.checkSelfPermission(this,
                s);

        if(permissionCheck != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,
                    new String[]{s},
                    1);
        }
    }

    public TtsData getTtsData()
    {
        return ttsData;
    }

    protected void onDestroy() {
        super.onDestroy();
        tts.shutdown();
    }
}
