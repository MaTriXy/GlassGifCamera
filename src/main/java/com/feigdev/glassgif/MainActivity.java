package com.feigdev.glassgif;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.WindowManager;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends Activity implements GifFlowControl {
    private static final String TAG = "MainActivity";
    static ArrayList<String> listOfFiles = new ArrayList<String>();
    static String gifFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    @Override
    public void onResume() {
        super.onResume();
        getFragmentManager().beginTransaction()
                .replace(R.id.container, new PhotoFrag())
                .commit();
    }

    @Override
    public void startBuild() {
        getFragmentManager().beginTransaction()
                .replace(R.id.container, new GifBuilderFrag())
                .commit();
    }

    @Override
    public void startDisplay() {
        // https://www.grokkingandroid.com/adding-files-to-androids-media-library-using-the-mediascanner/
        try {
            Intent intent =
                    new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            intent.setData(Uri.fromFile(new File(gifFile)));
            sendBroadcast(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }

        getFragmentManager().beginTransaction()
                .replace(R.id.container, new GifDisplayFrag())
                .commit();
    }

}
