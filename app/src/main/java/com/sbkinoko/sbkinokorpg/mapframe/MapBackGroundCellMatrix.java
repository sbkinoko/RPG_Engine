package com.sbkinoko.sbkinokorpg.mapframe;

import static com.sbkinoko.sbkinokorpg.GameParams.X_axis;
import static com.sbkinoko.sbkinokorpg.GameParams.Y_axis;

import android.content.Context;
import android.util.Log;
import android.widget.FrameLayout;

import com.sbkinoko.sbkinokorpg.GameParams;
import com.sbkinoko.sbkinokorpg.MainGame;
import com.sbkinoko.sbkinokorpg.OptionConst;
import com.sbkinoko.sbkinokorpg.mapframe.event.MapEventID;
import com.sbkinoko.sbkinokorpg.mapframe.map.mapdata.MapData;
import com.sbkinoko.sbkinokorpg.mapframe.player.Player;

public class MapBackGroundCellMatrix {

    /**
     * playerがいるcellから上下左右に捜索する範囲
     */
    private static final int checkArea = 1;

    BGC_Strategy bgcStrategy;
    MapData nowMap;
    private final MapBackgroundCell[][] mapBackgroundCells = new MapBackgroundCell[GameParams.allCellNum][GameParams.allCellNum];
    private final Player player;
    Context context;

    MapBackGroundCellMatrix(Context context, FrameLayout frameLayout, Player player1, MapFrame mapFrame1) {
        this.context = context;
        for (int i = 0; i < GameParams.allCellNum; i++) {
            for (int j = 0; j < GameParams.allCellNum; j++) {
                mapBackgroundCells[i][j] =
                        new MapBackgroundCell(context, frameLayout, player1,
                                mapFrame1, i, j);
            }
        }
        this.player = player1;
    }

    public void setNowMap(MapData mapData) {
        nowMap = mapData;
    }

    public void reDraw() {
        for (MapBackgroundCell[] mapBackgroundCell : mapBackgroundCells) {
            for (MapBackgroundCell backgroundCell : mapBackgroundCell) {
                backgroundCell.reDraw();
            }
        }
    }

    public int[] getPlayerMapXY() {
        return getBGC_player_in().getMapPoint();
    }

    public void resetBackGroundCellText() {
        OptionConst.ResetVisibilityStrategy resetVisibilityStrategy =
                OptionConst.getResetVisibleStrategy(mapBackgroundCells);

        for (int y = 0; y < mapBackgroundCells.length; y++) {
            for (int x = 0; x < mapBackgroundCells[y].length; x++) {
                resetVisibilityStrategy.setVisibility(y, x);
            }
        }
    }

    public void setBGImage() {
        for (int y = 0; y < GameParams.allCellNum; y++) {
            for (int x = 0; x < GameParams.allCellNum; x++) {
                mapBackgroundCells[y][x].setData(nowMap);
            }
        }
    }

    public void roadBackGround(int mapY, int mapX) {
        MapBackgroundCell tmpBGCell;
        for (int y = 0; y < GameParams.allCellNum; y++) {
            for (int x = 0; x < GameParams.allCellNum; x++) {
                tmpBGCell = mapBackgroundCells[y][x];
                float[] tmpPoint = new float[2];
                tmpPoint[Y_axis] = y * MainGame.cellLength;
                tmpPoint[X_axis] = x * MainGame.cellLength;
                tmpBGCell.setViewPoint(tmpPoint);

                int[] mapPoint = new int[2];
                mapPoint[Y_axis] = mapY - (GameParams.visibleCellNum - 1) / 2 + y;
                mapPoint[X_axis] = mapX - (GameParams.visibleCellNum - 1) / 2 + x;
                tmpBGCell.setMapPoint(mapPoint);
            }
        }

        setBGImage();

        setText();
    }

    public void setText() {
        for (int y = 0; y < GameParams.allCellNum; y++) {
            for (int x = 0; x < GameParams.allCellNum; x++) {
                mapBackgroundCells[y][x].setText();
            }
        }
    }

    /**
     * @param cellPoint BGCの座標
     * @return 範囲内に矯正した座標
     */
    private static int correctCellID(int cellPoint) {
        if (cellPoint < 0) {
            cellPoint += GameParams.allCellNum;
        } else if (GameParams.allCellNum <= cellPoint) {
            cellPoint -= GameParams.allCellNum;
        }
        return cellPoint;
    }


    /**
     * @param point 欲しいBGCの座標
     * @return 要求したBGC
     */
    public MapBackgroundCell getBGC(int[] point) {
        return mapBackgroundCells[point[Y_axis]][point[X_axis]];
    }

    /**
     * @param y 欲しいBGCのy座標
     * @param x 欲しいBGCのx座標
     * @return 要求したBGC
     */
    public MapBackgroundCell getBGC(int y, int x) {
        return mapBackgroundCells[y][x];
    }

    public MapBackgroundCell getBGC_player_in() {
        Log.d("msg", "matrix getPlayerBGC "
                + player.getBackGroundCell()[Y_axis] + ":"
                + player.getBackGroundCell()[X_axis]);
        return mapBackgroundCells[player.getBackGroundCell()[Y_axis]][player.getBackGroundCell()[X_axis]];
    }

    abstract private static class BGC_Strategy implements BGCStrategyInterface {

    }

    /**
     * methodを全てのBGCに対して実行する
     *
     * @return methodがtrueを返したらtrueを返し実行を止める
     */
    private boolean doProcessForAllCells() {
        int tmpCellY, tmpCellX,
                preCellY = player.getBackGroundCell()[Y_axis],
                preCellX = player.getBackGroundCell()[X_axis];
        MapBackgroundCell tmpBGC;

        for (int i = -checkArea; i <= checkArea; i++) {
            tmpCellY = correctCellID(i + preCellY);
            for (int j = -checkArea; j <= checkArea; j++) {
                tmpCellX = correctCellID(j + preCellX);
                tmpBGC = mapBackgroundCells[tmpCellY][tmpCellX];

                if (bgcStrategy.doMethod(tmpBGC)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean checkCanNotGoToGoal() {
        bgcStrategy = new CheckCanNotGoToGoalBGCStrategyStrategy();
        return doProcessForAllCells();
    }

    private class CheckCanNotGoToGoalBGCStrategyStrategy extends BGC_Strategy {
        @Override
        public boolean doMethod(MapBackgroundCell tmpBGC) {
            if (tmpBGC.isOutOfMapRange(nowMap)) {
                return false;//移動後のマスが範囲外
            }

            if (!tmpBGC.isPartOfPlayerIn()) {
                return false;//移動後のマスと少しも被っていない
            }

            //斜めに移動　出来ればそのまま
            return tmpBGC.isColliding();//斜めに移動できなかった
        }
    }

    public boolean checkObjectAction() {
        bgcStrategy = new CheckObjectActionBGCStrategyStrategy();
        return doProcessForAllCells();
    }


    private class CheckObjectActionBGCStrategyStrategy extends BGC_Strategy {
        @Override
        public boolean doMethod(MapBackgroundCell tmpBGC) {
            if (tmpBGC.isOutOfMapRange(nowMap)) {
                return false;
            }

            if (!tmpBGC.isPartOfPlayerIn()) {
                return false;
            }
            if (!tmpBGC.isColliding()) {
                return false;
            }

            MapEventID actionType = tmpBGC.checkObjectType();
            if (actionType != MapEventID.Non) {
                Log.d("msg", "---アクション↑-----");
                player.setActionType(actionType);
                player.setActionCell(tmpBGC.getCell());
                return true;
            }
            return false;
        }
    }

    public boolean checkCellsCollision() {
        bgcStrategy = new CheckCellCollisionBGCStrategyStrategy();
        return doProcessForAllCells();
    }

    private class CheckCellCollisionBGCStrategyStrategy extends BGC_Strategy {
        /**
         * @param tmpBGC 今から調べたいBGC
         * @return 全てのセルを調べるため何も返さない
         */
        @Override
        public boolean doMethod(MapBackgroundCell tmpBGC) {
            if (tmpBGC.isOutOfMapRange(nowMap)) {
                return false;//範囲外なので次を調査
            }

            if (!tmpBGC.isPartOfPlayerIn()) {
                return false;
            }
            //当たり判定のチェック
            if (tmpBGC.canNotMove()) {
                player.setReCheckCell(tmpBGC.getCell());
            }
            return false;
        }
    }

    /**
     * allInFlagを設定する
     */
    public void setAllInFlag() {
        bgcStrategy = new checkCellPlayerInStrategyStrategy();
        doProcessForAllCells();
    }

    private class checkCellPlayerInStrategyStrategy extends BGC_Strategy {
        /**
         * @param tmpBGC 今から調べたいBGC
         * @return tmpBGCに全身が入っていたらtrue
         */
        @Override
        public boolean doMethod(MapBackgroundCell tmpBGC) {
            if (!tmpBGC.isPlayerCenterIn()) {
                return false;
            }
            player.setNextBackGroundCell(tmpBGC.getCell());
            //全身が入っていたらtrue
            player.setNextAllInFlag(tmpBGC.isAllOfPlayerIn());
            return true;//中心が入っているBGCを確認したから
        }
    }

    interface BGCStrategyInterface {
        /**
         * 各BGCに対して行う処理を記述
         *
         * @param tmpBGC 今から調べたいBGC
         * @return 何か起こればtrue
         */
        boolean doMethod(MapBackgroundCell tmpBGC);
    }
}
