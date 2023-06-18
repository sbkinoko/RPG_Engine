package com.sbkinoko.sbkinokorpg.mapframe.map.bgcell;

import static com.sbkinoko.sbkinokorpg.mapframe.map.bgcell.MapObjectEventData.objectHeight_Ground;
import static com.sbkinoko.sbkinokorpg.mapframe.map.bgcell.MapObjectEventData.objectHeight_Water;

import com.sbkinoko.sbkinokorpg.mapframe.collisionview.CollisionData;
import com.sbkinoko.sbkinokorpg.mapframe.event.AutoActionList;
import com.sbkinoko.sbkinokorpg.mapframe.event.MapEventID;

public class MakeCell_Tunnel extends MakeCell {
    @Override
    public CollisionData[] getCollisionData(int connectType) {
        return new CollisionData[]{
                new CollisionData(MapObjectEventData.getRectangle(0, 0, 0.2, 1),
                        objectHeight_Ground, MapEventID.Non, AutoActionList.non),
                new CollisionData(MapObjectEventData.getRectangle(0.8, 0, 0.2, 1),
                        objectHeight_Ground, MapEventID.Non, AutoActionList.non),
                new CollisionData(MapObjectEventData.getRectangle(0, 0, 1, 0.2),
                        objectHeight_Water, MapEventID.Non, AutoActionList.non),
                new CollisionData(MapObjectEventData.getRectangle(0, 0.8, 1, 0.2),
                        objectHeight_Water, MapEventID.Non, AutoActionList.non),
        };
    }
}
