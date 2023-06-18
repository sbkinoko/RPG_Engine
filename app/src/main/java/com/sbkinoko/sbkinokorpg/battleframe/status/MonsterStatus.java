package com.sbkinoko.sbkinokorpg.battleframe.status;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.sbkinoko.sbkinokorpg.GameParams;
import com.sbkinoko.sbkinokorpg.MainGame;
import com.sbkinoko.sbkinokorpg.battleframe.BattleFrame;
import com.sbkinoko.sbkinokorpg.battleframe.condition.Escape;
import com.sbkinoko.sbkinokorpg.battleframe.monster_action.OrderAction;
import com.sbkinoko.sbkinokorpg.battleframe.monster_action.RandomAction;
import com.sbkinoko.sbkinokorpg.battleframe.monster_action.SelectActionInterface;
import com.sbkinoko.sbkinokorpg.battleframe.status.battle_params.ATK;
import com.sbkinoko.sbkinokorpg.battleframe.status.battle_params.DEF;
import com.sbkinoko.sbkinokorpg.battleframe.status.battle_params.HP;
import com.sbkinoko.sbkinokorpg.battleframe.status.battle_params.MP;
import com.sbkinoko.sbkinokorpg.dataList.List_Monster;
import com.sbkinoko.sbkinokorpg.dataList.item.List_Skill;
import com.sbkinoko.sbkinokorpg.game_item.action_item.UseItem;
import com.sbkinoko.sbkinokorpg.game_item.action_item.nonaction.NonAction;
import com.sbkinoko.sbkinokorpg.mylibrary.ArrayToProb;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MonsterStatus extends Status {
    ImageView iv;
    int imageID;
    int monsterNum;
    int prize;
    private final Context context;
    private FrameLayout parent;
    Runnable runnable;
    List<Runnable> runnableList = new ArrayList<>();
    List<TextView> dmgTvList = new ArrayList<>();
    float ivPosi_X;
    float ivPosi_Y;
    BattleFrame battleFrame;

    SelectActionInterface selectAction;

    ArrayToProb haveItem;

    public void setBattleFrame(BattleFrame battleFrame) {
        this.battleFrame = battleFrame;
    }

    private float getRandomParam(int range) {
        return (float) (new Random().nextInt(range * 2) + 100 - range) / 100;
    }

    public MonsterStatus(int number, Context context1) {
        context = context1;

        MonsterData monsterData = List_Monster.getDataAt(number);
        int range1, range2;

        if (List_Monster.isBoss(number)) {
            range1 = 0;
            range2 = 0;
        } else {
            range1 = 20;
            range2 = 10;
        }

        int hp_int = (int) (monsterData.getHP() * getRandomParam(range1));
        int mp_int = (int) (monsterData.getMP() * getRandomParam(range1));
        this.hp = new HP(hp_int, hp_int);
        this.mp = new MP(mp_int, mp_int);
        this.totalATK = new ATK((int) (monsterData.getATK() * getRandomParam(range2)));
        this.totalDEF = new DEF((int) (monsterData.getDEF() * getRandomParam(range2)));

        setConditionResistances(monsterData.getConResistances());
        setAtrResistances(monsterData.getAtrResistances());

        setTotalSpeed(monsterData.getSPD());

        this.prize = monsterData.getPRIZE();
        this.exp = monsterData.getEXP();
        this.imageID = monsterData.getImageID();

        this.skills = new int[monsterData.getSkills().length];
        for (int i = 0; i < skills.length; i++) {
            this.skills[i] = monsterData.getSkills()[i];
        }
        this.NAME = monsterData.getName();
        selectAction = getSelectAction(monsterData.getActionSelectType());

        haveItem = monsterData.getHaveItem();
    }

    SelectActionInterface getSelectAction(int selectType) {
        switch (selectType) {
            case List_Monster.RANDOM:
                return new RandomAction();
            case List_Monster.ORDER:
                return new OrderAction();
        }
        throw new RuntimeException();
    }

    private boolean isEscape = false;

    public void escape() {
        isEscape = true;
        iv.setVisibility(View.INVISIBLE);
        resetInfo();
        addCondition(new Escape());
    }

    @Override
    public boolean isAlive() {
        return super.isAlive()
                && !isEscape;
    }

    @Override
    public boolean canRevive() {
        return super.canRevive()
                && !isEscape;
    }

    public int getMonsterNum() {
        return monsterNum;
    }

    public void setIV(ImageView imageView, FrameLayout frameLayout) {
        this.parent = frameLayout;
        this.iv = imageView;
        this.iv.setImageResource(this.imageID);
        this.iv.setVisibility(View.VISIBLE);
        ivPosi_X = iv.getX();
        ivPosi_Y = iv.getY();
    }

    public ImageView getIv() {
        return this.iv;
    }

    @Override
    public int getActionItemPosition() {
        return 0;
    }

    public void setAction() {
        if (!isAlive()) {
            setActionItem(new NonAction());
            return;
        }

        selectAction.selectAction(this);

        int skillID = skills[getLastSelectedSkill()];
        setActionItem(List_Skill.getSkillAt(skillID));
        int skillTargetNum = new List_Skill().getTargetNum(skillID);

        //todo ランダムじゃなくすならここで実装
        if (UseItem.canSelectEnm(getEffectType())) {
            setChooseEnm(
                    getTargetArray(GameParams.PLAYER_NUM, skillTargetNum));
        } else if (UseItem.canSelectAly(getEffectType())) {
            setChooseAly(
                    getTargetArray(getMonsterNum(), skillTargetNum));
        }
    }

    public int[] getTargetArray(int targetNum, int skillTargetNum) {
        int[] targetArray = new int[skillTargetNum];
        Random random = new Random();
        int firstTarget = random.nextInt(targetNum);

        for (int i = 0; i < skillTargetNum; i++) {
            targetArray[i] = -1;
        }

        for (int i = 0; i < skillTargetNum; i++) {
            targetArray[i] = firstTarget + i;
            if (targetNum <= targetArray[i]) {
                targetArray[i] -= targetNum;
            }
            if (i != 0 && targetArray[i] == firstTarget) {
                targetArray[i] = -1;
                break;//1週したから
            }
        }
        return targetArray;
    }

    public void setMonsterNum(int num) {
        monsterNum = num;
    }


    @Override
    public void decHP(int dmg) {
        super.decHP(dmg);
        setDmgTv(dmg);
    }

    private void setDmgTv(int dmg) {
        battleFrame.setShaving(true);
        TextView dmgTv = new TextView(context);
        String dmgTxt = Integer.toString(dmg);
        dmgTv.setText(dmgTxt);
        dmgTv.setLayoutParams(new ViewGroup.LayoutParams(
                iv.getWidth() / 2,
                iv.getHeight() / 2
        ));
        Random random = new Random();
        dmgTv.setX(iv.getX() + random.nextInt(iv.getWidth() * 3 / 4));
        dmgTv.setY(iv.getY() + random.nextInt(iv.getHeight() * 3 / 4));
        dmgTv.setAutoSizeTextTypeUniformWithConfiguration(10, 200, 10, 0);

        parent.addView(dmgTv);

        runnable = new Runnable() {
            int showedFrame = 0;
            final TextView tv = dmgTv;
            final int offset_1 = random.nextInt(360);
            final int offset_2 = random.nextInt(360);

            final float tvPosi_X = tv.getX();
            final float tvPosi_Y = tv.getY();

            @Override
            public void run() {
                showedFrame++;
                shakeView(showedFrame, tv, tvPosi_X, tvPosi_Y, offset_1, offset_2);
                //shakeView(showedFrame,iv, ivPosi_X, ivPosi_Y,offset_2,offset_1);
                blinking(iv, showedFrame);

                if (MAX_EFFECT_FRAME_NUM < showedFrame) {
                    removeDmgTv();
                    resetPosition();
                    battleFrame.setShaving(false);
                } else {
                    MainGame.tapHandler().postDelayed(runnable, GameParams.FrameLate);
                }
            }
        };
        runnableList.add(runnable);
        MainGame.tapHandler().post(runnable);
        dmgTvList.add(dmgTv);
    }

    public void shakeView(int showedFrame, View view, float start_x, float start_y, int offset_1, int offset_2) {
        view.setX(start_x + (float) Math.cos((96 * showedFrame + offset_1) * Math.PI / 360) * view.getWidth() / 100);
        view.setY(start_y + (float) Math.sin((72 * showedFrame + offset_2) * Math.PI / 360) * view.getHeight() / 100);
    }

    public void removeDmgTv() {
        MainGame.tapHandler().removeCallbacks(runnableList.get(0));
        runnableList.remove(0);
        parent.removeView(dmgTvList.get(0));
        dmgTvList.remove(0);
    }

    public void blinking(View view, int showedFrame) {
        if (MAX_EFFECT_FRAME_NUM < showedFrame) {
            return;//時間外なので操作なし
        }
        if (MAX_EFFECT_FRAME_NUM == showedFrame) {
            //最後なので見えなくなっていたら見えるようにする
            if (view.getVisibility() == View.INVISIBLE) {
                view.setVisibility(View.VISIBLE);
            }
            return;
        }

        //点滅処理
        switch (showedFrame % 3) {
            case 1:
                view.setVisibility(View.INVISIBLE);
                break;
            default:
                view.setVisibility(View.VISIBLE);
                break;
        }
    }


    /**
     * ivの位置をもとに戻す
     */
    public void resetPosition() {
        iv.setX(ivPosi_X);
        iv.setY(ivPosi_Y);
        if (this.hp.getValue() <= 0) {
            this.iv.setVisibility(View.GONE);
        } else {
            this.iv.setVisibility(View.VISIBLE);
        }
    }

    public int getPrize() {
        if (isEscape) {
            return 0;
        }
        return prize;
    }

    public int getExp() {
        if (isEscape) {
            return 0;
        }
        return exp;
    }

    /**
     * 落とすアイテムを決定
     * 一度取ったら不所持にする
     *
     * @return 落とすアイテム番号
     */
    public int getDropItem() {
        if (!hasItem()) {
            return 0;
        }
        int _dropItem = haveItem.getDatum();
        haveItem = null;
        return _dropItem;
    }

    public boolean hasItem() {
        return haveItem != null;
    }
}
