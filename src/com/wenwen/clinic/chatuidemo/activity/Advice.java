package com.wenwen.clinic.chatuidemo.activity;

import com.wenwen.clinic.chatuidemo.R;
import com.wenwen.clinic.debug.DebugLog;

import android.os.Bundle;
import android.view.View;

public class Advice extends BaseActivity {
    private final String TAG = "Advice";

    @Override
    protected void onCreate(Bundle arg0) {
        // TODO Auto-generated method stub
        super.onCreate(arg0);
        DebugLog.i(TAG, "onCreate");
        setContentView(R.layout.advice);
    }

    public void back(View view) {
        finish();
    }
}
