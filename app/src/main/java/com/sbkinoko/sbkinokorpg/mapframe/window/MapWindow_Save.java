package com.sbkinoko.sbkinokorpg.mapframe.window;

import static com.sbkinoko.sbkinokorpg.gameparams.GameParams.PLAYER_NUM;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sbkinoko.sbkinokorpg.MainGame;
import com.sbkinoko.sbkinokorpg.MyDataBaseHelper;
import com.sbkinoko.sbkinokorpg.application.MyEntryPoints;
import com.sbkinoko.sbkinokorpg.battleframe.status.PlayerStatus;
import com.sbkinoko.sbkinokorpg.battleframe.status.Status;
import com.sbkinoko.sbkinokorpg.dataList.item.List_Tool;
import com.sbkinoko.sbkinokorpg.dataList.player_status.JobStatus;
import com.sbkinoko.sbkinokorpg.dataList.player_status.List_JobStatus;
import com.sbkinoko.sbkinokorpg.gameparams.Axis;
import com.sbkinoko.sbkinokorpg.gameparams.GameParams;
import com.sbkinoko.sbkinokorpg.gameparams.MoveState;
import com.sbkinoko.sbkinokorpg.mapframe.MapFrame;
import com.sbkinoko.sbkinokorpg.mapframe.MapPoint;
import com.sbkinoko.sbkinokorpg.mapframe.map.mapdata.MapData;
import com.sbkinoko.sbkinokorpg.mapframe.player.Player;
import com.sbkinoko.sbkinokorpg.repository.playertool.PlayerToolRepository;
import com.sbkinoko.sbkinokorpg.window.MenuWindowInterface;

import dagger.hilt.EntryPoints;

public class MapWindow_Save extends MapGameWindow implements MenuWindowInterface {

    static String[] saveTexts;
    Player player;
    private static PlayerStatus[] statuses;

    static {
        saveTexts = new String[]{
                "する",
                "しない"
        };
    }

    private final PlayerToolRepository playerToolRepository;

    public MapWindow_Save(Context context, MapFrame mapFrame, Player player) {
        super(context, mapFrame);

        menuTV = new TextView[saveTexts.length];
        setMenuTvs();
        setSelectedTv(selectedTV);
        this.player = player;

        MyEntryPoints myEntryPoints = EntryPoints.get(context.getApplicationContext(), MyEntryPoints.class);

        playerToolRepository = myEntryPoints.playerToolRepository();
    }

    @Override
    public void setFramePosition() {
        frameLayout.setLayoutParams(new FrameLayout.LayoutParams(
                MainGame.playWindowSize / 2,
                MainGame.playWindowSize / 10 * saveTexts.length
        ));
        frameLayout.setX((float) MainGame.playWindowSize / 2);
    }

    public static int[] getSaveData(Player player) {
        int[] returnData;

        returnData = getPlayerData(player);

        getEventData(player);

        getItemData(player);

        return returnData;
    }

    static int[] getPlayerData(Player player) {
        Cursor cursor;
        int[] returnData = new int[3];

        cursor = MainGame.DataBase.query(
                MyDataBaseHelper.getPlayerTableName(),
                MyDataBaseHelper.PlayerColNames,
                null,
                null,
                null,
                null,
                null);
        cursor.moveToLast();
        int colID;

        colID = cursor.getColumnIndex(MyDataBaseHelper.CELL_X);
        returnData[Axis.X.id] = cursor.getInt(colID);

        colID = cursor.getColumnIndex(MyDataBaseHelper.CELL_Y);
        returnData[Axis.Y.id] = cursor.getInt(colID);

        float[] startPoint = new float[2];
        colID = cursor.getColumnIndex(MyDataBaseHelper.PLAYER_X);
        startPoint[Axis.X.id] = cursor.getFloat(colID);

        colID = cursor.getColumnIndex(MyDataBaseHelper.PLAYER_Y);
        startPoint[Axis.Y.id] = cursor.getFloat(colID);

        player.setRelativePoint(startPoint);

        colID = cursor.getColumnIndex(MyDataBaseHelper.MAP_NUMBER);
        returnData[2] = cursor.getInt(colID);

        colID = cursor.getColumnIndex(MyDataBaseHelper.MOVE_STATE);
        int intMoveState = cursor.getInt(colID);
        MoveState moveState = MoveState.convertIntToMoveState(intMoveState);
        player.setMoveState(moveState);

        colID = cursor.getColumnIndex(MyDataBaseHelper.MONEY);
        player.setMoney(cursor.getInt(colID));

        cursor.close();
        return returnData;
    }

    static void getEventData(Player player) {
        for (int i = 0; i < MapData.eventFlagNum; i++) {
            String colName = MyDataBaseHelper.EVENT_STEP + i;
            Cursor cursor = MainGame.DataBase.query(
                    MyDataBaseHelper.getPlayerTableName(),
                    new String[]{colName},
                    null,
                    null,
                    null,
                    null,
                    null);
            cursor.moveToLast();
            int colID = cursor.getColumnIndex(colName);
            int flagValue = (cursor.getInt(colID));
            player.setEventFlag(new int[]{i, flagValue});
            cursor.close();
        }
    }

    static void getItemData(Player player) {
        Cursor cursor;

        cursor = MainGame.DataBase.query(
                MyDataBaseHelper.getItemTableName(),
                MyDataBaseHelper.getItemTableColNames(),
                null,
                null,
                null,
                null,
                null);
        cursor.moveToFirst();
        int itemID, itemNum;
        int cIndex;
        do {
            cIndex = cursor.getColumnIndex(MyDataBaseHelper.ITEM_ID);
            itemID = cursor.getInt(cIndex);

            cIndex = cursor.getColumnIndex(MyDataBaseHelper.ITEM_NUM);
            itemNum = cursor.getInt(cIndex);

            player.addItem(itemID, itemNum);
        } while (cursor.moveToNext());
        cursor.close();
    }


    public static void addData() {

        addPlayerData();

        addItemData();

        addStatusData();

        addMapData();
    }

    static private void addPlayerData() {
        float playerX = MainGame.relativeCenter[Axis.X.id];
        float playerY = MainGame.relativeCenter[Axis.Y.id];
        Log.d("msg", GameParams.startX + ":" + GameParams.startY + " X" + playerX + " Y" + playerY);

        ContentValues values = new ContentValues();

        values.put(MyDataBaseHelper.DATA_NUMBER, 1);
        values.put(MyDataBaseHelper.CELL_X, GameParams.startX);
        values.put(MyDataBaseHelper.CELL_Y, GameParams.startY);
        values.put(MyDataBaseHelper.PLAYER_X, playerX);
        values.put(MyDataBaseHelper.PLAYER_Y, playerY);
        values.put(MyDataBaseHelper.MAP_NUMBER, GameParams.startMap);
        values.put(MyDataBaseHelper.MONEY, 1000);
        values.put(MyDataBaseHelper.MOVE_STATE, MoveState.MoveState_Ground.getMoveStateInt());
        values.put(MyDataBaseHelper.UPDATE_TIME, System.currentTimeMillis());

        for (int i = 0; i < MapData.eventFlagNum; i++) {
            String colName = MyDataBaseHelper.EVENT_STEP + i;
            values.put(colName, 1);
        }


        MainGame.DataBase = MainGame.myDataBaseHelper.getWritableDatabase();
        MainGame.DataBase.insert(
                MyDataBaseHelper.PLAYER_INFO_TABLE,
                "0",
                values
        );

        if (MainGame.getAutoSaveRow() != 1) {
            MainGame.DataBase.insert(
                    MyDataBaseHelper.AUTO_PLAYER_INFO_TABLE,
                    "0",
                    values
            );
        }
    }


    static private void addItemData() {
        MainGame.DataBase = MainGame.myDataBaseHelper.getWritableDatabase();
        for (int i = 0; i < new List_Tool().getItemNum(); i++) {
            ContentValues values = new ContentValues();
            values.put(MyDataBaseHelper.ITEM_POSITION, i);
            values.put(MyDataBaseHelper.ITEM_ID, 0);
            values.put(MyDataBaseHelper.ITEM_NUM, 0);

            MainGame.DataBase.insert(
                    MyDataBaseHelper.ITEM_TABLE_NAME,
                    "0",
                    values
            );

            if (MainGame.getAutoSaveRow() != 1) {
                MainGame.DataBase.insert(
                        MyDataBaseHelper.AUTO_ITEM_TABLE_NAME,
                        "0",
                        values
                );
            }
        }
    }

    static private void addStatusData() {
        MainGame.DataBase = MainGame.myDataBaseHelper.getWritableDatabase();
        for (int playerID = 0; playerID < PLAYER_NUM; playerID++) {
            ContentValues values = new ContentValues();
            values.put(MyDataBaseHelper.STATUS_ID, playerID);
            values.put(MyDataBaseHelper.EXP, 0);

            JobStatus playerStatus = List_JobStatus.getStatusList(playerID);

            values.put(MyDataBaseHelper.STATUS_HP, playerStatus.getHP(1));
            values.put(MyDataBaseHelper.STATUS_MP, playerStatus.getMP(1));

            for (int j = 0; j < Status.EQP_NUM; j++) {
                values.put(MyDataBaseHelper.STATUS_EQP + j, 1);
            }
            for (int j = 0; j < PlayerStatus.canHaveToolNum; j++) {
                values.put(MyDataBaseHelper.STATUS_ITEM + j, 0);
            }

            MainGame.DataBase.insert(
                    MyDataBaseHelper.STATUS_TABLE_NAME,
                    "0",
                    values
            );

            if (MainGame.getAutoSaveRow() != 1) {
                MainGame.DataBase.insert(
                        MyDataBaseHelper.AUTO_STATUS_TABLE_NAME,
                        "0",
                        values
                );
            }
        }
    }

    static private void addMapData() {
        for (int i = 0; i < MapData.MAP_NUM; i++) {
            ContentValues values = new ContentValues();

            MapData tmpMap = MapData.getMapData(i);

            values.put(MyDataBaseHelper.MAP_ID, i);

            assert tmpMap != null;
            for (int j = 0; j < MapData.MAX_BOX_NUM; j++) {
                if (j < tmpMap.getTreasureBoxes().length) {
                    values.put(MyDataBaseHelper.TREASURE_BOX + j, tmpMap.getTreasureBoxes()[j]);
                } else {
                    values.put(MyDataBaseHelper.TREASURE_BOX + j, 0);

                }
            }

            MainGame.DataBase.insert(
                    MyDataBaseHelper.MAP_TABLE_NAME,
                    "0",
                    values
            );

            if (MainGame.getAutoSaveRow() != 1) {
                MainGame.DataBase.insert(
                        MyDataBaseHelper.AUTO_MAP_TABLE_NAME,
                        "0",
                        values
                );
            }
        }
    }

    /**
     * セーブ処理
     *
     * @param autoSaveFlag autoSaveだった場合true
     */
    public void save(boolean autoSaveFlag) {

        MainGame.DataBase = MainGame.myDataBaseHelper.getWritableDatabase();
        //todo 先にテーブル名を取得しておく
        savePlayerData(autoSaveFlag);

        saveItemData(autoSaveFlag);

        saveStatus(autoSaveFlag);

        saveMapData(autoSaveFlag);

        Toast.makeText(context,
                "セーブ処理",
                Toast.LENGTH_SHORT).show();
    }

    private void savePlayerData(boolean autoSaveFlag) {
        MapPoint mapPoint = mapFrame.getBgcMatrix().getPlayerMapXY();
        int x = mapPoint.getX();
        int y = mapPoint.getY();
        float playerX = mapFrame.relativePlayerPoint()[Axis.X.id];
        float playerY = mapFrame.relativePlayerPoint()[Axis.Y.id];
        int mapNumber = mapFrame.getMapViewModel().getMapID().ordinal();
        Log.d("msg", x + ":" + y + " X" + playerX + " Y" + playerY);

        ContentValues values = new ContentValues();
        values.put(MyDataBaseHelper.DATA_NUMBER, 1);
        values.put(MyDataBaseHelper.CELL_X, x);
        values.put(MyDataBaseHelper.CELL_Y, y);
        values.put(MyDataBaseHelper.PLAYER_X, playerX);
        values.put(MyDataBaseHelper.PLAYER_Y, playerY);
        values.put(MyDataBaseHelper.MAP_NUMBER, mapNumber);
        values.put(MyDataBaseHelper.MONEY, player.getMoney());
        values.put(MyDataBaseHelper.MOVE_STATE, player.getMoveState().getMoveStateInt());
        values.put(MyDataBaseHelper.UPDATE_TIME, System.currentTimeMillis());

        for (int i = 0; i < MapData.eventFlagNum; i++) {
            String colName = MyDataBaseHelper.EVENT_STEP + i;
            values.put(colName, player.getEventFlag(i));
        }

        String tableName;
        if (autoSaveFlag) {
            tableName = MyDataBaseHelper.AUTO_PLAYER_INFO_TABLE;
        } else {
            tableName = MyDataBaseHelper.PLAYER_INFO_TABLE;
        }

        MainGame.DataBase.update(
                tableName,
                values,
                MyDataBaseHelper.DATA_NUMBER + " =?",
                new String[]{"1"}
        );
    }

    private void saveItemData(boolean autoSaveFlag) {
        String tableName;
        if (autoSaveFlag) {
            tableName = MyDataBaseHelper.AUTO_ITEM_TABLE_NAME;
        } else {
            tableName = MyDataBaseHelper.ITEM_TABLE_NAME;
        }

        for (int toolPosition = 0; toolPosition < player.getAllItem().length; toolPosition++) {
            ContentValues values = new ContentValues();
            values.put(MyDataBaseHelper.ITEM_NUM, player.getToolNumAt(toolPosition));
            values.put(MyDataBaseHelper.ITEM_ID, player.getToolIdAt(toolPosition));
            MainGame.DataBase.update(
                    tableName,
                    values,
                    MyDataBaseHelper.ITEM_POSITION + " =?",
                    new String[]{toolPosition + ""}
            );
        }
    }

    private void saveStatus(boolean autoSaveFlag) {
        String tableName;

        if (autoSaveFlag) {
            tableName = MyDataBaseHelper.AUTO_STATUS_TABLE_NAME;
        } else {
            tableName = MyDataBaseHelper.STATUS_TABLE_NAME;
        }
        for (int playerId = 0; playerId < PLAYER_NUM; playerId++) {
            ContentValues values = new ContentValues();
            values.put(MyDataBaseHelper.EXP, statuses[playerId].getExp());
            values.put(MyDataBaseHelper.STATUS_HP, statuses[playerId].getHP());
            values.put(MyDataBaseHelper.STATUS_MP, statuses[playerId].getMP());

            for (int j = 0; j < Status.EQP_NUM; j++) {
                values.put(MyDataBaseHelper.STATUS_EQP + j, statuses[playerId].getEQP(j));
            }
            for (int toolPos = 0; toolPos < PlayerStatus.canHaveToolNum; toolPos++) {
                values.put(
                        MyDataBaseHelper.STATUS_ITEM + toolPos,
                        playerToolRepository.getItem(playerId, toolPos)
                );
            }
            MainGame.DataBase.update(
                    tableName,
                    values,
                    MyDataBaseHelper.STATUS_ID + " =?",
                    new String[]{playerId + ""}
            );
        }
    }

    private void saveMapData(boolean autoSaveFlag) {
        String tableName;
        //getTableName(autoSaveFlag);
        if (autoSaveFlag) {
            tableName = MyDataBaseHelper.AUTO_MAP_TABLE_NAME;
        } else {
            tableName = MyDataBaseHelper.MAP_TABLE_NAME;
        }

        MapData tmpMap;
        for (int i = 0; i < MapData.MAP_NUM; i++) {
            ContentValues values = new ContentValues();
            tmpMap = MainGame.mapDataList[i];
            values.put(MyDataBaseHelper.MAP_ID, i);

            if (tmpMap.getTreasureBoxes() != null) {
                for (int j = 0; j < tmpMap.getTreasureBoxes().length; j++) {
                    values.put(MyDataBaseHelper.TREASURE_BOX + j, tmpMap.getTreasureBoxes()[j]);
                }
            }

            MainGame.DataBase.update(
                    tableName,
                    values,
                    MyDataBaseHelper.MAP_ID + " =?",
                    new String[]{i + ""}
            );
        }
    }

    @Override
    public void useBtA() {
        switch (selectedTV) {
            case 0:
                save(false);
                //セーブする処理
            case 1:
                //セーブしない処理
                closeMenu();
                mapFrame.mapMainMenuWindow.openMenu();
                break;
        }
    }

    @Override
    public void setMenuTv(int i) {
        menuTV[i].setLayoutParams(new LinearLayout.LayoutParams(
                MainGame.playWindowSize / 2,
                MainGame.playWindowSize / 10
        ));
        String txt = saveTexts[i];
        menuTV[i].setY((float) i * MainGame.playWindowSize / 10);
        menuTV[i].setText(txt);
    }


    public static PlayerStatus[] getStatusData(Context context) {
        statuses = new PlayerStatus[GameParams.PLAYER_NUM];

        Cursor cursor;
        MainGame.DataBase = MainGame.myDataBaseHelper.getReadableDatabase();

        cursor = MainGame.DataBase.query(
                MyDataBaseHelper.getStatusTableName(),
                MyDataBaseHelper.getStatusColNames(),
                null, null,
                null, null,
                null
        );
        cursor.moveToFirst();

        int colID;
        int tmpValue;

        for (int playerID = 0; playerID < statuses.length; playerID++) {

            PlayerStatus status = new PlayerStatus("てすとちゃん" + (playerID + 1), playerID, context);
            statuses[playerID] = status;

            colID = cursor.getColumnIndex(MyDataBaseHelper.EXP);
            tmpValue = cursor.getInt(colID);
            status.setExp(tmpValue);

            colID = cursor.getColumnIndex(MyDataBaseHelper.STATUS_HP);
            tmpValue = cursor.getInt(colID);
            status.setHP(tmpValue);

            colID = cursor.getColumnIndex(MyDataBaseHelper.STATUS_MP);
            tmpValue = cursor.getInt(colID);
            status.setMP(tmpValue);

            for (int j = 0; j < Status.EQP_NUM; j++) {
                colID = cursor.getColumnIndex(MyDataBaseHelper.STATUS_EQP + j);
                tmpValue = cursor.getInt(colID);
                status.setEqp(tmpValue, j);
            }
            for (int j = 0; j < PlayerStatus.canHaveToolNum; j++) {
                colID = cursor.getColumnIndex(MyDataBaseHelper.STATUS_ITEM + j);
                tmpValue = cursor.getInt(colID);
                status.addHaveItem(tmpValue);
            }
            cursor.moveToNext();
        }

        cursor.close();
        return statuses;
    }
}