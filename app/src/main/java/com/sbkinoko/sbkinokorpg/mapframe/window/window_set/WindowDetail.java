package com.sbkinoko.sbkinokorpg.mapframe.window.window_set;

import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sbkinoko.sbkinokorpg.MainGame;
import com.sbkinoko.sbkinokorpg.R;
import com.sbkinoko.sbkinokorpg.TextData;
import com.sbkinoko.sbkinokorpg.battleframe.status.Status;
import com.sbkinoko.sbkinokorpg.game_item.action_item.item.ActionItem;
import com.sbkinoko.sbkinokorpg.game_item.action_item.item.WarpItem;
import com.sbkinoko.sbkinokorpg.mapframe.MapFrame;
import com.sbkinoko.sbkinokorpg.mapframe.npc.eventdata.EventShopping;
import com.sbkinoko.sbkinokorpg.mapframe.window.MapGameWindow;
import com.sbkinoko.sbkinokorpg.mapframe.window.WindowIdList;
import com.sbkinoko.sbkinokorpg.mapframe.window.window_set.list_strategy.StrategyForList;
import com.sbkinoko.sbkinokorpg.mapframe.window.window_set.list_strategy.StrategyForWarp;
import com.sbkinoko.sbkinokorpg.mapframe.window.window_set.list_strategy.shopping.StrategyForBuy;
import com.sbkinoko.sbkinokorpg.window.MenuWindowInterface;

public class WindowDetail extends MapGameWindow implements MenuWindowInterface {
    private int topItemPosition = 0;
    static public final int DETAIL_LIST_ITEM_NUM = 10;

    GroupOfWindows groupOfWindows;

    public MapFrame getMapFrame() {
        return mapFrame;
    }

    public WindowDetail(Context context, GroupOfWindows groupOfWindows,
                        MapFrame mapFrame) {
        super(context, mapFrame);
        this.menuTV = new TextView[DETAIL_LIST_ITEM_NUM];
        this.groupOfWindows = groupOfWindows;
    }

    @Override
    public void setFramePosition() {
        frameLayout.setLayoutParams(new FrameLayout.LayoutParams(
                MainGame.playWindowSize / 2,
                MainGame.playWindowSize
        ));

        frameLayout.setX((float) MainGame.playWindowSize / 2);
    }

    @Override
    public void setSelectedTv(int ID) {
        selectedTV = ID;
        for (int i = 0; i < this.menuTV.length; i++) {
            if (i != this.selectedTV) {
                this.menuTV[i].setBackground(null);
            } else {
                this.menuTV[i].setBackgroundResource(R.drawable.selected_frame);
                setEffectTxt();
            }
        }
    }

    @Override
    public void setMenuTv(int i) {
        menuTV[i].setLayoutParams(new LinearLayout.LayoutParams(
                MainGame.playWindowSize / 2,
                MainGame.playWindowSize / DETAIL_LIST_ITEM_NUM
        ));
        menuTV[i].setOnTouchListener(new tvTouch2());

        menuTV[i].setY(i * (float) MainGame.playWindowSize / DETAIL_LIST_ITEM_NUM);

        menuTV[i].setText(getTextForView(i));
    }

    /**
     * menuの文字を入れ替える
     */
    public void resetMenuTvs() {
        for (int i = 0; i < menuTV.length; i++) {
            menuTV[i].setText(getTextForView(i));
        }
    }

    private String getTextForView(int i) {
        return getStrategyForList().getText(i + topItemPosition);
    }

    private int[] nowList;

    public void setNowList() {
        nowList = getStrategyForList().getNowList();
    }

    public int[] getNowList() {
        return nowList;
    }

    public int getSelectedItemID() {
        return selectedItemID;
    }

    public boolean canSelect(int viewID) {
        return getStrategyForList().canSelect(topItemPosition + viewID);
    }

    private int savedSelectedTV = 0;
    private int savedTopItemPosition = 0;

    int selectedItemID;

    public void saveSelectedItem() {
        savedSelectedTV = selectedTV;
        savedTopItemPosition = topItemPosition;
        selectedItemID = nowList[topItemPosition + selectedTV];
    }

    public void resetSelectedTV() {
        savedSelectedTV = 0;
        savedTopItemPosition = 0;
        setSelectedTv(-1);
    }

    public int getSelectedTV() {
        return selectedTV;
    }

    public int getSavedSelectedTV() {
        return savedSelectedTV;
    }

    public int getWindowType() {
        return groupOfWindows.getWindowType();
    }

    public void showMenu(int playerID) {
        super.openMenu();
        this.isOpen = false;
        groupOfWindows.setPlayerID(playerID);

        selectedTV = -1;
        topItemPosition = 0;

        setNowList();
        setMenuTvs();
        setSelectedTv(selectedTV);
    }

    public void openMenu(int playerId) {
        super.openMenu();
        groupOfWindows.setPlayerID(playerId);

        groupOfWindows.setDetailOpen();
        setNowList();
        setMenuTvs();
        setSelectedTv(selectedTV);

        topItemPosition = 0;
        getStrategyForList().openMenu_Detail();
    }

    public void reloadMenu() {
        setNowList();

        if (nowList[0] == 0) {//一番上が空ならば
            useBtB();
        } else {
            topItemPosition = savedTopItemPosition;
            resetMenuTvs();
        }
    }

    public void openMenuWithSetting(EventShopping itemData) {
        groupOfWindows.setWindowType(WindowIdList.NUM_MapMenu_BUY);
        ((StrategyForBuy) getStrategyForList()).setItemData(itemData.getItems());
        groupOfWindows.showMoney();
        openMenu(0);
    }

    public void openWithWarpData() {
        mapFrame.window_player.closeMenu();

        ActionItem warpItem = groupOfWindows.getActionItem();
        groupOfWindows.setWindowType(((WarpItem) warpItem).getWindowId());
        ((StrategyForWarp) groupOfWindows.getStrategyForList()).setWarpItem(warpItem);
        openMenu(0);
    }

    @Override
    public void openMenu() {
        super.openMenu();
        groupOfWindows.setDetailOpen();
        reloadMenu();
        setSelectedTv(getSavedSelectedTV());
    }

    @Override
    public void closeMenu() {
        super.closeMenu();
        mapFrame.window_explanation.closeMenu();
    }

    private void setEffectTxt() {
        mapFrame.window_explanation.openMenu();
        mapFrame.window_explanation.setText(2,
                TextData.itemInfo + ":" + (selectedTV + topItemPosition));
    }

    /**
     * selectedTVを範囲内へ矯正
     * これが最終決定することは少ないのでsetSelectedTVはしない
     */
    @Override
    protected void correctSelectedTV() {
        if (groupOfWindows.getWindowType() == WindowIdList.NUM_MapMenu_STATUS) {
            if (topItemPosition + selectedTV == 0) {
                setSelectedTv(Status.PARAM_NUM);
            }
            return;
        }

        if (selectedTV < 0) {
            selectedTV = nowList.length - 1;
            return;
        }

        if (selectedTV < nowList.length) {
            return;
        }

        selectedTV = 0;
    }

    public void setOpen() {
        isOpen = true;
    }

    /**
     * detailをタップして開く
     *
     * @param index
     */
    public void goToDetailWindow(int index) {
        groupOfWindows.setFromPlayer();
        mapFrame.window_explanation.hideMsg();
        setSelectedTv(index);
    }

    /**
     * ステータスをタップして開く
     *
     * @param index
     */
    public void goToStatusWindow(int index) {
        selectedTV = index;
        while (!canSelect(getSelectedTV())) {
            selectedTV++;
            correctSelectedTV();
        }
        setSelectedTv(selectedTV);
    }

    /**
     * 1つ上のアイテムを表示する
     */
    private void scrollToUp() {
        topItemPosition--;
        if (topItemPosition < 0) {
            topItemPosition = nowList.length - DETAIL_LIST_ITEM_NUM;
            selectedTV = LAST_ITEM;
        }
        while (!canSelect(getSelectedTV())) {
            topItemPosition--;
            if (topItemPosition < 0) {
                topItemPosition = 0;
                selectedTV--;
            }
        }
        setSelectedTv(selectedTV);
        resetMenuTvs();
    }

    final int LAST_ITEM = DETAIL_LIST_ITEM_NUM - 1;

    /**
     * １つ下のアイテムを表示する
     */
    private void scrollToDown() {
        topItemPosition++;
        if (!canSelect(LAST_ITEM)) {
            topItemPosition--;
            setSelectedTv(getSelectedTV() + 1);
            if (LAST_ITEM <= getSelectedTV()) {
                selectTopItem();
            }
        } else {
            setSelectedTv(getSelectedTV());
        }
        resetMenuTvs();
    }

    private void selectTopItem() {
        topItemPosition = 0;
        if (groupOfWindows.getWindowType() != WindowIdList.NUM_MapMenu_STATUS) {
            setSelectedTv(0);
        } else {
            setSelectedTv(Status.PARAM_NUM);
        }
    }

    /**
     * 水平方向のスクロール
     *
     * @param nowTappingX
     * @param standardTappedX
     * @param scrolledFlag
     * @return
     */
    private float scroll_Horizontal(float nowTappingX, float standardTappedX, boolean scrolledFlag) {
        int scrollBoundary;
        if (scrolledFlag) {
            scrollBoundary = viewWidth / 3;
        } else {
            scrollBoundary = viewWidth / 2;
        }

        if (nowTappingX - standardTappedX < -scrollBoundary) {
            useBtLeft();
            return standardTappedX - scrollBoundary;
        }

        if (scrollBoundary >= nowTappingX - standardTappedX) {
            return standardTappedX;
        }

        useBtRight();
        return standardTappedX + scrollBoundary;
    }

    /**
     * 垂直方向のスクロール
     *
     * @param nowTappingY
     * @param standardTappedY
     * @return
     */
    private float scroll_Vertical(float nowTappingY, float standardTappedY) {
        if (nowTappingY - standardTappedY < -viewHeight) {
            topItemPosition--;
            if (topItemPosition < 0) {
                topItemPosition = 0;
                useBtUp();
            } else {
                setEffectTxt();
            }
            resetMenuTvs();
            return standardTappedY - viewHeight;
        }

        if (nowTappingY - standardTappedY <= viewHeight) {
            return standardTappedY;
        }

        topItemPosition++;
        if (!canSelect(DETAIL_LIST_ITEM_NUM - 1)) {//
            topItemPosition--;
            useBtDown();
        } else {
            setEffectTxt();
        }
        resetMenuTvs();

        return standardTappedY + viewHeight;
    }

    /**
     * dirの方向へselectedTVを移動する
     *
     * @param dir 　+1で下へ　-1で上へ
     */
    private void selectNextItem(int dir) {
        selectedTV += dir;
        correctSelectedTV();

        while (!canSelect(getSelectedTV())) {
            selectedTV += dir;
            correctSelectedTV();
        }

        setSelectedTv(selectedTV);
    }

    private void goToLastSelectableTV() {
        do {
            selectedTV++;
        } while (canSelect(getSelectedTV()));//選べなくなったら抜ける
        selectedTV--;//行きすぎなので戻る
        correctSelectedTV();
        setSelectedTv(selectedTV);
    }

    private void moveTopItemPosition() {
        topItemPosition += DETAIL_LIST_ITEM_NUM;
        if (nowList.length <= topItemPosition + DETAIL_LIST_ITEM_NUM) {
            topItemPosition = nowList.length - DETAIL_LIST_ITEM_NUM;
        }
    }

    @Override
    public void useBtA() {
        groupOfWindows.setSelectedItemPosition(selectedTV + topItemPosition);
        getStrategyForList().use_Detail();
    }

    @Override
    public void useBtB() {
        getStrategyForList().useBtB_Detail();
    }

    @Override
    public void useBtM() {
        getStrategyForList().useBtM();
        if (groupOfWindows.isShopping()) {

        } else {
            super.useBtM();
        }
    }

    @Override
    public void useBtUp() {
        if (needUpScroll()) {
            scrollToUp();
        } else {//一番上じゃない
            selectNextItem(-1);
        }
    }

    private boolean needUpScroll() {
        return (DETAIL_LIST_ITEM_NUM <= nowList.length  //リストが長い
                && selectedTV == 0)
                || (groupOfWindows.getWindowType() == WindowIdList.NUM_MapMenu_STATUS//ステータスの最上部　装備1
                && selectedTV == Status.PARAM_NUM);
    }

    @Override
    public void useBtDown() {
        if (isNeedDownScroll()) {
            scrollToDown();
        } else {
            selectNextItem(+1);
        }
    }

    private boolean isNeedDownScroll() {
        return (DETAIL_LIST_ITEM_NUM <= nowList.length //リストが全部表示できない
                && DETAIL_LIST_ITEM_NUM - 1 == selectedTV);//選択肢が一番下にいる
    }

    @Override
    public void useBtRight() {
        if (nowList.length <= DETAIL_LIST_ITEM_NUM) {
            goToLastSelectableTV();
            return;
        }

        int tmpSelectedTV = selectedTV;
        int tmpTopItemID = topItemPosition;

        moveTopItemPosition();

        selectedTV = DETAIL_LIST_ITEM_NUM - 1;//そのページの一番下を確認

        while (!canSelect(getSelectedTV())) {//アイテムが出るまで上に行く
            topItemPosition--;
            if (topItemPosition < 0) {
                topItemPosition = 0;
                selectedTV--;
            }
        }

        if (topItemPosition != tmpTopItemID) {//一番上が変更されていなければ
            selectedTV = tmpSelectedTV;//元の位置に戻す
        }

        setSelectedTv(selectedTV);
        resetMenuTvs();
    }

    public void useBtLeft() {
        if (nowList.length < DETAIL_LIST_ITEM_NUM) {
            setSelectedTv(0);
            return;
        }

        if (topItemPosition == 0) {
            setSelectedTv(0);
        } else {
            topItemPosition -= DETAIL_LIST_ITEM_NUM;
            if (topItemPosition < 0) {
                topItemPosition = 0;
            }
        }
        correctSelectedTV();
        resetMenuTvs();
    }

    void openTap(int viewID) {
        //windowが開いている時の処理
        if (!canSelect(viewID)) {//アイテムが空
            return;
        }

        if (selectedTV == viewID) {//既に選ばれているviewをタップしたので実行
            WindowDetail.this.useBtA();
            return;
        }

        getStrategyForList().tapDetailItem(viewID);
    }

    void closedTap(int viewID) {

        if (mapFrame.window_explanation.getUseOrGiveFlag()) {
            //使う渡すじゃなかったらそのまま開く→誰に? を誰の? に変える処理もする
            mapFrame.window_player.showMenu(groupOfWindows.getWindowType());
            mapFrame.window_explanation.hideMsg();
            mapFrame.getMapWindow_list_detail().openMenu();
            return;
        }

        if (groupOfWindows.isShopping()) {
            if (groupOfWindows.getWindowDetail().getSelectedTV() == viewID) {
                return;
            }
            groupOfWindows.getWindowAmount().useBtB();
        } else if (!groupOfWindows.isWindowTypeFrom()) {
            mapFrame.window_player.useBtB();
            return;
        }

        if (canSelect(viewID)) {
            getStrategyForList().tapDetailItem(viewID);
        } else {
            getStrategyForList().tapDetailItem(0);
        }
        groupOfWindows.setDetailOpen();
    }

    private StrategyForList getStrategyForList() {
        return groupOfWindows.getStrategyForList();
    }

    public class tvTouch2 implements View.OnTouchListener {
        float tappedY = 0;
        float tappedX = 0;
        boolean scrolledFlag = false;

        @Override
        public boolean onTouch(View view, MotionEvent event) {
            int viewID = getViewID(view);

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:

                    tappedY = event.getY();
                    tappedX = event.getX();
                    scrolledFlag = false;

                    if (isOpen()) {//windowが開いていない時の処理
                        openTap(viewID);
                        return true;
                    }
                    closedTap(viewID);
                    return true;

                case MotionEvent.ACTION_MOVE:
                    Log.d("msg_MW_detail", "moving");
                    tappedY = scroll_Vertical(event.getY(), tappedY);


                    float tmpTappedX = tappedX;
                    tappedX = scroll_Horizontal(event.getX(), tappedX, scrolledFlag);

                    if (!scrolledFlag && (tappedX != tmpTappedX)) {
                        scrolledFlag = true;
                    }

                    break;
            }
            return true;
        }
    }
}
