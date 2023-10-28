package com.sbkinoko.sbkinokorpg.mapframe;

import static com.sbkinoko.sbkinokorpg.gameparams.GameParams.X_axis;

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

import com.sbkinoko.sbkinokorpg.MainGame;
import com.sbkinoko.sbkinokorpg.OptionConst;
import com.sbkinoko.sbkinokorpg.R;
import com.sbkinoko.sbkinokorpg.battleframe.BattleSystem;
import com.sbkinoko.sbkinokorpg.controller.ControllerFrame;
import com.sbkinoko.sbkinokorpg.gameparams.Axis;
import com.sbkinoko.sbkinokorpg.gameparams.BattleResult;
import com.sbkinoko.sbkinokorpg.gameparams.EscapeFlag;
import com.sbkinoko.sbkinokorpg.gameparams.EventBattleFlag;
import com.sbkinoko.sbkinokorpg.gameparams.GameParams;
import com.sbkinoko.sbkinokorpg.gameparams.MoveState;
import com.sbkinoko.sbkinokorpg.mapframe.event.MapEvent;
import com.sbkinoko.sbkinokorpg.mapframe.map.bgcell.MakeCellFactory;
import com.sbkinoko.sbkinokorpg.mapframe.map.mapdata.DefeatedWarp;
import com.sbkinoko.sbkinokorpg.mapframe.map.mapdata.MapData;
import com.sbkinoko.sbkinokorpg.mapframe.map.mapdata.MapId;
import com.sbkinoko.sbkinokorpg.mapframe.map.mapdata.TestField;
import com.sbkinoko.sbkinokorpg.mapframe.player.Player;
import com.sbkinoko.sbkinokorpg.mapframe.player.PlayerImageTouchListener;
import com.sbkinoko.sbkinokorpg.mapframe.player.PlayerView;
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
    private PlayerView playerView;
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

    private final ImageView black;

    private final MapViewModel mapViewModel;

    public MapViewModel getMapViewModel() {
        return mapViewModel;
    }

    public MapFrame(Context context,
                    Configuration config,
                    Player player1,
                    int frameWidth,
                    int frameHeight,
                    ControllerFrame controllerFrame) {
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
        this.playerView = new PlayerView(
                context,
                player.getPlayerSize(),
                player.getMoveState(),
                new PlayerImageTouchListener(
                        player,
                        this,
                        controllerFrame
                )
        );

        this.controllerFrame = controllerFrame;

        mapBackGroundCellMatrix = new MapBackGroundCellMatrix(context, frameLayout, player, this);
        mapBackGroundCellMatrix.resetBackGroundCellText();


        black = new ImageView(context);
        black.setLayoutParams(
                new ViewGroup.LayoutParams(
                        MainGame.playWindowSize,
                        MainGame.playWindowSize
                )
        );
        black.setBackground(ResourcesCompat.getDrawable(res, R.drawable.black_image, null));

        mapViewModel = new MapViewModel(context, player, frameLayout);

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

    public MapBackGroundCellMatrix getBgcMatrix() {
        return mapBackGroundCellMatrix;
    }

    public void setPlayerImage() {
        this.frameLayout.addView(playerView.getImageView());
        this.frameLayout.addView(playerView.getTouchActionView());
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

    public void openFromBattle(
            EventBattleFlag eventBattleFlag,
            BattleResult battleResult
    ) {
        frameLayout.setVisibility(View.VISIBLE);
        if (eventBattleFlag.isEventBattle()) {
            player.proceedNowEventFlag(battleResult == BattleResult.Win);
            doAction();
        } else {
            if (battleResult == BattleResult.Lose) {
                //fixme map battleで使う処理をbattleに任せきりなのはよくない
                battleSystem.processForGameOver();
                //ゲームオーバーになったときの処理
                //そのまま完全復活かセーブデータからか
                if (player.getLastTownId() != null) {
                    int[] loadPoint = ((DefeatedWarp) (player.getLastTownId().getMapData())).getDefeatedWarpPoint();
                    loadMap(loadPoint);
                }
            }
        }

        Toast.makeText(context, "" + battleResult, Toast.LENGTH_SHORT).show();
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

    public void loadFirstMap(int[] roadPoint, float[] relativeCenter) {
        loadMap(roadPoint);
        player.setRelativePoint(relativeCenter);
        mapViewModel.checkNPCPosition();
    }

    public void loadMap(int[] loadPoint) {
        beforeLoadBackground();
        MainGame.tapHandler().post(() -> loadBackGround(loadPoint));
    }

    private void beforeLoadBackground() {
        loadFinishFlag = false;
        cantMove = true;
        setBlack();
        setLoadingText();
    }

    TextView loadingMessage;
    Runnable loadingRunnable;

    Runnable removeBlackOutRunnable = new Runnable() {
        @Override
        public void run() {
            if (loadFinishFlag) {
                frameLayout.removeView(black);
                frameLayout.removeView(loadingMessage);
                MainGame.tapHandler().removeCallbacks(loadingRunnable);
                cantMove = false;
            } else {
                MainGame.tapHandler().postDelayed(this, 100);
            }
        }
    };

    private void setBlack() {
        frameLayout.addView(black);
        MainGame.tapHandler().postDelayed(removeBlackOutRunnable, 1000);
    }

    private void setLoadingText() {
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

    private boolean loadFinishFlag = false;

    //todo loadPointクラスを作る
    private void loadBackGround(int[] roadPoint) {
        MapData nextMap = MainGame.mapDataList[roadPoint[2]];
        MapId nextMapId = MapId.convertIntToMapId(roadPoint[2]);
        if (nextMapId.isCanBeLastTown()
                && player != null) {
            //todo セーブ処理を後で書く
            player.setLastTownId(nextMapId);
            Toast.makeText(context, "update LastTown" + nextMapId, Toast.LENGTH_SHORT).show();
        }
        mapViewModel.setNowMap(nextMap);
        mapBackGroundCellMatrix.setNowMap(mapViewModel.getNowMap());

        //fixme mapDataにループの情報を持たせる
        loopFlag = (mapViewModel.getNowMap() instanceof TestField);

        mapBackGroundCellMatrix.roadBackGround(roadPoint[Axis.Y.id], roadPoint[X_axis]);

        player.goCenter();
        playerView.setImageViewPosition(
                player.getPlayerPosition());

        mapChangeTime = System.currentTimeMillis();

        mapViewModel.resetNPC(
                mapBackGroundCellMatrix.getBGC_player_in().getMapPoint()
        );

        loadFinishFlag = true;

    }

    private void loadBackGroundWithSave(int[] roadPoint) {
        loadBackGround(roadPoint);
        mapSaveWindow.save(true);
    }


    //必ずせーぶされるのでどうするか考える
    public void moveMap(int[] loadPoint) {
        beforeLoadBackground();
        MainGame.tapHandler().post(() -> loadBackGroundWithSave(loadPoint));
    }

    public MapData getNowMap() {
        return mapViewModel.getNowMap();
    }


    private final int ImageChangeTime = GameParams.ImageChangeTime;
    private double lastImageChangeTime;

    public void reDrawPlayer() {
        playerView.reDraw();
    }

    public void reDraw(double lastCallTime) {
        if (cantMove) return;
        boolean _cantMove = !(lastCallTime - mapChangeTime > GameParams.canMoveTime);

        mapViewModel.getNpcMatrix().moveNPC(mapTextBoxWindow.isOpen());

        if (!_cantMove && (player.canMove() || player.getAutoMovingFlag())) {
            movePlayer();
            checkCellEvent();
        }

        if (lastCallTime - lastImageChangeTime > ImageChangeTime
        ) {
            player.setDir();
            playerView.changeImage(
                    player.canAction(),
                    player.getActionViewPosition(),
                    player.getDir()
            );
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
            mapViewModel.getNpcMatrix().scrollNPC(scroll);
            scrollBGC(scroll);
        }

        player.moveInMap(getActualMoveDist(scroll));

        playerView.setImageViewPosition(
                player.getPlayerPosition()
        );

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

        if (actualScroll[X_axis] && actualScroll[Axis.Y.id]) {
            for (int y = 0; y < GameParams.allCellNum; y++) {
                for (int x = 0; x < GameParams.allCellNum; x++) {
                    mapBackGroundCellMatrix.getBGC(y, x).scroll(X_axis);
                    mapBackGroundCellMatrix.getBGC(y, x).scroll(Axis.Y.id);
                }
            }
            return;
        }

        int axis;
        if (actualScroll[X_axis]) {
            axis = X_axis;
        } else {
            axis = Axis.Y.id;
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
        if (player.getCanMoveDir()[Axis.Y.id] && !scroll[Axis.Y.id]) {
            tmpV[Axis.Y.id] = player.getV()[Axis.Y.id];
        }
        return tmpV;
    }

    /**
     * @param flags 今見たいflag
     * @return どちらかがtrueならtrueを返す
     */
    private boolean isFlagTrue(boolean[] flags) {
        return flags[X_axis] || flags[Axis.Y.id];
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
        int npc_ID = mapViewModel.getNpcMatrix().getNPCCollision();

        if (player.getCanMoveDir()[X_axis] && player.getCanMoveDir()[Axis.Y.id]) {
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
            return !mapViewModel.getNpcMatrix().isColliding(npc_ID);//斜めに移動できるからそのまま斜めに移動
        }

        return false;
    }

    private void setMoveDir() {
        if (Math.abs(player.getV()[X_axis]) < Math.abs(player.getV()[Axis.Y.id])) {
            player.setCanNotMove_Axis(X_axis);
        } else {
            player.setCanNotMove_Axis(Axis.Y.id);
        }
    }

    public void setPlayerMoveState(MoveState moveState) {
        player.setMoveState(moveState);
        playerView.setMoveStateImage(player.getMoveState());
    }


    public void checkCellEvent() {
        if (!isAllMenuClosed()) {
            return;
        }

        if (player.getMoveState() == MoveState.MoveState_Sky) {
            return;
        }

        //全身が入ったときのマスの処理
        if (player.isAllPartIn()
                && player.isMoved()) {
            new MapEvent(this).autoAction(
                    mapBackGroundCellMatrix.getBGC_player_in().getAutoAction());
        }
    }

    private void encounterMons() {
        int mapX = mapBackGroundCellMatrix.getPlayerMapXY().getX();
        int mapY = mapBackGroundCellMatrix.getPlayerMapXY().getY();
        // map.isOutOfMapを使えそう
        if (mapX < 0
                || mapViewModel.getNowMap().getMap()[0].length <= mapX
                || mapY < 0
                || mapViewModel.getNowMap().getMap().length <= mapY) {
            return;
        }
        int monsType = mapViewModel.getNowMap().getMonsType(mapY, mapX);
        if (monsType == 0) {
            return;
        }

        int cellType = mapViewModel.getNowMap().getCellType(mapY, mapX);
        if (isAppMons(cellType)) {
            startBattle(cellType,
                    monsType,
                    EscapeFlag.Can,
                    EventBattleFlag.NotEvent);
        }
    }

    private boolean isAppMons(int cellType) {
        return OptionConst.encounter < MakeCellFactory.make(cellType, context, player).getMonsRnd();
    }

    public void startBattle(
            int CELL_TYPE,
            int battleID,
            EscapeFlag escapeFlag,
            EventBattleFlag eventBattleFlag
    ) {
        int[] appMonsData = getAppMonsData(battleID);

        if (appMonsData == null) {
            return;
        }

        battleSystem.startBattle(
                appMonsData,
                MakeCellFactory.make(CELL_TYPE, context, player).getBattleBG(),
                escapeFlag,
                eventBattleFlag
        );//対戦モンスターの決定
    }

    private int[] getAppMonsData(int battleID) {
        int _battleID = battleID - 1;
        int[] appMonsData;
        if (mapViewModel.getNowMap().canBeSkyMonster(_battleID)) {
            _battleID -= MapData.SKY_MONS;
            if (player.getMoveState() == MoveState.MoveState_Sky) {
                appMonsData = mapViewModel.getNowMap().getAppSkyMonster(_battleID).getMonsterIDs();
            } else {
                appMonsData = mapViewModel.getNowMap().getAppGroundMonsterFromSky(_battleID).getMonsterIDs();
            }
        } else {
            if (player.getMoveState() == MoveState.MoveState_Sky) {
                return null;
            }
            appMonsData = mapViewModel.getNowMap().getAppGroundMonster(_battleID).getMonsterIDs();
        }
        return appMonsData;
    }

    public void checkAction() {
        player.setCanAction(canAction() || mapViewModel.getNpcMatrix().canAction());
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
            new MapEvent(this).nextEvent(mapViewModel.getNpcMatrix().getTalkingNPC());
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
                        if (player.getMoveState() == MoveState.MoveState_Sky) {
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
                    mapTapped[Axis.Y.id] = e.getY();

                    runnable = () -> {

                        float distance;
                        distance = (float) Math.sqrt(
                                Math.pow((mapTapped[X_axis] - player.getCenter()[X_axis]), 2)
                                        + Math.pow((mapTapped[Axis.Y.id] - player.getCenter()[Axis.Y.id]), 2)
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
