package com.wenwen.clinic.chatuidemo.activity;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.wenwen.clinic.chatuidemo.DemoApplication;
import com.wenwen.clinic.chatuidemo.R;
import com.wenwen.clinic.chatuidemo.adapter.ClinicOrderAdapter;
import com.wenwen.clinic.chatuidemo.domain.ClinicOrderInfo;
import com.wenwen.clinic.chatuidemo.utils.HttpClientRequest;
import com.wenwen.clinic.chatuidemo.utils.Urls;
import com.wenwen.clinic.debug.DebugLog;

public class ClinicOrder extends BaseActivity {
    private final String TAG = "ClinicOrder";
    private ListView listView;
    private ClinicOrderAdapter clinicOrderAdapter;
    private List<ClinicOrderInfo> list;

    @Override
    protected void onCreate(Bundle arg0) {
        // TODO Auto-generated method stub
        super.onCreate(arg0);
        DebugLog.i(TAG, "onCreate");
        setContentView(R.layout.clinic_order);
        getdata();
        list = new ArrayList<ClinicOrderInfo>();
        listView = (ListView) findViewById(R.id.list);
    }

    private void getdata() {
        // TODO Auto-generated method stub
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("预约列表..");
        RequestParams params = new RequestParams();
        params.put("uid", DemoApplication.getInstance().getUserUid());
        params.put("flag", "0");
        HttpClientRequest.post(Urls.GETORDER, params, 3000,
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
                            DebugLog.i(TAG, "res" + res);
                            JsonParser parser = new JsonParser();
                            JsonArray Jarray = parser.parse(res)
                                    .getAsJsonArray();
                            ClinicOrderInfo acountInfo = null;
                            Gson gson = new Gson();
                            for (JsonElement obj : Jarray) {
                                acountInfo = gson.fromJson(obj,
                                        ClinicOrderInfo.class);
                                list.add(acountInfo);
                            }
                            clinicOrderAdapter = new ClinicOrderAdapter(
                                    ClinicOrder.this, list);
                            listView.setAdapter(clinicOrderAdapter);
                        } catch (Exception e) {
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

    public void back(View view) {
        finish();
    }
}
