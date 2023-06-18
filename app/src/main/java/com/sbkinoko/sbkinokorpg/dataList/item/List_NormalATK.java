package com.sbkinoko.sbkinokorpg.dataList.item;

import static com.sbkinoko.sbkinokorpg.GameParams.EFFECT_TYPE_ATK;
import static com.sbkinoko.sbkinokorpg.GameParams.canInBattle;

import com.sbkinoko.sbkinokorpg.game_item.action_item.item.ActionItem;
import com.sbkinoko.sbkinokorpg.game_item.action_item.n_atk.N_ATK;
import com.sbkinoko.sbkinokorpg.game_item.action_item.n_atk.N_ATKData;
import com.sbkinoko.sbkinokorpg.battleframe.BattleConst;

public class List_NormalATK extends List_Item {
    static private final N_ATKData[] normalATKDataList = {
            //技タイプ　 どこで使えるか　技属性　威力　 対象　MP
            new N_ATKData("NULL", 0, 0, 0, 0, 0),
            new N_ATKData("通常攻撃", canInBattle, EFFECT_TYPE_ATK, 0, 10, 1),
    };

    public List_NormalATK() {
        super(normalATKDataList);
    }

    public static N_ATKData getN_ATKDataAt(int index) {
        return normalATKDataList[index];
    }

    public N_ATK getN_ATKAt(int index) {
        return new N_ATK(normalATKDataList[index]);
    }

    @Override
    public ActionItem getItemAt(int index) {
        return getN_ATKAt(index);
    }

    @Override
    public int getAction() {
        return BattleConst.Action_NormalATK;
    }

}
