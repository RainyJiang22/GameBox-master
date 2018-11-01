package com.example.gamebox_master.View;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.gamebox_master.R;

/**
 * Created by Jacky on 2018/10/28.
 */

/**
 * 加载图片资源类Bitmap
 */
public class GameBitmaps {

    //需要为每一个图片安排一个static变量
    public static Bitmap ManBitmap = null;
    public static Bitmap BoxBitmap = null;


    public static void loadGameBitmaps(Resources res){
        if (ManBitmap == null) //如果为null就加载图片，没有就说明已经加载过了
            ManBitmap = BitmapFactory.decodeResource(res, R.drawable.eggman_48x48);

        if (BoxBitmap  == null)
            BoxBitmap = BitmapFactory.decodeResource(res,R.drawable.box_48x48);
    }

    //释放图片对象占据的内存
    public static void releaseGameBitmaps(){
        if (ManBitmap != null){
            ManBitmap.recycle();
            ManBitmap = null;
        }
        if (BoxBitmap != null){
            BoxBitmap.recycle();
            BoxBitmap = null;
        }
    }
}
