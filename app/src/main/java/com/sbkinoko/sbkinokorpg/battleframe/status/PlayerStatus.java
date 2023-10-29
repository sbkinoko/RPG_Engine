package com.sbkinoko.sbkinokorpg.battleframe.status;

import static com.sbkinoko.sbkinokorpg.dataList.ResistanceDataList.defaultAtrResistance;
import static com.sbkinoko.sbkinokorpg.dataList.ResistanceDataList.defaultConditionResistance;

import android.content.Context;

import com.sbkinoko.sbkinokorpg.battleframe.action_choice.NormalAtkPlayerActionList;
import com.sbkinoko.sbkinokorpg.battleframe.action_choice.PlayerActionList;
import com.sbkinoko.sbkinokorpg.battleframe.status.battle_params.ATK;
import com.sbkinoko.sbkinokorpg.battleframe.status.battle_params.BattleParam;
import com.sbkinoko.sbkinokorpg.battleframe.status.battle_params.DEF;
import com.sbkinoko.sbkinokorpg.battleframe.status.battle_params.HP;
import com.sbkinoko.sbkinokorpg.battleframe.status.battle_params.MINT;
import com.sbkinoko.sbkinokorpg.battleframe.status.battle_params.MP;
import com.sbkinoko.sbkinokorpg.battleframe.status.battle_params.SPD;
import com.sbkinoko.sbkinokorpg.dataList.List_Equipment;
import com.sbkinoko.sbkinokorpg.dataList.player_status.JobStatus;
import com.sbkinoko.sbkinokorpg.dataList.player_status.List_JobStatus;
import com.sbkinoko.sbkinokorpg.application.MyEntryPoints;
import com.sbkinoko.sbkinokorpg.repository.playertool.PlayerToolRepository;

import dagger.hilt.EntryPoints;

public class PlayerStatus extends Status {
    JobStatus listPlayerStatus;
    private final int playerID;
    ATK atk;
    DEF def;
    MINT mint;
    SPD spd;

    public void changeJob(JobStatus job) {
        listPlayerStatus = job;
        setExp(exp);
    }

    private final PlayerToolRepository playerToolRepository;

    public PlayerStatus(String name, int playerID, Context context) {
        this.playerID = playerID;
        listPlayerStatus = List_JobStatus.getStatusList(playerID + 1);
        this.NAME = name;

        MyEntryPoints myEntryPoints
                = EntryPoints.get(context.getApplicationContext(), MyEntryPoints.class);
        playerToolRepository = myEntryPoints.playerToolRepository();
    }

    public int getPlayerID() {
        return playerID;
    }

    private void setStatus(int lv) {
        this.hp = new HP(listPlayerStatus.getHP(lv), listPlayerStatus.getHP(lv));
        this.mp = new MP(listPlayerStatus.getMP(lv), listPlayerStatus.getMP(lv));
        this.atk = new ATK(listPlayerStatus.getATK(lv));
        this.totalATK = new ATK(this.atk.getValue());
        this.def = new DEF(listPlayerStatus.getDEF(lv));
        this.totalDEF = new DEF(this.def.getValue());
        this.mint = new MINT(listPlayerStatus.getHealMP(lv));
        this.HealMP = new MINT(listPlayerStatus.getHealMP(lv));
        this.skills = listPlayerStatus.getSkill(lv);
        this.spd = new SPD(listPlayerStatus.getSpeed(lv));
        setTotalSpeed(listPlayerStatus.getSpeed(lv));
        setConditionResistances(defaultConditionResistance);
        setAtrResistances(defaultAtrResistance);
    }

    protected PlayerActionList playerActionList = new NormalAtkPlayerActionList();

    public void setActionList(PlayerActionList playerActionList) {
        this.playerActionList = playerActionList;
    }

    public PlayerActionList getActionList() {
        return playerActionList;
    }

    public void setThisTurnChoice(int thisTurnChoice) {
        playerActionList.setThisTurnChoice(this, thisTurnChoice);
    }

    public int[] getActionItemList() {
        return playerActionList.getActionItemList(this);
    }

    public int getActionItemPosition() {
        return playerActionList.getItemPosition(this);
    }

    public int getActionType() {
        return playerActionList.getThisTurnAction();
    }

    public static final int canHaveToolNum = 12;

    public int[] getAllTool() {
        return playerToolRepository.getAllItem(playerID);
    }

    public int getToolId(int itemPos) {
        return playerToolRepository.getItem(playerID, itemPos);
    }

    public boolean canReceiveTool() {
        //最後の道具が埋まっていなければ受け取れる
        return playerToolRepository.canReceiveTool(playerID);
    }

    public void addHaveItem(int itemNumber) {
        playerToolRepository.addItem(
                playerID,
                itemNumber);
    }

    public void decreaseItem(int itemPosition) {
        playerToolRepository.decreasePlayerTool(
                playerID,
                itemPosition);
    }

    public void setEqp(int eqpId, int where) {
        EQP[where] = eqpId;
        changeStatus(eqpId, 1);
    }

    public void changeEQP(int eqpId, int where) {
        //旧装備のステータス降下
        changeStatus(EQP[where], -1);

        //新装備のステータス上昇
        setEqp(eqpId, where);
    }

    /**
     * @param where 取得したい場所
     * @return その場所の装備
     */
    public int getEQP(int where) {
        return EQP[where];
    }

    /**
     * @param eqpId    装備のID
     * @param multiply +1なら加算　-1で減算
     */
    private void changeStatus(int eqpId, int multiply) {
        //新装備のステータス上昇
        BattleParam[] upStatusList = List_Equipment.getUpStatus_ID(eqpId);
        for (BattleParam battleParam : upStatusList) {
            int value = battleParam.getValue() * multiply;
            switch (battleParam.getParamID()) {
                case ATK:
                    totalATK = new ATK(totalATK.getValue() + value);
                    break;
                case DEF:
                    totalDEF = new DEF(totalDEF.getValue() + value);
                    break;
            }
        }
    }

    boolean isLevelUp;

    /**
     * @param e 経験値
     */
    public void addExp(int e) {
        exp += e;
        isLevelUp = checkLevelUp();
    }

    public boolean isLevelUp() {
        return isLevelUp;
    }

    public void setExp(int e) {
        exp = e;
        LV = 0;
        do {
            LV++;
        } while (listPlayerStatus.getNeedEXP(getLV() + 1) <= exp);

        setStatus(getLV());

        for (int i = 0; i < EQP_NUM; i++) {
            setEqp(EQP[i], i);
        }

    }

    public int getExp() {
        return exp;
    }

    public int getLV() {
        return LV;
    }

    /**
     * @return LevelUpがあったらtrue
     */
    private boolean checkLevelUp() {
        if (exp == 0) {
            setStatus(1);
            return false;
        }
        if (exp < listPlayerStatus.getNeedEXP(getLV() + 1)) {
            return false;
        }

        if (hp.getValue() <= 0) {                //死んでたらレベルは上がらない
            exp = listPlayerStatus.getNeedEXP(getLV() + 1) - 1;
            return false;
        }

        do {
            LV++;
        } while (listPlayerStatus.getNeedEXP(getLV() + 1) <= exp);

        setStatus(getLV());
        for (int i = 0; i < EQP_NUM; i++) {
            setEqp(EQP[i], i);
        }

        return true;
    }

    public String[] getStatuses() {
        final int index_NAME = 0,
                index_HP = 1,
                index_MP = 2,
                index_ATK = 3,
                index_DEF = 4,
                index_LV = 5,
                index_EXP = 6,
                index_Speed = 7;

        String[] tmpList = new String[Status.PARAM_NUM + Status.EQP_NUM];
        tmpList[index_NAME] = listPlayerStatus.getName();

        tmpList[index_HP] = "HP:" + hp.getRatio();
        tmpList[index_MP] = "MP:" + mp.getRatio();
        tmpList[index_ATK] = "ATK:" + totalATK.getValue();
        tmpList[index_DEF] = "DEF:" + totalDEF.getValue();
        tmpList[index_EXP] = "EXP:" + getExp();
        tmpList[index_LV] = "LV:" + getLV();
        tmpList[index_Speed] = "Spd:" + getTotalSpeed().getValue();
        for (int i = 0; i < EQP_NUM; i++) {
            tmpList[PARAM_NUM + i] = List_Equipment.getNameID(EQP[i]);
        }
        return tmpList;
    }

    public void allRecover(){
        setHP(Integer.MAX_VALUE);
        setMP(Integer.MAX_VALUE);
    }
}
