package com.sbkinoko.sbkinokorpg.battleframe.condition;

import com.sbkinoko.sbkinokorpg.battleframe.status.Status;

public class DefaultCondition {
    DefaultCondition preCondition = null;
    DefaultCondition nextCondition = null;

    DefaultCondition sameTypeNextCondition = null;
    DefaultCondition sameTypePreCondition = null;

    ConditionData conditionData = null;

    int restTurn = -1;

    public ConditionData getConditionData() {
        return conditionData;
    }

    public boolean isNeedBeforeStep() {
        return false;
    }

    public int beforeStep(Status nowPlayer) {
        return 0;
    }

    public void decRestTurn() {
        restTurn--;
    }

    public void checkCure() {
        if (restTurn == 0) {
            removeCondition();
        }
    }

    public void removeCondition() {
        //同じ状態異常が前にある場合
        if (sameTypePreCondition != null) {
            sameTypePreCondition.sameTypeNextCondition = sameTypeNextCondition;
            if (sameTypeNextCondition != null) {
                sameTypeNextCondition.sameTypePreCondition = sameTypePreCondition;
            }
            return;
        }
        
        //先頭で同じ状態異常がある場合
        if (sameTypeNextCondition != null) {
            if (preCondition != null) {
                preCondition.nextCondition = sameTypeNextCondition;
            }
            if (nextCondition != null) {
                nextCondition.preCondition = sameTypeNextCondition;
            }
            sameTypeNextCondition.sameTypePreCondition = null;
            return;
        }

        //先頭で同じ状態異常がない場合
        preCondition.nextCondition = nextCondition;
        if (nextCondition != null) {
            nextCondition.preCondition = preCondition;
        }
    }

    public boolean isSkipATKStep() {
        return false;
    }

    public boolean isNeedAfterStep() {
        return false;
    }

    public int getDamage(Status nowPlayer) {
        return 0;
    }

    public String getTxt(Status nowPlayer, int dmg) {
        return null;
    }

    protected String getDmgText(Status nowPlayer, int dmg) {
        return nowPlayer.getName() + "は" + getName() + "で" + dmg + "ダメージ受けた";
    }

    public int afterStep(Status nowPlayer) {
        return 0;
    }

    public String getName() {
        return conditionData.getName();
    }

    public void resetCondition() {
        nextCondition = null;
    }

    public void setNextCondition(DefaultCondition newCondition) {
        if (newCondition.getConditionData() == this.conditionData) {
            setSameTypeNextCondition(newCondition);
            return;
        }

        if (this.nextCondition == null) {
            this.nextCondition = newCondition;
            this.nextCondition.preCondition = this;
        } else {
            this.nextCondition.setNextCondition(newCondition);
        }
    }

    private void setSameTypeNextCondition(DefaultCondition newCondition) {
        if (this.sameTypeNextCondition == null) {
            this.sameTypeNextCondition = newCondition;
            newCondition.sameTypePreCondition = this;
        } else {
            sameTypeNextCondition.setSameTypeNextCondition(newCondition);
        }
    }

    public DefaultCondition getNextCondition() {
        return nextCondition;
    }

    public DefaultCondition getSameTypeNextCondition() {
        return sameTypeNextCondition;
    }
}
