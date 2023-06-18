package com.sbkinoko.sbkinokorpg;

import android.view.View;

import com.sbkinoko.sbkinokorpg.mapframe.MapBackgroundCell;

//todo　optionPackageの作成
public class OptionConst {
    public static int V = 15;//起動時
    public static int V_mul = 3;

    public static int getActualV() {
        return V * V_mul;
    }

    public static void changeSpeed() {
        OptionConst.V_mul += 1;
        if (OptionConst.V_mul > 10) {
            OptionConst.V_mul = 1;
        }
    }

    public static int
            encounter = 200;//100で通常　200は出ない

    public static void changeMonsApp() {
        if (encounter != 200) {
            encounter = 200;
        } else {
            encounter = 100;
        }
    }


    public static boolean
            collisionDrawFlag = true;//trueで当たり判定を表示

    public static void changeCollision() {
        OptionConst.collisionDrawFlag = !OptionConst.collisionDrawFlag;
    }

    public static int
            escapeFlag = 1;

    public static void changeEscapeFlag() {
        if (OptionConst.escapeFlag == 1) {
            OptionConst.escapeFlag = 2;
        } else {
            OptionConst.escapeFlag = 1;
        }
    }


    public static final int
            moveType_Button = 1,
            moveType_Stick = 2;
    public static final int
            COMMAND_BUTTON_LEFT = 1,
            COMMAND_BUTTON_RIGHT = 2;

    private static int
            buttonPattern = 1;

    public static boolean isCommandButtonLeft() {
        return buttonPattern == COMMAND_BUTTON_LEFT;
    }

    public static int moveButtonType = moveType_Button;


    public static void changeButtonPattern() {
        if (OptionConst.isCommandButtonLeft()) {
            OptionConst.buttonPattern = COMMAND_BUTTON_RIGHT;
        } else {
            OptionConst.buttonPattern = COMMAND_BUTTON_LEFT;
        }
    }

    public static void changeMoveButtonType() {
        if (OptionConst.moveButtonType == 1) {
            OptionConst.moveButtonType = 2;
        } else {
            OptionConst.moveButtonType = 1;
        }
    }

    public static boolean
            visibilityOfMapCellNum = true;

    public static void changeCellNum() {
        OptionConst.visibilityOfMapCellNum = !OptionConst.visibilityOfMapCellNum;
    }

    static public ResetVisibilityStrategy getResetVisibleStrategy(MapBackgroundCell[][] mapBackgroundCell) {
        if (OptionConst.visibilityOfMapCellNum) {
            return new VisibleStrategy(mapBackgroundCell);
        } else {
            return new InvisibleStrategy(mapBackgroundCell);
        }
    }

    static public abstract class ResetVisibilityStrategy {
        MapBackgroundCell[][] mapBackgroundCells;

        ResetVisibilityStrategy(MapBackgroundCell[][] mapBackgroundCells) {
            this.mapBackgroundCells = mapBackgroundCells;
        }

        abstract public void setVisibility(int i, int j);

    }

    static public class VisibleStrategy extends ResetVisibilityStrategy {
        VisibleStrategy(MapBackgroundCell[][] mapBackgroundCells) {
            super(mapBackgroundCells);
        }

        @Override
        public void setVisibility(int i, int j) {
            mapBackgroundCells[i][j].getTV().setVisibility(View.VISIBLE);
        }
    }

    static public class InvisibleStrategy extends ResetVisibilityStrategy {
        InvisibleStrategy(MapBackgroundCell[][] mapBackgroundCells) {
            super(mapBackgroundCells);
        }

        @Override
        public void setVisibility(int i, int j) {
            mapBackgroundCells[i][j].getTV().setVisibility(View.GONE);
        }
    }
}
