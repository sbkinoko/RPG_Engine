package com.sbkinoko.sbkinokorpg.mapframe.map.bgcell;

import static com.sbkinoko.sbkinokorpg.mapframe.map.bgcell.MapObjectEventData.getLargeRectangle;
import static com.sbkinoko.sbkinokorpg.mapframe.map.bgcell.MapObjectEventData.objectHeight_Water;

import com.sbkinoko.sbkinokorpg.mapframe.collisionview.CollisionData;
import com.sbkinoko.sbkinokorpg.mapframe.event.AutoActionList;
import com.sbkinoko.sbkinokorpg.mapframe.event.MapEventID;

class MakeCell_Type_Water extends MakeCell {
    @Override
    public CollisionData[] getCollisionData(int connectType) {
        return new CollisionData[]{
                new CollisionData(
                        getLargeRectangle(),
                        objectHeight_Water, MapEventID.MapACTION_GO_WATER, AutoActionList.non)
        };
    }

    @Override
    public int getMonsRnd() {
        return MonsAppRnd.getHighMonsApp();
    }
}
