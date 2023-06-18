package com.sbkinoko.sbkinokorpg.mapframe.window.window_option;

import android.content.Context;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sbkinoko.sbkinokorpg.MainGame;
import com.sbkinoko.sbkinokorpg.mapframe.MapFrame;
import com.sbkinoko.sbkinokorpg.mapframe.window.MapGameWindow;
import com.sbkinoko.sbkinokorpg.window.MenuWindowInterface;

public class Window_Option extends MapGameWindow implements MenuWindowInterface {
    static final int monster = 0,
            collision = 1,
            escape = 2,
            moveSpeed = 3,
            button = 4,
            stick = 5,
            cellNum = 6,
            back = 7;

    public Window_Option(Context context, MapFrame mapFrame) {
        super(context, mapFrame);
        menuTV = new TextView[back + 1];
        setMenuTvs();
        setSelectedTv(selectedTV);
    }

    @Override
    public void setFramePosition() {
        frameLayout.setLayoutParams(new FrameLayout.LayoutParams(
                MainGame.playWindowSize / 2,
                MainGame.playWindowSize / 10 * (back + 1)
        ));
        frameLayout.setX((float) MainGame.playWindowSize / 2);
    }

    private Option getOption(int viewID) {
        switch (viewID) {
            case monster:
                return new MonsterOption();
            case collision:
                return new CollisionOption(mapFrame);
            case escape:
                return new EscapeOption();
            case button:
                return new ButtonOption(mapFrame);
            case moveSpeed:
                return new MoveSpeedOption();
            case stick:
                return new MoveButtonOption(mapFrame);
            case cellNum:
                return new CellNumOption(mapFrame);
            case back:
                return new Back(this);
        }
        throw new RuntimeException();
    }

    @Override
    public void useBtA() {
        Option option = getOption(selectedTV);
        option.useBtA();
        menuTV[selectedTV].setText(option.getTxt());
    }

    @Override
    public void setMenuTv(int i) {
        menuTV[i].setLayoutParams(new LinearLayout.LayoutParams(
                MainGame.playWindowSize / 2,
                MainGame.playWindowSize / 10
        ));

        Option option = getOption(i);
        menuTV[i].setY(i * (float) MainGame.playWindowSize / 10);
        menuTV[i].setText(option.getTxt());
    }
}
