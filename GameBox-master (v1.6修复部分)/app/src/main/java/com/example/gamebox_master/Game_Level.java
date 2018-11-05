package com.example.gamebox_master;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import com.example.gamebox_master.View.GameLevels;

/**
 * Created by Jacky on 2018/10/24.
 */

public class Game_Level extends AppCompatActivity {


    String[] levellist = new String[]{"第1关","第2关","第3关","第4关"};
    private GridView gv_levels;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_level);
//        setContentView(R.layout.game_levels_item_listview);
        gv_levels = (GridView) findViewById(R.id.gv_levels);
        ArrayAdapter<String> arrayAdapter =  new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, GameLevels.getLevelList());
        gv_levels.setAdapter(arrayAdapter);




       gv_levels.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
               Intent intent = new Intent(Game_Level.this,GameActivity.class);
               intent.putExtra("Selected_Level",i+1);
               startActivity(intent);
           }
       });
    }
}
