package com.sbkinoko.sbkinokorpg.mapframe;

import com.sbkinoko.sbkinokorpg.gameparams.Axis;

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

    public int getAxisPoint(Axis axis) {
        switch (axis) {
            case X:
                return x;
            case Y:
                return y;
            default:
                throw new RuntimeException("軸の値が不正です");
        }
    }

    public void applyLoop(Axis axis, int mapLength) {
        if (getAxisPoint(axis) < 0) {
            movePositionOfAxis(axis, mapLength);
        } else if (mapLength <= getAxisPoint(axis)) {
            movePositionOfAxis(axis, -mapLength);
        }
    }

    public void movePositionOfAxis(Axis axis, int diff) {
        switch (axis) {
            case X:
                x += diff;
                break;
            case Y:
                y += diff;
                break;
            default:
                throw new RuntimeException("軸の値が不正です");
        }
    }
}
