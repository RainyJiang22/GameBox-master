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
    public static Bitmap ManBitmap ;
    public static Bitmap BoxBitmap ;
    public static Bitmap WallBitmap;
    public static Bitmap FlagBitmap;
    public static Bitmap DoneBitmap;
//    public static Bitmap SoundOpenBitmap;
//    public static Bitmap SoundCloseBitmap;


    public static void loadGameBitmaps(Resources res){
        if (ManBitmap == null) //如果为null就加载图片，没有就说明已经加载过了
            ManBitmap = BitmapFactory.decodeResource(res, R.drawable.eggman_48x48);

        if (BoxBitmap  == null)
            BoxBitmap = BitmapFactory.decodeResource(res,R.drawable.box_48x48);

        if (FlagBitmap == null)
            FlagBitmap = BitmapFactory.decodeResource(res,R.drawable.flag_48x48);
        if (WallBitmap == null)
            WallBitmap = BitmapFactory.decodeResource(res,R.drawable.wall_48x48);
        if (DoneBitmap == null)
            DoneBitmap = BitmapFactory.decodeResource(res,R.drawable.done_72x72);
//        if (SoundOpenBitmap == null)
//            SoundOpenBitmap = BitmapFactory.decodeResource(res,R.drawable.laba_open_48x48);
//        if (SoundCloseBitmap == null)
//            SoundCloseBitmap = BitmapFactory.decodeResource(res,R.drawable.laba_close_48x48);
    }

    //释放图片对象占据的内存
    private static void releaseGameBitmaps(Bitmap bitmap){
//        if (ManBitmap != null){
//            ManBitmap.recycle();
//            ManBitmap = null;
//        }
//        if (BoxBitmap != null){
//            BoxBitmap.recycle();
//            BoxBitmap = null;
//        }

        if (bitmap != null){
            bitmap.recycle();
            bitmap = null;
        }
    }


    public static void releaseBitmaps(){
        //game board;
        releaseGameBitmaps(BoxBitmap);
        releaseGameBitmaps(ManBitmap);
        releaseGameBitmaps(WallBitmap);
        releaseGameBitmaps(FlagBitmap);
        releaseGameBitmaps(DoneBitmap);
//        releaseGameBitmaps(SoundOpenBitmap);
//        releaseGameBitmaps(SoundCloseBitmap);

    }



}
