package yescorp.com.tuixiangzi.Game;

/**
 * Created by Jacky on 2018/10/22.
 */

public class GameCoordiater{

    public int row;
    public int column;

    public GameCoordiater(){
        row = 0;
        column = 0;
    }


    public GameCoordiater(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }


    public void set(int row,int column){
        this.row = row;
        this.column = column;
    }

    public boolean isEqualto(GameCoordiater cell){
    return  row == cell.row  && column == cell.column;
    }
}
