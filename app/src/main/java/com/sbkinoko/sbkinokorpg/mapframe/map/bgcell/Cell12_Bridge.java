package com.sbkinoko.sbkinokorpg.mapframe.map.bgcell;

import static com.sbkinoko.sbkinokorpg.mapframe.map.bgcell.MapObjectEventData.getLargeRectangle;
import static com.sbkinoko.sbkinokorpg.mapframe.map.bgcell.MapObjectEventData.objectHeight_Ground;

import com.sbkinoko.sbkinokorpg.mapframe.collisionview.CollisionData;
import com.sbkinoko.sbkinokorpg.mapframe.event.AutoActionList;
import com.sbkinoko.sbkinokorpg.mapframe.event.MapEventID;

class Cell12_Bridge extends MakeCell {
    @Override
    public int getBGImg(int CELL_TYPE) {
        return super.getBGImg(2);
    }

    @Override
    public CollisionData[] getCollisionData(int connectType) {
        return new CollisionData[]{
                new CollisionData(getLargeRectangle(),
                        objectHeight_Ground, MapEventID.MapACTION_GO_GROUND, AutoActionList.non)
        };
    }
}
