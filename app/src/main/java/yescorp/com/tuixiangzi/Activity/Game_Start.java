package yescorp.com.tuixiangzi.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import yescorp.com.tuixiangzi.R;

/**
 * Created by Jacky on 2018/10/15.
 */

public class Game_Start extends AppCompatActivity {

    //定义常量
    public static String KEY_SELECTED_LEVEL = "Selected_Level";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_start);


        //选中关卡，并显示出来
        int selected_level = getIntent().getIntExtra(KEY_SELECTED_LEVEL,1);
        TextView tvLevel = (TextView) findViewById(R.id.tv_level);
        tvLevel.setText(getResources().getString(R.string.what_you_select)+ "第"+ selected_level + "关");
    }
}
