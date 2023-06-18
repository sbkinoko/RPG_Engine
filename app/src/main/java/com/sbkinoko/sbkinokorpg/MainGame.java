package com.sbkinoko.sbkinokorpg;

import static com.sbkinoko.sbkinokorpg.GameParams.playerSize;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.sbkinoko.sbkinokorpg.battleframe.BattleFrame;
import com.sbkinoko.sbkinokorpg.battleframe.BattleSystem;
import com.sbkinoko.sbkinokorpg.controller.ControllerFrame;
import com.sbkinoko.sbkinokorpg.mapframe.MapFrame;
import com.sbkinoko.sbkinokorpg.mapframe.Player;
import com.sbkinoko.sbkinokorpg.mapframe.map.mapdata.MapData;
import com.sbkinoko.sbkinokorpg.mapframe.window.MapWindow_Save;

public class MainGame extends AppCompatActivity {
    public static int playWindowSize;

    public static int getPlayWindowSize() {
        return playWindowSize;
    }

    private static int playWindowSize_Unit;

    public static int getPlayWindowSize_Unit() {
        return playWindowSize_Unit;
    }


    public static ViewGroup.LayoutParams getDefaultListItemParams() {
        return new ViewGroup.LayoutParams(
                playWindowSize_Unit * 5,
                playWindowSize_Unit
        );
    }

    public static int cellLength;
    public static float[] relativeCenter;

    public MapFrame mapFrame;
    public BattleFrame battleFrame;
    public ControllerFrame controllerFrame;
    public BattleSystem battleSystem;

    private static int autoSaveRow;

    TextView fpsView;
    double lastCallTime = 0;
    int fps;
    double[] fpsArray = new double[30];
    int count = 0;

    public static MyDataBaseHelper myDataBaseHelper;
    public static SQLiteDatabase DataBase;
    Cursor cursor;
    ConstraintLayout LL;


    Player player;

    public Configuration config;

    private final Handler handler = new Handler();
    private final static Handler tapHandler = new Handler();

    public static Handler getTapHandler() {
        return tapHandler;
    }

    public static void removeTapHandler(Runnable runnable) {
        tapHandler.removeCallbacks(runnable);
    }

    Runnable runnable;

    int[] roadPoint = new int[3];
    MapData mapData;
    public static MapData[] mapDataList = new MapData[MapData.MAP_NUM];

    static int startType;

    int frameWidth, frameHeight;

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_game);

        intent = getIntent();

        startType = intent.getIntExtra(MainActivity.START_TYPE, 0);

        LL = findViewById(R.id.LLayout);
        LL.post(this::callGame);
    }

    static public Handler tapHandler() {
        return tapHandler;
    }

    private void callGame() {
        Resources resources = getResources();
        config = resources.getConfiguration();

        measureFrameSize();

        cellLength = playWindowSize / GameParams.visibleCellNum;
        OptionConst.V = cellLength / GameParams.fps;

        relativeCenter = new float[]{
                (1 - playerSize) * cellLength / 2,
                (1 - playerSize) * cellLength / 2};

        player = new Player(cellLength, this);

        roadData();

        setMapData();

        mapData = mapDataList[roadPoint[2]];

        mapFrame = new MapFrame(this, config, player, frameWidth, frameHeight);

        battleFrame = new BattleFrame(this, config, frameWidth, frameHeight);

        controllerFrame = new ControllerFrame(player, this, config, frameWidth, frameHeight);

        battleSystem = new BattleSystem(mapFrame, battleFrame, this);

        setToOtherFrame();

        mapFrame.setMapWindows();

        mapFrame.setPlayerImage();

        setFPSView();

        LL.addView(battleFrame.getBattleFrame());
        LL.addView(mapFrame.getFrameLayout());
        LL.addView(controllerFrame.getControllerFL());
        LL.addView(fpsView);

        mapFrame.roadMap(roadPoint);
        player.setRelativePoint(relativeCenter);
        mapFrame.checkNPCPosition();

        GameParams.setPlayerNum(battleSystem.getPlayerNum());

        runnable = () -> {
            lastCallTime = System.currentTimeMillis();
            push(lastCallTime);

            String txt = fps + ":" + player.getWhere();
            fpsView.setText(txt);

            if (player.isInMap()) {
                mapFrame.reDraw(lastCallTime);
            }

            controllerFrame.reDrawStick();

            handler.postDelayed(runnable, GameParams.FrameLate);
        };

        handler.post(runnable);
    }

    private void measureFrameSize() {
        frameWidth = LL.getWidth();
        frameHeight = LL.getHeight();
        Log.d("msg", "tate:" + frameWidth + ":" + frameHeight);
        if (config.orientation == Configuration.ORIENTATION_PORTRAIT) {
            playWindowSize = frameWidth;
        } else {
            playWindowSize = frameHeight;
        }
        playWindowSize_Unit = playWindowSize / 10;
    }

    private void setFPSView() {
        fpsView = new TextView(this);

        lastCallTime = System.currentTimeMillis();
        fpsView.setText("表示");
        fpsView.setTextSize(50);
        fpsView.setY((float) (LL.getHeight() * 0.9));
    }

    private void setToOtherFrame() {
        controllerFrame.setBattleFrame(battleFrame);
        controllerFrame.setMapFrame(mapFrame);

        battleFrame.setButtonsFrame(controllerFrame);
        battleFrame.setMapFrame(mapFrame);
        battleFrame.setPlayer(player);
        battleFrame.setBattleWindows(battleSystem);

        mapFrame.setBattleSystem(battleSystem);
        mapFrame.setButtonsFrame(controllerFrame);

        player.setButtonsFrame(controllerFrame);
        player.setMapFrame(mapFrame);
    }


    private void roadData() {
        myDataBaseHelper = new MyDataBaseHelper(this);
        DataBase = myDataBaseHelper.getReadableDatabase();

        if (startType == MainActivity.start_reset) {
            myDataBaseHelper.deleteTable(DataBase);
            myDataBaseHelper.onCreate(DataBase);
        }

        setAutoSaveFileRow();

        cursor = DataBase.query(
                MyDataBaseHelper.getPlayerTableName(),
                MyDataBaseHelper.PlayerColNames,
                null,
                null,
                null,
                null,
                null);

        cursor.moveToLast();
        int dataNum = cursor.getCount();
        cursor.close();

        if (dataNum != 1) {
            Toast.makeText(this,
                    "データベースないよ"
                    , Toast.LENGTH_SHORT).show();

            MapWindow_Save.addData();
        }

        roadPoint = MapWindow_Save.getSaveData(player);
        player.addAllItem();
    }

    private void setMapData() {
        //getNameにする
        String[] colName = new String[MapData.MAX_BOX_NUM + 1];
        colName[0] = MyDataBaseHelper.MAP_ID;
        for (int j = 0; j < MapData.MAX_BOX_NUM; j++) {
            colName[j + 1] = MyDataBaseHelper.TREASURE_BOX + j;
        }

        Log.d("msg", "MainGame " + MyDataBaseHelper.getMapTableName());
        cursor = DataBase.query(
                MyDataBaseHelper.getMapTableName(),
                colName,
                null,
                null,
                null,
                null,
                null);
        cursor.moveToFirst();

        int itemID;
        int cIndex;
        for (int i = 0; i < MapData.MAP_NUM; i++) {
            cIndex = cursor.getColumnIndex(MyDataBaseHelper.MAP_ID);
            itemID = cursor.getInt(cIndex);

            MapData tmpMap = MapData.getMapData(itemID);

            assert tmpMap != null;
            if (tmpMap.getTreasureBoxes() != null) {
                for (int j = 0; j < tmpMap.getTreasureBoxes().length; j++) {
                    cIndex = cursor.getColumnIndex(MyDataBaseHelper.TREASURE_BOX + j);
                    itemID = cursor.getInt(cIndex);
                    tmpMap.getTreasureBoxes()[j] = itemID;
                }
            }
            mapDataList[i] = tmpMap;
            cursor.moveToNext();
        }
        cursor.close();
    }

    private void push(double time) {
        fpsArray[count] = time;
        count++;
        if (count == fpsArray.length) {
            fps = (int) ((1000 * GameParams.fps) / (fpsArray[fpsArray.length - 1] - fpsArray[0]));
            count = 0;
        }
    }


    public static void setAutoSaveFileRow() {
        Cursor cursor;
        cursor = DataBase.query(
                MyDataBaseHelper.AUTO_PLAYER_INFO_TABLE,
                MyDataBaseHelper.PlayerColNames,
                null,
                null,
                null,
                null,
                null);
        cursor.moveToLast();
        autoSaveRow = cursor.getCount();
        cursor.close();
    }

    public static int getAutoSaveRow() {
        return autoSaveRow;
    }
}
