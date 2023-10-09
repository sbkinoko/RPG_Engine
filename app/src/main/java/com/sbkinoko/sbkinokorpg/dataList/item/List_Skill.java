package com.sbkinoko.sbkinokorpg.dataList.item;

import static com.sbkinoko.sbkinokorpg.battleframe.BattleConst.maxMonsNum;
import static com.sbkinoko.sbkinokorpg.battleframe.condition.ConditionData.CON_PARALYZE;
import static com.sbkinoko.sbkinokorpg.battleframe.condition.ConditionData.CON_POISON;
import static com.sbkinoko.sbkinokorpg.gameparams.GameParams.STATUS_ATK;
import static com.sbkinoko.sbkinokorpg.gameparams.WhereCanUse.canInBattle;
import static com.sbkinoko.sbkinokorpg.gameparams.WhereCanUse.canInEither;
import static com.sbkinoko.sbkinokorpg.gameparams.WhereCanUse.canInField;
import static com.sbkinoko.sbkinokorpg.gameparams.WhereCanUse.canNotInEither;

import com.sbkinoko.sbkinokorpg.battleframe.BattleConst;
import com.sbkinoko.sbkinokorpg.battleframe.condition.ConditionData;
import com.sbkinoko.sbkinokorpg.game_item.action_item.item.ActionItem;
import com.sbkinoko.sbkinokorpg.game_item.action_item.skill.ConditionSkillData;
import com.sbkinoko.sbkinokorpg.game_item.action_item.skill.KillerSkillData;
import com.sbkinoko.sbkinokorpg.game_item.action_item.skill.Skill;
import com.sbkinoko.sbkinokorpg.game_item.action_item.skill.SkillData;
import com.sbkinoko.sbkinokorpg.game_item.action_item.skill.SuccessiveSkillData;
import com.sbkinoko.sbkinokorpg.game_item.action_item.skill.WarpSkillData;
import com.sbkinoko.sbkinokorpg.gameparams.EffectType;
import com.sbkinoko.sbkinokorpg.mapframe.map.mapdata.MapChangeDataList;
import com.sbkinoko.sbkinokorpg.mylibrary.ArrayToProb;

public class List_Skill extends List_Item {
    static private final SkillData[] skillDataList = {
            //技タイプ　 どこで使えるか　技属性　威力　 対象　MP
            new SkillData("NULL", canNotInEither, EffectType.EFFECT_TYPE_NULL, 0, 0, 0, 0),
            new SkillData("一文字", canInBattle, EffectType.EFFECT_TYPE_ATK, 1, 10, maxMonsNum, 0),
            new ConditionSkillData("麻痺にする", canInBattle, EffectType.EFFECT_TYPE_CONDITION,
                    CON_PARALYZE,
                    new ArrayToProb(new int[][]{
                            {50, 2}, {30, 3}, {20, 4}
                    }),
                    1, 0),
            new SkillData("蘇生", canInEither, EffectType.EFFECT_TYPE_REVIVE, 0, 50, 1, 5),
            new ConditionSkillData("毒", canInBattle, EffectType.EFFECT_TYPE_CONDITION,
                    CON_POISON,
                    new ArrayToProb(new int[][]{
                            {50, 2}, {30, 3}, {20, 4}
                    }),
                    1, 0),
            new KillerSkillData("麻痺キラー", canInBattle, EffectType.EFFECT_TYPE_ATK, 0, 10, 1, 5,
                    new ConditionData[]{CON_PARALYZE}),
            new SkillData("MAX回復", canInEither, EffectType.EFFECT_TYPE_HEAL, 0, 9999, 1, 5),
            new SuccessiveSkillData("めった切り", canInBattle, EffectType.EFFECT_TYPE_CONTINUE_ATK,
                    0, 10, 1, 7,
                    true, new ArrayToProb(new int[][]{{100, 2}})),
            new SuccessiveSkillData("ランダム斬り", canInBattle, EffectType.EFFECT_TYPE_CONTINUE_ATK,
                    0, 10, maxMonsNum, 7,
                    false, new ArrayToProb(new int[][]{{30, 2}, {50, 3}, {20, 4}})),
            new WarpSkillData("キャンプ", canInField, 0,
                    new int[]{20, 21, MapChangeDataList.InFrontOfEnemy, -1}),

            new SkillData("全体回復", canInEither, EffectType.EFFECT_TYPE_HEAL, 0, 10, maxMonsNum, 50),//10
            new SkillData("攻撃アップ", canInBattle, EffectType.EFFECT_TYPE_BUFF, STATUS_ATK, 1, 1, 0),
            new SkillData("ハンティング", canInBattle, EffectType.EFFECT_TYPE_STEEL, 0, 10, 1, 0),
            new SkillData("かみつく", canInBattle, EffectType.EFFECT_TYPE_ATK, 0, 10, 1, 0),
            new ConditionSkillData("毒にする", canInBattle, EffectType.EFFECT_TYPE_CONDITION, CON_POISON,
                    new ArrayToProb(new int[][]{
                            {50, 2}, {30, 3}, {20, 4}
                    }),
                    1, 0),
            new SkillData("癒しの香り", canInBattle, EffectType.EFFECT_TYPE_HEAL, 0, 9999, maxMonsNum, 0),
            new SkillData("うたう", canInBattle, EffectType.EFFECT_TYPE_HEAL, 0, 30, 1, 0),
            new SkillData("sample8", canNotInEither, EffectType.EFFECT_TYPE_ATK, 0, 15, 1, 0),
            new SkillData("sample9", canNotInEither, EffectType.EFFECT_TYPE_ATK, 0, 15, 1, 0),
            new SkillData("sample10", canNotInEither, EffectType.EFFECT_TYPE_ATK, 0, 15, 1, 0),

            new SkillData("sample11", canNotInEither, EffectType.EFFECT_TYPE_ATK, 0, 15, 1, 0),//20
            new SkillData("sample12", canNotInEither, EffectType.EFFECT_TYPE_ATK, 0, 15, 1, 0),
            new SkillData("sample13", canNotInEither, EffectType.EFFECT_TYPE_ATK, 0, 15, 1, 0),
            new SkillData("逃げ出した", canInBattle, EffectType.EFFECT_TYPE_ESCAPE, 0, 15, 1, 0),
    };

    public List_Skill() {
        super(skillDataList);
    }

    public static Skill getSkillAt(int index) {
        return skillDataList[index].getSkill();
    }

    @Override
    public ActionItem getItemAt(int index) {
        return getSkillAt(index);
    }

    public int getNeedMP(int itemID) {
        return ((SkillData) dataList[itemID]).getNeedMP();
    }

    @Override
    public int getAction() {
        return BattleConst.Action_Skill;
    }

}
