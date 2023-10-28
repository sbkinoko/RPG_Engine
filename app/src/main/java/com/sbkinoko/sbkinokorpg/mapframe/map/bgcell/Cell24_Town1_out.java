package com.sbkinoko.sbkinokorpg.mapframe.map.bgcell;

import static com.sbkinoko.sbkinokorpg.mapframe.map.bgcell.MapObjectEventData.objectHeight_Ground;

import com.sbkinoko.sbkinokorpg.R;
import com.sbkinoko.sbkinokorpg.mapframe.collisionview.CollisionData;
import com.sbkinoko.sbkinokorpg.mapframe.event.AutoActionList;
import com.sbkinoko.sbkinokorpg.mapframe.event.MapEventID;

public class Cell24_Town1_out extends MakeCell {
    @Override
    public int getBGImg(int CELL_TYPE) {
        return R.drawable.bg_00;
    }

    @Override
    public CollisionData[] getCollisionData(int connectType) {
        return new CollisionData[]{
                new CollisionData(
                        MapObjectEventData.getLargeRectangle(),
                        objectHeight_Ground, MapEventID.Non, AutoActionList.mapChange)
        };
    }
}
