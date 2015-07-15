package com.example.xiachanjin.test;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by xiachanjin on 15-7-14.
 */

class MyListAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<HashMap<String, Object>> mList;
    private LayoutInflater mLayoutInflater;

    public final class ViewHolder {
        public TextView title;
        public TextView name;
        public Button btn;
        private ImageView imageView;
    }

    public MyListAdapter(Context ctx, ArrayList<HashMap<String, Object>> arrayList) {
        mContext = ctx;
        mList = arrayList;
        mLayoutInflater = LayoutInflater.from(ctx);
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        Log.v("MyListViewBase", "getView " + position + " " + convertView);
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.custom_list, null);
            holder = new ViewHolder();
            holder.title = (TextView) convertView.findViewById(R.id.title);
            holder.name = (TextView) convertView.findViewById(R.id.name);
            holder.btn = (Button) convertView.findViewById(R.id.button);
            holder.imageView = (ImageView) convertView.findViewById(R.id.imageview);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.title.setText(mList.get(position).get("title").toString());
        holder.name.setText(mList.get(position).get("name").toString());
        holder.imageView.setImageResource((int)mList.get(position).get("image"));
        holder.btn.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View v) {
                                              Log.d(MainActivity.TAG, "button clicked!");
                                          }
                                      }
        );

        return convertView;
    }
}
