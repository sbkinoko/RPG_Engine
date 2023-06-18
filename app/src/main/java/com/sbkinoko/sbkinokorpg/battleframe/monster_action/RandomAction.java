package com.sbkinoko.sbkinokorpg.battleframe.monster_action;

import com.sbkinoko.sbkinokorpg.battleframe.status.MonsterStatus;

import java.util.Random;

public class RandomAction implements SelectActionInterface {
    @Override
    public void selectAction(MonsterStatus monsterStatus) {
        monsterStatus.setLastSelectedSkill(new Random().nextInt(monsterStatus.getSkills().length));
    }
}
