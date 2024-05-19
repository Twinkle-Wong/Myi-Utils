package com.myi_utils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void QR(View view) {
        Intent intent = new Intent(MainActivity.this,QRActivity.class);
        startActivity(intent);
    }
    public void POST(View view) {
        Intent intent = new Intent(MainActivity.this,PostActivity.class);
        startActivity(intent);
    }
}