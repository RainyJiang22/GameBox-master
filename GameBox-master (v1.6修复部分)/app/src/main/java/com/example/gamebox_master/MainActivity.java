package com.example.gamebox_master;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_main);

        Button btnGameIntro = (Button) findViewById(R.id.btn_game_intro);
      //  btnGameIntro.setOnClickListener(new BtnGameIntro_ClickListener());

        Button btnGameStart = (Button) findViewById(R.id.btn_start_game);
//        btnGameStart.setOnClickListener(new BtnStart_ClickListener());

        Button btnExit = (Button) findViewById(R.id.btn_exit);
  //      btnExit.setOnClickListener(new BtnExit_ClickListener());
    }
}
