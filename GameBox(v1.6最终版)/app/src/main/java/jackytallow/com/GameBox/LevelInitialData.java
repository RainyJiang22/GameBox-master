package jackytallow.com.GameBox;


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
