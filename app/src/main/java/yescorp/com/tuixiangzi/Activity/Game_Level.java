package yescorp.com.tuixiangzi.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import yescorp.com.tuixiangzi.R;

/**
 * Created by Jacky on 2018/10/15.
 */

public class Game_Level extends AppCompatActivity {

    //定义GrdeView控件
    private GridView gridView;

    //定义设置关卡数组
    String levellist[] = new String[]{"第一关","第二关","第三关","第四关"};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_level);
        //初始化GrideView控件
        gridView = (GridView) findViewById(R.id.game_level);

        /***
         * ArrayAdapter<String>是一个模板类，String作为一个模板参数
         *
         * arrayAdapter:用作变量，是对ArrayAdapter的使用
         */

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,R.layout.levels_item_textview,levellist);


        //设置适配器
        gridView.setAdapter(arrayAdapter);


        //设置匿名监听事件 ，调用AdapterView.OnitemONclickeListener接口
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Game_Level.this,Game_Start.class);
                startActivity(intent);
            }
        });
    }
}
