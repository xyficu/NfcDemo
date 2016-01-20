package com.example.amd48.wxdemo.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.amd48.wxdemo.R;

public class InitActivity extends AppCompatActivity {
    private static final String TAG = "InitActivity/xyf/nfc";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.v(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_init);

        Button btn3 = (Button)findViewById(R.id.buttonStartNfcReadTesting);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(InitActivity.this, NfcActivity.class));
//                finish();
            }
        });

    }

    @Override
    protected void onStart(){
        super.onStart();
        Log.v(TAG, "onStart");
    }

    @Override
    protected void onResume(){
        super.onResume();
        Log.v(TAG, "onResume");
    }

    @Override
    protected void onPause(){
        super.onPause();
        Log.v(TAG, "onPause");
    }

    @Override
    protected void onStop(){
        super.onStop();
        Log.v(TAG, "onStop");
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.v(TAG, "onDestroy");
    }
}
