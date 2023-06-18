package com.sbkinoko.sbkinokorpg.battleframe;

import com.sbkinoko.sbkinokorpg.battleframe.condition.DefaultCondition;
import com.sbkinoko.sbkinokorpg.battleframe.status.Status;

enum BattleStep {
    BEFORE {
        @Override
        boolean isNeedStep(DefaultCondition condition) {
            return condition.isNeedBeforeStep();
        }

        @Override
        int stepAction(DefaultCondition condition, Status nowPlayer) {
            return condition.beforeStep(nowPlayer);
        }

        @Override
        BattleStep getNextStep() {
            return ATK;
        }
    },
    ATK {
        @Override
        boolean isNeedStep(DefaultCondition condition) {
            return false;
        }

        @Override
        int stepAction(DefaultCondition condition, Status nowPlayer) {
            return 0;
        }

        @Override
        BattleStep getNextStep() {
            return AFTER;
        }

    },
    AFTER {
        @Override
        boolean isNeedStep(DefaultCondition condition) {
            return condition.isNeedAfterStep();
        }

        @Override
        int stepAction(DefaultCondition condition, Status nowPlayer) {
            return condition.afterStep(nowPlayer);
        }

        @Override
        BattleStep getNextStep() {
            return END;
        }
    },
    END {
        @Override
        int stepAction(DefaultCondition condition, Status nowPlayer) {
            return 0;
        }

        @Override
        boolean isNeedStep(DefaultCondition condition) {
            return false;
        }

        @Override
        BattleStep getNextStep() {
            return null;
        }
    },

    STEEL {
        @Override
        boolean isNeedStep(DefaultCondition condition) {
            return false;
        }

        @Override
        int stepAction(DefaultCondition condition, Status nowPlayer) {
            return 0;
        }

        @Override
        BattleStep getNextStep() {
            return AFTER;
        }
    };

    abstract boolean isNeedStep(DefaultCondition condition);

    abstract int stepAction(DefaultCondition condition, Status nowPlayer);

    abstract BattleStep getNextStep();
}
