package com.sbkinoko.sbkinokorpg.battleframe.condition;

import android.util.Log;

import com.sbkinoko.sbkinokorpg.battleframe.status.Status;

public class Paralyze extends DefaultCondition {
    int turnCount;

    public Paralyze(int restTurn) {
        conditionData = ConditionData.CON_PARALYZE;
        turnCount = 0;
        this.restTurn = restTurn;
    }

    @Override
    public boolean isNeedBeforeStep() {
        return true;
    }

    @Override
    public boolean isSkipATKStep() {
        return true;
    }

    @Override
    public int beforeStep(Status nowPlayer) {
        checkCure();
        return 0;
    }

    @Override
    public void checkCure() {
        turnCount++;
        Log.d("test", restTurn + "");
        //todo 確率で麻痺を治すならここに記述
        if (restTurn <= turnCount
            // || new Random().nextInt() % restTurn < turnCount
        ) {
            removeCondition();
        }
    }

    @Override
    public String getTxt(Status nowPlayer, int dmg) {
        return nowPlayer.getName() + "は痺れて動けない";
    }
}
