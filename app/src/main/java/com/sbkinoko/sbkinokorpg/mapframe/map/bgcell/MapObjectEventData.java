package com.sbkinoko.sbkinokorpg.mapframe.map.bgcell;


import com.sbkinoko.sbkinokorpg.mapframe.collisionview.CollisionData;
import com.sbkinoko.sbkinokorpg.mapframe.event.AutoActionList;
import com.sbkinoko.sbkinokorpg.mapframe.event.MapEventID;

public class MapObjectEventData {
    final public static int
            objectHeight_Water = 1,
            objectHeight_Ground = 2,
            objectHeight_Wall = 3,
            objectHeight_Cliff = 4,
            objectHeight_Non = -1;

    final public static CollisionData
            GroundCollision = new CollisionData(getLargeRectangle(),
            objectHeight_Ground, MapEventID.MapACTION_GO_GROUND, AutoActionList.non),
            WallCollision = new CollisionData(getLargeRectangle(),
                    objectHeight_Wall, MapEventID.Non, AutoActionList.non);

    static public double[] getRectangle(double startY, double startX, double height, double width) {
        return new double[]{
                startX, startY,
                startX, startY + height,
                startX + width, startY + height,
                startX + width, startY,
                startX + 0.5 * width, startY + height,
                startX, startY + height * 0.5,
                startX + width, startY
        };
    }

    static public double[] getLargeRectangle() {
        return getRectangle(0, 0, 1, 1);
    }
}
