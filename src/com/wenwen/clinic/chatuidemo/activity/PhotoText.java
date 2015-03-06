package com.wenwen.clinic.chatuidemo.activity;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.wenwen.clinic.chatuidemo.R;
import com.wenwen.clinic.chatuidemo.utils.HttpClientRequest;
import com.wenwen.clinic.chatuidemo.utils.Urls;
import com.wenwen.clinic.debug.DebugLog;

public class PhotoText extends BaseActivity implements OnClickListener{
    private final String TAG = "PhotoText";
    private String uid, username, accountname;
    private EditText et_points_every_num, et_points_weekly_num;
    private Button sure;

    @Override
    protected void onCreate(Bundle arg0) {
        // TODO Auto-generated method stub
        super.onCreate(arg0);
        DebugLog.i(TAG, "onCreate");
        setContentView(R.layout.activity_phototext);
        uid = getIntent().getExtras().getString("uid");
        accountname = getIntent().getExtras().getString("accountname");
        username = getIntent().getExtras().getString("username");
        init();
        initdata();
    }

    private void init() {
        et_points_every_num = (EditText) findViewById(R.id.et_points_every_num);
        et_points_weekly_num = (EditText) findViewById(R.id.et_points_weekly_num);
        sure = (Button) findViewById(R.id.sure);
        sure.setOnClickListener(this);
    }

    private void initdata() {

        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("获得预约信息...");
        pd.show();
        RequestParams params = new RequestParams();
        params.put("uid", uid);
        params.put("flag", "3");
        HttpClientRequest.post(Urls.GETORDERDETAILS, params, 3000,
                new AsyncHttpResponseHandler() {
                    @Override
                    public void onStart() {
                        // TODO Auto-generated method stub
                        super.onStart();
                        pd.show();
                    }

                    @Override
                    public void onSuccess(int arg0, Header[] arg1,
                            byte[] arg2) {
                        // TODO Auto-generated method stub
                        try {
                            String res = new String(arg2);
                            JSONObject result = new JSONObject(res);
                            DebugLog.i("res", result.toString());
                            switch (Integer.valueOf(result.getString("ret"))) {
                            case -1:
                                Toast.makeText(PhotoText.this, "未设置预约信息", 0)
                                        .show();
                                break;
                            case 1:
                                et_points_every_num .setText(result.getString("order_coin_once"));
                                et_points_weekly_num .setText( result.getString("order_coin_week"));
                                break;
                            default:
                                break;
                            }
                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFinish() {
                        // TODO Auto-generated method stub
                        super.onFinish();
                        pd.dismiss();
                    }

                    @Override
                    public void onFailure(int arg0, Header[] arg1,
                            byte[] arg2, Throwable arg3) {
                        // TODO Auto-generated method stub

                    }
                });
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        if (v == sure) {
            startActivity(new Intent(this, ChatActivity.class)
            .putExtra("accountname", accountname)
            .putExtra("username", username)
            .putExtra("type", "2"));
            finish();
        }
    }
}
