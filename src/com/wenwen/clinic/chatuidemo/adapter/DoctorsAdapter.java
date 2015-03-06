package com.wenwen.clinic.chatuidemo.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.wenwen.clinic.chatuidemo.R;
import com.wenwen.clinic.chatuidemo.domain.AcountInfo;
import com.wenwen.clinic.debug.DebugLog;

public class DoctorsAdapter extends BaseAdapter {
    private Context mycontext;
    private List<AcountInfo> listdata;
    protected LayoutInflater layoutInflater;

    public DoctorsAdapter(Context context, List<AcountInfo> data) {
        // TODO Auto-generated constructor stub
        this.mycontext = context;
        this.listdata = data;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return listdata.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return getItem(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        ViewHolder holder = null;

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.doctors_row_contact_item, null);
            holder = new ViewHolder();
            holder.avatar = (ImageView) convertView.findViewById(R.id.avatar);
            holder.name = (TextView) convertView.findViewById(R.id.name);
            holder.hospital = (TextView) convertView.findViewById(R.id.hospital);
            holder.information = (TextView) convertView.findViewById(R.id.information);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.name.setText(listdata.get(position).getAccount_name()+"  " +listdata.get(position).getAccount_job());
        holder.hospital.setText(listdata.get(position).getAccount_hospital());
        holder.information.setText(listdata.get(position).getAccount_info());
        return convertView;
    }

    private class ViewHolder {
        private ImageView avatar;
        private TextView hospital;
        private TextView name;
        private TextView information;
    }
}
