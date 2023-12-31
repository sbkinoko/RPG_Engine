package com.sbkinoko.sbkinokorpg.mapframe.collisionview;

import static com.sbkinoko.sbkinokorpg.mapframe.map.bgcell.MapObjectEventData.objectHeight_Non;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;
import android.view.ViewGroup;

import com.sbkinoko.sbkinokorpg.MainGame;
import com.sbkinoko.sbkinokorpg.OptionConst;
import com.sbkinoko.sbkinokorpg.R;
import com.sbkinoko.sbkinokorpg.gameparams.Axis;
import com.sbkinoko.sbkinokorpg.gameparams.MoveState;
import com.sbkinoko.sbkinokorpg.mapframe.event.MapEventID;
import com.sbkinoko.sbkinokorpg.mapframe.map.bgcell.MapObjectEventData;
import com.sbkinoko.sbkinokorpg.mapframe.player.Player;

public class CollisionView extends View {

    double[] corners;
    Paint paint;
    Player player;
    private int length;
    private final CollisionData collisionData;

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
        return getX() + getWidth() * corners[0] <= player.getPoints()[Axis.X.id][0]
                && getY() + getHeight() * corners[1] <= player.getPoints()[Axis.Y.id][0]
                && player.getPoints()[Axis.X.id][1] <= getX() + getWidth() * corners[4]
                && player.getPoints()[Axis.Y.id][1] <= getY() + getHeight() * corners[5];
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

        //V[Axis.X.id]= 0;
        V[Axis.Y.id] = player.getV()[Axis.Y.id];
        player.setCollisionPoints(V);//x取り消し　縦だけチェック
        tmpFlag[Axis.Y.id] = this.isColliding();

        V[Axis.X.id] = player.getV()[Axis.X.id];
        V[Axis.Y.id] = 0;
        player.setCollisionPoints(V);//y取り消し　横だけチェック
        tmpFlag[Axis.X.id] = this.isColliding();

        player.setCollisionPoints(player.getV());

        if (tmpFlag[Axis.Y.id]) {//y方向だけ移動できない
            player.setCanNotMove_Axis(Axis.Y.id);
        }

        if (tmpFlag[Axis.X.id]) {//x方向だけ移動できない
            player.setCanNotMove_Axis(Axis.X.id);
        }

        //どっちもOKだったらそのセル番号を保存
        return !tmpFlag[Axis.X.id] && !tmpFlag[Axis.Y.id];
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
        return checkCross(
                corner[corner.length - 2], corner[0],
                corner[corner.length - 1], corner[1]);
    }

    private boolean checkState_Cell(int objectHeight) {
        if (objectHeight == MapObjectEventData.objectHeight_Cliff) {
            //最も高いオブジェクトなので絶対に衝突
            return true;
        }

        if (player.getMoveState() == MoveState.MoveState_Sky) {
            //空を飛んでいるので衝突しない
            return false;
        }
        //同じ高さじゃなかったら衝突
        //todo objectHeightのenum化
        return player.getMoveState().getMoveStateInt() != objectHeight;
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

        int largeY = player.getCollisionPoints()[Axis.Y.id][1];
        int smallY = player.getCollisionPoints()[Axis.Y.id][0];
        int smallX = player.getCollisionPoints()[Axis.X.id][0];
        int largeX = player.getCollisionPoints()[Axis.X.id][1];

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
        return player.getCollisionPoints()[Axis.Y.id][0] <= a * player.getCollisionPoints()[Axis.X.id][side] + b
                && a * player.getCollisionPoints()[Axis.X.id][side] + b <= player.getCollisionPoints()[Axis.Y.id][1]
                && X1 <= player.getCollisionPoints()[Axis.X.id][side]
                && player.getCollisionPoints()[Axis.X.id][side] <= X2;
    }

    private boolean isUpDownCrossing(double a, double b, double Y1, double Y2, int ud) {
        return player.getCollisionPoints()[Axis.X.id][0] <= (player.getCollisionPoints()[Axis.Y.id][ud] - b) / a
                && (player.getCollisionPoints()[Axis.Y.id][ud] - b) / a <= player.getCollisionPoints()[Axis.X.id][1]
                && Y1 <= player.getCollisionPoints()[Axis.Y.id][ud]
                && player.getCollisionPoints()[Axis.Y.id][ud] <= Y2;
    }
}

