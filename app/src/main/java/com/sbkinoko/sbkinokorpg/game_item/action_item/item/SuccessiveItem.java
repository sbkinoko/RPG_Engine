package com.sbkinoko.sbkinokorpg.game_item.action_item.item;

import com.sbkinoko.sbkinokorpg.battleframe.status.Status;

import java.util.Random;

public interface SuccessiveItem {
    int getSuccessiveNum();

    boolean isStoppable();

    default boolean isFinished() {
        return getSuccessiveNum() <= getATKCount();
    }

    default boolean isStopByKill(Status attackedStatus) {
        return isStoppable() &&
                !attackedStatus.isAlive();
    }

    default boolean stopAtk(Status attackedStatus) {
        incATKCount();
        return isStopByKill(attackedStatus)
                || isFinished();
    }

    int getATKCount();

    void incATKCount();

    int getTargetNum();

    default Status getTarget(Status[] enemies, int chosenEnm) {
        Status enemy;

        if (getTargetNum() == 1) {
            enemy = enemies[chosenEnm];
        } else {
            do {
                enemy = enemies[new Random().nextInt(enemies.length)];
            } while (!enemy.isAlive());
        }
        return enemy;
    }
}
