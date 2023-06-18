package com.sbkinoko.sbkinokorpg.battleframe.condition;

import com.sbkinoko.sbkinokorpg.battleframe.status.Status;

public class Poison extends DefaultCondition {
    public Poison(int restTurn) {
        conditionData = ConditionData.CON_POISON;
        this.restTurn = restTurn;
    }

    @Override
    public boolean isNeedAfterStep() {
        return true;
    }

    @Override
    public int getDamage(Status nowPlayer) {
        int dmg = nowPlayer.getMaxHP() / 10;// GameParams.CON_POISON_DMG*2;
        if (dmg == 0) {
            dmg++;
        }
        return dmg;
    }

    @Override
    public String getTxt(Status nowPlayer, int dmg) {
        return getDmgText(nowPlayer, dmg);
    }

    @Override
    public int afterStep(Status nowPlayer) {
        int dmg = getDamage(nowPlayer);
        nowPlayer.decHP(dmg);
        decRestTurn();
        checkCure();
        return dmg;
    }
}
