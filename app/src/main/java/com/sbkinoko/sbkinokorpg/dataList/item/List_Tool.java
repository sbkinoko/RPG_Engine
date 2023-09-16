package com.sbkinoko.sbkinokorpg.dataList.item;

import static com.sbkinoko.sbkinokorpg.gameparams.WhereCanUse.canInBattle;
import static com.sbkinoko.sbkinokorpg.gameparams.WhereCanUse.canInEither;
import static com.sbkinoko.sbkinokorpg.gameparams.WhereCanUse.canInField;
import static com.sbkinoko.sbkinokorpg.gameparams.WhereCanUse.canNotInEither;

import com.sbkinoko.sbkinokorpg.battleframe.BattleConst;
import com.sbkinoko.sbkinokorpg.game_item.action_item.item.ActionItem;
import com.sbkinoko.sbkinokorpg.game_item.action_item.tool.Tool;
import com.sbkinoko.sbkinokorpg.game_item.action_item.tool.ToolDataAction;
import com.sbkinoko.sbkinokorpg.gameparams.EffectType;

public class List_Tool extends List_Item {
    private static final ToolDataAction[] _TOOL_Data_LIST = {
            //名前　どこでつかえるか 効果 属性　数値　　対象数　使用で消失 販売価格
            new ToolDataAction("ダミー", canNotInEither, EffectType.EFFECT_TYPE_HEAL, 0, 0, 1, false),//0
            new ToolDataAction("薬草", canInEither, EffectType.EFFECT_TYPE_HEAL, 0, 30, 1, true, 100),
            new ToolDataAction("聖水", canInBattle, EffectType.EFFECT_TYPE_ATK, 0, 50, 1, true, 200),
            new ToolDataAction("炎の水", canInBattle, EffectType.EFFECT_TYPE_ATK, 0, 30, 1, true, 300),
            new ToolDataAction("焼肉セット", canInField, EffectType.EFFECT_TYPE_HEAL, 0, 30, 1, true, 400),
            new ToolDataAction("ドラゴン", canInEither, EffectType.EFFECT_TYPE_MOVE, 0, 30, 0, false, 500),
            new ToolDataAction("回復爆弾", canInEither, EffectType.EFFECT_TYPE_HEAL, 0, 30, 4, true, 600),
            new ToolDataAction("爆弾", canInBattle, EffectType.EFFECT_TYPE_ATK, 0, 30, 3, true, 700),
            new ToolDataAction("鍵", canNotInEither, EffectType.EFFECT_TYPE_ATK, 0, 30, 1, true, 800),
            new ToolDataAction("sample5", canNotInEither, EffectType.EFFECT_TYPE_ATK, 0, 30, 1, true),
            new ToolDataAction("sample6", canNotInEither, EffectType.EFFECT_TYPE_ATK, 0, 30, 1, true),
            new ToolDataAction("sample7", canNotInEither, EffectType.EFFECT_TYPE_ATK, 0, 30, 1, true),
            new ToolDataAction("sample8", canNotInEither, EffectType.EFFECT_TYPE_ATK, 0, 30, 1, true),
            new ToolDataAction("sample9", canNotInEither, EffectType.EFFECT_TYPE_ATK, 0, 30, 1, true),
            new ToolDataAction("sample10", canNotInEither, EffectType.EFFECT_TYPE_ATK, 0, 30, 1, true),
    };

    public List_Tool() {
        super(_TOOL_Data_LIST);
    }

    public static Tool getToolAt(int index) {
        return new Tool(_TOOL_Data_LIST[index]);
    }

    @Override
    public ActionItem getItemAt(int index) {
        return getToolAt(index);
    }

    @Override
    public int getAction() {
        return BattleConst.Action_Tool;
    }

    public int getPrice(int itemID) {
        return ((ToolDataAction) dataList[itemID]).getPrice();
    }

}


