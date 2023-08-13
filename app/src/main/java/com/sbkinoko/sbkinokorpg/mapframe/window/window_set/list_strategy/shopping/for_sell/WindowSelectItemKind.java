package com.sbkinoko.sbkinokorpg.mapframe.window.window_set.list_strategy.shopping.for_sell;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sbkinoko.sbkinokorpg.MainGame;
import com.sbkinoko.sbkinokorpg.gameparams.GameParams;
import com.sbkinoko.sbkinokorpg.mapframe.MapFrame;
import com.sbkinoko.sbkinokorpg.mapframe.window.MapGameWindow;
import com.sbkinoko.sbkinokorpg.mapframe.window.window_set.GroupOfWindows;
import com.sbkinoko.sbkinokorpg.mapframe.window.window_set.list_strategy.shopping.for_sell.sell_item.ShoppingItemData;

public class WindowSelectItemKind extends MapGameWindow {
    private final GroupOfWindows groupOfWindows;

    public WindowSelectItemKind(Context context, GroupOfWindows groupOfWindows,
                                MapFrame mapFrame) {
        super(context, mapFrame);
        menuTV = new TextView[2];
        this.groupOfWindows = groupOfWindows;

        for (int i = 0; i < menuTV.length; i++) {
            menuTV[i] = new TextView(context);
        }
    }

    @Override
    protected void setSelectedTv(int ID) {
        super.setSelectedTv(ID);
        groupOfWindows.setWindowType(
                ShoppingItemData.getShoppingItem(ID, groupOfWindows.getPlayer()).getSellWindowType());
        groupOfWindows.getWindowDetail().showMenu(GameParams.PLAYER_NUM);
    }

    @Override
    public void useBtA() {
        groupOfWindows.getWindowDetail().openMenu();
        this.isOpen = false;
    }

    @Override
    public void useBtB() {
        closeMenu();
    }

    @Override
    public void useBtM() {
        useBtB();
    }

    @Override
    public void setMenuTv(int i) {
        menuTV[i].setText(ShoppingItemData.getShoppingItem(i, mapFrame.player).getName());
        menuTV[i].setLayoutParams(new ViewGroup.LayoutParams(
                MainGame.getPlayWindowSize_Unit() * 5,
                MainGame.getPlayWindowSize_Unit()
        ));
        menuTV[i].setY(i * MainGame.getPlayWindowSize_Unit());
    }

    @Override
    public void setFramePosition() {
        frameLayout.setY(MainGame.getPlayWindowSize_Unit());
        frameLayout.setLayoutParams(new ViewGroup.LayoutParams(
                MainGame.getPlayWindowSize_Unit() * 5,
                MainGame.getPlayWindowSize_Unit() * 2
        ));
    }

    @Override
    public void openMenu() {
        setMenuTvs();
        groupOfWindows.showMoney();
        setSelectedTv(0);
        super.openMenu();
    }

    public void reOpenMenu() {
        setSelectedTv(getSelectedTV());
        super.openMenu();
    }

    @Override
    public void closeMenu() {
        if (isOpen()) {
            super.closeMenu();
            groupOfWindows.closeShoppingWindow();
        }
    }

    @Override
    protected void tapTv(int id) {
        groupOfWindows.getWindowAmount().closeMenu();
        groupOfWindows.getWindowDetail().setIsClosed();
        isOpen = true;

        super.tapTv(id);
    }
}
