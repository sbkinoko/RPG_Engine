package com.sbkinoko.sbkinokorpg.battleframe.status;

import com.sbkinoko.sbkinokorpg.GameParams;
import com.sbkinoko.sbkinokorpg.battleframe.BattleConst;
import com.sbkinoko.sbkinokorpg.battleframe.condition.DefaultCondition;
import com.sbkinoko.sbkinokorpg.battleframe.status.battle_params.ATK;
import com.sbkinoko.sbkinokorpg.battleframe.status.battle_params.DEF;
import com.sbkinoko.sbkinokorpg.battleframe.status.battle_params.HP;
import com.sbkinoko.sbkinokorpg.battleframe.status.battle_params.MINT;
import com.sbkinoko.sbkinokorpg.battleframe.status.battle_params.MP;
import com.sbkinoko.sbkinokorpg.battleframe.status.battle_params.SPD;
import com.sbkinoko.sbkinokorpg.battleframe.status.resistance.ResistanceList;
import com.sbkinoko.sbkinokorpg.game_item.action_item.item.ActionItem;
import com.sbkinoko.sbkinokorpg.game_item.action_item.n_atk.N_ATK;
import com.sbkinoko.sbkinokorpg.game_item.action_item.use_item.UseItem;

public abstract class Status {
    HP hp;

    public void decHP(int dmg) {
        hp.dec(dmg);
    }

    public void incHP(int cure) {
        hp.inc(cure);
    }

    public void setHP(int hp) {
        this.hp.setParam(hp);
    }

    public int getHP() {
        return hp.getValue();
    }

    public int getMaxHP() {
        return hp.getMax();
    }

    public boolean isAlive() {
        return 0 < getHP();
    }

    public boolean canRevive() {
        return getHP() <= 0;
    }

    MP mp;

    public void decMP(int mp) {
        this.mp.dec(mp);
    }

    public void setMP(int mp) {
        this.mp.setParam(mp);
    }

    public int getMP() {
        return this.mp.getValue();
    }

    protected ATK totalATK;

    public ATK getTotalATK() {
        return totalATK;
    }

    protected DEF totalDEF = new DEF(10);

    public void resetInfo() {
        totalATK.resetRank();
        condition.resetCondition();
    }

    protected MINT HealMP = new MINT(10);

    public MINT getHealMP() {
        return HealMP;
    }

    protected SPD totalSpeed = new SPD(10);

    public SPD getTotalSpeed() {
        return totalSpeed;
    }

    public void setTotalSpeed(int speed1) {
        this.totalSpeed = new SPD(speed1);
    }

    final static public int PARAM_NUM = 8;

    final public static int EQP_NUM = 4;
    int[] EQP = new int[EQP_NUM];

    String NAME = "";

    ResistanceList conditionResistances;

    public void setConditionResistances(int[] conditionResistances) {
        this.conditionResistances = new ResistanceList(conditionResistances);
    }

    public int getConditionResistance(int id) {
        return conditionResistances.getResistance(id).getResist();
    }

    ResistanceList atrResistances;

    public void setAtrResistances(int[] atrResistances) {
        this.atrResistances = new ResistanceList(atrResistances);
    }

    public int getAtrResistance(int id) {
        return atrResistances.getResistance(id).getResist();
    }


    int exp = 0;
    int LV = 1;

    private int[] chooseEnm = new int[]{0};
    private int[] chooseAly = new int[]{0};

    protected int[] skills;

    public int[] getSkills() {
        return skills;
    }

    private int lastSelectedSkill = 0;

    public void setLastSelectedSkill(int skillPosition) {
        lastSelectedSkill = skillPosition;
    }

    public int getLastSelectedSkill() {
        return lastSelectedSkill;
    }

    protected final int MAX_EFFECT_FRAME_NUM = GameParams.fps / 2;

    public String getName() {
        return NAME;
    }

    public void setName(String name) {
        this.NAME = name;
    }

    private ActionItem actionItem = new N_ATK(null);

    public void setActionItem(ActionItem actionItem) {
        this.actionItem = actionItem;
    }

    public ActionItem getActionItem() {
        return actionItem;
    }

    public abstract int getActionItemPosition();

    public boolean canNotAction() {
        return isActionNon()
                || !isAlive();
    }


    public boolean isActionNon() {
        return actionItem.getActionType() == BattleConst.Action_NON;
    }

    public int getEffectType() {
        return actionItem.getEffect();
    }

    public void setChooseEnm(int[] selectedEnms) {
        this.chooseEnm = selectedEnms;
    }

    public int[] getChooseEnm() {
        return this.chooseEnm;
    }

    public void correctChooseEnm(Status[] enemies) {
        for (int i = 0; i < chooseEnm.length; i++) {
            if (chooseEnm[i] < 0) {
                return;
            }

            while (!enemies[chooseEnm[i]].isAlive()) {//HPが0なら以降の対象をずらす
                for (int j = i; j < chooseEnm.length; j++) {//i番目から最後まで
                    chooseEnm[j]++;
                    if (enemies.length <= chooseEnm[j]) {
                        chooseEnm[j] -= enemies.length;
                    }
                }
            }

            if (chooseEnm[i] == chooseEnm[0] && i != 0) {//既に選んだ対象ならそれ以降をリセット
                for (int j = i; j < chooseEnm.length; j++) {
                    chooseEnm[j] = -1;//選択が1周したのでもうやめる
                }
                return;
            }
        }
    }

    public void correctChooseAly(Status[] allies) {
        int effectType = getEffectType();
        for (int i = 0; i < chooseAly.length; i++) {
            while (!UseItem.canSelectALY(effectType, allies[chooseAly[i]])) {
                for (int j = i; j < chooseAly.length; j++) {
                    chooseAly[j]++;
                    if (allies.length <= chooseAly[j]) {
                        chooseAly[j] -= allies.length;
                    }
                }
            }
            if (chooseAly[i] == chooseAly[0] && i != 0) {//既に選んだ対象ならそれ以降をリセット
                for (int j = i; j < chooseAly.length; j++) {
                    chooseAly[j] = -1;//選択が1周したのでもうやめる
                }
                return;
            }
        }
    }

    private int lastSelectedTool = 0;//どのアイテムを選んだか

    public void setLastSelectedTool(int i) {
        this.lastSelectedTool = i;
    }

    public int getLastSelectedTool() {
        return lastSelectedTool;
    }

    public boolean isLackOfMP() {
        return actionItem.isLackOfMP(getMP());
    }

    public int[] getChooseAly() {
        return this.chooseAly;
    }

    public void setChooseAly(int[] alliesChoose1) {
        this.chooseAly = alliesChoose1;
    }

    DefaultCondition condition = new DefaultCondition();

    public void addCondition(DefaultCondition CONDITION) {
        this.condition.setNextCondition(CONDITION);
    }

    public DefaultCondition getCondition() {
        return this.condition;
    }

    public String
    getActionTxt() {
        return actionItem.getActionTxt(this);
    }
}