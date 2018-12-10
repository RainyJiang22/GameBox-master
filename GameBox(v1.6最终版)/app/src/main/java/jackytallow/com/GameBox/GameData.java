package jackytallow.com.GameBox;

import android.content.res.Resources;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class GameData {
//    private static GameInitialData mAllLevelsInitialData;         //所有关卡的数据
    private int mSelectedLevel;
    private int mRowNum;
    private int mColumnNum;
    private StringBuffer[] mGameState;
    private TCell mManPostion = new TCell();
    private LevelInitialData mSelectedInitialData;    //当前所选的关卡的初始数据，与mSelectedLevel对应
    private List<TCell> mFlagCells = new ArrayList<>();             //记住所有红旗所在的位置
    private List<GameStepData> mGameSteps = new ArrayList<>();      //记住人走过的每一步（及其箱子的每一次移动）。用以支持“悔一步”操作

    public GameData(Resources res, int level) throws IOException {
        if (GameInitialData.GameLevels.size() == 0)
            //GameInitialData.addInitGameData();
            GameInitialData.readInitialData(res, GameInitialData.CONFIG_FILE_NAME);
        mSelectedLevel = level;   //level从1开始计数
        mSelectedInitialData = GameInitialData.GameLevels.get(level - 1);
        initializeGameState();
    }

    private void initializeGameState() {
        mRowNum = mSelectedInitialData.mRowNum;
        mColumnNum = mSelectedInitialData.mColumnNum;
        if (mRowNum < GameInitialData.DEFAULT_ROW_NUM)
            mGameState = new StringBuffer[GameInitialData.DEFAULT_ROW_NUM];  //尾部将添加若干空行
        else
            mGameState = new StringBuffer[mRowNum];

        StringBuffer leftBlanks = new StringBuffer("");
        StringBuffer rightBlanks = new StringBuffer("");
        //游戏区域不足11列，左右两边加上若干空白列，凑足11列
        if (mColumnNum < GameInitialData.DEFAULT_COLUMN_NUM){
            int leftBlankCnt = (GameInitialData.DEFAULT_COLUMN_NUM - mColumnNum) / 2;
            for (int i = 0; i < leftBlankCnt; i++ )
                leftBlanks.append(" ");
            for (int i = 0; i < GameInitialData.DEFAULT_COLUMN_NUM - mColumnNum - leftBlankCnt; i++)
                rightBlanks.append(" ");
            mColumnNum = GameInitialData.DEFAULT_COLUMN_NUM;
        }

        for (int r = 0; r < mRowNum; r++) {
            mGameState[r] = new StringBuffer(leftBlanks);
            mGameState[r].append(mSelectedInitialData.mInitialState[r]);
            mGameState[r].append(rightBlanks);
            //Log.d("GameData", "initializeGameState(), mGameState[" + r + "].length=" + mGameState[r].length());
            for (int c = 0; c < mColumnNum; c++) {
                if (mGameState[r].charAt(c) == GameInitialData.MAN || mGameState[r].charAt(c) == GameInitialData.MAN_FLAG) {
                    mManPostion.set(r, c);
                }
                if (mGameState[r].charAt(c) == GameInitialData.FLAG || mGameState[r].charAt(c) == GameInitialData.MAN_FLAG
                        || mGameState[r].charAt(c) == GameInitialData.BOX_FLAG){
                    TCell cell = new TCell(r, c);
                    mFlagCells.add(cell);
                }
            }
        }
        //行数不足11行，使得墙体图片看起来偏大，故添加若干空行
        if (mRowNum < GameInitialData.DEFAULT_ROW_NUM){
            for (int i = mRowNum; i < GameInitialData.DEFAULT_ROW_NUM; i++) {
                StringBuffer blankLine = new StringBuffer();
                for (int c = 0; c < mColumnNum; c++)
                    blankLine.append(" ");
                mGameState[i] = blankLine;
            }
            mRowNum = GameInitialData.DEFAULT_ROW_NUM;
        }
    }

    public StringBuffer[] getGameState() {
        return mGameState;
    }

    public TCell getmManPostion(){
        return  mManPostion;
    }

    public int getBoardColumnNum(){
        return mColumnNum;
    }

    public int getBoardRowNum(){
        return mRowNum;
    }

    public void goUp() {
        go(mManPostion.row, mManPostion.column, mManPostion.row - 1, mManPostion.column);
    }

    //搬运工从(srcRow, srcColumn)移动到(destRow, destColumn)
    private void go(int srcRow, int srcColumn, int destRow, int destColumn){
        if (destRow < 0 || destRow >= mRowNum || destColumn < 0 || destColumn >= mColumnNum)
            return;   //越界
//        mCurrentStep = null;
        boolean isBoxMoved = false;
        int rowOffset = destRow - srcRow;
        int columnOffset = destColumn - srcColumn;
        char cell = mGameState[destRow].charAt(destColumn);
        if (cell == GameInitialData.BOX) {
            isBoxMoved = moveBox(destRow, destColumn, destRow + rowOffset, destColumn + columnOffset);
            cell = mGameState[destRow].charAt(destColumn);
        }

        if (cell == GameInitialData.NOTHING || cell == GameInitialData.FLAG){
            manGoAway();
            mManPostion.row = destRow;
            mManPostion.column = destColumn;
            mGameState[mManPostion.row].setCharAt(mManPostion.column, GameInitialData.MAN);

            recordMoveInfo(srcRow, srcColumn, destRow, destColumn, isBoxMoved);
        }
    }

    public void goDown() {
        go(mManPostion.row, mManPostion.column, mManPostion.row + 1, mManPostion.column);
    }

    public void goRight() {
        go(mManPostion.row, mManPostion.column, mManPostion.row, mManPostion.column + 1);
    }

    public void goLeft() {
        go(mManPostion.row, mManPostion.column, mManPostion.row, mManPostion.column - 1);
    }

    private void recordMoveInfo(int srcRow, int srcColumn, int destRow, int destColumn, boolean isBoxMoved) {
        GameStepData stepData = new GameStepData();
        stepData.setManPrvPosition(new TCell(srcRow, srcColumn));
        stepData.setManCurrentPosition(new TCell(destRow, destColumn));
        if (isBoxMoved){
            int rowOffset = destRow - srcRow;
            int columnOffset = destColumn - srcColumn;
            stepData.setBoxPrvPosition(new TCell(destRow, destColumn));
            stepData.setBoxCurrentPosition(new TCell(destRow + rowOffset, destColumn + columnOffset));
        }
        mGameSteps.add(stepData);
//        logOneStep(stepData);
    }

    private void restoreInitialState(int row, int column) {
        if (hasFlag(row, column))
            mGameState[row].setCharAt(column, GameInitialData.FLAG);
        else
            mGameState[row].setCharAt(column, GameInitialData.NOTHING);
    }

    private void manGoAway() {
        restoreInitialState(mManPostion.row, mManPostion.column);
        if (GameSound.isSoundAllowed()) GameSound.playOneStepSound();
    }

    //把箱子从(srcRow, srcColumn)移动到(destRow, destColumn)
    private boolean moveBox(int srcRow, int srcColumn, int destRow, int destColumn){
        if (destRow < 0 || destRow >= mRowNum || destColumn < 0 || destColumn >= mColumnNum)
            return false;
        char cell = mGameState[destRow].charAt(destColumn);
        if (cell  == GameInitialData.NOTHING || cell == GameInitialData.FLAG){
            restoreInitialState(srcRow, srcColumn);
            mGameState[destRow].setCharAt(destColumn, GameInitialData.BOX);
            if (GameSound.isSoundAllowed()) GameSound.playMoveBoxSound();
            return true;
        }
        return false;
    }

    //据所选关卡的初始数据处获取单元格(row, column)是否有红旗
    public boolean hasFlag(int row, int column) {
        for (int i = 0; i < mFlagCells.size(); i++) {
            TCell cell = mFlagCells.get(i);
            if (cell.row == row && cell.column == column)
                return true;
        }
        return  false;
    }

    //所有箱子到达目的地了么？是的话，返回true, 否则返回false。
    public boolean isGameOver() {
        for (int i = 0; i < mFlagCells.size(); i++){
            TCell cell = mFlagCells.get(i);
//            Log.d("GameData", "isGameOver(), Flag " + i + "=(" + cell.row + ", " + cell.column + ")");
            if (mGameState[cell.row].charAt(cell.column) != 'B')
                return false;
        }
        return true;
    }

    public boolean undoMove(){
        if (mGameSteps.isEmpty())
            return false;
        GameStepData step = mGameSteps.remove(mGameSteps.size() - 1);
//        logUndoOneStep(step);
        assert(mManPostion.isEqualTo(step.getManCurrentPosition()));
        restoreInitialState(step.getManCurrentPosition().row, step.getManCurrentPosition().column);
        int manRow = step.getManPrvPosition().row;
        int manColumn = step.getManPrvPosition().column;
        mManPostion.set(manRow, manColumn);
        mGameState[manRow].setCharAt(manColumn, 'M');
        TCell boxPrvPos = step.getBoxPrvPosition();
        TCell boxCurPos = step.getBoxCurrentPosition();
        if (boxPrvPos != null && boxCurPos != null){
            //assert mGameState[boxCurPos.row].charAt(boxCurPos.column) == 'B';
            restoreInitialState(boxCurPos.row, boxCurPos.column);
            mGameState[boxPrvPos.row].setCharAt(boxPrvPos.column, 'B');
        }
        return true;
    }

    private void logUndoOneStep(GameStepData step) {
        logOneStep(step.getManCurrentPosition(), step.getManPrvPosition(), step.getBoxCurrentPosition(), step.getBoxPrvPosition());
    }

    private void logOneStep(GameStepData step) {
        TCell manPrvPos = step.getManPrvPosition();
        TCell manCurPos = step.getManCurrentPosition();
        TCell boxPrvPos = step.getBoxPrvPosition();
        TCell boxCurPos = step.getBoxCurrentPosition();
        logOneStep(manPrvPos, manCurPos, boxPrvPos, boxCurPos);
    }

    private void logOneStep(TCell manPrvPos, TCell manCurPos, TCell boxPrvPos, TCell boxCurPos) {
        Log.d("GameData", "一步：(" + manPrvPos.row + ", " + manPrvPos.column + ") -> (" + manCurPos.row + ", " + manCurPos.column + ")" );
        if (boxPrvPos != null && boxCurPos != null) {
            Log.d("GameData", "箱子：(" + boxPrvPos.row + ", " + boxPrvPos.column + ") -> (" + boxCurPos.row + ", " + boxCurPos.column + ")" );
        }
    }
}
