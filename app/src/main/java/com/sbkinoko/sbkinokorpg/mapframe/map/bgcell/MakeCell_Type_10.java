package com.sbkinoko.sbkinokorpg.mapframe.map.bgcell;

import static com.sbkinoko.sbkinokorpg.mapframe.map.bgcell.MapObjectEventData.getRectangle;
import static com.sbkinoko.sbkinokorpg.mapframe.map.bgcell.MapObjectEventData.objectHeight_Ground;
import static com.sbkinoko.sbkinokorpg.mapframe.map.bgcell.MapObjectEventData.objectHeight_Water;

import com.sbkinoko.sbkinokorpg.mapframe.collisionview.CollisionData;
import com.sbkinoko.sbkinokorpg.mapframe.event.AutoActionList;
import com.sbkinoko.sbkinokorpg.mapframe.event.MapEventID;

class MakeCell_Type_10 extends MakeCell {
    @Override
    public CollisionData[] getCollisionData(int connectType) {
        return new CollisionData[]{
                new CollisionData(getRectangle(0, 0, 0.5, 1),
                        objectHeight_Ground, MapEventID.MapACTION_GO_GROUND, AutoActionList.non),
                new CollisionData(getRectangle(0.5, 0, 0.5, 1),
                        objectHeight_Water, MapEventID.MapACTION_GO_WATER, AutoActionList.non),
        };
    }
}
