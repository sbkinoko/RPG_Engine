package com.sbkinoko.sbkinokorpg.mapframe.map.bgcell;

import static com.sbkinoko.sbkinokorpg.mapframe.map.bgcell.MapObjectEventData.GroundCollision;
import static com.sbkinoko.sbkinokorpg.mapframe.map.bgcell.MapObjectEventData.objectHeight_Wall;

import com.sbkinoko.sbkinokorpg.mapframe.collisionview.CollisionData;
import com.sbkinoko.sbkinokorpg.mapframe.event.AutoActionList;
import com.sbkinoko.sbkinokorpg.mapframe.event.MapEventID;

public class Cell41_RotateWall extends MakeCell {
    @Override
    public CollisionData[] getCollisionData(int connectType) {
        switch (player.getEventFlag(9)) {
            case 2:
                return new CollisionData[]{
                        new CollisionData(
                                MapObjectEventData.getRectangle(0, 0, 0.2, 1),
                                objectHeight_Wall, MapEventID.Non, AutoActionList.non),
                        new CollisionData(MapObjectEventData.getRectangle(0, 0.8, 1, 0.2),
                                objectHeight_Wall, MapEventID.Non, AutoActionList.non),
                        new CollisionData(MapObjectEventData.getRectangle(0.8, 0, 0.2, 0.2),
                                objectHeight_Wall, MapEventID.Non, AutoActionList.non),
                        GroundCollision
                };
        }
        return super.getCollisionData(connectType);
    }
}
