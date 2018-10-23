package yescorp.com.tuixiangzi;

import android.content.ContentProvider;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by 612226 on 2016/7/4.
 */
public class PrfsManager{
    public static final String PRFS_PASSED_LEVEL_NAME = "yescorp.com.tuixiangzi.PassedLevels";
    // KEY_PASSED_LEVEL_PREFIX + i => 第i级的Key（i=1, 2, ...)
    public static final String KEY_PASSED_LEVEL_PREFIX = "Passed_Level_";

    private static SharedPreferences mSharedPreferences;
    private static SharedPreferences mPassedLevels;

    public static void setString(Context context, String locale, String code){
        mSharedPreferences= PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor=mSharedPreferences.edit();
        editor.putString(locale,code);
        editor.commit();
    }

    public static String getString(Context context,String locale){
        mSharedPreferences=PreferenceManager.getDefaultSharedPreferences(context);
        return mSharedPreferences.getString(locale,null);
    }

    public static void setBoolean(Context context, String key, boolean value){
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public static boolean getBoolean(Context context, String key){
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return mSharedPreferences.getBoolean(key, false);   //false是默认值，在找不到键值为key的配置项时使用
    }

    public static void setPassedLevel(Context context, int level){
        mPassedLevels = context.getSharedPreferences(PRFS_PASSED_LEVEL_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mPassedLevels.edit();
        editor.putBoolean(KEY_PASSED_LEVEL_PREFIX + level, true);
        editor.commit();
    }

    public static boolean getPassedLevel(Context context, int level){
        mPassedLevels = context.getSharedPreferences(PRFS_PASSED_LEVEL_NAME, Context.MODE_PRIVATE);
        return  mPassedLevels.getBoolean(KEY_PASSED_LEVEL_PREFIX + level, false);  //false是默认值，没有关卡level的首选项值时返回false
}

    /**
     * 增加选择关卡出现关卡不一致的情况
     */

    public static boolean isPassedLevel(Context context,int level){
        mPassedLevels = context.getSharedPreferences(PRFS_PASSED_LEVEL_NAME,Context.MODE_PRIVATE);


        return mPassedLevels.getBoolean(KEY_PASSED_LEVEL_PREFIX + level,false);
    }
}