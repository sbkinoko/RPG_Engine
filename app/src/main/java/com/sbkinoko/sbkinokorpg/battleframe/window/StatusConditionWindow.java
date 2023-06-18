package com.sbkinoko.sbkinokorpg.battleframe.window;

import android.content.Context;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.sbkinoko.sbkinokorpg.GameParams;
import com.sbkinoko.sbkinokorpg.MainGame;
import com.sbkinoko.sbkinokorpg.battleframe.BattleConst;
import com.sbkinoko.sbkinokorpg.battleframe.BattleSystem;
import com.sbkinoko.sbkinokorpg.battleframe.condition.ConditionData;
import com.sbkinoko.sbkinokorpg.battleframe.condition.DefaultCondition;

public class StatusConditionWindow extends BattleGameWindow {
    int maxPageNum = 1;
    int pageNum;
    final int itemNum = 6;
    final int
            LEFT_ARROW = BattleConst.STATUS_NUM,
            RIGHT_ARROW = BattleConst.STATUS_NUM + 1;

    public StatusConditionWindow(Context context,
                                 BattleSystem battleSystem) {
        super(context, battleSystem);

        menuTV = new TextView[itemNum + 2];
        setMenuTvs();
        setSelectedTv(0);
    }

    @Override
    public void setFramePosition() {
        frameLayout.setLayoutParams(new FrameLayout.LayoutParams(
                MainGame.playWindowSize,
                MainGame.playWindowSize / 3
        ));
        frameLayout.setY((float) MainGame.playWindowSize * 2 / 3);
    }

    private void setMaxPageNum() {
        maxPageNum = battleSystem.getStatusNum() - 1;
    }

    @Override
    public void useBtA() {
        switch (selectedTV) {
            case LEFT_ARROW://左矢印
                changePage(-1);
                break;
            case RIGHT_ARROW://右矢印
                changePage(1);
                break;

            default://その他選択肢
        }
    }

    @Override
    public void useBtB() {
        this.closeMenu();
        battleFrame.battleWindow_chooseAction.openMenu();
    }

    public class tvTouch2 implements View.OnTouchListener {
        @Override
        public boolean onTouch(View view, MotionEvent event) {
            if (event.getAction() != MotionEvent.ACTION_DOWN) {
                return true;
            }

            int tappedViewID = getViewID(view);
            if (selectedTV == tappedViewID) {
                useBtA();
                return true;
            }

            if (isArrowButton(tappedViewID)) {//リスト両端の矢印を選んでる
                setSelectedTv(tappedViewID);
                return true;
            }

            return true;
        }
    }

    private boolean isArrowButton(int viewID) {
        return viewID == RIGHT_ARROW
                || viewID == LEFT_ARROW;
    }

    @Override
    public void useBtLeft() {
        switch (selectedTV) {
            case LEFT_ARROW:
                useBtA();
                break;
            case RIGHT_ARROW:
                setSelectedTv(LEFT_ARROW);
                break;
        }
    }

    @Override
    public void useBtRight() {
        switch (selectedTV) {
            case LEFT_ARROW:
                setSelectedTv(RIGHT_ARROW);
                break;
            case RIGHT_ARROW:
                useBtA();
                break;
        }
    }

    @Override
    public void useBtDown() {

    }

    @Override
    public void useBtUp() {

    }

    private void changePage(int move) {
        pageNum += move;
        if (pageNum < 0) {
            pageNum = maxPageNum;
        } else if (maxPageNum < pageNum) {
            pageNum = 0;
        }
        setMenuTvs();
        setSelectedTv(selectedTV);
    }

    private void setTVPoints(int i) {
        if (i == LEFT_ARROW) {
            menuTV[i].setLayoutParams(new FrameLayout.LayoutParams(
                    (int) (MainGame.playWindowSize * 0.1),
                    MainGame.playWindowSize / 3
            ));
            menuTV[i].setGravity(Gravity.END | Gravity.CENTER_VERTICAL);
            return;
        }

        if (i == RIGHT_ARROW) {
            menuTV[i].setLayoutParams(new FrameLayout.LayoutParams(
                    (int) (MainGame.playWindowSize * 0.1),
                    MainGame.playWindowSize / 3
            ));
            menuTV[i].setX((int) (MainGame.playWindowSize * 0.9));
            menuTV[i].setGravity(Gravity.START | Gravity.CENTER_VERTICAL);
            return;
        }

        menuTV[i].setLayoutParams(new FrameLayout.LayoutParams(
                (int) (MainGame.playWindowSize * 0.8) / 2,
                MainGame.playWindowSize / 3 / 3
        ));

        setTv_X(i);

        setTV_Y(i);
    }

    private void setTv_X(int i) {
        if (i % 2 == 0) {
            menuTV[i].setX((float) MainGame.playWindowSize / 10);//左に10分の1の隙間
        } else {
            menuTV[i].setX((float) MainGame.playWindowSize / 2);
        }
    }


    private void setTV_Y(int i) {
        int height;
        if (isArrowButton(i)) {
            height = 0;
        } else {
            height = (int) (i / 2);
        }

        menuTV[i].setY((float) MainGame.playWindowSize / 3 / 3 * (height));

    }

    private String getTxt(int i) {
        String itemName = "";

        switch (i) {
            case LEFT_ARROW:
                itemName = "〈";
                break;
            case RIGHT_ARROW:
                itemName = "〉";
                break;

            case 0:
                itemName = "攻撃:";
                if (pageNum < GameParams.PLAYER_NUM) {
                    itemName += battleSystem.getPlayer(pageNum).getTotalATK().getRank();
                }
                break;
            case 1:
                if (pageNum < GameParams.PLAYER_NUM) {
                    DefaultCondition condition = battleSystem.getPlayer(pageNum).getCondition();
                    int count = 0;
                    do {
                        if (condition.getConditionData() == ConditionData.CON_POISON) {
                            count++;
                        }
                        condition = condition.getNextCondition();
                    } while (condition != null);

                    if (count == 1) {
                        itemName = "毒 ";
                    } else if (1 < count) {
                        itemName = "毒*" + count;
                    }
                }
                break;

            default:
                itemName = pageNum + ":" + i;
                break;

        }

        return itemName;
    }

    @Override
    public void setMenuTv(int i) {
        setTVPoints(i);
        menuTV[i].setText(getTxt(i));
        menuTV[i].setOnTouchListener(new tvTouch2());
    }

    public void openMenu(int whoseAction) {
        //選択中の番号を入れるとその番号の情報を引っ張ってくる
        super.openMenu();
        setMaxPageNum();
        pageNum = whoseAction;
        setMenuTvs();
        setSelectedTv(selectedTV);
    }
}
