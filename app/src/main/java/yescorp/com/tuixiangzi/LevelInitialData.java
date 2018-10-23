package yescorp.com.tuixiangzi;

/**
 * Created by 612226 on 2016/7/2.
 */
public class LevelInitialData {
    int mRowNum;
    int mColumnNum;

    String [] mInitialState;

    public LevelInitialData(int rowNum, int columnNum){
        mRowNum= rowNum;
        mColumnNum = columnNum;
        mInitialState = new String[rowNum];
    }

    public LevelInitialData(int rowNum, int columnNum, String[] initialState){
        mRowNum = rowNum;
        mColumnNum = columnNum;
        mInitialState = initialState;
    }
}
