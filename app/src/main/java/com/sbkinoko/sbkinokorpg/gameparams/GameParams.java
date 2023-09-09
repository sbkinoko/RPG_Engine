package com.sbkinoko.sbkinokorpg.gameparams;

import com.sbkinoko.sbkinokorpg.mapframe.map.mapdata.TestField;

public class GameParams {
    public static final float playerSize = (float) 0.4;
    public static final int visibleCellNum = 5,
            allCellNum = visibleCellNum + 2;
    public static final double MapScrollSmall = 0.4,
            MapScrollLarge = 0.6;
    public static final float actionOffset = (float) 0.5;
    public final static int
            ID_MapMain = 100,
            ID_btUP = 200,
            ID_btDOWN = 201,
            ID_btLEFT = 202,
            ID_btRIGHT = 203,
            ID_btA = 204,
            ID_btB = 205,
            ID_btMenu = 206;

    public final static int Y_axis = 0, X_axis = 1;

    public final static float sellRatio = (float) 0.8;

    public final static int
            whereMap = 0,
            whereBattle = 1;

    public final static int canMoveTime = 500;

    public final static int
            ImageChangeTime = 300;  //主人公の画像切り替えの時間[mSec]

    public final static int MONS_APP_DIST = 1;


    public final static int
            startY = 5,
            startX = 5,
            startMap = TestField.MAP_ID;

    public static int fps = 30;
    public static int FrameLate = 1000 / fps;//ミリ秒　(33)で秒間30フレーム 40→25fps

    public static int PLAYER_NUM = 2;//編成変更時


    public final static int
            MoveState_Water = 1,
            MoveState_Ground = 2,
            MoveState_Sky = 3;

    public static final int
            STATUS_ATK = 1;

    static public void setPlayerNum(int num) {
        PLAYER_NUM = num;
    }
}
