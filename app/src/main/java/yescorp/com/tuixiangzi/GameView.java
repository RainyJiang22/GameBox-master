package yescorp.com.tuixiangzi;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.io.IOException;


public class GameView extends View {

    private GameActivity mGameActivity;
    private float mColumnWidth;
    private float mRowHeight;
    private GameData mGameData;
    private int mGameLevel;
    private int mTopLeft_x = 0;
    private int mTopLeft_y = 0;
    private Rect mManRect = new Rect();          //搬运工所在的位置
//    private Rect mRectBtnNextLevel = new Rect();
//    private Rect mRectBtnReset = new Rect();
//    private Rect mRectBtnExit = new Rect();
//    private Rect mRectBtnPrvLevel = new Rect();
    private Rect mRectSoundSwitch = new Rect();
//    private boolean mSoundAllowed = true;

    public GameView(Context context) {
        super(context);
        init((GameActivity) context);
    }

    private void init(GameActivity context) {
        mGameActivity = context;
        mGameLevel = mGameActivity.getGameLevel();
        setFocusable(true);
        setFocusableInTouchMode(true);
        try {
            mGameData = new GameData(getResources(), mGameLevel);
        } catch (IOException e) {
            Toast.makeText(mGameActivity, "无法打开或读取配置文件。程序退出。", Toast.LENGTH_LONG).show();
            System.exit(-1);
        }
    }

    //为使用布局文件而设置的构造函数
    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init((GameActivity)context);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mColumnWidth = (float)w / mGameData.getBoardColumnNum();
        mRowHeight = (float)w / mGameData.getBoardRowNum();
        mTopLeft_y = (h - w) / 2;
        getManRect(mGameData.getmManPostion(), mRowHeight, mColumnWidth);
        super.onSizeChanged(w, h, oldw, oldh);
    }


    private void getManRect(TCell tCell, float rowHeight,float columnWidth ) {
        int left = (int)(mTopLeft_x + tCell.column * columnWidth);
        int top = (int)(mTopLeft_y + tCell.row * rowHeight);
        int right = (int)(left + columnWidth);
        int bottom = (int)(top + rowHeight);
        mManRect.set(left, top, right, bottom);
    }

    private void goToLevel(int level){
        mGameLevel = level;
        try {
            mGameData = new GameData(getResources(), mGameLevel);
        } catch (IOException e) {
            e.printStackTrace();
        }

        mColumnWidth = getWidth() / mGameData.getBoardColumnNum();
        mRowHeight = getWidth() / mGameData.getBoardRowNum();   //正方形区域
        getManRect(mGameData.getmManPostion(), mRowHeight, mColumnWidth);
        invalidate();
    }

    public void resetGame(){
        goToLevel(mGameLevel);
    }

    public void gotoNextLevel() {
        if (mGameLevel < GameInitialData.GameLevels.size())  //mGameLevel从1开始计数
            goToLevel(mGameLevel + 1);
        else
            Toast.makeText(mGameActivity, R.string.no_more_levels, Toast.LENGTH_LONG).show();
    }

    public void gotoPrvLevel(){
        if (mGameLevel > 1)
            goToLevel(mGameLevel - 1);
        else
            Toast.makeText(mGameActivity, R.string.already_first_level, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //背景色
        Paint background = new Paint();
        background.setColor(getResources().getColor(R.color.board_background));

        canvas.drawRect(0, 0, getWidth(), getHeight(), background);
        //游戏区域
        drawGameBoard(canvas);
        //音效开关
        drawSoundSwitch(canvas);

        //成功过关
        if (mGameData.isGameOver()) {
            drawDoneLabel(canvas);
        }

        //drawButtons(canvas);
    }


    private void drawGameBoard(Canvas canvas) {
        Rect destRect = new Rect();
        for (int r = 0; r < mGameData.getBoardRowNum(); r++ )
            for (int c = 0; c < mGameData.getBoardColumnNum(); c++){
                int topleft_x = (int)(mTopLeft_x + c * mColumnWidth);
                int topleft_y = (int)(mTopLeft_y + r * mRowHeight);
                destRect.set(topleft_x, topleft_y,(int)(topleft_x + mColumnWidth) + 2, (int)(topleft_y + mRowHeight) + 2);//+2是为了去除墙体之间的缝隙
                if (mGameData.hasFlag(r, c))
                    canvas.drawBitmap(GameBitmaps.mFlagBitmap, null, destRect, null);
                StringBuffer []gameState = mGameData.getGameState();
                switch (gameState[r].charAt(c)){
                    case GameInitialData.BOX:
                        canvas.drawBitmap(GameBitmaps.mBoxBitmap, null, destRect, null);
                        break;
                    case GameInitialData.FLAG:
                        canvas.drawBitmap(GameBitmaps.mFlagBitmap, null, destRect, null);
                        break;
                    case GameInitialData.NOTHING:
                        break;
                    case GameInitialData.MAN:
                        canvas.drawBitmap(GameBitmaps.mManBitmap, null, destRect, null);
                        break;
                    case GameInitialData.WALL:
//                        destRect.set(destRect.left, destRect.top, destRect.right+2, destRect.bottom + 2);  //+2是为了去除墙体之间的缝隙
                        canvas.drawBitmap(GameBitmaps.mWallBitmap, null, destRect, null);
                        break;
                    case GameInitialData.BOX_FLAG:
                        canvas.drawBitmap(GameBitmaps.mFlagBitmap, null, destRect, null);
                        canvas.drawBitmap(GameBitmaps.mBoxBitmap, null, destRect, null);
                        break;
                    case GameInitialData.MAN_FLAG:
                        canvas.drawBitmap(GameBitmaps.mFlagBitmap, null, destRect, null);
                        canvas.drawBitmap(GameBitmaps.mManBitmap, null, destRect, null);
                        break;
                }
            }
    }

    private void drawSoundSwitch(Canvas canvas) {
        mRectSoundSwitch.set(canvas.getWidth() - 2 * (int)mColumnWidth, 0, canvas.getWidth(), 2 * (int)mColumnWidth);
        if (GameSound.isSoundAllowed())
            canvas.drawBitmap(GameBitmaps.mSoundOpenBitmap, null, mRectSoundSwitch, null);
        else
            canvas.drawBitmap(GameBitmaps.mSoundCloseBitmap, null, mRectSoundSwitch, null);
    }

    private void drawDoneLabel(Canvas canvas) {
        int begin_x = mTopLeft_x + 120;
        int begin_y = mTopLeft_y + 120;
        int end_x = canvas.getWidth() - 120;
        int end_y = begin_y + (end_x - begin_x);
        Rect label_rect = new Rect(begin_x, begin_y, end_x, end_y);
        Paint paint = new Paint();
        paint.setAlpha(125);
        canvas.drawBitmap(GameBitmaps.mDoneBitmap, null, label_rect, paint);
        canvas.drawBitmap(GameBitmaps.mBoxBitmap,null,label_rect,paint);
    }

    private void drawButtons(Canvas canvas){
        final int BOTTOM_MARGIN = canvas.getHeight() / 20; //离屏幕低端的距离
        final int LEFT_MARGIN = canvas.getWidth() * 2 / 5 / 5; //分离空间占2/5;
        final int RIGHT_MARGIN = LEFT_MARGIN;
        final int BUTTON_INTERVAL = LEFT_MARGIN;
        final int BUTTON_WIDTH = canvas.getWidth() * 3 / (5 * 4); //按钮占3/5;

    }

/*
    private void drawButtons(Canvas canvas) {
        final int BOTTOM_MARGIN = canvas.getHeight() / 20;  //离屏幕底端的距离
        final int LEFT_MARGIN = canvas.getWidth() * 2 / 5 / 5;  //分隔空间占2/5
        final int RIGHT_MARGIN = LEFT_MARGIN;
        final int BUTTON_INTERVAL = LEFT_MARGIN;
        final int BUTTON_WIDTH = canvas.getWidth() * 3 / (5 * 4);    //按钮占3/5
        int Button_Height = BUTTON_WIDTH * GameBitmaps.mBtnNextBitmap.getHeight() / GameBitmaps.mBtnNextBitmap.getWidth();
        int button_y = canvas.getHeight() - BOTTOM_MARGIN - Button_Height;
        int buttion_1_x = LEFT_MARGIN;
        mRectBtnPrvLevel.set(buttion_1_x, button_y, buttion_1_x + BUTTON_WIDTH, button_y + Button_Height);
        canvas.drawBitmap(GameBitmaps.mBtnPrevBitmap, null, mRectBtnPrvLevel, null);
        mRectBtnNextLevel.set(buttion_1_x, button_y, buttion_1_x + BUTTON_WIDTH, button_y + Button_Height);
        int button_2_x = buttion_1_x + BUTTON_WIDTH + BUTTON_INTERVAL;
        mRectBtnNextLevel.set(button_2_x, button_y, button_2_x + BUTTON_WIDTH, button_y + Button_Height);
        canvas.drawBitmap(GameBitmaps.mBtnNextBitmap, null, mRectBtnNextLevel, null);
        int button_3_x = button_2_x + BUTTON_WIDTH + BUTTON_INTERVAL;
        mRectBtnReset.set(button_3_x, button_y, button_3_x + BUTTON_WIDTH, button_y + Button_Height);
        canvas.drawBitmap(GameBitmaps.mBtnResetBitmap, null, mRectBtnReset, null);
        int button_4_x = button_3_x + BUTTON_WIDTH + BUTTON_INTERVAL;
        mRectBtnExit.set(button_4_x, button_y, button_4_x + BUTTON_WIDTH, button_y + Button_Height);
        canvas.drawBitmap(GameBitmaps.mBtnExitBitmap, null, mRectBtnExit, null);
    }
*/


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() != MotionEvent.ACTION_DOWN)
            return super.onTouchEvent(event);

        int touch_x = (int) event.getX();
        int touch_y = (int) event.getY();
        if (!mGameData.isGameOver()) {
            //用户通过在游戏区域触摸来控制搬运工的行进
            //当触摸点落在搬运工所在单元格的上、下、左、右格子n时，即意味着指示搬运工走到格子n上（阻挡问题另外考虑）
            if (touch_left_to_man(touch_x, touch_y))
                mGameData.goLeft();
            if (touch_right_to_man(touch_x, touch_y))
                mGameData.goRight();
            if (touch_above_to_man(touch_x, touch_y))
                mGameData.goUp();
            if (touch_blow_to_man(touch_x, touch_y))
                mGameData.goDown();
            getManRect(mGameData.getmManPostion(), mRowHeight, mColumnWidth);  //重新计算搬运工的屏幕位置
            if (mRectSoundSwitch.contains(touch_x, touch_y))
                GameSound.switchSoundAllowed();
            invalidate();

            if (mGameData.isGameOver()){
                PrfsManager.setPassedLevel(mGameActivity, mGameLevel);   //记住已经通过本关卡
                if (GameSound.isSoundAllowed()) GameSound.playGameOverSound(mGameActivity.getAssets());
            }
        }


        //pressButton(touch_x, touch_y);

        return true;
    }
/*    private void pressButton(int touch_x, int touch_y) {
        if (mRectBtnPrvLevel.contains(touch_x, touch_y))
            gotoPrvLevel();

        if (mRectBtnNextLevel.contains(touch_x, touch_y)){
            gotoNextLevel();
        }

        if (mRectBtnReset.contains(touch_x, touch_y)){
            goToLevel(mGameLevel);
        }

        if (mRectBtnExit.contains(touch_x, touch_y)){
            Intent startMain = new Intent(Intent.ACTION_MAIN);
            startMain.addCategory(Intent.CATEGORY_HOME);
            startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mGameActivity.startActivity(startMain);
            System.exit(0);
        }
    }*/


    private boolean touch_blow_to_man(int touch_x, int touch_y) {
        Rect belowRect = new Rect(mManRect.left, mManRect.top + (int)mRowHeight, mManRect.right, mManRect.bottom + (int)mRowHeight);
        return belowRect.contains(touch_x, touch_y);
    }

    private boolean touch_above_to_man(int touch_x, int touch_y) {
        Rect aboveRect = new Rect(mManRect.left, mManRect.top - (int)mRowHeight, mManRect.right, mManRect.bottom - (int)mRowHeight);
        return aboveRect.contains(touch_x, touch_y);
    }

    private boolean touch_right_to_man(int touch_x, int touch_y) {
        Rect rightRect = new Rect(mManRect.left + (int)mColumnWidth, mManRect.top, mManRect.right + (int)mColumnWidth, mManRect.bottom);
        return rightRect.contains(touch_x, touch_y);
    }

    private boolean touch_left_to_man(int touch_x, int touch_y) {
        Rect leftRect = new Rect(mManRect.left - (int)mColumnWidth, mManRect.top, mManRect.right - (int)mColumnWidth, mManRect.bottom);
        return leftRect.contains(touch_x, touch_y);
    }

    public void undoMove(){
        if (mGameData.undoMove()) {
            invalidate();
            getManRect(mGameData.getmManPostion(), mRowHeight, mColumnWidth);  //重新计算搬运工的位置
        }
    }
}
