package yescorp.com.tuixiangzi;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

/**
 * Created by 612226 on 2016/6/25.
 */
public class XuanGuanQiaAdapter extends ArrayAdapter<Boolean> {
    private Context mContext;
    private String[] mGuanQiaList;
    private Boolean[] mGameLevels_PassedOrNot;  //每一个关卡都对应一个元素，若已经过关，则值为true，否则值为false

//    public XuanGuanQiaAdapter(Context context, int resource) {
//        super(context, resource);
//    }
//
//    public XuanGuanQiaAdapter(Context context, int resource, int textViewResourceId) {
//        super(context, resource, textViewResourceId);
//    }

    public XuanGuanQiaAdapter(Context context, int resource, Boolean[] guanQiaList) {
        super(context, resource, guanQiaList);
        mContext = context;
        mGameLevels_PassedOrNot = guanQiaList;
    }

    @Override
    public Boolean getItem(int position) {
        return mGameLevels_PassedOrNot[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView tvGQ = null;
        if (convertView == null)
            tvGQ = new TextView(mContext);
        else
            tvGQ = (TextView) convertView;
        int level = position + 1;
        tvGQ.setText("第" + level + "关");
        tvGQ.setGravity(Gravity.CENTER);
        if (mGameLevels_PassedOrNot[position])
            tvGQ.setBackgroundResource(R.drawable.gv_passed_guanqia_item_tv_border);
        else
            tvGQ.setBackgroundResource(R.drawable.gv_guanqia_item_tv_border);
        tvGQ.setPadding(10, 10, 10, 10);
        tvGQ.setTextSize(18f);
        return  tvGQ;
    }


}
