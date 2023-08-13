package com.sbkinoko.sbkinokorpg.mapframe.collisionview;

import static com.sbkinoko.sbkinokorpg.GameParams.X_axis;
import static com.sbkinoko.sbkinokorpg.GameParams.Y_axis;
import static com.sbkinoko.sbkinokorpg.mapframe.map.bgcell.MapObjectEventData.objectHeight_Non;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;
import android.view.ViewGroup;

import com.sbkinoko.sbkinokorpg.GameParams;
import com.sbkinoko.sbkinokorpg.MainGame;
import com.sbkinoko.sbkinokorpg.OptionConst;
import com.sbkinoko.sbkinokorpg.R;
import com.sbkinoko.sbkinokorpg.mapframe.event.MapEventID;
import com.sbkinoko.sbkinokorpg.mapframe.map.bgcell.MapObjectEventData;
import com.sbkinoko.sbkinokorpg.mapframe.player.Player;

public class CollisionView extends View {

    double[] corners;
    Paint paint;
    Player player;
    private int length;
    private CollisionData collisionData;

    public CollisionData getCollisionData() {
        return collisionData;
    }

    public void reDraw() {
        invalidate();
    }

    public CollisionView(Context context, CollisionData collisionData, Player player1) {
        super(context);
        this.collisionData = collisionData;
        this.corners = collisionData.getCorners();
        this.player = player1;
        paint = new Paint();
        paint.setColor(getResources().getColor(R.color.collisionLine, null));
        paint.setStrokeWidth(5);

        length = MainGame.cellLength;
        this.setLayoutParams(new ViewGroup.LayoutParams(
                length,
                length
        ));
    }

    public boolean canAutoAction() {
        if (!collisionData.isAutoAction()) {
            return false;
        }

        //今見ている四角形に全身が入っているか
        return getX() + getWidth() * corners[0] <= player.getPoints()[X_axis][0]
                && getY() + getHeight() * corners[1] <= player.getPoints()[Y_axis][0]
                && player.getPoints()[X_axis][1] <= getX() + getWidth() * corners[4]
                && player.getPoints()[Y_axis][1] <= getY() + getHeight() * corners[5];
    }


    public MapEventID getActionType() {
        return collisionData.getAction();
    }

    public void setLayoutParams(int length1) {
        super.setLayoutParams(new ViewGroup.LayoutParams(
                length1,
                length1
        ));
        this.length = length1;
    }

    public int getLength() {
        return this.length;
    }

    public double[] getCorners() {
        return this.corners;
    }

    private int getLineColor() {
        switch (collisionData.getHeight()
            //(int) corners[corners.length - 1][0]
        ) {
            case 1:
                return R.color.collisionLine1;
            case 2:
                return R.color.collisionLine2;
            case 3:
                return R.color.collisionLine3;
            case objectHeight_Non:
                return R.color.red;
            default:
                return R.color.collisionLine;
        }
    }

    private void drawCollisionLines(Canvas canvas) {
        for (int l = 0; l < corners.length / 2 - 1; l++) {
            canvas.drawLine((float) corners[2 * l] * this.getWidth(),
                    (float) corners[2 * l + 1] * this.getWidth(),
                    (float) corners[2 * l + 2] * this.getWidth(),
                    (float) corners[2 * l + 3] * this.getWidth(),
                    paint);
        }

        canvas.drawLine((float) corners[corners.length - 2] * this.getWidth(),
                (float) corners[corners.length - 1] * this.getWidth(),
                (float) corners[0] * this.getWidth(),
                (float) corners[1] * this.getWidth(), paint);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (!OptionConst.collisionDrawFlag) {
            canvas.drawColor(0);
            return;
        }

        paint.setColor(getResources().getColor(getLineColor(), null));
        drawCollisionLines(canvas);
    }

    /**
     * 入力されたCVに対して斜めに移動できなければ各方向の移動をチェック
     *
     * @return 斜めに移動できないが、それぞれに移動できればtrueを返す
     */
    public boolean cantMove() {
        boolean[] tmpFlag = {false, false};
        int[] V = new int[2];
        if (!this.isColliding()) {
            return false;//移動してもぶつからない
        }

        //V[X_axis]= 0;
        V[Y_axis] = player.getV()[Y_axis];
        player.setCollisionPoints(V);//x取り消し　縦だけチェック
        tmpFlag[Y_axis] = this.isColliding();

        V[X_axis] = player.getV()[X_axis];
        V[Y_axis] = 0;
        player.setCollisionPoints(V);//y取り消し　横だけチェック
        tmpFlag[X_axis] = this.isColliding();

        player.setCollisionPoints(player.getV());

        if (tmpFlag[Y_axis]) {//y方向だけ移動できない
            player.setCanNotMove_Axis(Y_axis);
        }

        if (tmpFlag[X_axis]) {//x方向だけ移動できない
            player.setCanNotMove_Axis(X_axis);
        }

        //どっちもOKだったらそのセル番号を保存
        return !tmpFlag[X_axis] && !tmpFlag[Y_axis];
    }


    /**
     * Playerとぶつかっていたらtrueを返す
     */
    public boolean isColliding() {
        double[] corner = this.getCorners();

        if (!checkState_Cell(collisionData.getHeight())) {
            return false;//空を飛んでいるので衝突しない
        }

        if (collisionData.getHeight() == objectHeight_Non) {
            return false;
        }


        for (int l = 0; l < corner.length / 2 - 1; l++) {
            if (checkCross(
                    corner[2 * l], corner[2 * l + 2],
                    corner[2 * l + 1], corner[2 * l + 3])) {
                return true;
            }
        }
        if (checkCross(
                corner[corner.length - 2], corner[0],
                corner[corner.length - 1], corner[1])) {
            return true;
        }


        return false;
    }

    private boolean checkState_Cell(int objectHeight) {
        if (objectHeight == MapObjectEventData.objectHeight_Cliff) {
            //最も高いオブジェクトなので絶対に衝突
            return true;
        }

        if (player.getMoveState() == GameParams.MoveState_Sky) {
            //空を飛んでいるので衝突しない
            return false;
        }
        //同じ高さじゃなかったら衝突
        return player.getMoveState() != objectHeight;
    }

    private boolean checkCross(double x1, double x2, double y1, double y2) {
        int Y1, Y2;//Y2が下側の点
        if (y1 < y2) {
            Y1 = (int) (this.getY() + this.getLength() * y1);
            Y2 = (int) (this.getY() + this.getLength() * y2);
        } else {
            Y1 = (int) (this.getY() + this.getLength() * y2);
            Y2 = (int) (this.getY() + this.getLength() * y1);
        }

        int X1, X2;//X2が右側の点
        if (x1 < x2) {
            X1 = (int) (this.getX() + this.getLength() * x1);
            X2 = (int) (this.getX() + this.getLength() * x2);
        } else {
            X1 = (int) (this.getX() + this.getLength() * x2);
            X2 = (int) (this.getX() + this.getLength() * x1);
        }

        int largeY = player.getCollisionPoints()[Y_axis][1];
        int smallY = player.getCollisionPoints()[Y_axis][0];
        int smallX = player.getCollisionPoints()[X_axis][0];
        int largeX = player.getCollisionPoints()[X_axis][1];

        if (x1 == x2) {
            return Y1 <= largeY
                    && smallY <= Y2
                    && smallX <= X1
                    && X1 <= largeX;
        }

        if (y1 == y2) {
            return X1 <= largeX
                    && smallX <= X2
                    && smallY <= Y1
                    && Y1 <= largeY;
        }

        double a = (y2 - y1) / (x2 - x1);//傾きなので位置は重要ではない
        double b = ((this.getX() + this.getLength() * x2) * (this.getY() + this.getLength() * y1)
                - (this.getX() + this.getLength() * x1) * (this.getY() + this.getLength() * y2))
                / ((this.getX() + this.getLength() * x2) - (this.getX() + this.getLength() * x1));


        return isSideCrossing(a, b, X1, X2, 0)
                || isSideCrossing(a, b, X1, X2, 1)
                || isUpDownCrossing(a, b, Y1, Y2, 0)
                || isUpDownCrossing(a, b, Y1, Y2, 1);
    }

    private boolean isSideCrossing(double a, double b, double X1, double X2, int side) {
        return player.getCollisionPoints()[Y_axis][0] <= a * player.getCollisionPoints()[X_axis][side] + b
                && a * player.getCollisionPoints()[X_axis][side] + b <= player.getCollisionPoints()[Y_axis][1]
                && X1 <= player.getCollisionPoints()[X_axis][side]
                && player.getCollisionPoints()[X_axis][side] <= X2;
    }

    private boolean isUpDownCrossing(double a, double b, double Y1, double Y2, int ud) {
        return player.getCollisionPoints()[X_axis][0] <= (player.getCollisionPoints()[Y_axis][ud] - b) / a
                && (player.getCollisionPoints()[Y_axis][ud] - b) / a <= player.getCollisionPoints()[X_axis][1]
                && Y1 <= player.getCollisionPoints()[Y_axis][ud]
                && player.getCollisionPoints()[Y_axis][ud] <= Y2;
    }
}

