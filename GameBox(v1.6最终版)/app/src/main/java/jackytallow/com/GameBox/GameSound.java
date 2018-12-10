package jackytallow.com.GameBox;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;

import java.io.IOException;


public class GameSound {
    private static SoundPool mSoundPool;
    private static int mOneSetpMusicId;
    private static  int mMoveBoxMusicId;
    private static int mGameOverMusicId;
    private static  boolean mSoundAllowed = true;

    public static void loadSound(AssetManager assetManager) {
        try {
            AssetFileDescriptor oneStepFd = assetManager.openFd("onestep.ogg");
            AssetFileDescriptor moveBoxFd = assetManager.openFd("movebox.ogg");
            AssetFileDescriptor gameOverFd = assetManager.openFd("game_over.ogg");
            mSoundPool = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
            mOneSetpMusicId = mSoundPool.load(oneStepFd, 1);
            mMoveBoxMusicId = mSoundPool.load(moveBoxFd, 1);
            mGameOverMusicId = mSoundPool.load(gameOverFd, 1);
        } catch (IOException e) {
            mSoundPool = null;
            mOneSetpMusicId = -1;
            mMoveBoxMusicId = -1;
        }
    }

    public static void playOneStepSound(){
        if (mSoundPool != null )
            mSoundPool.play(mOneSetpMusicId, 1.0f, 1.0f, 1, 0, 1);
    }

    public static void playMoveBoxSound(){
        if (mSoundPool != null)
            mSoundPool.play(mMoveBoxMusicId, 1.0f, 1.0f, 1, 0, 1);
    }

    public static void releaseSound(){
        if (mSoundPool == null) return;
        mSoundPool.unload(mOneSetpMusicId);
        mSoundPool.unload(mMoveBoxMusicId);
        mSoundPool.unload(mGameOverMusicId);
        mSoundPool.release();
    }

    public static void playGameOverSound(AssetManager assetManager){
        if (mSoundPool != null)
            mSoundPool.play(mGameOverMusicId, 1.0f, 1.0f, 1, 0 ,1);
    }

    public static void switchSoundAllowed(){
        mSoundAllowed = !mSoundAllowed;
    }

    public static  boolean isSoundAllowed(){
        return mSoundAllowed;
    }
}
