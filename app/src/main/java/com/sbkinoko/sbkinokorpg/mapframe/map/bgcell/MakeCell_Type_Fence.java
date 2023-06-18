package com.sbkinoko.sbkinokorpg.mapframe.map.bgcell;

import static com.sbkinoko.sbkinokorpg.mapframe.map.bgcell.MapObjectEventData.GroundCollision;
import static com.sbkinoko.sbkinokorpg.mapframe.map.bgcell.MapObjectEventData.getRectangle;
import static com.sbkinoko.sbkinokorpg.mapframe.map.bgcell.MapObjectEventData.objectHeight_Wall;

import com.sbkinoko.sbkinokorpg.mapframe.collisionview.CollisionData;
import com.sbkinoko.sbkinokorpg.mapframe.event.AutoActionList;
import com.sbkinoko.sbkinokorpg.mapframe.event.MapEventID;

class MakeCell_Type_Fence extends MakeCell {

    @Override
    public CollisionData[] getCollisionData(int connectType) {
        switch (connectType) {
            case 1:
                return new CollisionData[]{
                        GroundCollision,
                        new CollisionData(getRectangle(0, 0.35, 1, 0.3),
                                objectHeight_Wall, MapEventID.Non, AutoActionList.non),
                };
            case 2:
                return new CollisionData[]{
                        GroundCollision,
                        new CollisionData(getRectangle(0.35, 0, 0.3, 1),
                                objectHeight_Wall, MapEventID.Non, AutoActionList.non),
                };
            case 8:
                return new CollisionData[]{
                        GroundCollision,
                        new CollisionData(getRectangle(0.35, 0.35, 0.65, 0.3),
                                objectHeight_Wall, MapEventID.Non, AutoActionList.non),
                        new CollisionData(getRectangle(0.35, 0.35, 0.3, 0.65),
                                objectHeight_Wall, MapEventID.Non, AutoActionList.non),
                };
            case 7:
                return new CollisionData[]{
                        new CollisionData(getRectangle(0.35, 0.35, 0.65, 0.3),
                                objectHeight_Wall, MapEventID.Non, AutoActionList.non),
                        new CollisionData(getRectangle(0.35, 0, 0.3, 0.65),
                                objectHeight_Wall, MapEventID.Non, AutoActionList.non),
                        GroundCollision,
                };
            case 5:
                return new CollisionData[]{
                        new CollisionData(getRectangle(0, 0.35, 0.65, 0.3),
                                objectHeight_Wall, MapEventID.Non, AutoActionList.non),
                        new CollisionData(getRectangle(0.35, 0.35, 0.3, 0.65),
                                objectHeight_Wall, MapEventID.Non, AutoActionList.non),
                        GroundCollision,
                };
            case 6:
                return new CollisionData[]{
                        new CollisionData(getRectangle(0, 0.35, 0.65, 0.3),
                                objectHeight_Wall, MapEventID.Non, AutoActionList.non),
                        new CollisionData(getRectangle(0.35, 0, 0.3, 0.65),
                                objectHeight_Wall, MapEventID.Non, AutoActionList.non),
                        GroundCollision,
                };
        }
        return null;
    }
}
