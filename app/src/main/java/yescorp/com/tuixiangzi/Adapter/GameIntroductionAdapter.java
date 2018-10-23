package yescorp.com.tuixiangzi.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by Jacky on 2018/10/15.
 */

/**
 * 自定义GameIntroductionAdapter，继承ArrayAdapter
 */
public class GameIntroductionAdapter extends ArrayAdapter<String>{

    private String[] introductionStatements;
    private Context mContext;


    public GameIntroductionAdapter( Context context, int resource, String[] statements) {
        super(context, resource, statements);
        introductionStatements = statements;
        mContext  = context;
    }


    @Override
    public int getCount() {
        return introductionStatements.length;
    }

    @Override
    public String getItem(int position) {
        return introductionStatements[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        TextView test;
        if (convertView == null)
            test = new TextView(mContext);
        else
            test = (TextView) convertView;

        /**
         * 这里判断位置是否是偶数
         * 偶数的时候是问题，用黑色字体
         * 奇数的时候是回答，用红色字体
         */
        if(position % 2 == 0)
            test.setTextColor(Color.BLACK);
        else
            test.setTextColor(Color.RED);

        test.setText(introductionStatements[position]);

        return test;
    }
}
