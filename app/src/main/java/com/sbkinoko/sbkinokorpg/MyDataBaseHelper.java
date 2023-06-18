package com.sbkinoko.sbkinokorpg;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.sbkinoko.sbkinokorpg.battleframe.status.PlayerStatus;
import com.sbkinoko.sbkinokorpg.battleframe.status.Status;
import com.sbkinoko.sbkinokorpg.mapframe.map.mapdata.MapData;

public class MyDataBaseHelper extends SQLiteOpenHelper {
    public static final int DBVersion = 1;
    public static final String DBName = "SB_kinoko.db";
    public static final String ID = "_id";

    public static final String PLAYER_INFO_TABLE = "player_info";
    public static final String AUTO_PLAYER_INFO_TABLE = "auto_" + PLAYER_INFO_TABLE;

    public static final String DATA_NUMBER = "data_number";
    public static final String MONEY = "money";
    public static final String CELL_X = "cell_x";
    public static final String CELL_Y = "cell_y";
    public static final String PLAYER_X = "player_x";
    public static final String PLAYER_Y = "player_Y";
    public static final String MOVE_STATE = "move_state";
    public static String MAP_NUMBER = "map_number";
    public static String UPDATE_TIME = "update_time";
    public static String EVENT_STEP = "event_step";

    public static String[] PlayerColNames = new String[]{
            DATA_NUMBER,
            CELL_X,
            CELL_Y,
            PLAYER_X,
            PLAYER_Y,
            MONEY,
            MOVE_STATE,
            MAP_NUMBER,
            UPDATE_TIME,
    };

    private static final StringBuilder SQL_CREATE_PLAYER_ENTRY = new StringBuilder(
            " ("
                    + ID + " INTEGER PRIMARY KEY ,"
                    + DATA_NUMBER + " INTEGER ,"
                    + MOVE_STATE + " INTEGER ,"
                    + CELL_X + " INTEGER ,"
                    + CELL_Y + " INTEGER ,"
                    + MONEY + " INTEGER , "
                    + PLAYER_X + " FLOAT ,"
                    + PLAYER_Y + " FLOAT,"
                    + UPDATE_TIME + " LONG,"
                    + MAP_NUMBER + " int");

    public static final String STATUS_TABLE_NAME = "statusList";
    public static final String AUTO_STATUS_TABLE_NAME = "auto_" + STATUS_TABLE_NAME;

    public static final String STATUS_ID = "status_id_";
    public static final String STATUS_EQP = "status_eqp_";
    public static final String STATUS_ITEM = "status_item_";
    public static final String STATUS_HP = "status_HP";
    public static final String STATUS_MP = "status_MP";
    public static final String EXP = "status_exp";

    private static final StringBuilder SQL_CREATE_STATUS_ENTRY = new StringBuilder(
            "( "
                    + ID + " INTEGER PRIMARY KEY ,"
                    + STATUS_ID + " INTEGER ,"
                    + STATUS_HP + " INTEGER ,"
                    + STATUS_MP + " INTEGER ,"
                    + EXP + " INTEGER");


    public static final String ITEM_TABLE_NAME = "itemList";
    public static final String AUTO_ITEM_TABLE_NAME = "auto_" + ITEM_TABLE_NAME;

    public static final String ITEM_POSITION = "item_position";
    public static final String ITEM_ID = "item_id";
    public static final String ITEM_NUM = "item_num";

    private static final String SQL_CREATE_ITEM_ENTRY =
            " ( "
                    + ID + " INTEGER PRIMARY KEY ,"
                    + ITEM_POSITION + " INTEGER ,"
                    + ITEM_ID + " INTEGER ,"
                    + ITEM_NUM + " INTEGER)";


    public static final String MAP_TABLE_NAME = "map_treasure_list";
    public static final String AUTO_MAP_TABLE_NAME = "auto_" + MAP_TABLE_NAME;

    public static final String MAP_ID = "map_id";
    public static final String TREASURE_BOX = "treasure_box";

    private static final StringBuilder SQL_CREATE_MAP_ENTRY = new StringBuilder(
            "( "
                    + ID + " INTEGER PRIMARY KEY ,"
                    + MAP_ID + " INTEGER");

    private static final String SQL_DELETE_ENTRY =
            "DROP TABLE IF EXISTS ";

    private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS ";

    private static final String[] TableNames;

    static {

        for (int i = 0; i < MapData.eventFlagNum; i++) {
            SQL_CREATE_PLAYER_ENTRY.append(" ,").append(EVENT_STEP).append(i).append(" INTEGER");
        }
        SQL_CREATE_PLAYER_ENTRY.append(")");

        for (int i = 0; i < Status.EQP_NUM; i++) {
            SQL_CREATE_STATUS_ENTRY.append(" ," + STATUS_EQP).append(i).append("  INTEGER ");
        }
        for (int i = 0; i < PlayerStatus.canHaveToolNum; i++) {
            SQL_CREATE_STATUS_ENTRY.append("," + STATUS_ITEM).append(i).append("  INTEGER ");
        }
        SQL_CREATE_STATUS_ENTRY.append(")");


        for (int i = 0; i < MapData.MAX_BOX_NUM; i++) {
            SQL_CREATE_MAP_ENTRY.append(" ," + TREASURE_BOX).append(i).append("  INTEGER ");
        }
        SQL_CREATE_MAP_ENTRY.append(")");

        TableNames = new String[]{
                PLAYER_INFO_TABLE,
                STATUS_TABLE_NAME,
                ITEM_TABLE_NAME,
                MAP_TABLE_NAME
        };
    }

    MyDataBaseHelper(Context context) {
        super(context, DBName, null, DBVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE + PLAYER_INFO_TABLE + SQL_CREATE_PLAYER_ENTRY);
        db.execSQL(CREATE_TABLE + AUTO_PLAYER_INFO_TABLE + SQL_CREATE_PLAYER_ENTRY);
        db.execSQL(CREATE_TABLE + STATUS_TABLE_NAME + SQL_CREATE_STATUS_ENTRY);
        db.execSQL(CREATE_TABLE + AUTO_STATUS_TABLE_NAME + SQL_CREATE_STATUS_ENTRY);
        db.execSQL(CREATE_TABLE + ITEM_TABLE_NAME + SQL_CREATE_ITEM_ENTRY);
        db.execSQL(CREATE_TABLE + AUTO_ITEM_TABLE_NAME + SQL_CREATE_ITEM_ENTRY);
        db.execSQL(CREATE_TABLE + MAP_TABLE_NAME + SQL_CREATE_MAP_ENTRY);
        db.execSQL(CREATE_TABLE + AUTO_MAP_TABLE_NAME + SQL_CREATE_MAP_ENTRY);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db,
                          int oldVer, int newVer) {

        for (String tableName : TableNames) {
            db.execSQL(SQL_DELETE_ENTRY + tableName);
        }
        onCreate(db);
    }

    public void deleteTable(SQLiteDatabase db) {
        for (String tableName : TableNames) {
            db.execSQL(SQL_DELETE_ENTRY + tableName);
        }
    }

    public static String getStatusTableName() {
        if (MainGame.startType == MainActivity.start_autoSave) {
            return AUTO_STATUS_TABLE_NAME;
        }

        return STATUS_TABLE_NAME;
    }

    public static String[] getStatusColNames() {
        int dataNum = 4;
        String[] colNames = new String[dataNum + PlayerStatus.canHaveToolNum + Status.EQP_NUM];
        colNames[0] = MyDataBaseHelper.STATUS_ID;
        colNames[1] = MyDataBaseHelper.EXP;
        colNames[2] = MyDataBaseHelper.STATUS_HP;
        colNames[3] = MyDataBaseHelper.STATUS_MP;
        for (int i = 0; i < Status.EQP_NUM; i++) {
            colNames[dataNum + i] = MyDataBaseHelper.STATUS_EQP + i;
        }
        for (int i = 0; i < PlayerStatus.canHaveToolNum; i++) {
            colNames[dataNum + Status.EQP_NUM + i] = MyDataBaseHelper.STATUS_ITEM + i;
        }
        return colNames;
    }


    public static String getPlayerTableName() {
        if (MainGame.startType == MainActivity.start_autoSave) {
            return AUTO_PLAYER_INFO_TABLE;
        }
        return PLAYER_INFO_TABLE;
    }

    public static String getItemTableName() {
        if (MainGame.startType == MainActivity.start_autoSave) {
            return AUTO_ITEM_TABLE_NAME;
        }

        return MyDataBaseHelper.ITEM_TABLE_NAME;
    }

    public static String[] getItemTableColNames() {
        return new String[]{
                MyDataBaseHelper.ITEM_ID,
                MyDataBaseHelper.ITEM_NUM};
    }

    public static String getMapTableName() {
        if (MainGame.startType == MainActivity.start_autoSave) {
            return MyDataBaseHelper.AUTO_MAP_TABLE_NAME;
        }
        return MyDataBaseHelper.MAP_TABLE_NAME;

    }
}


