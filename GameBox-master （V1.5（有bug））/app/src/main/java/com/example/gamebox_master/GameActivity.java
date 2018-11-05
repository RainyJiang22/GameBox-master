package com.example.gamebox_master;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.gamebox_master.View.GameLevels;
import com.example.gamebox_master.View.GameState;
import com.example.gamebox_master.View.GameView;

/**
 * Created by Jacky on 2018/10/25.
 */

public class GameActivity extends AppCompatActivity {

    //定义一个关卡的常量
    public static final  String KEY_SELECTED_LEVEL = "Selected_Level";
    private GameState mCurrentState;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.game_start);


        int selected_level = getIntent().getIntExtra(KEY_SELECTED_LEVEL,1);
//        TextView tvLevel = (TextView) findViewById(R.id.tv_levels);
//        tvLevel.setText(getResources().getString(R.string.what_you_select)+ "第"+ selected_level + "关");
        mCurrentState = new GameState(GameLevels.getLevel(selected_level));


        GameView gameView =  new GameView(this);
        setContentView(gameView);




    }

    public GameState getCurrentState(){
        return mCurrentState;
    }
}
