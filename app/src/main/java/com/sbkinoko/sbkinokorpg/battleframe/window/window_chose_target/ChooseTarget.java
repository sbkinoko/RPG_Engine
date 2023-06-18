package com.sbkinoko.sbkinokorpg.battleframe.window.window_chose_target;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.res.ResourcesCompat;

import com.sbkinoko.sbkinokorpg.MainGame;
import com.sbkinoko.sbkinokorpg.R;
import com.sbkinoko.sbkinokorpg.battleframe.BattleConst;
import com.sbkinoko.sbkinokorpg.battleframe.BattleSystem;
import com.sbkinoko.sbkinokorpg.battleframe.status.MonsterStatus;
import com.sbkinoko.sbkinokorpg.battleframe.status.PlayerStatus;
import com.sbkinoko.sbkinokorpg.game_item.action_item.UseItem;

public class ChooseTarget extends Window_ChooseTarget {
    private int enmNum;
    private final TextView skillName;
    private PlayerStatus nowPlayer;
    private int targetNum = 1;
    private final int arrowSize;
    private int[] targetAllies;
    private int[] selectedEnm;

    public ChooseTarget(Context context, BattleSystem battleSystem) {
        super(context, battleSystem);

        this.menuTV = new TextView[BattleConst.maxMonsNum];

        arrowSize = MainGame.playWindowSize / 10;
        setMenuTvs();
        setSelectedTv(selectedTV);

        for (int i = 0; i < BattleConst.maxMonsNum; i++) {
            menuTV[i].setBackground(ResourcesCompat.getDrawable
                    (res, R.drawable.selected_frame, null));
        }

        skillName = new TextView(context);
        skillName.setVisibility(View.GONE);
        skillName.setLayoutParams(new FrameLayout.LayoutParams(
                MainGame.playWindowSize,
                MainGame.playWindowSize / 3
        ));
        skillName.setY((float) MainGame.playWindowSize / 3 * 2);
        skillName.setBackground(ResourcesCompat.getDrawable(
                res, R.drawable.character_frame, null));
        skillName.setAutoSizeTextTypeUniformWithConfiguration(
                10, 200,
                10, 0);
        skillName.setPadding(5, 5, 5, 5);
        skillName.setGravity(Gravity.CENTER);
        skillName.setMaxLines(1);

        battleFrame.getBattleFrame().addView(skillName);
    }


    @Override
    public void setFramePosition() {
        frameLayout.setLayoutParams(new FrameLayout.LayoutParams(
                MainGame.playWindowSize,
                MainGame.playWindowSize
        ));
        this.frameLayout.setBackground(null);
    }

    @Override
    public void setMenuTv(int i) {
        this.menuTV[i].setLayoutParams(new FrameLayout.LayoutParams(
                MainGame.playWindowSize / 10,
                MainGame.playWindowSize / 10
        ));
        menuTV[i].setGravity(Gravity.CENTER);
        menuTV[i].setText("↓");
    }

    private void setTarget() {
        if (canSelectEnm) {//敵を選択する
            nowPlayer.setChooseEnm(selectedEnm);
            return;
        }
        targetAllies[0] = battleFrame.battleWindow_Top.getSelectedTV();
        nowPlayer.setChooseAly(targetAllies);
    }

    private void moveAllow(int move) {
        selectedEnm[0] += move;
        if (selectedEnm[0] < 0) {
            selectedEnm[0] = enmNum - 1;
        } else if (enmNum <= selectedEnm[0]) {
            selectedEnm[0] = 0;
        }
        checkEnm(move);
        setAllow();
    }

    private void setAllow() {
        int tmpSelectedEnm;
        ImageView iv;
        MonsterStatus tmpMonster;

        for (int i = 0; i < targetNum; i++) {
            for (int j = 0; j < BattleConst.maxMonsNum; j++) {
                tmpSelectedEnm = selectedEnm[0] + i + j;
                if (battleSystem.getMonstersNum() <= tmpSelectedEnm) {
                    tmpSelectedEnm -= battleSystem.getMonstersNum();
                    if (tmpSelectedEnm == selectedEnm[0]
                            && 0 < j) {
                        return;
                    }
                }
                tmpMonster = battleSystem.getMonster(tmpSelectedEnm);
                if (tmpMonster.isAlive()) {
                    iv = tmpMonster.getIv();
                    this.menuTV[i].setX(
                            iv.getX()
                                    + (float) iv.getWidth() / 2
                                    - (float) arrowSize / 2);
                    selectedEnm[i] = tmpSelectedEnm;
                    break;//次の矢印を設定しに行く
                }
            }
        }
    }

    public void setArrowVisibility() {
        menuTV[0].setVisibility(View.VISIBLE);//1個以外非表示
        selectedEnm = new int[targetNum];
        selectedEnm[0] = nowPlayer.getChooseEnm()[0];
        int validTargetNum = 0;
        for (int i = 0; i < battleSystem.getMonstersNum(); i++) {
            if (battleSystem.getMonster(i).isAlive()) {
                validTargetNum++;
            }
        }
        if (validTargetNum < targetNum) {

            targetNum = validTargetNum;
        }

        for (int i = 1; i < BattleConst.maxMonsNum; i++) {
            if (i < targetNum) {
                menuTV[i].setVisibility(View.VISIBLE);
                selectedEnm[i] = -1;
            } else {
                menuTV[i].setVisibility(View.INVISIBLE);
            }
        }
    }

    private boolean canSelectEnm = true;

    public boolean getCanSelectEnm() {
        return canSelectEnm;
    }

    private void checkTargetType() {
        canSelectEnm = UseItem.canSelectEnm(nowPlayer.getEffectType());
        //todo 敵味方全体を追加するならここの処理
        if (canSelectEnm) {
            chooseEnm();
        } else {
            chooseAly();
        }
    }

    private void chooseEnm() {
        setArrowVisibility();
        checkEnm(1);
        setAllow();
        this.frameLayout.setVisibility(View.VISIBLE);
    }

    private void checkEnm(int move) {
        while (!battleSystem.getMonster(selectedEnm[0]).isAlive()) {
            selectedEnm[0] += move;
            if (selectedEnm[0] < 0) {
                selectedEnm[0] = enmNum - 1;
            } else if (enmNum <= selectedEnm[0]) {
                selectedEnm[0] = 0;
            }
        }
    }

    private void chooseAly() {
        int targetAlly = nowPlayer.getChooseAly()[0];
        if (targetNum == 1) {
            targetAllies = new int[1];
        } else {
            targetAllies = new int[battleSystem.getPlayerNum()];
        }
        targetAllies[0] = targetAlly;
        nowPlayer.setChooseAly(targetAllies);
        battleFrame.battleWindow_Top.openMenu(
                nowPlayer.getChooseAly(), nowPlayer.getEffectType());
        this.frameLayout.setVisibility(View.GONE);
    }

    public void setSelectedEnm(int i) {
        if (selectedEnm[0] == i) {
            useBtA();
        } else {
            selectedEnm[0] = i;
            setAllow();
        }
    }

    public void openMenu(PlayerStatus nowPlayer) {
        super.openMenu();
        this.nowPlayer = nowPlayer;
        this.skillName.setVisibility(View.VISIBLE);

        this.enmNum = battleSystem.getMonstersNum();

        float tmpHeight = battleSystem.getMonster(0).getIv().getY()
                - arrowSize;
        for (int i = 0; i < BattleConst.maxMonsNum; i++) {
            menuTV[i].setY(tmpHeight);
        }
        targetNum = nowPlayer.getActionItem().getTargetNum();
        checkTargetType();
        skillName.setText(getBottomTxt());
    }

    private String getBottomTxt() {
        return nowPlayer.getName() + ":" + nowPlayer.getActionItem().getName();
    }

    @Override
    public void closeMenu() {
        super.closeMenu();
        battleFrame.battleWindow_Top.closeMenu();
        this.skillName.setVisibility(View.GONE);
    }

    @Override
    public void useBtA() {
        if (nowPlayer.isLackOfMP()) {
            Toast.makeText(context, "MPがなくてつかえません", Toast.LENGTH_SHORT).show();
            return;
        }

        battleSystem.incWhoseActionSelect();

        setTarget();
        this.closeMenu();
        battleFrame.battleWindow_chooseAction.openMenu();
    }

    @Override
    public void useBtB() {
        nowPlayer.getActionList().backFromTarget(battleFrame, battleSystem);

        if (canSelectEnm) {
            nowPlayer.setChooseEnm(new int[]{selectedTV});
        } else {
            nowPlayer.setChooseAly(new int[]{battleFrame.battleWindow_Top.getSelectedTV()});
        }

        this.closeMenu();
    }

    @Override
    public void useBtLeft() {
        if (canSelectEnm) {
            moveAllow(-1);
        } else {
            battleFrame.battleWindow_Top.useBtLeft();
        }
    }

    @Override
    public void useBtRight() {
        if (canSelectEnm) {
            moveAllow(1);
        } else {
            battleFrame.battleWindow_Top.useBtRight();
        }
    }
}
