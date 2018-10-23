package yescorp.com.tuixiangzi.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import yescorp.com.tuixiangzi.R;

/**
 * Created by Jacky on 2018/10/15.
 */

public class Game_Main  extends AppCompatActivity{
    //定义一个button的游戏简介变量btnGameIntroduction
    private Button btnGameIntroduction;

    //定义开始游戏按钮
    private Button btnGameStart;

    //定义游戏结束按钮
    private Button btnGameExit;


    //1.第一步，先设置按钮监听，获取游戏简介按钮控件
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //初始化btnGameIntroduction控件
        btnGameIntroduction = (Button) findViewById(R.id.game_introduction);
        //设置按钮点击匿名类事件,设置监听器
        btnGameIntroduction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //判断一下是否点击成功了游戏简介按钮
//                Toast.makeText(Game_Main.this,"点击了游戏简介",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Game_Main.this,Game_Introduction.class);
                startActivity(intent);
            }
        });

        //初始化开始游戏按钮
        btnGameStart = (Button) findViewById(R.id.game_start);
        btnGameStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //判断是否点击了游戏开始按钮
            //    Toast.makeText(Game_Main.this,"点击了游戏开始",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Game_Main.this,Game_Level.class);
                startActivity(intent);
            }

        });

        //初始化游戏结束按钮
        btnGameExit = (Button) findViewById(R.id.game_exit);
        btnGameExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //直接结束活动
                finish();
            }
        });
    }
}
