package com.example.amd48.wxdemo.activity;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.tech.NfcF;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.example.amd48.wxdemo.R;

public class MainActivity extends AppCompatActivity {

    private NfcAdapter mNfcAdapter = null;
    private static final String Tag = "xyf/nfc";
    private static final String TAG = "Main2Activity/xyf/nfc";
    private IntentFilter[] intentFiltersArray;
    private String[][] techListsArray;
    private PendingIntent pendingIntent;

    //check if the device supports nfc
    private void NfcCheck(){
        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if(mNfcAdapter == null){
            Log.w(Tag, "Device not support NFC");
            return;
        }else{
            if(!mNfcAdapter.isEnabled()){
                Log.w(Tag, "Your NFC is not enabled");
                return;
            }else{
                Log.w(Tag, "NFC is enabled");
            }

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        NfcCheck();

        //NFC前台调度使用
        pendingIntent = PendingIntent.getActivity(
                this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);

        IntentFilter ndef = new IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED);
        try{
            ndef.addDataType("*/*");
        }
        catch (IntentFilter.MalformedMimeTypeException e){
            throw new RuntimeException("fail", e);
        }
        intentFiltersArray = new IntentFilter[]{ndef,};
        techListsArray = new String[][]{new String[] {NfcF.class.getName()}};

        //Act跳转：1. 设置intent 2. 启动跳转
//        Intent intent = new Intent();
//        intent.setClass(
//                MainActivity.this,
//                Main2Activity.class
//        );
//        startActivity(intent);

    }

    //解析intent
    private void ResolveIntent(Intent intent){
//        Log Util.i(MyConstant.Tag, Tag_ASSIST + "into resolveIntent");

        String action = intent.getAction();
        if(NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)){
//
            NdefMessage[] messages = null;
            Parcelable[] rawMsgs = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
            if(rawMsgs != null){
                messages = new NdefMessage[rawMsgs.length];
                for(int i=0; i<rawMsgs.length; i++){
                    messages[i] = (NdefMessage)rawMsgs[i];
//                    LogUtil
                }
            }else{
                byte[] empty = new byte[]{};
                NdefRecord record = new NdefRecord(NdefRecord.TNF_UNKNOWN, empty, empty, empty);
                NdefMessage msg = new NdefMessage(new NdefRecord[]{record});
                messages = new NdefMessage[]{msg};
            }
            //code here
        }else if(NfcAdapter.ACTION_TECH_DISCOVERED.equals(intent.getAction())){

        }else if(NfcAdapter.ACTION_TAG_DISCOVERED.equals(intent.getAction())){

        }else{
            finish();
            return;
        }
    }

    @Override
    public void onPause(){
        super.onPause();
        Log.v(TAG, "onPause");
        mNfcAdapter.disableForegroundDispatch(this);
    }

    @Override
    public void onResume(){
        super.onResume();
        Log.v(TAG, "onResume");
        mNfcAdapter.enableForegroundDispatch(this, pendingIntent, intentFiltersArray,
                techListsArray);
    }

    @Override
    protected void onStart(){
        super.onStart();
        Log.v(TAG, "onStart");
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

    public void onNewIntent(Intent intent){
        Tag tagFromIntent = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
