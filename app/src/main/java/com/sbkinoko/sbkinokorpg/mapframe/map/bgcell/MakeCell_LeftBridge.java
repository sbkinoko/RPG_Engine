package com.sbkinoko.sbkinokorpg.mapframe.map.bgcell;

import static com.sbkinoko.sbkinokorpg.mapframe.map.bgcell.MapObjectEventData.getRectangle;
import static com.sbkinoko.sbkinokorpg.mapframe.map.bgcell.MapObjectEventData.objectHeight_Non;
import static com.sbkinoko.sbkinokorpg.mapframe.map.bgcell.MapObjectEventData.objectHeight_Wall;

import com.sbkinoko.sbkinokorpg.mapframe.collisionview.CollisionData;
import com.sbkinoko.sbkinokorpg.mapframe.event.AutoActionList;
import com.sbkinoko.sbkinokorpg.mapframe.event.MapEventID;

public class MakeCell_LeftBridge extends MakeCell {
    @Override
    public CollisionData[] getCollisionData(int connectType) {
        return new CollisionData[]{
                new CollisionData(
                        new double[]{0, 1,
                                1, 1,
                                1, 0.8},
                        objectHeight_Wall, MapEventID.Non, AutoActionList.non),
                new CollisionData(
                        new double[]{0, 0.2,
                                0, 0.4,
                                1, 0.2,
                                1, 0},
                        objectHeight_Wall, MapEventID.Non, AutoActionList.non),
                new CollisionData(
                        getRectangle(0, 0, 1, 0.5),
                        objectHeight_Non, MapEventID.Non, AutoActionList.setGround),
                new CollisionData(
                        getRectangle(0, 0.5, 1, 0.5),
                        objectHeight_Non, MapEventID.Non, AutoActionList.setWater),
        };
    }
}
