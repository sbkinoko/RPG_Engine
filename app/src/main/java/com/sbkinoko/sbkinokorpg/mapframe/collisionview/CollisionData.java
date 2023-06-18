package com.sbkinoko.sbkinokorpg.mapframe.collisionview;

import com.sbkinoko.sbkinokorpg.mapframe.event.AutoActionList;
import com.sbkinoko.sbkinokorpg.mapframe.event.MapEventID;

public class CollisionData {
    double[] corners;
    int height;
    MapEventID action;
    AutoActionList autoActionList;

    //todo 出来れば高さもenumにする
    public CollisionData(double[] corners, int height, MapEventID action, AutoActionList autoAction) {
        this.corners = corners;
        this.height = height;
        this.action = action;
        this.autoActionList = autoAction;
    }

    public int getHeight() {
        return height;
    }

    public MapEventID getAction() {
        return action;
    }

    public double[] getCorners() {
        return corners;
    }

    public boolean isAutoAction() {
        return autoActionList != AutoActionList.non;
    }

    public AutoActionList getAutoActionList() {
        return autoActionList;
    }
}
