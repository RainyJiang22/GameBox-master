package com.example.gamebox_master.View;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.example.gamebox_master.R;

import static android.content.ContentValues.TAG;

/**
 * Created by Jacky on 2018/10/25.
 */

public class GameView  extends View{

    private float mCellWidth;
    public static final int CELL_NUM_PER_LINE = 12;
    //箱子一开始的区域位置
    private int mBoxRow = 5;
    private int mBoxColumn = 5;

//    private Bitmap ManBitmap = null;
    /**
     * 改成使用GameBitmaps图像类的图片对象
     */
    private int mManRow;
    private int mManColumn;


    public GameView(Context context) {
        super(context);
      //  ManBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.eggman_48x48);
       GameBitmaps.loadGameBitmaps(getResources());
        //加载图片，获取getResources资源管理器

        //释放图片资源
        GameBitmaps.releaseGameBitmaps();
    }


    //当GameView实例的尺寸发生变化，就会调用onSizeChanged

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mCellWidth = w / CELL_NUM_PER_LINE;
    }


    //绘制画面


    /**
     * 在Java代码中获取资源的方法：
     	获取res/values/colors.xml内名字为xxx的颜色值：
     getResources().getColor(R.color.xxx);
     	获取res/values/strings.xml内名字为yyy的字符串：
     getResources().getString(R.string.yyy);



     2.继续使用onDraw方法绘制背景色和网格线
     * @param canvas
     */



    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //背景色
        Paint background = new Paint();
        background.setColor(getResources().getColor(R.color.background));
        canvas.drawRect(0,0,getWidth(),getHeight(),background);


        //开始绘制游戏区域(首先绘制网格线)

        //首先创建画刷，画刷的颜色是黑色的
        Paint linePaint = new Paint();
        linePaint.setColor(Color.BLACK);
        //这里绘制12X12的游戏区域
        for(int r = 0;r<= CELL_NUM_PER_LINE;r++)
            canvas.drawLine(0,r * mCellWidth,getWidth(),r * mCellWidth,linePaint);
        for (int c = 0;c<= CELL_NUM_PER_LINE;c++)
            canvas.drawLine(c * mCellWidth,0,c * mCellWidth,CELL_NUM_PER_LINE * mCellWidth,linePaint);

        //绘制搬砖工

        /**
         * 这里可以使用Canvas类的drawBitmap方法来绘制图片：
         public void drawBitmap (Bitmap bitmap, Rect src, RectF dst, Paint paint)
         */
        Rect srcRect = new Rect(0, 0, GameBitmaps.ManBitmap.getWidth(), GameBitmaps.ManBitmap.getHeight());
     //   Rect destRect = new Rect(0, 0, (int)mCellWidth, (int)mCellWidth);
        Rect destRect = getRect(mManRow, mManColumn);
        canvas.drawBitmap(GameBitmaps.ManBitmap, srcRect, destRect, null);



        //绘制箱子
        Rect destRect1= getRect(mBoxRow,mBoxColumn);
        canvas.drawBitmap(GameBitmaps.BoxBitmap,srcRect,destRect1,null);
    }


    /**
     *使用onTouchEvent函数实现触摸事件
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() != MotionEvent.ACTION_DOWN)
            return true;

        int touch_x = (int) event.getX(); //触摸点的x坐标
        int touch_y = (int) event.getY(); //触摸点的y坐标



        //向上移动
        if (touch_up_to_man(touch_x,touch_y,mManRow,mManColumn)){


            if (isBoxupMan()){
                if (mManRow-1>0) { //防止越界
                    mBoxRow--;
                    mManRow--;
                }
                else if(mManRow-1>0){
                    mManRow--;
                }

            }

        }

        //Log.d(TAG, "onTouchEvent: UP ");

        //向下移动
        if (touch_blow_to_man(touch_x,touch_y,mManRow,mManColumn)){

            if (isBoxblowMan()){
                if (mManRow+1<CELL_NUM_PER_LINE){
                   mBoxRow++;
                    mManRow++;
                }
                else if(mManRow+1<CELL_NUM_PER_LINE){
                    mManRow++;
                }

            }

        }


       // Log.d(TAG, "onTouchEvent: DOWN");
        //向右移动
        if (touch_right_to_man(touch_x,touch_y,mManRow,mManColumn)){

            if (isBoxrightMan()){
                if (mManColumn+1<CELL_NUM_PER_LINE) {
                    mBoxColumn++;
                    mManColumn++;
                }
                else if(mManColumn+1<CELL_NUM_PER_LINE){
                    mManColumn++;
                }
            }
        }

      //  Log.d(TAG, "onTouchEvent: RIGHT");
       //向左移动
        if (touch_left_to_man(touch_x,touch_y,mManRow,mManColumn)){

            if (isBoxleftMan()){
                if (mManColumn-1>0) {
                    mBoxColumn--;
                    mManColumn--;
                }
                else if(mBoxColumn-1>0){
                    mManColumn--;
                }
            }

        }
    //    Log.d(TAG, "onTouchEvent: LEFT");

        postInvalidate();
        return true;
    }


    /**
     * 搬砖工向下移动
     */
    private boolean touch_blow_to_man(int touch_x,int touch_y,int manRow,int manColum){
        int belowRow = manRow + 1;
        Rect belowRect = getRect(belowRow,manColum);
        return belowRect.contains(touch_x,touch_y);
    }

    /**
     *搬砖工向右移动
     */
    private boolean touch_right_to_man(int touch_x,int touch_y,int manRow,int manColum){
        int rightColumn = manColum + 1;          //右侧单元格列号
        Rect rightRect = getRect(manRow,rightColumn);
        return rightRect.contains(touch_x,touch_y);
    }

    /**
     * 搬砖工向左移动
     */
    private boolean touch_left_to_man(int touch_x,int touch_y,int manRow,int manColum){
        int leftColumn = manColum - 1;          //左侧单元格列号
        Rect leftRect = getRect(manRow,leftColumn);
        return leftRect.contains(touch_x,touch_y);
    }

    /**
     * 搬砖工向上移动
     */
    private boolean touch_up_to_man(int touch_x,int touch_y,int manRow,int manColum){
        int upRow = manRow - 1;          //上侧单元格列号
        Rect upRect = getRect(upRow,manColum);
        return upRect.contains(touch_x,touch_y);
    }



    /**
     * 单元格的矩形区域
     */
    private Rect getRect(int row,int column){
        int left = (int)(column * mCellWidth);
        int top = (int) (row * mCellWidth);
        int right = (int)((column + 1) * mCellWidth);
        int bottom = (int)((row + 1) * mCellWidth);
        return new Rect(left, top, right, bottom);
    }


    /**
     * 判断箱子是否在搬砖工下方
     */
    private boolean isBoxblowMan(){
        return  mBoxColumn == mManColumn && mBoxRow == mManRow + 1;
    }

    /**
     * 判断箱子是否在搬砖工上方
     */
    private boolean isBoxupMan(){
        return mBoxColumn == mManColumn && mBoxRow == mManRow - 1;
    }

    /**
     * 判断箱子是否在搬砖工右方
     */
    private boolean isBoxrightMan(){
        return mBoxRow == mManRow && mBoxColumn == mBoxColumn+1;
    }

    /**
     * 判断箱子是否在搬砖工左方
     */
    private boolean isBoxleftMan(){
        return mBoxRow == mManRow && mBoxColumn == mBoxColumn -1;
    }
}
