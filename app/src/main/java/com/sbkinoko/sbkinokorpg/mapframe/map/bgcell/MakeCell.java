package com.sbkinoko.sbkinokorpg.mapframe.map.bgcell;

import static com.sbkinoko.sbkinokorpg.GameParams.X_axis;
import static com.sbkinoko.sbkinokorpg.GameParams.Y_axis;
import static com.sbkinoko.sbkinokorpg.mapframe.map.bgcell.MapObjectEventData.GroundCollision;

import android.content.Context;
import android.content.res.Resources;
import android.widget.ImageView;

import com.sbkinoko.sbkinokorpg.R;
import com.sbkinoko.sbkinokorpg.mapframe.MapBackgroundCell;
import com.sbkinoko.sbkinokorpg.mapframe.MapFrame;
import com.sbkinoko.sbkinokorpg.mapframe.collisionview.CollisionData;
import com.sbkinoko.sbkinokorpg.mapframe.collisionview.CollisionView;
import com.sbkinoko.sbkinokorpg.mapframe.map.mapdata.MapData;
import com.sbkinoko.sbkinokorpg.mapframe.player.Player;

public abstract class MakeCell {
    MapData nowMap;
    int x, y;
    CollisionView[] cvs;
    Resources res;
    Context context;
    int cellType;
    Player player;

    public void setCellType(int cellType, Context context) {
        this.cellType = cellType;
        this.context = context;
        this.res = context.getResources();
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setCellInf(MapBackgroundCell bgc, MapData nowMap) {
        ImageView
                bgv = bgc.getIV(),
                obv = bgc.getOV();

        x = bgc.getMapPoint()[X_axis];
        y = bgc.getMapPoint()[Y_axis];
        this.nowMap = nowMap;

        int imgId1 = getBGImg(cellType);
        if (imgId1 == 0) {
            checkAround(nowMap.getMap(), bgv);
        } else {
            bgv.setImageResource(imgId1);
        }

        int[] objectInfo = getObjectImg();
        int imgId2 = objectInfo[0];
        obv.setImageResource(imgId2);

        CollisionData[] collisionData;
        if (objectInfo.length > 1) {
            collisionData = getCollisionData(objectInfo[1]);
        } else {
            collisionData = getCollisionData(0);
        }

        cvs = new CollisionView[collisionData.length];
        for (int i = 0; i < cvs.length; i++) {
            cvs[i] = new CollisionView(context, collisionData[i], player);
        }
        bgc.setCVs(cvs);
    }

    public CollisionData[] getCollisionData(int connectType) {
        return new CollisionData[]{GroundCollision};
    }

    public int getMonsRnd() {
        return MonsAppRnd.getMidMonsApp();
    }

    public int getBGImg(int CELL_TYPE) {
        String imgName = "";
        if (CELL_TYPE < 10) {
            imgName += ("_0" + CELL_TYPE);
        } else {
            imgName += ("_" + CELL_TYPE);
        }
        return res.getIdentifier("bg" + imgName, "drawable", context.getPackageName());
    }

    public int[] getObjectImg() {
        int connectType = -1;
        String imgName = "";
        if (cellType < 10) {
            imgName += ("_0" + cellType);
        } else {
            imgName += ("_" + cellType);
        }

        int imgId2 = res.getIdentifier("ob" + imgName, "drawable", context.getPackageName());
        if (imgId2 == 0) {
            connectType = checkConnect(nowMap.getMap(), cellType, y, x);
            if (connectType < 10) {
                imgName += ("_0" + connectType);
            } else {
                imgName += ("_" + connectType);
            }
            imgId2 = res.getIdentifier("ob" + imgName, "drawable", context.getPackageName());
        }

        return new int[]{imgId2, connectType};
    }

    public int getBattleBG() {
        String imgName = "";
        if (cellType < 10) {
            imgName += ("_0" + cellType);
        } else {
            imgName += ("_" + cellType);
        }
        int imgID = res.getIdentifier("bg_bt" + imgName, "drawable", context.getPackageName());
        if (imgID == 0) {
            imgID = R.drawable.bg_bt_01;
        }
        return imgID;
    }

    private void checkAround(int[][][] nowMap, ImageView iv) {
        // Log.d("msg", "CellINF Check Around");
        int tmpY = y, tmpX = x;
        int[][] cellTypes = {
                {-1, 0},
                {-1, 0},
                {-1, 0},
                {-1, 0},
                {-1, 0},
                {-1, 0},
                {-1, 0},
                {-1, 0},
                {-1, 0}
        };
        int typeNum = 0;

        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (tmpY + i < 0) {
                    tmpY += nowMap.length;
                } else if (nowMap.length <= tmpY + i) {
                    tmpY -= nowMap.length;
                }
                if (tmpX + j < 0) {
                    tmpX += nowMap[0].length;
                } else if (nowMap[0].length <= tmpX + j) {
                    tmpX -= nowMap[0].length;
                }

                for (int k = 0; k < cellTypes.length; k++) {
                    if (nowMap[tmpY + i][tmpX + j][0] == cellTypes[k][0]) {
                        cellTypes[k][1]++;
                        break;
                    } else if (cellTypes[k][0] == -1) {
                        cellTypes[k][0] = nowMap[tmpY + i][tmpX + j][0];
                        cellTypes[k][1]++;
                        typeNum++;
                        break;
                    }
                }
            }
        }

        for (int i = 0; i < typeNum - 1; i++) {
            for (int j = 0; j < i - 1; j++) {
                if (cellTypes[j][1] < cellTypes[j + 1][1]) {
                    int tmp;
                    tmp = cellTypes[j][1];
                    cellTypes[j][1] = cellTypes[j + 1][1];
                    cellTypes[j + 1][1] = tmp;

                    tmp = cellTypes[j][0];
                    cellTypes[j][0] = cellTypes[j + 1][0];
                    cellTypes[j + 1][0] = tmp;
                }
            }
        }

        iv.setImageResource(0);
        for (int i = 0; i < typeNum; i++) {
            switch (cellTypes[i][0]) {
                case 0:
                case 2:
                case 3:
                case 6:
                    iv.setImageResource(getBGImg(cellTypes[i][0]));
                    return;
                default:
                    break;
            }
        }
    }


    private int checkConnect(int[][][] nowMap, int cellType, int CELL_Y, int CELL_X) {
        //Log.d("msg", "CellInf Check Connect");
        int tmpY, tmpX;
        int[][] cellConnection =
                {
                        {0, 0, 0},
                        {0, 1, 0},
                        {0, 0, 0}
                };

        int roadType;
        for (int i = -1; i <= 1; i++) {
            tmpY = CELL_Y + i;

            if (!MapFrame.getLoopFlag()) {
                if (tmpY < 0 || nowMap.length <= tmpY) {
                    continue;
                }
            } else {
                if (tmpY < 0) {
                    tmpY += nowMap.length;
                } else if (nowMap.length <= tmpY) {
                    tmpY -= nowMap.length;
                }
            }

            for (int j = -1; j <= 1; j++) {
                tmpX = CELL_X + j;
                if (MapFrame.getLoopFlag()) {
                    if (tmpX < 0) {
                        tmpX += nowMap[0].length;
                    } else if (nowMap[0].length <= tmpX) {
                        tmpX -= nowMap[0].length;
                    }
                } else {
                    if (tmpX < 0 || nowMap[0].length <= tmpX) {
                        continue;
                    }
                }

                if (nowMap[tmpY][tmpX][0] == cellType) {
                    cellConnection[1 + i][1 + j] = 1;
                }
            }
        }

        roadType = 8 * cellConnection[0][1] + 4 * cellConnection[1][0] +
                2 * cellConnection[1][2] + cellConnection[2][1];
        switch (roadType) {
            case 15:
                return 3;//上下左右
            case 14:
                return 10;//左右下
            case 13:
                return 11;
            case 12:
                return 6;
            case 11:
                return 9;
            case 10:
                return 5;
            case 9:
            case 8:
            case 1:
                return 1;
            case 7:
                return 12;
            case 6:
            case 4:
            case 2:
                return 2;
            case 5:
                return 7;
            case 3:
                return 8;

            case 0:
                return 1;//上下なしはデフォルトを返す
        }
        return 0;
    }
}






