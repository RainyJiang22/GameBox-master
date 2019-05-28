package com.example.gamebox_master.View;

/**
 * Created by Jacky on 2018/11/3.
 */


import java.util.ArrayList;
import java.util.List;

/**
 * 存储游戏关卡的开局
 */
public class GameLevels {

    public static final int DEFAULT_ROW_NUM = 12;
    public static final int DEFAULT_COLUMN_NUM = 12;
    public static ArrayList<String[]> OriginalLevels = new ArrayList<>();
    //存储多个开局的列表
    //loadGameLevels()的作用是加载关卡列表



    //游戏区单元格放了什么
    public static final char NOTHING = ' ';  //该单元格什么也没有
    public static final char BOX = 'B';             //该单元格放的是箱子
    public static final char FLAG = 'F';            //红旗，表示箱子的目的地
    public static final char MAN = 'M';              //搬运工
    public static final char WALL = 'W';             //墙
    public static final char MAN_FLAG = 'R';        //搬运工 + 红旗
    public static final char BOX_FLAG = 'X';        //箱子 + 红旗

    public static final String [] LEVEL_1 = {
            "  WWWW ",
            "  WF W ",
            "  WB W ",
            "  WM W ",
            "  WWWW ",
            "       ",
            "       "
    };
    public static final String [] LEVEL_2 = {
            "            ",
            "            ",
            "  WWWWWWW   ",
            "  W FFB W   ",
            "  W W B W   ",
            "  W W W W   ",
            "  W BMW W   ",
            "  WFB   W   ",
            "  WFWWWWW   ",
            "  WWW       ",
            "            ",
            "            "
    };


    //添加到开局列表中
    public static  void loadGameLevels(){
        if (OriginalLevels.isEmpty()){
            OriginalLevels.add(LEVEL_1);
            OriginalLevels.add(LEVEL_2);
        }
    }

    //getLevel()是根据关卡号level得到该关卡开始的局面
    public static String[] getLevel(int level){
        loadGameLevels();  //加载关卡列表
        return OriginalLevels.get(level - 1);
    }

    public static List<String> getLevelList(){
        loadGameLevels();
        List<String> levelList = new ArrayList<>();
        int levelNum = OriginalLevels.size();
        for (int i = 1;i<= levelNum;i++){
            levelList.add("第" + i + "关");
        }

        return levelList;  //返回关卡名列表
    }



}
