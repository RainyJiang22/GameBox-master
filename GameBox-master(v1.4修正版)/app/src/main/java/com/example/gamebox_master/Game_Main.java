package com.example.gamebox_master;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by Jacky on 2018/10/24.
 */

public class Game_Main extends AppCompatActivity {

   //定义游戏简介按钮
    private Button game_intro;

    //定义开始游戏按钮
    private Button game_start;

   //定义游戏结束按钮
     private Button game_exit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_main);


        game_start = (Button) findViewById(R.id.game_start);
        game_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Game_Main.this,Game_Level.class);
                startActivity(intent);
            }
        });

        game_intro  = (Button) findViewById(R.id.game_introduction);
        game_intro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Game_Main.this,Game_Introduction.class);
                startActivity(intent);
            }
        });


        game_exit = (Button) findViewById(R.id.game_exit);
        game_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //直接结束活动
                finish();
            }
        });
    }
}
