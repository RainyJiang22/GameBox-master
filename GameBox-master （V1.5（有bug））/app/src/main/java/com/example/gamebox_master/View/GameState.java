package com.example.gamebox_master.View;

/**
 * Created by Jacky on 2018/11/4.
 */


/**
 * 存储游戏局面的GameState类
 */

public class GameState {

    private int mManRow;    //记住搬砖工所在单元格的行号
    private int mMancolumn; //记住搬砖工所在单元格的列号

    private StringBuffer[] mLabelInCells; //用StringBuffer数组标识局面的二维数组
    //mLabelInCells是数组名字，数组中的一个元素对应二维矩阵的一行
    public GameState(String[] initialState){
        mLabelInCells =  new StringBuffer[initialState.length];
        for(int i  = 0;i<initialState.length;i++)
            mLabelInCells[i] = new StringBuffer(initialState[i]);
    }

    //getLabelInCells返回表示局面的二维矩阵
    public StringBuffer[] getmLabelInCells(){
        return mLabelInCells;
    }

}
