package com.sbkinoko.sbkinokorpg.mapframe.map.bgcell;

import static com.sbkinoko.sbkinokorpg.mapframe.map.bgcell.MapObjectEventData.GroundCollision;
import static com.sbkinoko.sbkinokorpg.mapframe.map.bgcell.MapObjectEventData.objectHeight_Wall;

import com.sbkinoko.sbkinokorpg.R;
import com.sbkinoko.sbkinokorpg.mapframe.collisionview.CollisionData;
import com.sbkinoko.sbkinokorpg.mapframe.event.AutoActionList;
import com.sbkinoko.sbkinokorpg.mapframe.event.MapEventID;

public class Cell40_switch extends MakeCell {
    @Override
    public CollisionData[] getCollisionData(int connectType) {
        return new CollisionData[]{
                new CollisionData(
                        MapObjectEventData.getRectangle(0.2, 0.3, 0.6, 0.4),
                        objectHeight_Wall, MapEventID.MapAction_Switch, AutoActionList.non),
                GroundCollision
        };
    }

    @Override
    public int getBGImg(int CELL_TYPE) {
        return R.drawable.bg_00;
    }
}
