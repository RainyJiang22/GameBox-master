package yescorp.com.tuixiangzi;

/**
 * Created by 612226 on 2016/7/12.
 */
public class GameStepData {
    public TCell mManPrvPosition;
    public TCell mManCurrentPosition;
    public TCell mBoxPrvPosition;
    public TCell mBoxCurrentPosition;

    public GameStepData(){
//        mManPrvPosition = new TCell();
//        mManCurrentPosition = new TCell();
//        mBoxPrvPosition = new TCell();
//        mBoxCurrentPosition = new TCell();
    }

    public TCell getManPrvPosition() {
        return mManPrvPosition;
    }

    public void setManPrvPosition(TCell mManPrvPosition) {
        this.mManPrvPosition = mManPrvPosition;
    }

    public TCell getManCurrentPosition() {
        return mManCurrentPosition;
    }

    public void setManCurrentPosition(TCell mManCurrentPosition) {
        this.mManCurrentPosition = mManCurrentPosition;
    }

    public TCell getBoxPrvPosition() {
        return mBoxPrvPosition;
    }

    public void setBoxPrvPosition(TCell mBoxPrvPosition) {
        this.mBoxPrvPosition = mBoxPrvPosition;
    }

    public TCell getBoxCurrentPosition() {
        return mBoxCurrentPosition;
    }

    public void setBoxCurrentPosition(TCell mBoxCurrentPosition) {
        this.mBoxCurrentPosition = mBoxCurrentPosition;
    }
}
