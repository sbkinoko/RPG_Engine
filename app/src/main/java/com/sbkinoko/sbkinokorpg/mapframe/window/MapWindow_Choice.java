package com.sbkinoko.sbkinokorpg.mapframe.window;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.sbkinoko.sbkinokorpg.MainGame;
import com.sbkinoko.sbkinokorpg.mapframe.MapFrame;
import com.sbkinoko.sbkinokorpg.mapframe.npc.eventdata.ChoiceData;
import com.sbkinoko.sbkinokorpg.mapframe.npc.eventdata.EventChoice;
import com.sbkinoko.sbkinokorpg.mapframe.player.Player;

public class MapWindow_Choice extends MapGameWindow {
    Player player;
    int flagID;

    ChoiceData[] choiceData = new ChoiceData[]{
            new ChoiceData(0, "")
    };

    public MapWindow_Choice(Context context, MapFrame mapFrame, Player player1) {
        super(context, mapFrame);

        this.menuTV = new TextView[1];
        this.player = player1;
        setMenuTvs();
        setSelectedTv(selectedTV);
    }

    @Override
    public void setFramePosition() {
        frameLayout.setLayoutParams(new ViewGroup.LayoutParams(
                MainGame.playWindowSize / 2,
                MainGame.playWindowSize / 5
        ));
        frameLayout.setY(MainGame.playWindowSize / 4);
    }

    @Override
    public void setMenuTv(int i) {
        menuTV[i].setLayoutParams(new ViewGroup.LayoutParams(
                viewWidth,
                viewHeight
        ));
        menuTV[i].setY(viewHeight * i);
        menuTV[i].setText(choiceData[i].getTxt());
    }

    @Override
    public void useBtB() {
        if (selectedTV == length - 1) {
            useBtA();
            return;
        }
        setSelectedTv(length - 1);
    }

    @Override
    public void useBtA() {
        player.setNextEventFlag(true);
        player.setEventFlag(new int[]{flagID, choiceData[selectedTV].getAfterID()});
        closeMenu();
        mapFrame.checkNextEvent();
    }

    @Override
    public void useBtM() {
        useBtB();
    }

    int length = 1;

    public void openMenu(EventChoice data, int flagID) {
        setSelectedTv(0);
        removeTVs();
        choiceData = data.getChoiceData();
        length = (data.getChoiceData().length);

        menuTV = new TextView[length];

        setFrameSize();
        setMenuTvs();
        setSelectedTv(selectedTV);
        this.isOpen = true;
        this.flagID = flagID;
    }

    private void setFrameSize() {
        parent.removeView(frameLayout);
        frameLayout.setLayoutParams(new FrameLayout.LayoutParams(
                MainGame.playWindowSize / 2,
                MainGame.playWindowSize / 10 * length
        ));
        parent.addView(frameLayout);
    }
}
