package com.sbkinoko.sbkinokorpg.mapframe.map.bgcell;

import static com.sbkinoko.sbkinokorpg.mapframe.map.bgcell.MapObjectEventData.objectHeight_Ground;

import com.sbkinoko.sbkinokorpg.mapframe.collisionview.CollisionData;
import com.sbkinoko.sbkinokorpg.mapframe.event.AutoActionList;
import com.sbkinoko.sbkinokorpg.mapframe.event.MapEventID;

public class MakeCell_Type_SymbolMons extends MakeCell {
    @Override
    public CollisionData[] getCollisionData(int connectType) {
        return new CollisionData[]{
                new CollisionData(
                        MapObjectEventData.getLargeRectangle(),
                        objectHeight_Ground, MapEventID.Non, AutoActionList.startBattle)
        };
    }
}
