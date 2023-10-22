package com.sbkinoko.sbkinokorpg.mapframe.map.bgcell;

import static com.sbkinoko.sbkinokorpg.mapframe.map.bgcell.MapObjectEventData.GroundCollision;
import static com.sbkinoko.sbkinokorpg.mapframe.map.bgcell.MapObjectEventData.objectHeight_Wall;

import com.sbkinoko.sbkinokorpg.mapframe.collisionview.CollisionData;
import com.sbkinoko.sbkinokorpg.mapframe.event.AutoActionList;
import com.sbkinoko.sbkinokorpg.mapframe.event.MapEventID;

class MakeCell_Type_TreasureBox extends MakeCell {
    @Override
    public int[] getObjectImg() {
        String imgId2;
        if (nowMap.getTreasureBoxes()[nowMap.getTreasureBoxId(mapPoint)] == 0) {
            imgId2 = "ob_98_0";
        } else {
            imgId2 = "ob_98_1";
        }
        return new int[]{
                res.getIdentifier(imgId2, "drawable", context.getPackageName())
        };
    }

    @Override
    public CollisionData[] getCollisionData(int connectType) {
        return new CollisionData[]{
                new CollisionData(
                        new double[]{0.33, 0.3,
                                0.33, 0.65,
                                0.5, 0.65,
                                0.65, 0.5,
                                0.65, 0.3
                        }, objectHeight_Wall, MapEventID.MapACTION_TREASURE_BOX, AutoActionList.non),
                GroundCollision
        };
    }
}
