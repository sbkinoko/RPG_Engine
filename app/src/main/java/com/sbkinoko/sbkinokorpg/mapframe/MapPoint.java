package com.sbkinoko.sbkinokorpg.mapframe;

import static com.sbkinoko.sbkinokorpg.gameparams.GameParams.X_axis;
import static com.sbkinoko.sbkinokorpg.gameparams.GameParams.Y_axis;

public class MapPoint {
    private int x;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    private int y;

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public MapPoint(int y, int x) {
        this.y = y;
        this.x = x;
    }

    public int getAxisPoint(int axis) {
        switch (axis) {
            case X_axis:
                return x;
            case Y_axis:
                return y;
            default:
                throw new RuntimeException("軸の値が不正です");
        }
    }

    public void applyLoop(int axis, int mapLength) {
        if (getAxisPoint(axis) < 0) {
            movePositionOfAxis(axis, mapLength);
        } else if (mapLength <= getAxisPoint(axis)) {
            movePositionOfAxis(axis, -mapLength);
        }
    }

    public void movePositionOfAxis(int axis, int diff) {
        switch (axis) {
            case X_axis:
                x += diff;
                break;
            case Y_axis:
                y += diff;
                break;
            default:
                throw new RuntimeException("軸の値が不正です");
        }
    }
}
