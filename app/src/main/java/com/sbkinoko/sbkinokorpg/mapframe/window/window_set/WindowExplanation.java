package com.sbkinoko.sbkinokorpg.mapframe.window.window_set;

import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sbkinoko.sbkinokorpg.MainGame;
import com.sbkinoko.sbkinokorpg.game_item.action_item.item.ActionItem;
import com.sbkinoko.sbkinokorpg.game_item.action_item.use_item.UseItemInField;
import com.sbkinoko.sbkinokorpg.mapframe.MapFrame;
import com.sbkinoko.sbkinokorpg.mapframe.window.MapGameWindow;
import com.sbkinoko.sbkinokorpg.mapframe.window.WindowIdList;

public class WindowExplanation extends MapGameWindow {
    String txt;
    private int unitHeight, unitWidth;
    private int UseOrGiveTV = 0;
    private boolean useOrGiveFlag = false;
    GroupOfWindows groupOfWindows;

    private final int useTV = 0,
            giveTV = 1,
            infoTV = 2;


    public WindowExplanation(Context context, MapFrame mapFrame, GroupOfWindows groupOfWindows) {
        super(context, mapFrame, "info");
        this.groupOfWindows = groupOfWindows;
        this.menuTV = new TextView[3];
        setMenuTvs();
        setSelectedTv(selectedTV);
    }

    @Override
    public void setFramePosition() {
        unitHeight = MainGame.playWindowSize / 10;
        unitWidth = MainGame.playWindowSize / 2;

        frameLayout.setLayoutParams(new ViewGroup.LayoutParams(
                unitWidth,
                unitHeight * 5
        ));
        frameLayout.setY(unitHeight * 5);
    }

    public boolean getUseOrGiveFlag() {
        return useOrGiveFlag;
    }

    public void useItem() {
        ActionItem actionItem = groupOfWindows.getActionItem();

        int[] target = UseItemInField.getTarget(actionItem);

        if (target == null) {//ターゲットの設定が必要
            hideMsg();
            groupOfWindows.reOpenPlayerWindow();
        } else {//自動で選択されている
            mapFrame.window_player.useItem(target);
        }
    }

    @Override
    public void setTvTouch(int viewID) {
        menuTV[viewID].setOnTouchListener(new tvTouch2());
    }

    @Override
    public void setMenuTv(int i) {
        switch (i) {
            case 0:
            case 1:
                menuTV[i].setAutoSizeTextTypeUniformWithConfiguration(10, 200, 10, 0);
                menuTV[i].setLayoutParams(new ViewGroup.LayoutParams(
                        unitWidth,
                        unitHeight
                ));
                break;

            case 2:
                menuTV[i].setLayoutParams(new LinearLayout.LayoutParams(
                        unitWidth,
                        unitHeight * 3
                ));
                break;
        }
        menuTV[i].setY(unitHeight * i);
        Log.d("testMsg", "menuTV[" + i + "]");
        Log.d("testMsg", "size:" + menuTV[i].getWidth() + ":" + menuTV[i].getWidth());
        Log.d("testMsg", "place:" + menuTV[i].getX() + ":" + menuTV[i].getY());

    }

    public class tvTouch2 implements View.OnTouchListener {
        @Override
        public boolean onTouch(View view, MotionEvent event) {
            if (event.getAction() != MotionEvent.ACTION_DOWN) {
                return true;
            }

            int viewID = getViewID(view);

            //説明欄なので何も起こさない
            if (viewID == infoTV) {
                return true;
            }

            if (!useOrGiveFlag) {
                useBtA();
                return true;
            }

            if (viewID == selectedTV) {
                useBtA();
            } else {
                setSelectedTv(viewID);
            }
            return true;
        }
    }


    @Override
    public void useBtRight() {
        if (useOrGiveFlag) {
            setSelectedTv(1);
        }
    }

    @Override
    public void useBtLeft() {
        if (useOrGiveFlag) {
            setSelectedTv(0);
        }
    }

    @Override
    public void useBtDown() {
        if (!useOrGiveFlag) {
            return;
        }

        selectedTV++;
        selectedTV = selectedTV % 2;
        setSelectedTv(selectedTV);
    }

    @Override
    public void useBtUp() {
        if (!useOrGiveFlag) {
            return;
        }

        selectedTV++;
        selectedTV = selectedTV % 2;
        setSelectedTv(selectedTV);
    }

    @Override
    public void openMenu() {
        super.openMenu();

        setSelectedTv(-1);
        this.isOpen = false;
    }

    public void setOpen() {
        isOpen = true;
    }

    @Override
    public void closeMenu() {
        super.closeMenu();
        hideMsg();
    }

    public void setUseOrGive() {
        menuTV[useTV].setText("使う");
        menuTV[giveTV].setText("渡す");
        groupOfWindows.setExplanationOpen();

        this.useOrGiveFlag = true;
        setSelectedTv(UseOrGiveTV);
    }

    private boolean useFlag;

    public void setUse() {
        menuTV[useTV].setText("使う");
        menuTV[giveTV].setText("");
        groupOfWindows.setExplanationOpen();

        this.useFlag = true;
        setSelectedTv(0);
    }

    public void setEQP() {
        menuTV[useTV].setText("つける");
        menuTV[giveTV].setText("");
        groupOfWindows.setExplanationOpen();

        this.useFlag = true;
        setSelectedTv(0);
    }


    public boolean isUseFlag() {
        return useFlag;
    }

    /**
     * @param tvNumber 入れたいtv 2が情報用
     * @param txt      入れる文字列
     */
    public void setText(int tvNumber, String txt) {
        this.txt = txt;
        this.isOpen = false;
        setSelectedTv(-1);

        menuTV[tvNumber].setText(txt);
    }

    public void hideMsg() {
        menuTV[0].setText("");
        menuTV[1].setText("");

        setSelectedTv(-1);
        useOrGiveFlag = false;
        useFlag = false;
    }


    @Override
    public void useBtA() {
        UseOrGiveTV = selectedTV;
        switch (selectedTV) {
            case useTV:
                if (groupOfWindows.getWindowType() == WindowIdList.NUM_MapMenu_EQP_LIST) {
                    hideMsg();
                    groupOfWindows.reOpenPlayerWindow();
                    return;
                }

                //フィールドで使えるならばターゲットの数によって今後の動作を変更する
                ActionItem actionItem = groupOfWindows.getActionItem();

                if (UseItemInField.canUseInField(actionItem)) {
                    useItem();
                } else {
                    mapFrame.getMapTextBoxWindow().canNotUse();
                }
                break;

            case giveTV:
                hideMsg();
                mapFrame.window_player.openMenu(WindowIdList.NUM_MapMenu_ITEM_GIVE);
                break;
        }
    }

    @Override
    public void useBtB() {
        groupOfWindows.setDetailOpen();
        UseOrGiveTV = selectedTV;
        hideMsg();
    }
}
