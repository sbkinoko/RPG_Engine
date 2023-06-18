package com.sbkinoko.sbkinokorpg.dataList;

import com.sbkinoko.sbkinokorpg.battleframe.status.battle_params.ATK;
import com.sbkinoko.sbkinokorpg.battleframe.status.battle_params.BattleParam;
import com.sbkinoko.sbkinokorpg.battleframe.status.battle_params.DEF;
import com.sbkinoko.sbkinokorpg.game_item.eqp_item.EQPItemData;

public class List_Equipment {

    static final int
            where_All = -1,
            where_Hand1 = 0,
            where_Clothes = 2,
            where_Hand2 = 1,
            hand1_sword = 0,
            hand2_shield = 0,
            clothes_armor = 0;

    static BattleParam[] non = new BattleParam[]{
            new ATK(0)
    };

    public static final EQPItemData[] eqpDataList = new EQPItemData[]{
            new EQPItemData("ダミー", where_Hand1, hand1_sword, 10, non),//0
            new EQPItemData("なし", where_All, where_Hand1, 10, non),//0
            new EQPItemData("剣1", where_Hand1, where_Hand1, 10,
                    new BattleParam[]{new ATK(10)}),//0
            new EQPItemData("剣2", where_Hand1, hand1_sword, 10,
                    new BattleParam[]{new ATK(20)}),//0
            new EQPItemData("剣3", where_Hand1, hand1_sword, 10,
                    new BattleParam[]{new ATK(30)}),//0
            new EQPItemData("剣4", where_Hand1, hand1_sword, 10,
                    new BattleParam[]{new ATK(40)}),//0
            new EQPItemData("盾", where_Hand2, hand2_shield, 10,
                    new BattleParam[]{new DEF(10)}),//0
            new EQPItemData("鎧", where_Clothes, clothes_armor, 10,
                    new BattleParam[]{new DEF(10)}),//0
    };


    public static int getListLength() {
        return eqpDataList.length;
    }

    public static String getNameID(int i) {
        return eqpDataList[i].getName();
    }

    public static int getWhere(int i) {
        return eqpDataList[i].getWhere();
    }

    public static int getPrice(int i) {
        return eqpDataList[i].getPrice();
    }

    /**
     * @param i 装備のID
     * @return 上がる能力のリスト
     */
    public static BattleParam[] getUpStatus_ID(int i) {
        return eqpDataList[i].getUpStatusList();
    }

    /**
     * @param haveEQP 持ってる装備
     * @param where   装備ヶ所
     * @return 装備ヶ所に付けられる装備のリスト
     */
    public static int[] getVariableEQPList(int[][] haveEQP, int where) {
        int[] tmpList = new int[haveEQP.length];
        int itemID;
        int itemNum = 0;

        for (int i = 0; i < tmpList.length; i++) {
            if (haveEQP[i][1] <= 0) {
                break;
            }

            itemID = haveEQP[i][0];
            if (itemID <= 0) {
                break;
            }

            if (eqpDataList[itemID].getWhere() != where
                    && eqpDataList[itemID].getWhere() != where_All) {
                continue;
            }

            tmpList[itemNum++] = itemID;
        }
        return tmpList;
    }
}
