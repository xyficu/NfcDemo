package com.example.amd48.wxdemo.activity;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.amd48.wxdemo.R;

public class Main2Activity extends Activity {

    private static final String TAG = "xyf/nfc";
    public static final String MIME_TEXT_PLAIN = "text/plain";

    private NfcAdapter mNfcAdapter = null;
    private PendingIntent mNfcPendingIntent = null;
    TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v(TAG, "onCreate");
        setContentView(R.layout.activity_main2);

        mTextView = (TextView)findViewById(R.id.textViewTagId);
        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);


        if (mNfcAdapter == null) {
            //提示若不支持NFC，Activity退出
            Toast.makeText(this, "本设备不支持NFC.", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        if (!mNfcAdapter.isEnabled()) {
            Toast.makeText(this, "NFC开关未开启.请开启NFC开关.", Toast.LENGTH_LONG).show();
            finish();
            return;
        } else {
            Toast.makeText(this, "NFC开关已开启，NFC工作中...", Toast.LENGTH_LONG).show();
        }

        //NFC前台调度系统
        mNfcPendingIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);

    }

    //处理标签数据
    private void handleIntent(Intent intent){
        String action = intent.getAction();
        //1. 如果是ACTION_NDEF_DISCOVERED类型的记录
        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)) {

            String type = intent.getType();
            if (MIME_TEXT_PLAIN.equals(type)) {

                Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
//                TODO

            } else {
                Log.d(TAG, "Wrong mime type: " + type);
            }
            //2. 如果是ACTION_TECH_DISCOVERED类型的记录
        } else if (NfcAdapter.ACTION_TECH_DISCOVERED.equals(action)) {

            // In case we would still use the Tech Discovered Intent
            Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            String[] techList = tag.getTechList();
            String searchedTech = Ndef.class.getName();

            for (String tech : techList) {
                if (searchedTech.equals(tech)) {
//                    TODO
                    break;
                }
            }
            //3. 如果是ACTION_TAG_DISCOVERED类型的记录
        }else if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(action)){
            Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            String[] techList = tag.getTechList();
            String searchedTech = Ndef.class.getName();
            String tagId = bytesToHexString(tag.getId());

            mTextView.setText(tagId);
        }else{
            Log.v(TAG, "unknown intent");

        }

    }

    //字符序列转换为16进制字符串
    private String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("0x");
        if (src == null || src.length <= 0) {
            return null;
        }
        char[] buffer = new char[2];
        for (int i = 0; i < src.length; i++) {
            buffer[0] = Character.forDigit((src[i] >>> 4) & 0x0F, 16);
            buffer[1] = Character.forDigit(src[i] & 0x0F, 16);
            System.out.println(buffer);
            stringBuilder.append(buffer);
        }
        return stringBuilder.toString();
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

        //开启NFC前台调度系统
        enableForegroundDispatch();

        //处理Intent
        handleIntent(getIntent());

    }

    private void enableForegroundDispatch(){
        if(mNfcAdapter!=null){
            mNfcAdapter.enableForegroundDispatch(this, mNfcPendingIntent, null, null);
        }
    }

    private void disableForegroundDispatch(){
        if(mNfcAdapter!=null){
            mNfcAdapter.disableForegroundDispatch(this);
        }
    }

    @Override
    protected void onNewIntent(Intent intent){
        // onResume gets called after this to handle the intent

        Log.v(TAG, "onNewIntent=================================");
        setIntent(intent);

    }

    @Override
    protected void onPause(){
        super.onPause();

        //关闭NFC前台调度系统
        disableForegroundDispatch();

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


