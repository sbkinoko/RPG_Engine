package com.sbkinoko.sbkinokorpg.battleframe.window;

import android.content.Context;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.sbkinoko.sbkinokorpg.MainGame;
import com.sbkinoko.sbkinokorpg.R;
import com.sbkinoko.sbkinokorpg.battleframe.BattleConst;
import com.sbkinoko.sbkinokorpg.battleframe.BattleSystem;
import com.sbkinoko.sbkinokorpg.battleframe.status.PlayerStatus;
import com.sbkinoko.sbkinokorpg.dataList.item.List_Item;
import com.sbkinoko.sbkinokorpg.dataList.item.List_Tool;
import com.sbkinoko.sbkinokorpg.window.MenuWindowInterface;

public class BattleWindow_ChooseItem extends BattleGameWindow implements MenuWindowInterface {
    int
            pageNum = 0,
            maxPageNum;
    int[] nowList;

    final int
            LEFT_ARROW = BattleConst.ITEM_NUM,
            RIGHT_ARROW = BattleConst.ITEM_NUM + 1;

    PlayerStatus nowPlayer;

    List_Item list_item;

    public BattleWindow_ChooseItem(Context context, BattleSystem battleSystem) {
        super(context, battleSystem);

        this.nowPlayer = battleSystem.getPlayer(0);
        nowList = new int[]{1};

        menuTV = new TextView[BattleConst.ITEM_NUM + 2];
        list_item = new List_Tool();
        setMenuTvs();
    }

    private void setMaxPageNum() {
        for (int i = 0; ; i++) {
            int lastPosition_inPage = (i + 1) * BattleConst.ITEM_NUM;
            if (nowList.length <= lastPosition_inPage) {
                maxPageNum = i;
                return;
            }

            if (nowList[lastPosition_inPage] == 0) {
                maxPageNum = i;
                return;
            }
        }
    }

    @Override
    public void useBtA() {
        switch (selectedTV) {
            case LEFT_ARROW:
                if (hasSomePages()) {
                    changePage(-1);
                }
                break;
            case RIGHT_ARROW:
                if (hasSomePages()) {
                    changePage(1);
                }
                break;

            default://その他選択肢
                int thisTurnChoice = selectedTV +
                        BattleConst.ITEM_NUM * pageNum;

                if (canUseInBattle(thisTurnChoice)) {//使えたら
                    nowPlayer.setThisTurnChoice(thisTurnChoice);
                    nowPlayer.setActionItem(list_item.getItemAt(nowList[thisTurnChoice]));
                    this.closeMenu();
                    battleFrame.battleChooseEnmWindow.openMenu(nowPlayer);
                }
        }
    }

    @Override
    public void useBtB() {
        this.closeMenu();
        battleFrame.battleWindow_chooseAction.openMenu();
    }

    private boolean canUseInBattle(int thisTurnChoice) {
        int itemID = nowList[thisTurnChoice];
        return list_item.canUseInBattle(itemID);
    }

    public class tvTouch implements View.OnTouchListener {
        @Override
        public boolean onTouch(View view, MotionEvent event) {
            if (event.getAction() != MotionEvent.ACTION_DOWN) {
                return true;
            }

            int viewID = getViewID(view);
            if (selectedTV == viewID) {
                useBtA();
                return true;
            }

            if (isArrowButton(viewID)) {//リスト両端の矢印を選んでる
                if (hasSomePages()) {//2p以上ある
                    setSelectedTv(viewID);
                }
                return true;
            }

            int positionInList = viewID + pageNum * BattleConst.ITEM_NUM;

            if (isItem(positionInList)) {
                setSelectedTv(viewID);
                return true;
            }

            return true;
        }
    }

    private boolean isArrowButton(int viewId) {
        return LEFT_ARROW <= viewId;
    }

    @Override
    public void useBtUp() {
        if (isArrowButton(selectedTV)) {
            return;
        }
        do {
            selectedTV -= 2;
            if (selectedTV < 0) {
                selectedTV += BattleConst.ITEM_NUM;
            }
        } while (canNotSelect(selectedTV));
        //ループ中で光らせるTVを変える必要はないから
        setSelectedTv(selectedTV);
    }

    @Override
    public void useBtDown() {
        if (isArrowButton(selectedTV)) {
            return;
        }

        do {
            selectedTV += 2;
            if (BattleConst.ITEM_NUM <= selectedTV) {
                selectedTV -= BattleConst.ITEM_NUM;
            }
        } while (canNotSelect(selectedTV));

        setSelectedTv(selectedTV);
    }

    @Override
    public void useBtLeft() {
        do {
            checkBtLeft();
        } while (canNotSelect(selectedTV));

        //ループ中で光らせるTVを変える必要はないから
        setSelectedTv(selectedTV);
    }

    private void checkBtLeft() {
        if (hasSomePages()) {
            switch (selectedTV) {
                case LEFT_ARROW:
                    selectedTV = RIGHT_ARROW;
                    changePage(-1);
                    return;

                case RIGHT_ARROW:
                    selectedTV = BattleConst.ITEM_NUM / 2;
                    return;
            }

            if (selectedTV % 2 == 0) {
                selectedTV = LEFT_ARROW;
                return;
            }
        }

        moveLeftRight();
    }

    @Override
    public void useBtRight() {
        do {
            checkBtRight();
        } while (canNotSelect(selectedTV));

        //ループ中で光らせるTVを変える必要はないから
        setSelectedTv(selectedTV);
    }

    private void checkBtRight() {
        if (hasSomePages()) {
            switch (selectedTV) {
                case LEFT_ARROW:
                    selectedTV = BattleConst.ITEM_NUM / 2 - 1;
                    return;
                case RIGHT_ARROW:
                    selectedTV = LEFT_ARROW;
                    changePage(+1);
                    return;
            }

            if (selectedTV % 2 == 1) {
                selectedTV = RIGHT_ARROW;
                return;
            }
        }
        moveLeftRight();
    }


    private boolean hasSomePages() {
        return 1 <= maxPageNum;
    }

    /**
     * i番目のviewが選択可能かどうか
     */
    private boolean canNotSelect(int i) {
        if (isArrowButton(i)) {
            return false;
        }

        int position = i + pageNum * BattleConst.ITEM_NUM;
        return !isItem(position);
    }


    private boolean isItem(int position) {
        if (nowList.length <= position) {
            return false;
        }

        return nowList[position] != 0;
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

    private void setTVPoints(int viewId) {
        if (viewId == LEFT_ARROW) {
            menuTV[viewId].setLayoutParams(new FrameLayout.LayoutParams(
                    (int) (MainGame.playWindowSize * 0.1),
                    MainGame.playWindowSize / 3
            ));
            menuTV[viewId].setGravity(Gravity.END | Gravity.CENTER_VERTICAL);
            return;
        }

        if (viewId == RIGHT_ARROW) {
            menuTV[viewId].setLayoutParams(new FrameLayout.LayoutParams(
                    (int) (MainGame.playWindowSize * 0.1),
                    MainGame.playWindowSize / 3
            ));
            menuTV[viewId].setX((int) (MainGame.playWindowSize * 0.9));
            menuTV[viewId].setGravity(Gravity.START | Gravity.CENTER_VERTICAL);
            return;
        }

        menuTV[viewId].setLayoutParams(new FrameLayout.LayoutParams(
                (int) (MainGame.playWindowSize * 0.8) / 2,
                MainGame.playWindowSize / 3 / 3
        ));

        setX_Tv(viewId);

        setY_Tv(viewId);
    }

    private void setX_Tv(int viewId) {
        if (viewId % 2 == 0) {
            menuTV[viewId].setX((float) MainGame.playWindowSize / 10);//左に10分の1の隙間
        } else {
            menuTV[viewId].setX((float) MainGame.playWindowSize / 2);
        }
    }

    private void setY_Tv(int viewId) {
        if (isArrowButton(viewId)) {
            return;
        }
        float y = (float) MainGame.playWindowSize / 3 / 3 * ((int) (viewId / 2.0));
        menuTV[viewId].setY(y);
    }

    private String getTxt(int viewId) {
        if (isArrowButton(viewId)
                && !hasSomePages()) {
            return "";
        }

        if (viewId == RIGHT_ARROW) {
            return "〉";
        } else if (viewId == LEFT_ARROW) {
            return "〈";
        }

        //pageNum ページ目　i番目の選択肢
        int itemNum = viewId + pageNum * BattleConst.ITEM_NUM;
        if (!isItem(itemNum)) {//アイテムが空なら
            return "";
        }

        if (canUseInBattle(itemNum)) {
            menuTV[viewId].setTextColor(res.getColor(R.color.ok, null));
        } else {
            menuTV[viewId].setTextColor(res.getColor(R.color.ng, null));
        }

        return list_item.getName(nowList[itemNum]);
    }

    @Override
    public void setMenuTv(int viewId) {
        setTVPoints(viewId);
        menuTV[viewId].setText(getTxt(viewId));
    }

    @Override
    public void setTvTouch(int viewID) {
        menuTV[viewID].setOnTouchListener(new tvTouch());
    }

    public void openMenu(PlayerStatus nowPlayer, List_Item list_item) {
        this.nowPlayer = nowPlayer;
        super.openMenu();

        nowList = nowPlayer.getActionItemList();

        int itemPosition = nowPlayer.getActionItemPosition();
        this.list_item = list_item;
        pageNum = (itemPosition - selectedTV) / BattleConst.ITEM_NUM;
        setMaxPageNum();
        setMenuTvs();
        setSelectedTv(itemPosition % BattleConst.ITEM_NUM);
    }
}