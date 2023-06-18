package com.sbkinoko.sbkinokorpg.mapframe;

import static com.sbkinoko.sbkinokorpg.GameParams.X_axis;
import static com.sbkinoko.sbkinokorpg.GameParams.Y_axis;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.res.ResourcesCompat;

import com.sbkinoko.sbkinokorpg.GameParams;
import com.sbkinoko.sbkinokorpg.MainGame;
import com.sbkinoko.sbkinokorpg.OptionConst;
import com.sbkinoko.sbkinokorpg.R;
import com.sbkinoko.sbkinokorpg.battleframe.BattleSystem;
import com.sbkinoko.sbkinokorpg.controller.ControllerFrame;
import com.sbkinoko.sbkinokorpg.mapframe.event.MapEvent;
import com.sbkinoko.sbkinokorpg.mapframe.map.bgcell.MakeCellFactory;
import com.sbkinoko.sbkinokorpg.mapframe.map.mapdata.MapData;
import com.sbkinoko.sbkinokorpg.mapframe.map.mapdata.TestField;
import com.sbkinoko.sbkinokorpg.mapframe.npc.NPC;
import com.sbkinoko.sbkinokorpg.mapframe.npc.NPCMatrix;
import com.sbkinoko.sbkinokorpg.mapframe.window.MapWindow_Choice;
import com.sbkinoko.sbkinokorpg.mapframe.window.MapWindow_Save;
import com.sbkinoko.sbkinokorpg.mapframe.window.MapWindow_TextBox;
import com.sbkinoko.sbkinokorpg.mapframe.window.window_main.Window_Main;
import com.sbkinoko.sbkinokorpg.mapframe.window.window_option.Window_Option;
import com.sbkinoko.sbkinokorpg.mapframe.window.window_set.GroupOfWindows;
import com.sbkinoko.sbkinokorpg.mapframe.window.window_set.WindowDetail;
import com.sbkinoko.sbkinokorpg.mapframe.window.window_set.WindowExplanation;
import com.sbkinoko.sbkinokorpg.mapframe.window.window_set.WindowPlayer;
import com.sbkinoko.sbkinokorpg.mapframe.window.window_set.amount.WindowAmount;
import com.sbkinoko.sbkinokorpg.mapframe.window.window_set.list_strategy.shopping.for_sell.WindowSelectItemKind;
import com.sbkinoko.sbkinokorpg.window.GameWindow;

public class MapFrame {
    private final FrameLayout frameLayout;
    private final Context context;
    private final MapBackGroundCellMatrix mapBackGroundCellMatrix;

    public Player player;
    private MapData nowMap;
    public long mapChangeTime;
    public static boolean cantMove;
    private static boolean loopFlag = true;

    Runnable runnable;

    private MapWindow_TextBox mapTextBoxWindow;

    public MapWindow_TextBox getMapTextBoxWindow() {
        return mapTextBoxWindow;
    }

    public Window_Main mapMainMenuWindow;
    public Window_Option window_option;
    public MapWindow_Save mapSaveWindow;
    private WindowDetail window__detail;

    public WindowDetail getMapWindow_list_detail() {
        return window__detail;
    }

    public WindowPlayer window_player;
    public WindowExplanation window_explanation;
    private MapWindow_Choice mapWindow_choice;

    public MapWindow_Choice getMapWindow_choice() {
        return mapWindow_choice;
    }

    public GameWindow[] mapWindows;

    public BattleSystem battleSystem;

    public void setBattleSystem(BattleSystem battleSystem) {
        this.battleSystem = battleSystem;
    }

    public ControllerFrame controllerFrame;
    private final NPCMatrix npcMatrix;

    private final ImageView black;

    public NPCMatrix getNpcMatrix() {
        return npcMatrix;
    }

    public NPC[] getNpcList() {
        return npcMatrix.getNpcList();
    }

    public MapFrame(Context context,
                    Configuration config, Player player1,
                    int frameWidth, int frameHeight) {
        this.context = context;
        frameLayout = new FrameLayout(context);

        Resources res = context.getResources();
        frameLayout.setBackground(ResourcesCompat.getDrawable(res, R.drawable.frame_line, null));
        frameLayout.setId(GameParams.ID_MapMain);
        frameLayout.setOnTouchListener(new MapTouchListener());

        frameLayout.setX(getLeftSide(config, frameWidth, frameHeight));

        frameLayout.setLayoutParams(
                new ViewGroup.LayoutParams(
                        MainGame.playWindowSize,
                        MainGame.playWindowSize
                ));

        this.player = player1;

        mapBackGroundCellMatrix = new MapBackGroundCellMatrix(context, frameLayout, player, this);
        mapBackGroundCellMatrix.resetBackGroundCellText();

        npcMatrix = new NPCMatrix(player, context, frameLayout);
        black = new ImageView(context);
        black.setLayoutParams(
                new ViewGroup.LayoutParams(
                        MainGame.playWindowSize,
                        MainGame.playWindowSize
                )
        );
        black.setBackground(ResourcesCompat.getDrawable(res, R.drawable.black_image, null));

    }

    private float getLeftSide(Configuration config, int frameWidth, int frameHeight) {
        if (config.orientation == Configuration.ORIENTATION_PORTRAIT) {
            Toast.makeText(context, "TATE", Toast.LENGTH_SHORT).show();
            return 0;
        } else {
            Toast.makeText(context, "yoko", Toast.LENGTH_SHORT).show();
            return (float) (frameWidth - frameHeight) / 2;
        }
    }

    int getMapWidth() {
        return nowMap.getWidth();
    }

    int getHeight() {
        return nowMap.getHeight();
    }

    public MapBackGroundCellMatrix getBgcMatrix() {
        return mapBackGroundCellMatrix;
    }

    public void setPlayerImage() {
        this.frameLayout.addView(player.getImageView());
        this.frameLayout.addView(player.getTouchActionView());
    }

    GroupOfWindows groupOfWindows;

    WindowSelectItemKind selectSellItemKind;

    public WindowSelectItemKind getSelectSellItemKind() {
        return selectSellItemKind;
    }

    public void setMapWindows() {
        groupOfWindows = new GroupOfWindows(context);
        groupOfWindows.setPlayerInfo(player, battleSystem.getPlayers());

        mapWindows = new GameWindow[]{
                mapTextBoxWindow =
                        new MapWindow_TextBox(context, this),
                window_option
                        = new Window_Option(context, this),
                mapSaveWindow
                        = new MapWindow_Save(context, this, player),
                window__detail
                        = new WindowDetail(context, groupOfWindows, this),
                window_player
                        = new WindowPlayer(context, this, groupOfWindows),
                window_explanation
                        = new WindowExplanation(context, this, groupOfWindows),
                mapMainMenuWindow
                        = new Window_Main(context, this),
                mapWindow_choice
                        = new MapWindow_Choice(context, this, player),
                selectSellItemKind = new WindowSelectItemKind(context, groupOfWindows, this)
        };

        groupOfWindows.setWindows(
                window__detail,
                window_player,
                window_explanation,
                mapTextBoxWindow,
                selectSellItemKind);

    }

    public WindowAmount getAmountWindow() {
        return groupOfWindows.getWindowAmount();
    }

    public GroupOfWindows getWindowGroupInfo() {
        return groupOfWindows;
    }

    public void clickActiveWindow(View view, MotionEvent event) {
        if (openingWindow == amountID) {
            getAmountWindow().OnBtClicked(view, event);
            return;
        }
        mapWindows[openingWindow].OnBtClicked(view, event);
    }

    public FrameLayout getFrameLayout() {
        return this.frameLayout;
    }

    public void setBattleFrame(BattleSystem battleSystem) {
        this.battleSystem = battleSystem;
    }

    public void setButtonsFrame(ControllerFrame controllerFrame1) {
        this.controllerFrame = controllerFrame1;
    }

    public float[] relativePlayerPoint() {
        float[] relativePoint = new float[2];
        MapBackgroundCell tmpBGCell = mapBackGroundCellMatrix.getBGC_player_in();
        for (int axis = 0; axis < 2; axis++) {
            relativePoint[axis] = (player.getPoints()[axis][0]
                    - tmpBGCell.getViewPoint()[axis][0]);
        }
        return relativePoint;
    }

    public static boolean getLoopFlag() {
        return loopFlag;
    }

    public int getMapID() {
        return nowMap.getMapID();
    }

    TextView loadingMessage;
    Runnable loadingRunnable;

    private void setBlack() {
        frameLayout.addView(black);

        cantMove = true;

        Runnable blackOutRunnable = () -> {
            cantMove = false;
            frameLayout.removeView(black);
            frameLayout.removeView(loadingMessage);
            MainGame.tapHandler().removeCallbacks(loadingRunnable);
        };

        MainGame.tapHandler().postDelayed(blackOutRunnable, 2000);
        loadingMessage = new TextView(context);
        loadingMessage.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        loadingMessage.setText("loading");
        loadingMessage.setTextSize(50);
        loadingMessage.setBackgroundColor(context.getResources().getColor(R.color.red));
        frameLayout.addView(loadingMessage);

        loadingRunnable = new Runnable() {
            @Override
            public void run() {
                loadingMessage.setText(loadingMessage.getText() + ".");
                MainGame.tapHandler().postDelayed(this, 100);
            }
        };

        MainGame.tapHandler().postDelayed(
                loadingRunnable,
                100);
    }


    public void roadMap(int[] roadPoint) {
        setBlack();
        this.nowMap = MainGame.mapDataList[roadPoint[2]];
        mapBackGroundCellMatrix.setNowMap(nowMap);
        loopFlag = (nowMap instanceof TestField);

        mapBackGroundCellMatrix.roadBackGround(roadPoint[Y_axis], roadPoint[X_axis]);

        player.goCenter();

        mapChangeTime = System.currentTimeMillis();

        npcMatrix.remove();

        if (nowMap.getNpcData() == null) {
            return;
        }

        npcMatrix.setNpcList(nowMap.getNpcData(), mapBackGroundCellMatrix.getBGC_player_in().getMapPoint());

    }

    public void checkNPCPosition() {
        npcMatrix.avoidPlayer();
    }

    public void changeNowMap(int[] roadPoint) {
        roadMap(roadPoint);
        mapSaveWindow.save(true);
    }

    public MapData getNowMap() {
        return nowMap;
    }


    private int ImageChangeTime = GameParams.ImageChangeTime;
    private double lastImageChangeTime;

    public void reDraw(double lastCallTime) {
        if (cantMove) return;
        boolean _cantMove = !(lastCallTime - mapChangeTime > GameParams.canMoveTime);

        npcMatrix.moveNPC(mapTextBoxWindow.isOpen());

        if (!_cantMove && (player.canMove() || player.getAutoMovingFlag())) {
            movePlayer();
            checkCellEvent();
        }

        if (lastCallTime - lastImageChangeTime > ImageChangeTime
        ) {
            player.changeImage();
            checkAction();
            lastImageChangeTime = lastCallTime;
        }
    }

    /**
     * 移動できる方向に実際に移動する関数
     */
    private void movePlayer() {

        player.setCollisionPoints(player.getV());

        if (player.getAutoMovingFlag()) {
            player.decDistToGoal();
        } else {
            checkAfterPosition();
            if (!isFlagTrue(player.getCanMoveDir())) {
                return;//xyどちらにも移動できなければ
            }
        }

        boolean[] scroll = new boolean[2];
        for (int axis = 0; axis < 2; axis++) {
            scroll[axis] = isScrolling(axis);
        }

        if (isFlagTrue(scroll)) {
            npcMatrix.scrollNPC(scroll);
            scrollBGC(scroll);
        }

        player.moveInMap(getActualMoveDist(scroll));

        mapBackGroundCellMatrix.setAllInFlag();

        if (player.isEncounterMons()) {
            encounterMons();
        }
    }

    private void scrollBGC(boolean[] scroll) {
        boolean[] actualScroll = new boolean[2];
        for (int axis = 0; axis < 2; axis++) {
            if (player.getCanMoveDir()[axis] && scroll[axis]) {
                actualScroll[axis] = true;
            }
        }

        if (!isFlagTrue(actualScroll)) {
            return;
        }

        if (actualScroll[X_axis] && actualScroll[Y_axis]) {
            for (int y = 0; y < GameParams.allCellNum; y++) {
                for (int x = 0; x < GameParams.allCellNum; x++) {
                    mapBackGroundCellMatrix.getBGC(y, x).scroll(X_axis);
                    mapBackGroundCellMatrix.getBGC(y, x).scroll(Y_axis);
                }
            }
            return;
        }

        int axis;
        if (actualScroll[X_axis]) {
            axis = X_axis;
        } else {
            axis = Y_axis;
        }

        for (int y = 0; y < GameParams.allCellNum; y++) {
            for (int x = 0; x < GameParams.allCellNum; x++) {
                mapBackGroundCellMatrix.getBGC(y, x).scroll(axis);
            }
        }
    }

    /**
     * @param scroll 各方向のスクロールflag
     * @return 主人公が画面内で移動した距離
     */
    private int[] getActualMoveDist(boolean[] scroll) {
        int[] tmpV = {0, 0};
        if (player.getCanMoveDir()[X_axis] && !scroll[X_axis]) {
            tmpV[X_axis] = player.getV()[X_axis];
        }
        if (player.getCanMoveDir()[Y_axis] && !scroll[Y_axis]) {
            tmpV[Y_axis] = player.getV()[Y_axis];
        }
        return tmpV;
    }

    /**
     * @param flags 今見たいflag
     * @return どちらかがtrueならtrueを返す
     */
    private boolean isFlagTrue(boolean[] flags) {
        return flags[X_axis] || flags[Y_axis];
    }

    /**
     * @param axis 今考える軸
     * @return scrollするならtrue
     */
    boolean isScrolling(int axis) {
        int center = player.getCenter()[axis];
        int v = player.getV()[axis];
        return ((MainGame.playWindowSize * GameParams.MapScrollLarge <= center) &&
                0 < v) ||
                ((center < MainGame.playWindowSize * GameParams.MapScrollSmall) &&
                        (v < 0));//画面の中心なのでスクロールはなし
    }

    /**
     * 移動後の位置が実際に立ち入り可能かチェックする
     */
    public void checkAfterPosition() {
        player.resetCanMoveDir();
        boolean reCheckFlag = mapBackGroundCellMatrix.checkCellsCollision();
        int npc_ID = npcMatrix.getNPCCollision();

        if (player.getCanMoveDir()[X_axis] && player.getCanMoveDir()[Y_axis]) {
            if (isColliding(reCheckFlag, npc_ID)) {
                setMoveDir();
            }
        }
    }

    private boolean isColliding(boolean reCheckFlag, int npc_ID) {
        if (reCheckFlag) {
            //斜めには行けないのはcellのせい
            return !mapBackGroundCellMatrix.getBGC(player.getReCheckCell()).isColliding();//斜めに移動できるからそのまま斜めに移動
        }

        if (npc_ID != -1) {
            //斜めには行けないのはnpcのせい
            return !npcMatrix.isColliding(npc_ID);//斜めに移動できるからそのまま斜めに移動
        }

        return false;
    }

    private void setMoveDir() {
        if (Math.abs(player.getV()[X_axis]) < Math.abs(player.getV()[Y_axis])) {
            player.setCanNotMove_Axis(X_axis);
        } else {
            player.setCanNotMove_Axis(Y_axis);
        }
    }


    public void checkCellEvent() {
        if (!isAllMenuClosed()) {
            return;
        }

        if (player.getMoveState() == GameParams.MoveState_Sky) {
            return;
        }

        if (mapBackGroundCellMatrix.getBGC_player_in().isOutOfMapRange(nowMap)) {
            return;//範囲外なのでイベントはない
        }


        //全身が入ったときのマスの処理
        if (player.isAllPartIn()
                && player.isMoved()) {
            new MapEvent(this).autoAction(
                    mapBackGroundCellMatrix.getBGC_player_in().getAutoAction());
        }
    }

    private void encounterMons() {
        int mapX = mapBackGroundCellMatrix.getPlayerMapXY()[X_axis];
        int mapY = mapBackGroundCellMatrix.getPlayerMapXY()[Y_axis];
        if (mapX < 0
                || nowMap.getMap()[0].length <= mapX
                || mapY < 0
                || nowMap.getMap().length <= mapY) {
            return;
        }
        int monsType = nowMap.getMonsType(mapY, mapX);
        if (monsType == 0) {
            return;
        }

        int cellType = nowMap.getCellType(mapY, mapX);
        if (isAppMons(cellType)) {
            startBattle(cellType,
                    monsType,
                    true);
        }
    }

    private boolean isAppMons(int cellType) {
        return OptionConst.encounter < MakeCellFactory.make(cellType, context, player).getMonsRnd();
    }

    public void startBattle(int CELL_TYPE, int battleID, boolean canEscape) {
        int[] appMonsData = getAppMonsData(battleID);

        if (appMonsData == null) {
            return;
        }

        battleSystem.startBattle(
                appMonsData,
                MakeCellFactory.make(CELL_TYPE, context, player).getBattleBG(),
                canEscape
        );//対戦モンスターの決定
    }

    private int[] getAppMonsData(int battleID) {
        int _battleID = battleID - 1;
        int[] appMonsData;
        if (nowMap.canBeSkyMonster(_battleID)) {
            _battleID -= MapData.SKY_MONS;
            if (player.getMoveState() == GameParams.MoveState_Sky) {
                appMonsData = nowMap.getAppSkyMonster(_battleID).getMonsterIDs();
            } else {
                appMonsData = nowMap.getAppGroundMonsterFromSky(_battleID).getMonsterIDs();
            }
        } else {
            if (player.getMoveState() == GameParams.MoveState_Sky) {
                return null;
            }
            appMonsData = nowMap.getAppGroundMonster(_battleID).getMonsterIDs();
        }
        return appMonsData;
    }

    public void checkAction() {
        player.setCanAction(canAction() || npcMatrix.canAction());
    }

    public boolean canAction() {
        double[] offset = player.getActionCollision();
        int[] tmpAP = new int[2];
        for (int axis = 0; axis < 2; axis++) {
            tmpAP[axis] = (int) (offset[axis] * player.getPlayerSize());
        }
        player.setCollisionPoints(tmpAP);
        return canObjectAction();

    }

    private boolean canObjectAction() {
        return mapBackGroundCellMatrix.checkObjectAction();
    }

    public void closeAllMenu() {
        for (GameWindow mapWindow : mapWindows) {
            mapWindow.closeMenu();
        }
    }

    int openingWindow = -1;

    int amountID = 100;

    public boolean isAllMenuClosed() {
        openingWindow = -1;
        for (int i = 0; i < mapWindows.length; i++) {
            if (mapWindows[i].isOpen()) {
                openingWindow = i;
                return false;
            }
        }
        if (getAmountWindow() != null
                && getAmountWindow().isOpen()) {
            openingWindow = amountID;
            return false;
        }
        return true;
    }

    public void checkNextEvent() {
        if (player.isNextEventFlag()) {
            new MapEvent(this).nextEvent(npcMatrix.getTalkingNPC());
        }
    }

    private void doAction() {
        if (!player.canAction()) {
            return;
        }
        new MapEvent(this).doEvent();
        player.setCanMove(false);
    }

    public void onClick(View view, MotionEvent event) {
        if (player.getAutoMovingFlag() || cantMove) {
            return;
        }

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                switch (view.getId()) {
                    case GameParams.ID_btRIGHT:
                        player.setV(new int[]{0, OptionConst.getActualV()});
                        break;
                    case GameParams.ID_btLEFT:
                        player.setV(new int[]{0, -OptionConst.getActualV()});
                        break;
                    case GameParams.ID_btUP:
                        player.setV(new int[]{-OptionConst.getActualV(), 0});
                        break;
                    case GameParams.ID_btDOWN:
                        player.setV(new int[]{OptionConst.getActualV(), 0});
                        break;
                    case GameParams.ID_btMenu:
                        mapMainMenuWindow.openMenu();
                        player.setCanMove(false);
                        break;
                    case GameParams.ID_btB:
                        if (player.getMoveState() == GameParams.MoveState_Sky) {
                            new MapEvent(this).goGround();
                        }
                        break;
                    case GameParams.ID_btA:
                        doAction();
                        break;
                    default:
                        break;
                }
                break;

            case MotionEvent.ACTION_UP:
                switch (view.getId()) {
                    case GameParams.ID_btRIGHT:
                    case GameParams.ID_btLEFT:
                    case GameParams.ID_btUP:
                    case GameParams.ID_btDOWN:
                        player.setCanMove(false);
                        break;
                }
                break;
        }
    }

    private class MapTouchListener implements View.OnTouchListener {

        @Override
        public boolean onTouch(View v, MotionEvent e) {
            if (cantMove) return true;

            int[] V = new int[2];
            float[] mapTapped = new float[2];

            for (GameWindow mapWindow : mapWindows) {
                if (mapWindow.isOpen()) {
                    controllerFrame.useBtB(e);
                    return true;
                }
            }
            switch (e.getAction()) {
                case MotionEvent.ACTION_DOWN:
                case MotionEvent.ACTION_MOVE:

                    MainGame.removeTapHandler(runnable);
                    player.setCanMove(true);
                    mapTapped[X_axis] = e.getX();
                    mapTapped[Y_axis] = e.getY();

                    runnable = () -> {

                        float distance;
                        distance = (float) Math.sqrt(
                                Math.pow((mapTapped[X_axis] - player.getCenter()[X_axis]), 2)
                                        + Math.pow((mapTapped[Y_axis] - player.getCenter()[Y_axis]), 2)
                        );
                        int vParam = OptionConst.getActualV();

                        for (int axis = 0; axis < 2; axis++) {
                            float tap = mapTapped[axis];
                            int center = player.getCenter()[axis];
                            float vf = (tap - center) / distance * vParam;
                            if ((0 < vf && tap < center + vf)
                                    || (vf < 0 && center + vf < tap)) {
                                vf = tap - center;
                            }
                            V[axis] = (int) vf;
                        }
                        player.setV(V);

                        MainGame.getTapHandler().postDelayed(runnable, GameParams.FrameLate);
                    };

                    MainGame.getTapHandler().post(runnable);

                    break;

                case MotionEvent.ACTION_UP:
                    MainGame.removeTapHandler(runnable);
                    player.setCanMove(false);
                    break;
            }
            v.performClick();
            return true;
        }
    }
}
