package com.example.gamebox_master.View;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

import com.example.gamebox_master.R;

/**
 * Created by Jacky on 2018/10/25.
 */

public class GameView  extends View{

    private float mCellWidth;
    public static final int CELL_NUM_PER_LINE = 12;
    private Bitmap ManBitmap = null;


    public GameView(Context context) {
        super(context);
        ManBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.eggman_48x48);
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
        Rect strRect = new Rect(0,0,ManBitmap.getWidth(),ManBitmap.getHeight());
        Rect desRect = new Rect(0,0,(int)mCellWidth,(int)mCellWidth);
        canvas.drawBitmap(ManBitmap,strRect,desRect,null);
    }

}
