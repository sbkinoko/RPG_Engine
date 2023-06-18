package com.sbkinoko.sbkinokorpg.mapframe.map.bgcell;

import com.sbkinoko.sbkinokorpg.GameParams;
import com.sbkinoko.sbkinokorpg.R;

class MakeCell_Cloud extends MakeCell {
    @Override
    public int getBGImg(int CELL_TYPE) {
        return R.drawable.bg_00;
    }

    @Override
    public int[] getObjectImg() {
        if (player.getMoveState() == GameParams.MoveState_Sky) {
            return new int[]{R.drawable.ob_50};
        }
        return new int[]{0};
    }
}
