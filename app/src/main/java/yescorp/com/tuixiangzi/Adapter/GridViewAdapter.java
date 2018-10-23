package yescorp.com.tuixiangzi.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * Created by Jacky on 2018/10/15.
 */

public class GridViewAdapter extends BaseAdapter {
    private Context context;
    private String[] levelist;

    @Override
    public int getCount() {
        return levelist.length;
    }

    @Override
    public Object getItem(int position) {
        return levelist[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
