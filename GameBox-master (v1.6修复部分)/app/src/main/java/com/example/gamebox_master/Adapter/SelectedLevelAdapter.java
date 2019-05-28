package com.example.gamebox_master.Adapter;

import android.annotation.TargetApi;
import android.content.ContentProvider;
import android.content.Context;

import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.gamebox_master.R;

/**
 * Created by Jacky on 2018/11/5.
 */

public class SelectedLevelAdapter extends ArrayAdapter<Boolean> {

    private Context mContext;
    private  String[] levelList;
    private Boolean[] mLevelList_PressedorNot;

    public SelectedLevelAdapter( Context context,int resource, int textViewResourceId) {
        super(context, resource, textViewResourceId);
    }

    public SelectedLevelAdapter(Context context,int resource,Boolean[] levelList){
        super(context,resource,levelList);
        mContext = context;
        mLevelList_PressedorNot = levelList;

    }

    @Override
    public Boolean getItem(int position) {
        return mLevelList_PressedorNot[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

   @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

       TextView tvlevel = null;
       if (convertView == null)
           tvlevel = new TextView(mContext);
       else
           tvlevel = (TextView) convertView;
       int level = position + 1;
       tvlevel.setText("第"+ level + "关");
       tvlevel.setGravity(Gravity.CENTER);
       if (mLevelList_PressedorNot[position])
           tvlevel.setBackgroundResource(R.drawable.gv_passed_guanqia_item_tv_border);
       else
           tvlevel.setBackgroundResource(R.drawable.gv_guanqia_item_tv_border);

       tvlevel.setPadding(10,10,10,10);
       tvlevel.setTextSize(18f);
       return tvlevel;
    }
}
