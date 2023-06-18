package com.sbkinoko.sbkinokorpg.mapframe.window;

import android.content.Context;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.res.ResourcesCompat;

import com.sbkinoko.sbkinokorpg.MainGame;
import com.sbkinoko.sbkinokorpg.R;
import com.sbkinoko.sbkinokorpg.TextData;
import com.sbkinoko.sbkinokorpg.mapframe.MapFrame;
import com.sbkinoko.sbkinokorpg.mapframe.UseUpInfo;
import com.sbkinoko.sbkinokorpg.window.TextWindow;

public class MapWindow_TextBox extends TextWindow {
    int pageNum;
    String[] text = {""};

    MapFrame mapFrame;

    public MapWindow_TextBox(Context context, MapFrame mapFrame) {
        super(context);
        this.mapFrame = mapFrame;

        this.parent = mapFrame.getFrameLayout();

        viewHeight = MainGame.playWindowSize / 10;
        viewWidth = MainGame.playWindowSize / 2;
        frameLayout.setBackground(ResourcesCompat.getDrawable(res, R.drawable.character_frame, null));
        this.setFramePosition();

        this.menuTV = new TextView[1];
        setMenuTvs();
    }

    @Override
    public void setFramePosition() {
        frameLayout.setLayoutParams(new FrameLayout.LayoutParams(
                MainGame.playWindowSize * 4 / 5,
                MainGame.playWindowSize / 5
        ));
        frameLayout.setX((float) MainGame.playWindowSize / 10);
        frameLayout.setY((float) (MainGame.playWindowSize * 8 / 10));
    }

    @Override
    public void setMenuTv(int i) {
        this.menuTV[i].setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        ));
        menuTV[i].setGravity(Gravity.CENTER);
    }

    @Override
    public void useBtB() {
        this.closeMenu();
    }


    @Override
    public void useBtA() {
        useBtB();
    }


    @Override
    public void useBtM() {
        useBtB();
    }

    private boolean isLastPage() {
        return pageNum == text.length - 1;
    }

    @Override
    public void closeMenu() {
        if (!isLastPage()) {
            pageNum++;
            menuTV[0].setText(text[pageNum]);
            return;
        }

        super.closeMenu();
        mapFrame.checkCellEvent();
        mapFrame.checkNextEvent();
        if (useUp) {
            mapFrame.window_player.useBtB();
            mapFrame.window_explanation.useBtB();
            useUp = false;
        }
    }

    public void changeTargetWindow(){
        isOpen = false;
    }

    /**
     * 入力データが複数の時のセットtxt
     *
     * @param text 　表示する文章
     */
    public void openMenu(String[] text) {
        if(text == null){
            return;
        }
        super.openMenu();
        this.text = text;
        pageNum = 0;
        menuTV[0].setText(this.text[pageNum]);
    }

    private boolean useUp;

    public void openMenu(UseUpInfo useUpInfo) {
        this.openMenu(new String[]{useUpInfo.getTxt()});
        this.useUp = useUpInfo.isUseUp();
    }

    public void canNotUse() {
        openMenu(new String[]{TextData.cantUse});
    }

}
