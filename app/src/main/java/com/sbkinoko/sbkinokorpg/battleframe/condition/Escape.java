package com.sbkinoko.sbkinokorpg.battleframe.condition;

import com.sbkinoko.sbkinokorpg.battleframe.status.Status;

public class Escape extends DefaultCondition {
    @Override
    public boolean isNeedAfterStep() {
        return true;
    }

    @Override
    public String getTxt(Status nowPlayer, int dmg) {
        return null;
    }
}
