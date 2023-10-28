package com.sbkinoko.sbkinokorpg.mapframe;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.sbkinoko.sbkinokorpg.MainGame;
import com.sbkinoko.sbkinokorpg.gameparams.Axis;
import com.sbkinoko.sbkinokorpg.gameparams.GameParams;
import com.sbkinoko.sbkinokorpg.mapframe.collisionview.CollisionView;
import com.sbkinoko.sbkinokorpg.mapframe.event.AutoActionList;
import com.sbkinoko.sbkinokorpg.mapframe.event.MapEventID;
import com.sbkinoko.sbkinokorpg.mapframe.map.bgcell.MakeCellFactory;
import com.sbkinoko.sbkinokorpg.mapframe.map.bgcell.MapObjectEventData;
import com.sbkinoko.sbkinokorpg.mapframe.map.mapdata.MapData;
import com.sbkinoko.sbkinokorpg.mapframe.player.Player;

public class MapBackgroundCell {
    private final ImageView iv,
            ov;//ObjectView
    private CollisionView[] cvs;
    private final TextView tv;
    private MapPoint mapPoint;
    private final int[] cell = new int[2];
    private final MapFrame mapFrame;
    private final float[][] viewPoint = new float[2][2];
    private final Player player;
    private final Context context;

    MapBackgroundCell(Context context, FrameLayout layout,
                      Player player1, MapFrame mapFrame1, int cellY, int cellX) {
        this.mapFrame = mapFrame1;
        this.player = player1;
        this.context = context;

        this.iv = new ImageView(context);
        iv.setScaleType(ImageView.ScaleType.FIT_XY);
        //iv.setImageResource(R.drawable.background_frame);
        iv.setLayoutParams(new ViewGroup.LayoutParams(
                MainGame.cellLength,
                MainGame.cellLength
        ));

        this.ov = new ImageView(context);
        this.ov.setScaleType(ImageView.ScaleType.FIT_XY);
        this.ov.setLayoutParams(new ViewGroup.LayoutParams(
                MainGame.cellLength,
                MainGame.cellLength
        ));

        this.tv = new TextView(context);
        this.tv.setLayoutParams(new ViewGroup.LayoutParams(
                MainGame.cellLength,
                MainGame.cellLength
        ));

        this.cell[Axis.Y.id] = cellY;
        this.cell[Axis.X.id] = cellX;

        this.cvs = new CollisionView[1];
        cvs[0] = new CollisionView(context, MapObjectEventData.GroundCollision, player1);

        layout.addView(iv);
        layout.addView(ov);
        layout.addView(tv);
    }

    public void setText() {
        String CellPointTxt = "cellY:" + cell[Axis.Y.id] +
                "\ncellX:" + cell[Axis.X.id] +
                "\nMapY:" + mapPoint.getY() +
                "\nMapX:" + mapPoint.getX();
        tv.setText(CellPointTxt);
    }

    public void setMapPoint(MapPoint mapPoint) {
        this.mapPoint = mapPoint;
        modifyPointByLoop(Axis.X.id);
        modifyPointByLoop(Axis.Y.id);
    }

    public MapPoint getMapPoint() {
        return mapPoint;
    }

    /**
     * @param axis 軸
     * @param d    移動量
     */
    private void moveMapPoint(int axis, int d) {
        mapPoint.movePositionOfAxis(axis, d);
        modifyPointByLoop(axis);
    }

    /**
     * @param axis 　スクロールする方向
     */
    public void scroll(int axis) {
        int dir = 0;
        float[] afterViewPoint = {
                getViewPoint()[Axis.Y.id][0],
                getViewPoint()[Axis.X.id][0]};

        afterViewPoint[axis] -= player.getV()[axis];

        if (getViewPoint()[axis][1] <= 0
                && 0 < player.getV()[axis]) {
            dir = 1;
        } else if (MainGame.playWindowSize <= getViewPoint()[axis][0]
                && player.getV()[axis] < 0) {
            dir = -1;
        }

        if (dir != 0) {
            int moveDist = GameParams.allCellNum * dir;
            afterViewPoint[axis] += moveDist * MainGame.cellLength;
            moveMapPoint(axis, moveDist);
            setData(mapFrame.getNowMap());
            setText();
        }

        setViewPoint(afterViewPoint);
    }

    public int[] getCell() {
        return cell;
    }

    private void modifyPointByLoop(int axis) {
        if (!MapFrame.getLoopFlag()) {
            return;
        }

        int mapLength = getMapLength(axis);

        mapPoint.applyLoop(axis, mapLength);
    }

    private int getMapLength(int axis) {
        if (axis == Axis.X.id) {
            return mapFrame.getMapViewModel().getMapWidth();
        } else {
            return mapFrame.getMapViewModel().getMapHeight();
        }
    }

    public ImageView getIV() {
        return iv;
    }

    public ImageView getOV() {
        return ov;
    }

    private int collidingCVID;

    public boolean isColliding() {
        collidingCVID = -1;
        for (int i = 0; i < cvs.length; i++) {
            if (cvs[i].isColliding()) {
                collidingCVID = i;
                return true;
            }
        }
        return false;
    }

    public int getCollidingCVID() {
        return collidingCVID;
    }

    public void setCVs(CollisionView[] cvs1) {
        for (CollisionView cv : cvs) {
            mapFrame.getFrameLayout().removeView(cv);
        }
        cvs = cvs1;
        float x = iv.getX(),
                y = iv.getY();
        for (CollisionView cv : cvs) {
            mapFrame.getFrameLayout().addView(cv);
            cv.setX(x);
            cv.setY(y);
        }
    }

    public TextView getTV() {
        return tv;
    }

    public void setViewPoint(float[] points) {
        this.iv.setX(points[Axis.X.id]);
        this.ov.setX(points[Axis.X.id]);
        this.tv.setX(points[Axis.X.id]);

        this.iv.setY(points[Axis.Y.id]);
        this.ov.setY(points[Axis.Y.id]);
        this.tv.setY(points[Axis.Y.id]);

        for (CollisionView cv : cvs) {
            cv.setX(points[Axis.X.id]);
            cv.setY(points[Axis.Y.id]);
        }

        viewPoint[Axis.X.id][0] = points[Axis.X.id];
        viewPoint[Axis.X.id][1] = points[Axis.X.id] + MainGame.cellLength;
        viewPoint[Axis.Y.id][0] = points[Axis.Y.id];
        viewPoint[Axis.Y.id][1] = points[Axis.Y.id] + MainGame.cellLength;
    }

    public float[][] getViewPoint() {
        return viewPoint;
    }

    public void setData(MapData map) {
        int cellType = getCellType(map);

        MakeCellFactory.make(cellType, context, player).setCellInf(this, map);

        reDraw();
    }

    private int getCellType(MapData map) {
        if (map.isOutOfMap(mapPoint)) {
            return map.getOutSideCell();
        }

        return map.getCellType(mapPoint);
    }

    public void reDraw() {
        for (CollisionView cv : cvs) {
            cv.reDraw();
        }
    }

    /**
     * @return playerの中心が入っていたらtrue
     */
    public boolean isPlayerCenterIn() {
        return this.iv.getX() <= this.player.getAfterCenterX() &&
                this.player.getAfterCenterX() <= this.iv.getX() + MainGame.cellLength &&
                this.iv.getY() <= this.player.getAfterCenterY() &&
                this.player.getAfterCenterY() <= this.iv.getY() + MainGame.cellLength;
    }

    /**
     * @return playerの全身が入っていたらtrue
     */
    public boolean isAllOfPlayerIn() {
        return this.iv.getX() <= this.player.getCollisionPoints()[Axis.X.id][0] &&
                this.player.getCollisionPoints()[Axis.X.id][1] <= this.iv.getX() + MainGame.cellLength &&
                this.iv.getY() <= this.player.getCollisionPoints()[Axis.Y.id][0] &&
                this.player.getCollisionPoints()[Axis.Y.id][1] <= this.iv.getY() + MainGame.cellLength;
    }

    /**
     * @return playerの一部が入っていたらtrue
     */
    public boolean isPartOfPlayerIn() {
        return this.iv.getX() <= this.player.getCollisionPoints()[Axis.X.id][1]
                && this.player.getCollisionPoints()[Axis.X.id][0] <= this.iv.getX() + MainGame.cellLength
                && this.iv.getY() <= this.player.getCollisionPoints()[Axis.Y.id][1]
                && this.player.getCollisionPoints()[Axis.Y.id][0] <= this.iv.getY() + MainGame.cellLength;
    }

    public boolean canNotMove() {
        for (CollisionView cv : cvs) {
            if (cv.cantMove()) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param nowMap 今いるマップのデータ
     * @return マップの範囲外ならtrue
     */
    public boolean isOutOfMapRange(MapData nowMap) {
        return nowMap.isOutOfMap(mapPoint);
    }

    /**
     * @return 今のセルにあるオブジェクトのデータ
     */
    public MapEventID checkObjectType() {
        return cvs[getCollidingCVID()].getActionType();
    }

    public AutoActionList getAutoAction() {
        for (CollisionView cv : cvs) {
            if (cv.canAutoAction()) {
                return cv.getCollisionData().getAutoActionList();
            }
        }
        return AutoActionList.non;
    }
}
