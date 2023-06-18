package com.sbkinoko.sbkinokorpg.mapframe.window.window_main;

import android.content.Context;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sbkinoko.sbkinokorpg.MainGame;
import com.sbkinoko.sbkinokorpg.mapframe.MapFrame;
import com.sbkinoko.sbkinokorpg.mapframe.window.MapGameWindow;
import com.sbkinoko.sbkinokorpg.mapframe.window.WindowIdList;
import com.sbkinoko.sbkinokorpg.mapframe.window.window_main.menu.BackMenuItem;
import com.sbkinoko.sbkinokorpg.mapframe.window.window_main.menu.BookMenuItem;
import com.sbkinoko.sbkinokorpg.mapframe.window.window_main.menu.MainMenuItem;
import com.sbkinoko.sbkinokorpg.mapframe.window.window_main.menu.OptionMenuItem;
import com.sbkinoko.sbkinokorpg.mapframe.window.window_main.menu.SaveMenuItem;
import com.sbkinoko.sbkinokorpg.mapframe.window.window_main.menu.use_detail.EQPMenuItem;
import com.sbkinoko.sbkinokorpg.mapframe.window.window_main.menu.use_detail.SkillMenuItem;
import com.sbkinoko.sbkinokorpg.mapframe.window.window_main.menu.use_detail.StatusMenuItem;
import com.sbkinoko.sbkinokorpg.mapframe.window.window_main.menu.use_detail.ToolMenuItem;
import com.sbkinoko.sbkinokorpg.window.MenuWindowInterface;

public class Window_Main extends MapGameWindow implements MenuWindowInterface {
    final int ITEM_NUM = WindowIdList.NUM_MapMenu_BACK + 1;

    public Window_Main(Context context, MapFrame mapFrame) {
        super(context, mapFrame);

        menuTV = new TextView[ITEM_NUM];
        setMenuTvs();
        setSelectedTv(selectedTV);
    }

    @Override
    public void setFramePosition() {
        frameLayout.setLayoutParams(new FrameLayout.LayoutParams(
                MainGame.playWindowSize / 2,
                MainGame.playWindowSize / 10 * ITEM_NUM
        ));
        frameLayout.setX((float) MainGame.playWindowSize / 2);
    }

    @Override
    public void useBtA() {
        getMapMenu(selectedTV).useBtA();
        closeMenu();
    }

    MainMenuItem getMapMenu(int id) {
        MainMenuItem mainMenuItem;
        switch (id) {
            case WindowIdList.NUM_MapMenu_ITEM_SEE:
                mainMenuItem = new ToolMenuItem();
                break;
            case WindowIdList.NUM_MapMenu_SKILL_SEE:
                mainMenuItem = new SkillMenuItem();
                break;
            case WindowIdList.NUM_MapMenu_STATUS:
                mainMenuItem = new StatusMenuItem();
                break;
            case WindowIdList.NUM_MapMenu_SAVE:
                mainMenuItem = new SaveMenuItem();
                break;
            case WindowIdList.NUM_MapMenu_BOOK:
                mainMenuItem = new BookMenuItem();
                break;
            case WindowIdList.NUM_MapMenu_OPTION:
                mainMenuItem = new OptionMenuItem();
                break;
            case WindowIdList.NUM_MapMenu_BACK:
                mainMenuItem = new BackMenuItem();
                break;
            case WindowIdList.NUM_MapMenu_EQP_LIST:
                mainMenuItem = new EQPMenuItem();
                break;
            default:
                throw new RuntimeException();
        }

        mainMenuItem.setMapFrame(mapFrame);
        return mainMenuItem;
    }

    @Override
    public void useBtB() {
        closeMenu();
    }

    @Override
    public void setMenuTv(int i) {
        menuTV[i].setLayoutParams(new LinearLayout.LayoutParams(
                MainGame.playWindowSize / 2,
                MainGame.playWindowSize / 10
        ));
        menuTV[i].setY((float) i * MainGame.playWindowSize / 10);
        menuTV[i].setText(getMapMenu(i).getTxt());
    }
}

