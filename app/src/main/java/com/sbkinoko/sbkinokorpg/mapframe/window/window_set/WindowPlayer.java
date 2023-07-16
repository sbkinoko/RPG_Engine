package com.sbkinoko.sbkinokorpg.mapframe.window.window_set;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.res.ResourcesCompat;

import com.sbkinoko.sbkinokorpg.GameParams;
import com.sbkinoko.sbkinokorpg.MainGame;
import com.sbkinoko.sbkinokorpg.R;
import com.sbkinoko.sbkinokorpg.dataList.List_Equipment;
import com.sbkinoko.sbkinokorpg.game_item.action_item.tool_give.ToolGive;
import com.sbkinoko.sbkinokorpg.game_item.action_item.tool.LastItemUseUpDate;
import com.sbkinoko.sbkinokorpg.game_item.use_item.UseItem;
import com.sbkinoko.sbkinokorpg.game_item.use_item.UseItemInField;
import com.sbkinoko.sbkinokorpg.mapframe.MapFrame;
import com.sbkinoko.sbkinokorpg.mapframe.UseUpInfo;
import com.sbkinoko.sbkinokorpg.mapframe.event.MapEvent;
import com.sbkinoko.sbkinokorpg.mapframe.window.MapGameWindow;
import com.sbkinoko.sbkinokorpg.mapframe.window.WindowIdList;
import com.sbkinoko.sbkinokorpg.mapframe.window.window_set.list_strategy.StrategyForList;
import com.sbkinoko.sbkinokorpg.window.MenuWindowInterface;

import java.util.Arrays;

public class WindowPlayer extends MapGameWindow implements MenuWindowInterface {
    GroupOfWindows groupOfWindows;

    public WindowPlayer(Context context, MapFrame mapFrame, GroupOfWindows groupOfWindows) {
        super(context, mapFrame);
        this.groupOfWindows = groupOfWindows;
    }

    @Override
    public void setFramePosition() {
        if (menuTV == null) {
            return;
        }
        frameLayout.setLayoutParams(new FrameLayout.LayoutParams(
                MainGame.playWindowSize / 2,
                MainGame.playWindowSize / 10 * (menuTV.length)
        ));
    }

    @Override
    public void useBtA() {
        getStrategyForList().useBtA_Player();
    }

    public void goToDetailWindow() {
        groupOfWindows.getWindowExplanation().openMenu();
        groupOfWindows.getWindowDetail().openMenu(selectedTV);
        groupOfWindows.setFromPlayer();
    }

    public void goToUseWindow() {
        if (allSelect) {
            useItem(getAllSelectablePlayers());
        } else {
            useItem(new int[]{selectedTV});
        }
        groupOfWindows.setToPlayer();
    }

    public void giveProcess() {
        groupOfWindows.setToPlayer();
        UseUpInfo useUpInfo = ToolGive.giveProcess(
                groupOfWindows.getFromPlayer(),
                groupOfWindows.getToPlayer(),
                groupOfWindows.getSelectedItemPosition(),
                mapFrame.player,
                mapFrame.battleSystem.getPlayers());
        groupOfWindows.getWindowDetail().reloadMenu();
        groupOfWindows.getWindowText().openMenu(useUpInfo);
    }

    @Override
    public void setMenuTv(int i) {
        menuTV[i].setLayoutParams(new LinearLayout.LayoutParams(
                MainGame.playWindowSize / 2,
                MainGame.playWindowSize / 10
        ));
        menuTV[i].setOnTouchListener(new tvTouch2());
        String txt = setPlayerName(i);
        menuTV[i].setY(i * (float) MainGame.playWindowSize / 10);
        menuTV[i].setText(txt);
    }

    private String setPlayerName(int i) {
        if (groupOfWindows.getPlayerStatuses().length <= i) {
            return "ふくろ";
        }

        return this.groupOfWindows.getPlayerStatuses()[i].getName();
    }

    public void useItem(int[] selectedPlayer) {
        //使用後にプレイヤー以外を選択する場合
        if (groupOfWindows.getActionItem().getEffect()
                == GameParams.EFFECT_TYPE_WARP) {
            new MapEvent(mapFrame).openWarpMenu();
            return;
        }

        //使うとアイテムの位置かずれる恐れがあるため先に文字列を取得しておく
        final String txt1 = getUseTxt(selectedPlayer);

        UseItemInField.useInField(
                groupOfWindows,
                selectedPlayer,
                new MapEvent(mapFrame));

        changeDetailContent();

        //アイテムを使い切った場合はウインドウを閉じたい
        UseUpInfo useUpInfo = new UseUpInfo(txt1, LastItemUseUpDate.isLastItemUseUp());

        mapFrame.getMapTextBoxWindow().openMenu(useUpInfo);
    }

    private String getUseTxt(int[] selectedPlayer) {
        String txt1 = "";
        if (selectedPlayer.length == 1 && selectedPlayer[0] != -1) {
            txt1 = groupOfWindows.getPlayerStatuses()[selectedPlayer[0]].getName() + "に";
        } else if (selectedPlayer[0] != -1) {
            txt1 = groupOfWindows.getPlayerStatuses()[selectedPlayer[0]].getName() + "達に";
        }

        txt1 += groupOfWindows.getActionItem().getName();
        txt1 += "を使った";
        return txt1;
    }

    private void changeDetailContent() {
        groupOfWindows.getWindowDetail().setNowList();
        groupOfWindows.getWindowDetail().resetMenuTvs();
    }

    @Override
    public void setSelectedTv(int ID) {
        selectedTV = ID;
        if (allSelect) {
            setAllSelectableTV();
            return;
        }
        for (int i = 0; i < this.menuTV.length; i++) {
            if (i != this.selectedTV) {
                this.menuTV[i].setBackground(null);
            } else {
                this.menuTV[i].setBackground(ResourcesCompat.getDrawable(res, R.drawable.selected_frame, null));
                groupOfWindows.getWindowDetail().showMenu(i);
            }
        }
    }

    private void setAllSelectableTV() {
        for (int i = 0; i < this.menuTV.length; i++) {
            if (canSelectTV(i)) {
                this.menuTV[i].setBackground(ResourcesCompat.getDrawable(res, R.drawable.selected_frame, null));
            } else {
                this.menuTV[i].setBackground(null);
            }

            if (i == this.selectedTV) {
                groupOfWindows.getWindowDetail().showMenu(i);
            }
        }
    }

    public StrategyForList getStrategyForList() {
        return groupOfWindows.getStrategyForList();
    }

    public void useBtB() {
        getStrategyForList().useBtB_Player();
    }

    public void goToSkillList() {
        groupOfWindows.setToPlayer();
        openMenu(WindowIdList.NUM_MapMenu_SKILL_SEE);
        groupOfWindows.getWindowExplanation().hideMsg();
        groupOfWindows.getWindowDetail().openMenu();
    }

    public void goToEQPList() {
        groupOfWindows.setToPlayer();
        openMenu(WindowIdList.NUM_MapMenu_EQP_LIST);
        groupOfWindows.getWindowExplanation().hideMsg();
        groupOfWindows.getWindowDetail().openMenu();
    }

    public void goToUseOrGive() {
        groupOfWindows.setToPlayer();
        openMenu(WindowIdList.NUM_MapMenu_ITEM_SEE);
        groupOfWindows.getWindowDetail().openMenu();
        mapFrame.window_explanation.setUseOrGive();
    }

    public void showMenu(int windowType) {
        openMenu(windowType);
        this.isOpen = false;
    }

    public void setOpen() {
        this.isOpen = true;
    }

    @Override
    public void openMenu() {
        openMenu(groupOfWindows.getWindowType());
    }

    boolean allSelect;

    public void resetAllSelect() {
        allSelect = false;
    }

    public void openMenu(int windowType) {

        super.openMenu();
        groupOfWindows.setPlayerOpen();
        groupOfWindows.setWindowType(windowType);

        if (menuTV != null) {
            removeTVs();
        }

        if (groupOfWindows.canSeeBag()) {
            this.menuTV = new TextView[groupOfWindows.getPlayerStatuses().length + 1];
        } else {
            this.menuTV = new TextView[groupOfWindows.getPlayerStatuses().length];
        }

        setFramePosition();
        allSelect = groupOfWindows.isSelectableAll(groupOfWindows.getWindowDetail().getSelectedItemID());

        if (selectedTV != -1) {//閉じた後にもう一度表示してしまうと右のウインドウが開いた状態にならない　
            setMenuTvs();
        }

        getStrategyForList().openMenu_Player();
    }

    public void openWithFromPlayer() {
        setSelectedTv(groupOfWindows.getFromPlayer());
        correctSelectedTV();
        groupOfWindows.setPlayerOpen();
    }

    private String getUseItemName() {
        //todo EQPをactoinItemにして実装　or　Item　を実装
        if (groupOfWindows.getWindowType() == WindowIdList.EQP_LIST_TO) {
            int itemID = groupOfWindows.getPlayer().getHaveEQP()[groupOfWindows.getSelectedItemPosition()][0];
            return List_Equipment.getNameID(itemID);
        }

        return groupOfWindows.getActionItem().getName();
    }

    public void openWithComment(String txt) {
        int _toPlayer;

        mapFrame.window_explanation.setText(0, getUseItemName());
        _toPlayer = getSelectablePlayer();
        if (_toPlayer == -1) {
            return;
        }
        setSelectedTv(_toPlayer);

        mapFrame.window_explanation.setText(1, "誰に" + txt + "?");
    }

    @Override
    public void closeMenu() {
        super.closeMenu();
        selectedTV = 0;
        groupOfWindows.setFromPlayer();
        groupOfWindows.setToPlayer();
        groupOfWindows.getWindowDetail().closeMenu();
    }

    private int[] getAllSelectablePlayers() {
        int[] targets = new int[GameParams.PLAYER_NUM];

        Arrays.fill(targets, -1);
        int selectableNum = 0;
        for (int i = 0; i < targets.length; i++) {
            int targetID = selectedTV + i;
            if (targets.length <= targetID) {
                targetID -= targets.length;
            }
            if (canSelectTV(selectedTV)) {
                targets[selectableNum] = targetID;
                selectableNum++;
            }
        }

        return targets;
    }

    private int getSelectablePlayer() {
        int firstToPlayer = groupOfWindows.getToPlayer();

        if (GameParams.PLAYER_NUM <= firstToPlayer) {
            firstToPlayer = 0;
        }

        if (groupOfWindows.getWindowType() == WindowIdList.EQP_LIST_TO) {
            return firstToPlayer;
        }

        int _toPlayer = firstToPlayer;

        while (!UseItem.canSelectALY(
                groupOfWindows.getActionItem().getEffect(),
                groupOfWindows.getPlayerStatuses()[_toPlayer])) {

            _toPlayer++;
            if (GameParams.PLAYER_NUM <= _toPlayer) {
                _toPlayer -= GameParams.PLAYER_NUM;
            }

            //一周して対象がいなかった
            if (_toPlayer == firstToPlayer) {
                useBtB();
                mapFrame.getMapTextBoxWindow().canNotUse();
                return -1;
            }
        }

        return _toPlayer;
    }

    public int getSelectedTV() {
        return selectedTV;
    }

    private boolean canSelectTV(int ID) {
        return UseItem.canSelectALY(
                groupOfWindows.getActionItem().getEffect(),
                groupOfWindows.getPlayerStatuses()[ID]);
    }

    private void moveToCanSelectTv(int dir) {
        int firstToPlayer = selectedTV;
        selectedTV += dir;
        correctSelectedTV();
        if (groupOfWindows.needTarget()) {
            while (!canSelectTV(selectedTV)) {
                selectedTV += dir;
                correctSelectedTV();
                if (selectedTV == firstToPlayer) {//1周したら終了
                    return;//変わらないから終了
                }
            }
        }

        setSelectedTv(selectedTV);
    }

    public void moveToTopSelectableTV() {
        if (!groupOfWindows.needTarget()) {
            setSelectedTv(0);
            return;
        }

        for (int i = 0; i < GameParams.PLAYER_NUM; i++) {
            if (canSelectTV(selectedTV)) {
                setSelectedTv(i);
                return;
            }
        }
    }

    public void moveToBottomSelectableTV() {
        if (!groupOfWindows.needTarget()) {
            setSelectedTv(menuTV.length - 1);
            return;
        }

        for (int i = GameParams.PLAYER_NUM - 1; 0 <= i; i--) {
            if (UseItem.canSelectALY(
                    groupOfWindows.getActionItem().getEffect(),
                    groupOfWindows.getPlayerStatuses()[selectedTV])) {
                setSelectedTv(i);
                break;
            }
        }
    }

    @Override
    public void useBtUp() {
        moveToCanSelectTv(-1);
    }

    @Override
    public void useBtDown() {
        moveToCanSelectTv(1);
    }

    @Override
    public void useBtRight() {
        moveToBottomSelectableTV();
    }

    @Override
    public void useBtLeft() {
        moveToTopSelectableTV();
    }

    public class tvTouch2 implements View.OnTouchListener {
        @Override
        public boolean onTouch(View view, MotionEvent event) {
            if (event.getAction() != MotionEvent.ACTION_DOWN) {
                return true;
            }

            //メッセージが出ていたらそれを消す。
            if (groupOfWindows.getWindowText().isOpen()) {
                mapFrame.getMapTextBoxWindow().useBtB();
                return true;
            }

            tapItem(getViewID(view));

            return true;
        }
    }

    void tapItem(int viewID) {
        groupOfWindows.getStrategyForList().tapPlayer(viewID);
    }
}
