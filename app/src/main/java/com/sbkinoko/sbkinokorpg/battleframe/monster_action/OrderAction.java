package com.sbkinoko.sbkinokorpg.battleframe.monster_action;


import com.sbkinoko.sbkinokorpg.battleframe.status.MonsterStatus;


public class OrderAction implements SelectActionInterface {
    int index = 0;

    @Override
    public void selectAction(MonsterStatus monsterStatus) {

        monsterStatus.setLastSelectedSkill(index);

        index++;
        if (monsterStatus.getSkills().length <= index) {
            index = 0;
        }
    }
}
